<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>保存成功</title>
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/message.css"/>"/>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/list.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
        <!-- script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ContentFrame.js"/>"></script-->
    <script type="text/Javascript" language="Javascript">
    var ret=new Object();
    ret.ID = '<bean:write name="group" property="ID" ignore="true"/>';
    ret.name = '<cms:node name="group" propertyName="title" ignore="true"/>';
	    if(document.all){
	    window.returnValue=ret;
	    	window.close();
	    }else{
	    window.parent.returnValue=ret;
	    	window.parent.close();
	    }
    //不能用这个脚本了，因为是新弹出的页面，不能按原来的页面框架来传递信息了。
  //window.opener.top.document.frames('list').location.reload();
    </script>
    </head>
    <body oncontextmenu="return false">
    <div id="messageBox" style="text-align:center;margin-top:10px">
  <p align="center"><font size="3">内容保存成功</font></p>
    </div>
    </body>
</html>
