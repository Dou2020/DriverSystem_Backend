INSERT INTO product_category (name) VALUES
('Fenos'),
('Chasis'),
('Servicios Mantenimiento');

INSERT INTO product (name, brand, category_id, unit, is_service, taxable, cost, price, active) VALUES
('Aceite de Motor', 'Marca A', 1, 'LITRO', false, true, 10.00, 15.00, true),
('Filtro de Aceite', 'Marca B', 1, 'UNIDAD', false, true, 5.00, 8.00, true),
('Bujías', 'Marca C', 1, 'UNIDAD', false, true, 2.50, 4.00, true),
('Líquido de Frenos', 'Marca D', 1, 'LITRO', false, true, 3.00, 5.00, true),
('Limpiaparabrisas', 'Marca E', 2, 'LITRO', false, true, 1.50, 3.00, true),
('Kit de Herramientas', 'Marca F', 2, 'SET', false, true, 20.00, 30.00, true),
('Lavado de Carro Completo', NULL, NULL, 'SERVICIO', true, true, NULL, 25.00, true);