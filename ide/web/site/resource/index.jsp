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
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/webdav.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smeditor.js"/>"></script>		
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smtemplate.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smresource.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/resourcetree.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smsitefactory.js"/>"></script>
<script language="Javascript" type="text/Javascript">
Globals.contextPath="<html:rewrite page="" module=""/>";
var oTree;
var oTemplate;
var oResource;
function doInit(){
	var template = '<%= ""+request.getParameter("template")%>'; //网站模版；
	if(template==null){
		template = '<%= ""+(String)request.getAttribute("template")%>';
	}
	var types = '<%= ""+ request.getParameter("types") %>'; //可接受的文件类型
	if(types==null){
		types = '<%= ""+(String)request.getAttribute("types")%>';
	}
	if(types!='null')
		webFXTreeConfig.fileTypes = types.split(/[\,\|]/);
	oTemplate = siteFactory.getTemplate(template);
	oResource = new SMResource(oTemplate, oTemplate.name, "/");
	//oResource.getChildren();
	oTree = new ResourceTree(oResource); 
	document.getElementById("folderList").innerHTML = oTree.toString();
	oTree.expand();
};

function doOK(){
	if(selectedPathes==null||selectedPathes.length==0){
		alert("请选择文件！");
		return; 
	}
	window.returnValue = selectedPathes;
	window.close();
}
</script>
</head>
<body onload="doInit()">
<table width="100%" cellspacing="0" cellpadding="2" border="0">
	<tr>
		<td valign="top" width="200px"><table width="100%" cellspacing="2" cellpadding="0" border="0">
		<tr><td><div class="insetDiv" style="width: 200px; height: 300px;" id="folderList"></div></td></tr>
		<tr><td><div class="insetDiv" style="width: 200px; height: 180px;" id="enlargeDiv"><img id="enlargeImg" alt="放大图片" src="/ide/common/images/sourceimg.gif" border="0" /></div></td></tr>
		<tr><td>高度：<span id="imgHeight"></span>px 宽度：<span id="imgWidth"></span>px</td></tr>
		</table></td>
		<td valign="top" align="left"><table width="100%" cellspacing="2" cellpadding="0" border="0">
		<tr><td><div class="insetDiv" style="width: 560px; height:480px;" id="fileList">&nbsp;</div></td></tr>
		<tr><td><iframe src='upload.jsp?tempalte=<%=request.getParameter("template")%>' frameborder="0" scrolling="no" width="560px" height="60px" ALLOWTRANSPARENCY="true"></iframe></td></tr>
		</table>		
		</td>
	</tr>
</table>
          <div class="operation">
            <button onclick="doOK(this.form)">确定</button>
	    	<button onclick="window.close()">取消</button>
          </div>

</body>
</html>
