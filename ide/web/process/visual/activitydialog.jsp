<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<TITLE> 活动属性 </TITLE>

<link rel="stylesheet" type="text/css" href="../style.css">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language=Javascript type="text/Javascript" src="inc/function.js"></script>
<script language=Javascript type="text/Javascript" src="inc/shiftlang.js"></script>
<script language=Javascript type="text/Javascript" src="inc/webflow.js.jsp"></script>
<script language=Javascript type="text/Javascript" src="inc/webTab/webTab.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>

<style>
body {
	background-color: buttonface;
	scroll: no;
	margin: 7px, 0px, 0px, 7px;
	border: none;
	overflow: hidden;	
	font-size:12;	

}
body,td,select{font:12px Tahoma,Arial,宋体;}
.theTableClass td{
 padding:2px 0px 2px 2px;
}
</style>
<SCRIPT LANGUAGE="JavaScript">
var isAdd = false;
//<!--
function iniWindow(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var stepId = getURLParam("activityID");
   var stepType = getURLParam("type");   
   if(stepId==null){
	   stepId = '';
   }
   try{
	   
     if(opener.LANG!='') shiftLanguage(opener.LANG,"stepdialog");
     
     var FlowXML = opener.document.all.FlowXML;
     if(stepId==''){
         isAdd = true;
	   atNewStep(stepType);	   
	 }else{
	   if(FlowXML.value!=''){
		   isAdd = false;
	     atEditStep(FlowXML,stepId);
	     document.all.StepId.readOnly=true;
	     
	   }else{
	     alert('打开流程属性对话框时出错！\n\nOpen Step Dialog-Window Error!');
		 window.close();  
	   }	   
	 }
     var typeRadio = document.all.activityType;
     if(typeRadio!=null){
	     for(var n=0;n<typeRadio.length;n++){
		     typeRadio[n].disabled=true;
	     }
     }
   }catch(e){
     alert('打开活动属性对话框时出错！\n\nOpen Step Dialog-Window Error!');
	 window.close();
   }
}

function okOnClick(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var stepId = getURLParam("activityID");
   if(stepId==null){
	   stepId = '';
   }
   try{
     var FlowXML = opener.document.all.FlowXML;

	 xml = getStepXML(FlowXML,stepId);
	 var activityID = document.all.StepId.value;
	 var activityName = document.all.StepText.value;
	 var serviceID = document.all.service.value;
	 var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
	 xmlDoc.async = false;
	 xmlDoc.loadXML(xml);
	 var xmlRoot = xmlDoc.documentElement;
	 var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);
	 var processID = Flow.getAttribute("Id");
	 var activityType = getRadioGroupValue(document.all.activityType);
	 if(xml!='') {
			FlowXML.value = xml;
			window.close();
		 }
   }catch(e){
     //alert('关闭活动属性对话框时出错！\n\nClose Step Dialog-Window Error!');
	 //window.close();
   }   
}
function cancelOnClick(){
   window.close();
}
function applyOnClick(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var stepId = getURLParam("activityID");
   if(stepId==null){
	   stepId = '';
   }
   try{
     var FlowXML = opener.document.all.FlowXML;

	 xml = getStepXML(FlowXML,stepId);	 
	 if(xml!='') {
	   FlowXML.value = xml;
	   btnApply.disabled=true;
	 }
   }catch(e){
     alert('应用活动属性时出错！\n\nApply Step Properties Error!');
	 window.close();
   }
}

function atNewStep(type){
	var FlowXML = window.dialogArguments.document.all.FlowXML;
	var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
	  xmlDoc.async = false;
	  xmlDoc.loadXML(FlowXML.value);
	  var xmlRoot = xmlDoc.documentElement;
	  var Steps = xmlRoot.getElementsByTagName("Activities").item(0);
	  for ( var i = 0;i < 100;i++ ) {
		  var newID = "newstep"+i;
		   if(!existID(Steps,newID)){
			   document.all.StepId.value = "newstep"+i;
				break;
		   }
	  }
	  for ( var i = 0;i < 100;i++ ) {
		  var newName = "新活动"+i;
		   if(!existName(Steps,newName)){
			   document.all.StepText.value = "新活动"+i;
				break;
		   }
	  }
      var typeRadio = document.all.activityType;
	     if(typeRadio!=null){
		     for(var n=0;n<typeRadio.length;n++){
			     if(typeRadio[n].value==type){
			    	 typeRadio[n].checked='checked';
			    	 break;
			     }			     
		     }
	     }
	     if(type!='3'){
	    	 document.getElementById("serviceArea").style.display = 'none';
	     }
}
function atEditStep(FlowXML,stepId){
  var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;
  xmlDoc.loadXML(FlowXML.value);
  var xmlRoot = xmlDoc.documentElement;
  var Steps = xmlRoot.getElementsByTagName("Activities").item(0);

  for ( var i = 0;i < Steps.childNodes.length;i++ ) {
    Step = Steps.childNodes.item(i);
	nId = Step.getAttribute("Id");
	if(nId==stepId){
	  document.all.StepId.value = stepId;document.all.StepId.disabled=true;
      document.all.StepText.value = Step.getAttribute("Name");
      if(Step.getElementsByTagName("Description").item(0)!=null){
    	  document.all.StepDesp.value = Step.getElementsByTagName("Description").item(0).text;
      }
      //setRadioGroupValue(document.all.StepType,Step.getElementsByTagName("BaseProperties").item(0).getAttribute("stepType"));
  
      document.all.Width.value = Step.getElementsByTagName("NodeGraphicsInfo").item(0).getAttribute("Width");
      document.all.Height.value = Step.getElementsByTagName("NodeGraphicsInfo").item(0).getAttribute("Height");
      document.all.X.value = Step.getElementsByTagName("Coordinates").item(0).getAttribute("XCoordinate");
      document.all.Y.value = Step.getElementsByTagName("Coordinates").item(0).getAttribute("YCoordinate");      
      //活动类型回显
      var actTypeValue = '';
      if(Step.getElementsByTagName("Implementation").item(0)!=null){
    	  actTypeValue = 3;
      }else if(Step.getElementsByTagName("Route").item(0)!=null){
    	  actTypeValue = 5;
      }else{
          actTypeValue = 4;
      }
      var typeRadio = document.all.activityType;
	     if(typeRadio!=null){
		     for(var n=0;n<typeRadio.length;n++){
			     if(typeRadio[n].value==actTypeValue){
			    	 typeRadio[n].checked='checked';
			    	 break;
			     }			     
		     }
	     }
	     //
	     //服务回显
	     
      if(Step.getElementsByTagName("TaskApplication")!=null){
          if(Step.getElementsByTagName("TaskApplication").item(0)!=null){
        	  document.all.service.value = Step.getElementsByTagName("TaskApplication").item(0).getAttribute("Id");
          }else{
        	  document.getElementById("serviceArea").style.display = 'none';
          }
      }
      if(Step.getElementsByTagName("ActualParameters").item(0)!=null){
      	document.all.paramsInput.value = Step.getElementsByTagName("ActualParameters").item(0).xml;
      }
      //	
	  break;
	}
  } 

}

function getStepXML(FlowXML,stepId){
  id = document.all.StepId.value;
  text = document.all.StepText.value;
  //stepType = getRadioGroupValue(document.all.StepType);
  stepType = "Manual";
  var actType = getRadioGroupValue(document.all.activityType);
  if(id=='') {alert('请先填写活动编号！\n\nPlease input Step Id!');return '';}
  if(text=='') {alert('请先填写活动名称！\n\nPlease input Step Name!');return '';}
  
  width = document.all.Width.value;
  height = document.all.Height.value;
  x = document.all.X.value;
  y = document.all.Y.value;
  var serviceID = document.all.service.value;
  var activityDesp = document.all.StepDesp.value;
  //textWeight = document.all.TextWeight.value;
  //strokeWeight = document.all.StrokeWeight.value;
 
  var xml = ""; 
  //生成活动xml
  //xml+= '<Step><BaseProperties id="'+id+'" text="'+text+'" stepType="'+stepType+'" />';
  //xml+= '<VMLProperties width="'+width+'" height="'+height+'" x="'+x+'" y="'+y+'" textWeight="'+textWeight+'" strokeWeight="'+strokeWeight+'" zIndex="" />';
  //xml+= '<FlowProperties /></Step>';
  xml+= '<Activity Id="'+id+'" Name="'+text+'"  StartMode="Manual" >';
  xml+= '<Description>'+activityDesp+'</Description>';
  xml+= '<NodeGraphicsInfos>';
  xml+= '<NodeGraphicsInfo Width="'+width+'"  Height="'+height+'" >';
  xml+= '<Coordinates XCoordinate="'+x+'"  YCoordinate="'+y+'" />';
  xml+= '</NodeGraphicsInfo></NodeGraphicsInfos>';
  if(actType!=null&&actType=='3'){ //如果是自动活动，则添加服务配置
	  xml+= '<Implementation><Task><TaskApplication Id="'+serviceID+'">';
	  var serviceParams = document.all.paramsInput.value;
	  if(serviceParams!=null&&serviceParams!=''){
		  xml+= serviceParams;
	  }else{
		  xml+= '<ActualParameters></ActualParameters>';
	  }
	  xml+= '</TaskApplication></Task></Implementation>';
  }else if(actType!=null&&actType=='5'){
	  xml+= '<Route />';
  }
  xml+= '</Activity>';
  var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;
  xmlDoc.loadXML(FlowXML.value);
  var xmlRoot = xmlDoc.documentElement;
  var Steps = xmlRoot.getElementsByTagName("Activities").item(0);

  //添加：查找编号冲突的Id
  //修改：查找原来的Id
  for ( var i = 0;i < Steps.childNodes.length;i++ ) {
      Step = Steps.childNodes.item(i);
	  nId = Step.getAttribute("Id");
	  
	  if(nId==id && stepId=='') {
	    alert('新活动已存在！请重新输入！\n\nThis Step Id has exist! Please input non-repeat Step Id!');return '';
	  }
	  if(nId==stepId && stepId!='') {
	    Steps.removeChild(Step);break;
	  }
  }
  

  var xmlDoc2 = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc2.async = false;
  xmlDoc2.loadXML(xml);    
  Steps.appendChild(xmlDoc2.documentElement); 
  return xmlRoot.xml;
}
//-->
function editService(){
	var opener = window.dialogArguments;
	var stepId = getURLParam("activityID");
	var serviceID = document.all.service.value;
	var serviceParam = '';
	if(stepId!=null){
		var FlowXML = opener.document.all.FlowXML;
		xml = getStepXML(FlowXML,stepId);
		var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
		xmlDoc.async = false;
		xmlDoc.loadXML(xml);
		var xmlRoot = xmlDoc.documentElement;
		var Steps = xmlRoot.getElementsByTagName("Activities").item(0);
		var Step;
		for ( var i = 0;i < Steps.childNodes.length;i++ ) {
		      Step = Steps.childNodes.item(i);
			  nId = Step.getAttribute("Id");
			  if(nId==stepId){
				  break;
			  }
		  }
		serviceParam = Step.getElementsByTagName("ActualParameters").item(0).xml;
	}else{
		serviceParam = '<ActualParameters></ActualParameters>';
	}
	if(serviceID!=null&&serviceID!=''&&serviceID!='0'){
		var url= '<html:rewrite page="/process/visual/serviceDialog.jsp" module=""/>?timeStamp=' + new Date().getTime();
		url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent('服务配置'))+"&url="+encodeURI('<html:rewrite page="/process/visual/serviceDialog.jsp" module=""/>?timeStamp='+new Date().getTime());
		var oObject = new Object();	
		oObject.serviceID = serviceID;
		oObject.serviceValue = serviceParam;
	    var result=showModalDialog(url,oObject,"dialogWidth:450px;dialogHeight:360px;help:no;scrollbars:yes;status:no");
	    if(result!=null&&result!=''){
	    	document.all.paramsInput.value = result;
	    }
	}else{
		alert("请选择一个服务！");
		return;
	}
	    
}

function selectPanel(oSelect){
	var index=oSelect.options[oSelect.selectedIndex].value;
	var fieldsets=document.all.oFieldset;
	for(i=0;i<fieldsets.length;i++){
		fieldsets[i].style.display="none";
	}
	fieldsets[parseInt(index)].style.display="";
}
</SCRIPT> 

</HEAD>

<BODY onload='iniWindow()' onunload=''>
<table border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
	<td class="pannelDiv" valign="top">
		<select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="15" style="background-color: buttonface;">
			<option value="0">基本设置</option>
		 	<option value="1">图表设置</option>
		</select>
	</td>
	<td id="tdFieldsets" valign="top" align="left">
		<fieldset id="oFieldset" style="width:370px;height:210px" class="fieldPanel" >
			<div >
				<table border="0" cellpadding="0" cellspacing="0" class="theTableClass">
					<tr>
						<td width="70px;">活动编号</td>
						<td><INPUT TYPE="text" NAME="StepId" value="newstep" class=txtput  style="width:193px"></td>
					</tr>
					<tr>
						<td>活动名称</td>
						<td><INPUT TYPE="text" NAME="StepText" value="" class=txtput style="width:193px"></td>
					</tr>
					<tr>
						<td>活动类型</td>
						<td>
							<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="activityType" value="3" checked="checked"><span id=tabpage1_5>自动活动</span>&nbsp;<INPUT TYPE="radio" NAME="activityType" value="4" ><span id=tabpage1_6>交互活动</span>&nbsp;<INPUT TYPE="radio" NAME="activityType" value="5" ><span id=tabpage1_7>判断活动</span>&nbsp;</FONT>
						</td>
					</tr>
					<tr id="serviceArea">
						<td>选择服务</td>
						<td>
							<select name="service" id="service" style="width:200px;">
								<option value="0">无</option>
								<logic:iterate id="service" name="services">
									<option title='<bean:write name="service" property="value"/>' value='<bean:write name="service" property="key"/>'><bean:write name="service" property="value"/></option>
								</logic:iterate>
							</select>&nbsp;<button onclick="editService()">配置参数</button><input type="hidden" id="paramsInput" name="paramsInput" value=""/>
						</td>
					</tr>
					<tr valign="top">
						<td>活动描述</td>
						<td><textarea rows="5" cols="22" name="StepDesp"></textarea></td>
					</tr>
				</table>
			</div>
		</fieldset> 
		<fieldset id="oFieldset" style="display: none" class="fieldPanel" style="width:370px;height:210px">
			<div style="height: 210px;">
				<table border="0" cellpadding="0" cellspacing="0" class="theTableClass">
					<tr>
						<td style="padding-right:20px;">图形宽度</td>
						<td ><input TYPE="text" NAME="Width" value="200" class=txtput  style="width:193px"/></td>
					</tr>
					<tr>
						<td style="padding-right:20px;">图形高度</td>
						<td><input TYPE="text" NAME="Height" value="200" class=txtput  style="width:193px"/></td>
					</tr>
					<tr>
						<td style="padding-right:20px;">图形X坐标</td>
						<td><input TYPE="text" NAME="X" value="1800" class=txtput  style="width:193px"/></td>
					</tr>
					<tr>
						<td style="padding-right:20px;">图形Y坐标</td>
						<td><input TYPE="text" NAME="Y" value="150" class=txtput  style="width:193px"/></td>
					</tr>
				</table>
			</div>
		</fieldset>
		<div id="toolbar" align="right" style="padding:10px">
			<button id="btnOk" onclick="okOnClick()">确定</button>
			<button id="btnCancel" onclick="cancelOnClick()">取消</button>
			<!--<input type=button id="btnApply" class=btn value="应 用" onclick="jscript: applyOnClick();">-->
		</div>
	</td>
</tr>
</table>
</BODY>
</HTML>
