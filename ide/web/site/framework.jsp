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
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <link type="text/css" rel="stylesheet" href="<html:rewrite  module="/site" page='/style/selector.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<html:rewrite  module="/site" page='/style/tree.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/style.css"/>">
    <script type="text/javascript" src="<html:rewrite page="/script/common.js" module="/common"/>"></script>
    <script type="text/javascript" src="<html:rewrite page="/tree.js" module="/site"/>"></script>
  </head>
  <body>
    <tiles:getAsString name="content"/>
  </body>
</html>
