var siteFolder = window.dialogArguments.siteFolder;
var channelSource = window.dialogArguments.channelSource;
var oTemplate  = siteFolder.template;
var parentFolder = siteFolder;

window.onload = function() {
	initStyleTable();
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
		document.getElementById("allStyles").options.add(opt);
	};
	var opt=document.createElement("option");
	opt.text = "rss样式  (/style/rssStyle.css)";
	opt.value = "/"+oTemplate.name+"/style/rssStyle.css";
	document.getElementById("currentStyles").options.add(opt);
};

function validateChannel(name) {
	var oChannel = oTemplate.getChannel(parentFolder.contextPath+"/"+name+".rss");
	return !oChannel.exists();
}

function newFilter(definitionObj,filterObj){
	if(definitionObj.value==null||definitionObj.value.trim().length==0){
		alert("请选择内容分类！");
		return;
	}
	newFilter_Search(definitionObj,filterObj,'')
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