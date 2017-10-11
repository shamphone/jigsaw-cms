<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<html>
<head></head>
    <script language="JavaScript">
    function ok(oForm){
    	oForm.submit();
    }
    </script>
<body>
<table cellpadding="0" cellspacing="0" border="0">
<portlet:form action="save" method="POST">
  <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected"><bean:message key="com.fulong.portal.portlet.counter.edit.option0.baseSet" bundle="portalCounter"/></option>
        </select></td>
        <td><fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.portal.portlet.counter.edit.formTitle1.type" bundle="portalCounter"/></td>
              <td class="formComponent">
                <html:select property="preference(type)">
                  <html:option value="system"><bean:message key="com.fulong.portal.portlet.counter.edit.formTitle1.option1.system" bundle="portalCounter"/></html:option>
                  <html:option value="site"><bean:message key="com.fulong.portal.portlet.counter.edit.formTitle1.option2.site" bundle="portalCounter"/></html:option>
                  <html:option value="channel"><bean:message key="com.fulong.portal.portlet.counter.edit.formTitle1.option3.channel" bundle="portalCounter"/></html:option>
                  <html:option value="content"><bean:message key="com.fulong.portal.portlet.counter.edit.formTitle1.option4.content" bundle="portalCounter"/></html:option>
                </html:select>
              </td>
            </tr>
            <!-- <tr>
              <td class="formTitle"></td>
              <td class="formComponent">
                <html:checkbox style="width:25px" property="preference(noIncrease)">只显示，不计数</html:checkbox>
              </td>
            </tr> -->
          </table>
        </fieldset>
            <div class="toolbar">
	       		<button type="button" onclick="ok(this.form)">保存</button>
	          	<button onclick="window.parent.close()">取消</button>
            </div>
            </td>
      </tr></portlet:form>
    </table>
</body>
</html>
