<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns:stedysoft>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="EXPIRES" content="0"/>
<meta http-equiv="Pragma" Content="No-cach" />
<TITLE> 转移属性页 </TITLE>
		
<link rel="stylesheet" type="text/css" href="inc/style.css">
<link rel="stylesheet" type="text/css" href="inc/webTab/webtab.css">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language=Javascript type="text/Javascript" src="inc/function.js"></script>
<script language=Javascript type="text/Javascript" src="inc/shiftlang.js"></script>
<script language=Javascript type="text/Javascript" src="inc/webflow.js.jsp"></script>
<script language=Javascript type="text/Javascript" src="inc/webTab/webTab.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script type="text/Javascript" src='<html:rewrite page="/script/portlet.js" module="/common"/>'></script>

<style>
body {
	background-color: buttonface;
	scroll: no;
	margin: 7px, 0px, 0px, 7px;
	border: none;
	overflow: hidden;	

}
.theTableClass td{
 padding:2px 0px 2px 2px;
}
</style>

<SCRIPT LANGUAGE="JavaScript">
<!--
function iniWindow(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var actionId = getURLParam("transitionID");
   if(actionId==null){
	   actionId = '';
   }
   try{
     if(opener.LANG!='') shiftLanguage(opener.LANG,"actiondialog");

     var FlowXML = opener.document.all.FlowXML;		 

     if(FlowXML.value!=''){
	     iniActionDialog(FlowXML,actionId);
	 }else{
	     alert('打开转移属性对话框时出错！\n\nOpen Action Dialog-Window Error!');
		 window.close();  
	 }	 
   }catch(e){
     //alert('打开转移属性对话框时出错！\n\nOpen Action Dialog-Window Error!');
	 //window.close();
   }   
}
function iniActionDialog(FlowXML,actionId){
   FlowXML = window.dialogArguments.document.all.FlowXML;
   xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
   xmlDoc.async = false;
   xmlDoc.loadXML(FlowXML.value);
   
   var xmlRoot = xmlDoc.documentElement;
   var Actions = xmlRoot.getElementsByTagName("Transitions").item(0);
   var fromStep = '',toStep = '';
   if(actionId!=null&&actionId!=""){
	   for ( var i = 0;i < Actions.childNodes.length;i++ ){
		      Action = Actions.childNodes.item(i);
			  id = Action.getAttribute("Id");
			  if(id==actionId){
			    fromStep = Action.getAttribute("From");
				toStep = Action.getAttribute("To");

				//修改转移时先填充参数
				document.all.ActionId.value = actionId;
		    	document.all.ActionText.value = Action.getAttribute("Name");

		    	//绑定内容分类回显
		    	var categoryID = '';
		    	if(Action.getElementsByTagName("BindingCategory").item(0)!=null){
		    		  categoryID =  Action.getElementsByTagName("BindingCategory").item(0).getAttribute("Id");
		        	  document.all.category.value = Action.getElementsByTagName("BindingCategory").item(0).getAttribute("Id");
		        	  document.all.categoryName.value = Action.getElementsByTagName("BindingCategory").item(0).getAttribute("Name");
		        }

		    	//条件回显
		    	if(Action.getElementsByTagName("Condition").item(0)!=null){
		        	  document.all.transType.value = Action.getElementsByTagName("Condition").item(0).getAttribute("Type");
		        	  var conditions = Action.getElementsByTagName("Condition").item(0).text;
		        	  document.all.condition.value = conditions;
		        	  var conditionStrs = conditions.split(" And ");
		        	  var conditionSelect = document.all.filterPatterns;
		        	  for(var u=0; u<conditionStrs.length; u++){
		        		  var newOption=document.createElement("option");
		        	      newOption.value=conditionStrs[u];
		        		  var req = getXMLHttpRequest();
		      			  var url='<html:rewrite page="/process/visual/getFilterPattern.do?categoryID=" module=""/>'+categoryID+'&pattern='+conditionStrs[u].trim()+'&kongzhi=空值&timeStamp=' + new Date().getTime();
		      		      req.open("POST",encodeURI(url),false);
		      		      req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		      		      req.send(null);
		      		      var data=req.responseText;
		      		      if(data!=null&&data!=""){
		      		      	newOption.text=data;
		      		      }
		      		      conditionSelect.add(newOption);
		        	  }
		        }		        
		    	break;
			  }
		   }
   }else{
	   for ( var i = 0;i < 100;i++ ){
		   var newID = "newaction"+i;
		   if(!existID(Actions,newID)){
			   document.all.ActionId.value = "newaction"+i;
				break;
		   }
	   }
	   for ( var i = 0;i < 100;i++ ){
		   var newName = "新转移"+i;
		   if(!existName(Actions,newName)){
			   document.all.ActionText.value = "新转移"+i;
				break;
		   }
	   }
   }
   

   //生成from，to steplist   
   var Steps = xmlRoot.getElementsByTagName("Activities").item(0);
   var from = document.all.From;
   var to = document.all.To;
   for ( var i = 0;i < Steps.childNodes.length;i++ ) {
      Step = Steps.childNodes.item(i);
	  id = Step.getAttribute("Id");
	  text = Step.getAttribute("Name");

	  fromSelected = fromStep==id?true:false;
	  toSelected = toStep==id?true:false;
	  if(id!='end') addSelectOption(from,text,id,fromSelected);
	  if(id!='begin') addSelectOption(to,text,id,toSelected);	  
   } 
}

function okOnClick(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var actionId = getURLParam("transitionID");
   if(actionId==null){
	   actionId = '';
   }
   try{
     var FlowXML = opener.document.all.FlowXML;

	 xml = getActionXML(FlowXML,actionId);
	 var transitionID = document.all.ActionId.value;
	 var transitionName = document.all.ActionText.value;
	 var fromActID=document.all.From.value;
	 var toActID=document.all.To.value;
	 var condition=document.all.condition.value;
	 var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
	 xmlDoc.async = false;
	 xmlDoc.loadXML(xml);
	 var xmlRoot = xmlDoc.documentElement;
	 var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);
	 var processID = Flow.getAttribute("Id");
	 if(xml!='') {
		   FlowXML.value = xml;
		   window.close();
		 }
   }catch(e){
     //alert('关闭转移属性对话框时出错！\n\nClose Action Dialog-Window Error!');
	 //window.close();
   }   
}
function cancelOnClick(){
   window.close();
}
function applyOnClick(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var actionId = getURLParam("transitionID");
   if(actionId==null){
	   actionId = '';
   }
   try{
     var FlowXML = opener.document.all.FlowXML;

	 xml = getActionXML(FlowXML,actionId);
	 if(xml!='') {
	   FlowXML.value = xml;
	   btnApply.disabled=true;
	 }
   }catch(e){
     alert('应用转移属性时出错！\n\nApply Action Properties Error!');
	 window.close();
   }
}

function getActionXML(FlowXML,actionId){
  id = document.all.ActionId.value;
  text = document.all.ActionText.value;
  actionType = 'PolyLine';  
  from = getSelectValue(document.all.From);
  to = getSelectValue(document.all.To);
  var type = getSelectValue(document.all.transType);
  var condition = document.all.condition.value;
  var bindingCategoryID =  document.all.category.value;
  var bindingCategoryName =  document.all.categoryName.value;
  if(id=='') {alert('请先填写转移编号！\n\nPlease input Action Id!');return '';}
  if(text=='') {alert('请先填写转移名称！\n\nPlease input Action Name!');return '';}
  if(from=='' || to=='') {alert('请先选择起始活动和目的活动！');return '';}
 
  var xml = ""; 
  //生成转移xml
  //xml+= '<Action><BaseProperties id="'+id+'" text="'+text+'" actionType="'+actionType+'" from="'+from+'" to="'+to+'" />';
  //xml+= '<VMLProperties startArrow="'+startArrow+'" endArrow="'+endArrow+'" strokeWeight="'+strokeWeight+'" zIndex="" />';
  //xml+= '<FlowProperties /></Action>';
  xml+= '<Transition From="'+from+'" Id="'+id+'" Name="'+text+'" To="'+to+'">';
  xml+= '<BindingCategory Id="'+bindingCategoryID+'" Name="'+bindingCategoryName+'" />';
  xml+= '<Condition Type="'+type+ '">' + condition + '</Condition></Transition>';
  
  var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;
  xmlDoc.loadXML(FlowXML.value);
  var xmlRoot = xmlDoc.documentElement;
  var Actions = xmlRoot.getElementsByTagName("Transitions").item(0);

  //添加：查找编号冲突的Id
  //修改：查找原来的Id  
  for ( var i = 0;i < Actions.childNodes.length;i++ ) {
      Action = Actions.childNodes.item(i);
	  lId = Action.getAttribute("Id");

	  if(lId==id && actionId=='') {
	    alert('新转移编号已存在！请重新输入！\n\nThis Action Id has exist! Please input non-repeat Action Id!');return '';
	  }
	  if(lId==actionId && actionId!='') {
	    Actions.removeChild(Action);break;
	  }	  
  }

  var xmlDoc2 = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc2.async = false;
  xmlDoc2.loadXML(xml);     
  Actions.appendChild(xmlDoc2.documentElement); 
  
  return xmlRoot.xml;
}

function selectPanel(oSelect){
	var index=oSelect.options[oSelect.selectedIndex].value;
	var fieldsets=document.all.oFieldset;
	for(i=0;i<fieldsets.length;i++){
		fieldsets[i].style.display="none";
	}
	fieldsets[parseInt(index)].style.display="";
}
function setCondition(oSelect,hiddenInput){
	var oOptions = oSelect.options;
	var conditionStr = '';
	if(oOptions.length>1){
		for(i=0;i<oOptions.length-1;i++){
			conditionStr+=oOptions[i].value + ' And ';
		}
		conditionStr+=oOptions[oOptions.length-1].value;
	}else{
		for(i=0;i<oOptions.length;i++){
			conditionStr+=oOptions[i].value;
		}
	}	
	hiddenInput.value = conditionStr;
}
//-->
</SCRIPT> 

</HEAD>

<BODY onload='iniWindow()' onunload=''>
<table border="0" cellpadding="0" cellspacing="0" height=200px>
<tr>
	<td class="pannelDiv" valign="top">
		<select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="15" style="height:230px;background-color: buttonface;">
			<option value="0">基本设置</option>
			<option value="1">条件设置</option>
		</select>
	</td>
	<td id="tdFieldsets" valign="top" align="left">
		<fieldset id="oFieldset" style="" class="fieldPanel" style="width:365px;height:223px;">
			<div style="height: 170px;">
				<table border="0" cellpadding="0" cellspacing="0" class="theTableClass">
					<tr>
						<td width="70px;">转移编号</td>
						<td style="padding:2px 0px 2px 2px"><INPUT TYPE="text" NAME="ActionId" value="newaction" class=txtput></td>
					</tr>
					<tr>
						<td>转移名称</td>
						<td><INPUT TYPE="text" NAME="ActionText" value="" class=txtput></td>
					</tr>
					<tr>
						<td>转移类型</td>
						<td>
							<SELECT NAME="transType" class=txtput style="width:205px">
								<option value="NONE">无条件转移</option>
								<option value="CONDITION">条件转移</option>
								<option value="OTHERWISE">其他</option>
								<option value="EXCEPTION">发生异常时转移</option>
								<option value="DEFAULTEXCEPTION">缺省异常转移</option>
							</SELECT>
						</td>
					</tr>
					<tr>
						<td>起始活动</td>
						<td><SELECT NAME="From" class=txtput style="width:205px"></SELECT></td>
					</tr>
					<tr>
						<td>目的活动</td>
						<td><SELECT NAME="To" class=txtput style="width:205px"></SELECT></td>
					</tr>
				</table>
			</div>
		</fieldset>
		<fieldset id="oFieldset" style="display: none" class="fieldPanel" style="width:365px;height:223px;">
			<div style="height: 200px;">
				<table border="0" cellpadding="0" cellspacing="0" class="theTableClass">
					<tr>
						<td>
							内容分类：<input type="hidden" name="category" id="category" />                  			
						</td>
						<td colspan="2">
							<input type="text" name="categoryName" readonly="readonly" value="" class="txtput"/>
                  			<button class="commonbut" id="searchN" onclick="searchNodeDefinition(document.getElementById('category'),document.getElementById('categoryName'))">选择...</button>
						</td>
					</tr>
					<tr valign="top">
						<td>
							<input type="hidden" name="condition" id="condition"/>
							转移条件：
						</td>
						<td>
							<select multiple="multiple" name="filterPatterns" size="5" style="width:205px;"></select>
						</td>
						<td valign="top">
							<button class="commonbut" onclick="newFilter(document.getElementById('category'),document.getElementById('filterPatterns'));setCondition(document.getElementById('filterPatterns'),document.getElementById('condition'));">添 加</button><br/>
                      		<button class="commonbut" onclick="deleteOption(document.getElementById('filterPatterns'));setCondition(document.getElementById('filterPatterns'),document.getElementById('condition'));">删 除</button>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<div id="toolbar" align="right">
			<button onclick="okOnClick()">确定</button>
			<button onclick="cancelOnClick()">取消</button>
			<!--<input type=button id="btnApply" class=btn value="应 用" onclick="jscript: applyOnClick();">-->
		</div>
	</td>
</tr>
</table>
</BODY>
</HTML>
