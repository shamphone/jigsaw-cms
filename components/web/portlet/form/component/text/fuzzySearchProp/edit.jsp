<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
  	<html:hidden property="preference(contentType)"/>
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
              <td class="formTitle">属性</td>
              <td class="formComponent">
                <input name="preference(category-id)" type="hidden" value="<bean:write name="definition" ignore="true"/>" />
                <html:hidden property="preference(propertyId)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category-id)'],form.elements['preference(propertyId)'],form.elements['fieldName'],['0','2','5','6','7','8','9']);addPrefix(form.elements['preference(propertyId)']);">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">回显</td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="defaultEcho">初始值</html:radio>
              	<html:text property="preference(value)"/> 
			  </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="searchEcho">从页面请求参数中获取</html:radio>
              </td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
              	<html:radio property="preference(echo)" value="nodeEcho">从当前内容中获取</html:radio>
              </td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
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
          <button type="submit">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
          </div>
        </td>
      </tr>
    </portlet:form>
  </table>
  <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
  <script type="text/javascript" language="javascript">
    var oForm= document.forms[0];
    function addPrefix(oObject){
	   oObject.value = "coolinkKeyword_" + oObject.value;
    }
</script>
