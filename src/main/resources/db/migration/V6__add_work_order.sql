-- ============================
-- Órdenes de trabajo y seguimiento
-- ============================
CREATE TABLE maintenance_type (
                                  id BIGSERIAL PRIMARY KEY,
                                  code text UNIQUE NOT NULL,           -- CORRECTIVE, PREVENTIVE
                                  name text NOT NULL
);

INSERT INTO  maintenance_type VALUES (1,'CORRECTIVE', 'Corrective'),
                                     (2, 'PREVENTIVE', 'Preventive');

CREATE TABLE work_status (
                             id BIGSERIAL PRIMARY KEY,
                             code text UNIQUE NOT NULL,           -- CREATED, ASSIGNED, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED, CLOSED, NO_AUTHORIZED
                             name text NOT NULL
);
INSERT INTO work_status (code, name) VALUES
                                         ('CREATED', 'Created'),
                                         ('ASSIGNED', 'Assigned'),
                                         ('IN_PROGRESS', 'In Progress'),
                                         ('ON_HOLD', 'On Hold'),
                                         ('COMPLETED', 'Completed'),
                                         ('CANCELLED', 'Cancelled'),
                                         ('CLOSED', 'Closed'),
                                         ('NO_AUTHORIZED', 'No Authorized');



CREATE TABLE work_order (
                            id BIGSERIAL PRIMARY KEY,
                            code text UNIQUE NOT NULL,           -- WO-YYYY-####
                            vehicle_id  BIGINT NOT NULL REFERENCES vehicle(id) ON DELETE RESTRICT,
                            customer_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
                            type_id   BIGINT NOT NULL REFERENCES maintenance_type(id) ON DELETE RESTRICT,
                            status_id BIGINT NOT NULL REFERENCES work_status(id) ON DELETE RESTRICT,
                            description text,
                            estimated_hours numeric(6,2) CHECK (estimated_hours >= 0),
                            opened_at timestamptz NOT NULL DEFAULT now(),
                            closed_at timestamptz,
                            created_by BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT
);

--------------------------------------------
CREATE SEQUENCE work_order_seq START 1;

CREATE OR REPLACE FUNCTION generate_work_order_code()
RETURNS TRIGGER AS $$
BEGIN
    NEW.code := 'WO-' || EXTRACT(YEAR FROM CURRENT_DATE) || '-' ||
                LPAD(nextval('work_order_seq')::text, 4, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_generate_work_order_code
    BEFORE INSERT ON work_order
    FOR EACH ROW
    EXECUTE FUNCTION generate_work_order_code();

--------------------------------------------------

INSERT INTO work_order (vehicle_id, customer_id, type_id, status_id, description, estimated_hours, opened_at, closed_at, created_by)
VALUES
    ( 1, 1, 1, 1, 'Cambio de aceite y revisión general', 2.50, now(), NULL, 1),
    ( 2, 2, 2, 2, 'Revisión de frenos y pastillas', 3.00, now(), NULL, 2),
    ( 3, 3, 1, 3, 'Alineación y balanceo de ruedas', 1.75, now(), NULL, 1);



CREATE INDEX ix_work_order_status  ON work_order(status_id);
CREATE INDEX ix_work_order_vehicle ON work_order(vehicle_id);
CREATE INDEX ix_work_order_opened  ON work_order(opened_at);


-- Asignaciones (empleado / especialista)
CREATE TABLE work_assignment (
                                 id BIGSERIAL PRIMARY KEY,
                                 work_order_id BIGINT NOT NULL REFERENCES work_order(id) ON DELETE CASCADE,
                                 assignee_id  BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
                                 role BIGINT NOT NULL REFERENCES  app_role(id) ON DELETE  RESTRICT ,
                                 assigned_at timestamptz NOT NULL DEFAULT now(),
                                 released_at timestamptz
);
INSERT INTO work_assignment (work_order_id, assignee_id, role, assigned_at, released_at)
VALUES
    (1, 3, 2, now(), NULL),
    (1, 4, 3, now(), NULL),
    (2, 5, 2, now(), NULL),
    (3, 3, 3, now(), NULL);


CREATE INDEX ix_assignment_work ON work_assignment(work_order_id, assigned_at DESC);

-- Bitácora / avance (línea de tiempo operativa)
CREATE TABLE work_log (
                          id BIGSERIAL PRIMARY KEY,
                          work_order_id BIGINT NOT NULL REFERENCES work_order(id) ON DELETE CASCADE,
                          author_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
                          log_type text NOT NULL CHECK (log_type IN ('NOTE','DIAGNOSIS','PROGRESS','ISSUE','CUSTOMER_NOTE')),
                          note text NOT NULL,
                          hours numeric(6,2) CHECK (hours >= 0),               -- tiempo real invertido
                          created_at timestamptz NOT NULL DEFAULT now()
);

INSERT INTO work_log (work_order_id, author_id, log_type, note, hours, created_at)
VALUES
    (1, 3, 'NOTE', 'Se revisó el motor y todo está en buen estado', 1.50, now()),
    (1, 4, 'DIAGNOSIS', 'Se detectó desgaste en las pastillas de freno', 0.75, now()),
    (2, 5, 'PROGRESS', 'Se avanzó con la alineación de las ruedas', 1.25, now()),
    (3, 3, 'ISSUE', 'Cliente reporta ruido en el sistema de frenos', 0.50, now()),
    (3, 4, 'CUSTOMER_NOTE', 'Cliente solicita revisión adicional del sistema eléctrico', NULL, now());

CREATE INDEX ix_work_log_work ON work_log(work_order_id, created_at DESC);

-- Opinión del cliente (calificación del servicio) pendiente
CREATE TABLE service_feedback (
                                  id BIGSERIAL PRIMARY KEY,
                                  work_order_id BIGINT NOT NULL REFERENCES work_order(id) ON DELETE CASCADE,
                                  customer_id  BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
                                  rating smallint NOT NULL CHECK (rating BETWEEN 1 AND 5),
                                  comment text,
                                  created_at timestamptz NOT NULL DEFAULT now(),
                                  UNIQUE (work_order_id, customer_id)  -- una calificación por OT
);
INSERT INTO service_feedback (work_order_id, customer_id, rating, comment, created_at)
VALUES
    (1, 1, 5, 'Excelente servicio, todo quedó perfecto', now()),
    (2, 2, 4, 'Buen servicio, aunque tardaron un poco más de lo esperado', now()),
    (3, 3, 3, 'Servicio adecuado, pero podrían mejorar la comunicación', now());
