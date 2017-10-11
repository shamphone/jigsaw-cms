<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="styles" name="preferences" property="values(block-styles)" type="java.lang.String[]"/>
<bean:define id="length" name="preferences" property="values(length)" type="java.lang.String[]"/>
<bean:define id="fields" name="preferences" property="values(block-fields)" type="java.lang.String[]"/>
<bean:define id="formats" name="preferences" property="values(block-formats)" type="java.lang.String[]"/>
<bean:define id="columns" name="preferences" property="value(column)" type="java.lang.String"/>
<bean:define id="blockTypes" name="preferences" property="values(block-types)" type="java.lang.String[]"/>
<%int linkNum=Integer.parseInt(request.getAttribute("linkNum").toString());%>
<table id="<bean:write name='javax.portlet.id'/>" width="100%" border="0" align="center" cellpadding="0" cellspacing="0"><tr>
        <fulong:for id="column" name="preferences" property="value(column)"><td>
<ul class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>">
<fulong:for id="row" name="preferences" property="value(row)">
  <logic:iterate id="content" name="contents" offset='<%= ""+(row.intValue()*Integer.parseInt(columns)+column.intValue())%>' length="1" indexId="cur">
    <li>
      <fulong:for id="block" name="preferences" property="value(block)">
        <%if(cur.intValue()<linkNum){%>
        <a target="<bean:write name="preferences" property="value(newWindows)" ignore="true"/>" title="<cms:node name="content" propertyName='<%= ""+fields[block.intValue()] %>' format="<%= formats[block.intValue()] %>" ignore="true"/>" href="<site:nodeURL name="content" type="<%=blockTypes[block.intValue()]%>"/>"><span class="<%= styles[block.intValue()] %>"><cms:node length="<%=Integer.parseInt(0+length[block.intValue()])%>" name="content" propertyName='<%= ""+fields[block.intValue()] %>'  format="<%= formats[block.intValue()] %>"  ignore="true"/></span></a>
         <%}else{%> <span class="<%= styles[block.intValue()] %>"><cms:node length="<%=Integer.parseInt(length[0+block.intValue()])%>" name="content" propertyName='<%= ""+fields[block.intValue()] %>' format="<%= formats[block.intValue()] %>" ignore="true"/></span>
            <%}%><bean:write name="preferences" property="value(seperator)" filter="false" ignore="true"/>
          </fulong:for>
        </li>
      </logic:iterate>
    </fulong:for>
</ul></td>
            </fulong:for></tr></table>
<logic:equal value="true" name="preferences" property="value(show-pager)">
  <div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="default" /></div>
</logic:equal>
