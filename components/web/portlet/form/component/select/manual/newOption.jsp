<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="../style/dialog.jsp" />
</head>
<body>

<table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
	<form>
	<tr>
		<td nowrap="nowrap">值：</td>
		<td><input name="key" onkeyup="this.form.text.value=this.value"/></td>
	</tr>
	<tr>
		<td nowrap="nowrap">名称：</td>
		<td><input name="text" /></td>
	</tr>
	<tr>
		<td></td>
		<td>
		<button type="button" onClick="ok(this.form)" type="button" tabindex="0">确定</button>
		</td>
	</tr>
	</form>
</table>

<script type="text/Javascript" language="Javascript">
	function ok(oForm) {
		oOption = new Object();
		oOption.value = oForm.key.value;
		oOption.text = oForm.text.value;

		window.parent.returnValue = oOption;
		window.parent.close();
	}
</script>
</body>
</html>