<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script language="Javascript" type="text/Javascript" src="../tree.jsp"></script>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" type="text/css" href="tree.css">
    <title>资源管理</title>
</head>
<body>
    <div id="leftTree">
      <div id="topImage"></div>
      <div id="tree">
        <fulong:tree name="folderTree" objectId="afolder">
          <a class="close" target="folderFrame" href="folderItems.do?folder=<fulong:encode name="afolder" property="path"/>">
          <logic:equal value="sites" name="afolder" property="name">资源库</logic:equal>
          <logic:notEqual value="sites" name="afolder" property="name"><bean:write name="afolder" property="name"/></logic:notEqual>
          </a>
        </fulong:tree>
      </div>
    </div>
    <div id="rightFrame">
      <iframe name="folderFrame" frameborder="0" scrolling="no" id="folderFrame" src="folderItems.do">
      </iframe>
    </div>
</body>
</html>
