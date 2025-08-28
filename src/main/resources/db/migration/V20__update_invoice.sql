-- 1. Buscar y eliminar la constraint antigua
DO $$
DECLARE
constraint_name text;
BEGIN
    -- Obtener el nombre de la FK que apunta a work_order
SELECT conname
INTO constraint_name
FROM pg_constraint
WHERE conrelid = 'invoice'::regclass
      AND confrelid = 'work_order'::regclass;

-- Si existe, la eliminamos
IF constraint_name IS NOT NULL THEN
        EXECUTE format('ALTER TABLE invoice DROP CONSTRAINT %I', constraint_name);
END IF;
END
$$;

-- 2. Renombrar la columna
ALTER TABLE invoice
    RENAME COLUMN work_order_id TO quotation_id;

-- 3. Crear la nueva foreign key apuntando a quotation(id)
ALTER TABLE invoice
    ADD CONSTRAINT invoice_quotation_id_fkey
        FOREIGN KEY (quotation_id) REFERENCES quotation(id) ON DELETE SET NULL;



-- generar una factura cuando la cotizacion se acepto
CREATE OR REPLACE FUNCTION generate_invoice_on_quotation()
RETURNS TRIGGER AS $$
BEGIN
    -- Solo ejecuta si el estado se actualizó a 'APPROVED'
    IF NEW.status = 'APPROVED' AND (OLD.status IS DISTINCT FROM NEW.status) THEN
        INSERT INTO invoice (type, quotation_id, user_id, status, currency, notes,issue_date)
        VALUES (
            'CUSTOMER',
            NEW.id,                 -- referencia a la cotización
            NEW.customer_id,        -- cliente de la cotización
            'ISSUED',
            'GTQ',
            'Factura generada a partir de la cotización ' || NEW.code,
            NOW()
        );
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_generate_invoice
    AFTER UPDATE ON quotation
    FOR EACH ROW
    WHEN (OLD.status IS DISTINCT FROM NEW.status)  -- Solo si hay cambio
EXECUTE FUNCTION generate_invoice_on_quotation();
