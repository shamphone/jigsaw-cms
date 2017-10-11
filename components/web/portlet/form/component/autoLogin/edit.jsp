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
          <option value="0" selected="selected">基本设置</option>
        </select>
      </td>
      <td>
        <fieldset>
        </fieldset>
          <div class="toolbar">
          <button type="button" type="submit">保存</button>
          <button type="button" onclick="window.parent.close()">取消</button></div>
      </td>
    </tr>
  </portlet:form>
</table>
            <script type="text/javascript" language="javascript">
            document.forms[0].submit();
            </script>