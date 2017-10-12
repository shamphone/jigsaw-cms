<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>活动属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="../style.css" />
<script language="Javascript" type="text/Javascript" src="../script.js"></script>
<script language="Javascript" type="text/Javascript">
	var PMEditor = parent.frames["mainFrame"].PMEditor;
	var activity = PMEditor.selectedObject;
	window.onload = function() {
		actForm["ID"].value = activity.getId();
		actForm["name"].value = activity.getName();
		actForm["description"].value = activity.getDescription();
	};
	function updateName(name) {
		activity.setName(name);
		//parent.frames["leftFrame"].PMTree.Tree.getSelected().setTitle(name);
	}
	function updateDesc(desc) {
		activity.setDescription(desc);
	}
</script>
</head>
<body>
<table cellpadding="2" cellspacing="0" border="0">
	<form name="actForm">
	<tr>
		<td valign="top">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<th>活动ID：</th>
			</tr>
			<tr>
				<td><input type="text" name="ID" size="25" readonly="readonly" disabled="disabled" /></td>
			</tr>
			<tr>
				<th>活动名称：</th>
			</tr>
			<tr>
				<td><input type="text" name="name" size="25" onblur="updateName(this.value)" /></td>
			</tr>
		</table>
		</td>
		<td valign="top">
		<table  cellpadding="2" cellspacing="0" border="0">
			<tr>
				<th>活动描述：</th>
			</tr>
			<tr>
				<td><textarea rows="4" cols="40" name="description" onblur="updateDesc(this.value)"></textarea></td>
			</tr>
		</table>
		</td>
	</tr>

	</form>
</table>
</body>
</html>