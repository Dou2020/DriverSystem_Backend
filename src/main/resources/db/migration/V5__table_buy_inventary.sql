-- ============================
-- Inventario / Compras
-- ============================

CREATE TABLE product_category (
  id BIGSERIAL PRIMARY KEY,
  name text NOT NULL
);

CREATE TABLE product (
  id BIGSERIAL PRIMARY KEY,
  name text NOT NULL,
  brand text,
  category_id BIGINT REFERENCES product_category(id) ON DELETE SET NULL,
  unit text NOT NULL DEFAULT 'UN',
  is_service boolean NOT NULL DEFAULT false,
  taxable boolean NOT NULL DEFAULT true,
  cost numeric(14,4) CHECK (cost >= 0),
  price numeric(14,4) CHECK (price >= 0),
  active boolean NOT NULL DEFAULT true
);
CREATE INDEX ix_product_category ON product(category_id);
