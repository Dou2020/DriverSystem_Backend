CREATE  OR REPLACE  VIEW view_income_report AS
SELECT
    i.id,
    i.code AS invoice_code,
    i.issue_date,
    i.due_date,
    i.status,
    i.currency,
    i.total,
    i.outstanding_balance,
    COALESCE(SUM(p.amount), 0) AS paid_amount,
    (i.total - COALESCE(SUM(p.amount), 0)) AS pending_amount,
    c.name AS customer_name,
    c.doc_number AS customer_doc,
    q.code AS quotation_code,
    wo.code AS work_order_code
FROM invoice i
         JOIN app_user c ON i.user_id = c.id
         LEFT JOIN payment p ON i.id = p.invoice_id
         LEFT JOIN quotation q ON i.quotation_id = q.id
         LEFT JOIN work_order wo ON q.work_order_id = wo.id
WHERE i.type = 'CUSTOMER'
GROUP BY i.id, i.code, i.issue_date, i.due_date, i.status, i.currency,
         i.total, i.outstanding_balance, c.name, c.doc_number, q.code, wo.code;


CREATE  OR REPLACE  VIEW view_expense_report AS
SELECT
    i.id,
    i.code AS invoice_code,
    i.issue_date,
    i.due_date,
    i.status,
    i.currency,
    i.total,
    i.outstanding_balance,
    COALESCE(SUM(p.amount), 0) AS paid_amount,
    (i.total - COALESCE(SUM(p.amount), 0)) AS pending_amount,
    s.name AS supplier_name,
    s.doc_number AS supplier_doc,
    gr.id AS goods_receipt_id,
    po.code AS purchase_order_code
FROM invoice i
         JOIN app_user s ON i.user_id = s.id
         LEFT JOIN payment p ON i.id = p.invoice_id
         LEFT JOIN goods_receipt gr ON i.goods_receipt_id = gr.id
         LEFT JOIN purchase_order po ON gr.purchase_order_id = po.id
WHERE i.type = 'SUPPLIER'
GROUP BY i.id, i.code, i.issue_date, i.due_date, i.status, i.currency,
         i.total, i.outstanding_balance, s.name, s.doc_number, gr.id, po.code;



CREATE OR REPLACE FUNCTION get_financial_summary_by_period(
    start_date DATE,
    end_date DATE
)
RETURNS TABLE (
    period_type TEXT,
    total_income NUMERIC(14,4),
    total_expenses NUMERIC(14,4),
    net_balance NUMERIC(14,4)
) AS $$
BEGIN
RETURN QUERY
SELECT
    'Ingresos' AS period_type,
    COALESCE(SUM(i.total), 0) AS total_income,
    0 AS total_expenses,
    COALESCE(SUM(i.total), 0) AS net_balance
FROM invoice i
WHERE i.type = 'CUSTOMER'
  AND i.issue_date BETWEEN start_date AND end_date
  AND i.status != 'VOID'

UNION ALL

SELECT
    'Egresos' AS period_type,
    0 AS total_income,
    COALESCE(SUM(i.total), 0) AS total_expenses,
    -COALESCE(SUM(i.total), 0) AS net_balance
FROM invoice i
WHERE i.type = 'SUPPLIER'
  AND i.issue_date BETWEEN start_date AND end_date
  AND i.status != 'VOID'

UNION ALL

SELECT
    'Balance Neto' AS period_type,
    COALESCE(SUM(CASE WHEN i.type = 'CUSTOMER' THEN i.total ELSE 0 END), 0) AS total_income,
    COALESCE(SUM(CASE WHEN i.type = 'SUPPLIER' THEN i.total ELSE 0 END), 0) AS total_expenses,
    COALESCE(SUM(CASE WHEN i.type = 'CUSTOMER' THEN i.total ELSE -i.total END), 0) AS net_balance
FROM invoice i
WHERE i.issue_date BETWEEN start_date AND end_date
  AND i.status != 'VOID';
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_supplier_expenses_detail(
    start_date DATE,
    end_date DATE,
    in_supplier_id BIGINT DEFAULT NULL
)
RETURNS TABLE (
    supplier_name TEXT,
    supplier_doc TEXT,
    invoice_code TEXT,
    issue_date DATE,
    due_date DATE,
    status TEXT,
    total_amount NUMERIC(14,4),
    paid_amount NUMERIC(14,4),
    pending_amount NUMERIC(14,4),
    purchase_order_code TEXT,
    goods_receipt_id BIGINT
) AS $$
BEGIN
RETURN QUERY
SELECT
    s.name AS supplier_name,
    s.doc_number AS supplier_doc,
    i.code AS invoice_code,
    i.issue_date,
    i.due_date,
    i.status,
    i.total AS total_amount,
    COALESCE(SUM(p.amount), 0) AS paid_amount,
    (i.total - COALESCE(SUM(p.amount), 0)) AS pending_amount,
    po.code AS purchase_order_code,
    gr.id AS goods_receipt_id
FROM invoice i
         JOIN app_user s ON i.user_id = s.id
         LEFT JOIN payment p ON i.id = p.invoice_id
         LEFT JOIN goods_receipt gr ON i.goods_receipt_id = gr.id
         LEFT JOIN purchase_order po ON gr.purchase_order_id = po.id
WHERE i.type = 'SUPPLIER'
  AND i.issue_date BETWEEN start_date AND end_date
  AND i.status != 'VOID'
      AND (in_supplier_id IS NULL OR s.id = in_supplier_id)
GROUP BY s.name, s.doc_number, i.id, i.code, i.issue_date, i.due_date,
    i.status, i.total, po.code, gr.id
ORDER BY i.issue_date;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_payments_report(
    start_date DATE,
    end_date DATE,
    in_payment_method TEXT DEFAULT NULL
)
RETURNS TABLE (
    payment_date TIMESTAMP WITH TIME ZONE,
    invoice_code TEXT,
    invoice_type TEXT,
    customer_supplier TEXT,
    doc_number TEXT,
    payment_method TEXT,
    amount NUMERIC(14,4),
    reference TEXT
) AS $$
BEGIN
RETURN QUERY
SELECT
    p.paid_at AS payment_date,
    i.code AS invoice_code,
    i.type AS invoice_type,
    COALESCE(c.name, s.name) AS customer_supplier,
    COALESCE(c.doc_number, s.doc_number) AS doc_number,
    pm.name AS payment_method,
    p.amount,
    p.reference
FROM payment p
         JOIN invoice i ON p.invoice_id = i.id
         JOIN payment_method pm ON p.method_id = pm.id
         LEFT JOIN app_user c ON (i.type = 'CUSTOMER' AND i.user_id = c.id)
         LEFT JOIN app_user s ON (i.type = 'SUPPLIER' AND i.user_id = s.id)
WHERE p.paid_at::DATE BETWEEN start_date AND end_date
      AND (in_payment_method IS NULL OR pm.code = in_payment_method)
ORDER BY p.paid_at;
END;
$$ LANGUAGE plpgsql;

--  resumen financiero por período
-- SELECT * FROM get_financial_summary_by_period('2023-08-30', '2025-12-31');
--
-- -- Todos los proveedores
-- SELECT * FROM get_supplier_expenses_detail('2023-01-01', '2025-12-31');
--
-- -- Proveedor específico
-- SELECT * FROM get_supplier_expenses_detail('2023-01-01', '2025-12-31', 123);
--
-- -- Todos los métodos de pago
-- SELECT * FROM get_payments_report('2023-01-01', '2025-12-31');
--
-- -- Método de pago específico
-- SELECT * FROM get_payments_report('2025-01-01', '2025-12-31', 'TRANSFER');