CREATE TABLE item (
                      id BIGSERIAL PRIMARY KEY,
                      parent_id BIGINT NOT NULL, -- References quotation.id or invoice.id
                      product_id BIGINT REFERENCES product(id) ON DELETE SET NULL, -- Optional product reference
                      quantity numeric(14,3) NOT NULL CHECK (quantity > 0) -- Item quantity
);
CREATE INDEX ix_item_parent ON item(parent_id);


-- Quotations for customer offers
CREATE TABLE quotation (
                           id BIGSERIAL PRIMARY KEY,
                           code text UNIQUE NOT NULL, -- e.g., QT-YYYY-####
                           work_order_id BIGINT NOT NULL REFERENCES work_order(id) ON DELETE CASCADE, -- Reference to work order
                           status text NOT NULL CHECK (status IN ('DRAFT', 'SENT', 'APPROVED', 'REJECTED', 'EXPIRED')), -- Quotation status
                           created_at timestamptz NOT NULL DEFAULT now(), -- Creation date
                           approved_at timestamptz, -- Approval date
                           approved_by BIGINT REFERENCES app_user(id) -- Customer who approved
);
CREATE INDEX ix_quotation_status ON quotation(status);

--------------------------------------------------------

CREATE SEQUENCE quotation_seq START 1;

CREATE OR REPLACE FUNCTION generate_quotation_code()
RETURNS TRIGGER AS $$
BEGIN
    NEW.code := 'GT-' || EXTRACT(YEAR FROM CURRENT_DATE) || '-' ||
                LPAD(nextval('quotation_seq')::text, 4, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_generate_quotation_code
    BEFORE INSERT ON quotation
    FOR EACH ROW
    EXECUTE FUNCTION generate_quotation_code();

----------------------------------------------------------

-- =======================
-- QUOTATION (5 inserts)
-- =======================
INSERT INTO quotation ( work_order_id, status)
VALUES
    ( 1,  'DRAFT'),
    ( 1,  'SENT'),
    ( 2,  'APPROVED'),
    ( 3,  'REJECTED'),
    ( 2,  'EXPIRED');

CREATE TABLE goods_receipt (
                               id BIGSERIAL PRIMARY KEY,
                               purchase_order_id BIGINT NOT NULL REFERENCES purchase_order(id) ON DELETE RESTRICT,
                               received_at timestamptz NOT NULL DEFAULT now(),
                               received_by BIGINT REFERENCES app_user(id)
);

-- =======================
-- GOODS_RECEIPT
-- =======================
INSERT INTO goods_receipt (purchase_order_id, received_at, received_by)
VALUES
    (1, '2025-08-26 10:00:00', 2),
    (2, '2025-08-26 12:30:00', 3),
    (1, '2025-08-25 09:15:00', 4),
    (2, '2025-08-24 14:45:00', 5),
    (1, '2025-08-23 16:20:00', 5);



-- Invoices (for both customers and suppliers)
CREATE TABLE invoice (
                         id BIGSERIAL PRIMARY KEY,
                         code text UNIQUE NOT NULL, -- e.g., INV-YYYY-####
                         type text NOT NULL CHECK (type IN ('CUSTOMER', 'SUPPLIER')), -- CUSTOMER (AR) or SUPPLIER (AP)
                         work_order_id BIGINT REFERENCES work_order(id) ON DELETE SET NULL, -- Optional, for customer invoices
                         goods_receipt_id BIGINT REFERENCES goods_receipt(id) ON DELETE SET NULL, -- Optional, for supplier invoices
                         user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT, -- Customer or supplier
                         issue_date date NOT NULL DEFAULT CURRENT_DATE, -- Issue date
                         due_date date, -- Due date (optional)
                         status text NOT NULL CHECK (status IN ('ISSUED', 'PARTIALLY_PAID', 'PAID', 'VOID')), -- Invoice status
                         currency VARCHAR(3) NOT NULL DEFAULT 'GTQ', -- Currency (default GTQ)
                         notes text -- Additional notes
);
CREATE INDEX ix_invoice_user ON invoice(user_id);
CREATE INDEX ix_invoice_date ON invoice(issue_date);
CREATE INDEX ix_invoice_status ON invoice(status);

--------------------------------------------------------

CREATE SEQUENCE invoice_seq START 1;

CREATE OR REPLACE FUNCTION generate_invoice_code()
RETURNS TRIGGER AS $$
BEGIN
    NEW.code := 'INT-' || EXTRACT(YEAR FROM CURRENT_DATE) || '-' ||
                LPAD(nextval('invoice_seq')::text, 4, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_generate_invoice_code
    BEFORE INSERT ON invoice
    FOR EACH ROW
    EXECUTE FUNCTION generate_invoice_code();

----------------------------------------------------------

-- =======================
-- INVOICE
-- =======================
INSERT INTO invoice (type, work_order_id, goods_receipt_id, user_id, status, currency, notes)
VALUES
    ( 'CUSTOMER', 1, NULL, 2, 'ISSUED', 'GTQ', 'Factura por servicios de reparación'),
    ( 'CUSTOMER', 2, NULL, 3, 'PARTIALLY_PAID', 'GTQ', 'Factura parcial por materiales'),
    ( 'CUSTOMER', 3, NULL, 4, 'PAID', 'USD', 'Factura completa cliente internacional'),
    ( 'SUPPLIER', NULL, 1, 5, 'ISSUED', 'GTQ', 'Factura de proveedor local'),
    ( 'SUPPLIER', NULL, 2, 5, 'PAID', 'USD', 'Factura proveedor internacional');

-- Payment methods
CREATE TABLE payment_method (
                                id BIGSERIAL PRIMARY KEY,
                                code text UNIQUE NOT NULL CHECK (code IN ('CASH', 'CARD', 'TRANSFER', 'CHECK', 'MIXED')), -- Payment method types
                                name text NOT NULL -- Method name
);
INSERT INTO payment_method (code, name) VALUES
                                            ('CASH', 'Efectivo'),
                                            ('CARD', 'Tarjeta de Crédito/Débito'),
                                            ('TRANSFER', 'Transferencia Bancaria'),
                                            ('CHECK', 'Cheque'),
                                            ('MIXED', 'Pago Mixto');


-- Payments made (for customer and supplier invoices)
CREATE TABLE payment (
                         id BIGSERIAL PRIMARY KEY,
                         invoice_id BIGINT NOT NULL REFERENCES invoice(id) ON DELETE CASCADE, -- Reference to invoice
                         method_id BIGINT NOT NULL REFERENCES payment_method(id) ON DELETE RESTRICT, -- Payment method
                         amount numeric(14,4) NOT NULL CHECK (amount > 0), -- Payment amount
                         paid_at timestamptz NOT NULL DEFAULT now(), -- Payment date
                         reference text -- Payment reference (optional)
);
CREATE INDEX ix_payment_invoice ON payment(invoice_id);

-- =======================
-- PAYMENT
-- =======================
INSERT INTO payment (invoice_id, method_id, amount, reference)
VALUES
    (1, 1, 500.00, 'Pago parcial en efectivo'),
    (2, 2, 300.00, 'Pago con tarjeta VISA'),
    (3, 3, 1000.00, 'Transferencia bancaria - Banco G&T'),
    (4, 1, 600.00, 'Pago en efectivo proveedor local'),
    (5, 4, 1200.00, 'Cheque proveedor internacional');


