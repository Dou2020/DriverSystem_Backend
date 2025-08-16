INSERT INTO app_role (code, name) VALUES
                                      ('ADMIN', 'Administrador del sistema'),
                                      ('EMPLOYEE', 'Empleado interno'),
                                      ('SPECIALIST', 'Especialista externo'),
                                      ('CUSTOMER', 'Cliente'),
                                      ('SUPPLIER', 'Proveedor');

-- Insert app_user
INSERT INTO app_user (username, email, phone, password_hash, user_type, doc_type, doc_number, name)
VALUES ('dou', 'doueduar12@gmail.com', '+50212345678', '$2a$10$jGKVeArX.GLopAN3BIE9LeH4DgyGmQJBOivLWlMXJVmAJaDcDEfA2', 'PERSON', 'DPI', '1234567890101', 'Eduardo Gomez');

