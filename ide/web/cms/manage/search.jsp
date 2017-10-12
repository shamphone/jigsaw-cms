<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>搜索内容</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate">
		<link rel="stylesheet" type="text/css" href='<html:rewrite module="/common" page="/style/contents.jsp"/>' />
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script language="Javascript" type="text/javascript" src="../classes/cmsdialog.js"></script>
		<script language="Javascript" type="text/Javascript" src="classes/cmssearch.js"></script>
	
	</head>
	<body  style="border: 0px; margin: 0px; padding: 0px;background-color:buttonface">
		<html:form action="/search.do" method="get" target="list">
			<input type="hidden" id="definitionID" name="definitionID" />
			<table align="center" cellspacing="2" cellspadding="2" border="0" width="100%">
				<tr>
					<td style="width:265px;width:240px\9" align="left" valign="top">
					<fieldset style="height: 130px"><legend>搜索条件</legend>
					<table align="center" cellspacing="2" cellspadding="2" border="0" width="100%">
						<tr valign="top">
							<td><select id="filterColumns" name="filters" size="6" style="width: 180px" multiple="multiple" /></td>
							<td>
								<div><button type="button" onclick="newFilter(this.form.elements['filters'])">添加</button></div>
								<div><button type="button" onclick="delFilterColumns(this.form.elements['filters'])";>删除</button></div>
							</td>
						</tr>
					</table>
					</fieldset>
					</td>
					<td width="176px" align="left" valign="top">
					<fieldset style="height:130px;"><legend>排序设置</legend>
					<table align="center" cellspacing="2" cellspadding="2" border="0">
						<tr>
							<td>
								<select name="orderBy1" style="width: 100px;">
									<option oldIndex="1" value="1"></option>
									<logic:iterate id="allp" name="properties" indexId="index">
									<logic:notEqual name="allp" property="type" value="0">
									<logic:notEqual name="allp" property="type" value="10">
									<logic:notEqual name="allp" property="type" value="6">
									<logic:notEqual name="allp" property="type" value="9">
									<logic:notEqual name="allp" property="type" value="2">
									<logic:notEqual name="allp" property="type" value="8">
									<logic:equal name="allp" property="multiple" value="false">
									<option oldIndex="<bean:write name='index'/>" value="<bean:write name='allp' property='ID'/>"><bean:write name="allp" property="name" /></option>
									</logic:equal>
									</logic:notEqual></logic:notEqual></logic:notEqual></logic:notEqual></logic:notEqual></logic:notEqual>
									</logic:iterate>
								</select>
							</td>
							<td>
								<select name="asc1">
									<option oldIndex="1" value="0">升序</option>
									<option oldIndex="2" value="1">降序</option>
								</select>
							</td>
						</tr>				
					</table>
					</fieldset>
					</td>
					<td style="width:265px;width:240px\9;" align="left" valign="top">
					<fieldset style="height: 130px"><legend>展示的列</legend>
						<table align="center" cellspacing="2" cellspadding="2" border="0" width="100%">
							<tr valign="top">
								<td>
								<select size="6" style="width: 180px" name="displayColumns" id="displayColumns" multiple="multiple">
									<logic:iterate id="allp" name="properties" indexId="index">
									<logic:notEqual name="allp" property="type" value="10"> <!--liulei modified in 2010-1-12 10,0 prop will not be displayed any more-->
									<logic:notEqual name="allp" property="type" value="0"> 
									<logic:equal name="allp" property="multiple" value="false">
									<option oldIndex="<bean:write name='index'/>" value="<bean:write name='allp' property='ID'/>"><bean:write name="allp" property="name" /></option>
									</logic:equal></logic:notEqual></logic:notEqual></logic:iterate>								
								</select></td>
								<td>
									<div><button type="button"onclick="moveUp(this)">上移</button></div>
									<div><button type="button"onclick="moveDown(this)">下移</button></div>
								</td>
							</tr>
						</table>
					</fieldset>
					</td>
					<td>
						<fieldset style="height: 130px"><legend></legend>
							<table cellspacing="2" cellspadding="2" border="0" width="100%" height="86%">
								<tr>
									<td>
										<div><button type="button"onclick="searchNode(this.form);">执行搜索</button></div>
										<div><button type="button"onclick="clearCol(this.form);">重新搜索</button></div>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
