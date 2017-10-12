<%@page contentType="text/javascript; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
function _IsFileNonexistent($path) {
	var url = '<html:rewrite module="/site/editor" page="/isFileNonexistent.do"/>?timestamp=' + new Date().getTime() + '&path=' + encodeURIComponent($path);
	var request = new HttpRequest(url);
	if (request.Get())
		return true;
	return false;
}
function _GetLegalFileName($input) {
	var fileName = $input.value.trim();
	if (!fileName) {
		alert("文件名不能为空");
		return null;
	}
	var reg1 = /^[^\\\/:\*\?\"<>\|]+$/;
	if (!reg1.test(fileName)) {
		alert("文件名不能包含以下字符：\n \\ / : * ? < > |");
		return null;
	}
	var reg2 = /^[^\.]+.*$/;
	if (!reg2.test(fileName)) {
		alert("文件名不能为空");
		return null;
	}
	var reg3 = /^(.+?)\.+$/;
	var temp = fileName.match(reg3);
	if (temp)
		fileName = temp[1];
	return fileName;
}
function _InsertFile($path) {
	var url = '<html:rewrite module="/site/editor" page="/insertFile.do"/>?timestamp=' + new Date().getTime() + '&path=' + encodeURIComponent($path);
	var request = new HttpRequest(url);
	var content = request.Get();
	if (content)
		return true;
	return false;
}
function _Ok() {
	var text = document.getElementById("fileName");
	var fileName = _GetLegalFileName(text);
	if (!fileName) {
		text.select();
		text.focus();
		return;
	}
	var path = relativePath + "/" + fileName;
	if (!_IsFileNonexistent(path)) {
		alert("该文件名已被使用，请更名后重试");
		text.select();
		text.focus();
		return;
	}
	if (!_InsertFile(path)) {
		alert("创建失败，稍后重试");
		return;
	}
	var ret = {};
	ret['fileName'] = fileName;
	window.returnValue = ret;
	this.close();
}