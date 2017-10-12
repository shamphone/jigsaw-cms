create or replace procedure create_index
as
v_tablename varchar2(30);
v_sql_index varchar2(200);
v_idxname varchar2(35);
v_tmp int;
CURSOR cur_tablenames IS SELECT TNAME FROM TAB where tname like '_\_%' escape '\';
begin
   OPEN cur_tablenames;
      LOOP      
         FETCH cur_tablenames INTO v_tablename;
            EXIT WHEN cur_tablenames%NOTFOUND;
            IF cur_tablenames%FOUND THEN 
               begin 
                  select 1 into v_tmp from dual 
                  where exists(select * from user_indexes t where t.index_name = 'IDX_'||v_tablename); 
               exception 
                  when NO_DATA_FOUND then v_tmp:=0; 
               end;
               if v_tmp = 0 then 
                  v_idxname:='idx_'||v_tablename;
                  v_sql_index:='create index '||v_idxname||' on '||v_tablename||'(NODE_ID)';
                  dbms_output.put_line(v_sql_index);
                  EXECUTE IMMEDIATE v_sql_index;
               end if;
            end if;              
      end loop;
   close cur_tablenames;
end;
--------------------------------------
--call create_index()