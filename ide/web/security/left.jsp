<%@ page contentType="text/html;charset=UTF-8" language="java" session="false"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色列表</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/left.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/module/modules.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/objects.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/nodetree.js"/>"></script>
<script type="text/javascript">
var UMTree;
function doInit(){
	if(!document.all){
		document.getElementById("navcontainer").style.height = document.body.clientHeight-55;
	}
	UMTree = new NodeTree('root-group','childGroup','title');
	document.getElementById("navcontainer").innerHTML = UMTree.toString();
	CModuleCollection.render(document.getElementById("modules"),"security");
};
/**
 * 节点被选中的时候的事件响应
 * @param id
 * @return
 */
function NodeTree_OnClick(id){
	top.frames["list"].location='role.do?ID='+id;
};
</script>

</head>
<body onload="doInit()">
<div id="blockTitle">组织模型</div>
<div id="navcontainer"></div>
<div id="modules" align="right"></div>
</body>
</html>