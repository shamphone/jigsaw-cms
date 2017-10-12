<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">复制栏目</tiles:put>
	<tiles:put name="javascript">
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
	<script type="text/javascript">
		var oChannel = window.dialogArguments.oChannel;
		var siteFactory = window.dialogArguments.siteFactory;
		var oTemplate = oChannel.template;
		var siteFolder = oChannel.getFolder();
		
		function initPage(){
			document.getElementById("name").value = oChannel.name;
			document.getElementById("name").select();
			document.getElementById("displayName").value = oChannel.displayName;
			document.getElementById("parentDisplayName").value = siteFolder.displayName;
		}

		window.onload = initPage;

		function selectFolder(oDisplayName){
			var folder = SiteDialog.selectFolder(oTemplate,siteFactory);
			if(folder!=null){
				oDisplayName.value = folder.displayName;
				siteFolder = folder;
				oTemplate = folder.template;
			}
		}

		function validateChannel(name) {
			var newChannel = oTemplate.getChannel(siteFolder.contextPath+"/"+name+"."+oChannel.getSuffix());
			return !newChannel.exists();
		}
		
		function validate() {
			var name = document.getElementById("name").value = document.getElementById("name").value.trim();
			if(!validateName(name)){
				alert("名称只能包含字母、数字和下划线！");
				return false;
			}
			if(!validateLength(name,4,33)){
				alert("名称长度必须在4和32之间！");
				return false;
			}
			if(!validateChannel(name)){
				alert("该页面已存在!");
				return false;
			}
			var displayName = document.getElementById("displayName").value;
			if(!validateRequired(displayName)){
				alert("显示名称不能为空！");
				return false;
			}
			if(!validateLength(displayName,1,33)){
				alert("显示名称长度必须在1和32之间！");
				return false;
			}
			return true;
		}
		
		function doOK(){
			disableButton();
			if(validate()){
				var ret = new Object();
				ret.siteFolder = siteFolder;
				ret.name = document.getElementById("name").value;
				ret.displayName = document.getElementById("displayName").value;
				window.returnValue = ret;
				window.close();
			}else{
				enableButton();
			}
		}
	</script>
 	</tiles:put>
	<tiles:put name="dialog">
	  	<form>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td class="pannelDiv">
					<td valign="top">
					<table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
						<tr>
							<td nowrap="nowrap">页面名称</td>
							<td><input type="text"  id="name" name="name" style="IME-MODE: disabled;width: 194px;"/></td>
						</tr>
						<tr>
							<td nowrap="nowrap">显示名称</td>
							<td><input type="text" style="width: 194px;" id="displayName" name="displayName"  title="在页面以及客户端浏览器上显示的页面名称" /></td>
						</tr>
						<tr>
							<td>父文件夹</td>
							<td>
								<input type="text" style="width: 128px;" value=""  name="parentDisplayName" id="parentDisplayName" readonly="readonly"/>
								<input type="button" class="btnMore"  onclick="selectFolder(document.getElementById('parentDisplayName'))" value="选择...">
							</td>
						</tr>
					</table>
					<div class="operation">
						<button id="btnOk" class="commonbut" onclick="doOK()">确定</button>
						<button id="btnCancel" class="commonbut" onclick="window.close();">取消</button>
					</div>
					</td>
				</tr>
			</table>
		</form>
	</tiles:put>
</tiles:insert>
