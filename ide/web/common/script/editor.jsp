<%@ page contentType="text/javascript; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
 /**
  * 打开查找用户对话框
  */
function selectUser(IDObject,NameObject){
var url;
url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent("查找用户"))+"&url="+encodeURI('<html:rewrite module="/cms" page="/category/searchUser.do"/>');
var arr = showModalDialog(url,window,"dialogWidth:600px;dialogHeight:570px;help:no;scrollbars:yes;status:no");
if(arr!=null){
            NameObject.value = arr.commonname;
            IDObject.value = arr.ID;
          }
}
 /**
  * 打开查找机构对话框
  */
function selectOrg(IDObject,NameObject){
var url;
url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent("查找机构"))+"&url="+encodeURI('<html:rewrite module="/cms" page="/category/searchOrg.do"/>');
var arr = showModalDialog(url,window,"dialogWidth:600px;dialogHeight:570px;help:no;scrollbars:yes;status:no");
if(arr!=null){
            NameObject.value = arr.commonname;
            IDObject.value = arr.ID;
          }
}
/**
 * 内容选择器（开始）
 * @param 上传对象
 */
function onContentSelectorChange(contentSelector, deleteButton, upperButton, lowerButton) {
    var oOptions = contentSelector.options;
    if (oOptions.length > 0 && contentSelector.selectedIndex != -1) {
		upperButton.disabled = oOptions[0].selected;
		lowerButton.disabled = oOptions[oOptions.length - 1].selected;
		deleteButton.disabled = false;
    } else {
		upperButton.disabled = true;
		lowerButton.disabled = true;
		deleteButton.disabled = true;
    }
}
 /**
  * 添加默认查找内容
  */
function appendDefaultContent(contentSelector) {
    var url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent("查找内容"))+"&url="+encodeURI('<html:rewrite module="/cms" page="/content/searchCont.do"/>');
    var arr = showModalDialog(url,window,"dialogWidth:600px;dialogHeight:570px;help:no;scrollbars:yes;status:no");
    if(arr!=null){

        for (var i=0; i<arr.length; i++) {
            var content = arr[i];
            if (!isContentExist(contentSelector, content.ID)) {
                var oOption = document.createElement("OPTION");
                contentSelector.options.add(oOption);
                oOption.value = content.ID;
                oOption.innerText = content.title;
            }
        }
    }
    contentSelector.fireEvent("onchange");
}
 /**
  * 判断内容是否存在
  */
function isContentExist(contentSelector, value) {
    var is = false;
    var oOptions = contentSelector.options;
    for (var i=0; i<oOptions.length; i++) {
        if (oOptions[i].value == value) {
            is = true;
            break;
        }
    }
    return is;
}
 /**
  * 删除默认内容
  */
function deleteDefaultContent(contentSelector) {
    var oOptions = contentSelector.options;
    for (var i=0; i<oOptions.length; ) {
        var oOption = oOptions[i];
        if (oOption.selected)
            oOptions.removeChild(oOption);
        else
            i++;
    }
    contentSelector.fireEvent("onchange");
}
// 上移
function upperShiftDefaultContent(contentSelector) {
    var oOptions = contentSelector.options;
    for (var i=1; i<oOptions.length; i++) {
		if (oOptions[i].selected)
			oOptions[i].swapNode(oOptions[i - 1]);
    }
    contentSelector.fireEvent("onchange");
}
// 下移
function lowerShiftDefaultContent(contentSelector) {
    var oOptions = contentSelector.options;
    for (var i=oOptions.length - 2; i>-1; i--) {
		if (oOptions[i].selected)
			oOptions[i].swapNode(oOptions[i + 1]);
    }
    contentSelector.fireEvent("onchange");
}
/**
 * 内容选择器（结束）
 */

/**
 * 图片上传控件验证
 * @param 上传对象
 */
function userPicCheck(picObj,propID){
    var aPicPath = (picObj.value).split('.');
    var sPicStyle = aPicPath[aPicPath.length-1];
    if(sPicStyle!='jpg' && sPicStyle!='JPG' &&
        sPicStyle!='jpeg' && sPicStyle!='JPEG' &&
        sPicStyle!='bmp' && sPicStyle!='BMP' &&
        sPicStyle!='gif' && sPicStyle!='GIF' &&
        sPicStyle!='png' && sPicStyle!='PNG'){
        document.getElementById(propID+"newpic").innerHTML = "";
        document.getElementById(propID+"changFlg").value="false";
        alert("请输入图片格式文件：\n jpg,jpeg,bmp,gif,png");
    } else {
        var picPath = "file://localhost/"+picObj.value;
        var sHtml = '&nbsp;&nbsp;新图片：<img src="'+picPath+'" width="64" height="64" alt="" border="0"/>';
        document.getElementById(propID+"newpic").innerHTML = sHtml;
        document.getElementById(propID+"changFlg").value="true";
    }
}

/**
 * 普通文件上传控件验证
 * @param 上传对象
 */
function userFileCheck(picObj){
    if(picObj.value == null || picObj.value ==""){
        document.getElementById("changFlg").value="false";
    } else {
        document.getElementById("changFlg").value="true";
    }
}
 /**
  * 打开查找内容对话框
  */
function selectContent(IDObject,NameObject){
var url;
url = "<html:rewrite module='/common' page='/modalWrapper.jsp'/>?title="+encodeURIComponent(encodeURIComponent("查找内容"))+"&url="+encodeURI('<html:rewrite module="/cms" page="/content/searchContents.do"/>');
var arr = showModalDialog(url,window,"dialogWidth:600px;dialogHeight:570px;help:no;scrollbars:yes;status:no");
if(arr!=null){
            NameObject.value = arr.Name;
            IDObject.value = arr.ID;
          }
}