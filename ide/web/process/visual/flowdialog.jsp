<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<TITLE> 流程属性 </TITLE>

<link rel="stylesheet" type="text/css" href="inc/style.css">
<link rel="stylesheet" type="text/css" href="inc/webTab/webtab.css">
<script language=Javascript type="text/Javascript" src="inc/function.js"></script>
<script language=Javascript type="text/Javascript" src="inc/shiftlang.js"></script>
<script language=Javascript type="text/Javascript" src="inc/webTab/webTab.js"></script>

<style>
body {
	background-color: buttonface;
	scroll: no;
	margin: 7px, 0px, 0px, 7px;
	border: none;
	overflow: hidden;	

}
</style>

<SCRIPT LANGUAGE="JavaScript">
<!--
function iniWindow(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var flowId = url.indexOf('?flowid=')<0?'':url.slice(url.indexOf('?flowid=')+8,url.length);
   
   try{
     if(opener.LANG!='') shiftLanguage(opener.LANG,"flowdialog"); 

     var FlowXML = opener.document.all.FlowXML;
     if(flowId==''){
	   atNewFlow();
	 }else{
	   if(FlowXML.value!=''){
	     atEditFlow(FlowXML,flowId);
	   }else{
	     alert('打开流程属性对话框时出错！\n\nOpen Flow Dialog-Window Error!');
		 window.close();  
	   }
	 }
   }catch(e){
     alert('打开流程属性对话框时出错！\n\nOpen Flow Dialog-Window Error!');
	 window.close();
   }
}

function okOnClick(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var flowId = url.indexOf('?flowid=')<0?'':url.slice(url.indexOf('?flowid=')+8,url.length);
   
   try{
     var FlowXML = opener.document.all.FlowXML;

	 xml = getFlowXML(FlowXML,flowId);
	 if(xml!='') {
	   FlowXML.value = xml;
	   window.close();
	 }	 
	 
   }catch(e){
     alert('关闭流程属性对话框时出错！\n\nOpen Flow Dialog-Window Error!');
	 window.close();
   }   
}
function cancelOnClick(){
   window.close();
}
function applyOnClick(){
   var opener = window.dialogArguments;
   var url = opener.dialogURL;
   var flowId = url.indexOf('?flowid=')<0?'':url.slice(url.indexOf('?flowid=')+8,url.length);

   try{
     var FlowXML = opener.document.all.FlowXML;

	 xml = getFlowXML(FlowXML,flowId);	 
	 if(xml!='') {
	   FlowXML.value = xml;
	 }
   }catch(e){
     alert('应用流程属性时出错！\n\nApply Flow Properties Error!');
	 window.close();
   }
}
function atNewFlow(){}

function atEditFlow(FlowXML,flowId){
  var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;
  xmlDoc.loadXML(FlowXML.value);
  var xmlRoot = xmlDoc.documentElement;
  var Flow = xmlRoot.getElementsByTagName("FlowConfig").item(0);

  var beginStepId = '',beginStepText = '',endStepId = '',endStepText = '';
  var Steps = xmlRoot.getElementsByTagName("Steps").item(0);
  for ( var i = 0;i < Steps.childNodes.length;i++ ) {
	   Step = Steps.childNodes.item(i);
	   nType = Step.getElementsByTagName("BaseProperties").item(0).getAttribute("stepType");	 
	   if(nType=='BeginStep'){
	     beginStepId = Step.getElementsByTagName("BaseProperties").item(0).getAttribute("id");
		 beginStepText = Step.getElementsByTagName("BaseProperties").item(0).getAttribute("text");
	   }
	   if(nType=='EndStep'){
	     endStepId = Step.getElementsByTagName("BaseProperties").item(0).getAttribute("id");
		 endStepText = Step.getElementsByTagName("BaseProperties").item(0).getAttribute("text");
	   }
  }

  document.all.FlowId.value = flowId;
  document.all.FlowText.value = Flow.getElementsByTagName("BaseProperties").item(0).getAttribute("flowText");

  document.all.BeginStepId.value = beginStepId
  document.all.EndStepId.value = endStepId;
  document.all.BeginStepText.value = beginStepText;
  document.all.EndStepText.value = endStepText;

  document.all.BeginStepId.disabled=true;
  document.all.EndStepId.disabled=true;
    
  document.all.StepTextColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepTextColor");
  document.all.StepStrokeColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepStrokeColor");
  document.all.StepShadowColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepShadowColor");
  document.all.StepFocusedStrokeColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepFocusedStrokeColor");
  setRadioGroupValue(document.all.IsStepShadow,Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("isStepShadow"));
  document.all.ActionStrokeColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("actionStrokeColor");
  document.all.ActionTextColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("actionTextColor");
  document.all.ActionFocusedStrokeColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("actionFocusedStrokeColor");
  document.all.SStepTextColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("sStepTextColor");
  document.all.SStepStrokeColor.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("sStepStrokeColor");
  document.all.StepColor1.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepColor1");
  document.all.StepColor2.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepColor2");
  setRadioGroupValue(document.all.IsStep3D,Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("isStep3D"));
  document.all.Step3DDepth.value = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("step3DDepth");
  
  setRadioGroupValue(document.all.FlowMode,Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("flowMode"));
  document.all.StartTime.value = Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("startTime");
  document.all.EndTime.value = Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("endTime");
  setRadioGroupValue(document.all.IfMonitor,Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("ifMonitor"));
  setRadioGroupValue(document.all.RunMode,Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("runMode"));
  setRadioGroupValue(document.all.NoteMode,Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("noteMode"));
  document.all.ActiveForm.value = Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("activeForm");
  document.all.AutoExe.value = Flow.getElementsByTagName("FlowProperties").item(0).getAttribute("autoExe");
  
}

function getFlowXML(FlowXML,id){
  flowId = document.all.FlowId.value;
  flowText = document.all.FlowText.value;
  beginStepId = document.all.BeginStepId.value;
  endStepId = document.all.EndStepId.value;
  beginStepText = document.all.BeginStepText.value;
  endStepText = document.all.EndStepText.value;
  if(flowId=='') {alert('请先填写流程编号！\n\nPlease input Flow Id!');return '';}
  if(flowText=='') {alert('请先填写流程名称！\n\nPlease input Flow Name!');return '';}
  if(beginStepId=='' || endStepId=='') {alert('请先填写开始或结束步骤的编号！\n\nPlease input Begin-End Step\'s Id!');return '';}
  if(beginStepText=='' || endStepText=='') {alert('请先填写开始或结束步骤的名称！\n\nPlease input Begin-End Step\'s Name!');return '';}
  
  stepTextColor = document.all.StepTextColor.value;
  stepStrokeColor = document.all.StepStrokeColor.value;
  stepShadowColor = document.all.StepShadowColor.value;
  stepFocusedStrokeColor = document.all.StepFocusedStrokeColor.value;
  isStepShadow = getRadioGroupValue(document.all.IsStepShadow);
  actionStrokeColor = document.all.ActionStrokeColor.value;
  actionTextColor = document.all.ActionTextColor.value;
  actionFocusedStrokeColor = document.all.ActionFocusedStrokeColor.value;
  sStepTextColor = document.all.SStepTextColor.value;
  sStepStrokeColor = document.all.SStepStrokeColor.value;
  stepColor1 = document.all.StepColor1.value;
  stepColor2 = document.all.StepColor2.value;
  isStep3D = getRadioGroupValue(document.all.IsStep3D);
  step3DDepth = document.all.Step3DDepth.value;
  
  flowMode = getRadioGroupValue(document.all.FlowMode);
  startTime = document.all.StartTime.value;
  endTime = document.all.EndTime.value;
  ifMonitor = getRadioGroupValue(document.all.IfMonitor);
  runMode = getRadioGroupValue(document.all.RunMode);
  noteMode = getRadioGroupValue(document.all.NoteMode);
  activeForm = document.all.ActiveForm.value;
  autoExe = document.all.AutoExe.value;
 
  var xml = ""; 
  xml+= '<WebFlow><FlowConfig>'
  xml+= '<BaseProperties flowId="'+flowId+'" flowText="'+flowText+'" />';
  xml+= '<VMLProperties stepTextColor="'+stepTextColor+'" stepStrokeColor="'+stepStrokeColor+'" stepShadowColor="'+stepShadowColor+'" stepFocusedStrokeColor="'+stepFocusedStrokeColor+'" isStepShadow="'+isStepShadow+'" actionStrokeColor="'+actionStrokeColor+'" actionTextColor="'+actionTextColor+'" actionFocusedStrokeColor="'+actionFocusedStrokeColor+'" sStepTextColor="'+sStepTextColor+'" sStepStrokeColor="'+sStepStrokeColor+'" stepColor1="'+stepColor1+'" stepColor2="'+stepColor2+'" isStep3D="'+isStep3D+'" step3DDepth="'+step3DDepth+'"/>';
  xml+= '<FlowProperties flowMode="'+flowMode+'" startTime="'+startTime+'" endTime="'+endTime+'" ifMonitor="'+ifMonitor+'" runMode="'+runMode+'" noteMode="'+noteMode+'" activeForm="'+activeForm+'" autoExe="'+autoExe+'" />';
  xml+= '</FlowConfig>';

  if(id==''){
	  //自动添加开始步骤
	  xml+= '<Steps><Step><BaseProperties id="'+beginStepId+'" text="'+beginStepText+'" stepType="BeginStep" />';
	  xml+= '<VMLProperties width="170" height="180" x="200" y="200" textWeight="" strokeWeight="" isFocused="" zIndex="" />';
	  xml+= '<FlowProperties /></Step>';
	  //自动添加结束步骤
	  xml+= '<Step><BaseProperties id="'+endStepId+'" text="'+endStepText+'" stepType="EndStep" />';
	  xml+= '<VMLProperties width="170" height="180" x="1600" y="1600" textWeight="" strokeWeight="" isFocused="" zIndex="" />';
	  xml+= '<FlowProperties /></Step></Steps>';
	  //自动添加开始到结束的动作
	  xml+= '<Actions><Action><BaseProperties id="action" text="action0" actionType="PolyLine" from="begin" to="end" />';
	  xml+= '<VMLProperties startArrow="" endArrow="classic" strokeWeight="" isFocused="" zIndex="" />';
	  xml+= '<FlowProperties /></Action></Actions>';
  }else{
      var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
	  xmlDoc.async = false;
	  xmlDoc.loadXML(FlowXML.value);

	  var xmlRoot = xmlDoc.documentElement;
	  //修改开始和结束步骤
	  var Steps = xmlRoot.getElementsByTagName("Steps").item(0);
	  for ( var i = 0;i < Steps.childNodes.length;i++ ) {
		   Step = Steps.childNodes.item(i);
		   nType = Step.getElementsByTagName("BaseProperties").item(0).getAttribute("stepType");	 
		   if(nType=='BeginStep'){
			 Step.getElementsByTagName("BaseProperties").item(0).setAttribute("text",beginStepText);
		   }
		   if(nType=='EndStep'){
			 Step.getElementsByTagName("BaseProperties").item(0).setAttribute("text",endStepText);
		   }
      }
	  var Actions = xmlRoot.getElementsByTagName("Actions").item(0);

	  xml+= Steps.xml + Actions.xml;  
  }

  xml+= '</WebFlow>';

  return xml;
}
//-->
</SCRIPT> 
</HEAD>

<BODY onload='iniWindow()' onunload=''>
<INPUT TYPE="hidden" name=TempXML>

<table border="0" cellpadding="0" cellspacing="0" height=385px>
<thead>
  <tr id="WebTab">
	<td class="selectedtab" id="tab1" onmouseover='hoverTab("tab1")' onclick="switchTab('tab1','contents1');"><span id=tabpage1>基本属性</span></td>
	<td class="tab" id="tab2" onmouseover='hoverTab("tab2")' onclick="switchTab('tab2','contents2');"><span id=tabpage2>步骤样式</span></td>
	<td class="tab" id="tab3" onmouseover='hoverTab("tab3")' onclick="switchTab('tab3','contents3');"><span id=tabpage3>动作样式</span></td>
	<td class="tab" id="tab4" onmouseover='hoverTab("tab4")' onclick="switchTab('tab4','contents4');"><span id=tabpage4>工作流属性</span></td>
	<td class="tabspacer" width=70>&nbsp;</td>	
  </tr>
</thead>
<tbody>
  <tr>
	<td id="contentscell" colspan="5">
<!-- Tab Page 1 Content Begin -->
<div class="selectedcontents" id="contents1">
<TABLE border=0 width="100%" height="100%">
<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage1_1>基本属性</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">	
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage1_2>流程编号</span>&nbsp;&nbsp;<INPUT TYPE="text" name="FlowId" value="newflow" class=txtput></TD>
		<TD></TD>
	</TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage1_3>流程名称</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="FlowText" value="" class=txtput></TD>
		<TD></TD>
    </TR>	
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage1_4>开始与结束</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">		
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage1_5>开始步骤编号</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="BeginStepId" value="begin" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage1_6>结束步骤编号</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="EndStepId" value="end" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage1_7>开始步骤名称</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="BeginStepText" value="" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage1_8>结束步骤名称</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="EndStepText" value="" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>
<TR height="100%">
	<TD></TD><TD></TD><TD></TD>
</TR>
</TABLE>
</div>
<!-- Tab Page 1 Content End -->

<!-- Tab Page 2 Content Begin -->
<div class="contents" id="contents2">
<TABLE border=0 width="100%" height="100%">

<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage2_1>步骤通用样式</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">		
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_2>主色调</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StepColor1" value="green" class=txtput size=10></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_3>渲染色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StepColor2" value="white" class=txtput size=10>&nbsp;</TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_4>文本色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StepTextColor" value="blue" class=txtput size=10></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage2_5>边框色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StepStrokeColor" value="green" class=txtput size=10><FONT style="font-size:10pt;" COLOR="#919CD0">&nbsp;<span id=tabpage2_20>（3D时无效）</span></FONT></TD>
		<TD></TD>
	</TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_6>选中色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StepFocusedStrokeColor" value="yellow" class=txtput size=10><FONT style="font-size:10pt;" COLOR="#919CD0">&nbsp;<span id=tabpage2_21>（3D时无效）</span></FONT></TD>
		<TD></TD>
    </TR>	
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_7>有无阴影</span>&nbsp;&nbsp;<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="IsStepShadow" value="T" checked><span id=tabpage2_8>有阴影</span>&nbsp;<INPUT TYPE="radio" NAME="IsStepShadow" value="F"><span id=tabpage2_9>无阴影</span>&nbsp;<span id=tabpage2_22>（3D时无效）</span></FONT></TD>
		<TD></TD>
    </TR>	
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_10>阴影色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StepShadowColor" value="#b3b3b3" class=txtput size=10><FONT style="font-size:10pt;" COLOR="#919CD0">&nbsp;<span id=tabpage2_23>（3D时无效）</span></FONT></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_11>2D/3D视角</span>&nbsp;&nbsp;<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="IsStep3D" value="false" checked><span id=tabpage2_12>2D</span>&nbsp;<INPUT TYPE="radio" NAME="IsStep3D" value="true"><span id=tabpage2_13>3D</span>&nbsp;</FONT></TD>
		<TD></TD>
    </TR>	
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_14>3D深度</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="Step3DDepth" value="20" class=txtput size=10></TD>
		<TD></TD>
    </TR>
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage2_15>起止步骤样式</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">		
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage2_16>文本色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="SStepTextColor" value="green" class=txtput size=10></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage2_17>边框色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="SStepStrokeColor" value="green" class=txtput size=10></TD>
		<TD></TD>
	</TR>	
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR height="100%">
	<TD></TD><TD></TD><TD></TD>
</TR>
</TABLE>	  
</div>
<!-- Tab Page 2 Content End -->

<!-- Tab Page 3 Content Begin -->
<div class="contents" id="contents3">
<TABLE border=0 width="100%" height="100%">

<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage3_1>动作样式</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">		
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage3_2>线段色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="ActionStrokeColor" value="green" class=txtput size=10></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage3_3>文本色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="ActionTextColor" value="" class=txtput size=10></TD>
		<TD></TD>
	</TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage3_4>选中色</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="ActionFocusedStrokeColor" value="yellow" class=txtput size=10></TD>
		<TD></TD>
    </TR>
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR height="100%">
	<TD></TD><TD></TD><TD></TD>
</TR>
</TABLE>	  
</div>
<!-- Tab Page 3 Content End -->

<!-- Tab Page 4 Content Begin -->
<div class="contents" id="contents4">
<TABLE border=0 width="100%" height="100%">
<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage4_1>运行设定</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">	
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage4_2>流转模式</span>&nbsp;&nbsp;<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="FlowMode" value="1"><span id=tabpage4_3>正式流转</span>&nbsp;<INPUT TYPE="radio" NAME="FlowMode" value="0"><span id=tabpage4_4>模拟流转</span>&nbsp;</FONT></TD>
		<TD></TD>
	</TR>
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage4_5>开始时间</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="StartTime" value="" class=txtput></TD>
		<TD></TD>
	</TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage4_6>结束时间</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="EndTime" value="" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage4_7>是否监控</span>&nbsp;&nbsp;<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="IfMonitor" value="1"><span id=tabpage4_8>可监控</span>&nbsp;<INPUT TYPE="radio" NAME="IfMonitor" value="0"><span id=tabpage4_9>不可监控</span>&nbsp;</FONT></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage4_10>运行模式</span>&nbsp;&nbsp;<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="RunMode" value="1"><span id=tabpage4_11>人机交互</span>&nbsp;<INPUT TYPE="radio" NAME="RunMode" value="0"><span id=tabpage4_12>自动运行</span>&nbsp;</FONT></TD>
		<TD></TD>
    </TR>
	<TR valign=top>
		<TD></TD>
		<TD><span id=tabpage4_13>通知模式</span>&nbsp;&nbsp;<FONT style="font-size:10pt;" COLOR="#919CD0"><INPUT TYPE="radio" NAME="NoteMode" value="1"><span id=tabpage4_14>内部消息</span>&nbsp;<INPUT TYPE="radio" NAME="NoteMode" value="0"><span id=tabpage4_15>外部邮件</span>&nbsp;</FONT></TD>
		<TD></TD>
    </TR>
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage4_16>人机交互模式</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">	
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage4_17>交互表单</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="ActiveForm" value="" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR valign=top>
	<TD></TD>
	<TD width="100%" valign=top>
	<Fieldset style="border: 1px solid #C0C0C0;">
	<LEGEND align=left style="font-size:9pt;">&nbsp;<span id=tabpage4_18>自动运行模式</span>&nbsp;</LEGEND>
	<TABLE border=0 width="100%" height="100%" style="font-size:9pt;">		
	<TR valign=top>
		<TD width=5></TD>
		<TD><span id=tabpage4_19>运行程序</span>&nbsp;&nbsp;<INPUT TYPE="text" NAME="AutoExe" value="" class=txtput></TD>
		<TD></TD>
    </TR>
	<TR height="3">
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	</TABLE>
	</Fieldset>
	</TD>
	<TD></TD>
</TR>

<TR height="100%">
	<TD></TD><TD></TD><TD></TD>
</TR>
</TABLE>	  
</div>
<!-- Tab Page 4 Content End -->

	</td>
  </tr>
</tbody>
</table>

<table cellspacing="1" cellpadding="0" border="0" style="position: absolute; top: 400px; left: 0px;">
	<tr>
		<td width="100%"></td>
		<td><input type=button id="btnOk" class=btn value="确 定" onclick="jscript: okOnClick();">&nbsp;&nbsp;&nbsp;</td>
		<td><input type=button id="btnCancel" class=btn value="取 消" onclick="jscript: cancelOnClick();">&nbsp;&nbsp;&nbsp;</td>
		<td><input type=button id="btnApply" class=btn value="应 用" onclick="jscript: applyOnClick();">&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table> 
</BODY>
</HTML>