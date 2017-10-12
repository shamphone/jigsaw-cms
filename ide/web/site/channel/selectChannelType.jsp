<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">选择栏目类型</tiles:put>
  	<tiles:put name="javascript">
  		<meta http-equiv="pragma" content="no-cache">
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
	    <style type="text/css">
	    	div.Grid {
				width: 100px;
				height: 30px;
				float: left;
				text-align: left;
				padding-left: 25px;
			}
	    </style>
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script type="text/javascript">
			window.onload = function() {
				document.getElementById("index").checked = true;
			}
			function ok() {
				var oChannelTypes = document.getElementsByName("advanced");
				var advance = GetRadioValue(oChannelTypes);
				window.returnValue = advance;
				window.close();
			}
			function showDescription(displayName,postfix){
				var oDescription = document.getElementById("description");
				if(postfix!="folder"){
					oDescription.innerHTML = displayName + "类型栏目是后缀为"+postfix+"的页面";
				}else{
					oDescription.innerHTML = "创建一个文件夹";
				}
			}
		</script>
  	</tiles:put>
  	<tiles:put name="dialog">
		<table width="100%" border="0" cellpadding="4" cellspacing="0">
			<tr>
				<td width="180" valign="middle"><html:img
					page="/images/paddingleft.jpg" module="/common"
					style="border:2px inset"></html:img></td>
				<td valign="bottom">
				<fieldset style="padding: 5px 5px 5px 5px; height: 144px;">
				<legend>请选择栏目类型：</legend>
				<div style="margin-top: 8px;">
							<div class="Grid">
								<input type="radio"  id="folder" name="advanced" value="folder" onpropertychange="showDescription('文件夹','folder')" /> 
								<label for="folder">文件夹</label>
							</div>
					<logic:iterate id="type" name="channelTypes">
							<div class="Grid">
								<input type="radio" onpropertychange="showDescription('<bean:write name="type" property="displayName"/>','<bean:write name="type" property="postfix"/>')" id="<bean:write name="type" property="name"/>" name="advanced" value="<bean:write name="type" property="name"/>" /> 
								<label for="<bean:write name="type" property="name"/>"><bean:write name="type" property="displayName"/></label>
							</div>
					</logic:iterate>
				</div>
				</fieldset>
				<fieldset style="padding: 5px 5px 5px 5px; height: 180px;">
				<legend>说明</legend>
				<div id="description"></div>
				</fieldset>
				</td>
			</tr>
		</table>
		<div class="operation">
		<button id="btnOk" type="button" onclick="ok()">下一步</button>
		<button id="btnCancel" type="button" onclick="window.close()">取消</button>
		</div>
	</tiles:put>
</tiles:insert>
