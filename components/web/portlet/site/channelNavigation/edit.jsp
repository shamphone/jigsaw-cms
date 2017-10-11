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
          <option value="0" selected="selected"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.option0.baseSet" bundle="siteChannelNavigation"/></option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td><bean:message key="com.fulong.site.portlet.channelNavigation.edit.fieldset.navigtionDescribe" bundle="siteChannelNavigation"/> </td>
              <td class="formComponent" valign="top"><html:text property="preference(discription)" size="19"/> </td>
            </tr>
            <tr>
              <td><bean:message key="com.fulong.site.portlet.channelNavigation.edit.fieldset.openMethod" bundle="siteChannelNavigation"/> </td>
              <td class="formComponent" valign="top">
                <html:select property="preference(newWindows)" styleId="itemColumn">
                  <html:option value=""><bean:message key="com.fulong.site.portlet.channelNavigation.edit.option.oldWindow" bundle="siteChannelNavigation"/></html:option>
                  <html:option value="_blank"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.option_blank.newWindow" bundle="siteChannelNavigation"/></html:option>
                  <html:option value="_parent"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.option_parent.fatherWindow" bundle="siteChannelNavigation"/></html:option>
                  <html:option value="_top"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.option_top.topWindow" bundle="siteChannelNavigation"/></html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td class="formTitle" valign="top"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle1.navigtionDescribeStyle" bundle="siteChannelNavigation"/> </td>
              <td class="formComponent" valign="top"><html:text property="preference(head-style)" size="19"/><button type="button" onclick="selectStyle(this.form.elements['preference(head-style)'])"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle1.navigtionDescribeStyle.choose" bundle="siteChannelNavigation"/></button></td>
            </tr>
            <tr>
              <td class="formTitle" valign="top"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle2.navigtionStyle" bundle="siteChannelNavigation"/> </td>
              <td class="formComponent" valign="top"><html:text property="preference(middle-style)" size="19"/><button type="button" onclick="selectStyle(this.form.elements['preference(middle-style)'])"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle2.navigtionStyle.choose" bundle="siteChannelNavigation"/></button></td>
            </tr>
            <tr>
              <td class="formTitle" valign="top"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle3.navigtionStyleLastStyle" bundle="siteChannelNavigation"/> </td>
              <td class="formComponent" valign="top"><html:text property="preference(end-style)" size="19"/><button type="button" onclick="selectStyle(this.form.elements['preference(end-style)'])"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle3.navigtionStyleLastStyle.choose" bundle="siteChannelNavigation"/></button></td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle4.separator" bundle="siteChannelNavigation"/></td>
              <td class="formComponent"><html:text property="preference(seperator)"/></td>
            </tr>
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle5.separatorStyle" bundle="siteChannelNavigation"/></td>
              <td class="formComponent"><html:text property="preference(seperator-style)"/><button type="button" onclick="selectStyle(this.form.elements['preference(seperator-style)'])"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.formTitle5.separatorStyle.choose" bundle="siteChannelNavigation"/></button></td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
          <button type="submit"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.toolbar.submit.save" bundle="siteChannelNavigation"/></button>
          <button type="button" onclick="window.parent.close()"><bean:message key="com.fulong.site.portlet.channelNavigation.edit.toolbar.button.cancel" bundle="siteChannelNavigation"/></button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
