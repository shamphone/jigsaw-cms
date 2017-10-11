<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="javax.portlet.PortletPreferences" %>
<style>
	li{
		list-style:none;
	}
	ul{
		padding:0 0 0 0;
		margin:0 0 0 0;
	}
</style>
<ul <logic:notEmpty name="preferences" property="value(listStyle)">class="<bean:write name="preferences" property="value(listStyle)"/>"</logic:notEmpty>>
<%
  PortletPreferences pps = (PortletPreferences) request.getAttribute("preferences");
  String tmp = pps.getValue("fileCount", null);
  int fileCount = Integer.parseInt(tmp);
  String[] strs1 = (String[])request.getAttribute("paths");
  for (int i = 0; i < fileCount; i++) {%>
  <li>
  <!-- 以下4个TEXT标签用于存放该控件的file各属性 add by lkl 2010.4.13 -->
   <input type="hidden" id="txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>1" <logic:notEmpty name="preferences" property="value(size)">value="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>/>
   <input type="hidden" id="txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>2"  value="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>"/>
   <input type="hidden" id="txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>3" <logic:notEmpty name="preferences" property="value(fileStyle)"> value="<bean:write name="preferences" property="value(fileStyle)"/>"</logic:notEmpty>/>
   <input type="hidden" id="txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>4"  value="<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>"/>
    <div id="preview_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>" 
    	 style="<logic:notEmpty name="preferences" property="value(previewWidth)">width:<bean:write name="preferences" property="value(previewWidth)"/>;</logic:notEmpty> <logic:notEmpty name="preferences" property="value(previewHeight)">height:<bean:write name="preferences" property="value(previewHeight)"/>;</logic:notEmpty>" <logic:notEmpty name="preferences" property="value(previewStyle)">class="<bean:write name="preferences" property="value(previewStyle)"/>"</logic:notEmpty>>
    	 <img  style="<logic:notEmpty name="preferences" property="value(previewWidth)">width:<bean:write name="preferences" property="value(previewWidth)"/>;</logic:notEmpty> <logic:notEmpty name="preferences" property="value(previewHeight)">height:<bean:write name="preferences" property="value(previewHeight)"/>;</logic:notEmpty>" src="
    	 <%if (strs1 == null) {%>
    	 <logic:empty name="preferences" property="value(defaultImage)">/ide/common/images/no_pic.gif</logic:empty><logic:notEmpty name="preferences" property="value(defaultImage)"><bean:define id="defaultImage" name="preferences" property="value(defaultImage)" type="String"/><%=defaultImage%></logic:notEmpty>
    	 <%}else if(strs1.length > i && strs1[i] != null){%>
    	 <logic:present name='paths'><%=strs1[i] %></logic:present>
    	 <%}else{ %>
    	 <logic:empty name="preferences" property="value(defaultImage)">/ide/common/images/no_pic.gif</logic:empty><logic:notEmpty name="preferences" property="value(defaultImage)"><bean:define id="defaultImage" name="preferences" property="value(defaultImage)" type="String"/><%=defaultImage%></logic:notEmpty>
    	 <%} %>" /></div>
    <span id="previewspan_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>">
	<input type="file" <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> 
	name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>" 
	<logic:notEmpty name="preferences" property="value(fileStyle)">class="<bean:write name="preferences" property="value(fileStyle)"/>"</logic:notEmpty> 
     onchange="preview(preview_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>, this.value,this,previewspan_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>,txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>1,txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>2,txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>3,txt_<%=(String)request.getAttribute("javax.portlet.id")%><%=i%>4,'<bean:write name="preferences" property="value(maxSize)"/>','<logic:present name='pathValue'><bean:write name="pathValue" ignore="true"/></logic:present><logic:notPresent name='pathValue'><logic:empty name="preferences" property="value(defaultImage)">/ide/common/images/no_pic.gif</logic:empty><logic:notEmpty name="preferences" property="value(defaultImage)"><bean:define id="defaultImage" name="preferences" property="value(defaultImage)" type="String"/><%=defaultImage%></logic:notEmpty></logic:notPresent>')" /></span>
  </li>
<%} %>
</ul>

