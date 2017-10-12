create or replace procedure Textindex_opt
as
v_indexnames varchar2(30);
CURSOR cur_indexnames IS select INDEX_NAME from user_indexes where index_name like 'SH\_%' ESCAPE '\';
begin
   OPEN cur_indexnames;
      LOOP      
         FETCH cur_indexnames INTO v_indexnames;
            EXIT WHEN cur_indexnames%NOTFOUND;
            IF cur_indexnames%FOUND THEN 
               begin
                  ctx_ddl.optimize_index(v_indexnames,'full');
               end; 
               dbms_output.put_line(v_indexnames);
            end if;              
      end loop;
   close cur_indexnames;
end;
--------------------------------------
--call Textindex_opt()