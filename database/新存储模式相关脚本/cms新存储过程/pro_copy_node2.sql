create or replace procedure pro_copy_node2(pro_new_contentID Varchar2,pro_source_contentID Varchar2) 
as
CURSOR cur IS SELECT TNAME FROM TAB where tname like '_\_%' escape '\';
v_tablename varchar2(30);
v_sql varchar2(1000);
begin
    open cur;
       loop
          fetch cur into v_tablename;
          exit when cur%notfound;
             v_sql:='insert into '||v_tablename||'(node_id,value,vindex,length) select '||''''||pro_new_contentID||''''||' ,x.Value,x.vindex,x.length FROM '||v_tablename|| ' x where x.node_id'||'='''||pro_source_contentID||'''';
             --dbms_output.put_line(v_sql);
             EXECUTE IMMEDIATE v_sql;
       end loop;
       v_sql:='insert into node(pkid,parent_id,name,orderno) select '||''''||pro_new_contentID||''''||' ,n.parent_id,n.name,n.orderno from node n where n.pkid'||'='''||pro_source_contentID||'''';
       execute immediate v_sql;
    commit;
    close cur;
end;