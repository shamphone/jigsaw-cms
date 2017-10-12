<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>属性管理</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/pmstree.js"></script>
<script language="JavaScript" type="text/Javascript">	
var excludes = new Array();
var includes = new Array();
  <logic:present parameter="exclude">
  <bean:parameter id="filterType" name="exclude" multiple="true"/>
    <logic:iterate id="ft" name="filterType">
  	excludes.push('<bean:write name="ft"/>');
  </logic:iterate>
  </logic:present>
  <logic:present parameter="include">
  <bean:parameter id="leftType" name="include" multiple="true"/>
  <logic:iterate id="lt" name="leftType">
  includes.push('<bean:write name="lt"/>');
  </logic:iterate>
  </logic:present>
</script>
<script language="JavaScript" type="text/Javascript">       
var PMS = null;
function doInit(){
	PMS = new PMSTree('<bean:write name="definition" property="ID"/>',excludes, includes);
	document.getElementById("definitionTree").innerHTML=PMS.toString();
	if(!document.all){
		var spans=document.getElementById("DLGToolbar").getElementsByTagName("span");
	    for(var i=0;i<spans.length;i++){
	         if((spans[i].className == null)||(spans[i].className == "")){
		         var spanElement = spans[i];
		         spanElement.className="DLGButton";
		         //在火狐下添加浮动样式 目前样式需调整
		         if(i%2 == 0){
		        	 spanElement.onmousemove = function(){
			        	 this.className="DLGButton DLGButton_On_Over";
			 		 };
			 		 spanElement.onmouseout = function(spanElement){
			 			this.className="DLGButton";
			 		 };   
		         }
	         }    
	    }
	    document.body.style.overflow = "hidden";
	}
};
/**
 * 处理按钮选中事件
 */
PMS_Select	=	function(id){
	var node = PMSTreeNodes[id];
	document.getElementById("editSpan").disabled = false;
	document.getElementById("btnOk").disabled =  !node.isEnabled();
	document.getElementById("delSpan").disabled= node.isProtected();	
	PMS.selectedValue.name = node.getDisplayName();
	PMS.selectedValue.type = node.getType();
	PMS.selectedValue.ID = id;
	PMS.selectedValue.refID = node.getReferenceDefID();
	//id中带有“.”说明为子节点 不能删除和修改 by mali
	if(id.indexOf(".")!=-1){
		document.getElementById("editSpan").disabled = true;
		document.getElementById("delSpan").disabled= true;	
	}
	return true;
};

function ok(aForm){
	//禁止在属性选择器中选择跟分类或不选,已防止出现返回值"undefined"的情况    modified by 2010-8-19 mali
		if(typeof(PMS.selectedValue.ID) != "undefined"){
	       	window.returnValue=PMS.selectedValue;
	       	window.close();
		}else{
			PMS.selectedValue.ID = "";
			PMS.selectedValue.name = "";
			window.returnValue=PMS.selectedValue;
			window.close();
		}
}
</script>
<base target="_self" />
</head>
<body onload="doInit()">
      <div id="DLGToolbar" style="text-align:left;">
      <span onclick="PMS_Create(PMS)" ><html:img page="/images/newprop.png" module="/common" alt="添加属性" border="0"/><span>新建</span></span>
      <span id="editSpan" onclick="PMS_Edit(PMS)"><html:img page="/images/editprop.png" module="/common" alt="修改属性"  border="0"/><span>修改</span></span>
      <span id="delSpan" onclick="PMS_Delete(PMS)" ><html:img page="/images/delete.png" module="/common" alt="删除属性"  border="0"/><span>删除</span></span>
      </div>
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <div class="insetDiv" style="height:240px;width:305px;" id="definitionTree"></div>
          </td>
        </tr>
        </table>
         <div class="operation">        
          <span style="position:absolute;left:12px"><input type="checkbox" onclick="PMS.enableAll(this.checked)"><span id="OpenOff">全部可选</span></span>
          <button type="button" onclick="ok(this.form)" class="commonbut" id="btnOk">确定</button>
          <button type="button" onclick="window.close()" class="commonbut" id="BtnCancel">取消</button>
        </div>   
  </body>
  </html>
