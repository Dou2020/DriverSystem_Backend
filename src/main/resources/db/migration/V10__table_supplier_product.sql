-- proveedor ↔ producto
CREATE TABLE supplier_product (
  supplier_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
  product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
  stock_quantity INT CHECK (stock_quantity >= 0),
  lead_time_days INT CHECK (lead_time_days >= 0),
  PRIMARY KEY (supplier_id, product_id)
);

INSERT INTO supplier_product (supplier_id, product_id, stock_quantity, lead_time_days)
VALUES
  (5, 1, 15, 2),  -- 15 alternadores en stock, entrega en 2 días
  (5, 2, 5, 5);   -- sin stock de frenos, pero puede entregar en 5 días

