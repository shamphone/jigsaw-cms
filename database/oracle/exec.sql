spool dbInstalling.log
spool off;
spool log.log
create tablespace %username% datafile '%oraFileName%' size  %size%m autoextend on next 50m maxsize unlimited logging extent management local autoallocate segment space management auto;
create user %username% identified by %password% default tablespace %username% temporary tablespace temp;
grant connect , resource to  %username%;
grant dba to  %username%;

conn %username%/%password%@%serviceName%


prompt
prompt Creating table ACCOUNT
prompt ======================
prompt
create table ACCOUNT
(
  PKID            VARCHAR2(128) not null,
  COMMONNAME      VARCHAR2(512),
  POINTS          NUMBER default 0 not null,
  OWNER_ID        VARCHAR2(128),
  TYPE            NUMBER default 1 not null,
  TRADABLE        CHAR(1) default 0 not null,
  PAYABLE         CHAR(1) default 0 not null,
  CREATION_DATE   DATE not null,
  VALIDATION_DATE DATE
)
;
comment on column ACCOUNT.PKID
  is '主键';
comment on column ACCOUNT.COMMONNAME
  is '可显示名称';
comment on column ACCOUNT.POINTS
  is '积分，积分总值';
comment on column ACCOUNT.OWNER_ID
  is '所有者，机构或者用户的ID';
comment on column ACCOUNT.TYPE
  is '类型，1交易账户；2普通卡，3银卡，4金卡，5钻石卡';
comment on column ACCOUNT.TRADABLE
  is '可交易,0：不可交易；1：可交易';
comment on column ACCOUNT.PAYABLE
  is '可支付,0：不可支付；1：可支付';
comment on column ACCOUNT.CREATION_DATE
  is '创建日期';
comment on column ACCOUNT.VALIDATION_DATE
  is '验证日期';
alter table ACCOUNT
  add constraint ACCOUNT_PKID primary key (PKID);
create index ACCOUNT_COMMONNAME on ACCOUNT (COMMONNAME);
create index ACCOUNT_OWNER_ID on ACCOUNT (OWNER_ID);

prompt
prompt Creating table B_ISFOLDER
prompt =========================
prompt
create table B_ISFOLDER
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   CHAR(1),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index B_ISFOLDER_NODE_ID on B_ISFOLDER (NODE_ID);
create index B_ISFOLDER_VALUE on B_ISFOLDER (VALUE);
create index B_ISFOLDER_VINDEX on B_ISFOLDER (VINDEX);

prompt
prompt Creating table CLICKCOUNT
prompt =========================
prompt
create table CLICKCOUNT
(
  SUMVALUE NUMBER,
  NAME     VARCHAR2(128)
)
;
create index CLICK_NAME on CLICKCOUNT (NAME);
create index CLICK_SUMVALUE on CLICKCOUNT (SUMVALUE);

prompt
prompt Creating table DIARY_COUNT
prompt ==========================
prompt
create table DIARY_COUNT
(
  SUMVALUE    NUMBER default 1 not null,
  NAME        VARCHAR2(128) not null,
  ACCESS_DATE DATE not null
)
;
comment on column DIARY_COUNT.SUMVALUE
  is '计数值';
comment on column DIARY_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
comment on column DIARY_COUNT.ACCESS_DATE
  is '点击发生的日期。日期和小时组合形成时间点';
create index I_DIARY_ACCESSDATE on DIARY_COUNT (ACCESS_DATE);
create index I_DIARY_NAME on DIARY_COUNT (NAME);
create index I_DIARY_SUMVALUE on DIARY_COUNT (SUMVALUE);

prompt
prompt Creating table D_CREATEDTIME
prompt ============================
prompt
create table D_CREATEDTIME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   DATE,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index D_CREATEDTIME_NODE_ID on D_CREATEDTIME (NODE_ID);
create index D_CREATEDTIME_VALUE on D_CREATEDTIME (VALUE);
create index D_CREATEDTIME_VINDEX on D_CREATEDTIME (VINDEX);

prompt
prompt Creating table D_EXPIRY_DATE
prompt ============================
prompt
create table D_EXPIRY_DATE
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   DATE,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index D_EXPIRY_DATE_NODE_ID on D_EXPIRY_DATE (NODE_ID);
create index D_EXPIRY_DATE_VALUE on D_EXPIRY_DATE (VALUE);
create index D_EXPIRY_DATE_VINDEX on D_EXPIRY_DATE (VINDEX);

prompt
prompt Creating table D_LASTLOGINDATE
prompt ==============================
prompt
create table D_LASTLOGINDATE
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   DATE,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index D_LASTLOGINDATE_NODE_ID on D_LASTLOGINDATE (NODE_ID);
create index D_LASTLOGINDATE_VALUE on D_LASTLOGINDATE (VALUE);
create index D_LASTLOGINDATE_VINDEX on D_LASTLOGINDATE (VINDEX);

prompt
prompt Creating table D_LASTMODIFIEDDATE
prompt =================================
prompt
create table D_LASTMODIFIEDDATE
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   DATE,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index D_LASTMODIFIEDDATE_NODE_ID on D_LASTMODIFIEDDATE (NODE_ID);
create index D_LASTMODIFIEDDATE_VALUE on D_LASTMODIFIEDDATE (VALUE);
create index D_LASTMODIFIEDDATE_VINDEX on D_LASTMODIFIEDDATE (VINDEX);

prompt
prompt Creating table D_UPDATETIME
prompt ===========================
prompt
create table D_UPDATETIME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   DATE,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index D_UPDATETIME_NODE_ID on D_UPDATETIME (NODE_ID);
create index D_UPDATETIME_VALUE on D_UPDATETIME (VALUE);
create index D_UPDATETIME_VINDEX on D_UPDATETIME (VINDEX);

prompt
prompt Creating table I_RESOURCE_CONTENT
prompt =================================
prompt
create table I_RESOURCE_CONTENT
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   BLOB,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index I_RESOURCE_CONTENT_NODE_ID on I_RESOURCE_CONTENT (NODE_ID);
create index I_RESOURCE_CONTENT_VINDEX on I_RESOURCE_CONTENT (VINDEX);

prompt
prompt Creating table L_LENGTH
prompt =======================
prompt
create table L_LENGTH
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   NUMBER,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index L_LENGTH_NODE_ID on L_LENGTH (NODE_ID);
create index L_LENGTH_VALUE on L_LENGTH (VALUE);
create index L_LENGTH_VINDEX on L_LENGTH (VINDEX);

prompt
prompt Creating table MONTHLY_COUNT
prompt ============================
prompt
create table MONTHLY_COUNT
(
  SUMVALUE     NUMBER default 1 not null,
  NAME         VARCHAR2(128) not null,
  ACCESS_MONTH NUMBER not null,
  ACCESS_YEAR  NUMBER not null
)
;
comment on column MONTHLY_COUNT.SUMVALUE
  is '计数值';
comment on column MONTHLY_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
comment on column MONTHLY_COUNT.ACCESS_MONTH
  is '月份';
comment on column MONTHLY_COUNT.ACCESS_YEAR
  is '年份';
create index I_MONTHLY_ACCESS_MONTH on MONTHLY_COUNT (ACCESS_MONTH);
create index I_MONTHLY_ACCESS_YEAR on MONTHLY_COUNT (ACCESS_YEAR);
create index I_MONTHLY_NAME on MONTHLY_COUNT (NAME);
create index I_MONTHLY_SUMVALUE on MONTHLY_COUNT (SUMVALUE);

prompt
prompt Creating table NODE
prompt ===================
prompt
create table NODE
(
  PKID          VARCHAR2(128) not null,
  PARENT_ID     VARCHAR2(128) not null,
  NAME          VARCHAR2(128),
  ORDERNO       NUMBER,
  STRING_INDEX  VARCHAR2(1),
  CLOB_INDEX    VARCHAR2(1)
)
;
comment on column NODE.PARENT_ID
  is '本表PKID';
comment on column NODE.NAME
  is '子节点标识';
comment on column NODE.ORDERNO
  is '子节点序号';
comment on column NODE.STRING_INDEX
  is '用于内容标题全文检索';
comment on column NODE.CLOB_INDEX
  is '用于内容全文检索';
alter table NODE
  add constraint PKID_NODE primary key (PKID);
create index NODE_NAME on NODE (NAME);
create index NODE_ORDERNO on NODE (ORDERNO);
create index NODE_PARENT_ID on NODE (PARENT_ID);

prompt
prompt Creating table NODE_DEFINITION
prompt ==============================
prompt
create table NODE_DEFINITION
(
  PKID        VARCHAR2(128) not null,
  DELETE_MARK CHAR(1) default 0 not null,
  DESCRIPTION VARCHAR2(512),
  IS_SYSTEM   CHAR(1) default '0' not null,
  SUPER_ID    VARCHAR2(128),
  CREATE_TIME DATE default Sysdate not null,
  NAME        VARCHAR2(512)
)
;
comment on column NODE_DEFINITION.PKID
  is '由系统生成，不使用数据库提供的自动产生的主键';
comment on column NODE_DEFINITION.DELETE_MARK
  is '0:正常，1：已删除';
comment on column NODE_DEFINITION.DESCRIPTION
  is '描述';
comment on column NODE_DEFINITION.IS_SYSTEM
  is '是否是系统大纲 0不是 1是';
comment on column NODE_DEFINITION.NAME
  is '（类别）名称';
alter table NODE_DEFINITION
  add constraint PKID primary key (PKID);
create index NODE_DEFINITION_DELETE_MARK on NODE_DEFINITION (DELETE_MARK);
create index NODE_DEFINITION_SUPERID on NODE_DEFINITION (SUPER_ID);
create index NODE_DEFINITION_SYSTEM on NODE_DEFINITION (IS_SYSTEM);

prompt
prompt Creating table NODE_TYPE
prompt ========================
prompt
create table NODE_TYPE
(
  PKID       VARCHAR2(128) not null,
  DEFINITION VARCHAR2(128) not null,
  TYPE       CHAR(1) default '1' not null
)
;
comment on column NODE_TYPE.TYPE
  is '缺省为主定义';
alter table NODE_TYPE
  add constraint NODE_TYPE_PKID primary key (DEFINITION, PKID);
create index INDEX_NODE_TYPE_DEF on NODE_TYPE (DEFINITION);
create index INDEX_NODE_TYPE_PKID on NODE_TYPE (PKID);
create index INDEX_NODE_TYPE_TYPE on NODE_TYPE (TYPE);

prompt
prompt Creating table PROPERTY_DEFAULT
prompt ===============================
prompt
create table PROPERTY_DEFAULT
(
  PROPERTY_DEFINITION_ID VARCHAR2(128) not null,
  VALUE                  VARCHAR2(512) not null,
  DEFINITION_ID          VARCHAR2(128)
)
;
comment on column PROPERTY_DEFAULT.PROPERTY_DEFINITION_ID
  is 'PROPERTY_DEFINITION表主键';
comment on column PROPERTY_DEFAULT.DEFINITION_ID
  is 'NODE_DEFINITION表的主键';

prompt
prompt Creating table PROPERTY_DEFINITION
prompt ==================================
prompt
create table PROPERTY_DEFINITION
(
  PKID               VARCHAR2(128) not null,
  NODE_DEFINITION_ID VARCHAR2(128) not null,
  NAME               VARCHAR2(512) not null,
  MULTIPLE           CHAR(1) not null,
  MIN_LENGTH         NUMBER not null,
  MAX_LENGTH         NUMBER not null,
  TYPE               NUMBER not null,
  ORDERNO            NUMBER,
  DESCRIPTION        VARCHAR2(1024),
  EDITOR_TYPE        VARCHAR2(512),
  ENUM_ENTRY         VARCHAR2(512),
  REFERENCE_TYPE     VARCHAR2(512),
  DELETABLE          CHAR(1) default '1' not null,
  READ_ONLY          CHAR(1) default '0',
  NODE_TYPE          VARCHAR2(128)
)
;
comment on column PROPERTY_DEFINITION.PKID
  is '由系统生成，不使用数据库提供的自动产生的主键';
comment on column PROPERTY_DEFINITION.NODE_DEFINITION_ID
  is 'Node_definition表的主键';
comment on column PROPERTY_DEFINITION.NAME
  is '（类别）名称';
comment on column PROPERTY_DEFINITION.MULTIPLE
  is '是否枚举';
comment on column PROPERTY_DEFINITION.MIN_LENGTH
  is '最小枚举个数 非枚举属性，0表示非必选，1表示必填';
comment on column PROPERTY_DEFINITION.MAX_LENGTH
  is '最大枚举个数 非枚举属性，该值无效';
comment on column PROPERTY_DEFINITION.TYPE
  is '参考配置文件中对属性类型的定义';
comment on column PROPERTY_DEFINITION.ORDERNO
  is '同一节点定义下的序号';
comment on column PROPERTY_DEFINITION.DESCRIPTION
  is '该属性的描述';
comment on column PROPERTY_DEFINITION.EDITOR_TYPE
  is '表示这个属性在编辑的时候用哪个编辑器';
comment on column PROPERTY_DEFINITION.ENUM_ENTRY
  is '表示枚举字段对应的数据字典。';
comment on column PROPERTY_DEFINITION.REFERENCE_TYPE
  is '表明引用类型的属性定义具体的引用类型';
comment on column PROPERTY_DEFINITION.READ_ONLY
  is '定义属性是否可写';
comment on column PROPERTY_DEFINITION.NODE_TYPE
  is '复杂属性的类型';
alter table PROPERTY_DEFINITION
  add constraint PKID_PROPERTY_DEFINITION primary key (PKID, NODE_DEFINITION_ID);
create index PROPERTY_DEFINITION_DEFINITION on PROPERTY_DEFINITION (NODE_DEFINITION_ID);
create index PROPERTY_DEFINITION_ENUM_ENTRY on PROPERTY_DEFINITION (ENUM_ENTRY);
create index PROPERTY_DEFINITION_ORDERNO on PROPERTY_DEFINITION (ORDERNO);
create index PROPERTY_DEFINITION_TYPE on PROPERTY_DEFINITION (TYPE);

prompt
prompt Creating table P_PROP703
prompt ========================
prompt
create table P_PROP703
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(512),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index P_PROP703_NODE_ID on P_PROP703 (NODE_ID);
create index P_PROP703_VALUE on P_PROP703 (VALUE);
create index P_PROP703_VINDEX on P_PROP703 (VINDEX);

prompt
prompt Creating table QUARTERLY_COUNT
prompt ==============================
prompt
create table QUARTERLY_COUNT
(
  SUMVALUE       NUMBER default 1 not null,
  NAME           VARCHAR2(128) not null,
  ACCESS_QUARTER NUMBER not null,
  ACCESS_YEAR    NUMBER not null
)
;
comment on column QUARTERLY_COUNT.SUMVALUE
  is '计数值';
comment on column QUARTERLY_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
comment on column QUARTERLY_COUNT.ACCESS_QUARTER
  is '季度';
comment on column QUARTERLY_COUNT.ACCESS_YEAR
  is '年份';
create index I_QUARTERLY_NAME on QUARTERLY_COUNT (NAME);
create index I_QUARTERLY_SUMVALUE on QUARTERLY_COUNT (SUMVALUE);
create index QUARTERLY_ACCESS_QUARTER on QUARTERLY_COUNT (ACCESS_QUARTER);
create index QUARTERLY_ACCESS_YEAR on QUARTERLY_COUNT (ACCESS_YEAR);

prompt
prompt Creating table REALTIME_COUNT
prompt =============================
prompt
create table REALTIME_COUNT
(
  SUMVALUE    NUMBER default 1 not null,
  NAME        VARCHAR2(128) not null,
  ACCESS_TIME DATE not null
)
;
comment on column REALTIME_COUNT.SUMVALUE
  is '计数值，保留字段，暂定为1';
comment on column REALTIME_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
comment on column REALTIME_COUNT.ACCESS_TIME
  is '点击发生时间';
create index REALTIME_ACCESS_TIME on REALTIME_COUNT (ACCESS_TIME);
create index REALTIME_NAME on REALTIME_COUNT (NAME);
create index REALTIME_SUMVALUE on REALTIME_COUNT (SUMVALUE);

prompt
prompt Creating table R_CREATOR
prompt ========================
prompt
create table R_CREATOR
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(128),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index R_CREATOR_NODE_ID on R_CREATOR (NODE_ID);
create index R_CREATOR_VALUE on R_CREATOR (VALUE);
create index R_CREATOR_VINDEX on R_CREATOR (VINDEX);

prompt
prompt Creating table R_CREATORID
prompt ==========================
prompt
create table R_CREATORID
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(128),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index R_CREATORID_NODE_ID on R_CREATORID (NODE_ID);
create index R_CREATORID_VALUE on R_CREATORID (VALUE);
create index R_CREATORID_VINDEX on R_CREATORID (VINDEX);

prompt
prompt Creating table R_MEMBER
prompt =======================
prompt
create table R_MEMBER
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(128),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index R_MEMBER_NODE_ID on R_MEMBER (NODE_ID);
create index R_MEMBER_VALUE on R_MEMBER (VALUE);
create index R_MEMBER_VINDEX on R_MEMBER (VINDEX);

prompt
prompt Creating table R_MODIFIER
prompt =========================
prompt
create table R_MODIFIER
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(128),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index R_MODIFIER_NODE_ID on R_MODIFIER (NODE_ID);
create index R_MODIFIER_VALUE on R_MODIFIER (VALUE);
create index R_MODIFIER_VINDEX on R_MODIFIER (VINDEX);

prompt
prompt Creating table R_PROP31
prompt =======================
prompt
create table R_PROP31
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(128),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index R_PROP31_NODE_ID on R_PROP31 (NODE_ID);
create index R_PROP31_VALUE on R_PROP31 (VALUE);
create index R_PROP31_VINDEX on R_PROP31 (VINDEX);

prompt
prompt Creating table S_DISPLAYNAME
prompt ============================
prompt
create table S_DISPLAYNAME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_DISPLAYNAME_NODE_ID on S_DISPLAYNAME (NODE_ID);
create index S_DISPLAYNAME_VALUE on S_DISPLAYNAME (VALUE);
create index S_DISPLAYNAME_VINDEX on S_DISPLAYNAME (VINDEX);

prompt
prompt Creating table S_DOMAIN
prompt =======================
prompt
create table S_DOMAIN
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_DOMAIN_NODE_ID on S_DOMAIN (NODE_ID);
create index S_DOMAIN_VALUE on S_DOMAIN (VALUE);
create index S_DOMAIN_VINDEX on S_DOMAIN (VINDEX);

prompt
prompt Creating table S_LABEL
prompt ======================
prompt
create table S_LABEL
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_LABEL_NODE_ID on S_LABEL (NODE_ID);
create index S_LABEL_VALUE on S_LABEL (VALUE);
create index S_LABEL_VINDEX on S_LABEL (VINDEX);

prompt
prompt Creating table S_LINK
prompt =====================
prompt
create table S_LINK
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_LINK_NODE_ID on S_LINK (NODE_ID);
create index S_LINK_VALUE on S_LINK (VALUE);
create index S_LINK_VINDEX on S_LINK (VINDEX);

prompt
prompt Creating table S_MIME
prompt =====================
prompt
create table S_MIME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_MIME_NODE_ID on S_MIME (NODE_ID);
create index S_MIME_VALUE on S_MIME (VALUE);
create index S_MIME_VINDEX on S_MIME (VINDEX);

prompt
prompt Creating table S_MODEL
prompt ======================
prompt
create table S_MODEL
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_MODEL_NODE_ID on S_MODEL (NODE_ID);
create index S_MODEL_VALUE on S_MODEL (VALUE);
create index S_MODEL_VINDEX on S_MODEL (VINDEX);

prompt
prompt Creating table S_NAVIGATETEMPLATES
prompt ==================================
prompt
create table S_NAVIGATETEMPLATES
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_NAVIGATETEMPLATES_NODE_ID on S_NAVIGATETEMPLATES (NODE_ID);
create index S_NAVIGATETEMPLATES_VALUE on S_NAVIGATETEMPLATES (VALUE);
create index S_NAVIGATETEMPLATES_VINDEX on S_NAVIGATETEMPLATES (VINDEX);

prompt
prompt Creating table S_ORG_ENTERPRISENAME
prompt ===================================
prompt
create table S_ORG_ENTERPRISENAME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_ORG_ENTERPRISENAME_NODE_ID on S_ORG_ENTERPRISENAME (NODE_ID);
create index S_ORG_ENTERPRISENAME_VALUE on S_ORG_ENTERPRISENAME (VALUE);
create index S_ORG_ENTERPRISENAME_VINDEX on S_ORG_ENTERPRISENAME (VINDEX);

prompt
prompt Creating table S_ORG_QQ
prompt =======================
prompt
create table S_ORG_QQ
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_ORG_QQ_NODE_ID on S_ORG_QQ (NODE_ID);
create index S_ORG_QQ_VALUE on S_ORG_QQ (VALUE);
create index S_ORG_QQ_VINDEX on S_ORG_QQ (VINDEX);

prompt
prompt Creating table S_PATH
prompt =====================
prompt
create table S_PATH
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_PATH_NODE_ID on S_PATH (NODE_ID);
create index S_PATH_VALUE on S_PATH (VALUE);
create index S_PATH_VINDEX on S_PATH (VINDEX);

prompt
prompt Creating table S_PRICE_DESC
prompt ===========================
prompt
create table S_PRICE_DESC
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_PRICE_DESC_NODE_ID on S_PRICE_DESC (NODE_ID);
create index S_PRICE_DESC_VALUE on S_PRICE_DESC (VALUE);
create index S_PRICE_DESC_VINDEX on S_PRICE_DESC (VINDEX);

prompt
prompt Creating table S_PROP156
prompt ========================
prompt
create table S_PROP156
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_PROP156_NODE_ID on S_PROP156 (NODE_ID);
create index S_PROP156_VALUE on S_PROP156 (VALUE);
create index S_PROP156_VINDEX on S_PROP156 (VINDEX);

prompt
prompt Creating table S_PROP531
prompt ========================
prompt
create table S_PROP531
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_PROP531_NODE_ID on S_PROP531 (NODE_ID);
create index S_PROP531_VALUE on S_PROP531 (VALUE);
create index S_PROP531_VINDEX on S_PROP531 (VINDEX);

prompt
prompt Creating table S_PROP93
prompt =======================
prompt
create table S_PROP93
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_PROP93_NODE_ID on S_PROP93 (NODE_ID);
create index S_PROP93_VALUE on S_PROP93 (VALUE);
create index S_PROP93_VINDEX on S_PROP93 (VINDEX);

prompt
prompt Creating table S_QUANTITY_DESC
prompt ==============================
prompt
create table S_QUANTITY_DESC
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_QUANTITY_DESC_NODE_ID on S_QUANTITY_DESC (NODE_ID);
create index S_QUANTITY_DESC_VALUE on S_QUANTITY_DESC (VALUE);
create index S_QUANTITY_DESC_VINDEX on S_QUANTITY_DESC (VINDEX);

prompt
prompt Creating table S_SPECIFICATION_DESC
prompt ===================================
prompt
create table S_SPECIFICATION_DESC
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_SPECIFICATION_DESC_NODE_ID on S_SPECIFICATION_DESC (NODE_ID);
create index S_SPECIFICATION_DESC_VALUE on S_SPECIFICATION_DESC (VALUE);
create index S_SPECIFICATION_DESC_VINDEX on S_SPECIFICATION_DESC (VINDEX);

prompt
prompt Creating table S_TEMPLATES
prompt ==========================
prompt
create table S_TEMPLATES
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_TEMPLATES_NODE_ID on S_TEMPLATES (NODE_ID);
create index S_TEMPLATES_VALUE on S_TEMPLATES (VALUE);
create index S_TEMPLATES_VINDEX on S_TEMPLATES (VINDEX);

prompt
prompt Creating table S_TITLE
prompt ======================
prompt
create table S_TITLE
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_TITLE_NODE_ID on S_TITLE (NODE_ID);
create index S_TITLE_VALUE on S_TITLE (VALUE);
create index S_TITLE_VINDEX on S_TITLE (VINDEX);

prompt
prompt Creating table S_USER_COMMONNAME
prompt ================================
prompt
create table S_USER_COMMONNAME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_USER_COMMONNAME_NODE_ID on S_USER_COMMONNAME (NODE_ID);
create index S_USER_COMMONNAME_VALUE on S_USER_COMMONNAME (VALUE);
create index S_USER_COMMONNAME_VINDEX on S_USER_COMMONNAME (VINDEX);

prompt
prompt Creating table S_USER_PASSWORD
prompt ==============================
prompt
create table S_USER_PASSWORD
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_USER_PASSWORD_NODE_ID on S_USER_PASSWORD (NODE_ID);
create index S_USER_PASSWORD_VALUE on S_USER_PASSWORD (VALUE);
create index S_USER_PASSWORD_VINDEX on S_USER_PASSWORD (VINDEX);

prompt
prompt Creating table S_USER_USERNAME
prompt ==============================
prompt
create table S_USER_USERNAME
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   VARCHAR2(2048),
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index S_USER_USERNAME_NODE_ID on S_USER_USERNAME (NODE_ID);
create index S_USER_USERNAME_VALUE on S_USER_USERNAME (VALUE);
create index S_USER_USERNAME_VINDEX on S_USER_USERNAME (VINDEX);

prompt
prompt Creating table TOTALLY_COUNT
prompt ============================
prompt
create table TOTALLY_COUNT
(
  SUMVALUE NUMBER default 1 not null,
  NAME     VARCHAR2(128) not null
)
;
comment on column TOTALLY_COUNT.SUMVALUE
  is '计数值';
comment on column TOTALLY_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
alter table TOTALLY_COUNT
  add constraint TOTALLY_PK primary key (SUMVALUE, NAME);
create index TOTALLY_NAME on TOTALLY_COUNT (NAME);
create index TOTALLY_SUMVALUE on TOTALLY_COUNT (SUMVALUE);

prompt
prompt Creating table T_DESCRIPTION
prompt ============================
prompt
create table T_DESCRIPTION
(
  NODE_ID VARCHAR2(128) not null,
  VALUE   CLOB,
  VINDEX  NUMBER default 0 not null,
  LENGTH  NUMBER default 0 not null
)
;
create index T_DESCRIPTION_NODE_ID on T_DESCRIPTION (NODE_ID);
create index T_DESCRIPTION_VINDEX on T_DESCRIPTION (VINDEX);

prompt
prompt Creating table WEEKLY_COUNT
prompt ===========================
prompt
create table WEEKLY_COUNT
(
  SUMVALUE    NUMBER default 1 not null,
  NAME        VARCHAR2(128) not null,
  ACCESS_WEEK NUMBER not null,
  ACCESS_YEAR NUMBER not null
)
;
comment on column WEEKLY_COUNT.SUMVALUE
  is '计数值';
comment on column WEEKLY_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
comment on column WEEKLY_COUNT.ACCESS_WEEK
  is '以年计算的周';
comment on column WEEKLY_COUNT.ACCESS_YEAR
  is '年份';
create index I_WEEKLY_ACCESS_WEEK on WEEKLY_COUNT (ACCESS_WEEK);
create index I_WEEKLY_ACCESS_YEAR on WEEKLY_COUNT (ACCESS_YEAR);
create index I_WEEKLY_NAME on WEEKLY_COUNT (NAME);
create index I_WEEKLY_SUMVALUE on WEEKLY_COUNT (SUMVALUE);

prompt
prompt Creating table YEARLY_COUNT
prompt ===========================
prompt
create table YEARLY_COUNT
(
  SUMVALUE    NUMBER default 1 not null,
  NAME        VARCHAR2(128) not null,
  ACCESS_YEAR NUMBER not null
)
;
comment on column YEARLY_COUNT.SUMVALUE
  is '计数值';
comment on column YEARLY_COUNT.NAME
  is '可以是系统名称，也可以是对象的ID';
comment on column YEARLY_COUNT.ACCESS_YEAR
  is '年份';
create index I_YEARLY_ACCESS_YEAR on YEARLY_COUNT (ACCESS_YEAR);
create index I_YEARLY_NAME on YEARLY_COUNT (NAME);
create index I_YEARLY_SUMVALUE on YEARLY_COUNT (SUMVALUE);

prompt
prompt Creating view NODE_DATE
prompt =======================
prompt
create or replace view node_date as
Select n."PKID",n."DEFINITION_ID",n."PARENT_ID",n."NAME",n."ORDERNO",n."STRING_INDEX",n."CLOB_INDEX",d."NODE_ID",d."PROPERTY_ID",d."VALUE",d."VINDEX" From node n,Date_Value d Where n.pkid =d.node_id(+)
/

prompt
prompt Creating view NODE_DATE_DEF
prompt ===========================
prompt
create or replace view node_date_def as
Select n."PKID",n."DEFINITION_ID",n."PARENT_ID",n."NAME",n."ORDERNO",n."STRING_INDEX",n."CLOB_INDEX",d."NODE_ID",d."PROPERTY_ID",d."VALUE",d."VINDEX",t.definition,t.Type From node n,Date_Value d ,node_type t Where n.pkid =d.node_id(+) And n.pkid=t.pkid
/

prompt
prompt Creating procedure PRO_COPY_NODE2
prompt =================================
prompt
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
/

prompt
prompt Creating procedure PRO_COPY_NODEDEFINITION
prompt ==========================================
prompt
create or replace procedure pro_copy_NodeDefinition(sourceID Varchar2, destID Varchar2) Is

Begin

Insert Into nodedef_processdef (node_definition,process_definition)
Select destID ,process_definition
From nodedef_processdef x
Where x.node_definition =sourceID;

Insert Into node_definition_authorization (principal_id,principal_type,definition_id,action)
Select principal_id,principal_type,destID,action
From node_definition_authorization x
Where x.definition_id =sourceID;


Insert Into node_view (nodefid, prodefid, width, orderno)
Select destID,prodefid,width,orderno
From node_view x
Where x.nodefid =sourceID;

end pro_copy_NodeDefinition;
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
v_sql varchar2(200);
Cursor cur Is Select n.pkid From node n Start With n.pkid =ID Connect By Prior n.pkid = n.parent_id;
CURSOR cur_tablenames IS SELECT TNAME FROM TAB where tname like '_\_%' escape '\';
v_tablename varchar2(30);
begin
    open cur;
    fetch cur into cur_nodeid;
    while cur%found loop
       --delete from node_authorization where node_id=cur_nodeid;
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
prompt Creating procedure PRO_INSERT_NODEDEFINITION
prompt ============================================
prompt
create or replace procedure PRO_INSERT_NODEDEFINITION(PKID varchar2, DESCRIPTION Char,IS_SYSTEM Char, DELETE_MARK Char,CREATE_TIME Date,Name varchar2,SUPER_ID Varchar2) is
Begin

Insert Into node_definition t (t.pkid,t.delete_mark,t.description,t.is_system,t.create_time,t.Name) Values (PKID, DELETE_MARK,DESCRIPTION,IS_SYSTEM, CREATE_TIME,NAME);
--Insert Into node_definition_super b (b.definition_id,b.super_id,b.Type) Values (PKID,SUPER_ID,'1');

end PRO_INSERT_NODEDEFINITION;
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
prompt Creating procedure PRO_UPDATE_NODEDEFINITION
prompt ============================================
prompt
create or replace procedure PRO_UPDATE_NODEDEFINITION(pro_PKID        VARCHAR2,
                                                      pro_DESCRIPTION VARCHAR2,
                                                      pro_IS_SYSTEM   CHAR,
                                                      pro_DELETE_MARK CHAR,
                                                      pro_name        Varchar2,
                                                      pro_SUPER_ID    VARCHAR2) is
begin

  update NODE_DEFINITION T
     set T.DESCRIPTION = pro_DESCRIPTION,
         T.IS_SYSTEM   = pro_IS_SYSTEM,
         T.DELETE_MARK = pro_DELETE_MARK,
         T.name        = pro_name
   where T.PKID = pro_PKID;
   commit;
  --update NODE_DEFINITION_SUPER T
   --  set T.SUPER_ID = pro_SUPER_ID
  -- where T.DEFINITION_ID = pro_PKID
   --  And T.Type = '1';
    --    commit;

end PRO_UPDATE_NODEDEFINITION;
/


declare job number;
begin
  sys.dbms_job.submit( job,
                      'pro_count_new();',
                       Sysdate,
                       'TRUNC(sysdate) + 1 +1/ (24)');
  commit;
end;
/

insert into node (pkid, parent_id, name ) values ('2500486344911', '1000000000000', 'site');
insert into node_type (pkid , definition , type) values ('2500486344911' , 'site-scheme' , '1');
insert into s_domain (node_id , value ,vindex , length) values ('2500486344911', '%localhost%', '0' , '0');
insert into s_displayname (node_id , value , vindex , length) values ('2500486344911', '系统管理员的网站', '0' , '0');
insert into s_templates (node_id , value , vindex , length) values ('2500486344911', 'template001', '0' , '0');
insert into s_navigatetemplates (node_id , value , vindex , length) values ('2500486344911', 'template001', '0' , '0');
commit;

@coolink_database1.0.1.sql

spool off;
spool dbInstalled.log;
spool off;
quit;