<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
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
                <input type="hidden" name="categoryId" value="<bean:write name="definition" ignore="true"/>"/>
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<logic:present name="property"><bean:write name="property" property="name" ignore="true"/></logic:present><logic:notPresent name="property"><bean:write name="propertyDeleted" ignore="true"/></logic:notPresent>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(this.form['categoryId'],this.form['preference(propertyId)'],this.form['fieldName'])">选择...</button>
              </td>
            </tr>
              <tr>
                <td class="formTitle">选择范围</td>
                <td class="formComponent"><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
            <tr>
              <td class="formTitle">显示属性</td>
              <td class="formComponent">
                <html:hidden property="preference(viewPropertyId)"/>
                <input type="text" name="viewFieldName" readonly="readonly" value="<bean:write name="viewProperty" property="name" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],this.form.elements['preference(viewPropertyId)'],this.form.elements['viewFieldName'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">分类样式</td>
              <td class="formComponent">
                <html:text property="preference(categoryStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(categoryStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">内容样式</td>
              <td class="formComponent">
                <html:text property="preference(nodeStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(nodeStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">默认分类</td>
                <td class="formComponent">
                <html:hidden property="preference(defaultCategory)"/>
                <html:radio style="width:25px" styleId="b1" property="preference(defaultType)" value="default">
                        <label for="b1"><input style="width:125px" type="text" name="defaultCategoryName" readonly="readonly" value="<bean:write name="defaultCategory" property="name" ignore="true"/>" /></label></html:radio>
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(defaultCategory)'],form.elements['defaultCategoryName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(defaultCategory)'],form.elements['defaultCategoryName'])">清空</button>
                </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
                <td class="formComponent">
                <html:radio style="width:25px" styleId="b2" property="preference(defaultType)" value="user">
                        <label for="b2">当前用户所在分类</label></html:radio>
                </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent"><html:checkbox style="width:25px" styleId="b1" property="preference(submit)"><label for="b1">自动提交</label></html:checkbox></td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(propertyId)'],form.elements['preference(category)'],form.elements['preference(viewPropertyId)']),new Array('关联属性','选择范围','显示属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
