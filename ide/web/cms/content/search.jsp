<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">内容选择</tiles:put>
	<tiles:put name="dialog">
 <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
 <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/cmstree.js"></script>
<script language="Javascript" type="text/Javascript" src="../classes/objects.js"></script>
<script type="text/Javascript" language="Javascript">
	var repository = new Repository();
	var tree;
	var definition = repository.getNodeDefinition("<bean:write  name="rootId"/>");
	window.onload = function(){
		if(!document.all){
			document.getElementsByClassName("operation")[0].style.top = document.body.clientHeight-40;
		}
		 tree = new CMSTree(definition);
		 //by mali 修正内容选择占位符不显示左侧导航的脚本错误
		 if(document.getElementById("navcontainer")){
			document.getElementById("navcontainer").innerHTML = tree.toString();
		 	tree.expand();
		 }
	}
		
	var results = new Array();
	function ok() {
		window.returnValue = results;
		window.close();
	}
	function CMSTree_OnClick(categoryID){
		tree.definitionID=categoryID;

		var aas = "";
		<logic:equal value="undefined" parameter="sort">
		sort = "asc";
		</logic:equal>
		<logic:equal value="asc" parameter="sort">
		sort = "asc";
		</logic:equal>
		<logic:equal value="desc" parameter="sort">
		sort = "desc";
		</logic:equal>
		document.getElementById("selectContent").src = 'doSearch.do?definition=' + categoryID + '&multiple=' + '<bean:write name="isMutiple" ignore="true"/>'+'&searchText=' + '<bean:write name="searchText" ignore="true"/>'+'&sortproperty=' + '<bean:write name="orderfield" ignore="true"/>&sorttype='+sort + '&selectedProps=' + '<bean:write name="selectedProps" ignore="true"/>';
	}
</script>
		<form action="">
		<table border="0px" width="100%" height="100%" cellpadding="0px" cellspacing="2px">
			<tr>
			<logic:notEqual value="on" parameter="left">
				<td height="100%">
					<div class="treeDiv" style="width:100%" id="navcontainer"></div>
				</td>
				</logic:notEqual>
				<td style="border:2px inset" >
				<iframe marginheight="0" marginwidth="0" scrolling="auto" width="100%" height="100%" id="selectContent" frameborder="0"	src='doSearch.do?definition=<bean:write  name="rootId"/>&multiple=<bean:write name="isMutiple"/>&sortproperty=<bean:write name="orderfield"/><logic:equal value="asc" parameter="sort">&sorttype=asc</logic:equal><logic:equal value="desc" parameter="sort">&sorttype=desc</logic:equal>&searchText=<bean:write name="searchText"/>&selectedProps=<bean:write name="selectedProps"/>'> </iframe>
				</td>
			</tr>
		</table>
		</form>
		<div class="operation">
		<button class="commonbut" id="btnOk" onclick="ok();">确定</button>
		<button class="commonbut" id="btnCancel" onclick="window.close();">关闭</button>
		</div>

	</tiles:put>
</tiles:insert>
