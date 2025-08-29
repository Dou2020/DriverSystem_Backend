-- V26: Update generate_invoice_on_quotation to calculate and save total
CREATE OR REPLACE FUNCTION generate_invoice_on_quotation()
RETURNS TRIGGER AS $$
DECLARE
    calculated_total DECIMAL(10,2) := 0;
BEGIN
    -- Solo ejecuta si el estado se actualizó a 'APPROVED'
    IF NEW.status = 'APPROVED' AND (OLD.status IS DISTINCT FROM NEW.status) THEN
        -- Calcular el total sumando los subtotales de los items de la cotización
        SELECT COALESCE(SUM(subtotal), 0)
        INTO calculated_total
        FROM item_quotation
        WHERE quotation = NEW.id;

        INSERT INTO invoice (type, quotation_id, user_id, status, currency, notes, issue_date, outstanding_balance, total)
        VALUES (
            'CUSTOMER',
            NEW.id,                 -- referencia a la cotización
            NEW.customer_id,        -- cliente de la cotización
            'ISSUED',
            'GTQ',
            'Factura generada a partir de la cotización ' || NEW.code,
            NOW(),
            calculated_total,       -- outstanding_balance = total inicialmente
            calculated_total        -- total calculado de los items
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
