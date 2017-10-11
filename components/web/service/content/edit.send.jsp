<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置发送服务属性</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="styles.css" />
<link rel="stylesheet" type="text/css" href="/ide/common/style/dialog.jsp" />
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/objects.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/mappingtable.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript">
var config = window.dialogArguments.parameters;
var loadMapping = true;
var mapping=null;
function doInit(){
	config.populateForm(configForm);
	var comp =configForm.elements("actualRemoteURL");
	if(comp.value.length == 0) 
		comp.value = "http://" ;
	if(!document.all){
		document.getElementById("toolbar").style.top = document.body.clientHeight-40;
		document.getElementById("toolbar").style.left = 105;
		document.getElementById("toolbar").style.textAlign = "center";
		document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
		document.body.style.overflow = "hidden"
	}
};
function doConnect(){
	var ret = Remote.testURL(document.getElementById('actualRemoteURL').value);
	if(ret!=null){
		document.getElementById("selectDefinition").disabled = false;
		document.getElementById("coolinkVersion").value = ret.coolinkVersion;
	}
};
//兼容老版本的coolink
function doSelectDefinition(){
	var remoteURL = document.getElementById("actualRemoteURL").value;
	var coolinkVersion = document.getElementById("coolinkVersion").value;
	if(coolinkVersion!="1.0"){
		remoteURL += "/ide";
	}
	var ret = CMSDialog.RemoteNodeDefinitionSelector(remoteURL);
	if(ret!=null){
		document.getElementById("remoteCategoryID").value = ret.ID;
		document.getElementById("remoteCategoryName").value = ret.name;	
		loadMapping = true;			
	}		
};

Service.OnPanelSelect = function(index){
	if(index!=1)
		return;
	if(document.getElementById("remoteCategoryID").value.length ==0)
		return;
	if(document.getElementById("coolinkVersion").value.length ==0)
		return;
	if(!loadMapping)
		return;
	var remoteURL = document.getElementById("actualRemoteURL").value;
	if(document.getElementById("coolinkVersion").value!="1.0"){
		remoteURL += "/ide";
	}
	var remoteRepository = new Repository(remoteURL);
	var definition = remoteRepository.getNodeDefinition(document.getElementById("remoteCategoryID").value);	
	var repository = new Repository();
	var localDefinition = repository.getNodeDefinition(window.dialogArguments.definitionID);	
	mapping = new MappingTable(definition,localDefinition,config.getValues("mapping"));
	/**
	 * luobin modified in 2009-03-17
	 * bug5285
	 * 修改目的：重新选择远程分类后，但属性映射匹配时依然是用第一次选择的远程分类。
	 * 修改描述：先去掉之前已经生成的mappingTable
	 */
	if(document.getElementById("mappingTable")){
		document.getElementById("mappingContainer").removeChild(document.getElementById("mappingTable"));
	}
	document.getElementById("mappingContainer").appendChild(mapping.render("mappingTable"));
	loadMapping = false;
	
};
function ok(){
	document.getElementById("remoteURL").value = document.getElementById("actualRemoteURL").value;
	if(mapping)
		mapping.formatMappings();
	config.updateFromForm(configForm);
	window.returnValue = config;
	window.close();  
}
</script>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
					<option value="0" selected="selected">基本信息</option>
					<option value="1">属性映射</option>
			</select></td>
			<td id="tdFieldsets" valign="top" align="center"><form name="configForm"><fieldset class="fieldPanel">
		<table width="100%" cellpadding="2" cellspacing="0" border="0">
		<input type="hidden" value='<fulong:rewriteURL page="/" module="" />' name='localURL' id='localURL'/>
			<tr>
				<th>远程地址</th>
				<td>
				<input type="hidden" id="coolinkVersion" name="coolinkVersion" id="coolinkVersion">
				<input type="hidden" id="remoteURL" name="remoteURL" id="remoteURL">
				<input type="text" size="30" id="actualRemoteURL" name="actualRemoteURL" name="actualRemoteURL" onchange="loadMapping=true"/></td>
				<td><button type="button" onclick="doConnect()">连接...</button></td>
			</tr>
			<tr>			
				<th>远程分类</th><input type="hidden" size="40" name="remoteCategoryID" id="remoteCategoryID" />
				<td><input type="text" size="30" name="remoteCategoryName" id="remoteCategoryName" /></td>
				<td><button type="button" onclick="doSelectDefinition()" id="selectDefinition" disabled="disabled">选择...</button></td>
			</tr>
		</table></fieldset><fieldset class="fieldPanel">
		<table width="320px" cellspacing="0" cellpadding="2" border="0">		
		<thead><tr><th width="40%">远程分类属性：</th><th>映射到本地属性/值：</th></tr></thead>		
		</table>
		<div class="insetDiv" style="width:98%;height:260px;padding:0px;margin:0px;" id="mappingContainer"></div>
		<div style="padding:3px 3px 3px 3px"><input type="button" value="自动匹配" onclick="mapping.autoMatch()"/></div>
		</fieldset></form>
		<div id="toolbar">
            <button type="button" onclick="ok(this.form)">保存</button>
	    	<button type="button" onclick="window.close()">取消</button>
          </div>
		</td>
	</tr>
</table>
</body>
</html>