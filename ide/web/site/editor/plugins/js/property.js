var oTemplate = window.dialogArguments.window.oTemplate;
var oChannel = window.dialogArguments.window.oChannel;
var FCK = window.dialogArguments.window.FCK;
var FCKConfig = window.dialogArguments.window.FCKConfig;
var sHtml = FCK.GetXHTML( FCKConfig.FormatSource );
var channelSource = new ScriptChannelSource(sHtml);
/**
 * 初始化；
 * @return
 */
function doInit(){
	document.getElementById("name").value = oChannel.name;
	var displayName = channelSource.getDisplayName();
	if(displayName==null||displayName==""){
		displayName = oChannel.name;
	}
	document.getElementById("displayName").value = displayName;
};
/**
 * 更新设置
 * @return
 */
function doOK(){
	var displayName = document.getElementById("displayName").value;
	if(!validateRequired(displayName)){
		alert("栏目显示名称不能为空！");
		return false;
	}
	if(!validateLength(displayName,1,32)){
		alert("栏目显示名称长度必须在1和32之间！");
		return false;
	}
	channelSource.setDisplayName(displayName);
	FCK.SetHTML(channelSource.toString());
	window.close();
};
