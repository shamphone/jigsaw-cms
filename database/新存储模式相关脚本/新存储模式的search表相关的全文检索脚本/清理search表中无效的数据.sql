create or replace procedure del_null_search
as
v_del_sql varchar2(200);
begin
    v_del_sql := 'delete from search where text is null';
    execute immediate v_del_sql;
    commit;
end;