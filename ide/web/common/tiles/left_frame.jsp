<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base target="list" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/xtree/xtree.css"/>"/>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/xtree.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/left.jsp"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
    <tiles:getAsString name="javascript" ignore="true"/>
    <script language="Javascript" type="text/Javascript" >
    function init(){
       // LeftPanel.Init(<tiles:getAsString name="selected" ignore="true"/>);
    }
    </script>
  </head>
  <body onload="init()" scroll="no">
  <div id="navigatorTable"><tiles:getAsString name="tree"/></div>
  <!-- div style="padding-right:10px;margin:0,2px,0,2px;text-align:right;" class="moduleName"><a onclick="window.parent.location='<html:rewrite page='/security/j_spring_security_logout' module=''/>'"><html:img height="14" width="18" module="/common" page="/images/exit.gif"/>登出</a></div-->
  </body>
</html>
