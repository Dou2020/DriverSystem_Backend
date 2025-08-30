-- ============================
-- Usuarios
-- ============================
INSERT INTO app_user (username, email, phone, password_hash, user_type, doc_type, doc_number, name)
VALUES
    ('admin1', 'admin@taller.com', '50211111111', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', 'PERSON', 'DPI', '1234567890181', 'Gerente General'),
    ('emp1', 'empleado1@taller.com', '50222222222', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', 'PERSON', 'DPI', '2234567890101', 'Carlos Pérez'),
    ('spec1', 'especialista1@taller.com', '50233333333', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', 'PERSON', 'DPI', '3234567890101', 'Luis García'),
    ('cliente1', 'cliente1@mail.com', '50244444444', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', 'PERSON', 'DPI', '4234567890101', 'Ana López'),
    ('proveedor1', 'proveedor1@mail.com', '50255555555', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', 'ORGANIZATION', 'NIT', '1234567-8', 'Repuestos S.A.');

-- ============================
-- Asignación de roles
-- ============================
INSERT INTO user_role (user_id, role_id) VALUES
                                             (1, 1),  -- admin1 → ADMIN
                                             (2, 2),  -- emp1 → EMPLOYEE
                                             (3, 3),  -- spec1 → SPECIALIST
                                             (4, 4),  -- cliente1 → CUSTOMER
                                             (5, 5);  -- proveedor1 → SUPPLIER

-- ============================
-- Marcas de vehículos
-- ============================
INSERT INTO vehicle_make (name) VALUES
                                    ('Toyota'),
                                    ('Honda'),
                                    ('Nissan');

-- ============================
-- Modelos de vehículos
-- ============================
INSERT INTO vehicle_model (make_id, name) VALUES
                                              (1, 'Corolla'),
                                              (1, 'Hilux'),
                                                  (2, 'Civic'),
                                              (2, 'CR-V'),
                                              (3, 'Sentra'),
                                              (3, 'Navara');

-- ============================
-- Vehículos
-- ============================
INSERT INTO vehicle (vin, plate, make_id, model_id, model_year, color)
VALUES
    ('1HGCM82633A123456', 'P123FRT', 1, 1, 2018, 'Blanco'),
    ('2HGCM82633A654321', 'Q456TYU', 1, 2, 2020, 'Negro'),
    ('3N1AB7AP4HY123789', 'R789WER', 2, 3, 2019, 'Rojo'),
    ('4T1BF1FK5HU987654', 'S321DFG', 2, 4, 2021, 'Gris'),
    ('JN8AS5MT9CW456789', 'T654GHJ', 3, 5, 2017, 'Azul');

-- ============================
-- Visitas al taller
-- ============================
INSERT INTO vehicle_visit (vehicle_id, customer_id, arrived_at, notes)
VALUES
    (1, 4, now() - interval '10 days', 'Cambio de aceite y revisión general'),
    (2, 4, now() - interval '5 days', 'Reparación de frenos delanteros'),
    (3, 4, now() - interval '2 days', 'Cambio de llantas y balanceo'),
    (4, 4, now() - interval '1 days', 'Chequeo del sistema eléctrico'),
    (5, 4, now(), 'Revisión de transmisión');
