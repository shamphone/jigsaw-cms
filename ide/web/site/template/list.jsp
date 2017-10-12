<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板列表</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/site" page="/style/manage.css"/>"/>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitelist.js"/>"></script>
<style type="text/css">
	a {text-decoration:underline;}
</style>
<script type="text/javascript">
window.onload = function(){
	ListTable.Init(document.getElementById("listTable"));
}
</script>
</head>
<body style="border: 0px; margin: 0px; padding: 0px;">
<div id="oListPanel" style="height: expression(document.body.clientHeight-25);">
<table id="listTable" width="100%" cellpadding="2" cellspacing="0">
	<thead>
	<tr>
		<th width="20"><input type="checkbox" id="chkAll"></th>
		<th width="20">&nbsp;</th>
		<th width="70">名称</th>
		<th width="200">显示名称</th>
		<th width="150">分辨率</th>
		<th width="270">网站数</th>
		</tr>
	</thead>
	<tbody id="templateList">
		<logic:iterate id="template" name="templates" length="20" indexId="id">
		<logic:notEqual value="default" name="template" property="name">
			<tr  id='<bean:write name="template" property="ID"/>' siteCount='<bean:write name="template" property="siteCount"/>' templateName='<bean:write name="template" property="name"/>' displayName='<bean:write name="template" property="displayName"/>' resolution='<bean:write name="template" property="resolution"/>'>
				<td width="20"><input id="<bean:write name="template" property="ID"/>" type="checkbox" name="cb" value='<bean:write name="template" property="ID"/>'></td>
				<td><%= (id.intValue()+1) %></td>
				<td>
					<bean:write name="template" property="name" />&nbsp;
				</td>
				<td title="<bean:write name="template" property="displayName" />">
					<bean:write name="template" property="displayName" />&nbsp;
				</td>
				<td title="<logic:notEmpty name="template" property="resolution"><bean:write name="template" property="resolution" /></logic:notEmpty><logic:empty name="template" property="resolution">default</logic:empty>">
					<logic:notEmpty name="template" property="resolution">
						<bean:write name="template" property="resolution" />
					</logic:notEmpty>
					<logic:empty name="template" property="resolution">
						default
					</logic:empty>
				</td>
				<td title="<bean:write name="template" property="siteCount" />">
					<bean:write name="template" property="siteCount" />
				</td>
			</tr>
			</logic:notEqual>
		</logic:iterate>
	</tbody>
</table>
</div>
</body>
</html>

