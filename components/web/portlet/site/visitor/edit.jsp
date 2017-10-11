<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script language="javascript" src="/ide/site/portlet/script.js" type="text/javascript" ></script>
<portlet:form action="save" method="POST">
  <div class="pannelDiv">
    <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
      <option value="0" selected="selected"><bean:message key="com.fulong.site.portlet.visitor.edit.option0.baseSet" bundle="siteVisitor"/></option>
    </select></div>
    <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top"><bean:message key="com.fulong.site.portlet.visitor.edit.formTitle1.rowNum" bundle="siteVisitor"/></td>
              <td class="formComponent" valign="top">
                <html:text property="preference(row)"/>
              </td>
            </tr>
            <tr>
              <td class="formTitle" nowrap="nowrap" valign="top"><bean:message key="com.fulong.site.portlet.visitor.edit.formTitle2.Style" bundle="siteVisitor"/></td>
              <td class="formComponent" valign="top">
                <html:text property="preference(list-style)"/><input type="button" value='<bean:message key="com.fulong.site.portlet.visitor.edit.formTitle2.Style.choose" bundle="siteVisitor"/>' onclick="selectStyle(this.form.elements['preference(list-style)'])"/></td>
            </tr>
          </table>
  </fieldset>
  <div class="toolbar">
    <input type="submit" value='<bean:message key="com.fulong.site.portlet.visitor.edit.toolbar.submit.save" bundle="siteVisitor"/>'/>
    <input type="button" value='<bean:message key="com.fulong.site.portlet.visitor.edit.toolbar.button.cancel" bundle="siteVisitor"/>'onclick="window.parent.close()"/>
  </div>
</portlet:form>
