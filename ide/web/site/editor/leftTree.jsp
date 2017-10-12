<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
<head>
<title>栏目树</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/channeltree.js"/>"></script>

<script language="Javascript" type="text/Javascript">
function doInit(){
	var Editor = top.Editor;
	var oTemplate = Editor.template;
	
	var channelTree = new CHANNELTree(oTemplate);
	Editor.channelTree = channelTree;
	document.getElementById("channelTree").innerHTML = channelTree.toString();	
	channelTree.expand();
	templateTitle.innerHTML = oTemplate.displayName;
};

function collapseMenu(){
	window.parent.document.getElementById("main").cols="10,*"
	document.getElementById("mainPanel").style.display = "none";
	document.getElementById("menuPanel").style.display = "block";
	document.body.style.backgroundColor = "buttonface"
}

function expandMenu(){
	window.parent.document.getElementById("main").cols="230,*"
	document.getElementById("mainPanel").style.display = "block";
	document.getElementById("menuPanel").style.display = "none";
}
</script>
</head>
<body onload="doInit()" style="border-right:3px inset;border-right:1px inset\9;">
<div style="height: 100%;" id="mainPanel">
<div  onclick="collapseMenu()" style="cursor: default;" id="blockTitle"> 
<span class="hiddenText" id="templateTitle"></span>
<span><img title="折叠菜单" src='<html:rewrite module="/common" page="/editor/images/arrow_rtl.gif"/>'></span> 
</div>

<div id="channelTree" style="width:100%;height:96.6%;background-color:#ffffff;border:2px inset;overflow:scroll;padding-top:1px; ">
</div>
</div>

<div title="展开菜单" onclick="expandMenu()" id="menuPanel" style="width:100%;height:100%;cursor:default; display: none;">
<span style="margin-left:2px;"><img src="<html:rewrite module="/common" page="/editor/images/arrow_ltr.gif"/>"></span>
 </div>

</body>
</html>
