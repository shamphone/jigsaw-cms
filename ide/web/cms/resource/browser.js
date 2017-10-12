
var resourceType;

function refresh() {
	if (document.frames("viewer").Refresh)
		document.frames("viewer").Refresh();
}
function ok() {
	if (document.frames("viewer").Ok)
		document.frames("viewer").Ok();
	this.close();
}
function ResolveURL($url) {
	if (!$url)
		return null;
	var url = {};
	url.parameters = {};
	var questionSuffix = $url.indexOf("?");
	if (questionSuffix == -1 || questionSuffix == $url.length - 1) {
		url.href = questionSuffix == -1 ? $url : $url.substr(0, $url.length - 1);
		return url;
	}
	url.href = $url.substr(0, questionSuffix);
	var params = $url.substr(questionSuffix + 1).split("&");
	var ind, pair;
	for (var i=0; i<params.length; i++) {
		ind = params[i].indexOf("=");
		if (ind != -1 && ind != params[i].length - 1) {
			pair = params[i].split("=");
			url.parameters[pair[0]] = pair[1]; 
		}
	}
	return url;
}
function upload() {
	var ret = window.showModalDialog("uploadResource.jsp", "", "dialogWidth:380px;dialogHeight:100px;help:no;scrollbars:yes;status:no;");
	if (ret) {
		document.all("days").value = document.all("days").lastChild.value; 
		document.all("days").click();
	}
}
function download() {
	if (document.frames("viewer").Download)
		document.frames("viewer").Download();
}
function Delete() {
	if (document.frames("viewer").Delete)
		document.frames("viewer").Delete();
}
