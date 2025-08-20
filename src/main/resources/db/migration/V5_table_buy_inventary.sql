-- ============================
-- Inventario / Compras
-- ============================

CREATE TABLE product_category (
  id BIGSERIAL PRIMARY KEY,
  name text NOT NULL
);

INSERT INTO product_category (name) VALUES
('Motor y Transmisión'),
('Frenos'),
('Suspensión y Dirección'),
('Sistema Eléctrico y Luces'),
('Carrocería y Exterior'),
('Interior y Accesorios'),
('Refrigeración y Climatización'),
('Escape'),
('Llantas y Rines'),
('Filtración y Combustible');

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

-- Asumiendo que las categorías insertadas previamente tienen los siguientes IDs:
-- 1: Motor y Transmisión
-- 2: Frenos
-- 3: Suspensión y Dirección
-- 4: Sistema Eléctrico y Luces
-- 5: Carrocería y Exterior
-- 6: Interior y Accesorios
-- 7: Refrigeración y Climatización
-- 8: Escape
-- 9: Llantas y Rines
-- 10: Filtración y Combustible

INSERT INTO product (name, brand, category_id, unit, is_service, taxable, cost, price, active) VALUES
('Bujía de Platino CR7E', 'NGK', 1, 'UN', false, true, 5.5000, 12.9900, true),
('Disco de Freno Delantero', 'Brembo', 2, 'UN', false, true, 25.0000, 59.9900, true),
('Amortiguador Delantero Izq.', 'KYB', 3, 'UN', false, true, 40.0000, 95.0000, true),
('Batería 12V 75Ah', 'Optima', 4, 'UN', false, true, 80.0000, 180.0000, true),
('Espejo Retrovisor Derecho', 'Depo', 5, 'UN', false, true, 15.0000, 45.0000, true),
('Servicio Cambio de Aceite', NULL, NULL, 'SERVICIO', true, true, 20.0000, 50.0000, true),
('Filtro de Aire Motor', 'Mann-Filter', 10, 'UN', false, true, 8.0000, 19.9900, true),
('Pastillas de Freno Cerámicas Traseras', 'Akebono', 2, 'UN', false, true, 30.0000, 75.0000, true),
('Llanta Todoterreno 265/70R17', 'BFGoodrich', 9, 'UN', false, true, 120.0000, 250.0000, true),
('Balanceo de Llantas', NULL, NULL, 'SERVICIO', true, false, 10.0000, 25.0000, true),
('Aceite de Motor Sintético 5W30 (1L)', 'Castrol', 1, 'LT', false, true, 7.0000, 15.0000, true),
('Faro Delantero Izquierdo', 'TYC', 4, 'UN', false, true, 35.0000, 80.0000, true),
('Pulido de Faros', NULL, NULL, 'SERVICIO', true, true, 10.0000, 30.0000, true),
('Bomba de Agua para VW Golf', 'Bosch', 7, 'UN', false, true, 45.0000, 110.0000, true),
('Correa de Accesorios', 'Gates', 1, 'UN', false, true, 12.0000, 28.0000, true);