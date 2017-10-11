<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <html:hidden property="preference(identifier)"/>
    <input type="hidden" name="defaultCategoryId" value="<bean:write name='defaultCategory' property="ID" ignore="true"/>"/>
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
              <td class="formTitle" valign="top">内容来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio styleId="b1" property="preference(contentType)" value="default" style="width:26px">
                        <label for="b1">使用URL参数指定内容</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio  style="width:26px" styleId="b2" property="preference(contentType)" value="user"><label for="b2">使用当前登录用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio  style="width:26px" styleId="b3" property="preference(contentType)" value="site"><label for="b3">使用当前网站所属用户</label></html:radio>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td class="formTitle">内容类别</td>
                <td class="formComponent"><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">显示属性</td>
                <td class="formComponent">
                  <html:hidden property="preference(field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','1','2','3','4','5','6','7','8','9'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">内容样式</td>
                <td class="formComponent">
                  <html:text property="preference(contentStyle)"/>
                  <button type="button" class="commonbut" id="search" onclick="doSelectStyle(this.form.elements['preference(contentStyle)'],'')">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">页码样式</td>
                <td class="formComponent">
                  <html:text property="preference(pageStyle)"/>
                  <button type="button" class="commonbut" id="search" onclick="doSelectStyle(this.form.elements['preference(pageStyle)'],'')">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">显示模式</td>
                <td class="formComponent">
                  <html:select property="preference(around)">
                  	<html:option value="">1 2 3 4 5</html:option>
                  	<html:option value="apa">&lt;1&gt;&lt;2&gt;&lt;3&gt;&lt;4&gt;&lt;5&gt;</html:option>
                  	<html:option value="{p}">{1}{2}{3}{4}{5}</html:option>
                  	<html:option value="[p]">[1][2][3][4][5]</html:option>
                  	<html:option value="(p)">(1)(2)(3)(4)(5)</html:option>
                  	<html:option value="【p】">【1】【2】【3】【4】【5】</html:option>
                  </html:select>
                </td>
              </tr>
              <tr>
                <td class="formTitle">页码分隔符</td>
                <td class="formComponent">
                  <html:text property="preference(separator)"/>&nbsp;&nbsp;<br/>
                  (默认为空格)
                </td>
              </tr>
              <tr>
                <td class="formTitle">当前页码样式</td>
                <td class="formComponent">
                  <html:text property="preference(currentPageStyle)"/>
                  <button type="button" class="commonbut" id="search" onclick="doSelectStyle(this.form.elements['preference(currentPageStyle)'],'')">选择...</button>
                </td>
              </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(field)']),new Array('显示属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>