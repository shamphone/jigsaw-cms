
//以下是在portlet中共用的方法

/**
 * 选择样式 oInput：用于接收参数的控件，一般是Input控件 styleFilter：样式过滤器，即针对的标签
 */
 function doSelectStyle(oInput,styleFilter){
    
    var url = '/ide/site/css/css.jsp';
    var param=new Object();
    param.styleSheets= new Array();
    /**
    *Firefox edit页面已经丢失相关的window.dialogArguments参数  
    *用Iframe将其submit之前的window保存，然后提交 统一使用window.parent.dialogArguments
    *modified by lkl 2010.09.21
    **/
    param.path=window.parent.dialogArguments.page;
    var sheets=window.parent.dialogArguments.styleSheets;
    param.selectedStyle=""+oInput.value;
    param.filter=styleFilter;
    for(var i=0;i<sheets.length;i++){
      var href=sheets[i].href;
    /**
	 * luobin modified in 2010-03-18 bug 修改目的：下拉列表包含/portal/style.css.jsp
	 * 修改描述：!((href.indexOf('/fck_')>0)||(href.indexOf('/portal/')>0)||(href.length==0))改成
	 * !((href.indexOf('/fck_')>=0)||(href.indexOf('/portal/')>=0)||(href.length==0))
	 */
      if(!((href.indexOf('/fck_')>=0)||(href.indexOf('/portal/')>=0)||(href.length==0)))
         param.styleSheets.push(sheets[i]);
    }
    
    var value=showDialog(url, param, 470,460);
    if( value != null && value != '' ){
    	if(value == ":none"){
    		oInput.value = "";
    	}else{
    		oInput.value = value;
    	}
    }
}
/**
 * 插入占位符界面分页展示页面的选择Panel
 */
function selectPanelPortlet(oSelect){
var index=oSelect.options[oSelect.selectedIndex].value;
var portletType=document.getElementsByName("portletType");
for(i=0;i<portletType.length;i++){
portletType[i].style.display="none";
}
portletType[parseInt(index)].style.display="";
}


/**
 * 分页展示页面的选择Panel
 */
function selectPanel(oSelect){
var index=oSelect.options[oSelect.selectedIndex].value;
var fieldsets;
if(document.all){
	fieldsets=oSelect.form.getElementsByTagName("fieldset");
}else{
	fieldsets=document.getElementsByTagName("fieldset");
}
for(i=0;i<fieldsets.length;i++){
fieldsets[i].style.display="none";
}
fieldsets[parseInt(index)].style.display="";
}

function selectStyle(oInput){
doSelectStyle(oInput,null);
}

function showEditor(portletName,url){
var x=window.screenTop;
var y=window.screenLeft;
var ret=window.showModalDialog('/portal/modalWrapper.jsp?title='+encodeURIComponent(portletName)+'&url='+encodeURIComponent(url),'','dialogHeight:500px;dialogWidth:500px;status:no');
if(ret!=null){
  window.location.reload();
}
}


/**
 * navigator/edit.jsp
 */

 function selectAll(oSelect){
    oSelect.multiple=true;
    var obj=oSelect.options;
    for(var i=0;i<obj.length;i++)
    {
      obj[i].selected=true;
    }
  }
  function optionExists(value,oSelect){
   if(oSelect.options!=null)
    for(var j=0;j<oSelect.options.length;j++){
      if(oSelect.options[j].value==value)
      return true;
    }
    return false;
  }


/**
 * 同步 隐藏域和 checkbox
 */
function syn_checkbox(oCheck,sName){
 oCheck.form.elements[sName].value=""+oCheck.checked;
}

/**
 * field/edit.jsp
 */

/**
 * 控制显示pannel；
 */


function doScroll(elem){
if(elem!=null){
var oElem=document.all(elem,0);
if(oElem!=null){
window.scrollTo(oElem.offsetLeft,oElem.offsetTop);
}
}
}


function div_display(form){
	portletId = form.id
	if( form.selectItem.value==1 ) {
				eval("div1_"+portletId)  .style.display="";
				eval("div11_"+portletId) .style.display="";
				eval("div2_"+portletId)  .style.display="none";
				eval("div22_"+portletId) .style.display="none";
				eval("div3_"+portletId)  .style.display="none";
				eval("div33_"+portletId) .style.display="none";

	} else if(form.selectItem.value==2) {
				eval("div1_"+portletId)  .style.display="none";
				eval("div11_"+portletId) .style.display="none";
				eval("div2_"+portletId)  .style.display="";
				eval("div22_"+portletId) .style.display="";
				eval("div3_"+portletId)  .style.display="none";
				eval("div33_"+portletId) .style.display="none";

				setFolderURL(form);
			} else if(form.selectItem.value==3) {
				eval("div1_"+portletId)  .style.display="none";
				eval("div11_"+portletId) .style.display="none";
				eval("div2_"+portletId)  .style.display="none";
				eval("div22_"+portletId) .style.display="none";
				eval("div3_"+portletId)  .style.display="";
				eval("div33_"+portletId) .style.display="";

				setContentURL(form);
	}
}

function div_auto_display(form){
	portletId = form.id
	if( form.auto.value=="true" ) {
				eval("div_1_"+portletId).style.display="none";
				eval("div_2_"+portletId).style.display="none";
	} else {
				eval("div_1_"+portletId).style.display="";
				eval("div_2_"+portletId).style.display="";
	}
}

function setFolderURL(form){
contentFolderId = form.contentFolder.value;
form.url.value = form.path.value + "/" + contentFolderId + "/index.fdp";

}

function doPost(form,i){
form.pageNo.value =i;
form.submit();
}
function newSearch(form){
form.pageNo.value =1;
form.submit();
}
function setContentURL(form){
if (form.currentContent == null)
{
	return;
}
contentFolderId = form.content.value;
form.url.value = form.path.value + "/" + contentFolderId + "/content.fdp?contentId=" + GetRadioValue(form.currentContent);
}
function changeContentFolder(theForm){
// theForm.folder_id[0].value=theForm.folder_id[1].value;
	theForm.sub.value=0;
	theForm.submit();
}
function subForm(form){
	form.sub.value=1;
}
function upload(form){
	if(GetRadioValue(form.flag) =="2"){
	if(Trim(form.imgName.value) ==""){
		alert("图片名称不能为空!");
		return false;
	}
	}
	if (GetRadioValue(form.flag)=="1"){
	if(confirm('如果覆盖以前的的文件,以前应用这个文件的页面都会相应的改变。')){
	}else{
		return false;
	}
	}
	form.sub.value=2;
	return true;
}
function changeRule(form){
	if (form.rule.value=="custom"){
	form.contentFolderId.size="5"
	form.contentFolderId.multiple="true"
	}else{
	form.contentFolderId.size="1"
	form.contentFolderId.multiple=""
	}
}
function div_auto_display_rt(form){
	portletId = form.id;
	if( form.type.value=="auto" ) {
				eval("div_"+portletId).style.display="none";
	} else {
				eval("div_"+portletId).style.display="";
	}
}
function common_image_selectImg(theForm, theImage){
theForm.source.value=theImage.alt;
theForm.all("previewImg").src=theImage.src;
return false;
}
function nf_change(theForm){
theForm.sub.value=0;
theForm.submit();
}
function pc_change(theForm){
// if(theForm.source.options[theForm.source.selectedIndex].value=="empty")
theForm.sub.value=0;
theForm.submit();
}

function pc_submit(theForm){
if(theForm.source.options[theForm.source.selectedIndex].value=="empty"){
	if (Trim(theForm.fileName.value)==""){
		alert("名称不能为空！")
		return false;
	}
	theForm.source.options[theForm.source.selectedIndex].value=theForm.resourceFolderId.options[theForm.resourceFolderId.selectedIndex].value +"/" + theForm.fileName.value +".jspf";

}
return true
}

function review_submit(theForm){
if(theForm.forum.options[theForm.forum.selectedIndex].value=="empty"){
	if (Trim(theForm.fileName.value)==""){
		alert("名称不能为空！")
		return false;
	}
}
return true
}

function st_submit(form){
str=form.text.value;
str=str.replace(/</g,"&lt");
str=str.replace(/>/g,"&gt");
str=str.replace(/\n/g,"<br>");
alert(str)
form.text.value=str;
}
function review_post_submit(theform){
	if (Trim(theform.forumposttitle.value)==""){
		alert("标题不能为空！")
		return false;
	}
return true;
}
function static_select(oForm){
  var oSelect=oForm.elements["source"];
  if(oSelect.selectedIndex>0){
   var req=getXMLHttpRequest();
   var url=encodeURI(oSelect.options[oSelect.selectedIndex].title);
   var callback=function(){
       if((req.readystate==4)&&(req.status==200)){
       var newText=req.responseText;
          document.all("content").value=newText;
          document.all("editor").contentWindow.reloadHTML(newText);
       }
   }
   sendRequest(req,url,callback);
  }
}
function static_submit(oForm){
oForm.submit();
}
function static_delete(submitter){
if(confirm("删除这个页面片断将使所有引用这个页面片断的页面无法正常显示，确认删除？")){
var oSelect=submitter.form.elements["source"];
  if(oSelect.selectedIndex>0){
   var req=getXMLHttpRequest();
   var value=oSelect.options[oSelect.selectedIndex].value;
   var url="/ide/statics/deleteContents.do";
   url=url+"repositoryId=static&content="+encodeURIComponent(value);
   var callback=function(){
       if((req.readystate==4)&&(req.status==200)){
       oSelect.options.remove(oSelect.selectedIndex);
       oSelect.selectedIndex=0;
       document.all("editor").contentWindow.reloadHTML("");
       }
   }
   sendRequest(req,url,callback);
  }
}
}
function static_create(submitter){
  document.all.editPanel.style.display="none";
    document.all.createPanel.style.display="";
    document.all("create").value="true";
}
function static_edit(submitter){
  document.all.editPanel.style.display="";
    document.all.createPanel.style.display="none";
    document.all("create").value="false";
}
function html_submit(oForm){
oForm.elements["text"].value=oForm.all("staticContentEditor").innerHTML;
oForm.submit();
}

function portal_toggleSelect(selected){
  if(!enableToggle) return;
  selected.rows[0].style.display="";
}
function portal_toggleUnSelect(selected){
  if(!enableToggle) return;
  selected.rows[0].style.display="none";
}

