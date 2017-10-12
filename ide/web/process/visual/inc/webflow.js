
function newFlow(){
	if(document.all.FlowXML.value!='') {
	   if(!confirm('不保存旧流程，创建新流程吗？\n\nDon\'t save the old flow,and Create a new flow?')) return;
	}
	workFlowDialog(null,'Flow');	
}
function newStep(type){
	if(document.all.FlowXML.value=='') {
	   alert('请先创建新流程！');
	   return;
	}
	workFlowDialog(null,type);
}
function newAction(){
	if(document.all.FlowXML.value=='') {
	   alert('请先创建新流程！');
	   return;
	}
	workFlowDialog(null,'Action');
}
function editFlow(){
	if(document.all.FlowXML.value=='') {
	   alert('请先创建新流程！');
	   return;
	}
	var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
    xmlDoc.async = false;
    xmlDoc.loadXML(document.all.FlowXML.value);
    var xmlRoot = xmlDoc.documentElement;
    var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);

	workFlowDialog(Flow.getAttribute("Id"),'Flow');
}

function editStep(stepId,stepType){
	workFlowDialog(stepId,stepType);
}
function editAction(actionId){
	workFlowDialog(actionId,'Action');
}

//清空流程图
function delFlow(){
	clearVML();
	clearXML();
}
//删除活动
function delStep(activityID){
	if(activityID=="begin"){
		alert("开始活动不能删除！");
		return;
	}
	if(activityID=="end"){
		alert("结束活动不能删除！");
		return;
	}
	if(confirm("确认要删除活动？")){
	var xmlDoc1 = new ActiveXObject('MSXML2.DOMDocument');
    xmlDoc1.async = false;
    xmlDoc1.loadXML(document.all.FlowXML.value);
    var xmlRoot1 = xmlDoc1.documentElement;
    var Flow1 = xmlRoot1.getElementsByTagName("WorkflowProcess").item(0);
	var flowID1 = Flow1.getAttribute("Id");
	var Actions = xmlRoot1.getElementsByTagName("Transitions").item(0);
	 for ( var i = 0;i < Actions.childNodes.length;i++ ) {
		   var Action = Actions.childNodes.item(i);
		   var transitionID = Action.getAttribute("Id"); 
		   var from = Action.getAttribute("From"); 
		   var to = Action.getAttribute("To"); 
		   if(from==activityID||to==activityID){
			   removeXMLNode(transitionID);
		   }
	   }
	removeXMLNode(activityID);
	FOCUSEDOBJID = '';
	FOCUSEDOBJTYPE = '';
	PMEditor.selectedObject=null;
	}
	 
}
//删除转移
function delAction(transitionId){
if(confirm("确认要删除转移？")){
	var xmlDoc1 = new ActiveXObject('MSXML2.DOMDocument');
    xmlDoc1.async = false;
    xmlDoc1.loadXML(document.all.FlowXML.value);
    var xmlRoot1 = xmlDoc1.documentElement;
    var Flow1 = xmlRoot1.getElementsByTagName("WorkflowProcess").item(0);
	var flowID1 = Flow1.getAttribute("Id"); 	
	removeXMLNode(transitionId);
	FOCUSEDOBJID = '';
	FOCUSEDOBJTYPE = '';
	PMEditor.selectedObject=null;
	}

}

var CONST_LAY_LOWEST = '0';
var CONST_LAY_LOWER = '10';
var CONST_LAY_MIDDLE = '20';
var CONST_LAY_TOPPER = '30';
var CONST_LAY_TOPPEST = '40';

var AUTODRAW = true;
var FOCUSEDOBJID = '';
var FOCUSEDOBJTYPE = '';

function setFocusedObjLay(xmlRoot,oldObjId,newObjId,objType,newLay){
   var Steps = xmlRoot.getElementsByTagName("Activities").item(0);
   var Actions = xmlRoot.getElementsByTagName("Transitions").item(0);
	     
   if(objType=='Action'){
	   for ( var i = 0;i < Actions.childNodes.length;i++ ) {
		   Action = Actions.childNodes.item(i);
		   aId = Action.getAttribute("Id"); 
		   if(aId == newObjId){			  
			  document.getElementById(newObjId).style.zIndex = newLay;
		   }
	   }   	
       
   }else{
	   if(oldObjId!='' && newObjId!='' && newObjId!=oldObjId){
		   var fromStep = document.getElementById(oldObjId);
		   var toStep = document.getElementById(newObjId);

		   var fromStepWidth = parseInt(fromStep.style.width);
		   var fromStepHeight = parseInt(fromStep.style.height);
		   var fromStepX = parseInt(fromStep.style.left);
		   var fromStepY = parseInt(fromStep.style.top);

		   var toStepWidth = parseInt(toStep.style.width);
		   var toStepHeight = parseInt(toStep.style.height);	   
		   var toStepX = parseInt(toStep.style.left);
		   var toStepY = parseInt(toStep.style.top);

		   var flag = ifRepeatStep(fromStepX,fromStepY,fromStepWidth,fromStepHeight,toStepX,toStepY,toStepWidth,toStepHeight);
	   }else{
		   var flag = false;
	   }

	   for ( var i = 0;i < Steps.childNodes.length;i++ ) {
		   Step = Steps.childNodes.item(i);
		   sId = Step.getAttribute("Id");
		   if(newObjId == sId){
			  document.getElementById(sId).style.zIndex = newLay;
			  //Step.getElementsByTagName("VMLProperties").item(0).setAttribute("zIndex",newLay);			  
		   }

		   if(oldObjId == sId && flag){ //如果与原来的焦点活动叠盖则将原来的活动放在下面
			  document.getElementById(oldObjId).style.zIndex = parseInt(newLay)-10;
			  //Step.getElementsByTagName("VMLProperties").item(0).setAttribute("zIndex",parseInt(newLay)-1);	   
		   }
	   }
	   
	   for ( var i = 0;i < Actions.childNodes.length;i++ ) {
		   Action = Actions.childNodes.item(i);
		   aId = Action.getAttribute("Id"); 
		   fromStep = Action.getAttribute("From");
		   toStep = Action.getAttribute("To");
		   if(fromStep == newObjId || toStep == newObjId){			  
			  document.getElementById(aId).style.zIndex = newLay;
			  //Action.getElementsByTagName("VMLProperties").item(0).setAttribute("zIndex",newLay);
		   }else{
			  document.getElementById(aId).style.zIndex = CONST_LAY_MIDDLE;
			  //Action.getElementsByTagName("VMLProperties").item(0).setAttribute("zIndex",parseInt(newLay)-1);
		   }	
	   }   		   
   } 

   return xmlRoot;
}


function objFocusedOn(objId,type){
   var FlowXML = document.all.FlowXML;
   if(FlowXML.value!=''){
	   var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
       xmlDoc.async = false;
       xmlDoc.loadXML(FlowXML.value);
   
       var xmlRoot = xmlDoc.documentElement;
	   var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);
	   var focusedOnColor;
	   if(type=='Action'){
		   focusedOnColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("actionFocusedStrokeColor");		   
	   }
	   else{
		   focusedOnColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepFocusedStrokeColor");			   
	   }
	   xmlRoot = setFocusedObjLay(xmlRoot,FOCUSEDOBJID,objId,type,CONST_LAY_TOPPEST);AUTODRAW = false;FlowXML.value = xmlRoot.xml;AUTODRAW = true;
	   document.getElementById(objId).style.zIndex = CONST_LAY_TOPPEST;
	   document.getElementById(objId).StrokeColor = focusedOnColor;

	   //if(FOCUSEDOBJID == objId && MOVEFLAG) return;
		PMEditor.selectedObject= new Object();
	   PMEditor.selectedObject.id = objId;
	   PMEditor.selectedObject.type = type;
	   if(FOCUSEDOBJID == objId ) return;
	   objFocusedOff();
	   FOCUSEDOBJID = objId;
	   FOCUSEDOBJTYPE = type;
	   if(type=="Action"){
	   var transitions=xmlRoot.getElementsByTagName("Transitions").item(0).childNodes;
	   for(var i=0;i<transitions.length;i++){
	   		var transitionId=transitions.item(i).getAttribute("Id");
	   		var transitionName=transitions.item(i).getAttribute("Name");
	   	//	var transitionCondition=transitions.item(i).childNodes(0).text;
	   		if(transitionId==objId){
	   			document.getElementById("ActionIdhidden").value=transitionId;
				document.getElementById("ActionId").value=transitionId;
				document.getElementById("ActionText").value=transitionName;
				editTransaction(transitionId);
				var fieldsets=document.getElementsByTagName("fieldset");
				for(j=0;j<fieldsets.length;j++){
					fieldsets[j].style.display="none";
				}
	   			document.getElementsByTagName("fieldset")[1].style.display="";
	   		}
	   }
	   }else{
	   var activities=xmlRoot.getElementsByTagName("Activities").item(0).childNodes;
	   for(var k=0;k<activities.length;k++){
	   		var activityId=activities.item(k).getAttribute("Id");
	   		var activityName=activities.item(k).getAttribute("Name");
	   		var activityDescription=activities.item(k).childNodes(0).text;
	   		if(activityId==objId){
	   			document.getElementById("activityIDhidden").value=activityId;
				document.getElementById("activityID").value=activityId;
				document.getElementById("activityName").value=activityName;
				document.getElementById("activityDescription").value=activityDescription;

				document.getElementById("Height").value=activities.item(k).getElementsByTagName("NodeGraphicsInfo")[0].getAttribute("Height");
				document.getElementById("Width").value=activities.item(k).getElementsByTagName("NodeGraphicsInfo")[0].getAttribute("Width");
				document.getElementById("XCoordinate").value=activities.item(k).getElementsByTagName("Coordinates")[0].getAttribute("XCoordinate");
				document.getElementById("YCoordinate").value=activities.item(k).getElementsByTagName("Coordinates")[0].getAttribute("YCoordinate");
				if(activities.item(k).getElementsByTagName("Implementation").item(0)!=null){
				document.getElementById("paramsInput").value=activities.item(k).getElementsByTagName("ActualParameters")[0].xml;
				document.getElementById("activityType").value='3';
    	  		document.getElementById("serviceLabel").style.display="";
    	  		document.getElementById("service").style.display="";
    	  		document.getElementById("serviceConf").style.display="";
    	  		var services=document.getElementById("service").options;
      	   		for(var m=0;m<services.length;m++){
      	   			if(services[m].value==activities.item(k).getElementsByTagName("TaskApplication")[0].getAttribute("Id")){
      	   				services[m].selected=true;
      	   			}
      	   		}
      	   		}else if(activities.item(k).getElementsByTagName("Route").item(0)!=null){
      	   		document.getElementById("activityType").value='5';
    	  		document.getElementById("serviceLabel").style.display="none";
    	  		document.getElementById("service").style.display="none";
    	  		document.getElementById("serviceConf").style.display="none";
      	   		}else{
      	   		document.getElementById("activityType").value='4';
          		document.getElementById("serviceLabel").style.display="none";
    	  		document.getElementById("service").style.display="none";
    	  		document.getElementById("serviceConf").style.display="none";
      	 		}
      	 		
	   			var fieldsets=document.getElementsByTagName("fieldset");
				for(var n=0;n<fieldsets.length;n++){
					fieldsets[n].style.display="none";
				}
	   			document.getElementsByTagName("fieldset")[2].style.display="";
	   		}
	   }
	   }
   }
}
function objFocusedOff(){   
   if(FOCUSEDOBJID=='') return; 

   var FlowXML = document.all.FlowXML;
   if(FlowXML.value!=''){
	   var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
       xmlDoc.async = false;
       xmlDoc.loadXML(FlowXML.value);
   
       var xmlRoot = xmlDoc.documentElement;
	   var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);	  
	   
	   if(FOCUSEDOBJTYPE == 'Action'){
		   focusedOffColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("actionStrokeColor");
	   }else{
		   if(FOCUSEDOBJTYPE == 'BeginStep' || FOCUSEDOBJTYPE == 'EndStep'){
			   focusedOffColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("sStepStrokeColor");
		   }else{
			   focusedOffColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepStrokeColor");
		   }
	   }
	    
	   eval('document.all.'+FOCUSEDOBJID+'.StrokeColor="'+focusedOffColor+'"'); 
	   FOCUSEDOBJID = '';
	   FOCUSEDOBJTYPE = '';
   }
}

function moveStep(moveId){	
	dragIt(moveId,"FlowVML");
}

function redrawVML(){
   if(PMEditor.selectedObject!=null){
	   PMEditor.selectedObject = null;
   }   
   var FlowXML = document.all.FlowXML;
   var FlowVML = document.all.FlowVML;
   if(FlowXML.value!=''){
	   var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
       xmlDoc.async = false;
       xmlDoc.loadXML(FlowXML.value);
   
       var xmlRoot = xmlDoc.documentElement;
	   var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);       
	   var Steps = xmlRoot.getElementsByTagName("Activities").item(0);
       var Actions = xmlRoot.getElementsByTagName("Transitions").item(0);

	   var StepColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepStrokeColor");
	   var StepTextColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepTextColor");
	   var ActionColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("actionStrokeColor");
	   var sStepColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("sStepStrokeColor");
	   var sStepTextColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("sStepTextColor");	
	   var StepShadowColor = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepShadowColor");
	   var IsStepShadow = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("isStepShadow");
	   var StepColor1 = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepColor1");
	   var StepColor2 = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("stepColor2");
	   var IsStep3D = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("isStep3D");
	   var Step3DDepth = Flow.getElementsByTagName("VMLProperties").item(0).getAttribute("step3DDepth");
       
       var vmlHTML = '';
	   //draw activities
	   var nIndex,nId,nText,nType,nIsFocused,nTextColor,nStrokeColor,nShadowColor,nIsShadow,nWidth,nHeight,nX,nY,nStrokeWeight,nTextWeight,nColor1,nColor2,nIs3D,n3DDepth;
	   for ( var i = 0;i < Steps.childNodes.length;i++ )
	   {
		   Step = Steps.childNodes.item(i);
		   nId = Step.getAttribute("Id");
		   nText = Step.getAttribute("Name");
		   nType = '';
		   if(Step.getElementsByTagName("Implementation").item(0)!=null){
    	  		nType = 3;
      	   }else if(Step.getElementsByTagName("Route").item(0)!=null){
    	  		nType = 5;
      	   }else{
          		nType = 4;
      	   }
		   nIndex = CONST_LAY_LOWEST;
		   if(nId=='begin' || nId=='end'){
			   nTextColor = sStepTextColor;
		       nStrokeColor = sStepColor;	
		   }else{
			   nTextColor = StepTextColor;
			   nStrokeColor = StepColor;		       	   
		   }
		   nShadowColor = StepShadowColor;
		   nIsShadow = IsStepShadow;
		   nWidth = Step.getElementsByTagName("NodeGraphicsInfo").item(0).getAttribute("Width");
		   nHeight = Step.getElementsByTagName("NodeGraphicsInfo").item(0).getAttribute("Height");
		   nX = Step.getElementsByTagName("Coordinates").item(0).getAttribute("XCoordinate");
		   nY = Step.getElementsByTagName("Coordinates").item(0).getAttribute("YCoordinate");
		   nStrokeWeight = 1;
		   nTextWeight = 12;
		   nColor1 = StepColor1;
		   nColor2 = StepColor2;
		   nIs3D = IsStep3D;
		   n3DDepth = Step3DDepth;

		   vmlHTML+= getStepHTML(nType,nIndex,nId,nText,nTextColor,nStrokeColor,nShadowColor,nIsShadow,nWidth,nHeight,nX,nY,nStrokeWeight,nTextWeight,nColor1,nColor2,nIs3D,n3DDepth);
	   } 
	   clearVML();
	   drawObject(vmlHTML);
	   vmlHTML = '';
	   
	   //draw transitions
	   var removeFlag = false;
	   var lIndex,lId,lText,lType,lIsFocused,lFromStep,lToStep,lStrokeColor,lStrokeWeight,lStartArrow,lEndArrow;
	   for ( var i = 0;i < Actions.childNodes.length;i++ )
	   {
		   Action = Actions.childNodes.item(i);
		   lId = Action.getAttribute("Id");
		   lText = Action.getAttribute("Name");
		   lType = "PolyLine";
		   lIndex = CONST_LAY_LOWER;
		   lFromStep = Action.getAttribute("From");
		   lToStep = Action.getAttribute("To");	
		   lStartArrow = '';	
		   lEndArrow = 'Classic';
		   StrokeWeight = '';
		   lStrokeColor = ActionColor;
		   
		   result = getActionHTML(lType,lIndex,lId,lText,document.getElementById(lFromStep),document.getElementById(lToStep),lStrokeColor,lStrokeWeight,lStartArrow,lEndArrow)
		   
		   if(result!='') {
			   vmlHTML+= result;
		   }else{
			   Actions.removeChild(Action);
			   removeFlag = true;
		   }
	   } 

       drawObject(vmlHTML);
	   if(removeFlag) FlowXML.value = xmlRoot.xml;
   }
}
function clearXML(){
   var FlowXML = document.all.FlowXML;
   AUTODRAW = false;
   FlowXML.value = '';
}
function clearVML(){
   var FlowVML = document.all.FlowVML;
   FlowVML.innerHTML = '';
   
   FOCUSEDOBJID = '';
   FOCUSEDOBJTYPE = '';
}
function drawObject(ObjHTML){
   var FlowVML = document.all.FlowVML;
   FlowVML.innerHTML+= ObjHTML;
  
}
function removeXMLNode(objId){
   var FlowXML = document.all.FlowXML;
   if(FlowXML.value!=''){
	   xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
       xmlDoc.async = false;
       xmlDoc.loadXML(FlowXML.value);
   
       var xmlRoot = xmlDoc.documentElement;
	   var Steps = xmlRoot.getElementsByTagName("Activities").item(0);
       var Actions = xmlRoot.getElementsByTagName("Transitions").item(0);
	   
	   var findFlag = false;
	   for ( var i = 0;i < Steps.childNodes.length;i++ )
	   {
		   Step = Steps.childNodes.item(i);
		   id = Step.getAttribute("Id");
		   if(id==objId) {
			   if(id=="begin"){
				   alert("开始活动不可删除！");
				   return;
			   }else if(id=="end"){
				   alert("结束活动不可删除！");
				   return;
			   }else{
				   Steps.removeChild(Step);
				   findFlag = true;break;
			   }			   
		   }
	   }
	   if (!findFlag) {
	     for ( var i = 0;i < Actions.childNodes.length;i++ ){
		   Action = Actions.childNodes.item(i);
		   id = Action.getAttribute("Id");
		   if(id==objId) {
			   Actions.removeChild(Action);
			   findFlag = true;break;
		   }
	     }
	   }

	   if (findFlag) {AUTODRAW = true;FlowXML.value = xmlRoot.xml;}
   }
}
function getStepHTML(stepType,z_index,id,text,textColor,strokeColor,shadowColor,isShadow,width,height,X,Y,strokeWeight,textWeight,color1,color2,is3D,step3DDepth){
   var stepHTML = '';
   var styleHTML = 'id='+id+' title="'+id+'" style="z-index:'+z_index+';position:absolute;width:'+width+';height:'+height+';left:'+X+';top:'+Y+';" strokecolor="'+strokeColor+'" strokeweight="'+strokeWeight+'" onmouseover=\'this.style.cursor="hand";\' onmousedown=\'cleancontextMenu();objFocusedOn(this.id,"'+stepType+'");moveStep(this);\' oncontextmenu=\'objFocusedOn(this.id,"'+stepType+'");stepContextMenu("'+id+'","'+stepType+'");return false;\'';
   var textStyleHTML = 'style="text-align:center; color:'+textColor+'; font-size:'+textWeight+';font-family:宋体;"';
   var shadowHTML = '<v:shadow on="'+isShadow+'" type="single" color="'+shadowColor+'" offset="3px,3px"/>';
   var gradientHTML = '<v:fill type="gradient" color="'+color1+'" color2="'+color2+'" />';
   var step3DHTML = '<v:extrusion on="'+is3D+'" backdepth="'+step3DDepth+'" />';

   if(id=='begin'){
	   //修改颜色和字体
	   gradientHTML = '<v:fill type="gradient" color="#000000" color2="#000000" />';
	   textStyleHTML = 'style="text-align:center; color:'+textColor+'; font-size:10;font-family:宋体;"';
	   stepHTML = '<v:oval '+styleHTML+'>'+step3DHTML+gradientHTML+'</v:oval>';
   }else if(id=='end'){
   styleHTML = 'id='+id+' title="'+id+'" style="z-index:'+z_index+';position:absolute;width:'+width+';height:'+height+';left:'+X+';top:'+Y+';" strokecolor="black" strokeweight="'+strokeWeight+'" onmouseover=\'this.style.cursor="hand";\' onmousedown=\'cleancontextMenu();objFocusedOn(this.id,"'+stepType+'");moveStep(this);\' oncontextmenu=\'objFocusedOn(this.id,"'+stepType+'");stepContextMenu("'+id+'","'+stepType+'");return false;\'';
   		gradientHTML = "<v:fill type='frame' src='inc/images/end.gif' position='.02,.02'/>";

	   textStyleHTML = 'style="text-align:center; color:'+textColor+'; font-size:10;font-family:宋体;"';
	   stepHTML = '<v:oval '+styleHTML+'>'+step3DHTML+gradientHTML+'</v:oval>';
	   }
   else{
   if(stepType=='4'){
	   stepHTML = '<v:RoundRect '+styleHTML+'>'+step3DHTML+gradientHTML+'<v:TextBox inset="2pt,10pt,2pt,5pt" '+textStyleHTML+'>'+text+'</v:TextBox></v:RoundRect>';
   		}
   	if(stepType=='3'){
   		stepHTML = '<v:Rect '+styleHTML+'>'+step3DHTML+gradientHTML+'<v:TextBox inset="2pt,10pt,2pt,5pt" '+textStyleHTML+'>'+text+'</v:TextBox></v:Rect>';
   	}
   	if(stepType=='5'){
   		styleHTML = 'id='+id+' stepType='+stepType+' title="'+id+'" style="z-index:'+z_index+';position:absolute;width:200;height:200px;left:'+X+';top:'+Y+';" strokecolor="'+strokeColor+'" strokeweight="'+strokeWeight+'" onmouseover=\'this.style.cursor="hand";\' onmousedown=\'cleancontextMenu();objFocusedOn(this.id,"'+stepType+'");moveStep(this);\' oncontextmenu=\'objFocusedOn(this.id,"'+stepType+'");stepContextMenu("'+id+'","'+stepType+'");return false;\'';
   		var tempShape='<v:shapetype id="laure" coordorigin= "-120 50" coordsize="240 240" o:master="True" style="top:1;left:1;width:50;height:50" path="m 1,1 l -140,140, 1,280, 140,140 x e"></v:shapetype>';
		var tempShape2='<v:shape type="#laure"'+styleHTML+'>'+step3DHTML+gradientHTML+'<v:TextBox inset="2pt,10pt,2pt,5pt" '+textStyleHTML+'>'+text+'</v:TextBox></v:shape>';
		stepHTML=tempShape+tempShape2;	
   	//stepHTML='<v:image src="<html:rewrite module='/common' page='/images/route.gif'/>"'+styleHTML+'>'+step3DHTML+gradientHTML+'<v:TextBox inset="2pt,15pt,2pt,5pt" '+textStyleHTML+'>'+text+'</v:TextBox></v:image>';;
   	}
   }
   return stepHTML;
}
function getActionHTML(actionType,z_index,id,actionText,fromStep,toStep,strokeColor,strokeWeight,startArrow,endArrow){
   var actionHTML = '';

   if(actionType == 'Line'){
      actionHTML = '<v:line id='+id+' title="'+id+'" style="z-index:'+z_index+';position:absolute;" '+getActionPoints(actionType,fromStep,toStep)+' strokecolor="'+strokeColor+'" strokeweight="'+strokeWeight+'" onmousedown=\'cleancontextMenu();objFocusedOn(this.id,"Action");\' oncontextmenu=\'objFocusedOn(this.id,"Action");actionContextMenu("'+id+'");return false;\'><v:stroke StartArrow="'+startArrow+'" EndArrow="'+endArrow+'"/></v:line>';
   }else{
      actionHTML = '<v:PolyLine id='+id+' title="'+id+'" filled="false" Points="'+getActionPoints(actionType,fromStep,toStep)+'" style="z-index:'+z_index+';position:absolute;" strokecolor="'+strokeColor+'" strokeweight="'+strokeWeight+'" onmousedown=\'cleancontextMenu();objFocusedOn(this.id,"Action");\' oncontextmenu=\'objFocusedOn(this.id,"Action");actionContextMenu("'+id+'");return false;\'><v:stroke StartArrow="'+startArrow+'" EndArrow="'+endArrow+'"/></v:PolyLine>';
   }
	
   return actionHTML;
}
function ifRepeatStep(fromStepX,fromStepY,fromStepWidth,fromStepHeight,toStepX,toStepY,toStepWidth,toStepHeight){
  return (fromStepX + fromStepWidth >= toStepX) && (fromStepY + fromStepHeight >= toStepY) && (toStepX + toStepWidth >= fromStepX) && (toStepY + toStepHeight >= fromStepY);
}

function getActionPoints(actionType,fromStep,toStep){
   if (fromStep==null || toStep==null) return '';

   var pointsHTML = '';

   var fromStepWidth = parseInt(fromStep.style.width);
   var fromStepHeight = parseInt(fromStep.style.height);
   var toStepWidth = parseInt(toStep.style.width);
   var toStepHeight = parseInt(toStep.style.height);
   var fromStepX = parseInt(fromStep.style.left);
   var fromStepY = parseInt(fromStep.style.top);
   var toStepX = parseInt(toStep.style.left);
   var toStepY = parseInt(toStep.style.top);

   //FromStep Center X,Y
   fromCenterX = fromStepX + parseInt(fromStepWidth/2);
   fromCenterY = fromStepY + parseInt(fromStepHeight/2);
   //ToStep Center X,Y
   toCenterX = toStepX + parseInt(toStepWidth/2);
   toCenterY = toStepY + parseInt(toStepHeight/2);
   
   
   if(actionType=='Line' && fromStep!=toStep){//以下是Line的画线算法 //不正确
      //ToStep：左上顶点
      toStepX1 = toStepX;
      toStepY1 = toStepY;
      //ToStep：右上顶点
      toStepX2 = toStepX + toStepWidth;
      toStepY2 = toStepY;
      //ToStep：左下顶点
      toStepX3 = toStepX;
      toStepY3 = toStepY + toStepHeight;
      //ToStep：右下顶点
      toStepX4 = toStepX + toStepWidth;
      toStepY4 = toStepY + toStepHeight;
   
      //如果ToStep在FromStep的右下方，则取ToStep的左上顶点
      if (toStepX>fromStepX && toStepY>fromStepY) {toX = toStepX1;toY = toStepY1;}
      //如果ToStep在FromStep的左下方，则取ToStep的右上顶点
      if (toStepX<fromStepX && toStepY>fromStepY) {toX = toStepX2;toY = toStepY2;}
      //如果ToStep在FromStep的右上方，则取ToStep的左下顶点
      if (toStepX>fromStepX && toStepY<fromStepY) {toX = toStepX3;toY = toStepY3;}
      //如果ToStep在FromStep的左上方，则取ToStep的右下顶点
      if (toStepX<fromStepX && toStepY<fromStepY) {toX = toStepX4;toY = toStepY4;}

      pointsHTML = fromCenterX+','+fromCenterY+' '+toCenterX+','+toCenterY;     //lxh update 2008-10-16

   }else{//以下是PolyLine的画线算法

      if(fromStep!=toStep){
         //活动是否叠盖: 叠盖就不画连线
         if (ifRepeatStep(fromStepX,fromStepY,fromStepWidth,fromStepHeight,toStepX,toStepY,toStepWidth,toStepHeight)) {
            return "";
         } 
/*
         point2X = fromCenterX;
         point2Y = toCenterY;

         if (toCenterX > fromCenterX) {	//下一活动在右
            absY = toCenterY>=fromCenterY?toCenterY-fromCenterY:fromCenterY-toCenterY;  //下一活动在下方，下一活动中心到上一活动定点距离，否则
            if (parseInt(fromStepHeight/2)>=absY) {
               point1X = fromStepX + fromStepWidth;
               point1Y = toCenterY;
               point2X = point1X;
               point2Y = point1Y;
            }else{
               point1X = fromCenterX;
               point1Y = fromCenterY<toCenterY?fromStepY+fromStepHeight:fromStepY;
            }
            absX = toCenterX-fromCenterX;
            if (parseInt(fromStepWidth/2)>=absX) {
               point3X = fromCenterX;
               point3Y = fromCenterY<toCenterY?toStepY:toStepY+toStepHeight;
               point2X = point3X;
               point2Y = point3Y;
            }else{
               point3X = toStepX;
               point3Y = toCenterY;
            }
         }
         if (toCenterX < fromCenterX) {    //下一活动在左
            absY = toCenterY>=fromCenterY?toCenterY-fromCenterY:fromCenterY-toCenterY;
            if (parseInt(fromStepHeight/2)>=absY) {
               point1X = fromStepX;
               point1Y = toCenterY;
               point2X = point1X;
               point2Y = point1Y;
            }else{
               point1X = fromCenterX;
               point1Y = fromCenterY<toCenterY?fromStepY+fromStepHeight:fromStepY;
            }
            absX = fromCenterX-toCenterX;
            if (parseInt(fromStepWidth/2)>=absX) {
               point3X = fromCenterX;
               point3Y = fromCenterY<toCenterY?toStepY:toStepY+toStepHeight;
               point2X = point3X;
               point2Y = point3Y;
            }else{
               point3X = toStepX + toStepWidth;
               point3Y = toCenterY;
            }
         }
         if (toCenterX == fromCenterX) {    //与目标活动在同一竖列
            point1X = fromCenterX;
            point1Y = fromCenterY>toCenterY?fromStepY:fromStepY+fromStepHeight;
            point3X = fromCenterX;
            point3Y = fromCenterY>toCenterY?toStepY+toStepHeight:toStepY;
            point2X = point3X;
            point2Y = point3Y;
         }
         if (toCenterY == fromCenterY) {    //与目标活动在同一横行，好像走不到这步？
            point1X = fromCenterX>toCenterX?fromStepX:fromStepX+fromStepWidth;
            point1Y = fromCenterY;
            point3Y = fromCenterY;
            point3X = fromCenterX>toCenterX?toStepX+toStepWidth:toStepX;
            point2X = point3X;
            point2Y = point3Y;
         }

         pointsHTML = point1X+','+point1Y+' '+point2X+','+point2Y+' '+point3X+','+point3Y;
*/
         //lxh add 2008-10-17---begin----------------------
         var constLength = 30;

         if (toStepX >= fromStepX + fromStepWidth){
            //全右
            point1X = fromStepX + fromStepWidth;
            point1Y = fromCenterY;
            point2X = point1X + constLength;
            point2Y = point1Y;
            point3X = toStepX - constLength;
            point3Y = toCenterY;
            point4X = toStepX;
            point4Y = toCenterY;
             if(toStep.stepType=='5'){
   				point3X-=15;
   				point3Y-=20;
   				point4X-=15;
   				point4Y-=20;
   		}
   			if(fromStep.stepType=='5'){
   				point1X+=15;
   				point1Y-=23;
   				point2X+=20;
   				point2Y-=23;
   		}
         }
         if (toStepX <= fromStepX - toStepWidth){
            //全左
            point1X = fromStepX;
            point1Y = fromCenterY;
            point2X = point1X - constLength;
            point2Y = point1Y;
            point3X = toStepX + toStepWidth + constLength;
            point3Y = toCenterY;
            point4X = toStepX + toStepWidth;
            point4Y = toCenterY;
             if(toStep.stepType=='5'){
   				point3X+=17;
   				point3Y-=20;
   				point4X+=17;
   				point4Y-=20;
 		  }
 		  if(fromStep.stepType=='5'){
  		 		point1X-=20;
 		  		point1Y-=20;
   				point2X-=20;
 		  		point2Y-=20;
 		  }
         }
         if ((toStepX < fromStepX) && (toStepX > fromStepX - toStepWidth)){
            //半左
            point1X = fromStepX + fromStepWidth;
            point1Y = fromCenterY;
            point2X = point1X + constLength;
            point2Y = point1Y;
            point3X = point2X;
            point3Y = toCenterY;
            point4X = toStepX + toStepWidth;
            point4Y = toCenterY;
            if(toStep.stepType=='5'){
   				point3X+=17;
   				point3Y-=25;
   				point4X+=17;
   				point4Y-=25;
 		  }
 		  if(fromStep.stepType=='5'){
  		 		point1X+=20;
 		  		point1Y-=23;
   				point2X+=20;
 		  		point2Y-=23;
 		  }
         }
         if ((toStepX > fromStepX) && (toStepX < fromStepX + fromStepWidth)){
            //半右
            point1X = fromStepX + fromStepWidth;
            point1Y = fromCenterY;
            point2X = toStepX + toStepWidth + constLength;
            point2Y = point1Y;
            point3X = toStepX + toStepWidth + constLength;
            point3Y = toCenterY;
            point4X = toStepX + toStepWidth;
            point4Y = toCenterY;
            if(toStep.stepType=='5'){
   				point3X+=17;
   				point3Y-=20;
   				point4X+=17;
   				point4Y-=20;
 		  }
 		  if(fromStep.stepType=='5'){
  		 		point1X+=20;
 		  		point1Y-=20;
   				point2X+=20;
 		  		point2Y-=20;
 		  }
         }

         
        
   pointsHTML = point1X+','+point1Y+' '+point2X+','+point2Y+' '+point3X+','+point3Y+' '+point4X+','+point4Y;
         //lxh add 2008-10-17---end----------------------
      }else{
         //自连接 
         var constLength = 30;
         point0X = fromStepX+fromStepWidth-constLength;
         point0Y = fromStepY+fromStepHeight;
         point1X = point0X;
         point1Y = point0Y+constLength;
         point2X = fromStepX+fromStepWidth+constLength;
         point2Y = point1Y;
         point3X = point2X;
         point3Y = point0Y-constLength;
         point4X = fromStepX+fromStepWidth;
         point4Y = point3Y;

         pointsHTML = point0X+','+point0Y+' '+point1X+','+point1Y+' '+point2X+','+point2Y+' '+point3X+','+point3Y+' '+point4X+','+point4Y;
      	
      } //end for if (fromStep!=toStep)
   } //end for if (actionType=='Line')
   return pointsHTML;
}

function flowContextMenu(){
  //菜单源文本
  //var menuSrc = '<menu><entity id="c0"><description>创建新活动</description><image>inc/contextMenu/images/add_step.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();newStep("Manual");</onClick><contents></contents></entity>';
  //menuSrc+= '<entity id="c1"><description>创建新转移</description><image>inc/contextMenu/images/add_action.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();newAction();</onClick><contents></contents></entity>';
  var menuSrc= '<menu><entity id="c2"><description>刷新</description><image>inc/contextMenu/images/refresh_vml.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();redrawVML();</onClick><contents></contents></entity></menu>';
  showContextMenu(menuSrc);
}

function stepContextMenu(stepId,stepType){
  //菜单源文本
  //var menuSrc = '<menu><entity id="c0"><description>编辑活动['+stepId+']</description><image>inc/contextMenu/images/edit_obj.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();editStep("'+stepId+'","'+stepType+'");</onClick><contents></contents></entity>';
  //if(stepType!='BeginStep' && stepType!='EndStep') menuSrc+= '<entity id="c1"><description>删除活动['+stepId+']</description><image>inc/contextMenu/images/del_obj.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();delStep("'+stepId+'");</onClick><contents></contents></entity>';
  var menuSrc= '<menu><entity id="c2"><description>刷新</description><image>inc/contextMenu/images/refresh_vml.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();redrawVML();</onClick><contents></contents></entity></menu>';
  showContextMenu(menuSrc);
}

function actionContextMenu(actionId){
  //菜单源文本
  //var menuSrc = '<menu><entity id="c0"><description>编辑转移['+actionId+']</description><image>inc/contextMenu/images/edit_obj.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();editAction("'+actionId+'");</onClick><contents></contents></entity>';
  //menuSrc+= '<entity id="c1"><description>删除转移['+actionId+']</description><image>inc/contextMenu/images/del_obj.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();delAction("'+actionId+'");</onClick><contents></contents></entity>';
  var menuSrc= '<menu><entity id="c2"><description>刷新</description><image>inc/contextMenu/images/refresh_vml.gif</image><fontcolor>'+MenuTextColor_enable+'</fontcolor><onClick>cleancontextMenu();redrawVML();</onClick><contents></contents></entity></menu>';
  showContextMenu(menuSrc);
}

function showContextMenu(menuSrc){
  //生成右键菜单
  xmlMenu = new ActiveXObject('MSXML2.DOMDocument')
  xmlMenu.async = false
  xmlMenu.loadXML(menuSrc)
  var root = xmlMenu.documentElement 
  
  var menuText=''+root.xml+''
  loadContextMenu(menuText,'inc/contextMenu/contextmenu.xsl')
}

var dialogURL = "";
function workFlowDialog(id,type){	
	var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
    xmlDoc.async = false;
    xmlDoc.loadXML(document.all.FlowXML.value);
    var xmlRoot = xmlDoc.documentElement;
    var Flow = xmlRoot.getElementsByTagName("WorkflowProcess").item(0);
	var flowID = Flow.getAttribute("Id");
	switch (type){    
	    case 'Action': dialogURL = id==null?'transitiondialog.do?flowID='+flowID:'transitiondialog.do?transitionID='+id+'&flowID='+flowID;break;
		case 'Flow': dialogURL = id==null?'flowdialog.do':'flowdialog.do?flowid='+id;break;
		default:dialogURL = id==null?'activitydialog.do?flowID='+flowID+'&type='+type:'activitydialog.do?activityID='+id+'&flowID='+flowID+'&type='+type;break;
	}
	var dialog = window.showModalDialog(dialogURL, window, "dialogWidth:480px; dialogHeight:270px; center:yes; help:no; resizable:no; status:no") ;
//	drawTreeView();
}
function drawTreeView(){
  document.all.treeview.src="_flowtree.html";
}

function existID(elements,newID){  //校验id是否已经存在
	var isExist = false;
	for ( var i = 0;i < elements.childNodes.length;i++ ){
	      var element = elements.childNodes.item(i);
		  id = element.getAttribute("Id");
		  if(id==newID){
			  isExist = true;
			  break;
		  }
	}
	return isExist;
}
function existName(elements,newName){  //校验name是否已经存在
	var isExist = false;
	for ( var i = 0;i < elements.childNodes.length;i++ ){
	      var element = elements.childNodes.item(i);
		  name = element.getAttribute("Name");
		  if(name==newName){
			  isExist = true;
			  break;
		  }
	}
	return isExist;
}

function editTransaction(actionId){
   if(actionId==null){
	   actionId = '';
   }
   try{
     var FlowXML = document.all.FlowXML;		 

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
   FlowXML = document.all.FlowXML;
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
		      			  var url='getFilterPattern.do?categoryID='+categoryID+'&pattern='+conditionStrs[u].trim()+'&kongzhi=空值&timeStamp=' + new Date().getTime();
		      		      req.open("POST",encodeURI(url),false);
		      		      req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		      		      req.send(null);
		      		      var data=req.responseText;
		      		      if(data!=null&&data!=""){
		      		      	newOption.text=data;
		      		      }
		      		      removeAll(conditionSelect);
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
   removeAll(document.all.From);
	removeAll(document.all.To);
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