ALTER TABLE invoice
    ADD COLUMN total DECIMAL(10,2);

create  or replace view item_quotation as
select
    i.quantity,
    p.id as product_id,
    p.name,
    p.brand,
    p.unit,
    p.price,
    pc."name" as categoria,
    q.id as quotation,
    i.quantity * p.price as subtotal

from quotation q
         inner join item i  on i.parent_id =q.id
         inner join product p  on p.id = i.product_id
         inner join product_category pc  on  pc.id =p.category_id