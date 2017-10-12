<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html>
	<head>
		<title>Windows Internet Explorer</title>
		<script Language='Javascript' src='<html:rewrite page="/script/common.js" module="/common"/>' type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
			window.onload = function() {
				var args = window.dialogArguments;
				document.getElementById("question").innerHTML = args.question;
				document.getElementById("btnOk").innerHTML = args.yesText;
				document.getElementById("btnCancel").innerHTML = args.noText;
				_AdjustDialog();
			}
			
			function _AdjustDialog(){
				var version = isVersion();
				if (version == 'ie6') {
					var w = tabDialogSize.offsetWidth + 200;
					var h = tabDialogSize.offsetHeight + 25;
				} else {
					var w = tabDialogSize.offsetWidth + 16;
					var h = tabDialogSize.offsetHeight + 25;
				}
				/*
				if(config.IsSP2){
					h += 20;
				}*/
				window.dialogWidth = w + "px";
				window.dialogHeight = h + "px";
				window.dialogLeft = (screen.availWidth - w) / 2;
				window.dialogTop = (screen.availHeight - h) / 2;
			}

			function _Close($retValue) {
				window.returnValue = $retValue;
				window.close();
			}
		</script>
		<style type="text/css">
			body {background-color:#ECE9D8; margin:0px; padding:0px;overflow: hidden; }
			#tabDialogSize {font-size:9pt; }
			button {font-size:9pt; height:21px;width:76px;}
			.iconBar {padding-top:10px; padding-left:10px; }
			.buttonBar {padding-top:10px;padding-left:20px; }
			#question {padding-top:10px;}
		</style>
	</head>
	<body>
		<table id="tabDialogSize">
			<tr valign="top">
				<td width="50" align="left" class="iconBar">
					<img src="images/question.gif">
				</td>
				<td valign="center">
					<div id="question"></div>
				</td>
			</tr>
		</table>
		<center>
		<div class="buttonBar">
			<button type="button" class="commonbut" id="btnOk" onclick="_Close(true)">确定</button>&nbsp;
			<button type="button" class="commonbut" id="btnCancel" onclick="_Close(false)">取消</button>
		</div>
		</center>
	</body>
</html>
