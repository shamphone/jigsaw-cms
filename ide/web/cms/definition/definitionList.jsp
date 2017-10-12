<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择内容分类</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/objects.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/sitelist.js"/>"></script>
 <script language="JavaScript" type="text/Javascript">
 	var repository= new Repository();
 	var categories = window.dialogArguments.filters;
 	var multiple = window.dialogArguments.multiple;
	var definitionMap = new Array();
	window.onload = function() {
		var listTable = document.getElementById("listTable");

		if(multiple){
			 document.getElementById("header").innerHTML = '<input id="chkAll" type="checkbox"/>';
		}
		
		var oTbody = listTable.tBodies[0];
		for(var i=0;i<categories.length;i++){
	        var definition = repository.getNodeDefinition(categories[i]);
	        if(definition!=null){
	        	definition.ID = definition.getID();
	        	definitionMap[definition.ID] = definition;
	        	var row = oTbody.insertRow();
	        	row.id = definition.ID;
	        	var cell1 = row.insertCell();
	        	cell1.style.paddingLeft = '9px';
	        	cell1.innerHTML = multiple?'<input type="checkbox"/>':'<input type="radio" name="def"/>';
	        	var cell2 = row.insertCell();
	        	cell2.innerHTML = i+1;
	        	var cell3 = row.insertCell();
	        	cell3.innerHTML = definition.getName();
	        }
		}
		
		ListTable.Init(listTable,multiple);
    }
	
	function ok(){
		var selectedRows = ListTable.GetSelectedRow();
		if(selectedRows.length>0){
			var definitions = new Array();
			for(var i=0;i<selectedRows.length;i++){
				var definition = definitionMap[selectedRows[i].id];
				definitions.push(definition);
			}
			window.parent.returnValue=definitions;
			window.parent.close();
		}else{
			alert("请选择大纲！")
		}
	}
	function _close(){
         window.parent.close();
    }
 </script>    
</head>
<body ><form action="#">
      <table  width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <div class="insetDiv" style="height:198px" id="navcontainer">
            	<table style="background: white;" id="listTable" cellpadding="2" cellspacing="0">
            		<thead>
           				<th  width="28px" id="header">选择</th>
           				<th width="28px" >&nbsp;</th>
           				<th>大纲名称</th>
            		</thead>
					<tbody id="templateList">
					</tbody>
				</table>
            </div>
          </td>
        </tr>
      </table>
      <div class="operation">
        <button type="button" onclick="ok(this.form)" class="commonbut" id="tijiao">确定</button>
        <button type="button" onclick="_close()" class="commonbut" id="back">取消</button>
      </div>   
      </form>
</body>
</html>