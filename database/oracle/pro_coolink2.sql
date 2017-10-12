--------------------------------------------------
-- Export file for user ZHYT                    --
-- Created by fulong on 2011-1-24, 上午 9:15:27 --
--------------------------------------------------

spool pro_coolink2.log

prompt
prompt Creating procedure PRO_ANALYZEALLTABLES
prompt =======================================
prompt
CREATE OR REPLACE PROCEDURE pro_AnalyzeAllTables
IS
--分析所有表及索引
BEGIN
     --分析所有表：analyze table TABLENAME compute statistics
     for cur_item in (select table_name from user_tables) loop
         begin
              execute immediate 'analyze table '|| cur_item.table_name
                               || ' compute statistics';
         exception
            when others then
                dbms_output.put_line('分析表异常：'||sqlerrm);
         end;
     end loop;

    --分析所有索引：analyze index INDEXNAME estimate statistics
     for cur_item in (select index_name from user_indexes) loop
         begin
              execute immediate 'analyze index '|| cur_item.index_name
                                || ' estimate statistics';
         exception
            when others then
                dbms_output.put_line('分析索引异常：'||sqlerrm);
         end;
     end loop;
END pro_AnalyzeAllTables;
/

prompt
prompt Creating procedure PRO_COPY_NODE2
prompt =================================
prompt
create or replace procedure pro_copy_node2(pro_new_contentID Varchar2,pro_source_contentID Varchar2)
as
CURSOR cur IS SELECT TNAME FROM TAB where tname like '_\_%' escape '\';
CURSOR curno is select max(orderno)+1 from node n,(select parent_id,name from node where pkid =pro_source_contentID) pn where n.parent_id=pn.parent_id and n.name=pn.name;
v_tablename varchar2(128);
v_sql varchar2(1000);
v_orderno varchar2(128);
begin
    open cur;
       loop
          fetch cur into v_tablename;
          exit when cur%notfound;
             v_sql:='insert into '||v_tablename||'(node_id,value,vindex,length) select '||''''||pro_new_contentID||''''||' ,x.Value,x.vindex,x.length FROM '||v_tablename|| ' x where x.node_id'||'='''||pro_source_contentID||'''';
             EXECUTE IMMEDIATE v_sql;
       end loop;
       open curno;
       fetch curno into v_orderno;
       v_sql:='insert into node(pkid,definition,parent_id,name,type,orderno) select '||''''||pro_new_contentID||''''||' ,n.definition,n.parent_id,n.name,n.type,'||''||v_orderno||''||' from node n where n.pkid'||'='''||pro_source_contentID||'''';
       execute immediate v_sql;
    commit;
    close curno;
    close cur;
end;
/

prompt
prompt Creating procedure PRO_COUNT_NEW
prompt ================================
prompt
create or replace procedure pro_count_new is
begin

  declare
    pro_delete_sql varchar2(128)  := 'truncate table realtime_count';--清空实时表 by mali
    pro_delete1_sql varchar2(128) := 'truncate table  diary_count';
    pro_delete2_sql varchar2(128) := 'truncate table  monthly_count';
    pro_delete3_sql varchar2(128) := 'truncate table  weekly_count';
    pro_delete4_sql varchar2(128) := 'truncate table  quarterly_count';
    pro_delete5_sql varchar2(128) := 'truncate table  yearly_count';


  begin
    execute immediate pro_delete1_sql;
    execute immediate pro_delete2_sql;
    execute immediate pro_delete3_sql;
    execute immediate pro_delete4_sql;
    execute immediate pro_delete5_sql;

    commit;
    insert into diary_count d
      (d.sumvalue, d.name, d.access_date)
      select sum(t.sumvalue),
             t.name,
             trunc(t.access_time)
        from realtime_count t
       group by trunc(t.access_time), t.name;

       execute immediate pro_delete_sql;
    commit;

    insert
      into monthly_count m(m.sumvalue, m.name, m.access_month, access_year)
      select sum(t.sumvalue),
             t.name,
             To_char(t.access_date, 'mm'),
             To_char(t.access_date, 'yyyy')
        from diary_count t
       group by t.name,
                To_char(t.access_date, 'mm'),
                To_char(t.access_date, 'yyyy');

    commit;

    insert
      into weekly_count w(w.sumvalue, w.name, w.access_week, access_year)
      select sum(t.sumvalue),
             t.name,
             To_char(t.access_date, 'WW'),
             To_char(t.access_date, 'yyyy')
        from diary_count t
       group by t.name,
                To_char(t.access_date, 'WW'),
                To_char(t.access_date, 'yyyy');

    commit;

    insert
      into quarterly_count q(q.sumvalue, q.NAME, q.ACCESS_QUARTER, q.ACCESS_YEAR)
      select sum(t.sumvalue),
             t.name,
             Floor(t.access_month / 3) + 1,
             t.access_year
        from monthly_count t
       group by t.name, Floor(t.access_month / 3) + 1, t.access_year;

    commit;

    insert
      into yearly_count y(y.sumvalue, y.name, y.access_year)
      select sum(t.sumvalue), t.name, t.ACCESS_YEAR
        from quarterly_count t
       group by t.name, t.ACCESS_YEAR;
    commit;
           end;
  end pro_count_new;
/

prompt
prompt Creating procedure PRO_DELETE_NODE2
prompt ===================================
prompt
create or replace procedure pro_delete_Node2(ID Varchar2)
as
cur_nodeid  node.pkid%Type;
resource_nodeid  node.pkid%Type;
v_sql varchar2(200);
v_rsql varchar2(32767);
Cursor cur Is Select n.pkid From node n Start With n.pkid =ID Connect By Prior n.pkid = n.parent_id;
CURSOR cur_tablenames IS SELECT TNAME FROM TAB where tname like '_\_%' escape '\';
type cur_type is ref Cursor;
cur_resource cur_type;
v_tablename varchar2(30);
begin
    open cur;
    fetch cur into cur_nodeid;
    while cur%found loop
       begin
          open cur_tablenames;
             loop
                FETCH cur_tablenames INTO v_tablename;
                   EXIT WHEN cur_tablenames%NOTFOUND;
                   case 
                        when instr(v_tablename, 'R_', 1, 1)=1 then
                        v_sql:='delete from '||v_tablename||' where node_id'||'='''||cur_nodeid||''' or value'||'='''||cur_nodeid||'''';
                        when instr(v_tablename, 'P_', 1, 1)=1 then
                        --增加对资源节点的删除                        
                        v_rsql:='
                        select pkid  
                        from node n, 
                        (select value,count(1) as n from '||v_tablename||' where value in (select value from '||v_tablename||' where node_id='''||cur_nodeid||''' ) group by value) t 
                        where t.n = 1
                        and n.name = substr(t.value, instr(t.value, '||'''/'''||', 1, 2) + 1) 
                        and n.parent_id = 
                            (select pkid from node 
                            where parent_id = 
                                  (select pkid from (select * from node start with pkid = '''||cur_nodeid||''' connect by pkid = prior parent_id) 
                                   where parent_id = '||'''root'''||' and type =1) 
                                   and name = '||'''resources'''||')';                     
                        open cur_resource for v_rsql;
                        while cur_resource%found loop
			fetch cur_resource into resource_nodeid;
                        Delete From node Where pkid = resource_nodeid;
                        Delete From i_resource_content Where node_id = resource_nodeid;
                        Delete From s_mime Where node_id = resource_nodeid;
                        Delete From d_createdTime Where node_id = resource_nodeid;
                        Delete From l_length n Where node_id = resource_nodeid;
                        end loop;
                        close cur_resource;
                        
                        v_sql:='delete from '||v_tablename||' where node_id'||'='''||cur_nodeid||'''';
                        ELSE 
                        v_sql:='delete from '||v_tablename||' where node_id'||'='''||cur_nodeid||'''';
                   end case;
                   --dbms_output.put_line(v_sql);
                   EXECUTE IMMEDIATE v_sql;
             end loop;
          close cur_tablenames;
       end;
       Delete From node n Where pkid = cur_nodeid;
    fetch cur into cur_nodeid;
    end loop;
    close cur;
    commit;
end;
/

prompt
prompt Creating procedure PRO_DELETE_NODEDEFINITION
prompt ============================================
prompt
create or replace procedure pro_delete_NodeDefinition(ID Varchar2) Is
Begin

 Declare
  cur_NodeID  Varchar2(32);
  --定义游标
  Cursor cur Is
  Select n.pkid From node_definition n Start With n.pkid =ID
   Connect By Prior n.pkid = n.super_id and n.delete_mark<>'1';  --liulei modified： "and n.delete_mark<>'1'"  added

 Begin
 Open cur;
 Fetch cur Into cur_NodeID;
 While cur%Found
   Loop
       --delete from node_definition_authorization t where t.definition_id=cur_NodeID;
       --delete from nodedef_processdef t where t.node_definition =cur_NodeID;
       --delete from node_view t where t.nodefid=cur_NodeID;
       update NODE_DEFINITION set DELETE_MARK='1' , PKID=PKID||' '||to_char(sysdate,'yyyy-mm-dd hh:mi:ss') where PKID=cur_NodeID; --liulei modified：PKID 与 sysdate之间添加"空格" --songbo modified: sysdate精确到秒
       Fetch cur Into cur_NodeID;
   End Loop;
 Close cur;
 end;

end pro_delete_NodeDefinition;
/

prompt
prompt Creating procedure PRO_PROPERTYDEFINITION
prompt =========================================
prompt
create or replace procedure pro_propertyDefinition Is
--整理property_definition中不完整的属性
--步骤1 遍历node_definition
--步骤2 对superid不为空的加载其父大纲的属性 有一次遍历

Begin

 Declare
  definitionID  Varchar2(128);
  superID  Varchar2(128);
  --定义游标
  Cursor c1 Is
  Select n.pkid,n.super_id From node_definition n  Where n.super_id Is Not Null;
  Begin
   Open c1;
   Fetch c1 Into definitionID,superID;
   While c1%Found Loop
   Begin
    Declare
    property  Varchar2(128); --super definition property
    Cursor c2 Is
    Select p.pkid From property_definition p
    Where p.node_definition_id = superID;
    Begin
    Open c2;
    Fetch c2 Into property;
    While c2%Found Loop
    Declare
    pro_num Number;
    Begin
    Select Count(*) Into pro_num
    From property_definition r
    Where r.pkid =property And r.node_definition_id =definitionID;
    If(pro_num=0) Then
    insert into PROPERTY_DEFINITION (PKID, NODE_DEFINITION_ID, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, DESCRIPTION, EDITOR_TYPE, ENUM_ENTRY, REFERENCE_TYPE, DELETABLE, READ_ONLY, NODE_TYPE)
    Select D.PKID,definitionID,D.NAME,d.multiple,d.min_length,d.max_length,d.type,d.orderno,d.description,d.editor_type,d.enum_entry,d.reference_type,'0',d.read_only,d.Node_Type
    From property_definition d
    Where d.pkid =property And d.node_definition_id =superID;
    Commit;
    End If;
    End;
    Fetch c2 Into property;
    End Loop;
    Close c2;
    End;
    End;
    Fetch c1 Into definitionID,superID;
   End Loop;
 Close c1;
 End;

 End pro_propertyDefinition;
/

prompt
prompt Creating procedure PRO_RANKORDERNO
prompt ==================================
prompt
create or replace procedure pro_rankorderno(pro_parent_id Varchar2,pro_name Varchar2)
as
CURSOR cur IS select pn.pkid,pn.rn from (select po.* ,rownum rn from (select n.pkid, n.orderno from node n where n.parent_id = pro_parent_id and n.name = pro_name and type=1 order by n.orderno) po) pn where pn.orderno<>pn.rn;
pro_pkid varchar2(128);
pro_rn varchar2(128);
v_sql varchar2(1000);
begin
    open cur;
       loop
          fetch cur into pro_pkid,pro_rn;
          exit when cur%notfound;
             v_sql:='update node set orderno'||'='''||pro_rn||''''||' where pkid'||'='''||pro_pkid||'''';
             EXECUTE IMMEDIATE v_sql;
       end loop;
    commit;
    close cur;
end;
/

prompt
prompt Creating procedure PRO_RANKORDERNOS
prompt ===================================
prompt
create or replace procedure pro_rankordernos
as
CURSOR curpn is select n.parent_id ,n.name from node n group by n.parent_id ,n.name order by name;
pro_parent_id varchar2(128);
pro_name varchar2(128);
CURSOR cur IS select pn.pkid,pn.rn from (select po.* ,rownum rn from (select n.pkid, n.orderno from node n where n.parent_id = pro_parent_id and n.name = pro_name and type=1 order by n.orderno) po) pn where pn.orderno<>pn.rn;
pro_pkid varchar2(128);
pro_rn varchar2(128);
v_sql varchar2(1000);
begin
   open curpn;
      loop
          fetch curpn into pro_parent_id,pro_name;
          exit when curpn%notfound;
          open cur;
             loop
                fetch cur into pro_pkid,pro_rn;
                exit when cur%notfound;
                   v_sql:='update node set orderno'||'='''||pro_rn||''''||' where pkid'||'='''||pro_pkid||'''';
                   EXECUTE IMMEDIATE v_sql;
             end loop;
          commit;
          close cur;
      end loop;
      commit;
      close curpn;
end;
/


spool off
