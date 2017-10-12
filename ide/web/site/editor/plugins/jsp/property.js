var oTemplate = window.dialogArguments.window.oTemplate;
var oChannel = window.dialogArguments.window.oChannel;
var EditorDocument = window.dialogArguments.window.FCK.EditorDocument;
var FCK = window.dialogArguments.window.FCK;
/**
 * 初始化；
 * @return
 */
function doInit(){
	initBaseProperty();
	initMeta();
	initStyleTable();
	initScriptTable();
};
/**
 * 更新设置
 * @return
 */
function doOK(){
	var displayName = document.getElementsByName("displayName")[0].value;
	if(!validateRequired(displayName)){
		alert("栏目显示名称不能为空！");
		return false;
	}
	if(!validateLength(displayName,1,32)){
		alert("栏目显示名称长度必须在1和32之间！");
		return false;
	}
	setBaseProperty();
	setStyles();
	setScripts();
	setMeta();
	window.close();
};

function initBaseProperty(){
	document.getElementsByName("name")[0].value = oChannel.name;
	var displayName  = getDisplayName();
	if(displayName==null||displayName==""){
		displayName = oChannel.name;
	}
	document.getElementsByName("displayName")[0].value = displayName;
	var definitionID = getDefinitionID();
	if(definitionID!=null&&definitionID!=""){
		document.getElementsByName("definitionID")[0].value = definitionID;
		document.getElementsByName("definitionName")[0].value = CMSDialog.GetDefinitionName(definitionID);
	}
	var startActivity = getActivity();
	if(startActivity!=null&&startActivity!=""){
		var arr = startActivity.split(".");
		if(arr.length==2){
			document.getElementById("activityId").value = startActivity;
			var definition =  PMS.getProcess(arr[0]);
			var activity = definition.getActivity(arr[1]);
			document.getElementById("activityName").value = definition.getName()+"."+activity.getName();
		}
	}
	document.getElementById("title").value = getTitle();
	//document.all("folderPath").value = oChannel.name;
	//document.all("folderPath").value = oChannel.name;
};

/**
 * 选择活动
 */
  function doSelectActivity(oActivity,oName,oTemplate){
	  var ret = showDialog(Globals.contextPath+'/process/activity/selector.jsp',oTemplate,620,450);
	  if(ret!=null){
		  oActivity.value = ret.definition.ID+"."+ret.activity.ID;
		  oName.value=ret.definition.name +"."+ret.activity.name;
	  }
  }

function getDefinitionID(){
	if(FCK.Html==null){
		return "";
	}
	if(/<site:html(.*?)definition=\"([^\"]*)\"(.*?)/i.test(FCK.Html)){
		return RegExp["$2"];
	}
	return "";
}
function setDefinitionID(definitionID){
	if(/<site:html(.*?)definition=\"([^\"]*)\"(.*?)/i.test(FCK.Html)){
		FCK.Html = FCK.Html.replace(/(<site:html(.*?)definition=\")([^\"]*)(\"(.*?))/i,"$1"+definitionID+"$4");
	}else{
		FCK.Html = FCK.Html.replace(/(<site:html(.*?))(>)/i,"$1 definition=\""+definitionID+"\"$3");
	}
}
function getActivity(){
	if(FCK.Html==null){
		return "";
	}
	if(/<site:html(.*?)ontagstart=\"([^\"]*)\"(.*?)/i.test(FCK.Html)){
			return RegExp["$2"];
	}
	return "";
}
function setActivity(activity){
	if(/<site:html(.*?)ontagstart=\"([^\"]*)\"(.*?)/i.test(FCK.Html)){
			FCK.Html = FCK.Html.replace(/(<site:html(.*?)ontagstart=\")([^\"]*)(\"(.*?))/i,"$1"+activity+"$4");
	}else{
		FCK.Html = FCK.Html.replace(/(<site:html(.*?))(>)/i,"$1 ontagstart=\""+activity+"\"$3");
	}
}
function getDisplayName(){
	if(FCK.JspPageInstructions.length==0){
		return null;
	}
	if(/(<%@page(.*?)info=\")(.*?)(\"(.*?)%>)/i.test(FCK.JspPageInstructions[0])){
		return RegExp["$3"];
	}else{
		return null;
	}
}
function setDisplayName(displayName){
	if(/<%@page(.*?)info=(.*?)%>/i.test(FCK.JspPageInstructions[0])){
		FCK.JspPageInstructions[0] = FCK.JspPageInstructions[0].replace(/(<%@page(.*?)info=\")(.*?)(\"(.*?)%>)/i,"$1"+displayName+"$4");
	}else{
		FCK.JspPageInstructions[0] = FCK.JspPageInstructions[0].replace(/(<%@page(.*?))(%>)/i,"$1 info=\""+displayName+"\"$3");
	}
}
function getTitle(){
	if(/<site:title2(.*?)format=\"([^\"]*)\"(.*?)/i.test(FCK.Title)){
		return RegExp["$2"];
	}
	return "";
}
function setTitle(sTitle){
	if(/<site:title2(.*?)format=\"([^\"]*)\"(.*?)/i.test(FCK.Title)){
		FCK.Title = FCK.Title.replace(/(<site:title2(.*?)format=\")([^\"]*)(\"(.*?))/i,"$1"+sTitle+"$4");
	}else{
		FCK.Title = FCK.Title.replace(/(<site:title2(.*?))(>)/i,"$1 format=\""+sTitle+"\"$3");
	}
}

function setBaseProperty(){
	oChannel.name = document.getElementsByName("name")[0].value;
	setDisplayName(document.getElementsByName("displayName")[0].value);
	if(document.getElementsByName("definitionID")[0].value!=""){
		setDefinitionID(document.getElementsByName("definitionID")[0].value)
	}
	setTitle(document.getElementById("title").value);
	//活动
	var startActivity = document.getElementById("activityId").value;
	if(startActivity!=null&&startActivity!=""){
		setActivity(startActivity);
	}
};

/**
 * 记录下光标的位置
 * @param textObj
 * @return
 */
function setCaret(textObj) {
	if (textObj.createTextRange) {
		textObj.caretPos = document.selection.createRange().duplicate();
	}
}

/**
 * 在记录的光标位置插入字符
 * @param textObj
 * @param value
 * @return
 */
function insertAtCaret(textObj, value){
	if (document.all) {
		if (textObj.createTextRange && textObj.caretPos) {
			var caretPos = textObj.caretPos;
			caretPos.text = value;
			caretPos.select();
		}else{
			textObj.value += value;
		}
	} else {
		if (textObj.setSelectionRange) {
			var rangeStart = textObj.selectionStart;
			var rangeEnd = textObj.selectionEnd;
			var tempStr1 = textObj.value.substring(0, rangeStart);
			var tempStr2 = textObj.value.substring(rangeEnd);
			textObj.value = tempStr1 + textFeildValue + tempStr2;
		}
	}
}

/**
 * 初始化样式
 * @return
 */
function initStyleTable(){
	var channels = oTemplate.getChannelsOfType("style");
	for(var i=0;i<channels.length;i++ ){
		var channel = channels[i];
		var opt=document.createElement("option");
		opt.value = channel.path;
		opt.id = channel.path;
		if(document.all){
			opt.text = channel.displayName+"  ("+channel.contextPath+")";
			document.getElementsByName("allStyles")[0].options.add(opt);
		}else{
			opt.textContent = channel.displayName+"  ("+channel.contextPath+")";
			document.getElementsByName("allStyles")[0].add(opt,null);
		}
	};
	var styles = EditorDocument.getElementsByTagName("link");	
	for(i=0;i<styles.length;i++){
		var style = styles[i];
		if((style.href.trim().length>0)&&(style.href.indexOf("fck")<0) && (style.href.indexOf("portal")<0)){
			var opt;
			if(!document.all){
				var lh = window.location.host;
				var str;
				if(style.href.indexOf(lh)>=0){
					str = style.href.substring((style.href.indexOf(lh)+lh.length),style.href.length);
				}
				if(str != null && str.length>0 && str != undefined){
					opt = document.getElementById(str);
				}
			}else{
				opt = document.getElementById(style.href);
			}
			
			if(opt!=null){
				var newOpt = document.createElement("option");
				newOpt.value = opt.value;
				if(document.all){
					newOpt.text = opt.text;
					document.getElementsByName("currentStyles")[0].options.add(newOpt);
				}else{
					newOpt.textContent = opt.textContent;
					document.getElementsByName("currentStyles")[0].add(newOpt,null);
				}
			}
		}
	}
	
};
/**
 * 更新页面上的style设置；
 * @return
 */
function setStyles(){
	var styles =EditorDocument.getElementsByTagName("link");	
	var oHead = EditorDocument.getElementsByTagName("head")[0];
	for(var i=styles.length-1;i>=0;i--){		
		var style = styles[i];
		if((style.href.trim().length>0)&&(style.href.indexOf("fck")<0) && (style.href.indexOf("portal")<0)){
			style.parentNode.removeChild(style);
		}
	}
	var options =document.getElementsByName("currentStyles")[0].options;
	for(i=0;i<options.length;i++){
		var oLink = EditorDocument.createElement("link");
		oLink.href = options[i].value;
		//oLink.name = options[i].text;
		oLink.type="text/css";
		oLink.rel="stylesheet";		
		oHead.appendChild(oLink);
	}
};
/**
 * 初始化脚本列表；
 * @return
 */
function initScriptTable(){

	var channels = oTemplate.getChannelsOfType("script");
	
	for(var i=0;i<channels.length;i++ ){
		var channel = channels[i];
		var opt=document.createElement("option");
		if(document.all){
			opt.text = channel.displayName+"  ("+channel.contextPath+")";
		}else{
			opt.textContent = channel.displayName+"  ("+channel.contextPath+")";
		}
		opt.value = channel.path;
		opt.id = channel.path;
		if(document.all){
			document.getElementsByName("allScripts")[0].options.add(opt);
		}else{
			document.getElementsByName("allScripts")[0].add(opt,null);
		}
	};
	for(i=0;i<FCK.Script.length;i++){
		var opt = document.getElementById(FCK.Script[i]);
		if(opt!=null){
			var newOpt = document.createElement("option");
			newOpt.value = opt.value;
			if(document.all){
				newOpt.text = opt.text;
				document.getElementsByName("currentScripts")[0].options.add(newOpt);
			}else{
				newOpt.textContent = opt.textContent;
				document.getElementsByName("currentScripts")[0].add(newOpt,null);
			}
		}
	}
};

function initMeta(){
	document.getElementsByName("content-type")[0].value = getMetaContent("content-type");
	document.getElementsByName("keywords")[0].value = getMetaContent("keywords");
	document.getElementsByName("author")[0].value = getMetaContent("author");
	document.getElementsByName("robots")[0].value = getMetaContent("robots");
	document.getElementsByName("refresh")[0].value = getMetaContent("refresh");
	document.getElementsByName("pragma")[0].value = getMetaContent("pragma");
	document.getElementsByName("pageEnter")[0].value = getMetaContent("pageEnter");
	document.getElementsByName("pageExit")[0].value = getMetaContent("pageExit");
	
};
function setMeta(){
	//清空原来的meta标签；
	var elems = EditorDocument.documentElement.getElementsByTagName("meta");
	var oHead = EditorDocument.getElementsByTagName("head")[0];
	for(var i=elems.length-1;i>=0;i--)
		elems[i].parentNode.removeChild(elems[i]);
	document.getElementsByName("content-type")[0].value = "text/html; charset=utf-8";
	setHttpEquiv("content-type","content-type",null);
	setHttpEquiv("keywords",null,"keywords");
	setHttpEquiv("author",null,"author");
	setHttpEquiv("robots",null,"robots");
	setHttpEquiv("refresh","refresh",null);
	setHttpEquiv("pragma","pragma",null);
	setHttpEquiv("pageEnter","pageEnter",null);
	setHttpEquiv("pageExit","pageExit",null);
	
};
/**
 * 设置页面的meta
 * @param inputName 控件名称，它的值将成为meta的content的值
 * @param equivName meta的equiv值
 * @param metaName  meta的name值
 * @return
 */
function setHttpEquiv(inputName, equivName, metaName){
	var oHead = EditorDocument.getElementsByTagName("head")[0];	
	var elem=document.getElementsByName(inputName)[0];	
	if(elem.value!=null && elem.value.length>0){
		//注意，在编辑状态，fckEditor将meta标签转为FCK:meta标签
		var meta = EditorDocument.createElement("FCK:meta");
		if(equivName)
			meta.setAttribute("http-equiv",equivName);
		if(metaName)
			meta.setAttribute("name",metaName);
		meta.setAttribute("content", elem.value);
		oHead.appendChild(meta);
	}
	
};
function getMetaContent(name){
	var elems = EditorDocument.documentElement.getElementsByTagName("meta");
	for(var i=0;i<elems.length;i++){
		var key=elems[i].getAttribute("HTTP-EQUIV");
		if(key == null)
			key = elems[i].getAttribute("name");
		if((key!=null)&& (key.toLowerCase() == name.toLowerCase())){
			return elems[i].getAttribute("content");
		}		
	}
	return "";
};

/**
 * 从<script type="" src="url" ...></script>中解析出url;
 * @param script
 * @return
 */

function setScripts(){
	FCK.Script = new Array();
	var options = document.getElementsByName("currentScripts")[0].options;
	for(var i=0;i<options.length;i++){
		if(options[i].value.indexOf("portal.js.jsp")<0){
			FCK.Script.push(options[i].value);
		}
	}
}

/**
 * 分页展示页面的选择Panel
 */
function selectPanel(oSelect) {
	var index = oSelect.options[oSelect.selectedIndex].value;
	var fieldsets = oSelect.ownerDocument.getElementsByTagName("fieldset");
	for (i = 0; i < fieldsets.length; i++) {
		fieldsets[i].style.display = "none";
	}
	fieldsets[parseInt(index)].style.display = "";
};

function selectProperty(categoryID, objID, objName, excludeType) {
	if (categoryID == null) {
		alert("请选择分类！");
		return;
	}
	var result = CMSDialog.PropertyDefinitionSelector(categoryID, excludeType);
	if (result != null) {
		insertAtCaret(document.getElementById("title"),"{"+result.ID+"}");
	}
};
function disableButton() {
	document.getElementById("btnOk").disabled = "true";
};

function enableButton() {
	document.getElementById("btnOk").disabled = "";
};

function chooseCategory($form) {
	var ret = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);
	if (ret) {
		$form.definitionID.value = ret.ID;
		$form.definitionName.value = ret.name;
	}
};
