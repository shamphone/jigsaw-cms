<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST" onsubmit="doSubmit(this)">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">内容抽取</option>
        </select></td>
        <td>
          <fieldset>
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <td>内容类别</td>
                <td><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle" valign="top">复合属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(complexField)"/>
                  <html:hidden property="preference(complexDefinition)"/>
                  <input type="text" name="comDefinitionName" readonly="readonly" value="<logic:present name="property"><bean:write name="property" property="name" ignore="true"/></logic:present><logic:notPresent name="property"><bean:write name="propertyDeleted" ignore="true"/></logic:notPresent>" />
                  <button type="button" class="commonbut" id="search" onclick="searchComplexPropertyDefinition(form.elements['preference(category)'],form.elements['preference(complexField)'],form.elements['preference(complexDefinition)'],form.elements['comDefinitionName'])">选择...</button>
                </td>
              </tr>
              <tr>
              <td class="formTitle">显示样式</td>
              <td class="formComponent">
                <html:text property="preference(spanStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(spanStyle)'],'')">选择...</button>
              </td>
            </tr>
                <tr style="display:none;">
                  <td class="formTitle" valign="top">过滤属性</td>
                  <td nowrap="nowrap"  class="selectionEditor">
                  <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
                  <td>
                    <select multiple="multiple" name="preferences(filter-patterns)" size="5" style="width:210px;">
                      <logic:present name="preferences" property="values(filter-patterns)">
                        <logic:iterate id="pattern" name="preferences" property="values(filter-patterns)">
                          <option value='<bean:write name="pattern" filter="false"/>'><fulong:filter name="pattern" definition="definition"/></option>
                        </logic:iterate>
                      </logic:present>
                    </select>
                    </td><td valign="top">
                      <button type="button" class="commonbut" onclick="newFilter(form.elements['preference(refDefinition)'],form.elements['preferences(filter-patterns)'])">添 加</button><br/>
                      <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(filter-patterns)'])">删 除</button>
                    </td>
                    </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </fieldset>
            <div class="toolbar">
	       		<button type="button" onclick="doSubmit(this.form)">保存</button>
	          	<button type="button" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </portlet:form>
  </table>
  <script type="text/Javascript" language="Javascript">
  function doSubmit(oForm){
      //selectAll(document.all('preferences(filter-patterns)'));
      validatorRequired(new Array(oForm.elements['preference(complexField)']),new Array('复合属性'),oForm);      
    }
  /**
  *
  *选择复合属性
  **/
  
  function searchComplexPropertyDefinition(oCategory, oID, oComplexID, oComplexName){
    var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,['1','2','3','4','5','6','7','8','9','10']);
    if(arr!=null){
      oID.value = arr.ID;
      oComplexName.value = arr.name;
      oComplexID.value = arr.refID;
    }
  }
    </script>
