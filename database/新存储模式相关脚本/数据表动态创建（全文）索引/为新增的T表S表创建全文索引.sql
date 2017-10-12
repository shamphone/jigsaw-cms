create or replace procedure create_index_Textindex
as
v_tablename varchar2(30);
v_sql_index varchar2(600);
v_idxname varchar2(35);
v_tmp_index int;
v_tmp_textindex int;
E_FAILD EXCEPTION;
CURSOR cur_tablenames IS SELECT TNAME FROM TAB where tname like 'T\_%' escape '\' OR tname like 'S\_%' escape '\';
begin
   OPEN cur_tablenames;
      LOOP      
         FETCH cur_tablenames INTO v_tablename;
            EXIT WHEN cur_tablenames%NOTFOUND;
            IF cur_tablenames%FOUND THEN/*   
               begin 
                  select 1 into v_tmp_index from dual 
                  where exists(select * from user_indexes t where t.Index_Name = 'IDX_'||v_tablename); 
               exception 
                  when NO_DATA_FOUND then v_tmp_index:=0; 
               end;*/
               
               begin
                  select 1 into v_tmp_textindex from dual
                  where exists(select * from user_indexes t where t.Table_Name = v_tablename AND T.INDEX_NAME LIKE 'SH%');
               exception
                  when NO_DATA_FOUND THEN v_tmp_textindex:=0;
               end;
               /*
               BEGIN
               if v_tmp_index = 0 then 
                  v_idxname:='idx_'||v_tablename;
                  v_sql_index:='create index '||v_idxname||' on '||v_tablename||'(NODE_ID)';
                  dbms_output.put_line(v_sql_index);
                  EXECUTE IMMEDIATE v_sql_index;
               end if;
               EXCEPTION
                  WHEN E_FAILD THEN DBMS_OUTPUT.PUT_LINE('EXCEPTION: '||v_idxname||' FAILD');
               END;*/
               
               BEGIN
               IF v_tmp_textindex = 0 THEN
                  v_idxname:='SH_'||v_tablename;
                  if length(v_idxname)>25 then
                     select SUBSTR(v_idxname,1,25) into v_idxname from dual;
                  end if;
                  v_sql_index:='create index '||v_idxname||' on '||v_tablename||'(VALUE) '||'INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS (''lexer full_text_search_lexer'')';
                  dbms_output.put_line(v_sql_index); 
                  EXECUTE IMMEDIATE v_sql_index;
               end if;
               EXCEPTION
                  WHEN E_FAILD THEN DBMS_OUTPUT.PUT_LINE('EXCEPTION: '||v_idxname||' FAILD');
               END;
            end if;              
      end loop;
   close cur_tablenames;
end;
--------------------------------------
--call create_index_Textindex()
