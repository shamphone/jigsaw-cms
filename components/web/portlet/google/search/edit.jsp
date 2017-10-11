<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
        </select></td>
        <td><fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">网站地址</td>
              <td class="formComponent"><html:text property="preference(fieldName)"/></td>
            </tr>
            <tr>
              <td class="formTitle">文本框样式</td>
              <td class="formComponent">
                <html:text property="preference(textStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(textStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">文本框长度</td>
              <td class="formComponent"><html:text property="preference(textSize)"/></td>
            </tr>
            <tr>
            <tr>
              <td class="formTitle">最多字符数</td>
              <td class="formComponent"><html:text property="preference(textMaxLength)"/></td>
            </tr>
            <tr>
              <td class="formTitle">按钮样式</td>
              <td class="formComponent">
                <html:text property="preference(buttonStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(buttonStyle)'],'')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle">按钮文字</td>
              <td class="formComponent">
                <html:text property="preference(buttonName)"/>
              </td>
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
function doSubmit(oForm){
  return true;
}
</script>
