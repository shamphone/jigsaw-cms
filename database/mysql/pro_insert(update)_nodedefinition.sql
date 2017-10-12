# USE `coolinkclean_new`;

#
# Source for table node_definition_super
#

DROP TABLE IF EXISTS `node_definition_super`;
CREATE TABLE `node_definition_super` (
  `definition_id` varchar(128) DEFAULT NULL COMMENT '内容分类ID',
  `super_id` varchar(128) DEFAULT NULL COMMENT '父分类ID',
  `type` char(1) NOT NULL DEFAULT '0' COMMENT '是否是主父分类.1:主父分类；0：辅父分类'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Source for procedure pro_update_NodeDefinition
#

DROP PROCEDURE IF EXISTS `pro_update_NodeDefinition`;
CREATE DEFINER=`root`@`%` PROCEDURE `pro_update_NodeDefinition`(
  in pro_PKID varchar(128), 
  in pro_DESCRIPTION varchar(512),
  in pro_IS_SYSTEM Char(1), 
  in pro_DELETE_MARK Char(1), 
  in pro_name varchar(512), 
  in pro_SUPER_ID Varchar(128)
)
Begin
 update NODE_DEFINITION 
     set DESCRIPTION = pro_DESCRIPTION,
         IS_SYSTEM   = pro_IS_SYSTEM,
         DELETE_MARK = pro_DELETE_MARK,
         name        = pro_name
   where PKID = pro_PKID;

 update NODE_DEFINITION_SUPER 
     set SUPER_ID = pro_SUPER_ID
  where DEFINITION_ID = pro_PKID
     And Type = '1';
end;

#
# Source for procedure pro_insert_NodeDefinition
#

DROP PROCEDURE IF EXISTS `pro_insert_NodeDefinition`;
CREATE DEFINER=`root`@`%` PROCEDURE `pro_insert_NodeDefinition`(
  in PKID varchar(128), 
  in DESCRIPTION varchar(512),
  in IS_SYSTEM Char(1), 
  in DELETE_MARK Char(1), 
  in CREATE_TIME datetime,  
  in Name varchar(512), 
  in SUPER_ID Varchar(128)
)
Begin
Insert Into node_definition (pkid,delete_mark,description,is_system,create_time,Name) 
Values (PKID, DELETE_MARK,DESCRIPTION,IS_SYSTEM, CREATE_TIME,NAME);
Insert Into node_definition_super  (definition_id,super_id,Type) 
Values (PKID,SUPER_ID,'1');
end;