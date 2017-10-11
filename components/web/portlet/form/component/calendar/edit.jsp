<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">缺 省 值</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">关联属性</td>
              <td class="formComponent">
              <input name="preference(category-id)" type="hidden" value="<bean:write name="definition" ignore="true"/>" />
                <html:hidden property="preference(propertyId)" styleId="propId"/>
                <logic:present name="property">
                	<input type="text" id="fieldName" readonly="readonly" value="<bean:write name="property" property="name" ignore="true"/>" />
                </logic:present>
                <logic:notPresent name="property">
                	<input type="text" id="fieldName" readonly="readonly" value="<bean:write name="propertyDeleted" ignore="true"/>" />
                </logic:notPresent>
                <button type="button" class="commonbut" id="search" onclick="searchPropertyDefinition('<bean:write name="definition" ignore="true"/>')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent"><html:checkbox style="width:25px" property="preference(hasTime)">显示时间</html:checkbox></td>
            </tr>
            <tr>
              <td class="formTitle">Tab键顺序</td>
              <td class="formComponent">
                <html:text property="preference(tabindex)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">文本框长度</td>
              <td class="formComponent">
                <html:text property="preference(textSize)" onblur="validatorInteger(this)"/></td>
            </tr>
            <tr>
              <td class="formTitle">文本框样式</td>
              <td class="formComponent">
                <html:text property="preference(textStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(textStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">按钮文字</td>
              <td class="formComponent">
                <html:text property="preference(buttonWord)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">按钮样式</td>
              <td class="formComponent">
                <html:text property="preference(buttonStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(buttonStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent"><html:checkbox style="width:25px" styleId="b1" property="preference(submit)"><label for="b1">自动提交</label></html:checkbox></td>
            </tr>
          </table>
        </fieldset>
        <fieldset  style="display:none">
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">缺 省 值</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=true;" styleId="defaultMode1" value="none"/><label for="defaultMode1">无</label>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=true;" styleId="defaultMode2" value="now"/><label for="defaultMode2">即时时间</label>
                +<html:text property="preference(offset)" size="3" style="width:70px"></html:text>
                <html:select property="preference(unit)" style="width:45px">
                  <html:option value="6">日</html:option>
                  <html:option value="2">月</html:option>
                  <html:option value="1">年</html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=false;" styleId="defaultMode3" value="fix"/><label for="defaultMode3">固定时间</label>
                <html:text styleId="preference(defaultValue)" property="preference(defaultValue)" style="width:125px"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=true;" styleId="defaultMode4" value="searchEcho"/><label for="defaultMode4">从页面请求参数中获取</label>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">
                <html:radio style="width:25px" property="preference(defaultMode)" onclick="this.form['preference(defaultValue)'].disabled=true;" styleId="defaultMode5" value="nodeEcho"/><label for="defaultMode5">从当前内容中获取</label>
              </td>
            </tr>
            <tr>
              <td class="formTitle">&nbsp;</td>
              <td class="formComponent">多值属性的第<html:text size="8" property="preference(propertyWhich)" onblur="validatorInteger(this)"/>个值</td>
            </tr>
            <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(allowWrite)" styleId="b2"><label for="b2">允许手动填写</label></html:checkbox>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="button" onclick="validatorRequired(new Array(form.elements['preference(propertyId)']),new Array('关联属性'),this.form)">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
  /**
  *选择待显示的属性
  */
  function searchPropertyDefinition($categoryId){
    var arr = CMSDialog.PropertyDefinitionSelector($categoryId);
    if(arr!=null){
      document.getElementById("fieldName").value = arr.name;
      document.getElementById("propId").value = arr.ID;
    }
  }

  window.onload = function()
  {
    if (!document.getElementById('defaultMode3').checked)
      document.getElementsByName('preference(defaultValue)')[0].disabled = true;
  }
</script>
