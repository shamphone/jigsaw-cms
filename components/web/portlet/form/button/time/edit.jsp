<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST">
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                </select></td>
                <td><fieldset>
			        <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <td class="formTitle">内容类别</td>
                <td class="formComponent"><html:hidden property="preference(category)"/><input type="text" name="categoryName" readonly="readonly" value="<bean:write name="category" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'])">选择...</button>
                </td>
              </tr>
			            <tr>
			              <td class="formTitle">关联属性</td>
			              <td class="formComponent">
			                <html:hidden property="preference(propertyId)"/>
			                <logic:empty name="property">
			                	<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
			                </logic:empty>
			                <logic:notEmpty name="property">
			                	<input type="text" name="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
			                </logic:notEmpty>
			                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(propertyId)'],form.elements['fieldName'],['0','1','2','3','4','6','7','8','9','10'])">选择...</button>
			              </td>
			            </tr>
                        <tr>
                            <td class="formTitle">按钮名称</td>
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
                                <html:text property="preference(style)"/>
                                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                            </td>
                        </tr>
                        <!--<tr>
                            <td class="formTitle">宽度</td>
                            <td class="formComponent">
                                <html:text property="preference(size)" onblur="validatorInteger(this)"/>
                            </td>
                        </tr>-->
            <tr>
              <td class="formTitle">跳转日期</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=true;" styleId="defaultMode2" value="par"/><label for="defaultMode2">参数时间</label>
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=true;" styleId="defaultMode3" value="now"/><label for="defaultMode3">即时时间</label>
                </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
              +<html:text property="preference(offset)" size="3" style="width:70px"></html:text>
                <html:select property="preference(unit)" style="width:45px">
                  <html:option value="6">日</html:option>
                  <html:option value="4">周</html:option>
                  <html:option value="2">月</html:option>
                  <html:option value="1">年</html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=false;" styleId="defaultMode4" value="fix"/><label for="defaultMode4">固定时间</label>
                <html:text styleId="preference(defaultValue)" property="preference(defaultValue)" style="width:125px"/>
              </td>
            </tr>
                    </table>
                </fieldset>
                <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(propertyId)']),new Array('关联属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button></div></td>
                </tr></portlet:form>
            </table>
            <script type="text/Javascript" src='/ide/common/script/portlet.js'></script>