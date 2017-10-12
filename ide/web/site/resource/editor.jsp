<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%
	/**
	 * 资源管理，用来选择一个或者多个文件，并管理对应的模板的资源。
	 * 输入：
	 *    1. templateID：模板ID
	 * 输出：
	 *    选中的文件列表
	 */
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资源管理</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="style.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/XTreeUtils.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smresource.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/webdav.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="editor.js"></script>
<script language="Javascript" type="text/Javascript">
Globals.contextPath="<html:rewrite page="" module=""/>";
ResourceEditor.templateName = '<%= ""+request.getParameter("template")%>';
ResourceEditor.contextPath = '<%= ""+request.getParameter("path")%>'; 
</script>
</head>
<body onload="ResourceEditor.doInit()">
    <div id="DLGToolbar20">
      <span onclick='doCreate()' ><html:img page="/images/new_folder.gif" module="/common" alt="新建文件夹" border="0"/><span>新建文件夹</span></span>
      <span onclick='doEdit()'><html:img page="/images/rename.gif" module="/common" alt="重命名文件或者文件夹"  border="0"/><span>修改</span></span>
      <span onclick="doDelete()" ><html:img page="/images/delete.gif" module="/common" alt="删除文件或者文件夹"  border="0"/><span>删除</span></span>
      <span onclick="doCut()" ><html:img page="/images/cut.gif" module="/common" alt="转移到"  border="0"/><span>转移到</span></span>
      <span onclick="doCopy()" ><html:img page="/images/copy.gif" module="/common" alt="复制到"  border="0"/><span>复制到</span></span>
      <span onclick="doExport()" ><html:img page="/images/import.gif" module="/common" alt="下载"  border="0"/><span>下载</span></span>
      <span onclick='doImport()' ><html:img page="/images/export.gif" module="/common" alt="上传"  border="0"/><span>上传</span></span>
      <span onclick='doSelectAll()' ><html:img page="/images/checked.gif" module="/common" alt="全选/全不选"  border="0"/><span>全选/全不选</span></span>
      <span onclick='doRefresh()' ><html:img page="/images/refresh20.gif" module="/common" alt="刷新"  border="0"/><span>刷新</span></span>
    </div>
<div style="width:200px;height:200px;display:none;z-index:15;position:absolute;left:0px;top:0px;background-color:buttonface;border:2px outset;" id="enlargeDiv" onmouseover="ResourceEditor_DoMouseOverFloatWindow()" onmouseout="ResourceEditor_DoMouseOutFloatWindow()">
	<div class="insetDiv" style="width: 200px; height: 180px"><img id="enlargeImg" alt="放大图片" src="/ide/common/images/sourceimg.gif" border="0" align="absmiddle"/></div>
	<div style="text-align:center">高度：<span id="imgHeight"></span>px 宽度：<span id="imgWidth"></span>px</div>
</div>
<div class="insetDiv" style="width: 560px; height:480px;" id="fileList">&nbsp;</div>
</body>
</html>
