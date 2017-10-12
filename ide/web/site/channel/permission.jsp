<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">页面权限设置</tiles:put>
	<tiles:put name="javascript">
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smchannel.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smtemplate.js"/>"></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smfolder.js"/>"></script>
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/channeltree.js"/>"></script>
		<script type="text/javascript">
			webFXTreeConfig.style = "checkbox";
			var oTemplate = window.dialogArguments;
			function _ChooseGroup($valueControl, $textControl) {
				var groups = CMSDialog.NodeDefinitionSelector("principal-scheme");
				if (!groups||groups.length==null||groups.length==0)
					return;
				document.forms[0].reset();
				$valueControl.value = groups[0].ID;
				$textControl.value = groups[0].name;
				var url = "getPermissibleChannels.do?groupId=" + $valueControl.value + "&templateId=" + document.forms[0].all("templateId").value;
				var request = new HttpRequest(url);
				var channelIds = request.Get();
				if (!channelIds)
					return;
				var ids = channelIds.split(",");
				var boxes = document.forms[0].all("channelIds");
				for (var i=0; i<boxes.length; i++) {
					if (ids.contains(boxes[i].value))
						boxes[i].checked = true;
				}
			}
			function validate($form) {
				if (!$form("principalName").value) {
					alert("用户/组不能为空");
					return false;
				}
				return true;
			}
			function doInit(){
		    	var channelTree = new CHANNELTree(oTemplate);
		    	document.getElementById("channelTree").innerHTML = channelTree.toString();
		    	channelTree.expand();
		    };
		    window.onload = function(){
		          top.document.title= window.dialogArguments.name + "-"+window.document.title;
		          doInit();
	        }
		    function CHANNELTree_OnClick(path){
		    }
		</script>
	</tiles:put>
	<tiles:put name="dialog">
		<form action="savePermission.do" onsubmit="return validate(this)">
		<table width="100%" cellpadding="2" cellspacing="2" border="0">
			<tr>
				<td nowrap="nowrap">用户/组:</td>
				<td>
					<input name="principalId" type="hidden" />
					<input name="principalName" size="38" readonly="true"/>
					<button onclick="_ChooseGroup(this.form('principalId'), this.form('principalName'))">选择...</button>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" colspan="2" style="background:url(<html:rewrite module="/common" page="/xtree/images/new2.gif"/>) no-repeat 61px 2px;">
					授权页面&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表示用户必需登入才可访问)
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<div id="channelTree" style="width: 100%; height: 260px; background-color: #ffffff; border: 2px inset; overflow: scroll;">
				</div>
				</td>
			</tr>
		</table>
		<div class="operation">
			<button type="submit" class="commonbut" id="btnOk">确定</button>
			<button onclick="window.close()" class="commonbut" id="back">取消</button>
		</div>
		</form>
	</tiles:put>
</tiles:insert>