var items = [];
var resourceType;
var sortBasis;
var renderPattern;
var CONTEXT_PATH;
var PATH_IMAGE_UP_ARROW = "image/up.gif";
var PATH_IMAGE_DOWN_ARROW = "image/down.gif";
var owners = [];

Array.prototype.IndexOf = function($item) {
	for ( var i = 0; i < this.length; i++) {
		if (this[i] == $item)
			return i;
	}
	return -1;
}

function GetSortBasis() {
	return top.document.getElementById("selSortBasis").value;
}

function GetSortType() {
	return top.document.getElementById("selSortType").value;
}

function GetRenderPattern() {
	return top.document.getElementById("selRenderPattern").value;
}

// ViewerItem
function ViewerItem($id, $name, $length, $mime, $mimePrefix, $mimeSuffix, $createdTime, $lengthWithUnits, $path, $index) {
	this.id = $id;
	this.name = $name;
	this.length = $length;
	this.mime = $mime;
	this.mimePrefix = $mimePrefix;
	this.mimeSuffix = $mimeSuffix;
	this.createdTime = $createdTime;
	this.lengthWithUnits = $lengthWithUnits;
	this.path = $path;
	this.index = $index;
}

ViewerItem.SelectedItems = [];


ViewerItem.prototype = {
	SetClassName : function($className) {
		document.getElementById("item" + items.IndexOf(this)).className = $className;
	},
	OnClick : function() {
		if (event.ctrlKey) {
			var pos = ViewerItem.SelectedItems.IndexOf(this); 
			if (pos != -1) {
				this.SetClassName("unselected");
				ViewerItem.SelectedItems.splice(pos, 1);
			} else {
				this.SetClassName("selected");
				ViewerItem.SelectedItems.push(this);
			}
		} else {
			for (var i=0; i<ViewerItem.SelectedItems.length; i++)
				ViewerItem.SelectedItems[i].SetClassName("unselected");
			ViewerItem.SelectedItems = [];
			ViewerItem.SelectedItems.push(this);
			this.SetClassName("selected");
		}
	}
}

// 渲染
// 縮略圖
function ThumbnailRender() {
	var viewer = document.getElementById("viewer");
	viewer.innerHTML = "";
	var innerHTML = [];
	for ( var i = 0; i < items.length; i++) {
		var path = GetIconPath(items[i].mime, true, items[i].path);
		innerHTML.push('<span class="span1">\n');
		innerHTML.push('<table id="item' + i + '" title="' + items[i].name + '" width="100" class="unselected" cellpadding="0" cellspacing="0" onclick="items[' + i + '].OnClick();">\n');
		innerHTML.push("<tr>\n");
		innerHTML.push('<td align="center" height="100">\n');
		innerHTML.push('<img src="' + path + '" border="0"');
		if (items[i].mimePrefix == "image")
			innerHTML.push(' onload="if (this.width - this.height >= 0) this.width=100; else this.height=100;"');
		else
			innerHTML.push(' width=30 height=30"');
		innerHTML.push(" />\n");
		innerHTML.push("</td>\n");
		innerHTML.push("</tr>\n");
		innerHTML.push("<tr>\n");
		innerHTML.push('<td id="td2" align="center" height="40">' + items[i].name + '</td>');
		innerHTML.push("</tr>\n");
		innerHTML.push("</table>\n");
		innerHTML.push("</span>\n");
	}
	ChangeStyleSheet("thumbnail");
	viewer.innerHTML = innerHTML.join("");
}
// 詳細信息
function DetailRender() {
	var viewer = document.getElementById("viewer");
	viewer.innerHTML = "";
	var innerHTML = [];
	innerHTML.push("<table cellpadding='0' cellspacing='1'>\n");
	innerHTML.push("<thead>\n");
	innerHTML.push("<tr>\n");
	innerHTML.push('<td>名称</td>\n');
	innerHTML.push('<td class="lengthColHead">大小</td>\n');
	innerHTML.push('<td>类型</td>\n');
	innerHTML.push('<td>创建日期</td>\n');
	innerHTML.push("</tr>\n");
	innerHTML.push("</thead>\n");
	innerHTML.push("<tbody>\n");
	
	for ( var i = 0; i < items.length; i++) {
		var path = GetIconPath(items[i].mime);
		innerHTML.push("<tr id='item" + i + "' path='" + items[i].path + "' class='unselected' onclick='items[" + i + "].OnClick();'>\n");
		innerHTML.push("<td>" + items[i].name + "</td>\n");
		innerHTML.push("<td class='lengthCol'>" + items[i].lengthWithUnits + "</td>\n");
		innerHTML.push("<td>" + items[i].mime + "</td>\n");
		innerHTML.push("<td>" + items[i].createdTime + "</td>\n");
		innerHTML.push("</tr>\n");
	}
	innerHTML.push("</tbody>\n");
	innerHTML.push("</table>\n");
	ChangeStyleSheet("detail");
	viewer.innerHTML = innerHTML.join("");
}
// 图标
function IconRender() {
	var viewer = document.getElementById("viewer");
	viewer.innerHTML = "";
	var innerHTML = [];
	for ( var i = 0; i < items.length; i++) {
		var path = GetIconPath(items[i].mime);
		innerHTML.push('<span class="span1">\n');
		innerHTML.push('<table id="item' + i + '" title="' + items[i].name + '" width="60px;" class="unselected" cellpadding="0" cellspacing="0" onclick="items[' + i + '].OnClick();">\n');
		innerHTML.push("<tr>\n");
		innerHTML.push('<td align="center" valign="middle" height="34"><img src="' + path + '" border="0" width="30" height="30" /></td>\n');
		innerHTML.push("</tr>\n");
		innerHTML.push('<tr><td id="td2" align="center" valign="top" height="30">' + items[i].name + '</td></tr>\n');
		innerHTML.push("</table>\n");
		innerHTML.push("</span>\n");
	}
	ChangeStyleSheet("icon");
	viewer.innerHTML = innerHTML.join("");
}

function GetIconPath($mime, $isThumbnail, $path) {
	var path;
	var mimePrefix = $mime.indexOf("/")>-1 ? $mime.split("/")[0] : $mime;
	switch (mimePrefix) {
	case "image":
		path = $isThumbnail ? $path : "image/image.gif";
		break;
	case "video":
		path = "image/video.gif";
		break;
	case "audio":
		path = "image/audio.gif";
		break;
	}
	if (!path) {
		switch ($mime) {
		case "application/x-shockwave-flash":
			path = "image/flash.gif";
			break;
		default:
			path = "image/file.gif";
		}
	}
	return path;
}

//排序
function GetSorter($propName, $propType, $sortType) {
	return function compareRes(res1, res2) {
		var ret;
		var r1 = Convert(res1[$propName], $propType);
		var r2 = Convert(res2[$propName], $propType);
		if (r1 < r2)
			ret = -1;
		else if (r1 > r2)
			ret = 1;
		else
			ret = 0;
		if ($sortType == "desc")
			ret = -ret;
		return ret;
	}
}

function Convert(sValue, sType) {
	switch (sType) {
	case "int":
		return parseInt(sValue);
	case "float":
		return parseFloat(sValue);
	case "date":
		return new Date(Date.parse(sValue));
	default:
		return sValue.toString();
	}
}

function DetailRenderSorter(sColName_) {/*
	var selSortBasis = top.frames['frmPosition'].GetSelSortBasis();
	var selSortType = top.frames['frmPosition'].GetSelSortType();

	if (_sSortBasis == sColName_) {
		selSortType.options.namedItem(_sSortType == "asc" ? "desc" : "asc").selected = true;
		selSortType.fireEvent("onchange");
	} else {
		selSortBasis.options.namedItem(sColName_).selected = true;
		selSortType.options.namedItem("asc").selected = true;
		_sSortType = "asc";
		selSortBasis.fireEvent("onchange");
	}*/
}

//
function DoSort() {
  switch (GetSortBasis()) {
    case "fileName" :
      items.sort(GetSorter("name", null, GetSortType()));
      break;
    case "fileSize" :
      items.sort(GetSorter("length", "int", GetSortType()));
      break;
    case "fileType" :
      items.sort(GetSorter("mime", null, GetSortType()));
      break;
    case "createdTime" :
      items.sort(GetSorter("createdTime", null, GetSortType()));
      break;
    default :
      items.sort(GetSorter("name"));
  }
}

function DoRender() {
	switch (GetRenderPattern()) {
	case "thumbnail":
		ThumbnailRender();
		break;
	case "detail":
		DetailRender();
		break;
	case "icon":
		IconRender();
		break;
	default:
		IconRender();
	}
}

window.onload = function() {
	Initialize();
	DoRender();
}

function Refresh() {
	DoSort();
	DoRender();
}

function Ok() {
	var rets = [];
	for (var i=0; i<ViewerItem.SelectedItems.length; i++)
		rets.push(ViewerItem.SelectedItems[i].path);
	top.returnValue = rets;
}

function ChangeStyleSheet($usingSheet) {
	var sheets = document.getElementsByTagName('link');
	for (var i=0; i<sheets.length; i++) {
		sheets[i].disabled = "true";
	}
	sheets($usingSheet).disabled = "";
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

function Upload() {
	var ret = window.showModalDialog("uploadResource.jsp?timestamp=" + new Date().getTime(), "dialogWidth:620px;dialogHeight:400px;help:no;scrollbars:yes;status:no");
	if (ret) {
		var url = ResolveURL(window.location.href);
		if (url) {
			var params = [];
			for (var p in url.parameters) {
				if (p == "day")
					params.push("day=" + new Date().getTime());
				else
					params.push(p + "=" + url.parameters[p]);
			}
			window.location = params.length == 0 ? url.href : url.href + "?" + params.join("&");
		}
	}
}

function Download(){
	if(ViewerItem.SelectedItems.length>0) {
		var url = "download.do?id=";
		var req;
		for (var i=0; i<ViewerItem.SelectedItems.length; i++) {
			window.open(url + ViewerItem.SelectedItems[i].id, "_blank");
//			req = new HttpRequest(url + ViewerItem.SelectedItems[i].id);
//			req.Get();
		}
	}
}

function Delete(){
	if(ViewerItem.SelectedItems.length>0) {
		if (!confirm("确定要删除选中项吗？"))
			return;
		var url = "deleteResource.do?";
		var params = ['timestamp=' + new Date().getTime()];
		var req;
		for (var i=0; i<ViewerItem.SelectedItems.length; i++)
			params.push("id=" + ViewerItem.SelectedItems[i].id);
		req = new HttpRequest(url + params.join("&"));
		req.Get();
		window.location.replace(window.location.href);
	}
}