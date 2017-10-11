
function addPathToSelect(portletID,path,fileName){
	var oSelect = document.getElementById(portletID+"Select");
	if(oSelect!=null){
		if(path!=null&&fileName!=null){
			var newOption=document.createElement("option");
			newOption.value=path;
			newOption.text=fileName;
			oSelect.add(newOption);
		}
	}
}
function delAsynFile(portletID){
	var oSelect = document.getElementById(portletID+"Select");
	if(oSelect!=null){
		deleteOption(oSelect);
	}
}