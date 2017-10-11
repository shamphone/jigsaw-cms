<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portal"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<table id="<bean:write name='javax.portlet.id'/>" cellspacing="0" cellpadding="1" border="0" width="100%" <logic:notEmpty name="preferences" property="value(style)"> style="<bean:write name="preferences" property="value(style)" ignore="true"/>"</logic:notEmpty><logic:notEmpty  name="preferences" property="value(table-style)"> class="<bean:write name="preferences" property="value(table-style)" ignore="true"/>"</logic:notEmpty>>
<bean:define id="definitionId" name="preferences" property="value(category)" type="java.lang.String"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<bean:define id="contextName" name="contextName" type="java.lang.String"/>
        <site:insert page='<%= path.substring(0,path.length()-5)+".head.jspf" +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
<tbody>
<logic:notEmpty name="preferences" property="value(row)">
<bean:define id="rowCount" name="preferences" property="value(row)" type="java.lang.String"/>
<fulong:for id="row" name="preferences" property="value(row)">
        <site:insert page='<%= path.substring(0,path.length()-5)+".body.jspf" +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
</fulong:for>
</logic:notEmpty>
<logic:empty name="preferences" property="value(row)">
        <site:insert page='<%= path.substring(0,path.length()-5)+".body.jspf" +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>
</logic:empty>
</tbody>
        <site:insert page='<%= path.substring(0,path.length()-5)+".foot.jspf" +"?javax.portlet.page.mode=view&definition="+ definitionId %>' contextName="<%= contextName %>" flush="false"></site:insert>

</table>
<logic:equal value="true" name="preferences" property="value(show-pager)">
    <div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>">共计100项内容，当前为3/5页，<a href="#">首页</a>，<a href="#">上一页</a>，<a href="#">下一页</a>，<a href="#">末页</a></div>
</logic:equal>
