<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
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
              <td class="formTitle">关联属性</td>
              <td class="formComponent">
                <html:hidden property="preference(referenceId)"/>
                <html:hidden property="preference(referenceType)"/>
                <input type="text" name="referenceName" readonly="readonly" value="<bean:write name="reference" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="searchN" onclick="searchReferenceDefinition('<bean:write name="definition" ignore="true"/>')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(propertyId)"/>
                <input title="将在选项中展示的属性" type="text" name="fieldName" readonly="readonly" value="<logic:present name="property"><bean:write name="property" property="name" ignore="true"/></logic:present><logic:notPresent name="property"><bean:write name="propertyDeleted" ignore="true"/></logic:notPresent>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(this.form['preference(referenceType)'].value)">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">控件样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/><button type="button" class="commonbut"  onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">默认值</td>
              <td class="formComponent">
                 <input type="text"  name="valueName" value="<bean:write name="value" ignore="true"/>"/>
                <html:hidden property="preference(defaultValue)"/>
				<button type="button" onclick="nodeSelect.singleNodeSelect(this.form['preference(referenceType)'].value,this.form['preference(defaultValue)'],this.form['valueName'],this.form['preference(propertyId)'].value)">选择...</button>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="submit">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script language="javascript" src="/components/portlet/form/component/node/script.js.jsp" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  /**
  *选择引用的属性
  */
  function searchReferenceDefinition($categoryId){
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId, new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 10));
    if(arr!=null){
      document.getElementsByName("referenceName")[0].value = arr.name;
      document.getElementsByName("preference(referenceId)")[0].value = arr.ID;
      document.getElementsByName("preference(referenceType)")[0].value = arr.refID;
    }
  }
  /**
  *选择待显示的属性
  */
  function searchPropertyDefinition($categoryId){
    if (!$categoryId) {
       alert("请选择一个正确的引用字段");
       return;
    }
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId);
    if(arr!=null){
      document.getElementsByName("fieldName")[0].value = arr.name;
      document.getElementsByName("preference(propertyId)")[0].value = arr.ID;
    }
  }
</script>
