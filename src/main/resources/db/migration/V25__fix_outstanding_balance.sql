-- V25: Fix outstanding_balance initialization and update trigger
-- Update existing invoices with null outstanding_balance
UPDATE invoice
SET outstanding_balance = total
WHERE outstanding_balance IS NULL AND status = 'ISSUED';

UPDATE invoice
SET outstanding_balance = 0
WHERE outstanding_balance IS NULL AND status = 'PAID';

-- Update the trigger to include outstanding_balance
CREATE OR REPLACE FUNCTION generate_invoice_on_quotation()
RETURNS TRIGGER AS $$
BEGIN
    -- Solo ejecuta si el estado se actualizó a 'APPROVED'
    IF NEW.status = 'APPROVED' AND (OLD.status IS DISTINCT FROM NEW.status) THEN
        INSERT INTO invoice (type, quotation_id, user_id, status, currency, notes, issue_date, outstanding_balance)
        VALUES (
            'CUSTOMER',
            NEW.id,                 -- referencia a la cotización
            NEW.customer_id,        -- cliente de la cotización
            'ISSUED',
            'GTQ',
            'Factura generada a partir de la cotización ' || NEW.code,
            NOW(),
            0                      -- Will be updated when total is calculated
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
