-- Agregar columna visit_id a work_order para enlazar con vehicle_visit
ALTER TABLE work_order ADD COLUMN visit_id BIGINT REFERENCES vehicle_visit(id);

-- Crear índice para mejorar rendimiento de consultas
CREATE INDEX idx_work_order_visit_id ON work_order(visit_id);
