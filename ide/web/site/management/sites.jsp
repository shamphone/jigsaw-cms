<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>管理网站</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate">
	    <link rel="stylesheet" type="text/css" media="all" href='<html:rewrite module="/common" page="/calendar/skins/aqua/theme.css"/>' title="Aqua" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/site" page="/style/manage.css"/>"/>
		<script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/calendar.js"/>'></script>
		<script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/lang/cn_utf8.js"/>'></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="sites.js"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitelist.js"/>"></script>	
        <script language="Javascript" type="text/Javascript" >
 	        window.onload = function(){
 	 	        document.getElementById("oListPanel").style.height = document.body.clientHeight-27;
 	            if(document.getElementById("listTable")!=null){
	 	       		ListTable.Init(document.getElementById("listTable"));
 	        	}
	      	}
			function validate(oForm){
				var sBeginDate = oForm("beginDate").value;
				var sEndDate = oForm("endDate").value;
				if(sBeginDate.length>0&&sEndDate.length>0){
					if(sBeginDate>sEndDate){
						alert("起始时间不能大于结束时间");
						return false;
					}
				}
				return true;
			}
	      	 
        </script>
	</head>
	<body style="border: 0px; margin: 0px; padding: 0px;background: white;">
		<div id="oListPanel">
		<table  id="listTable"  align="center" cellpadding="2px" cellspacing="0px">
			<thead>
				<tr>
					<th width="20px"><input type="checkbox" id="chkAll"></th>
					<th width="16px">&nbsp;</th>
					<th width="140px">网站名称</th>
					<th width="140px">域名</th>
					<th width="140px">建站机构</th>
					<th width="140px">使用模板</th>
				</tr>
			</thead>
			<tbody id="siteList">
				<logic:iterate id="site" name="sites" length="20" indexId="index">
					<tr id="<bean:write name="site" property="ID" ignore="true"/>">
						<td align="center">
							<input type="checkbox" id="checkID"/>
						</td>
						<td align="center"><%= (1+index.intValue()) %></td>
						<td id="displayName_<bean:write name="site" property="ID" ignore="true"/>" title="<cms:node name="site" propertyName="displayName" ignore="true"/>">
							<cms:node name="site" propertyName="displayName" ignore="true"/>&nbsp;
						</td>
						<td title="<cms:node name="site" propertyName="domain" ignore="true"/>"><cms:node name="site" propertyName="domain" ignore="true"/></td>
						<td title="<cms:node name="site" property="parent" propertyName="user-commonname" ignore="true" />"><logic:present name="site" property="parent">
							<cms:node name="site" property="parent" propertyName="user-commonname" ignore="true" />
						</logic:present>&nbsp;</td>
						<td id='<bean:write name="site" property="ID" ignore="true"/>'>
						<logic:iterate name="site" property="nodeProperty(templates).values" id="templateName">
						<logic:present name='factory' property='<%= "template("+templateName+")" %>'>
							<bean:write name='factory' property='<%= "template("+templateName+").displayName" %>' ignore="true"/>&nbsp;&nbsp;
							</logic:present>
						</logic:iterate>&nbsp;&nbsp;
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</table>
		</div>
		<div id="gotobar"><fulong:pager pattern="goto" target="_self"/></div>
	</body>
</html>
