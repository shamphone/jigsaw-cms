
--以下部分为：创建大纲

insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('im-scheme', '视频会议大纲', '1', 'no-properties-scheme', '视频会议');

insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('im-conference', '会议大纲', '1', 'im-scheme', '会议');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('im-message', '留言大纲', '1', 'im-scheme', '留言');

insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('conferenceMode1', '普通会议大纲', '1', 'im-conference', '普通会议');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('conferenceMode4', '即时会议大纲', '1', 'im-conference', '即时会议');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('conferenceMode5',  '主控会议大纲', '1', 'im-conference', '主控会议');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('conferenceMode3', '咨询会议大纲', '1', 'im-conference', '咨询会议');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('conferenceMode2', '培训会议大纲', '1', 'im-conference', '培训会议');

insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('im-user', '用户大纲', '1', 'principal-scheme', '用户');

insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('group-scheme', '组大纲', '1', 'no-properties-scheme', '组');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('im-group', '公共联系人组大纲', '1', 'group-scheme', '公共联系人组');
insert into node_definition t (PKID, DESCRIPTION, IS_SYSTEM, SUPER_ID, NAME) values ('im-contactGroup', '联系人组大纲', '1', 'group-scheme', '联系人组');

commit;


--以下部分为：在各个大纲中创建属性

--用户大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('email', 'im-user', '邮箱', '0', '-1', '-1', '1', '22');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, NODE_TYPE) values ('contactGroup', 'im-user', '联系人组', '0', '-1', '-1', '0', '23', 'im-contactGroup', 'im-contactGroup');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('message', 'im-user', '留言', '0', '-1', '-1', '9', '24', 'im-message');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('firstName', 'im-user', '名字', '0', '-1', '-1', '1', '25');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('lastName', 'im-user', '姓', '0', '-1', '-1', '1', '26');

--组大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('name', 'group-scheme', '名称', '0', '-1', '-1', '1', '1');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('members', 'group-scheme', '成员', '0', '-1', '-1', '9', '2', 'im-user');

--公共联系人组大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('desc', 'im-group', '描述', '0', '-1', '-1', '1', '6');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('creatorId', 'im-group', '创建者', '0', '-1', '-1', '9', '7', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('managerId', 'im-group', '管理员', '0', '-1', '-1', '9', '8', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, NODE_TYPE) values ('subGroup', 'im-group', '子组', '0', '-1', '-1', '0', '9', 'im-group', 'im-group');

--联系人组大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, NODE_TYPE) values ('subGroup', 'im-contactGroup', '子联系人组', '0', '-1', '-1', '0', '13', 'im-contactGroup', 'im-contactGroup');

--留言大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('senderId', 'im-message', '发送者', '0', '-1', '-1', '9', '4', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('title', 'im-message', '留言标题', '0', '-1', '-1', '1', '5');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('content', 'im-message', '留言内容', '0', '-1', '-1', '1', '6');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('saveDate', 'im-message', '保存日期', '0', '-1', '-1', '5', '7');

--会议大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('title', 'im-conference', '标题', '0', '-1', '-1', '1', '1');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('creator', 'im-conference', '创建者', '0', '-1', '-1', '9', '2', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('document', 'im-conference', '文档', '0', '-1', '-1', '8', '3');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('startTime', 'im-commonConference', '开始时间', '0', '-1', '-1', '5', '4');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('endTime', 'im-commonConference', '结束时间', '0', '-1', '-1', '5', '5');

--普通会议大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO) values ('desc', 'conferenceMode1', '描述', '0', '-1', '-1', '1', '6');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('5', 'conferenceMode1', '主持人', '0', '-1', '-1', '9', '7', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, DELETABLE) values ('6', 'conferenceMode1', '参会人员', '0', '-1', '-1', '9', '7', 'im-user', '0');

--即时会议大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('12', 'conferenceMode4', '发起人', '0', '-1', '-1', '9', '7', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, DELETABLE) values ('13', 'conferenceMode4', '参会人', '0', '-1', '-1', '9', '8', 'im-user', '0');

--主控会议大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('14', 'conferenceMode5', '主控人', '0', '-1', '-1', '9', '5', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, DELETABLE) values ('15', 'conferenceMode5', '受控人', '0', '-1', '-1', '9', '6', 'im-user', '0');

--咨询会议大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('10', 'conferenceMode3', '咨询人', '0', '-1', '-1', '9', '5', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, DELETABLE) values ('11', 'conferenceMode3', '咨询人', '0', '-1', '-1', '9', '6', 'im-user', '0');

--培训会议大纲
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('7', 'conferenceMode2', '讲师', '0', '-1', '-1', '9', '6', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE) values ('8', 'conferenceMode2', '助教', '0', '-1', '-1', '9', '7', 'im-user');
insert into property_definition t (PKID, Node_Definition_Id, NAME, MULTIPLE, MIN_LENGTH, MAX_LENGTH, TYPE, ORDERNO, REFERENCE_TYPE, DELETABLE) values ('9', 'conferenceMode2', '学员', '0', '-1', '-1', '9', '8', 'im-user', '0');


--以下部分为：设置各种会议模式中的用户权限

--普通会议模式中的用户权限
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '1', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '2', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '3', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '4', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '7', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '8', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '9', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '10', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '11', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('5', '12', 'conferenceMode1');

insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '1', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '2', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '3', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '4', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '7', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '8', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '9', 'conferenceMode1');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('6', '10', 'conferenceMode1');

--即时会议模式中的用户权限
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '1', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '2', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '3', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '4', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '6', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '7', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '8', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '9', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '10', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '11', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('12', '12', 'conferenceMode4');

insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '1', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '2', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '3', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '4', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '7', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '8', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '9', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '10', 'conferenceMode4');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('13', '11', 'conferenceMode4');

--主控会议模式中的用户权限
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '1', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '2', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '3', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '4', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '5', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '6', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '7', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '8', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '9', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '10', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '11', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '12', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('14', '13', 'conferenceMode5');

insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('15', '1', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('15', '2', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('15', '3', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('15', '4', 'conferenceMode5');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('15', '14', 'conferenceMode5');

--咨询会议模式中的用户权限
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '1', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '2', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '3', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '4', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '7', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '8', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '9', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '10', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('10', '11', 'conferenceMode3');

insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '1', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '2', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '3', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '4', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '7', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '8', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '9', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '10', 'conferenceMode3');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('11', '11', 'conferenceMode3');

--培训会议模式中的用户权限
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '1', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '2', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '3', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '4', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '6', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '7', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '8', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '9', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '10', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '11', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('7', '12', 'conferenceMode2');

insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '1', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '2', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '3', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '4', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '6', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '7', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '8', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '9', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '10', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '11', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('8', '12', 'conferenceMode2');

insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('9', '2', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('9', '4', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('9', '5', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('9', '8', 'conferenceMode2');
insert into property_constraints t (PROPERTY_DEFINITION_ID, CONSTRAINT, NODE_ID) values ('9', '10', 'conferenceMode2');


commit;