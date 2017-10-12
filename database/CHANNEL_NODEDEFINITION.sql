-- Create table
create table CHANNEL_NODEDEFINITION
(
  TEMPLATE_ID        VARCHAR2(32) not null,
  CHANNEL_ID         VARCHAR2(32) not null,
  NODE_DEFINITION_ID VARCHAR2(32) not null
)
tablespace COOLINKCMS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column CHANNEL_NODEDEFINITION.TEMPLATE_ID
  is '网站主键';
comment on column CHANNEL_NODEDEFINITION.CHANNEL_ID
  is '栏目主键';
comment on column CHANNEL_NODEDEFINITION.NODE_DEFINITION_ID
  is '内容分类主键';
