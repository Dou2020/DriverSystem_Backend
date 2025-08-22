-- Agregar columna status a vehicle_visit
ALTER TABLE vehicle_visit ADD COLUMN status VARCHAR(20) DEFAULT 'NUEVA';

-- Crear índice para mejorar rendimiento de consultas por status
CREATE INDEX idx_vehicle_visit_status ON vehicle_visit(status);
