CREATE TABLE location (
  id BIGSERIAL PRIMARY KEY,
  code text UNIQUE NOT NULL,           -- ALM-01, WORKSHOP, SHOWROOM
  name text NOT NULL
);

-- existencias por ubicación
CREATE TABLE stock (
  product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
  location_id BIGINT NOT NULL REFERENCES location(id) ON DELETE CASCADE,
  qty numeric(14,3) NOT NULL DEFAULT 0,
  min_qty numeric(14,3) NOT NULL DEFAULT 0,
  PRIMARY KEY (product_id, location_id)
);

-- kardex
CREATE TABLE stock_movement (
  id BIGSERIAL PRIMARY KEY,
  product_id  BIGINT NOT NULL REFERENCES product(id) ON DELETE RESTRICT,
  location_id BIGINT NOT NULL REFERENCES location(id) ON DELETE RESTRICT,
  movement_type text NOT NULL CHECK (movement_type IN ('IN','OUT','ADJUST')),
  quantity  numeric(14,3) NOT NULL CHECK (quantity > 0),
  unit_cost numeric(14,4) CHECK (unit_cost >= 0),
  reference_type text CHECK (reference_type IN ('PO','GR','WO','INV','ADJ')),
  reference_id BIGINT,
  occurred_at timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX ix_move_prod_time ON stock_movement(product_id, occurred_at DESC);

-- Insertar ubicaciones
INSERT INTO location (code, name) VALUES
  ('ALM-01', 'Almacén Principal'),
  ('WORKSHOP', 'Taller'),
  ('SHOWROOM', 'Sala de Exhibición');

-- Insertar existencias (stock) por ubicación
-- Suponiendo que existen productos con id 1 y 2
INSERT INTO stock (product_id, location_id, qty, min_qty) VALUES
    (1, 1, 100, 10),  -- Producto 1 en Almacén Principal
    (1, 2, 20, 5),   -- Producto 1 en Taller
    (2, 1, 50, 5),   -- Producto 2 en Almacén Principal
    (2, 3, 15, 3);   -- Producto 2 en Sala de Exhibición

-- Insertar movimientos de stock (kardex)
-- Suponiendo que existen productos y ubicaciones con los ids correspondientes
INSERT INTO stock_movement (product_id, location_id, movement_type, quantity, unit_cost, reference_type, reference_id)
VALUES
  (1, 1, 'IN', 100, 10.50, 'PO', 101),
  (2, 2, 'IN', 50, 20.00, 'GR', 202),
  (1, 1, 'OUT', 10, 10.50, 'WO', 303);