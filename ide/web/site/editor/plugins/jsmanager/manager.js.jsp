<%@page contentType="text/javascript; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
function _Save() {
	var fileName = document.getElementById("list").value; 
	if (!fileName)
		return;
    var content = document.getElementById("editArea").value;
    var request = getXMLHttpRequest();
    var url = '<html:rewrite module="/site/editor" page="/saveFile.do"/>?path=' + encodeURIComponent(path + "/" + fileName) + '&timestamp=' + new Date().getTime();
    request.open("post", url, false);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    request.send(content);
    if(request.status == 200)
       	alert("保存成功！");
    else
    	alert("保存失败");
}
function _Create(modul) {
	var url = "<html:rewrite module="/site/editor" page="/plugins/jsmanager/create.jsp"/>?timestamp=" + new Date().getTime();
	if(modul&&modul=="css"){
		url += "&modul=css";
	}
	var ds = "dialogWidth:270px;dialogHeight:100px;help:no;scrollbars:yes;status:no";
	var arguments = {'path' : path};
	var ret = showModalDialog(url, arguments, ds);
	if (ret) {
		alert("创建成功");
		var list = document.getElementById("list");
		var option = document.createElement("option");
		option.text = option.value = ret["fileName"];
		list.add(option);
		list.value = option.value;
		list.fireEvent("onchange");
		window.parent.tree.location.reload();
	}
}
function _Delete() {
	var fileName = document.getElementById("list").value; 
	if (!fileName)
		return;
	if (!confirm("您确定要删除文件: " + fileName + " 吗？"))
		return;
    var url = '<html:rewrite module="/site/editor" page="/deleteFile.do"/>?path=' + encodeURIComponent(path + "/" + fileName) + '&timestamp=' + new Date().getTime();
    var request = new HttpRequest(url);
	var echo = request.Get();
	if (echo) {
		alert("删除成功");
		window.location.reload();
		window.parent.tree.location.reload();
	} else {
		alert("删除失败");
	}
}
function _RefreshEditArea($fileName) {
	var editArea = document.getElementById("editArea");
	if (!$fileName) {
		editArea.value = "";
		editArea.disabled = "true";
		return;
	}
	editArea.disabled = "";
	var url = '<html:rewrite module="/site/editor" page="/getFileContent.do"/>?timestamp=' + new Date().getTime() + '&path=' + encodeURIComponent(path + "/" + $fileName);
	var request = new HttpRequest(url);
	var content = request.Get();
	if (content != null)
		editArea.value = content;
}
function _OnSelectChange($value) {
	_RefreshEditArea($value);
}
function _Find() {
	var fileName = document.getElementById("list").value; 
	if (!fileName)
		return;
	var ds = "dialogWidth:350px;dialogHeight:140px;help:no;scrollbars:yes;status:no";
	var arguments = {'editArea' : document.getElementById("editArea"), 'win' : this};
	showModalDialog("<html:rewrite module="/site/editor" page="/plugins/jsmanager/find.jsp"/>?timestamp=" + new Date().getTime(), arguments, ds);
}
window.onload = function() {
	_InitList();
	var list = document.getElementById("list");
	_RefreshEditArea(list.value);
}