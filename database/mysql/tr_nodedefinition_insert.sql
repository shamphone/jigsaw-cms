# USE `coolinkclean_new`;

#
# Source for table node_quota
#

DROP TABLE IF EXISTS `node_quota`;
CREATE TABLE `node_quota` (
  `principal_id` varchar(32) NOT NULL DEFAULT '' COMMENT '授权者ID',
  `principal_type` decimal(22,0) DEFAULT NULL COMMENT '项类型，1：用户，2：机构；3：组',
  `node_id` varchar(32) NOT NULL DEFAULT '' COMMENT '节点ID',
  `unit` varchar(16) DEFAULT NULL COMMENT '单位',
  `quota` decimal(22,0) DEFAULT NULL COMMENT '配额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Source for trigger TR_NodeDefinition_INSERT
#

DROP TRIGGER IF EXISTS `TR_NodeDefinition_INSERT`;
CREATE DEFINER='root'@'%' TRIGGER `TR_NodeDefinition_INSERT` AFTER INSERT ON `node_definition`
  FOR EACH ROW begin
  INSERT INTO node_quota
    (principal_id, principal_type, node_id, unit, quota)
  values
    ('100', 0, new.pkid, 'long-term', 5);
end;