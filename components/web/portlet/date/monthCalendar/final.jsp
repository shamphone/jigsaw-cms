<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<table id="<bean:write name='javax.portlet.id'/>"<logic:notEmpty name="preferences" property="value(cellspacing)"> cellspacing="<bean:write name="preferences" property="value(cellspacing)" ignore="true"/>"</logic:notEmpty><logic:notEmpty  name="preferences" property="value(cellpadding)"> cellpadding="<bean:write name="preferences" property="value(cellpadding)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(width)"> width="<bean:write name='preferences' property='value(width)'/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(height)"> height="<bean:write name="preferences" property="value(height)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(border)"> border="<bean:write name="preferences" property="value(border)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(bordercolor)"> border="<bean:write name="preferences" property="value(bordercolor)" ignore="true"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(style)"> style="<bean:write name="preferences" property="value(style)" ignore="true"/>"</logic:notEmpty><logic:notEmpty  name="preferences" property="value(table-style)"> class="<bean:write name="preferences" property="value(table-style)" ignore="true"/>"</logic:notEmpty>>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<bean:define id="path" name="path" type="java.lang.String"/>
<bean:define id="date" name="date"/>
<fulong:for id="row" name="preferences" property="value(row)"><tr>
  <fulong:for id="column" name="preferences" property="value(column)">
    <logic:iterate indexId="index" id="day" name="weekList" length="1">
    <%request.setAttribute("com.fulong.longcon.dateParameter",day);request.setAttribute("indexId",index); %>
    <%java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
    int dayTemp = ((java.util.Date)day).getDay();
    String dateTemp = dateFormatter.format(day).toString();
    java.util.Date currentDate = new java.util.Date();
    String currentDateTemp = dateFormatter.format(currentDate).toString();
    if(dayTemp == 0){%>
    	<td <logic:notEmpty name="preferences" property="value(sunday-style)">class="<bean:write name="preferences" property="value(sunday-style)"/>"</logic:notEmpty>>
    <%}else if(dayTemp == 6){%>
    	<td <logic:notEmpty name="preferences" property="value(saturday-style)">class="<bean:write name="preferences" property="value(saturday-style)"/>"</logic:notEmpty>>
    <%}else if(dateTemp.equals(currentDateTemp)){%>
    	<td <logic:notEmpty name="preferences" property="value(today-style)">class="<bean:write name="preferences" property="value(today-style)"/>"</logic:notEmpty>>
    <%}else{%>
    	<td>
    <%}%>
    <logic:equal value="false" name="preferences" property="value(blankDate)">
   <site:insert page='<%=path%>' flush="false"></site:insert>
   </logic:equal>
   <logic:notEqual value="false" name="preferences" property="value(blankDate)">
   <%
   int monthTemp = ((java.util.Date)day).getMonth();
   int month=((java.util.Date)date).getMonth();
   if (monthTemp == month) {
   %>
   <site:insert page='<%=path%>' flush="false"></site:insert>
   <%} else {%>
   &nbsp;
   <%}%>
   </logic:notEqual>
    </td>
    <%request.setAttribute("com.fulong.longcon.dateParameter",null);request.setAttribute("indexId",null); %>
    </logic:iterate>
  </fulong:for>
</tr>
</fulong:for>
</table>

