<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>引用属性节点集的增删改服务</title>
<base target="_self" />
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="/ide/service/styles.css"/>
<base target="_self" />
<script language="Javascript" type="text/Javascript" src="/ide/common/script/common.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/service/scripts.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/cms/classes/cmsdialog.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/xmlhttp.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script type="text/javascript" language="javascript">
	var config = window.dialogArguments.parameters;
	function doInit() {
		config.populateForm(configForm);
	}
	function ok() {
		if(GetRadioValue(document.all("nodeSourceType"))==null||GetRadioValue(document.all("nodeSourceType"))==""){
			alert("请选择节点来源！");
			return false;
		}
		config.updateFromForm(configForm);
		config.setValue("nodeSourceType",GetRadioValue(document.all("nodeSourceType")));	 
		config.setValue("refPropID",document.all("refPropID").value);
		config.setValue("optPattern",GetRadioValue(document.all("optPattern")));
		window.returnValue = config;
		window.close();
	}
	function selectProperty(categoryID,objID,objName){
		if(categoryID==null){
			alert("请选择分类！");
			return;
			}
		    var result=CMSDialog.PropertyDefinitionSelector(categoryID,null,[9]);
		    if(result!=null){
		    	objID.value=result.ID;
		    	objName.value=result.name;
		      }
	}
	function selectCategory(objID,objName){
		  var definition=CMSDialog.NodeDefinitionSelector();
			if(definition!=null){
		   if(definition.length>0){
		       for(var j=0;j<definition.length;j++){
		    	   objID.value=definition[j].ID;
		    	   objName.value=definition[j].name;
		       }
		     }
		  }
		}
</script>
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="pannelDiv" valign="top">
       <select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
          <option value="0">基本配置</option>
	   </select>
	</td>
	<td id="tdFieldsets" valign="top" align="center">
	  <form name="configForm">
	   <fieldset class="fieldPanel">
		<table cellpadding="2" cellspacing="0" border="0">
		   <tr>
			  <td valign="top">引用节点来源</td>
			  <td colspan="2">
                 <input type="radio" name="nodeSourceType" value="urlParam"/> 从URL参数中获得<br />
                 <input type="radio" name="nodeSourceType" value="userParam"/> 取自当前登录用户节点<br />
                 <input type="radio" name="nodeSourceType" value="siteParam"/> 取自当前网站所有者<br />
              </td>
		   </tr>
		   <tr>
			  <td>分类</td>
			  <td>
                 <input type="hidden" name="CategoryID" />
                 <input type="text" name="CategoryName" readonly="readonly" />
              </td>
			  <td>
			     <button onclick="selectCategory(document.getElementById('CategoryID'),document.getElementById('CategoryName'))">选择...</button>
			  </td>
		   </tr>
		   <tr>
			  <td>引用属性</td>
			  <td>
			     <input type="hidden" name="refPropID" /><input type="text" name="refPropName" readonly="readonly" />
			  </td>
			  <td>
				 <button id="sourceButton" onclick="selectProperty(document.getElementById('CategoryID').value,document.getElementById('refPropID'),document.getElementById('refPropName'))">选择...</button>
		      </td>
			</tr>
			<tr>
			  <td valign="top">操作</td>
			  <td colspan="2">
                 <input type="radio" name="optPattern" value="increase"/> 在原有引用节点集的基础上增加<br />
                 <!-- <input type="radio" name="optPattern" value="replace"/> 替换原有引用节点集<br /> -->
                 <input type="radio" name="optPattern" value="minus"/> 从原有引用节点集中删除<br />
              </td>
			</tr>
			
		</table>
	   </fieldset>	   
	  </form>
	  <div id="toolbar">
		<button onclick="ok()" id="btnOK">确定</button>
		<button onclick="window.close()" id="btnCancel">取消</button>
	  </div>
	</td>
  </tr>	
</table>
</body>
</html>
