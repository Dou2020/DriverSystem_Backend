create view getVehicle as
select
    v.id,
    v.vin,
    v.plate,
    vm."name" as make,
    vm2."name" as model,
    v.model_year,
    v.created_at,
    v.color
from
    vehicle v
        inner join vehicle_make vm  on vm.id = v.make_id
        inner  join vehicle_model vm2  on vm2.id = v.model_id

