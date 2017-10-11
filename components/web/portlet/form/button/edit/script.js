var EditButton = new Object();
EditButton.GotoChannel = function (channel,nodeID,openInWindow){
	if(openInWindow!=null&&openInWindow!=""){
		if(openInWindow=="false"){
			document.location=channel+'?contentId='+nodeID;
		}else{
			window.open(channel+'?contentId='+nodeID);
		}
	}else{
		document.location=channel+'?contentId='+nodeID;
	}
}
