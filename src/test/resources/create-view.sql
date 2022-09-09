drop table PARENT_SEARCH_VIEW; -- only required because of spring.jpa.hibernate.ddl-auto=create

create or replace view PARENT_SEARCH_VIEW as
select p.ID, P.NAME, STRING_AGG(c.NAME, ',') within group (order by c.name desc) children_names
from PARENT P
         left join CHILD C on P.ID = C.PARENT_ID
group by p.ID, P.NAME;

drop table PARENT_WITH_CHILDREN_COUNT_VIEW; -- only required because of spring.jpa.hibernate.ddl-auto=create

create or replace view PARENT_WITH_CHILDREN_COUNT_VIEW as
select p.id, p.name, count(c.id) as  count
from parent p
         left outer join child c on p.id = c.parent_id
group by p.name, p.id