ALTER TABLE quotation
    ADD COLUMN customer_id BIGINT REFERENCES app_user(id) ON DELETE RESTRICT;

CREATE OR REPLACE FUNCTION set_customer_id_from_work_order()
RETURNS TRIGGER AS $$
BEGIN
    -- Buscar el customer_id de la work_order asociada
SELECT customer_id INTO NEW.customer_id
FROM work_order
WHERE id = NEW.work_order_id;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_customer_id
    BEFORE INSERT ON quotation
    FOR EACH ROW
    WHEN (NEW.customer_id IS NULL)  -- Solo si no se envía
    EXECUTE FUNCTION set_customer_id_from_work_order();
