<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件发送</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css" />
<base target="_self" />
<script language="Javascript" type="text/Javascript"
	src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript"
	src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript"
	src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript"
	src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript"
	src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	var def = window.dialogArguments.definitionID;
	function addPropertyDefinition(definitionID,oSelect) {
		if (definitionID == null) {
			alert("请选择分类！");
			return;
		}
		var arr = CMSDialog.PropertyDefinitionSelector(definitionID, null ,[1,3,4,5]);
		if (arr != null) {
			var newOption = document.createElement("option");
			newOption.value = arr.ID;
			newOption.type = arr.type;
			newOption.text = arr.name;
			oSelect.add(newOption);
		}
	}
	function removePropertyDefinition(definitionID,oSelect){
		deleteOption(oSelect);
	}
	//上移   
   function upperShift(obj)   
　　{　   
　　　　　　for(var i=1; i < obj.length; i++)   
　　　　　　{
	//最上面的一个不需要移动，所以直接从i=1开始   
　　　　　　　　if(obj.options[i].selected)   
　　　　　　　　{   
　　　　　　　　　　if(!obj.options.item(i-1).selected)   
　　　　　　　　　　{   
　　　　　　　　　　　　var selText = obj.options[i].text;   
　　　　　　　　　　　　var selValue = obj.options[i].value;   
                       obj.options[i].text = obj.options[i-1].text;   
                        obj.options[i].value = obj.options[i-1].value;   
                        obj.options[i].selected = false;   
                        obj.options[i-1].text = selText;   
                        obj.options[i-1].value = selValue;   
                        obj.options[i-1].selected=true;   
　　　　　　　　　　}   
　　　　　　　　}   
　　　　　　}   
　　　　}   
  
        //下移   
        function lowerShift(obj)   
　　　　{   
　　　　　　for(var i = obj.length -2 ; i >= 0; i--)   
　　　　　　{//向下移动，最后一个不需要处理，所以直接从倒数第二个开始   
　　　　　　　if(obj.options[i].selected)   
　　　　　　　{   
　　　　　　　　　　if(!obj.options[i+1].selected)   
　　　　　　　　　　{   
　　　　　　　　　　　　var selText = obj.options[i].text;   
　　　　　　　　　　　　var selValue = obj.options[i].value;   
           			  obj.options[i].text = obj.options[i+1].text;   
            		obj.options[i].value = obj.options[i+1].value;   
           obj.options[i].selected = false;   
         obj.options[i+1].text = selText;   
	            obj.options[i+1].value = selValue;   
            obj.options[i+1].selected=true;   
	　　　　　　　　　　}   
	　　　　　　　　}   
	　　　　　　}   
	　　　　}   
	function doInit() {
		config.populateForm(configForm);
		var properties = config.getValues("properties");
		if(properties!=null){
			for(var i=0;i<properties.length;i++){
				var opt = document.createElement("option");
				opt.value = properties[i];
				opt.text = CMSDialog.GetPropertyDefinitionName(def, properties[i]);
				configForm.elements('properties').options.add(opt);
			}
		}
	}
	function ok() {
		if(document.all("server").value == ""){
			alert("请填写邮箱服务器地址！");
			return;
		}
		if(document.all("from").value == ""){
			alert("请填写发件人邮箱地址！");
			return;
		}
		if(document.all("mailPassword").value == ""){
			alert("请填写发件人邮箱密码！");
			return;
		}
		if(document.all("emailPropID").value == ""){
			alert("请填写收件人邮箱地址！");
			return;
		}
		selectAll(configForm.elements('properties'));
		config.updateFromForm(configForm);
		config.setValue("emailPropID",document.all("emailPropID").value);
		config.setValue("content",document.all("content").value);
		config.setValue("server",document.all("server").value);
		config.setValue("from",document.all("from").value);
		config.setValue("mailPassword",document.all("mailPassword").value);
		config.setValue("title",document.all("title").value);
		config.setValue("address",document.all("address").value);
		window.returnValue = config;
		window.close();
	}
	function selectProperty(categoryID,objID,objName){
		if(categoryID==null){
			alert("请选择分类！");
			return;
			}
		    var result=CMSDialog.PropertyDefinitionSelector(categoryID,null,[1]);
		    if(result!=null){
		    	objID.value=result.ID;
		    	objName.value=result.name;
		      }
	}
	function selectCategory(objID,objName){
		  var definition=CMSDialog.NodeDefinitionSelector();
			if(definition!=null){
		   if(definition.length>0){
		       for(var j=0;j<definition.length;j++){
		    	   objID.value=definition[j].ID;
		    	   objName.value=definition[j].name;
		       }
		     }
		  }
		}
	function showLink(checkBox){
		if(checkBox.checked){
			document.getElementById("link").style.display = "";
		}else{
			document.getElementById("link").style.display = "none";
		}
	}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="pannelDiv" valign="top"><select id="pannelSelect"
			name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
			<option value="0">邮件服务器设置</option>
			<option value="1">替换信息设置</option>
			<option value="2">邮件内容设置</option>
		</select></td>
		<td id="tdFieldsets" valign="top" align="center">
		<form name="configForm">
		<fieldset class="fieldPanel">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<td>邮件服务器地址
				<td><input type="text" name="server" /></td>
			</tr>
			<tr>
				<td>发件人邮箱地址</td>
				<td><input type="text" name="from" /></td>
			</tr>
			<tr>
				<td>发件人邮箱密码</td>
				<td><input type="text" name="mailPassword" /></td>
			</tr>
			<tr>
				<td>收件人邮箱地址</td>
				<td><input type="hidden" name="emailPropID" /><input
					type="text" name="emailPropName" readonly="readonly" /></td>
				<td>
				<button id="btnDest"
					onclick="selectProperty(def,document.getElementById('emailPropID'),document.getElementById('emailPropName'))">选择...</button>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldPanel" style="display: none">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">属性</td>
				<td>
				<table width="241" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="150"><select multiple="multiple" name="properties"
							size="10" style="width: 150px;">
						</select></td>
						<td width="91" valign="top"></br>
						</br>
						<button class="commonbut"
							onclick="addPropertyDefinition(def,form.elements('properties'))">添加</button>
						</br>
						<button class="commonbut"
							onclick="removePropertyDefinition(def,form.elements('properties'))">删除</button>
						</br>
						<button class="commonbut" onclick="upperShift(form.elements('properties'))">上移</button>
						</br>
				        <button class="commonbut" onclick="lowerShift(form.elements('properties'))">下移</button>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td colspan="2"><br><font color="red">说明：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.内容中需要抽取的信息按替换信息设置中设置的顺序从1开始。在文中插入如'$1'则为设置中第一个属性值。<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.若需在内容中插入可点击的链接则插入'$link'并勾选'添加链接',将地址填入输入框,链接中相应参数亦可用配置参数代替。<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.当前时间用'$date'表示<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.加密前密码用'$password'表示</font>
			</td></tr>
		</table>
		</fieldset>
		<fieldset class="fieldPanel" style="display: none">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<td>邮件标题：</td>
				<td COLSPAN="2"><input type="text" name="title">
				</td>
			</tr>
			<tr>
				<td>邮件内容：</td>
				<td COLSPAN="2"><textarea cols="30" rows="10" name="content"></textarea></td>
			</tr><!--
			<tr>
				<td></td>
				<td><input type="checkbox" name="ifLink"
					onclick="showLink(this)" />添加链接</td>
			</tr>
			--><tr id="link">
				<td>链接地址:</td>
				<td><input type="text" name="address" width="50"/></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="hidden" name="password" /><input
					type="text" name="passwordName" readonly="readonly" /></td>
				<td>
				<button id="btnDest"
					onclick="selectProperty(def,document.getElementById('password'),document.getElementById('passwordName'))">选择...</button>
				</td>
			</tr>
		</table>
		</fieldset>
		</form>
		<div id="toolbar">
		<button onclick="ok()" id="btnOK">确定</button>
		<button onclick="window.close()" id="btnCancel">取消</button>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
