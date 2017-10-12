/**
*CATEGORY:COOLINK
*PROCEDURE:merge value in S_* tables into search table, for full text search
*CORP.:FULONG
*AUTHOR:LIULEI
*DATE:07/05/2009
*VERSION:2.2
*/

declare
   CURSOR cur_tablenames is SELECT TNAME FROM TAB WHERE TABTYPE='TABLE' AND TNAME LIKE 'S\_%' escape '\';
   v_sql_cur varchar2(100);
   c1 int;
   r1 int;
   v_value clob;
   v_valuetmp clob;
   v_nodeid varchar2(128);
   v_tmp int;
   table_name varchar2(100);
BEGIN
   OPEN cur_tablenames;
      LOOP      
         FETCH cur_tablenames INTO table_name;
         EXIT WHEN cur_tablenames%NOTFOUND;
         v_sql_cur := 'select node_id,value from '||table_name;
         dbms_output.put_line(table_name);
         c1:=dbms_sql.open_cursor;
         DBMS_SQL.PARSE(c1,v_sql_cur,DBMS_SQL.NATIVE);
         DBMS_SQL.DEFINE_COLUMN(c1,1,v_nodeid,128);
         DBMS_SQL.DEFINE_COLUMN(c1,2,v_value);
         r1:=dbms_sql.execute(c1);
         r1:=dbms_sql.fetch_rows(c1);
         while r1 > 0 loop
            dbms_sql.column_value(c1,1,v_nodeid);
            dbms_sql.column_value(c1,2,v_value);
            
            if v_value is null then
               v_value:=' ';
            end if;
            
            begin
               select 1 into v_tmp
               from dual
               where exists(select t.node_id from search t where t.node_id = v_nodeid);                                
            exception 
               when NO_DATA_FOUND then v_tmp:=0;
            end;
                       
            if v_tmp = 0 then 
               insert into search values(v_nodeid,v_value); 
            else
               update search t
               set t.text= t.text||' '||v_value
               where t.node_id = v_nodeid;
            end if;
            r1:=dbms_sql.fetch_rows(c1);     
         commit;
         end loop;       
         dbms_sql.close_cursor(c1);   
      end loop;
      close cur_tablenames;
end;

