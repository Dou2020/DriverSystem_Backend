CREATE OR REPLACE VIEW view_payment AS
SELECT
    p.id,
    p.invoice_id,
    p.amount,
    p.paid_at,
    p.reference,
    pm.name AS payment_method,
    i.user_id
FROM payment p
         INNER JOIN payment_method pm ON pm.id = p.method_id
         INNER JOIN invoice i ON i.id = p.invoice_id;
