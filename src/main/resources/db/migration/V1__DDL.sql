CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

-- ============================
-- Creation of schemas
-- ============================
CREATE SCHEMA security;
CREATE SCHEMA parties;
CREATE SCHEMA maintenance;
CREATE SCHEMA inventory;
CREATE SCHEMA finance;
CREATE SCHEMA audit;

-- ============================
-- 1) Catalogs / Domains
-- ============================
CREATE DOMAIN email AS citext
    CHECK (VALUE ~* '^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$');

CREATE DOMAIN phone AS text
    CHECK (VALUE ~ '^[0-9()+\-\s]{7,20}$');

CREATE TABLE security.app_role (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   code text UNIQUE NOT NULL, -- ADMIN, EMPLOYEE, SPECIALIST, SUPPLIER, CUSTOMER
                                   name text NOT NULL,
                                   description text
);

CREATE TABLE security.app_permission (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         code text UNIQUE NOT NULL,
                                         description text
);

CREATE TABLE security.role_permission (
                                          role_id UUID NOT NULL REFERENCES security.app_role(id) ON DELETE CASCADE,
                                          permission_id UUID NOT NULL REFERENCES security.app_permission(id) ON DELETE CASCADE,
                                          PRIMARY KEY (role_id, permission_id)
);

-- ============================
-- 2) Security / Users
-- ============================
CREATE TABLE security.app_user (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   username citext UNIQUE NOT NULL,
                                   email email UNIQUE NOT NULL,
                                   phone phone,
                                   password_hash text NOT NULL,
                                   is_active boolean NOT NULL DEFAULT true,
                                   created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE security.user_role (
                                    user_id UUID NOT NULL REFERENCES security.app_user(id) ON DELETE CASCADE,
                                    role_id UUID NOT NULL REFERENCES security.app_role(id) ON DELETE RESTRICT,
                                    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE security.user_mfa (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   user_id UUID NOT NULL REFERENCES security.app_user(id) ON DELETE CASCADE,
                                   mfa_type text NOT NULL CHECK (mfa_type IN ('TOTP','EMAIL','SMS')),
                                   target text NOT NULL, -- email or phone
                                   enabled boolean NOT NULL DEFAULT true,
                                   created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE security.password_reset (
                                         token UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         user_id UUID NOT NULL REFERENCES security.app_user(id) ON DELETE CASCADE,
                                         expires_at timestamptz NOT NULL,
                                         used_at timestamptz
);

-- ============================
-- 3) Unified Party Model
-- ============================
CREATE TABLE parties.party (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               party_type text NOT NULL CHECK (party_type IN ('PERSON','ORGANIZATION')),
                               doc_type text,      -- DPI, NIT, etc.
                               doc_number text,
                               name text NOT NULL, -- Company name or full name
                               trade_name text,
                               created_at timestamptz NOT NULL DEFAULT now()
);
CREATE UNIQUE INDEX uq_party_doc ON parties.party(doc_type, doc_number) WHERE doc_type IS NOT NULL AND doc_number IS NOT NULL;

CREATE TABLE parties.party_role (
                                    party_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE CASCADE,
                                    role text NOT NULL CHECK (role IN ('CUSTOMER','EMPLOYEE','SPECIALIST','SUPPLIER')),
                                    PRIMARY KEY (party_id, role)
);

CREATE TABLE parties.party_contact (
                                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                       party_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE CASCADE,
                                       contact_type text NOT NULL CHECK (contact_type IN ('EMAIL','PHONE','WHATSAPP')),
                                       value text NOT NULL,
                                       is_primary boolean NOT NULL DEFAULT false
);

CREATE TABLE parties.address (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 line1 text NOT NULL,
                                 line2 text,
                                 city text,
                                 state text,
                                 postal_code text,
                                 country_code char(2) NOT NULL DEFAULT 'GT'
);

CREATE TABLE parties.party_address (
                                       party_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE CASCADE,
                                       address_id UUID NOT NULL REFERENCES parties.address(id) ON DELETE CASCADE,
                                       addr_type text NOT NULL CHECK (addr_type IN ('BILLING','SHIPPING','HOME','WORK')),
                                       PRIMARY KEY (party_id, address_id)
);

CREATE TABLE parties.user_party (
                                    user_id UUID PRIMARY KEY REFERENCES security.app_user(id) ON DELETE CASCADE,
                                    party_id UUID NOT NULL UNIQUE REFERENCES parties.party(id) ON DELETE RESTRICT
);

-- ============================
-- 4) Vehicles
-- ============================
CREATE TABLE maintenance.vehicle_make (
                                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                          name text UNIQUE NOT NULL
);

CREATE TABLE maintenance.vehicle_model (
                                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                           make_id UUID NOT NULL REFERENCES maintenance.vehicle_make(id) ON DELETE RESTRICT,
                                           name text NOT NULL,
                                           UNIQUE (make_id, name)
);

CREATE TABLE maintenance.vehicle (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     customer_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT,
                                     vin text UNIQUE,
                                     plate text UNIQUE,
                                     make_id UUID NOT NULL REFERENCES maintenance.vehicle_make(id) ON DELETE RESTRICT,
                                     model_id UUID NOT NULL REFERENCES maintenance.vehicle_model(id) ON DELETE RESTRICT,
                                     model_year int CHECK (model_year BETWEEN 1950 AND extract(year from now())::int + 1),
  color text,
  created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE maintenance.vehicle_owner_history (
                                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                                   vehicle_id UUID NOT NULL REFERENCES maintenance.vehicle(id) ON DELETE CASCADE,
                                                   owner_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT,
                                                   start_at date NOT NULL,
                                                   end_at date,
                                                   CHECK (end_at IS NULL OR end_at >= start_at)
);
CREATE INDEX ix_owner_hist_vehicle ON maintenance.vehicle_owner_history(vehicle_id, start_at DESC);

-- ============================
-- 5) Work Orders
-- ============================
CREATE TABLE maintenance.maintenance_type (
                                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                              code text UNIQUE NOT NULL CHECK (code IN ('CORRECTIVE','PREVENTIVE')),
                                              name text NOT NULL
);

CREATE TABLE maintenance.work_status (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         code text UNIQUE NOT NULL CHECK (code IN ('CREATED','ASSIGNED','IN_PROGRESS','ON_HOLD','CANCELLED','COMPLETED','CLOSED','NO_AUTHORIZED')),
                                         name text NOT NULL
);

CREATE TABLE maintenance.work_order (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        code text UNIQUE NOT NULL, -- WO-YYYY-####
                                        vehicle_id UUID NOT NULL REFERENCES maintenance.vehicle(id) ON DELETE RESTRICT,
                                        customer_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT,
                                        type_id UUID NOT NULL REFERENCES maintenance.maintenance_type(id) ON DELETE RESTRICT,
                                        status_id UUID NOT NULL REFERENCES maintenance.work_status(id) ON DELETE RESTRICT,
                                        description text,
                                        opened_at timestamptz NOT NULL DEFAULT now(),
                                        closed_at timestamptz,
                                        created_by UUID NOT NULL REFERENCES security.app_user(id) ON DELETE RESTRICT
);
CREATE INDEX ix_work_order_status ON maintenance.work_order(status_id);
CREATE INDEX ix_work_order_vehicle ON maintenance.work_order(vehicle_id);

CREATE TABLE maintenance.task_type (
                                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                       name text UNIQUE NOT NULL
);

CREATE TABLE maintenance.work_task (
                                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                       work_order_id UUID NOT NULL REFERENCES maintenance.work_order(id) ON DELETE CASCADE,
                                       task_type_id UUID REFERENCES maintenance.task_type(id) ON DELETE SET NULL,
                                       description text NOT NULL,
                                       status_id UUID NOT NULL REFERENCES maintenance.work_status(id) ON DELETE RESTRICT,
                                       estimated_hours numeric(6,2) CHECK (estimated_hours >= 0),
                                       actual_hours numeric(6,2) CHECK (actual_hours >= 0),
                                       started_at timestamptz,
                                       finished_at timestamptz
);
CREATE INDEX ix_task_work ON maintenance.work_task(work_order_id);

CREATE TABLE maintenance.work_assignment (
                                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                             work_order_id UUID NOT NULL REFERENCES maintenance.work_order(id) ON DELETE CASCADE,
                                             assignee_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT, -- EMPLOYEE or SPECIALIST
                                             role text NOT NULL CHECK (role IN ('EMPLOYEE','SPECIALIST')),
                                             assigned_at timestamptz NOT NULL DEFAULT now(),
                                             released_at timestamptz
);
CREATE INDEX ix_assignment_work ON maintenance.work_assignment(work_order_id, assigned_at DESC);

CREATE TABLE maintenance.diagnosis_note (
                                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                            work_order_id UUID NOT NULL REFERENCES maintenance.work_order(id) ON DELETE CASCADE,
                                            author_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT,
                                            note text NOT NULL,
                                            created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE maintenance.timeline_event_type (
                                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                                 code text UNIQUE NOT NULL,
                                                 name text NOT NULL
);

CREATE TABLE maintenance.timeline_event (
                                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                            work_order_id UUID NOT NULL REFERENCES maintenance.work_order(id) ON DELETE CASCADE,
                                            event_type_id UUID NOT NULL REFERENCES maintenance.timeline_event_type(id) ON DELETE RESTRICT,
                                            event_at timestamptz NOT NULL DEFAULT now(),
                                            by_user UUID REFERENCES security.app_user(id) ON DELETE SET NULL,
                                            note text
);

-- ============================
-- 6) Inventory / Purchases
-- ============================
CREATE TABLE inventory.product_category (
                                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                            name text NOT NULL,
                                            parent_id UUID REFERENCES inventory.product_category(id) ON DELETE SET NULL,
                                            UNIQUE (parent_id, name)
);

CREATE TABLE inventory.product (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   sku text UNIQUE NOT NULL,
                                   name text NOT NULL,
                                   brand text,
                                   category_id UUID REFERENCES inventory.product_category(id) ON DELETE SET NULL,
                                   unit text NOT NULL DEFAULT 'UN',
                                   is_service boolean NOT NULL DEFAULT false,
                                   taxable boolean NOT NULL DEFAULT true,
                                   cost numeric(14,4) CHECK (cost >= 0),
                                   price numeric(14,4) CHECK (price >= 0),
                                   active boolean NOT NULL DEFAULT true
);

CREATE TABLE inventory.supplier_product (
                                            supplier_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE CASCADE,
                                            product_id UUID NOT NULL REFERENCES inventory.product(id) ON DELETE CASCADE,
                                            supplier_sku text,
                                            lead_time_days int CHECK (lead_time_days >= 0),
                                            PRIMARY KEY (supplier_id, product_id)
);

CREATE TABLE inventory.location (
                                    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                    code text UNIQUE NOT NULL, -- ALM-01, SHOWROOM, WORKSHOP
                                    name text NOT NULL
);

CREATE TABLE inventory.stock (
                                 product_id UUID NOT NULL REFERENCES inventory.product(id) ON DELETE CASCADE,
                                 location_id UUID NOT NULL REFERENCES inventory.location(id) ON DELETE CASCADE,
                                 qty numeric(14,3) NOT NULL DEFAULT 0,
                                 min_qty numeric(14,3) NOT NULL DEFAULT 0,
                                 PRIMARY KEY (product_id, location_id)
);

CREATE TABLE inventory.stock_movement (
                                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                          product_id UUID NOT NULL REFERENCES inventory.product(id) ON DELETE RESTRICT,
                                          location_id UUID NOT NULL REFERENCES inventory.location(id) ON DELETE RESTRICT,
                                          movement_type text NOT NULL CHECK (movement_type IN ('IN','OUT','ADJUST')),
                                          quantity numeric(14,3) NOT NULL CHECK (quantity > 0),
                                          unit_cost numeric(14,4) CHECK (unit_cost >= 0),
                                          ref_table text,   -- purchase_order, goods_receipt, invoice, work_task
                                          ref_id UUID,
                                          occurred_at timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX ix_move_prod_time ON inventory.stock_movement(product_id, occurred_at DESC);

CREATE TABLE inventory.purchase_order (
                                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                          code text UNIQUE NOT NULL, -- PO-YYYY-####
                                          supplier_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT,
                                          status text NOT NULL CHECK (status IN ('DRAFT','SENT','PARTIALLY_RECEIVED','RECEIVED','CANCELLED')),
                                          ordered_at timestamptz NOT NULL DEFAULT now(),
                                          expected_at date,
                                          currency char(3) NOT NULL DEFAULT 'GTQ',
                                          notes text
);

CREATE TABLE inventory.purchase_order_item (
                                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                               purchase_order_id UUID NOT NULL REFERENCES inventory.purchase_order(id) ON DELETE CASCADE,
                                               product_id UUID NOT NULL REFERENCES inventory.product(id) ON DELETE RESTRICT,
                                               quantity numeric(14,3) NOT NULL CHECK (quantity > 0),
                                               unit_cost numeric(14,4) NOT NULL CHECK (unit_cost >= 0),
                                               discount numeric(14,4) NOT NULL DEFAULT 0,
                                               tax_rate numeric(5,2) NOT NULL DEFAULT 12.00,
                                               UNIQUE (purchase_order_id, product_id)
);

CREATE TABLE inventory.goods_receipt (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         purchase_order_id UUID NOT NULL REFERENCES inventory.purchase_order(id) ON DELETE RESTRICT,
                                         received_at timestamptz NOT NULL DEFAULT now(),
                                         received_by UUID REFERENCES security.app_user(id)
);

CREATE TABLE inventory.goods_receipt_item (
                                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                              goods_receipt_id UUID NOT NULL REFERENCES inventory.goods_receipt(id) ON DELETE CASCADE,
                                              product_id UUID NOT NULL REFERENCES inventory.product(id) ON DELETE RESTRICT,
                                              quantity numeric(14,3) NOT NULL CHECK (quantity > 0),
                                              unit_cost numeric(14,4) NOT NULL CHECK (unit_cost >= 0)
);

-- ============================
-- 7) Quotations, Invoicing, and Payments
-- ============================
CREATE TABLE finance.quotation (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   code text UNIQUE NOT NULL, -- QT-YYYY-####
                                   work_order_id UUID NOT NULL REFERENCES maintenance.work_order(id) ON DELETE CASCADE,
                                   status text NOT NULL CHECK (status IN ('DRAFT','SENT','APPROVED','REJECTED','EXPIRED')),
                                   created_at timestamptz NOT NULL DEFAULT now(),
                                   approved_at timestamptz,
                                   approved_by UUID REFERENCES parties.party(id)
);

CREATE TABLE finance.quotation_item (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        quotation_id UUID NOT NULL REFERENCES finance.quotation(id) ON DELETE CASCADE,
                                        item_type text NOT NULL CHECK (item_type IN ('SERVICE','PART')),
                                        product_id UUID REFERENCES inventory.product(id) ON DELETE SET NULL,
                                        description text NOT NULL,
                                        quantity numeric(14,3) NOT NULL CHECK (quantity > 0),
                                        unit_price numeric(14,4) NOT NULL CHECK (unit_price >= 0),
                                        discount numeric(14,4) NOT NULL DEFAULT 0,
                                        tax_rate numeric(5,2) NOT NULL DEFAULT 12.00
);

CREATE TABLE finance.invoice (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 code text UNIQUE NOT NULL, -- INV-YYYY-####
                                 work_order_id UUID REFERENCES maintenance.work_order(id) ON DELETE SET NULL,
                                 customer_id UUID NOT NULL REFERENCES parties.party(id) ON DELETE RESTRICT,
                                 issue_date date NOT NULL DEFAULT CURRENT_DATE,
                                 status text NOT NULL CHECK (status IN ('ISSUED','PARTIALLY_PAID','PAID','VOID')),
                                 currency char(3) NOT NULL DEFAULT 'GTQ',
                                 notes text
);

CREATE TABLE finance.invoice_item (
                                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                      invoice_id UUID NOT NULL REFERENCES finance.invoice(id) ON DELETE CASCADE,
                                      item_type text NOT NULL CHECK (item_type IN ('SERVICE','PART')),
                                      product_id UUID REFERENCES inventory.product(id) ON DELETE SET NULL,
                                      description text NOT NULL,
                                      quantity numeric(14,3) NOT NULL CHECK (quantity > 0),
                                      unit_price numeric(14,4) NOT NULL CHECK (unit_price >= 0),
                                      discount numeric(14,4) NOT NULL DEFAULT 0,
                                      tax_rate numeric(5,2) NOT NULL DEFAULT 12.00
);

CREATE TABLE finance.payment_method (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        code text UNIQUE NOT NULL CHECK (code IN ('CASH','CARD','TRANSFER','CHECK','MIXED')),
                                        name text NOT NULL
);

CREATE TABLE finance.payment (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 invoice_id UUID NOT NULL REFERENCES finance.invoice(id) ON DELETE CASCADE,
                                 method_id UUID NOT NULL REFERENCES finance.payment_method(id) ON DELETE RESTRICT,
                                 amount numeric(14,4) NOT NULL CHECK (amount > 0),
                                 paid_at timestamptz NOT NULL DEFAULT now(),
                                 reference text
);
CREATE INDEX ix_payment_invoice ON finance.payment(invoice_id);

-- ============================
-- 8) Inventory ↔ Tasks/Sales relation
-- ============================
CREATE TABLE inventory.task_part_usage (
                                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                           work_task_id UUID NOT NULL REFERENCES maintenance.work_task(id) ON DELETE CASCADE,
                                           product_id UUID NOT NULL REFERENCES inventory.product(id) ON DELETE RESTRICT,
                                           location_id UUID NOT NULL REFERENCES inventory.location(id) ON DELETE RESTRICT,
                                           quantity numeric(14,3) NOT NULL CHECK (quantity > 0),
                                           unit_cost numeric(14,4) NOT NULL CHECK (unit_cost >= 0),
                                           used_at timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX ix_task_part ON inventory.task_part_usage(work_task_id);

-- ============================
-- 9) Generic Audit
-- ============================
CREATE TABLE audit.audit_log (
                                 id BIGSERIAL PRIMARY KEY,
                                 table_name text NOT NULL,
                                 entity_id uuid,
                                 action text NOT NULL CHECK (action IN ('INSERT','UPDATE','DELETE')),
  changed_by uuid REFERENCES security.app_user(id) ON DELETE SET NULL,
  changed_at timestamptz NOT NULL DEFAULT now(),
  diff jsonb
);