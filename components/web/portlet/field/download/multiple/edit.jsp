<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <input type="hidden" name="defaultCategoryId" value="<bean:write name='defaultCategory' property="ID" ignore="true"/>"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">域名设置</option>
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
              <td class="formTitle">关联属性</td>
              <td class="formComponent">
                <html:hidden property="preference(field)"/>
                <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','1','2','3','4','5','6','7','9','10'])">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">空链接占位</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(perch)" value="1" styleId="radio11" /><label for="radio11">是</label>
                <html:radio style="width:25px" property="preference(perch)" value="0" styleId="radio12" /><label for="radio12">否</label>
              </td>
            </tr>
            <tr>
              <td class="formTitle">展示文字</td>
              <td class="formComponent">
              <html:radio style="width:25px" styleId="c1" property="preference(showWhat)" value="name" onclick="showWhatChange(true)"/><label for="c1">显示文件名</label>
              <html:radio style="width:25px" styleId="c2" property="preference(showWhat)" value="text" onclick="showWhatChange(false)"/><label for="c2">显示文字</label>
                <logic:equal value="text" name="showWhat">
                  <html:text style="width:35px" property="preference(text)"/><button type="button" class="commonbut" id="advanced" onclick="advancedText(this.form['preference(text)'])">高级...</button>
                </logic:equal>
                <logic:notEqual value="text" name="showWhat">
                  <html:text style="width:35px" property="preference(text)" disabled="true"/><button type="button" disabled="true" class="commonbut" id="advanced" onclick="advancedText(this.form['preference(text)'])">高级...</button>
                </logic:notEqual>
              </td>
            </tr>
            <tr>
              <td class="formTitle">列表样式</td>
              <td class="formComponent">
                <html:text property="preference(style)"/>
                <button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:checkbox styleId="cacheImage" value="true" property="preference(cacheImage)"></html:checkbox>
                <label for="cacheImage">缓存</label>
              </td>
            </tr>
         </table>
        </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top">域名来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(siteType)" value="default">
                        <label for="b1">使用当前节点所有者的网站</label></html:radio>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b2" property="preference(siteType)" value="site"><label for="b2">使用指定的网站</label></html:radio>
                      <html:select property="preference(specifySite)">
                      		<logic:iterate id="site" name="sites">
	                      		<bean:define id="domain"><cms:node name="site" propertyName="domain"/></bean:define>
	                      		<bean:define id="displayName"><cms:node name="site" propertyName="displayName"/></bean:define>
                      			<html:option value="${domain}">${displayName}</html:option>
                      		</logic:iterate>
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b3" property="preference(siteType)" value="user"><label for="b3">使用当前登录用户</label></html:radio>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b4" property="preference(siteType)" value="custom"><label for="b4">自定义&nbsp;</label></html:radio><html:text style="width:196px;" property="preference(customValue)"></html:text>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
         </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="checkThisForm(this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  var fm= document.forms[0];
  //
  //
  function showWhatChange(bool)
  {
    fm['preference(text)'].disabled = bool;
    document.getElementById('advanced').disabled = bool;
  }
  function checkThisForm(oForm){
	  var showWhat = GetRadioValue(oForm.elements['preference(showWhat)']);
	  if(showWhat=="name"){
		  validatorRequired(new Array(oForm.elements['preference(field)']),new Array('关联属性'),oForm);
	  }else{
		  validatorRequired(new Array(oForm.elements['preference(field)'],oForm.elements['preference(text)']),new Array('关联属性','显示文字'),oForm);
	  }
  }
</script>
