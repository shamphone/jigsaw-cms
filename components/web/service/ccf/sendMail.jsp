<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>邮件自动发送</title>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/service" page="/styles.css"/>">
<script type="text/javascript" language="javascript">
		var config = window.dialogArguments.parameters;
		function doInit(){
			config.populateForm(configForm);
		}
		function ok(){
		       var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		       if(!reg.test(document.getElementsByName("from")[0].value)){
			       alert("请输入正确的邮箱地址");
		       } else {
				config.updateFromForm(configForm);
				window.returnValue = config;
				window.close();
		       }   
		}
		function isEmail(from){
		       var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		       if(!reg.test(from.value)){
			       alert("请输入正确的邮箱地址");
		       }
		       
		}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
					<option value="0">基本信息</option>
			</select></td>
			<td id="tdFieldsets" valign="top" align="center">
			<form name="configForm">
			<fieldset class="fieldPanel">
			<div
				style="height: 200px;">
				<table>
			<tr>
			<td>寄件人<td>
			<td>
			    <input type="text" name="from" size="37" onblur="isEmail(this)"/>
				</td>
			</tr>
			<tr>
			<td>密码<td>
			<td>
			    <input type="text" name="password" size="37"/>
				</td>
			</tr>
			<tr>
			<td>邮件服务器<td>
			<td>
			    <input type="text" name="server" size="37"/>
				</td>
			</tr>
			<tr>
			<td>主题<td>
			<td>
				<input type="text" name="subject" size="37"/>
				</td>
			</tr>
			<tr>
			<td>内容页地址<td>
			<td>
				<input type="text" name="path" size="37"/>
				</td>
			</tr>
			<tr>
			<td>内容<td>
			<td>
				<textarea  name="text" rows="4" cols="25"></textarea>
				</td>
			</tr>
			</table>
				</div>
			</fieldset>
			</form>
			<div id="toolbar">
		    <button type="button" onclick="ok()" id="btnOK">确定</button>
		    <button type="button" onclick="window.close()" id="btnCancel">取消</button>
			</div>
			</td>
		</tr>
</table>
</body>
</html>
