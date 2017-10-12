<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>第二步：选择本地分类</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/xtree/xtree.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/xtree.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/cmslocale.js"></script>
<script language="Javascript" type="text/Javascript" >
CMSTree.basePath = '<html:rewrite page="/" module="/cms" />';
CMSTree.rootDefinitionID = 'no-properties-scheme';
function doInit(){
	CMSTree.loadTree(oTree);
}
</script>
<base target="_self" />
</head>
<body onload="doInit()">
<table width="100%" cellpadding="2" cellspacing="0" border="0">
	<tr>
		<td>
		<div id="oTree" class="insetDiv" style="height:350px;"></div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="operation">
		<button onclick="CMSTree_Prev()" id="btnPrev" >上一步</button>		
		<button onclick="CMSTree_Next()" id="btnNext" disabled="disabled">下一步</button>
		<button onclick="window.close();" id="btnCancel">取消</button>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
