<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内容属性中敏感词处理</title>
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
		if(config.getValue("contentOption")=="move"||config.getValue("contentOption")=="recommend"||config.getValue("contentOption")=="copy"){
			document.getElementById("destCategoryTbody").style.display = "";
			document.getElementById("destPropertyTbody").style.display = "none";
			document.getElementById("replacePropertyTbody").style.display = "none";
		}else if(config.getValue("contentOption")=="modifyProperty"){
			document.getElementById("destCategoryTbody").style.display = "none";
			document.getElementById("destPropertyTbody").style.display = "";
			document.getElementById("replacePropertyTbody").style.display = "none";
		}else if(config.getValue("contentOption")=="replace"){
			document.getElementById("destCategoryTbody").style.display = "none";
			document.getElementById("destPropertyTbody").style.display = "none";
			document.getElementById("replacePropertyTbody").style.display = "";
		}else{
			document.getElementById("destCategoryTbody").style.display = "none";
			document.getElementById("destPropertyTbody").style.display = "none";
			document.getElementById("replacePropertyTbody").style.display = "none";
		}
	}
	function ok() {
		config.updateFromForm(configForm);
		config.setValue("sensitiveCategoryID",document.all("sensitiveCategoryID").value);	 
		config.setValue("checkingPropID",document.all("checkingPropID").value);
		config.setValue("sensitivePropID",document.all("sensitivePropID").value);
		config.setValue("contentOption",document.all("contentOption").value);
		config.setValue("destCategoryID",document.all("destCategoryID").value);
		config.setValue("modifyPropID",document.all("modifyPropID").value);
		config.setValue("destValue",document.all("destValue").value);
		window.returnValue = config;
		window.close();
	}
	function selectProperty(categoryID,objID,objName){
		if(categoryID==null){
			alert("请选择分类！");
			return;
			}
		    var result=CMSDialog.PropertyDefinitionSelector(categoryID,null,[1]);
		    if(result!=null){
		    	objID.value=result.ID;
		    	objName.value=result.name;
		      }
	}
	function selectStrOrTextProperty(categoryID,objID,objName){
		if(categoryID==null){
			alert("请选择分类！");
			return;
			}
		    var result=CMSDialog.PropertyDefinitionSelector(categoryID,null,[1,10]);
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
	function showOption(oSelect){
		var thisValue = oSelect.form.contentOption.value;
		if(thisValue=="move"||thisValue=="copy"||thisValue=="recommend"){
			document.getElementById("destCategoryTbody").style.display = "";
			document.getElementById("destPropertyTbody").style.display = "none";
			document.getElementById("replacePropertyTbody").style.display = "none";
		}else if(thisValue=="modifyProperty"){
			document.getElementById("destCategoryTbody").style.display = "none";
			document.getElementById("destPropertyTbody").style.display = "";
			document.getElementById("replacePropertyTbody").style.display = "none";
		}else if(thisValue=="replace"){
			document.getElementById("destCategoryTbody").style.display = "none";
			document.getElementById("destPropertyTbody").style.display = "none";
			document.getElementById("replacePropertyTbody").style.display = "";
		}else{
			document.getElementById("destCategoryTbody").style.display = "none";
			document.getElementById("destPropertyTbody").style.display = "none";
			document.getElementById("replacePropertyTbody").style.display = "none";
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
			  <td>内容分类</td>
			  <td>
                 <input type="hidden" name="CategoryID" />
                 <input type="text" name="CategoryName" readonly="readonly" />
              </td>
			  <td>
			     <button onclick="selectCategory(document.getElementById('CategoryID'),document.getElementById('CategoryName'))">选择...</button>
			  </td>
		   </tr>
		   <tr>
			  <td>待查属性</td>
			  <td>
			     <input type="hidden" name="checkingPropID" /><input type="text" name="checkingPropName" readonly="readonly" />
			  </td>
			  <td>
				 <button id="sourceButton" onclick="selectStrOrTextProperty(document.getElementById('CategoryID').value,document.getElementById('checkingPropID'),document.getElementById('checkingPropName'))">选择...</button>
		      </td>
		   </tr>
		   <tr>
			  <td>敏感词分类</td>
			  <td>
                 <input type="hidden" name="sensitiveCategoryID" />
                 <input type="text" name="sensitiveCategoryName" readonly="readonly" />
              </td>
			  <td>
			     <button onclick="selectCategory(document.getElementById('sensitiveCategoryID'),document.getElementById('sensitiveCategoryName'))">选择...</button>
			  </td>
		   </tr>
		   <tr>
			  <td>敏感词属性</td>
			  <td>
			     <input type="hidden" name="sensitivePropID" /><input type="text" name="sensitivePropName" readonly="readonly" />
			  </td>
		      <td>
				 <button id="sourceButton" onclick="selectProperty(document.getElementById('sensitiveCategoryID').value,document.getElementById('sensitivePropID'),document.getElementById('sensitivePropName'))">选择...</button>
			  </td>
		   </tr>
		   <tr>
		      <td>执行操作</td>
		      <td>
		         <select name="contentOption" onchange="showOption(this)">
		           <option value="">请选择...</option>
		           <option value="move">转移到</option>
		           <option value="copy">复制到</option>
		           <option value="recommend">推荐到</option>
		           <option value="replace">替换属性值中的敏感词为</option>
		           <option value="modifyProperty">标记另一属性值为</option>
		           <option value="remove">将该节点删除</option>
		         </select>
		      </td>
		   </tr>
		   <tbody id="destCategoryTbody" style="display:none;">
		      <tr>
			     <td>目标分类</td>
			     <td>
                    <input type="hidden" name="destCategoryID" />
                    <input type="text" name="destCategoryName" readonly="readonly" />
                 </td>
			     <td>
			        <button onclick="selectCategory(document.getElementById('destCategoryID'),document.getElementById('destCategoryName'))">选择...</button>
			     </td>
		      </tr>
		   </tbody>
		   <tbody id="destPropertyTbody" style="display:none;">
		      <tr>
			     <td>待修改属性</td>
			     <td>
			        <input type="hidden" name="modifyPropID" /><input type="text" name="modifyPropName"  readonly="readonly"/>
			     </td>
			     <td>
				    <button id="btnDest" onclick="selectProperty(document.getElementById('CategoryID').value,document.getElementById('modifyPropID'),document.getElementById('modifyPropName'))">选择...</button>
			     </td>
			  </tr>
			  <tr>
			     <td>修改为</td>
			     <td colspan="2">
			        <input type="text" name="destValue" value=""/>
			     </td>
			  </tr>
		   </tbody>
		   <tbody id="replacePropertyTbody" style="display:none;">
			  <tr>
			     <td>修改为</td>
			     <td colspan="2">
			        <input type="text" name="replaceValue" value=""/>
			     </td>
			  </tr>
		   </tbody>
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
