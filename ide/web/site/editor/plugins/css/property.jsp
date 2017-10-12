<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改脚本属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/channelsource.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="property.js"></script>
<base target="_self" />
<style type="text/css">
	input {width: 225px}
</style>
</head>
<body onload="doInit()">
    <form>
       <table width="100%" border="0" cellpadding="4" cellspacing="0">
			<tr>
				<td width="180" valign="middle"><html:img
					page="/images/paddingleft.jpg" module="/common"
					style="border:2px inset"></html:img></td>
				<td valign="bottom">
				   <table border="0"  cellpadding="2" cellspacing="2" align="center" width="100%">
			       	  <tr>
				          <td width="80px" >栏目名称</td>
				          <td>
				            <input type="text" readonly="readonly" name="name" style="IME-MODE: disabled" id="name" maxlength="32" title="系统中的ID及文件名称，全模板唯一。"/>
				          </td>
			          </tr>
			          <tr>
				          <td >显示名称</td>
				          <td>
				            <input type="text" id="displayName" title="系统中的展示名称，管理页面的时候看到的是这个。"/>
				          </td>
			          </tr>
			      </table>
				<fieldset style="padding: 5px 5px 5px 5px; height: 200px;">
				<legend>说明</legend>
				<div id="description">栏目显示名称只有在发布后才能生效</div>
				</fieldset>
				</td>
			</tr>
		</table>
      <div class="operation">
        <button id="btnOk" onclick="doOK()" >确定</button>
        <button id="btnCancel" onclick="window.close()">取消</button>
      </div>
    </form>
</body>
</html>


