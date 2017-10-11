<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
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
              <td class="formTitle">关联属性</td>
              <td class="formComponent">
                <html:hidden property="preference(referenceId)"/>
                <html:hidden property="preference(referenceType)"/>
                <input type="text" name="referenceName" readonly="readonly" value="<bean:write name="reference" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="searchN" onclick="searchReferenceDefinition('<bean:write name="definition" ignore="true"/>')">选择...</button>
              </td>
            </tr>
            <tr>
                <td class="formTitle">内容类别</td>
                <td class="formComponent">
                  <html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition('fieldName','preference(propertyId)',this.form['preference(category)'].value)">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/>
                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(hasNull)"/>添加空项
              </td>
            </tr>
            <tr>
              <td class="formTitle">空项值</td>
              <td class="formComponent"><html:text property="preference(nullValue)"/></td>
            </tr>
            <tr>
              <td class="formTitle">空项文本</td>
              <td class="formComponent"><html:text property="preference(nullText)"/></td>
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
                  <html:hidden property="preference(optionValue)"/>
                  <input type="text" name="fieldNameZ" readonly="readonly" value="<bean:write name="value" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchZ" onclick="searchPropertyDefinition('fieldNameZ','preference(optionValue)',this.form['preference(category)'].value)">选择...</button>
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
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(referenceId)'],form.elements['preference(propertyId)']),new Array('关联属性','显示属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script language="javascript" src="/components/portlet/form/component/node/script.js.jsp" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
function doSubmit(oForm){
    return true;
  }
var propertyType='<bean:write name="propertyType" ignore="true"/>';
var userID='<bean:write name="userID" ignore="true"/>';

if (propertyType == "0") {
	  document.getElementById("searchZ").disabled=true;
	  document.getElementsByName("preference(userID)")[0].disabled=true;
	  document.getElementsByName("fieldNameZ")[0].value="";
	  document.getElementsByName("preference(optionValue)")[0].value="";
 	  } else {
	  document.getElementsByName("preference(userID)")[0].disabled=false;
	  if(userID) {
		    document.getElementById("searchZ").disabled=true;
		    document.getElementsByName("fieldNameZ")[0].value="";
		    document.getElementsByName("preference(optionValue)")[0].value="";
	  } else {
		    document.getElementById("searchZ").disabled=false;
	  }
  }
  /**
  *选择引用的属性
  */
  function searchReferenceDefinition($categoryId){
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId, new Array(0, 6, 7, 8, 10));
    if(arr!=null){
      document.getElementsByName("referenceName")[0].value = arr.name;
      document.getElementsByName("preference(referenceId)")[0].value = arr.ID;
      document.getElementsByName("preference(referenceType)")[0].value = arr.refID;
      if (arr.refID == null) {
    	  document.getElementById("searchZ").disabled=false;
      	  document.getElementsByName("preference(userID)")[0].disabled=false;
      } else {
    	  document.getElementById("searchZ").disabled=true;
      	  document.getElementsByName("preference(userID)")[0].disabled=true;
		  document.getElementsByName("fieldNameZ")[0].value="";
		  document.getElementsByName("preference(optionValue)")[0].value="";
      }
    }
  }
  /**
  *选择待显示的属性
  */
  function searchPropertyDefinition(inputName,hiddenName,$categoryId){
    if (!$categoryId) {
       alert("请选择一个正确的引用字段");
    }
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId);
    if(arr!=null){
      document.getElementsByName(inputName)[0].value = arr.name;
      document.getElementsByName(hiddenName)[0].value = arr.ID;
    }
  }
  /**
   *选择ID时其他属性不可选
   */
  function searchDisabled(userID){
	    if(userID.checked) {
		    document.getElementById("searchZ").disabled=true;
		    document.getElementsByName("fieldNameZ")[0].value="";
		    document.getElementsByName("preference(optionValue)")[0].value="";
	    } else {
		    document.getElementById("searchZ").disabled=false;
	    }
  	}
</script>
