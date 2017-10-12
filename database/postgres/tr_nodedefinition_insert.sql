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

Date: 2010-09-26 15:46:39
*/

-- ----------------------------
-- Table structure for "public"."node_quota"
-- ----------------------------
CREATE TABLE "public"."node_quota" (
"principal_id" varchar(32) DEFAULT NULL::character varying NOT NULL,
"principal_type" numeric DEFAULT NULL,
"node_id" varchar(32) DEFAULT NULL NOT NULL,
"unit" varchar(16) DEFAULT NULL::character varying,
"quota" numeric DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."node_quota" OWNER TO "postgres";

COMMENT ON COLUMN "public"."node_quota"."principal_id" IS '授权者ID';

COMMENT ON COLUMN "public"."node_quota"."principal_type" IS '项类型，1：用户，2：机构；3：组';

COMMENT ON COLUMN "public"."node_quota"."node_id" IS '节点ID';

COMMENT ON COLUMN "public"."node_quota"."unit" IS '单位';

COMMENT ON COLUMN "public"."node_quota"."quota" IS '配额';


-- ----------------------------
-- Function structure for "public"."tr_nodedefinition_insert"
-- ----------------------------
CREATE OR REPLACE FUNCTION "public"."tr_nodedefinition_insert"()
  RETURNS "pg_catalog"."trigger" AS $BODY$BEGIN
INSERT INTO node_quota
    (principal_id, principal_type, node_id, unit, quota)
  values
    ('100', 0, new.pkid, 'long-term', 5);
	RETURN NULL;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

CREATE TRIGGER "tr_nodedefinition_insert" AFTER INSERT ON "public"."node_definition" FOR EACH ROW EXECUTE PROCEDURE "tr_nodedefinition_insert"();