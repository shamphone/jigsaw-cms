/*
Navicat PGSQL Data Transfer

Source Server         : newCoolink
Source Server Version : 80404
Source Host           : localhost:5432
Source Database       : newCoolink
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 80404
File Encoding         : 65001

Date: 2010-09-26 16:22:48
*/


-- ----------------------------
-- Function structure for "public"."pro_copy_node2"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_copy_node2"(pro_new_contentid varchar, pro_source_contentid varchar)
  RETURNS "pg_catalog"."void" AS $BODY$declare
	sql varchar;
	v_tablename varchar;
	cur CURSOR for SELECT tablename FROM pg_tables where schemaname='public' and tablename LIKE '_/_%' escape '/';
BEGIN
	open cur;
	fetch cur into v_tablename;
	while found loop
        sql:='insert into '||v_tablename||'(node_id,value,vindex,length) select '||''''||pro_new_contentID||''''||' ,x.Value,x.vindex,x.length FROM '||v_tablename|| ' x where x.node_id'||'='''||pro_source_contentID||'''';
        execute sql;             
	fetch cur into v_tablename;
	end loop;
   Close cur;   
  Insert Into node
    (pkid, parent_id, Name, orderno)
    Select pro_new_contentID, n.parent_id, n.Name, n.orderno
      From node n
     Where n.pkid = pro_source_contentID;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."pro_count_new"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_count_new"()
  RETURNS "pg_catalog"."void" AS $BODY$declare
	pro_delete_sql varchar;
	pro_delete1_sql varchar;
	pro_delete2_sql varchar;
	pro_delete3_sql varchar;
	pro_delete4_sql varchar;
	pro_delete5_sql varchar;
BEGIN
pro_delete_sql :=  'truncate table  realtime_count';
pro_delete1_sql := 'truncate table  diary_count';
pro_delete2_sql := 'truncate table  monthly_count';
pro_delete3_sql := 'truncate table  weekly_count';
pro_delete4_sql := 'truncate table  quarterly_count';
pro_delete5_sql := 'truncate table  yearly_count';
EXECUTE  pro_delete1_sql;
EXECUTE  pro_delete2_sql;
EXECUTE  pro_delete3_sql;
EXECUTE  pro_delete4_sql;
EXECUTE  pro_delete5_sql;

insert into diary_count 
      (sumvalue, name, access_date)
select sum(t.sumvalue),
             t.name,
             date_trunc('day', access_time)
        from realtime_count t
       group by date_trunc('day', access_time), t.name;       

EXECUTE  pro_delete_sql;

insert into monthly_count (sumvalue, name, access_month, access_year)
      select sum(t.sumvalue),
             t.name,
             extract(month from t.access_date),
             extract(year from t.access_date)
        from diary_count t
        group by t.name,extract(month from t.access_date),extract(year from t.access_date);
                
insert
      into weekly_count (sumvalue, name, access_week, access_year)
      select sum(t.sumvalue),
             t.name,
             extract(WEEK from t.access_date),
             extract(year from t.access_date)
        from diary_count t
       group by t.name,
                extract(WEEK from t.access_date),
                extract(year from t.access_date);

insert
      into quarterly_count (sumvalue, NAME, ACCESS_QUARTER, ACCESS_YEAR)
      select sum(t.sumvalue),
             t.name,
             CEILING(t.access_month / 3),
             t.access_year
        from monthly_count t
       group by t.name, CEILING(t.access_month / 3), t.access_year;

insert
      into yearly_count (sumvalue, name, access_year)
      select sum(t.sumvalue), t.name, t.ACCESS_YEAR
        from quarterly_count t
       group by t.name, t.ACCESS_YEAR;   
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."pro_delete_node2"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_delete_node2"(id varchar)
  RETURNS "pg_catalog"."void" AS $BODY$declare   
	cur_NodeID  varchar;  
	v_tablename varchar;
	v_sql VARCHAR;
	sql text;
	sqlres text;  
	cur_tablenames cursor for SELECT tablename FROM pg_tables where schemaname='public' and tablename LIKE '_/_%' escape '/';
	cur_id  Cursor  for  select pkid from coolink_node_tab;
BEGIN
  select recursive_select('node','pkid','parent_id',ID,null,'pkid') into sqlres;
  drop table  if exists coolink_node_tab;
  CREATE TEMPORARY TABLE coolink_node_tab(pkid varchar(128));  
  sql:=' insert into coolink_node_tab  select pkid from node where pkid IN('||sqlres||')';
   execute sql;
   Open cur_id;          
   Fetch cur_id Into cur_NodeID;
   while found loop
 --delete from node_authorization where node_id=cur_NodeID;
 --delete from node_type where PKID=cur_NodeID;
   Delete From node  Where pkid = cur_NodeID;
   open cur_tablenames;   
   FETCH cur_tablenames INTO v_tablename;
    while found loop
    v_sql='delete from '||v_tablename||' where node_id'||'='''||cur_NodeID||'''';
    EXECUTE v_sql;
    FETCH cur_tablenames INTO v_tablename;
    End loop;
    Close cur_tablenames;   
   Fetch cur_id Into cur_NodeID;
   End loop;
   Close cur_id;
   DROP TABLE IF EXISTS coolink_node_tab;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."pro_delete_nodedefinition"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_delete_nodedefinition"(id varchar)
  RETURNS "pg_catalog"."void" AS $BODY$declare
	cur_NodeID  Varchar;
	sql text;
	sqlres text;
	cur   Cursor  for  Select n.pkid From coolink_definition_tab n ;
BEGIN
  select recursive_select('node_definition','pkid','super_id',ID,null,'pkid') into sqlres;
	sql:=' insert into coolink_definition_tab  select pkid from node_definition where pkid IN('||sqlres||')';
  drop table  if exists coolink_definition_tab;
  CREATE TEMPORARY TABLE coolink_definition_tab(pkid varchar(256));
  EXECUTE sql;
	Open cur;
	Fetch cur Into cur_NodeID;
	While found loop
	--delete from node_definition_authorization  where definition_id=cur_NodeID;
	--delete from nodedef_processdef  where node_definition =cur_NodeID;
	--delete from node_view  where nodefid=cur_NodeID;
	update NODE_DEFINITION set DELETE_MARK='1' , PKID=PKID||' '||LOCALTIMESTAMP(0)  where PKID=cur_NodeID;
	Fetch cur Into cur_NodeID;
	End loop;
	Close cur;
	DROP TABLE IF EXISTS coolink_definition_tab;

END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."pro_insert_nodedefinition"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_insert_nodedefinition"(pro_pkid varchar, pro_description varchar, pro_is_system bpchar, pro_delete_mark bpchar, pro_create_time timestamp, pro_name varchar, pro_super_id varchar)
  RETURNS "pg_catalog"."void" AS $BODY$BEGIN
Insert Into node_definition (pkid,delete_mark,description,is_system,super_id,create_time,name) 
Values (pro_pkid , pro_delete_mark ,pro_description ,pro_is_system , pro_super_id ,pro_create_time ,pro_name );
--Insert Into node_definition_super  (definition_id,super_id,type) 
--Values (pro_pkid ,pro_super_id varchar,'1');
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."pro_propertydefinition"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_propertydefinition"()
  RETURNS "pg_catalog"."void" AS $BODY$Declare 
	definitionID  varchar;
	property  varchar;
	superID  varchar;
	pro_num numeric;
	sql varchar;
	c1 Cursor  for Select n.pkid,n.super_id From node_definition n  Where n.super_id Is Not Null;
	c2 Cursor  for select pkid from property_empty_tab;   
BEGIN
   Open c1;
   Fetch c1 Into definitionID,superID;
   while found loop      
    sql=' insert into property_empty_tab select pkid from property_definition where node_definition_id'||'='''||superID||'''';
    drop table  if exists property_empty_tab;
    CREATE TEMPORARY TABLE property_empty_tab(pkid varchar(256));
    EXECUTE sql;
    Open c2;          
    Fetch c2 Into property;
    while found loop
     Select Count(*) into pro_num From property_definition r Where r.pkid =property And r.node_definition_id =definitionID;
     If(pro_num=0) then
     insert into PROPERTY_DEFINITION (PKID, NODE_DEFINITION_ID, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, DESCRIPTION, EDITOR_TYPE, ENUM_ENTRY, REFERENCE_TYPE, DELETABLE, READ_ONLY, NODE_TYPE)
     Select D.PKID,definitionID,D.NAME,d.multiple,d.min_length,d.max_length,d.type,d.orderno,d.description,d.editor_type,d.enum_entry,d.reference_type,'0',d.read_only,d.Node_Type
     From property_definition d
     Where d.pkid =property And d.node_definition_id =superID;
     End If;
    Fetch c2 Into property;
    End loop;
    Close c2;
   drop table  if exists property_empty_tab;
   Fetch c1 Into definitionID,superID;
   End loop;
   Close c1;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."pro_update_nodedefinition"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."pro_update_nodedefinition"(pro_pkid varchar, pro_description varchar, pro_delete_mark bpchar, pro_name varchar, pro_super_id varchar, pro_is_system bpchar)
  RETURNS "pg_catalog"."void" AS $BODY$BEGIN
 update NODE_DEFINITION 
     set DESCRIPTION = pro_description,
         IS_SYSTEM   = pro_is_system,
         DELETE_MARK = pro_delete_mark,
         name        = pro_name,
	 super_id    = pro_super_id
   where PKID = pro_pkid;

-- update NODE_DEFINITION_SUPER 
--     set SUPER_ID = pro_super_id
--  where DEFINITION_ID = pro_pkid
--     And Type = '1';
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."recursive_select"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."recursive_select"(IN table_name varchar, IN child_col_name varchar, IN parent_col_name varchar, IN child_value varchar, IN parent_value varchar, IN return_col_name varchar, OUT result text)
  RETURNS "pg_catalog"."text" AS $BODY$declare 
	sqltext text;
	sqlresult text;
	sqlresults text;
	sql text;
BEGIN
IF (child_value IS NOT NULL and parent_value is  null ) or (child_value IS  NULL and parent_value is NOT  null) then
if child_value IS NOT NULL and parent_value is  null then
sql:=' select '||return_col_name||' from '||table_name||' where '||child_col_name||' ='''||child_value||''' limit 1';
sqltext:=' select '||child_col_name||' from '||table_name||' where '||child_col_name||' ='''||child_value||'''';
result:=' select '||return_col_name||' from '||table_name||' where '||child_col_name||' ='''||child_value||'''';
execute (sql) into sqlresult;
while  sqlresult is not NULL loop
sqlresult :=NULL;
sql:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' in ( '||sqltext||')'||' limit 1 ';   
sqlresults:=' select '||return_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';  
sqltext:=' select '||child_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';   
execute (sql) into sqlresult;
  if sqlresult is not NULL then
	result = result||' union all '||sqlresults; 
  end if;  
end loop;

else 

sql:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' ='''||parent_value||''' limit 1';
sqltext:=' select '||child_col_name||' from '||table_name||' where '||parent_col_name||' ='''||parent_value||'''';
result:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' ='''||parent_value||'''';
execute (sql) into sqlresult;
while  sqlresult is not NULL loop
sqlresult:=NULL;
sql:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' in ( '||sqltext||')'||' limit 1 ';   
sqlresults:=' select '||return_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';  
sqltext:=' select '||child_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';   
execute (sql) into sqlresult;
  if sqlresult is not NULL then
	result = result||' union all '||sqlresults; 
  end if;  
end loop;

end if;
end IF;

END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Function structure for "public"."recursive_select_sql"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."recursive_select_sql"(IN table_name varchar, IN child_col_name varchar, IN parent_col_name varchar, IN child_value_sql varchar, IN parent_value_sql varchar, IN return_col_name varchar, OUT result text)
  RETURNS "pg_catalog"."text" AS $BODY$declare 
	sqltext text;
	sqlresult text;
	sqlresults text;
	sql text;
BEGIN
IF (child_value_sql IS NOT NULL and parent_value_sql is  null ) or (child_value_sql IS  NULL and parent_value_sql is NOT  null) then
if child_value_sql IS NOT NULL and parent_value_sql is  null then
sql:=' select '||return_col_name||' from '||table_name||' where '||child_col_name||' in ('||child_value_sql||') limit 1';
sqltext:=' select '||child_col_name||' from '||table_name||' where '||child_col_name||' in ('||child_value_sql||')';
result:=' select '||return_col_name||' from '||table_name||' where '||child_col_name||' in ('||child_value_sql||')';
execute sql into sqlresult;
while  sqlresult is not NULL loop
sqlresult:=NULL;
sql:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' in ( '||sqltext||') limit 1 ';
sqlresults:=' select '||return_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';
sqltext:=' select '||child_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';
execute sql into sqlresult;
  if sqlresult is not NULL THEN
	result:= result||' union all '||sqlresults; 
  end if;
end loop;

else 

sql:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' in ('||parent_value_sql||') limit 1';
sqltext:=' select '||child_col_name||' from '||table_name||' where '||parent_col_name||' in ('||parent_value_sql||')';
result:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' in ('||parent_value_sql||')';
execute sql into sqlresult;
while  @sqlresult is not NULL loop
sqlresult:=NULL;
sql:=' select '||return_col_name||' from '||table_name||' where '||parent_col_name||' in ( '||sqltext||') limit 1 ';
sqlresults:=' select '||return_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';
sqltext:=' select '||child_col_name||' from '||table_name||' where  '||parent_col_name||' in ( '||sqltext||')';
execute sql into sqlresult;
  if sqlresult is not NULL THEN
	result:= result||' union all '||sqlresults; 
  end if;
end loop;

end if;
end IF;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
