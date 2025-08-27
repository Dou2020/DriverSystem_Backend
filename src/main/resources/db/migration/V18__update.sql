BEGIN;

-- 1) Limpiar datos (excepto flyway_schema_history)
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
    app_role
RESTART IDENTITY CASCADE;

-- 2) Catálogos básicos
-- Roles
INSERT INTO app_role (id, code, name) VALUES
  (1,'ADMIN','Administrador'),
  (2,'EMPLOYEE','Empleado'),
  (3,'SPECIALIST','Especialista'),
  (4,'CUSTOMER','Cliente'),
  (5,'SUPPLIER','Proveedor');

-- Tipos de mantenimiento
INSERT INTO maintenance_type VALUES (1,'CORRECTIVE', 'Corrective'),
                                   (2, 'PREVENTIVE', 'Preventive');

-- Estados de trabajo
INSERT INTO work_status (code, name) VALUES
                                         ('CREATED', 'Created'),
                                         ('ASSIGNED', 'Assigned'),
                                         ('IN_PROGRESS', 'In Progress'),
                                         ('ON_HOLD', 'On Hold'),
                                         ('COMPLETED', 'Completed'),
                                         ('CANCELLED', 'Cancelled'),
                                         ('CLOSED', 'Closed'),
                                         ('NO_AUTHORIZED', 'No Authorized');

-- 3) Usuarios
INSERT INTO app_user
(id, username, email, phone, password_hash, is_active, user_type, doc_type, doc_number, name, usa_mfa, availability)
VALUES
  (1,'admin','admin@taller.com','+50240000001','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','100000001','Admin Taller',false,true),
  (2,'maria.mecanica','maria.mecanica@taller.com','+50240000002','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','100000002','María Pérez',false,true),
  (3,'carlos.mecanico','carlos.mecanico@taller.com','+50240000003','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','100000003','Carlos López',false,true),
  (4,'ana.electrica','ana.electrica@taller.com','+50240000004','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','100000004','Ana Martínez',false,true),
  (5,'jorge.inyeccion','jorge.inyeccion@taller.com','+50240000005','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','100000005','Jorge Ramírez',false,true),
  (6,'luis.cliente','luis.cliente@example.com','+50240000006','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','200000006','Luis Gómez',false,false),
  (7,'sofia.cliente','sofia.cliente@example.com','+50240000007','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'PERSON','DPI','200000007','Sofía Castillo',false,false),
  (8,'empresa.proveedora','compras@autopartes-gt.com','+50240000008','$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2',true,'ORGANIZATION',NULL,NULL,'AutoPartes GT, S.A.',false,false);

-- Usuario-Rol
INSERT INTO user_role (user_id, role_id) VALUES
  (1,1), -- admin
  (2,2), (3,2), -- empleados
  (4,3), (5,3), -- especialistas
  (6,4), (7,4), -- clientes
  (8,5);        -- proveedor

-- 4) Vehículos
-- Marcas
INSERT INTO vehicle_make (id, name) VALUES
  (1,'Toyota'), (2,'Honda'), (3,'Ford');

-- Modelos
INSERT INTO vehicle_model (id, make_id, name) VALUES
  (1,1,'Corolla'),
  (2,1,'Hilux'),
  (3,2,'Civic'),
  (4,3,'Ranger');

-- 5) Sincronizar secuencias para evitar conflictos de ID
SELECT setval('app_role_id_seq', (SELECT MAX(id) FROM app_role));
SELECT setval('app_user_id_seq', (SELECT MAX(id) FROM app_user));
SELECT setval('vehicle_make_id_seq', (SELECT MAX(id) FROM vehicle_make));
SELECT setval('vehicle_model_id_seq', (SELECT MAX(id) FROM vehicle_model));
SELECT setval('vehicle_id_seq', (SELECT MAX(id) FROM vehicle));
SELECT setval('vehicle_user_id_seq', (SELECT MAX(id) FROM vehicle_user));
SELECT setval('maintenance_type_id_seq', (SELECT MAX(id) FROM maintenance_type));
SELECT setval('work_status_id_seq', (SELECT MAX(id) FROM work_status));

COMMIT;
