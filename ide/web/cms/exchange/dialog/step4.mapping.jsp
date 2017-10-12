<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>第三步：设置属性映射</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/cmsmapping.js"></script>
<base target="_self" />

</head>
<body>
<table width="100%" cellpadding="2" cellspacing="0" border="0">
	<tr>
		<td><input type="radio" value="auto" id="auto" name="mapping" onclick="btnNext.disabled=true;btnFinished.disabled=false"/><label for="auto">自动映射</label> </td>
	</tr>
	<tr>
		<td><input type="radio" value="exist" id="create" name="mapping"  onclick="btnNext.disabled=false;btnFinished.disabled=true"/><label for="create">新映射</label></td>
	</tr>
	<tr>
		<td><input type="radio" value="exist" id="exist" name="mapping" onclick="btnNext.disabled=true;btnFinished.disabled=true"/><label for="exist">使用现有映射</label> </td>
	</tr>
	<tr>
		<td><select id="mappings" multiple="multiple" size="15" style="margin-left:30px;width:400px;"></select> </td>
	</tr>
</table>
	<div class="operation">
		<button onclick="CMSTree_Prev()" id="btnPrev" >上一步</button>		
		<button onclick="CMSTree_Next()" id="btnNext" disabled="disabled">下一步</button>
		<button onclick="CMSTree_Next()" id="btnFinished" disabled="disabled">完成</button>
		<button onclick="window.close();" class="commonbut">取消</button>
		</div>
</body>
</html>
