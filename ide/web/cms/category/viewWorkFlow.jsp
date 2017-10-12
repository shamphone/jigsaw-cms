<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>工作流说明</title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/style.css"/>">
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/stat.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/area.jsp"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/date.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/industry.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/sortControl.js"/>"></script>
  </head>

  <body>
    <div style="height:370px;margin:20px;0px;20px;0px;overflow:scroll;">
      <logic:iterate id="definition" name="definitions">
        <div id="<bean:write name="definition" property="id"/>" style="margin:10px;0px;10px;20px">
          <strong><bean:write name="definition" property="name"/>：</strong>
          <div style="margin:10px;0px;10px;10px;white-space:sWrap;">
            <bean:write name="definition" property="description" />
          </div>
        </div>
      </logic:iterate>
    </div>
    <div class="operation">
      <button type="button" onclick="window.close()" class="commonbut" id="close">关闭</button>
    </div>
  </body>
  <script language="JavaScript" type="text/Javascript">
  <logic:present name="ID">
  var ID = '<bean:write name="ID"/>';
  var oDiv = document.getElementById(ID);
  if(oDiv!=null){
    oDiv.scrollIntoView(true);
    //oDiv.style.fontStyle="italic";
    //oDiv.style.fontSize="18";
    oDiv.style.backgroundColor = "silver";
  }
  </logic:present>
  </script>
</html>
