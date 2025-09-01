TRUNCATE TABLE
    service_feedback,
    work_log,
    work_assignment,
    work_order,
    vehicle_visit,
    vehicle_user,
    supplier_product,
    purchase_order_item,
    purchase_order,
    product,
    product_category,
    work_status,
    maintenance_type,
    vehicle,
    vehicle_model,
    vehicle_make,
    password_reset,
    user_mfa,
    user_role,
    app_user,
    app_role,
    payment_method,
    quotation
RESTART IDENTITY CASCADE;

-- 1. Insertar roles del sistema (con IDs específicos)
INSERT INTO public.app_role (id, code, name) VALUES
                                                 (1, 'ADMIN', 'Administrador'),
                                                 (2, 'EMPLOYEE', 'Empleado'),
                                                 (3, 'SPECIALIST', 'Especialista'),
                                                 (4, 'CUSTOMER', 'Cliente'),
                                                 (5, 'SUPPLIER', 'Proveedor');

-- 2. Insertar usuarios del sistema (con IDs específicos)
INSERT INTO public.app_user (id, username, email, phone, password_hash, is_active, user_type, doc_type, doc_number, name, usa_mfa, availability) VALUES
                                                                                                                                                     (1, 'admin', 'admin@taller.com', '+50240000001', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '100000001', 'Admin Taller', false, true),
                                                                                                                                                     (2, 'maria.mecanica', 'maria.mecanica@taller.com', '+50240000002', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '100000002', 'María Pérez', false, true),
                                                                                                                                                     (3, 'carlos.mecanico', 'carlos.mecanico@taller.com', '+50240000003', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '100000003', 'Carlos López', false, true),
                                                                                                                                                     (4, 'ana.electrica', 'ana.electrica@taller.com', '+50240000004', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '100000004', 'Ana Martínez', false, true),
                                                                                                                                                     (5, 'jorge.inyeccion', 'jorge.inyeccion@taller.com', '+50240000005', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '100000005', 'Jorge Ramírez', false, true),
                                                                                                                                                     (6, 'luis.cliente', 'luis.cliente@example.com', '+50240000006', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '200000006', 'Luis Gómez', false, false),
                                                                                                                                                     (7, 'sofia.cliente', 'sofia.cliente@example.com', '+50240000007', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '200000007', 'Sofía Castillo', false, false),
                                                                                                                                                     (8, 'empresa.proveedora', 'compras@autopartes-gt.com', '+50240000008', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'ORGANIZATION', NULL, NULL, 'AutoPartes GT, S.A.', false, false),
                                                                                                                                                     (9, 'recepcion', 'recepcion@taller.com', '+50240000009', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '100000009', 'Pedro Recepcionista', false, true),
                                                                                                                                                     (10, 'mario.cliente', 'mario.cliente@example.com', '+50240000010', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '200000010', 'Mario Hernández', false, false),
                                                                                                                                                     (11, 'carmen.cliente', 'carmen.cliente@example.com', '+50240000011', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', true, 'PERSON', 'DPI', '200000011', 'Carmen Ruiz', false, false);

-- 3. Asignar roles a usuarios
INSERT INTO public.user_role (user_id, role_id) VALUES
                                                    (1, 1), -- admin
                                                    (2, 2), (3, 2), -- empleados
                                                    (4, 3), (5, 3), -- especialistas
                                                    (6, 4), (7, 4), (10, 4), (11, 4), -- clientes
                                                    (8, 5), -- proveedor
                                                    (9, 2); -- recepcionista

-- 4. Insertar tipos de mantenimiento (con IDs específicos)
INSERT INTO public.maintenance_type (id, code, name) VALUES
                                                         (1, 'CORRECTIVE', 'Correctivo'),
                                                         (2, 'PREVENTIVE', 'Preventivo');

-- 5. Insertar estados de trabajo
INSERT INTO public.work_status (code, name) VALUES
                                                ('CREATED', 'Creado'),
                                                ('ASSIGNED', 'Asignado'),
                                                ('IN_PROGRESS', 'En Progreso'),
                                                ('ON_HOLD', 'En Espera'),
                                                ('COMPLETED', 'Completado'),
                                                ('CANCELLED', 'Cancelado'),
                                                ('CLOSED', 'Cerrado'),
                                                ('NO_AUTHORIZED', 'No Autorizado');

-- 6. Insertar marcas de vehículos
INSERT INTO public.vehicle_make (name) VALUES
                                           ('Toyota'),
                                           ('Honda'),
                                           ('Ford'),
                                           ('Nissan'),
                                           ('Hyundai'),
                                           ('Kia'),
                                           ('Volkswagen'),
                                           ('Chevrolet'),
                                           ('Mazda'),
                                           ('Subaru');

-- 7. Insertar modelos de vehículos
INSERT INTO public.vehicle_model (make_id, name) VALUES
-- Toyota
(1, 'Corolla'),
(1, 'Camry'),
(1, 'RAV4'),
(1, 'Hilux'),
(1, 'Tacoma'),

-- Honda
(2, 'Civic'),
(2, 'Accord'),
(2, 'CR-V'),
(2, 'Pilot'),
(2, 'HR-V'),

-- Ford
(3, 'F-150'),
(3, 'Focus'),
(3, 'Escape'),
(3, 'Explorer'),
(3, 'Ranger');

-- 8. Insertar vehículos
INSERT INTO public.vehicle (vin, plate, make_id, model_id, model_year, color, created_at) VALUES
-- Vehículos de clientes
('1HGCM82633A123456', 'P123ABC', 2, 6, 2018, 'Rojo', NOW()),
('5XYZH4AG4JH123456', 'P456XYZ', 3, 11, 2020, 'Azul', NOW()),
('2T2BK1BA3BC123456', 'P789DEF', 1, 1, 2019, 'Blanco', NOW()),
('5FNRL5H97CB123456', 'P321GHI', 2, 8, 2021, 'Negro', NOW()),
('1GNSKAKC7FR123456', 'P654JKL', 3, 12, 2017, 'Gris', NOW()),
('JM1GJ1W56F1234567', 'P987MNO', 9, 15, 2015, 'Verde', NOW()),
('5NPE24AF9FH123456', 'P159PQR', 4, 3, 2020, 'Plateado', NOW());

-- 9. Asociar vehículos a usuarios (clientes)
INSERT INTO public.vehicle_user (user_id, vehicle_id) VALUES
                                                          (6, 1), -- Luis Gómez - Toyota Corolla Rojo
                                                          (7, 2), -- Sofía Castillo - Ford F-150 Azul
                                                          (10, 3), -- Mario Hernández - Honda Civic Blanco
                                                          (11, 4), -- Carmen Ruiz - Honda CR-V Negro
                                                          (6, 5), -- Luis Gómez - Ford Focus Gris
                                                          (7, 6), -- Sofía Castillo - Mazda Ranger Verde
                                                          (10, 7); -- Mario Hernández - Nissan Escape Plateado

-- 10. Insertar categorías de productos
INSERT INTO public.product_category (name) VALUES
                                               ('Aceites y Lubricantes'),
                                               ('Filtros'),
                                               ('Frenos'),
                                               ('Suspensión y Dirección'),
                                               ('Motor'),
                                               ('Transmisión'),
                                               ('Eléctrico'),
                                               ('Carrocería'),
                                               ('Herramientas'),
                                               ('Servicios');

-- 11. Insertar productos y servicios
INSERT INTO public.product (name, brand, category_id, unit, is_service, taxable, cost, price, active) VALUES
-- Productos (repuestos)
('Aceite Sintético 5W-30', 'Mobil', 1, 'L', false, true, 45.00, 75.00, true),
('Filtro de Aceite', 'Fram', 2, 'UN', false, true, 25.00, 45.00, true),
('Pastillas de Freno Delanteras', 'Bosch', 3, 'UN', false, true, 120.00, 200.00, true),
('Batería 12V 60AH', 'ACDelco', 7, 'UN', false, true, 350.00, 550.00, true),
('Aceite de Transmisión', 'Valvoline', 6, 'L', false, true, 40.00, 65.00, true),
('Amortiguadores Delanteros', 'Monroe', 4, 'UN', false, true, 280.00, 450.00, true),
('Bujías', 'NGK', 5, 'UN', false, true, 15.00, 30.00, true),
('Correa de Distribución', 'Gates', 5, 'UN', false, true, 85.00, 150.00, true),

-- Servicios
('Cambio de Aceite y Filtro', NULL, 10, 'SERV', true, true, NULL, 150.00, true),
('Revisión General', NULL, 10, 'SERV', true, true, NULL, 250.00, true),
('Reemplazo de Pastillas de Freno', NULL, 10, 'SERV', true, true, NULL, 300.00, true),
('Alineación y Balanceo', NULL, 10, 'SERV', true, true, NULL, 400.00, true),
('Diagnóstico Computarizado', NULL, 10, 'SERV', true, true, NULL, 200.00, true),
('Reemplazo de Batería', NULL, 10, 'SERV', true, true, NULL, 100.00, true),
('Reemplazo de Amortiguadores', NULL, 10, 'SERV', true, true, NULL, 600.00, true),
('Reemplazo de Correa de Distribución', NULL, 10, 'SERV', true, true, NULL, 800.00, true);

-- 12. Insertar ubicaciones de inventario
INSERT INTO public.location (code, name) VALUES
                                             ('BOD01', 'Bodega Principal'),
                                             ('BOD02', 'Bodega de Repuestos'),
                                             ('TALLER', 'Área de Taller'),
                                             ('RECEP', 'Recepción')
    ON CONFLICT (code)
DO UPDATE SET name = EXCLUDED.name;

-- 13. Insertar stock inicial
INSERT INTO public.stock (product_id, location_id, qty, min_qty) VALUES
                                                                     (1, 1, 50.000, 10.000),
                                                                     (2, 1, 30.000, 5.000),
                                                                     (3, 2, 20.000, 4.000),
                                                                     (4, 2, 10.000, 2.000),
                                                                     (5, 1, 25.000, 8.000),
                                                                     (6, 2, 8.000, 2.000),
                                                                     (7, 1, 50.000, 10.000),
                                                                     (8, 2, 12.000, 3.000);

-- 14. Insertar visitas de vehículos
INSERT INTO public.vehicle_visit (vehicle_id, customer_id, arrived_at, departed_at, notes, status) VALUES
                                                                                                       (1, 6, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days' + INTERVAL '3 hours', 'Vehículo con ruido en motor', 'COMPLETED'),
                                                                                                       (2, 7, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days' + INTERVAL '2 hours', 'Cambio de aceite programado', 'COMPLETED'),
                                                                                                       (3, 10, NOW() - INTERVAL '1 day', NULL, 'Frenos haciendo ruido', 'EN_TALLER'),
                                                                                                       (4, 11, NOW(), NULL, 'Revisión de rutina', 'NUEVA'),
                                                                                                       (5, 6, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days' + INTERVAL '4 hours', 'Problemas con la batería', 'COMPLETED'),
                                                                                                       (6, 7, NOW() - INTERVAL '4 days', NULL, 'Vehículo no enciende', 'EN_TALLER');

-- 15. Insertar órdenes de trabajo
INSERT INTO public.work_order (code, vehicle_id, customer_id, type_id, status_id, description, estimated_hours, opened_at, closed_at, created_by, visit_id) VALUES
                                                                                                                                                                ('WO-2023-001', 1, 6, 1, 5, 'Diagnóstico de ruido en motor', 2.5, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days', 9, 1),
                                                                                                                                                                ('WO-2023-002', 2, 7, 2, 5, 'Cambio de aceite y filtro', 1.0, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 9, 2),
                                                                                                                                                                ('WO-2023-003', 3, 10, 1, 3, 'Revisión y posible cambio de pastillas de freno', 3.0, NOW() - INTERVAL '1 day', NULL, 9, 3),
                                                                                                                                                                ('WO-2023-004', 4, 11, 2, 1, 'Revisión de rutina y mantenimiento preventivo', 1.5, NOW(), NULL, 9, 4),
                                                                                                                                                                ('WO-2023-005', 5, 6, 1, 6, 'Reemplazo de batería', 1.0, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 9, 5),
                                                                                                                                                                ('WO-2023-006', 6, 7, 1, 4, 'Diagnóstico de problema de encendido', 2.0, NOW() - INTERVAL '4 days', NULL, 9, 6);

-- 16. Insertar asignaciones de trabajo
INSERT INTO public.work_assignment (work_order_id, assignee_id, role, assigned_at, released_at) VALUES
                                                                                                    (1, 2, 2, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days'),
                                                                                                    (2, 3, 2, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days'),
                                                                                                    (3, 4, 3, NOW() - INTERVAL '1 day', NULL),
                                                                                                    (4, 5, 3, NOW(), NULL),
                                                                                                    (5, 2, 2, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'),
                                                                                                    (6, 4, 3, NOW() - INTERVAL '4 days', NULL);

-- 17. Insertar registros de trabajo
INSERT INTO public.work_log (work_order_id, author_id, log_type, note, hours, created_at) VALUES
                                                                                              (1, 2, 'DIAGNOSIS', 'Se identificó que el ruido proviene de la banda de tiempo. Se requiere ajuste.', 0.5, NOW() - INTERVAL '5 days' + INTERVAL '1 hour'),
                                                                                              (1, 2, 'PROGRESS', 'Banda de tiempo ajustada correctamente. Ruido eliminado.', 1.0, NOW() - INTERVAL '5 days' + INTERVAL '2 hours'),
                                                                                              (2, 3, 'PROGRESS', 'Aceite y filtro cambiados. Vehículo listo.', 1.0, NOW() - INTERVAL '3 days' + INTERVAL '1 hour'),
                                                                                              (3, 4, 'DIAGNOSIS', 'Pastillas de freno desgastadas al 90%. Se requiere reemplazo.', 0.5, NOW() - INTERVAL '1 day' + INTERVAL '1 hour'),
                                                                                              (3, 4, 'ISSUE', 'No hay pastillas en inventario. Se debe ordenar.', 0.2, NOW() - INTERVAL '1 day' + INTERVAL '2 hours'),
                                                                                              (5, 2, 'DIAGNOSIS', 'Batería completamente descargada y dañada. Necesita reemplazo.', 0.3, NOW() - INTERVAL '2 days' + INTERVAL '1 hour'),
                                                                                              (5, 2, 'PROGRESS', 'Batería reemplazada. Sistema eléctrico funcionando correctamente.', 0.7, NOW() - INTERVAL '2 days' + INTERVAL '2 hours'),
                                                                                              (6, 4, 'DIAGNOSIS', 'Problema con el sistema de encendido. Se requiere diagnóstico profundo.', 1.0, NOW() - INTERVAL '4 days' + INTERVAL '1 hour'),
                                                                                              (6, 4, 'NOTE', 'Esperando autorización del cliente para proceder con reparación.', 0.5, NOW() - INTERVAL '3 days');

-- 18. Insertar métodos de pago
INSERT INTO payment_method (code, name) VALUES
                                            ('CASH', 'Efectivo'),
                                            ('CARD', 'Tarjeta de Crédito/Débito'),
                                            ('TRANSFER', 'Transferencia Bancaria'),
                                            ('CHECK', 'Cheque'),
                                            ('MIXED', 'Pago Mixto');


-- 19. Insertar relación proveedor-producto
INSERT INTO public.supplier_product (supplier_id, product_id, stock_quantity, lead_time_days) VALUES
                                                                                                  (8, 1, 100, 2),
                                                                                                  (8, 2, 200, 1),
                                                                                                  (8, 3, 50, 3),
                                                                                                  (8, 4, 30, 5),
                                                                                                  (8, 5, 80, 2),
                                                                                                  (8, 6, 20, 4),
                                                                                                  (8, 7, 150, 1),
                                                                                                  (8, 8, 25, 3);

-- 20. Insertar órdenes de compra
INSERT INTO public.purchase_order (code, supplier_id, status, ordered_at, expected_at, currency, notes) VALUES
                                                                                                            ('PO-2023-001', 8, 'RECEIVED', NOW() - INTERVAL '10 days', NOW() - INTERVAL '5 days', 'GTQ', 'Pedido regular de inventario'),
                                                                                                            ('PO-2023-002', 8, 'SENT', NOW() - INTERVAL '3 days', NOW() + INTERVAL '2 days', 'GTQ', 'Urgente: para orden de trabajo WO-2023-003'),
                                                                                                            ('PO-2023-003', 8, 'DRAFT', NOW() - INTERVAL '1 day', NOW() + INTERVAL '5 days', 'GTQ', 'Repuestos para mantenimiento preventivo');

-- 21. Insertar items de órdenes de compra
INSERT INTO public.purchase_order_item (purchase_order_id, product_id, quantity, unit_cost, discount, tax_rate) VALUES
                                                                                                                    (1, 1, 20.000, 42.50, 0.00, 12.00),
                                                                                                                    (1, 2, 15.000, 23.00, 5.00, 12.00),
                                                                                                                    (2, 3, 2.000, 115.00, 0.00, 12.00),
                                                                                                                    (3, 6, 4.000, 260.00, 10.00, 12.00),
                                                                                                                    (3, 7, 20.000, 13.00, 0.00, 12.00);

-- 22. Insertar movimientos de inventario
INSERT INTO public.stock_movement (product_id, location_id, movement_type, quantity, unit_cost, reference_type, reference_id, occurred_at) VALUES
                                                                                                                                               (1, 1, 'IN', 20.000, 42.50, 'PO', 1, NOW() - INTERVAL '5 days'),
                                                                                                                                               (2, 1, 'IN', 15.000, 23.00, 'PO', 1, NOW() - INTERVAL '5 days'),
                                                                                                                                               (1, 1, 'OUT', 2.000, 45.00, 'WO', 1, NOW() - INTERVAL '4 days'),
                                                                                                                                               (2, 1, 'OUT', 1.000, 25.00, 'WO', 2, NOW() - INTERVAL '3 days'),
                                                                                                                                               (4, 2, 'OUT', 1.000, 350.00, 'WO', 5, NOW() - INTERVAL '2 days');

-- 23. Insertar cotizaciones
INSERT INTO public.quotation (code, work_order_id, status, created_at, approved_at, approved_by, customer_id) VALUES
                                                                                                                  ('COT-2023-001', 1, 'APPROVED', NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 6, 6),
                                                                                                                  ('COT-2023-002', 2, 'APPROVED', NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 7, 7),
                                                                                                                  ('COT-2023-003', 3, 'SENT', NOW() - INTERVAL '1 day', NULL, NULL, 10),
                                                                                                                  ('COT-2023-004', 5, 'APPROVED', NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 6, 6),
                                                                                                                  ('COT-2023-005', 6, 'REJECTED', NOW() - INTERVAL '3 days', NULL, NULL, 7);  -- Cambiado de 'NO_AUTHORIZED' a 'REJECTED'

-- 24. Insertar items de cotización (usando tabla item)
INSERT INTO public.item (parent_id, product_id, quantity) VALUES
-- Items para cotización 1
(1, 9, 1.000),  -- Servicio de diagnóstico
(1, 1, 5.000),  -- Aceite
(1, 2, 1.000),  -- Filtro

-- Items para cotización 2
(2, 9, 1.000),  -- Servicio de cambio de aceite

-- Items para cotización 3
(3, 11, 1.000), -- Servicio de frenos
(3, 3, 1.000),  -- Pastillas de freno

-- Items para cotización 4
(4, 14, 1.000), -- Servicio de reemplazo de batería
(4, 4, 1.000),  -- Batería

-- Items para cotización 5
(5, 13, 1.000); -- Servicio de diagnóstico computarizado

-- 25. Insertar facturas
INSERT INTO public.invoice (code, type, quotation_id, user_id, issue_date, due_date, status, currency, notes, total, outstanding_balance) VALUES
                                                                                                                                              ('FAC-2023-001', 'CUSTOMER', 1, 6, CURRENT_DATE - INTERVAL '4 days', CURRENT_DATE - INTERVAL '1 day', 'PAID', 'GTQ', 'Factura por servicios de diagnóstico', 512.50, 0.00),
                                                                                                                                              ('FAC-2023-002', 'CUSTOMER', 2, 7, CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE + INTERVAL '1 day', 'PARTIALLY_PAID', 'GTQ', 'Factura por cambio de aceite', 150.00, 75.00),
                                                                                                                                              ('FAC-2023-003', 'CUSTOMER', 4, 6, CURRENT_DATE - INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 days', 'ISSUED', 'GTQ', 'Factura por reemplazo de batería', 650.00, 650.00);

-- 26. Insertar pagos
INSERT INTO public.payment (invoice_id, method_id, amount, paid_at, reference) VALUES
                                                                                   (1, 2, 512.50, NOW() - INTERVAL '4 days', 'TRX-123456'),
                                                                                   (2, 1, 75.00, NOW() - INTERVAL '2 days', NULL);

-- 27. Insertar comentarios de feedback
INSERT INTO public.service_feedback (work_order_id, customer_id, rating, comment, created_at) VALUES
                                                                                                  (1, 6, 5, 'Excelente servicio, resolvieron el problema rápidamente', NOW() - INTERVAL '4 days'),
                                                                                                  (2, 7, 4, 'Buen servicio, pero un poco demorado', NOW() - INTERVAL '2 days'),
                                                                                                  (5, 6, 5, 'Muy profesionales, solucionaron el problema de mi batería en poco tiempo', NOW() - INTERVAL '1 day');