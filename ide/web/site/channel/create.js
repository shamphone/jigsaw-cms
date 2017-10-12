var siteFolder = window.dialogArguments.siteFolder;
var channelSource = window.dialogArguments.channelSource;
var oTemplate  = siteFolder.template;
var parentFolder = siteFolder;

window.onload = function() {
	initBaseProperty();
	initStyleTable();
	initScriptTable();
}

function doOK() {
	if(validate()){
		var channelName = document.getElementById("name").value;
		var channelSource = renderSource();
		var param = new Object();
		param.channelName = channelName;
		param.channelSource = channelSource;
		param.siteFolder = parentFolder;
		window.returnValue = param;
		window.close();
	}
	enableButton();
}

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

/**
 * 分页展示页面的选择Panel
 */
function selectPanel(oSelect) {
	var index = oSelect.options[oSelect.selectedIndex].value;
	var fieldsets = oSelect.form.getElementsByTagName("fieldset");
	for (i = 0; i < fieldsets.length; i++) {
		fieldsets[i].style.display = "none";
	}
	fieldsets[parseInt(index)].style.display = "";
};

function initBaseProperty(){
	document.getElementById("title").value = channelSource.getTitle();
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
		opt.text = channel.displayName+"  ("+channel.contextPath+")";
		opt.value = channel.path;
		document.all("allStyles").options.add(opt);
	};
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
		opt.text = channel.displayName+"  ("+channel.contextPath+")";
		opt.value = channel.path;
		document.all("allScripts").options.add(opt);
	}
}
	
function selectCategory(objID, objName) {
	var ret = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);
	if (ret) {
		objID.value = ret.ID;
		objName.value = ret.name;
	}
}

function selectFolder(oPath,oDisplayName){
	var folder = SiteDialog.selectFolder(oTemplate);
	if(folder!=null){
		oPath.value = folder.contextPath;
		oDisplayName.value = folder.displayName;
		parentFolder = folder;
	}
}

function selectProperty(categoryID, obj, excludeType) {
	if (categoryID == null) {
		alert("请选择分类！");
		return;
	}
	var result = CMSDialog.PropertyDefinitionSelector(categoryID, excludeType);
	if (result != null) {
		insertAtCaret(document.getElementById("title"),"{"+result.ID+"}");
	}
}

function setCaret(textObj) {
	if (textObj.createTextRange) {
		textObj.caretPos = document.selection.createRange().duplicate();
	}
}

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

function validateChannel(name) {
	var oChannel = oTemplate.getChannel(parentFolder.contextPath+"/"+name+".jsp");
	return !oChannel.exists();
}
function validate() {
	var name = document.getElementById("name").value = document.getElementById("name").value.trim();
	if(!validateName(name)){
		alert("栏目名称只能包含字母、数字和下划线！");
		return false;
	}
	if(!validateLength(name,4,33)){
		alert("栏目名称长度必须在4和32之间！");
		return false;
	}
	if(!validateChannel(name)){
		alert("该页面已存在!");
		return false;
	}
	var displayName = document.getElementById("displayName").value;
	if(!validateRequired(displayName)){
		alert("栏目显示名称不能为空！");
		return false;
	}
	if(!validateLength(displayName,1,33)){
		alert("栏目显示名称长度必须在1和32之间！");
		return false;
	}
	return true;
}
function renderSource(){
	//设置显示名称
	var displayName = document.getElementById("displayName").value;
	if(displayName!=null&&displayName!=""){
		channelSource.setDisplayName(displayName);
	}
	//绑定分类
	var definitionID = document.getElementById("definitionID").value;
	if(definitionID!=null&&definitionID!=""){
		channelSource.setDefinition(definitionID);
	}
	//标题
	var title = document.getElementById("title").value;
	if(title!=null&&title!=""){
		channelSource.setTitle(title);
	}
	
	//活动
	var startActivity = document.getElementById("activityId").value;
	if(startActivity!=null&&startActivity!=""){
		channelSource.setActivity(startActivity);
	}
	//样式
	var currentStyles = document.getElementById("currentStyles").options;
	for(var i=0;i<currentStyles.length;i++){
		channelSource.addCss(currentStyles[i].value);
	}
	//脚本
	var currentScripts = document.getElementById("currentScripts").options;
	if(currentScripts.length>0){
		for(var i=0;i<currentScripts.length;i++){
			channelSource.addScript(currentScripts[i].value);
		}
	}
	//字符集
	var contentType = document.getElementById("contentType").value;
	if(contentType!=null&&contentType.trim()!=""){
		channelSource.setContentType(contentType);
	}
	
	//关键字
	var keywords = document.getElementById("keywords").value;
	if(keywords!=null&&keywords.trim()!=""){
		channelSource.setKeywords(keywords);
	}
	//作者
	var author = document.getElementById("author").value;
	if(author!=null&&author!=""){
		channelSource.setAuthor(author);
	}
	//爬虫设置
	var robots = document.getElementById("robots").value;
	if(robots!=null&&robots!=""){
		channelSource.setRobots(robots);
	}
	//刷新设置
	var refresh = document.getElementById("refresh").value;
	if(refresh!=null&&refresh!=""){
		channelSource.setRefresh(refresh);
	}
	//到期时间
	var expires = document.getElementById("expires").value;
	if(expires!=null&&expires!=""){
		channelSource.setExpires(expires);
	}
	//缓存设置
	var pragma = document.getElementById("pragma").value;
	if(pragma!=null&&pragma!=""){
		channelSource.setPragma(pragma);
	}
	//进入特效
	var pageEnter = document.getElementById("pageEnter").value;
	if(pageEnter!=null&&pageEnter!=""){
		channelSource.setPageEnter(pageEnter);
	}
	//离开特效
	var pageExit = document.getElementById("pageExit").value;
	if(pageExit!=null&&pageExit!=""){
		channelSource.setPageExit(pageExit);
	}
	return channelSource;
}

