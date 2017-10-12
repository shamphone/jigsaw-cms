<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link type="text/css" rel="stylesheet" href="<html:rewrite page="/style/style.css" module="/site"/>"/>
<link type="text/css" rel="stylesheet" href="<html:rewrite page="/style/selector.css" module="/site"/>"/>
<link type="text/css" rel="stylesheet" href="<html:rewrite page="/style/tree.css" module="/site"/>"/>
<script language="javascript" src="<html:rewrite page="/script/common.js" module="/common"/>" type="text/javascript"></script><script language="javascript" src="<html:rewrite page="/xtree/tree.js.jsp" module="/common"/>" type="text/javascript"></script><title>资源管理</title>
</head>
<body>
<html:form action="/delete.do" method="POST">
  <input type="hidden" name="path" value="<bean:write name="folder" property="path"/>"/>
  <div class="selector">    查找范围：
    <html:select property="folder" onchange="window.location='folderItems.do?folder='+encodeURIComponent(this.value)">
      <fulong:treeSelect name="folderList" objectId="iFolder">
        <bean:define id="id" name="iFolder" property="path"/>
        <html:option value='<%= ""+id%>'>
          <logic:equal name="iFolder" property="depth" value="2">我的资源库</logic:equal>
          <logic:greaterThan name="iFolder" property="depth" value="2">
            <bean:write name="iFolder" property="displayPath(&nbsp;&nbsp;&nbsp;&nbsp;)" filter="false"/>
          </logic:greaterThan>
        </html:option>
      </fulong:treeSelect>
    </html:select>
    <!--a href="javascript:history.back();" class="activeLink"><html:img alt="返回"  module="/common" page="/textEditor/buttonimage/standard/back.gif"  border="0" styleClass="ico"/></a-->
    <logic:greaterThan name="folder" property="depth" value="2">
      <a class="activeLink" href="folderItems.do?folder=<fulong:encode name="parentFolder" property="path"/>">
        <html:img page="/textEditor/buttonimage/standard/up.gif" module="/common" alt="向上一级" border="0" styleClass="ico"/>
      </a>
    </logic:greaterThan>
    <logic:equal name="folder" property="depth" value="1">
      <a class="inactiveLink">
        <html:img page="/textEditor/buttonimage/standard/up.gif" module="/common" alt="向上一级" border="0" styleClass="ico"/>
      </a>
    </logic:equal>
    <a href="create.do?folder=<fulong:encode name="folder" property="path"/>" class="activeLink">
      <html:img page="/textEditor/buttonimage/standard/new_folder.gif" module="/common" alt="新建文件夹" border="0" styleClass="ico"/>
    </a>
    <logic:greaterThan name="folder" property="depth" value="1">
      <a class="activeLink" href="edit.do?folder=<fulong:encode name="folder" property="path"/>">
        <html:img module="/common" page="/textEditor/buttonimage/standard/rename.gif" alt="修改文件夹" border="0" styleClass="ico"/>
      </a>
    </logic:greaterThan>
    <logic:equal name="folder" property="depth" value="1">
      <a class="inactiveLink">
        <html:img module="/common" page="/textEditor/buttonimage/standard/rename.gif" alt="修改文件夹" border="0"/>
      </a>
    </logic:equal>
    <a class="activeLink" href="javascript:doDelete(this)">
      <html:img title="删除文件夹" styleClass="ico" module="/common" page="/textEditor/buttonimage/standard/delete.gif" border="0"/>
    </a>
    <a class="activeLink" href="javascript:doCut(this)">
      <html:img title="剪切" styleClass="ico" module="/common" page="/textEditor/buttonimage/standard/cut.gif" border="0"/>
    </a>
    <a class="activeLink" href="javascript:doCopy(this)">
      <html:img title="复制" styleClass="ico" module="/common" page="/textEditor/buttonimage/standard/copy.gif" border="0"/>
    </a>
    <a class="activeLink" id="pasteButton" href="javascript:doPaste(this)">
      <html:img title="粘贴" styleClass="ico" module="/common" page="/textEditor/buttonimage/standard/paste.gif" border="0"/>
    </a>
    <a class="activeLink" href="preUpload.do">
      <html:img title="上传" styleClass="ico" module="/common" page="/textEditor/buttonimage/standard/export.gif" border="0"/>
    </a>
    <a class="activeLink" href="clipboard.jsp">
      <html:img title="查看剪贴板" styleClass="ico" module="/common" page="/textEditor/buttonimage/standard/clipboard.gif" border="0"/>
    </a>
  </div>
  <div class="main">
    <div id="folderList">
      <logic:iterate id="childFolder" name="childFolders">
        <div class="folderGrid">
          <div class="image">
            <a href="javascript:this.location='folderItems.do?folder='+encodeURIComponent('<bean:write name="childFolder" property="path" filter="false"/>')">
              <img width="90" height="90" alt="<bean:write name="childFolder" property="name"/>" src="../../common/images/doctype/folderpic.gif" border="0"/>
            </a>
          </div>
          <div class="itemMark">
            <input onclick="synButtons()" type="checkbox" name="item" value="<bean:write name="childFolder" property="path"/>"/>
            <span>
              <bean:write name="childFolder" property="name"/>
            </span>
          </div>
        </div>
      </logic:iterate>
    </div>
    <div id="fileList">
      <logic:iterate id="childFile" name="childFiles">
        <div class="folderGrid">
          <div class="image">
            <img width="90" height="90" alt="<bean:write name="childFile" property="name"/>" src="/resources/<html:rewrite page="" module=""/><bean:write name="owner" property="ID"/>/<bean:write name="childFile" property="name"/>"/>
          </div>
          <div class="itemMark">
            <input type="checkbox" name="item" value="/resources/<html:rewrite page="" module=""/><bean:write name="owner" property="ID"/>/<bean:write name="childFile" property="name"/>" onclick="synButtons()"/>
            <span>
              <bean:write name="childFile" property="name"/>
            </span>
          </div>
        </div>
      </logic:iterate>
    </div>
  </div>
  <div class="button">
    <button class="commonbut" id="commonbut" onclick="ok(this)">确认</button>
    <button class="commonbut" id="back" onclick="window.close()">取消</button>
  </div>
</html:form>
<script type="text/javascript" language="javascript">
    function doGoUp(url){
      window.location=url;
      return false;
    }
function ok(submitter){
  var item = submitter.form.item;
  var oInput=window.opener.document.getElementById("txtUrl");
  if(oInput!=null){
       oInput.value='<html:rewrite page="" module=""/>'+getValue(item,"");
   }

   var ret=new Object();
   ret='<html:rewrite page="" module=""/>'+getValue(item,"");
   window.returnValue=ret;
   window.close();
  if(oInput!=null){
       oInput.blur();
       oInput.focus();
  }

}
function doExport(submitter){
  submitter.form.action="export.do";
  submitter.form.submit();
}
function synButtons(){
  var theForm=document.forms("resourceForm");
  var checkboxes=theForm.elements["item"];
  var findselected=false;
  var i=0;
  if(checkboxes!=null){
    if(checkboxes.tagName!=null){
      findselected=checkboxes.checked;
   }
   else{
     while((i<checkboxes.length)&&(!findselected))
     {
       findselected=checkboxes[i].checked;
       i++;
    }
  }
}
 //   theForm.elements("deleteButton").disabled=!findselected;
 //   theForm.elements("cutButton").disabled=!findselected;
 //   theForm.elements("copyButton").disabled=!findselected;
 //   theForm.elements("exportButton").disabled=!findselected;

}
function doDelete(submitter){
  if(confirm('删除文件将有可能导致这个文件夹中的文件在页面上的原有的链接无法正常使用,确认删除?')){
    document.forms("resourceForm").submit();
  }
}
function doPaste(submitter){
  submitter.disabled=true;
  document.forms("resourceForm").action="paste.do";
  document.forms("resourceForm").submit();
}
function doImport(submitter){
  submitter.disabled=true;
  document.forms("resourceForm").action="prepareImport.do";
  document.forms("resourceForm").submit();
}

function doCopy(submitter){
  var checkboxes=document.forms("resourceForm").elements["item"];
  var selected="";
  for(i=0;i<checkboxes.length;i++){
    if(checkboxes[i].checked){
      selected=selected+checkboxes[i].value+";";
    }
  }
  setCookie("clipboard",selected);
  setCookie("operation","copy");
  synClipboard();
}
function doCut(submitter){
  var checkboxes=document.forms("resourceForm").elements["item"];
  var selected="";
  for(i=0;i<checkboxes.length;i++){
    if(checkboxes[i].checked){
      selected=selected+checkboxes[i].value+";";
    }
  }
  setCookie("clipboard",selected);
  setCookie("operation","cut");
  synClipboard();
}
function synClipboard(){
   var cut=(getCookie("operation")=="cut");
    var checkboxes=document.forms("resourceForm").elements["item"];
    var selected=getCookie("clipboard");
    document.getElementById("pasteButton").disabled=(selected==null||selected=="");
    if(checkboxes!=null){
       for(i=0;i<checkboxes.length;i++){
         if(cut){
           if(selected.indexOf(checkboxes[i].value+";")>=0)
           checkboxes[i].parentElement.parentElement.className="folderCut";
           else
           checkboxes[i].parentElement.parentElement.className="folderGrid";
         }else{
           checkboxes[i].parentElement.parentElement.className="folderGrid";
         }
       }
     }
}

synClipboard();
synButtons();
</script></body>
</html>
