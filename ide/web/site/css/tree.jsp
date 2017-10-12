<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong" %>
<html>
<head>
<title><bean:message key="product.name" bundle="common"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<html:rewrite module="" page='/styles/style.css'/>">
<script type="" language="JavaScript" src="<html:rewrite module="" page='/scripts/script.js'/>"></script>
<base target="main"/>
</head>
<body class="LeftBody">
<div id="LeftTop"><input type="button" class="commonbut" value="展开"/><input type="button" class="commonbut" value="闭合"/></div>
<ul>
  <li class="openNode"><a href="<html:rewrite module="/site/css" page='/main.do'/>">样式库</a>
  <ul><logic:iterate id="css" name="csses">
      <li><a href="<html:rewrite module="/site/css" page='/css.do'/>?id=<fulong:encode name='css' property='contextPath'/>"><bean:write name="css" property="name"/></a></li>
</logic:iterate></ul>
</li>
</ul>
</div></body></html>
