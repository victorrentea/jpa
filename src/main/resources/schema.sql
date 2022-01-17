drop table POST_SEARCH_VIEW; -- only required because of spring.jpa.hibernate.ddl-auto=create
//
create or replace view POST_SEARCH_VIEW as
select P.ID, P.TITLE, STRING_AGG(c.TITLE, ',') within group (order by c.title desc) comment_titles
from POST P
         left join COMMENTS C on P.ID = C.POST_ID
group by p.ID, P.TITLE;
//