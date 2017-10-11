<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <input type="hidden" name="defaultCategoryId" value="<bean:write name='defaultCategory' property="ID" ignore="true"/>"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected"><bean:message key="com.fulong.cms.portlet.field.edit.option0.baseSet"/></option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">自定义属性</td>
              <td class="formComponent">
                <html:select property="preference(type)">
                  <html:option value="keyword">关键字</html:option>
                  <html:option value="oldpassword">旧密码</html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(showpassword)">是否显示为密码框</html:checkbox>
              </td>
            </tr>
            <tr>
              <td class="formTitle">初始值</td>
              <td class="formComponent"><html:text property="preference(value)"/> </td>
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
              <td class="formTitle">长度</td>
              <td class="formComponent"><html:text property="preference(size)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">最多字符数</td>
              <td class="formComponent"><html:text property="preference(maxLength)" onblur="validatorInteger(this)"/></td>
            </tr>
          </table>
          </fieldset>
          <div class="toolbar">
          <button type="submit"><bean:message key="com.fulong.cms.portlet.field.edit.toolbar.submit.save"/></button>
          <button type="button" onclick="window.parent.close()"><bean:message key="com.fulong.cms.portlet.field.edit.toolbar.button.cancel"/></button>
          </div>
        </td>
      </tr>
    </portlet:form>
  </table>
  <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
  <script type="text/javascript" language="javascript">
    var oForm= document.forms[0];
    </script>
