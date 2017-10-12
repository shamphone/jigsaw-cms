CREATE OR REPLACE PROCEDURE Horizontal_Cut_Tabless
IS
/**
*CATEGORY:COOLINK
*PROCEDURE:Horizontal_Cut_Tabless
*原来的切分过程通过*_value表里找出属性ID来建表
*这样会忽略掉没有值的属性，造成错误，现在采取从
*property_definition表里取id和type做匹配的策略
*并加入对字段索引的创建
*CORP:FULONG
*AUTHOR:MALI
*DATE:03/06/2010
*VERSION:1.0
*/
    --declare
    cursor v_table is select distinct pkid,type from property_definition;
    --table_name varchar2(200);
    v_name varchar2(500);
    v_name1 varchar2(500);
    v_type varchar2(2);
    v_prefix varchar2(1);
    v_num integer;
    v_subtable_name VARCHAR2(200);
    v_sql_create VARCHAR2(500);
    v_sql_create1 VARCHAR2(500);
    v_sql_insert VARCHAR2(500);
    v_sql_insert1 VARCHAR2(500);
    v_sql_insert2 VARCHAR2(500);
    col_name VARCHAR2(20):='property_id';
    v_flag boolean;
    v_index boolean;--是否在value字段上建索引
    v_sql_index VARCHAR2(500);--创建索引语句
begin
   -- Test statements here
  open v_table;
  v_num := 0;
  loop
       fetch v_table into v_name,v_type;
       exit when v_table%NOTFOUND;
       if v_table%FOUND
          then
               --dbms_output.put_line('this is :'||v_num||v_name);
               --v_num := v_num+1;
           v_flag := true;
           v_index := true;
           v_sql_insert1 :='value(node_id,value,vindex) select node_id,value,vindex from';
           v_sql_insert2 :='select node_id,value,vindex,length from';
           case v_type
                when '1' then v_prefix := 's';--STRING_VALUE
                     --table_name:='STRING_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value varchar2(2048),vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert2||' string_value';
                when '2' then v_prefix := 'i';--BLOB_VALUE
                     --table_name:='BLOB_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value blob,vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert2||' blob_value';
                     v_index := false;
                when '3' then v_prefix := 'l';--LONG_VALUE
                     --table_name:='LONG_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value number,vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert1||' long_value';
                when '4' then v_prefix := 'f';--DOUBLE_VALUE
                     --table_name:='DOUBLE_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value number(30,8),vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert1||' double_value';
                when '5' then v_prefix := 'd';--DATE_VALUE
                     --table_name:='DATE_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value date,vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert1||' date_value';
                when '6' then v_prefix := 'b';--BOOLEAN_VALUE
                     --table_name:='BOOLEAN_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value char(1),vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert1||' boolean_value';
                when '8' then v_prefix := 'p';--PATH_VALUE
                     --table_name:='PATH_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value varchar2(512),vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert1||' path_value';
                when '9' then v_prefix := 'r';--REFERENCE_VALUE
                     --table_name:='REFERENCE_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value varchar2(128),vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert1||' reference_value';
                when '10' then v_prefix := 't';--CLOB_VALUE
                     --table_name:='CLOB_VALUE';
                     v_sql_create1:='(node_id varchar2(128) not null,value clob,vindex number default 0 not null,length number default 0 not null)';
                     v_sql_insert1:=v_sql_insert2||' clob_value'; 
                     v_index := false;
                else v_flag := false;       
            end case;
                --处理属性名称
             if v_flag then    
                SELECT REPLACE(v_name,' ','') INTO v_name1 FROM dual;
                IF LENGTH(v_name1)=LENGTH(v_name) THEN 
                  SELECT REPLACE(v_name1,'-','_') INTO v_name1 FROM dual;               
                  v_num:=v_num+1;
                  BEGIN 
                     v_subtable_name:=v_prefix||'_'||v_name1;                 
                     v_sql_create:='create table '||v_subtable_name||' '||v_sql_create1;                  
                     EXECUTE IMMEDIATE v_sql_create;   
                     v_sql_insert:='insert into '||v_subtable_name||' '||v_sql_insert1||' where '||col_name ||'='''||v_name||'''';                    
                     EXECUTE IMMEDIATE v_sql_insert;
                     v_sql_index := 'create index '||v_subtable_name||'_NODE_ID on '||v_subtable_name||'(NODE_ID)'; 
                     EXECUTE IMMEDIATE v_sql_index;
                     v_sql_index := 'create index '||v_subtable_name||'_VINDEX on '||v_subtable_name||'(VINDEX)'; 
                     EXECUTE IMMEDIATE v_sql_index;
                     if v_index then
                          v_sql_index := 'create index '||v_subtable_name||'_VALUE on '||v_subtable_name||'(VALUE)';
                          EXECUTE IMMEDIATE v_sql_index;
                     end if;   
                     EXCEPTION 
                      WHEN OTHERS THEN 
                        DBMS_OUTPUT.put_line('sqlcode : ' ||sqlcode); 
                        DBMS_OUTPUT.put_line('sqlerrm : ' ||sqlerrm); 
                        --ROLLBACK; 
                     COMMIT;
                  END; 
                 END IF;   
               END IF;    
               --v_curexe:=DBMS_SQL.FETCH_ROWS(v_curid);
               v_sql_create:='';
               v_sql_insert:='';
               if v_flag then
               DBMS_OUTPUT.PUT_LINE('total created '||v_num||' * '||v_prefix||'_'||v_name1);
               end if;
       end if; 
   end loop;  
   --end;     
end Horizontal_Cut_Tabless;
