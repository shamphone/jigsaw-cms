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
              <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle2.content"/>来源</td>
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
                  </table>
                </td>
              </tr>
              <tr>
                <td>内容类别</td>
                <td><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle4.attrubiteShow"/></td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','2','5','6','7','8','9'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle3.type"/></td>
                <td class="formComponent"><html:select property='<%= "preference(type)" %>' style="width:210px">
                  <html:option value="item"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle3.option.item"/></html:option>
                  <html:option value="index">栏目页</html:option>
                </html:select></td>
              </tr>
              <tr>
                <td class="formTitle">打开方式</td>
                <td><html:select property="preference(newWindows)" styleId="itemColumn" style="width:210px">
                  <html:option value="_self">原窗口</html:option>
                  <html:option value="_blank">新窗口</html:option>
                  <html:option value="_parent">父窗口</html:option>
                  <html:option value="_top">顶级窗口</html:option>
                </html:select>
              </td>
            </tr>
              <tr>
                <td class="formTitle">条目字数</td>
                <td class="formComponent"><html:text property="preference(length)" onblur="validatorInteger(this)"></html:text></td>
              </tr>
              <tr>
                <td class="formTitle">条目个数</td>
                <td class="formComponent"><html:text  property="preference(count)" onblur="validatorInteger(this)"></html:text></td>
              </tr>
              <tr>
                <td class="formTitle">分隔符</td>
                <td class="formComponent"><html:text property="preference(separator)"/></td>
              </tr>
            <tr>
              <td class="formTitle">高亮 样 式</td>
              <td class="formComponent"><html:text  property='preference(style)'/>
              <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(style)'])">选择...</button></td>
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
  <script type="text/javascript" language="javascript">
    var oForm= document.forms[0];
    </script>
