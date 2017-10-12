<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择远程分类</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/cmsremote.js"></script>
<script language="Javascript" type="text/Javascript" >
var tree ;
function doInit(){
	tree = new CMSTree(window.dialogArguments);
	document.getElementById("oTree").innerHTML = tree.toString();
}
</script>
</head>
<body onload="doInit()">
<table width="100%" cellpadding="2" cellspacing="0" border="0">
	<tr>
		<td colspan="2">
		<div id="oTree" class="insetDiv" style="height:250px;"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div class="operation">
		<button  id="btnOK" disabled="disabled" onclick="CMSTree_OK()">确定</button>
		<button  id="btnCancel" onclick="window.close();">取消</button>
		</div>
		</td>
	</tr>	
</table>
</body>
</html>
