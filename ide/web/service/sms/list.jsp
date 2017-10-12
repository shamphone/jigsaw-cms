<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务列表</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript" src="svconfig.js"></script>
<script type="text/javascript" src="classes/svlist.js"></script>
</head>
<body onload="SVList.init(document.getElementById('listTable'))"><div id="mainBody"><table width="100%" cellpadding="0" id="listTable" cellspacing="0">
<thead>
	<tr>	
		<th width="152" scope="col">ID</th>
		<th scope="col">名称</th>
		<th width="191" scope="col">实现类</th>
		<th width="60" scope="col">状态</th>		
	</tr>
	</thead>
	<tbody>
	<logic:iterate id="serviceConfig" name="category"  property="serviceConfigs" indexId="index">
	<bean:define id="cid" name="serviceConfig" property="id" type="String" />	 
	<bean:define id='manager' name='serviceManager' type="com.fulong.service.ServiceManager"></bean:define>
		<tr onclick="SVList_Click(this)" onmouseover="SVList_MouseOver(this)" onmouseout="SVList_MouseOut(this)" id='<bean:write name="serviceConfig" property="id" ignore="true" />'>
			<td align="left"><bean:write name="serviceConfig" property="id" ignore="true" /></td>
			<td align="left"><bean:write name="serviceConfig" property="name" ignore="true" /></td>
			<td align="left"><bean:write name="serviceConfig" property="className" ignore="true" /></td>
			<td align="left"><%try{ %>
			<bean:define id="cstate" name="manager" property='<%= "service("+cid+").state" %>'/>
			<bean:message  key='<%= "Service.SMS.State."+cstate %>' /> <% }catch(Exception ex){ %>
			<span style="color:red">出错</span>
			<%} %> 
			</td>
		</tr>
	</logic:iterate></tbody>
</table></div>
</body>
</html>
