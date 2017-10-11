<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置接收服务属性</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="styles.css" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/objects.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/mappingtable.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript">
var config = window.dialogArguments.parameters;
var loadMapping = true;
var mapping=null;
function doInit(){
	config.populateForm(configForm);
	var comp =configForm.elements["remoteURL"];
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
	var ret = Remote.testURL(document.getElementById('remoteURL').value);
	if(ret!=null)
		document.getElementById("selectDefinition").disabled = false;

};
function doSelectDefinition(){	
	var ret = CMSDialog.RemoteNodeDefinitionSelector(document.getElementById("remoteURL").value);
	if(ret!=null){
		document.getElementById("remoteCategoryID").value = ret.ID;
		document.getElementById("remoteCategoryName").value = ret.name;	
		loadMapping = true;			
	}		
};

function autoMatch(){
	if(mapping){
		mapping.autoMatch()
	}
}

Service.OnPanelSelect = function(index){
	if(index!=1)
		return;
	if(document.getElementById("remoteCategoryID").value.length ==0)
		return;
	if(!loadMapping)
		return;
	
	var repository = new Repository('<html:rewrite page="/" module=""/>');
	var definition = repository.getRemoteNodeDefinition(configForm.elements("remoteURL").value,document.getElementById("remoteCategoryID").value);	
	var localDefinition = repository.getNodeDefinition(window.dialogArguments.definitionID);	
	mapping = new MappingTable(localDefinition,definition,config.getValues("mapping"));
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
				<td><input type="text" size="30" name="remoteURL" id="remoteURL" onchange="loadMapping=true"/></td>
				<td><button type="button" onclick="doConnect()">连接...</button></td>
			</tr>
			<tr>			
				<th>远程分类</th><input type="hidden" size="40" name="remoteCategoryID" id="remoteCategoryID" />
				<td><input type="text" size="30" name="remoteCategoryName" id="remoteCategoryName" /></td>
				<td><button type="button" onclick="doSelectDefinition()" id="selectDefinition" disabled="disabled">选择...</button></td>
			</tr>
		</table></fieldset><fieldset class="fieldPanel">
		<table width="320px" cellspacing="0" cellpadding="2" border="0">		
		<thead><tr><th width="40%">本地分类属性：</th><th>映射到远程属性/值：</th></tr></thead>		
		</table>
		<div class="insetDiv" style="width:98%;height:180;height:260px\9;padding:0px;margin:0px;" id="mappingContainer"></div>
		<div style="padding:3px 3px 3px 3px"><input type="button" value="自动匹配" onclick="autoMatch()"/></div>
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