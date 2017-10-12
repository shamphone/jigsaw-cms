<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
	<head>
		<title>上传文件</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="expires" content="0">
	    <style type="text/css">
	      body, a, table, div, span, td, th, input, select{font:9pt;font-family: Verdana, Arial, Helvetica, sans-serif;}
	      body {padding-left:15px; background-color:#ECE9D8;}
	      .buttonBar {padding-top:10px; text-align:right; }
	    </style>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
		<script type="text/javascript" language="javascript">
			function validate($form) {
				if ($form("file").value)
					return true;
				alert("请选择要上传的文件");
			}
			function callback($path) {
				if (!$path) {
					alert("上传失败");
					return;
				}
				window.returnValue = "true";
				window.close();
			}
	    </script>
	</head>
	<body>
		<form action="uploadResource.do" enctype="multipart/form-data" method="post" target="hideFrame" onsubmit="return validate(this);">
		<table cellpadding=0 cellspacing=0 width="100%" height="100%">
			<tr>
				<td>
					<fieldset>
						<legend>上传文件</legend>
						目标文件:&nbsp;&nbsp;<input type="file" name="file" size="30"/>
					</fieldset>
					<div class="buttonBar">
						<button type="submit">上 传</button>
						<button onclick="window.close()">取 消</button>
					</div>
				</td>
			</tr>
		</table>
		</form>
		<iframe name="hideFrame" style="display:none"></iframe>
	</body>
</html>
