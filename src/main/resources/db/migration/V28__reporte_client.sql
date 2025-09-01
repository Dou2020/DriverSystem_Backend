CREATE OR REPLACE VIEW service_ratings_report AS
SELECT
    wo.code AS work_order_code,
    c.name AS customer_name,
    c.doc_number,
    c.phone,
    c.email,
    sf.rating,
    sf.comment AS customer_comment,
    sf.created_at AS feedback_date,
    mt.name AS service_type,
    v.vin,
    v.plate,
    vm.name AS vehicle_make,
    vmd.name AS vehicle_model,
    wo.opened_at AS service_date,
    wo.closed_at AS completion_date,
    (SELECT name FROM app_user WHERE id = wo.created_by) AS created_by,
    (SELECT STRING_AGG(au.name, ', ')
     FROM work_assignment wa
              JOIN app_user au ON wa.assignee_id = au.id
     WHERE wa.work_order_id = wo.id) AS assigned_technicians
FROM service_feedback sf
         JOIN work_order wo ON sf.work_order_id = wo.id
         JOIN app_user c ON sf.customer_id = c.id
         JOIN maintenance_type mt ON wo.type_id = mt.id
         JOIN vehicle v ON wo.vehicle_id = v.id
         JOIN vehicle_make vm ON v.make_id = vm.id
         JOIN vehicle_model vmd ON v.model_id = vmd.id
ORDER BY sf.created_at DESC, sf.rating DESC;


CREATE OR REPLACE VIEW customer_service_history AS
SELECT
    c.id AS customer_id,
    c.name AS customer_name,
    c.doc_type,
    c.doc_number,
    c.phone,
    c.email,
    wo.code AS work_order_code,
    wo.opened_at AS service_date,
    mt.name AS service_type,
    ws.name AS service_status,
    v.vin,
    v.plate,
    vm.name AS vehicle_make,
    vmd.name AS vehicle_model,
    v.model_year,
    v.color,
    wo.description AS service_description,
    COALESCE(SUM(wl.hours), 0) AS total_hours
FROM app_user c
         JOIN work_order wo ON c.id = wo.customer_id
         JOIN maintenance_type mt ON wo.type_id = mt.id
         JOIN work_status ws ON wo.status_id = ws.id
         JOIN vehicle v ON wo.vehicle_id = v.id
         JOIN vehicle_make vm ON v.make_id = vm.id
         JOIN vehicle_model vmd ON v.model_id = vmd.id
         LEFT JOIN work_log wl ON wo.id = wl.work_order_id
WHERE c.user_type = 'PERSON' OR c.user_type = 'ORGANIZATION'
GROUP BY c.id, c.name, c.doc_type, c.doc_number, c.phone, c.email,
         wo.code, wo.opened_at, mt.name, ws.name, v.vin, v.plate,
         vm.name, vmd.name, v.model_year, v.color, wo.description
ORDER BY c.name, wo.opened_at DESC;


CREATE OR REPLACE FUNCTION get_customer_service_history(p_customer_id bigint)
RETURNS TABLE (
    work_order_code text,
    service_date timestamptz,
    service_type text,
    service_status text,
    vehicle_details text,
    service_description text,
    total_hours numeric
) AS $$
BEGIN
RETURN QUERY
SELECT
    wo.code,
    wo.opened_at,
    mt.name,
    ws.name,
    CONCAT(vm.name, ' ', vmd.name, ' ', v.model_year, ' - Placa: ',
           COALESCE(v.plate, 'Sin placa'), ', VIN: ', COALESCE(v.vin, 'Sin VIN')) AS vehicle_details,
    wo.description,
    COALESCE(SUM(wl.hours), 0)
FROM work_order wo
         JOIN maintenance_type mt ON wo.type_id = mt.id
         JOIN work_status ws ON wo.status_id = ws.id
         JOIN vehicle v ON wo.vehicle_id = v.id
         JOIN vehicle_make vm ON v.make_id = vm.id
         JOIN vehicle_model vmd ON v.model_id = vmd.id
         LEFT JOIN work_log wl ON wo.id = wl.work_order_id
WHERE wo.customer_id = p_customer_id  -- Usamos el parámetro renombrado
GROUP BY wo.id, wo.code, wo.opened_at, mt.name, ws.name,
         vm.name, vmd.name, v.model_year, v.plate, v.vin, wo.description
ORDER BY wo.opened_at DESC;
END;
$$ LANGUAGE plpgsql;



-- - Obtener calificaciones de servicio
-- SELECT * FROM service_ratings_report;
--
-- -- Obtener historial de un cliente específico
-- SELECT * FROM get_customer_service_history(6);
