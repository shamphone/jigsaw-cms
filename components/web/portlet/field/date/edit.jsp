<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
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
                <td class="formComponent"><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">显示属性</td>
                <td class="formComponent">
                  <html:hidden property="preference(field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName"  ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','1','2','3','4','6','7','8','9','10'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">日期格式</td>
                <td class="formComponent">
                  <html:select property="preference(dateFormat)" style="width:210px">
                    <html:option value="">不显示</html:option>
                    <bean:define id="dateMap" name="dateFormats" type="java.util.Map"/>
                    <logic:iterate id="key" name="dateFormatKeys" type="String">
                      <html:option value="<%=key %>"><%=dateMap.get(key) %></html:option>
                    </logic:iterate>
                  </html:select>
                </td>
              </tr>
              <tr>
                <td class="formTitle">时间格式</td>
                <td class="formComponent">
                  <html:select property="preference(timeFormat)" style="width:210px">
                    <html:option value="">不显示</html:option>
                    <bean:define id="timeMap" name="timeFormats" type="java.util.Map"/>
                    <logic:iterate id="key" name="timeFormatKeys" type="String">
                      <html:option value="<%=key %>"><%=timeMap.get(key) %></html:option>
                    </logic:iterate>
                  </html:select>
                </td>
              </tr>
              <tr>
                  <td class="formTitle">样式</td>
                  <td class="formComponent"><html:text  property="preference(table-style)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(table-style)'])">选择...</button>
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
