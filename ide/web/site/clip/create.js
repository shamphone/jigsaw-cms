var siteFolder = window.dialogArguments.siteFolder;
var channelSource = window.dialogArguments.channelSource;
var oTemplate  = siteFolder.template;
var parentFolder = siteFolder;

  function disableButton()
  {
    document.getElementById("btnOk").disabled = "true";
  }
  function enableButton()
  {
    document.getElementById("btnOk").disabled = "";
  }
  function selectFolder(oPath,oDisplayName){
		var folder = SiteDialog.selectFolder(oTemplate);
		if(folder!=null){
			oPath.value = folder.contextPath;
			oDisplayName.value = folder.displayName;
			parentFolder = folder;
		}
	}
  function validateChannel(name) {
		var oChannel = oTemplate.getChannel(parentFolder.contextPath+"/"+name+".jspf");
		return !oChannel.exists();
  }
  function validate() {
  	disableButton();
  	var name = document.getElementById("name").value = document.getElementById("name").value.trim();
  	if(!validateRequired(name)){
		alert("栏目名称不能为空！");
		return false;
	}
  	if(!validateName(name)){
		alert("栏目名称只能包含字母、数字和下划线！");
		return false
	}
  	if(!validateLength(name,4,33)){
		alert("栏目名称长度必须在4和32之间！");
		return false;
	}
  	if(!validateChannel(name)) {
  		alert("该页面片段已存在！")
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
  
  function doOk(){
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
  function renderSource(){
		//设置显示名称
		var displayName = document.getElementById("displayName").value;
		if(displayName!=null&&displayName!=""){
			channelSource.setDisplayName(displayName);
		}
		return channelSource;
	}

