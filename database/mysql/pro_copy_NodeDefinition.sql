# USE `coolinkclean_new`;

#
# Source for table node_view
#

DROP TABLE IF EXISTS `node_view`;
CREATE TABLE `node_view` (
  `nodefid` varchar(128) NOT NULL DEFAULT '' COMMENT '指定的某个大纲的主键',
  `prodefid` varchar(128) NOT NULL DEFAULT '' COMMENT '指定的某个大纲定义下属性定义的主键',
  `width` decimal(22,0) DEFAULT NULL COMMENT '该列显示需要的宽度',
  `orderno` decimal(22,0) DEFAULT NULL COMMENT '该列显示的序号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Source for table nodedef_processdef
#

DROP TABLE IF EXISTS `nodedef_processdef`;
CREATE TABLE `nodedef_processdef` (
  `node_definition` varchar(128) NOT NULL DEFAULT '' COMMENT '大纲的pkid',
  `process_definition` varchar(128) DEFAULT NULL COMMENT '工作流的pkid'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Source for table node_definition_authorization
#

DROP TABLE IF EXISTS `node_definition_authorization`;
CREATE TABLE `node_definition_authorization` (
  `principal_id` varchar(32) NOT NULL DEFAULT '' COMMENT '授权者ID组/用户/机构的ID',
  `principal_type` decimal(22,0) DEFAULT NULL COMMENT '项类型，1：用户，2：机构；3：组授权者类型',
  `definition_id` varchar(128) NOT NULL DEFAULT '' COMMENT '大纲ID',
  `action` varchar(16) NOT NULL DEFAULT '' COMMENT '操作manage：管理read：读create：新增modify：修改delete：删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



#
# Source for procedure pro_copy_NodeDefinition
#

DROP PROCEDURE IF EXISTS `pro_copy_NodeDefinition`;
CREATE DEFINER=`root`@`%` PROCEDURE `pro_copy_NodeDefinition`(
  in sourceID Varchar(256),  
  in destID   Varchar(256)
)
BEGIN
	
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
END;