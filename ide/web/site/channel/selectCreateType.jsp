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
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>		
		<style>
			select {width: 150px;height: 100px;margin-left: 40px;margin-top: 20px;}
		</style>
		<script type="text/javascript">
		window.onload = function() {
			document.getElementById("advanced1").checked = true;
		}
		function ok() {
			var oFrom = document.getElementsByName("oForm")[0];
			var advance = GetRadioValue(oFrom.elements["advanced"]);
			var param = new Array();
			param.advance = advance;
			window.returnValue = param;
			window.close();
			return true;
		}
		function showDescription(id){
			var description = "";
			switch (id) {
			case "advanced1":
				description = "创建一个空的栏目";
				break;
			case "advanced2":
				description = "从一个本地模板页面中导入，该模板页面应该是使用coolink搭建的页面";
				break;
			case "advanced4":
				description = "从url地址复制";
				break;
			}
			document.getElementById("description").innerHTML = description;
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
				<legend>请选择创建方式：</legend>
				<div style="margin-top: 18px;">
				<form name="oForm">
				<table border="0" cellpadding="2" cellspacing="2" align="center">
					<tr>
						<td noWrap="nowrap"><input type="radio" onpropertychange="showDescription(this.id)" id="advanced1"
							name="advanced" value="blank" /><label for="advanced1">使用空白模板</label>
						</td>
					</tr>
					<tr>
						<td noWrap="nowrap"><input type="radio" onpropertychange="showDescription(this.id)"  Id="advanced2"
							name="advanced" value="template" /> <label for="advanced2">从模板页面中导入</label>
						</td>
					</tr>
					<tr>
						<td noWrap="nowrap"><input type="radio" onpropertychange="showDescription(this.id)"  Id="advanced4"
							name="advanced" value="url" /> <label for="advanced4">从URL地址复制&nbsp;&nbsp;</label>
						</td>
					</tr>
				</table>
				</form>
				</div>
				</fieldset>
				<fieldset style="padding: 5px 5px 5px 5px; height: 200px;">
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

