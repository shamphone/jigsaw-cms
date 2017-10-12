create or replace procedure create_trigger_S_T
as
--DECLARE
v_tablename varchar2(30);
v_sql_trigger varchar2(2500);
v_tmp int;
CURSOR cur_tablenames IS SELECT TNAME FROM TAB where tname like 'S\_%' escape '\' or tname like 'T\_%' escape '\';
begin
   OPEN cur_tablenames;
      LOOP      
         FETCH cur_tablenames INTO v_tablename;
            EXIT WHEN cur_tablenames%NOTFOUND;
            IF cur_tablenames%FOUND THEN 
               begin 
                  select 1 into v_tmp from dual 
                  where exists(select * from user_triggers t where t.table_name = v_tablename); 
               exception 
                  when NO_DATA_FOUND then v_tmp:=0; 
               end;
               if v_tmp = 0 then 
                  v_sql_trigger:= 'create or replace trigger '||v_tablename||' after update or delete or insert on '||v_tablename||' for each row declare v_tmp int; v_update clob;v_clob clob; begin case  when inserting then begin select 1 into v_tmp from dual where exists(select t.node_id from search t where t.node_id = :NEW.node_id); exception when NO_DATA_FOUND then v_tmp:=0; end; begin if v_tmp = 0 then INSERT INTO search(node_id,text) VALUES(:NEW.node_id,:NEW.value); else update search t set t.text= t.text||''' ||' ''||:NEW.value where t.node_id = :NEW.node_id; end if; end;when updating then begin select 1 into v_tmp from dual where exists(select t.node_id from search t where t.node_id = :old.node_id); exception when NO_DATA_FOUND then v_tmp:=0; end; begin if v_tmp = 0 then insert into search(node_id,text) values(:new.node_id,:new.value); else begin select text into v_clob from search where node_id like :old.node_id; SELECT dfn_clobreplace(text,:old.value,:NEW.value) into v_update FROM search where node_ID=:old.node_id;select replace(v_clob,'''||' '||''','''') into v_clob from dual;select replace(v_update,'''||' '||''','''') into v_update from dual;if v_clob = v_update then update search t set t.text= t.text||''' ||' ''||:NEW.value where t.node_id = :old.node_id; else update search t set t.text = v_update where T.node_ID = :old.node_id;end if;end;end if;end;when deleting then begin select 1 into v_tmp from dual where exists(select t.node_id from search t where t.node_id = :old.node_id); exception when NO_DATA_FOUND then v_tmp:=0; end; begin if v_tmp <> 0 then SELECT dfn_clobreplace(text,:old.value,'' '') into v_update FROM search where node_ID=:old.node_id;update search t set t.text = v_update where T.node_ID = :old.node_id;end if;end;end case;end;';
                  dbms_output.put_line(v_tablename);
                  EXECUTE IMMEDIATE v_sql_trigger;
                  --commit;
               end if;
            end if;              
      end loop;
   close cur_tablenames;
end;