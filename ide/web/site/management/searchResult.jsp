<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>

<%@page import="com.fulong.longcon.site.SiteFactory"%>
<%@page import="com.fulong.longcon.repository.Value"%>
<%@page import="com.fulong.longcon.repository.Node"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache, must-revalidate">
		<meta http-equiv="expires" content="0"> 
		<title>搜索结果</title>
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="searchResult.js"></script>
	</head>
	<body>
		<div class="contentBlock">
		<table id="listTable" align="center" cellpadding="0px" cellspacing="0px">
			<thead>
				<tr>
					<th width="26px"><input type="checkbox" onclick="_OnCheckboxAllClick(this)" id="chkAll"></th>
					<th width="24px">&nbsp;</th>
					<th width="140px">网站名称</th>
					<th width="140px">域名</th>
					<th>建站机构</th>
					<th width="140px">使用模板</th>
				</tr>
			</thead>
			<tbody>
				<logic:iterate id="site" name="sites" indexId="index">
					<script type="text/javascript" language="javascript">
		             var site=new Object();
		             site.ID = '<bean:write name="site" property="ID" ignore="true"/>';
		             site.displayName = '<cms:node name="site" propertyName="displayName" ignore="true"/>';
		             site.domain = '<cms:node name="site" propertyName="domain" ignore="true"/>';
		             sites[site.ID] = site;
          			</script>
					<tr id="<bean:write name="site" property="ID" ignore="true"/>" onclick="_OnRowClick(this)">
						<td>
							<input onclick="_OnCheckboxClick(this)" id='chk<bean:write name="index"/>' type="checkbox" value="<bean:write name="site" property="ID" ignore="true"/>" name="sites" />
							<label for='chk<bean:write name="index"/>' style="display:none; "></label>
						</td>
						<td style="text-align:right; padding-right:2px;"><%= (1+index.intValue()) %></td>
						<td class="ellipsis" title="<cms:node name="site" propertyName="displayName" ignore="true"/>"><cms:node name="site" propertyName="displayName" ignore="true"/>&nbsp;</td>
						<td class="ellipsis" title="<cms:node name="site" propertyName="domain" ignore="true"/>"><cms:node name="site" propertyName="domain" ignore="true"/></td>
						<td><logic:present name="site" property="parent">
							<cms:node name="site" property="parent" propertyName="user-commonname" ignore="true" />
						</logic:present>&nbsp;</td>
						<td id='<bean:write name="site" property="ID" ignore="true"/>'>
							<%
							Value[] templateNames = ((Node)site).getProperty("templates").getValues();
							SiteFactory factory = (SiteFactory)request.getAttribute("factory");
							for(com.fulong.longcon.repository.Value value:templateNames){
								String tName = value.getString();
								String displayName = factory.getTemplate(tName).getDisplayName();%>
								<%= displayName%>&nbsp;&nbsp;
							<%}%>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</table>
		</div>
		<div id="gotobar"><fulong:pager pattern="goto" target="_self"/></div>
	</body>
</html>