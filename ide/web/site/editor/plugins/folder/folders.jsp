<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
<head>
<title>选择目标文件夹</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smfoldertree.js"/>"></script>
<script type="text/javascript" language="javascript" src="<html:rewrite page='/dialog.js' module='/site'/>"></script>
<script language="Javascript" type="text/Javascript">
Globals.contextPath="<html:rewrite page="" module=""/>";
var channelTree;
var siteFactory = window.parent.dialogArguments.siteFactory;

function doInit(){
	divTree.style.height = (document.body.clientHeight - 60) + "px";
	var oTemplate = window.parent.dialogArguments.template;
	channelTree = new SMFolderTree(oTemplate);
	document.getElementById("divTree").innerHTML = channelTree.toString();	
	channelTree.expand();
};

function doOK(){
	var node = channelTree.getSelected();
	if(node==null){
		alert("请选择文件夹！");
		return false;
	}
	window.parent.returnValue = node.element;
	window.close();
};

function changeTemplate(){
    var template=SiteDialog.selectTemplateBySite();
    if(template!=null){
      reload(template.ID);
    }
}

function reload(templateName){
	var oTemplate = siteFactory.getTemplate(templateName)
	channelTree = new SMFolderTree(oTemplate);
	document.getElementById("divTree").innerHTML = channelTree.toString();	
	channelTree.expand();
}

</script>
</head>
<body onload="doInit()" bgcolor="buttonface">
<div id="divTree" style="width:100%;background-color:#ffffff;border:2px inset;overflow:scroll;padding-top:1px; ">
</div>
<div  class="operation">
<button type="button" onclick="changeTemplate()" style="margin:0px; padding:0px">打开其它模板...</button>
<button  id="submit" type="button" onclick="doOK(this.form)">确定</button>
<button  onclick="window.close();">取消</button>
</div>
</body>
</html>
