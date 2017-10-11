<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<bean:define id="path" name="childrenPath" type="java.lang.String"/>
<logic:equal value="on" name="preferences" property="value(showrootmsg)">
<logic:present name="rootDef">
<bean:define id="root" name="parentPath" type="java.lang.String"/>
<bean:define id="rootDefinition" name="rootDef" type="com.fulong.longcon.repository.NodeDefinition"/>
<ul id="<bean:write name='javax.portlet.id'/>" class='<bean:write name="preferences" property="value(style)" ignore="true"/>'>
<li id='<%=rootDefinition.getID() %>'>
<%request.setAttribute("com.fulong.longcon.nodeDefinition",rootDefinition); %>
<%String p; if(!root.equals("") && root != null) p=root; else p=path;%>
<site:insert page='<%=p %>' flush="false"></site:insert>
<%request.setAttribute("com.fulong.longcon.nodeDefinition",null);%>
</logic:present>
<ul>
</logic:equal>
<logic:notEqual value="on" name="preferences" property="value(showrootmsg)">
<ul id="<bean:write name='javax.portlet.id'/>" class='<bean:write name="preferences" property="value(style)" ignore="true"/>'>
</logic:notEqual>
<logic:present name="defs">
<logic:notEmpty name="preferences" property="value(num)">
<bean:define id="num" name="preferences" property="value(num)" type="java.lang.String"/>
<logic:iterate indexId="index" id="nodeDefinition" name="defs" length='<%=num%>'>
    <%request.setAttribute("com.fulong.longcon.nodeDefinition",nodeDefinition);request.setAttribute("indexId",index); %>
    <li id='<bean:write name="nodeDefinition" property="ID" ignore="true"/>'>
      <site:insert page='<%=path%>' flush="false"></site:insert>
    </li>
      <%request.setAttribute("com.fulong.longcon.nodeDefinition",null); request.setAttribute("indexId",null); %>
</logic:iterate>
</logic:notEmpty>
<logic:empty name="preferences" property="value(num)">
<logic:iterate id="nodeDefinition" name="defs">
    <%request.setAttribute("com.fulong.longcon.nodeDefinition",nodeDefinition);%>
    <li id='<bean:write name="nodeDefinition" property="ID" ignore="true"/>'>
      <site:insert page='<%=path%>' flush="false"></site:insert>
    </li>
      <%request.setAttribute("com.fulong.longcon.nodeDefinition",null); %>
</logic:iterate>
</logic:empty>
</logic:present>
</ul>
<logic:equal value="on" name="preferences" property="value(showrootmsg)">
<logic:present name="rootDef">
</li>
</ul>
</logic:present>
</logic:equal>
