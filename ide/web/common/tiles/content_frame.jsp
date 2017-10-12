<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><tiles:getAsString name="title" ignore="true"/></title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/content.jsp"/>"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/editor.jsp"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/area.jsp"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/date.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/sortControl.js"/>"></script>
        <!-- script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ContentFrame.js"/>"></script-->
        <tiles:getAsString name="javascript" ignore="true"/>
    </head>
    <body style="text-align:center">
        <tiles:getAsString name="body"/>
    </body>
</html>
