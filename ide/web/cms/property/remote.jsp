<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>远程属性选择器</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/pmsremote.js"></script>
<script language="JavaScript" type="text/Javascript">	     
  <logic:present parameter="exclude">
  <bean:parameter id="filterType" name="exclude" multiple="true"/>
    <logic:iterate id="ft" name="filterType">
  	PMS.excludes.push('<bean:write name="ft"/>');
  </logic:iterate>
  </logic:present>
  <logic:present parameter="include">
  <bean:parameter id="leftType" name="include" multiple="true"/>
  <logic:iterate id="lt" name="leftType">
  PMS.includes.push('<bean:write name="lt"/>');
  </logic:iterate>
  </logic:present>
</script>
<script language="JavaScript" type="text/Javascript">       
function doInit(){
	PMS.init('<%= request.getParameter("url")%>','<%= request.getParameter("ID")%>', document.all("definitionTree"));
};
function ok(aForm){         
	window.returnValue=PMS.selectedValue;	
    window.close();
}
</script>
<base target="_self" />
</head>
<body onload="doInit()">
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <div class="insetDiv" style="height:240px" id="definitionTree"></div>
          </td>
        </tr>
        </table>
         <div class="operation">        
          <span style="position:absolute;left:12px"><input type="checkbox" onclick="PMS.enableAll(this.checked)"><span id="OpenOff">全部可选</span></span>
          <button type="button" onclick="ok(this.form)" class="commonbut" id="btnOk">确定</button>
          <button type="button" onclick="window.close()" class="commonbut" id="BtnCancel">取消</button>
        </div>   
  </body>
  </html>
