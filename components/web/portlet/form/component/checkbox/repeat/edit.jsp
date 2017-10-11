<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <html:hidden property="preference(definitionId)"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">表单属性</td>
              <td class="formComponent">
                <input name="preference(nameDefinition)" type="hidden" value="<bean:write name="nameDefinition" property="ID" ignore="true"/>" />
                <html:hidden property="preference(name)"/>
                <input type="text" name="nameFieldName" readonly="readonly" value="<bean:write name="name" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(nameDefinition)'],form.elements['preference(name)'],form.elements['nameFieldName'])">选择...</button>
               </td>
            </tr>
          <tr>
              <td class="formTitle">初始值</td>
              <td class="formComponent"><html:text property="preference(initValue)"/> </td>
            </tr>
               
           <tr>
              <td class="formTitle" valign="top">内容来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(contentType)" value="default">
                        <label for="b1">使用URL参数指定内容</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b2" property="preference(contentType)" value="user"><label for="b2">使用当前登录用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b3" property="preference(contentType)" value="site"><label for="b3">使用当前网站所属用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b4" property="preference(contentType)" value="parameter"><label for="b4">使用传入参数值</label></html:radio>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td class="formTitle">内容类别</td>
                <td class="formComponent"><html:hidden property="preference(valueDefinition)"/><input type="text" name="valueDefinitionName" readonly="readonly" value="<bean:write name="valueDefinition" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(valueDefinition)'],form.elements['valueDefinitionName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">值属性</td>
                <td class="formComponent">
                   <html:checkbox property="preference(userID)" value="true" style="width:23px;" onclick="searchDisabled(this)"/> 使用节点ID作为选项值
                </td>
              </tr>        
              <tr>
                <td class="formTitle"></td>
                <td class="formComponent">
                  <html:hidden property="preference(value)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="value" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchS" onclick="searchPropertyDefinition(form.elements['preference(valueDefinition)'],form.elements['preference(value)'],form.elements['fieldName'])">选择...</button>
                </td>
              </tr>
              <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="searchEcho">搜索页回显</html:radio>&nbsp;&nbsp;
              	<html:radio property="preference(echo)" value="nodeEcho">编辑页回显</html:radio>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(name)']),new Array('表单属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  /**
  *选择引用的属性
  
  function searchReferenceDefinition($categoryId){
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId, new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 10));
    if(arr!=null){
      document.getElementById("referenceName").value = arr.name;
      document.getElementsByName("preference(referenceId)")[0].value = arr.ID;
      document.getElementsByName("preference(referenceType)")[0].value = arr.refID;
    }
  }
  function searchPropertyDefinition($categoryId){
    if (!$categoryId) {
       alert("请选择一个正确的字段");
    }
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId);
    if(arr!=null){
      document.getElementsByName("fieldName")[0].value = arr.name;
      document.getElementsByName("preference(propertyId)")[0].value = arr.ID;
    }
  }**/

  var property='<bean:write name="property" ignore="true"/>';
  var userID='<bean:write name="userID" ignore="true"/>';
  if (property == "0") {
  	  document.getElementById("searchS").disabled=true;
  	  document.getElementsByName("preference(userID)")[0].disabled=true;
 	  document.getElementsByName("fieldName")[0].value="";
	  document.getElementsByName("preference(value)")[0].value="";
    } else {
	  document.getElementsByName("preference(userID)")[0].disabled=false;
	  if(userID == "true") {
		    document.getElementById("searchS").disabled=true;
		    document.getElementsByName("fieldName")[0].value="";
		    document.getElementsByName("preference(value)")[0].value="";
	  } else {
		    document.getElementById("searchS").disabled=false;
	  }
    }
 

  function searchPropertyDefinition(oCategory,oID, oName, excludes){
      var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,excludes);
      if(arr!=null){
        oName.value = arr.name;
        oID.value = arr.ID;
        if (arr.refID == null) {
      	  document.getElementById("searchS").disabled=false;
      	  document.getElementsByName("preference(userID)")[0].disabled=false;
        } else {
      	  document.getElementById("searchS").disabled=true;
      	  document.getElementsByName("preference(userID)")[0].disabled=true;
  		  document.getElementsByName("fieldName")[0].value="";
  		  document.getElementsByName("preference(value)")[0].value="";
        }
      }
    }

  function searchDisabled(userID){
    if(userID.checked) {
	    document.getElementById("searchS").disabled=true;
	    document.getElementsByName("fieldName")[0].value="";
	    document.getElementsByName("preference(value)")[0].value="";
    } else {
	    document.getElementById("searchS").disabled=false;
    }
  }
</script>
