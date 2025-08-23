CREATE TABLE purchase_order (
  id BIGSERIAL PRIMARY KEY,
  code text UNIQUE NOT NULL,           -- PO-YYYY-####
  supplier_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
  status text NOT NULL CHECK (status IN ('DRAFT','SENT','PARTIALLY_RECEIVED','RECEIVED','CANCELLED')),
  ordered_at timestamptz NOT NULL DEFAULT now(),
  expected_at date,
  currency char(3) NOT NULL DEFAULT 'GTQ',
  notes text
);
CREATE INDEX ix_po_supplier ON purchase_order(supplier_id);
CREATE INDEX ix_po_status   ON purchase_order(status);

CREATE TABLE purchase_order_item (
  id BIGSERIAL PRIMARY KEY,
  purchase_order_id BIGINT NOT NULL REFERENCES purchase_order(id) ON DELETE CASCADE,
  product_id BIGINT NOT NULL REFERENCES product(id) ON DELETE RESTRICT,
  quantity  numeric(14,3) NOT NULL CHECK (quantity > 0),
  unit_cost numeric(14,4) NOT NULL CHECK (unit_cost >= 0),
  discount  numeric(14,4) NOT NULL DEFAULT 0,
  tax_rate  numeric(5,2) NOT NULL DEFAULT 12.00,
  UNIQUE (purchase_order_id, product_id)
);

-- Inserts para purchase_order
INSERT INTO purchase_order (id, code, supplier_id, status, ordered_at, expected_at, currency, notes)
VALUES (1, 'PO-2024-0001', 2, 'DRAFT', '2024-06-10 10:00:00+00', '2024-06-20', 'GTQ', 'Primera orden de ejemplo');

INSERT INTO purchase_order (id, code, supplier_id, status, ordered_at, expected_at, currency, notes)
VALUES (2, 'PO-2024-0002', 3, 'SENT', '2024-06-11 11:00:00+00', '2024-06-22', 'GTQ', 'Segunda orden de ejemplo');

-- Inserts para purchase_order_item
INSERT INTO purchase_order_item (id, purchase_order_id, product_id, quantity, unit_cost, discount, tax_rate)
VALUES (1, 1, 5, 10.000, 50.0000, 0, 12.00);

INSERT INTO purchase_order_item (id, purchase_order_id, product_id, quantity, unit_cost, discount, tax_rate)
VALUES (2, 1, 6, 5.000, 100.0000, 10.0000, 12.00);

INSERT INTO purchase_order_item (id, purchase_order_id, product_id, quantity, unit_cost, discount, tax_rate)
VALUES (3, 2, 7, 2.000, 200.0000, 0, 12.00);

