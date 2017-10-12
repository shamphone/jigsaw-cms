create or replace procedure pro_delete_Node2(ID Varchar2)
as
cur_nodeid  groups.id%Type;
v_sql varchar2(200);
Cursor cur Is Select n.pkid From node n Start With n.pkid =ID Connect By Prior n.pkid = n.parent_id;
CURSOR cur_tablenames IS SELECT TNAME FROM TAB where tname like '_\_%' escape '\';
v_tablename varchar2(30);
begin
    open cur;
    fetch cur into cur_nodeid;
    while cur%found loop
       delete from node_authorization where node_id=cur_nodeid;
       delete from node_type where PKID=cur_nodeid;
       Delete From node n Where pkid = cur_nodeid;
       begin
          open cur_tablenames;
             loop
                FETCH cur_tablenames INTO v_tablename;
                   EXIT WHEN cur_tablenames%NOTFOUND;
                   v_sql:='delete from '||v_tablename||' where node_id'||'='''||cur_nodeid||'''';
                   EXECUTE IMMEDIATE v_sql;
             end loop;
          close cur_tablenames;
       end;
    fetch cur into cur_nodeid;
    end loop;
    close cur;
    commit;
end;