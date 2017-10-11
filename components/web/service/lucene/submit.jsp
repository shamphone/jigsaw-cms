<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<base target="_self" />
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	function doInit() {
		config.populateForm(configForm);
		ok();
	}
	function ok() {
		config.updateFromForm(configForm);
		window.returnValue = config;
		window.close();
	}
</script>
</head>
<body onload="doInit()">
			<form name="configForm">
			</form>
			<div id="toolbar">
			    <button type="button" onclick="ok()" id="btnOK">确定</button>
			</div>
</body>
</html>
