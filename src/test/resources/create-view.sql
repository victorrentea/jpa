drop table PARENT_SEARCH; -- only required because of spring.jpa.hibernate.ddl-auto=create

create or replace view parent_search as
select p.ID, P.NAME, STRING_AGG(c.NAME, ',') within group (order by c.name desc) children_names
from PARENT P
         left join CHILD C on P.ID = C.PARENT_ID
group by p.ID, P.NAME;

