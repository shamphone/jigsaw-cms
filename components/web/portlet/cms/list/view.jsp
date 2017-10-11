<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="displayName" name="javax.portlet.config" property="portletName" type="String"/>
<cms:unique name="<%=displayName%>">
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
  <script language="javascript" src="<html:rewrite page='/cms/portlet/list/script.jsp' module=''/>" type="text/javascript" ></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
</cms:unique>
<bean:define id="styles" name="preferences" property="values(block-styles)" type="java.lang.String[]"/>
<bean:define id="length" name="preferences" property="values(length)" type="java.lang.String[]"/>
<bean:define id="fields" name="preferences" property="values(block-fields)" type="java.lang.String[]"/>
<bean:define id="customalValues" name="preferences" property="values(customalValues)" type="java.lang.String[]"/>
<bean:define id="formats" name="preferences" property="values(block-formats)" type="java.lang.String[]"/>
<bean:define id="columns" name="preferences" property="value(column)" type="java.lang.String"/>
<bean:define id="target" name="preferences" property="value(newWindows)" type="java.lang.String"/>
<bean:define id="blockTypes" name="preferences" property="values(block-types)" type="java.lang.String[]"/>
<bean:define id="editFields" name="preferences" property="values(editFields)" type="java.lang.String[]"/>
<table id="<bean:write name='javax.portlet.id'/>" width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr>
    <fulong:for id="column" name="preferences" property="value(column)"><td>
      <ul class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>">
      <fulong:for id="row" name="preferences" property="value(row)">
        <logic:iterate id="content" name="contents" offset='<%= ""+(row.intValue()*Integer.parseInt(columns)+column.intValue())%>' length="1">
          <li id="<bean:write name="content" property="ID" ignore="true"/>">
          <fulong:for id="block" name="preferences" property="value(block)">
<cms:field target="<%=target%>" length='<%=length[block.intValue()]%>' style="<%= styles[block.intValue()] %>" name="content" propertyName="<%=fields[block.intValue()]%>" type="<%=blockTypes[block.intValue()]%>" customalValues="<%=customalValues[block.intValue()]%>"/>
        <bean:write name="preferences" property="value(seperator)" filter="false" ignore="true"/>
          </fulong:for>
        <logic:equal value="true" name="preferences" property="value(optionColumn)">
              <span class="<bean:write name='preferences' property="value(optionStyle)" ignore="true"/>">
            </span>
          </logic:equal>
        </li>
      </logic:iterate>
    </fulong:for>
  </ul></td>
</fulong:for></tr></table>
<logic:equal value="true" name="preferences" property="value(show-pager)">
  <div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>">首页，上一页，下一页，末页</div>
</logic:equal>

