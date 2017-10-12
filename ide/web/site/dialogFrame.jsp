<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link type="text/css" rel="stylesheet" href="<html:rewrite page="/style/style.css" module="/site"/>"/>
<link type="text/css" rel="stylesheet" href="<html:rewrite page="/style/selector.css" module="/site"/>"/>
<link type="text/css" rel="stylesheet" href="<html:rewrite page="/style/tree.css" module="/site"/>"/>
<script language="javascript" src="<html:rewrite page="/script/common.js" module="/common"/>" type="text/javascript"></script>
<script language="javascript" src="<html:rewrite page="/script/tree.js" module="/common"/>" type="text/javascript"></script>
<title><tiles:getAsString name="title" ignore="true"/></title>
<base target="_self">
</head>
<body>
  <tiles:getAsString name="content"/>
</body>
</html>
