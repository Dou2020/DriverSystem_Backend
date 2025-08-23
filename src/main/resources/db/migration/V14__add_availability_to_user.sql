
ALTER TABLE app_user ADD COLUMN availability Boolean;

CREATE OR REPLACE VIEW  get_work_order as select
                                  wo.id,
                                  wo.code,
                                  wo.description,
                                  wo.estimated_hours,
                                  wo.opened_at,
                                  wo.closed_at,
                                  mt."name" as maintenance_type,
                                  ws."name" as  status,
                                  au.doc_number as doc_number_customer,
                                  au."name"  as customer,
                                  au.phone as  phone_customer,
                                  c."name" as created_by,
                                  g.vin,
                                  g.plate ,
                                  g.model,
                                  g.model_year,
                                  g.color,
                                  g.make,
                                  au.id as customer_id,
                                  mt.id as maintenance_id
                              from work_order wo
                                       inner join getvehicle g   on wo.vehicle_id = g.id
                                       inner  join app_user au  on wo.customer_id  = au.id
                                       inner join work_status ws  on wo.status_id = ws.id
                                       inner join maintenance_type mt  on  wo.type_id = mt.id
                                       inner join app_user c on c.id =wo.created_by;



create or replace view  user_availability as
select
    ur.user_id,
    au.name as "employee",
    au.availability,
    ur.role_id,
    r.name as "role",
    r.code
from user_role ur
         inner join app_user au on au.id = ur.user_id
         inner join app_role r on r.id = ur.role_id
WHERE au.availability = true
  AND r.code IN ('EMPLOYEE', 'SPECIALIST');

