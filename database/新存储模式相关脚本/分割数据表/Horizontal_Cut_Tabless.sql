/**
*CATEGORY:COOLINK
*PROCEDURE:Horizontal_Cut_Tabless
*CORP.:FULONG
*AUTHOR:LIULEI
*DATE:07/05/2009
*VERSION:2.2
*/
--CREATE OR REPLACE PROCEDURE Horizontal_Cut_Tabless
--IS
declare
  CURSOR cur_tablenames IS SELECT TNAME FROM TAB WHERE TABTYPE='TABLE' AND TNAME LIKE '%_VALUE';
  table_name TAB.TNAME%TYPE;
  v_sql_cur VARCHAR2(500);
  v_sql_create VARCHAR2(500);
  v_sql_create1 VARCHAR2(500);
  v_sql_insert VARCHAR2(500);
  v_sql_insert1 VARCHAR2(500);
  v_sql_insert2 VARCHAR2(500);
  v_curid INTEGER;
  v_curexe INTEGER;
  v_subtable_i INTEGER:=0; 
  v_subtable_name VARCHAR2(200);
  v_value VARCHAR2(128);
  v_value1 VARCHAR2(128);
  col_name VARCHAR2(20):='property_id';
  table_prefix VARCHAR2(1);
BEGIN
  OPEN cur_tablenames;
    LOOP      
      FETCH cur_tablenames INTO table_name;
      EXIT WHEN cur_tablenames%NOTFOUND;
         IF cur_tablenames%FOUND THEN         
            v_sql_insert1 :='value(node_id,value,vindex) select node_id,value,vindex from';
            v_sql_insert2 :='select node_id,value,vindex,length from';
            CASE table_name
               WHEN 'BLOB_VALUE' THEN
                  table_prefix:='i';
                  v_sql_create1:='(node_id varchar2(128),value blob,vindex number,length number)';
                  v_sql_insert1:=v_sql_insert2||' blob_value';
               WHEN 'BOOLEAN_VALUE' THEN
                  table_prefix:='b';
                  v_sql_create1:='(node_id varchar2(128),value char(1),vindex number,length number)';
                  v_sql_insert1:=v_sql_insert1||' boolean_value';
               WHEN 'CLOB_VALUE' THEN
                  table_prefix:='t';
                  v_sql_create1:='(node_id varchar2(128),value clob,vindex number,length number)';
                  v_sql_insert1:=v_sql_insert2||' clob_value';
               WHEN 'DATE_VALUE' THEN
                  table_prefix:='d';
                  v_sql_create1:='(node_id varchar2(128),value date,vindex number,length number)';
                  v_sql_insert1:=v_sql_insert1||' date_value';
               WHEN 'DOUBLE_VALUE' THEN
                  table_prefix:='f';
                  v_sql_create1:='(node_id varchar2(128),value number,vindex number,length number)';
                  v_sql_insert1:=v_sql_insert1||' double_value';
               WHEN 'LONG_VALUE' THEN
                  table_prefix:='l';
                  v_sql_create1:='(node_id varchar2(128),value number,vindex number,length number)';
                  v_sql_insert1:=v_sql_insert1||' long_value';
               WHEN 'PATH_VALUE' THEN
                  table_prefix:='p';
                  v_sql_create1:='(node_id varchar2(128),value varchar2(512),vindex number,length number)';
                  v_sql_insert1:=v_sql_insert1||' path_value';
               WHEN 'REFERENCE_VALUE' THEN
                  table_prefix:='r';
                  v_sql_create1:='(node_id varchar2(128),value varchar2(128),vindex number,length number)';
                  v_sql_insert1:=v_sql_insert1||' reference_value';
               WHEN 'STRING_VALUE' THEN
                  table_prefix:='s';
                  v_sql_create1:='(node_id varchar2(128),value varchar2(2048),vindex number,length number)';
                  v_sql_insert1:=v_sql_insert2||' string_value';
            END CASE;         
            v_sql_cur:='select distinct '||col_name||' from '||table_name; 
            v_curid:=DBMS_SQL.OPEN_CURSOR;
            DBMS_SQL.PARSE(v_curid,v_sql_cur,DBMS_SQL.NATIVE);
            DBMS_SQL.DEFINE_COLUMN(v_curid,1,v_value,128);
            v_curexe:=DBMS_SQL.EXECUTE(v_curid);
            v_curexe:=DBMS_SQL.FETCH_ROWS(v_curid);             
            WHILE v_curexe>0 LOOP
               DBMS_SQL.COLUMN_VALUE(v_curid,1,v_value);     
               SELECT REPLACE(v_value,' ','') INTO v_value1 FROM dual;
               IF LENGTH(v_value1)=LENGTH(v_value) THEN 
                  SELECT REPLACE(v_value1,'-','_') INTO v_value1 FROM dual;               
                  v_subtable_i:=v_subtable_i+v_curexe;
                  BEGIN 
                     v_subtable_name:=table_prefix||'_'||v_value1;                 
                     v_sql_create:='create table '||v_subtable_name||' '||v_sql_create1;                  
                     EXECUTE IMMEDIATE v_sql_create;   
                     v_sql_insert:='insert into '||v_subtable_name||' '||v_sql_insert1||' where '||col_name ||'='''||v_value||'''';                    
                     EXECUTE IMMEDIATE v_sql_insert;
                     COMMIT;
                  END; 
               END IF;     
               v_curexe:=DBMS_SQL.FETCH_ROWS(v_curid);
               v_sql_create:='';
               v_sql_insert:='';
            END LOOP; 
            v_sql_cur:='';
            DBMS_SQL.CLOSE_CURSOR(v_curid);            
            DBMS_OUTPUT.PUT_LINE('total created '||v_subtable_i||' '||table_prefix||' '||table_name||' '||' subtables');
            v_subtable_i:=0;      
         END IF;
    END LOOP;
END;