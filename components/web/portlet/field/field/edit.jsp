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
          <option value="1">高级设置</option>
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
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(category)'],form.elements['categoryName'],form.elements['fieldName'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle" valign="top">显示属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(field)"/>
                  <input type="text" name="fieldName" readonly="readonly" value="<bean:write name="propertyName" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(field)'],form.elements['fieldName'],['0','2','5','6','7','8','9'])">选择...</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle">自定义属性</td>
                <td class="formComponent"><html:text property="preference(customize)"></html:text></td>
              </tr>
              <tr>
                <td class="formTitle"><bean:message key="com.fulong.cms.portlet.field.edit.formTitle3.type"/></td>
                <td class="formComponent">
                <html:select property='preference(type)' style="width:210px" onchange="showChannel(this)">
                  <html:option value="item">简单属性块</html:option>
                  <html:option value="index">栏目页</html:option>
                </html:select></td>
              </tr>
              <tbody id="selectChannel" style="display:none">
              	<tr>
                  <td class="formTitle">跳转到栏目</td>
                  <td class="formComponent">
                      <html:hidden property="preference(channel)"/>
                      <input type="text" name="channelName" <logic:notEmpty name="channel">value="<bean:write name="siteTemplate" property="displayName" ignore="true"/>.<bean:write name="channel" property="displayName" ignore="true"/>"</logic:notEmpty>/><button type="button" class="commonbut" onclick="chooseColumn('<bean:write name="siteTemplate" property="name"/>',this.form.elements['preference(channel)'],this.form.elements['channelName'])">选择...</button>
                  </td>
                </tr>
                <tr>
                  <td class="formTitle" valign="top">跳转到属性</td>
                  <td class="formComponent" valign="top">
                      <html:hidden property="preference(forwardField)"/>
                      <input type="text" name="forwardFieldName" readonly="readonly" value="<bean:write name="forwardPropertyName" ignore="true"/>" />
                      <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(category)'],form.elements['preference(forwardField)'],form.elements['forwardFieldName'],['0','2','5','6','7','8','9'])">选择...</button>
                      <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(forwardField)'],form.elements['forwardFieldName'])">清空</button>
                  </td>
                </tr>
              </tbody>
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
                <td class="formTitle">自定义目标</td>
                <td class="formComponent"><html:text property="preference(target)"></html:text></td>
              </tr>
            </table>
         </fieldset>
        <fieldset style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
              <tr>
                <td>敏感词库</td>
                <td><html:hidden property="preference(sensitiveCategory)"/><input type="text" name="sensitiveCategoryName" readonly="readonly" value="<bean:write name="sensitiveCategory" property="name" ignore="true"/>" />
                  <button type="button" class="commonbut" id="searchN" onclick="searchNodeDefinition(form.elements['preference(sensitiveCategory)'],form.elements['sensitiveCategoryName'],form.elements['sensitiveFieldName'])">选择...</button>
                  <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['preference(sensitiveCategory)'],form.elements['sensitiveCategoryName'])">清空</button>
                </td>
              </tr>
              <tr>
                <td class="formTitle" valign="top">属性</td>
                <td class="formComponent" valign="top">
                  <html:hidden property="preference(sensitiveField)"/>
                  <input type="text" name="sensitiveFieldName" readonly="readonly" value="<bean:write name="sensitivePropertyName" ignore="true"/>" />
                  <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition(form.elements['preference(sensitiveCategory)'],form.elements['preference(sensitiveField)'],form.elements['sensitiveFieldName'],['0','2','5','6','7','8','9'])">选择...</button>
                </td>
              </tr>
              <tr>
                  <td>敏感词样式</td>
                  <td colspan="3"><html:text  property="preference(sensitiveStyle)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(sensitiveStyle)'])">选择...</button>
                  </td>
                </tr>
              <tr>
                <td class="formTitle">最多显示</td>
                <td class="formComponent"><html:text property="preference(length)" onblur="validatorInteger(this)"></html:text>个字</td>
              </tr>
              <tr>
                <td class="formTitle">替代文字</td>
                <td class="formComponent"><html:text title="被截去的文字部分的替代文字" property="preference(suffix)"></html:text></td>
              </tr>
              <tr>
                <td class="formTitle">清除HTML元素</td>
                <td class="formComponent"><html:radio style="width:25px" property="preference(cleanHtml)" value="yes" styleId="r11"/><label for="r11">是</label><html:radio style="width:25px" property="preference(cleanHtml)" value="no" styleId="r12"/><label for="r12">否</label></td>
              </tr>
              <tr>
                <td class="formTitle">显示多项</td>
                <td class="formComponent"><html:radio style="width:25px" property="preference(multiple)" value="yes" styleId="r21"/><label for="r21">是</label><html:radio style="width:25px"	 property="preference(multiple)" value="no" styleId="r22"/><label for="r22">否</label></td>
              </tr>
              <tr>
                <td class="formTitle">分隔符</td>
                <td class="formComponent"><html:text property="preference(separator)"/></td>
              </tr>
              <tr>
                <td></td>
                <td><html:checkbox style="width:25px" property="preference(hasNoSpan)" styleId="a1" value="true"/><label for="a1">去掉自带span标签</label></td>
              </tr>
            </table>
         </fieldset>
          <div class="toolbar">
          		<button type="button" onclick="validate(this.form);">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button>
          </div>
        </td>
      </tr>
    </portlet:form>
  </table>
  <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
  <script type="text/javascript" language="javascript">
 // alert("field内容域的值："+document.getElementsByName("preference(field)")[0].value)
  <logic:equal value="index" name="configForm" property="preference(type)">
  document.getElementById( "selectChannel" ).style.display='';
  </logic:equal>
  function showChannel(oSelect){
	  if(oSelect.value=="index"){
		  document.getElementById( "selectChannel" ).style.display='';
	  }else{
		  document.getElementById( "selectChannel" ).style.display='none';
	  }
  }
  function validate(oForm){
	if(validateForward(oForm)==false){
		return false;
	}
	validatorRequired(new Array(oForm.elements['preference(field)'],oForm.elements['fieldName']),new Array('显示属性','显示属性'),oForm);
  }
    </script>
