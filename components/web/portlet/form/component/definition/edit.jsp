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
                    <td nowrap="nowrap">内容分类</td>
                    <td>
                      <html:hidden property="preference(definitionId)"/>
                      <input type="text" name="categoryName" readonly="readonly" value="<bean:write name="definition" property="name" ignore="true"/>" />
                      <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(definitionId)'],form.elements['categoryName'])">选择...</button>
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
                <html:text property="preference(style)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">列表行数</td>
              <td class="formComponent"><html:text property="preference(size)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(multiple)"/>可以多选
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
              <td class="formTitle">默认分类</td>
                <td class="formComponent">
                <html:hidden property="preference(category)"/>
                <html:radio style="width:25px" styleId="b1" property="preference(defaultType)" value="default">
                        <label for="b1"><input style="width:125px" type="text" name="defaultCategoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" /></label></html:radio>
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['defaultCategoryName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(category)'],form.elements['defaultCategoryName'])">清空</button>
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
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(definitionId)']),new Array('内容分类'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
