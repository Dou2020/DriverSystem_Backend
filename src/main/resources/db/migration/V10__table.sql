-- proveedor ↔ producto
CREATE TABLE supplier_product (
  supplier_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
  product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
  stock_quantity INT CHECK (stock_quantity >= 0),
  lead_time_days INT CHECK (lead_time_days >= 0),
  PRIMARY KEY (supplier_id, product_id)
);