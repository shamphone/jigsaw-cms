<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<logic:equal value="true" name="preferences" property="value(showPage)">
<logic:equal value="true" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="default" /></div>
</logic:equal>
   <logic:equal value="isGotoPager" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="goto" /></div>
</logic:equal>
</logic:equal>
<ul id="<bean:write name='javax.portlet.id'/>" class="<bean:write name="preferences" property="value(list-style)" ignore="true"/>">
<bean:define id="path" name="path" type="java.lang.String"/>
<logic:equal value="recursion" name="preferences" property="value(recursion)">
<logic:present name="contents">
   	<logic:iterate indexId="index" id="content" name="contents">
   		<bean:define id="curNode" name="content" type="com.fulong.longcon.repository.Node"/>
   		<bean:define id="propName" name="preferences" property="value(refField)" type="java.lang.String"/>
   		<%request.setAttribute("com.fulong.longcon.Content",content);%>
    	<li id='<bean:write name="content" property="ID" ignore="true"/>'>
      		<site:insert page='<%=path%>' flush="false"></site:insert>
      		<% getChildren(request,response,pageContext,curNode,propName,path); %>
    	</li>
      	<%request.setAttribute("com.fulong.longcon.Content",null); %>
   	</logic:iterate>
</logic:present>
</logic:equal>
<logic:notEqual value="recursion" name="preferences" property="value(recursion)">
<logic:notEmpty name="preferences" property="value(row)">
<bean:define id="rowCount" name="preferences" property="value(row)" type="java.lang.String"/>
  <logic:present name="contents">
    <logic:iterate indexId="index" id="content" name="contents" length='<%=rowCount%>'>
    <%request.setAttribute("com.fulong.longcon.Content",content);request.setAttribute("indexId",index); %>
    <li id='<bean:write name="content" property="ID" ignore="true"/>'>
      <site:insert page='<%=path%>' flush="false"></site:insert>
    </li>
      <%request.setAttribute("com.fulong.longcon.Content",null); request.setAttribute("indexId",null); %>
    </logic:iterate>
   </logic:present>
   </logic:notEmpty>
   <logic:empty name="preferences" property="value(row)">
  <logic:present name="contents">
    <logic:iterate indexId="index" id="content" name="contents">
    <%request.setAttribute("com.fulong.longcon.Content",content);request.setAttribute("indexId",index); %>
    <li id='<bean:write name="content" property="ID" ignore="true"/>'>
      <site:insert page='<%=path%>' flush="false"></site:insert>
    </li>
      <%request.setAttribute("com.fulong.longcon.Content",null); request.setAttribute("indexId",null); %>
    </logic:iterate>
   </logic:present>
   </logic:empty>
   </logic:notEqual>
</ul>
<logic:equal value="true" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="default" /></div>
</logic:equal>
   <logic:equal value="isGotoPager" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="goto" /></div>
</logic:equal>
<logic:equal value="baidu1" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="baidu1" /></div>
</logic:equal>
<logic:equal value="baidu2" name="preferences" property="value(show-pager)">
	<div class="<bean:write name='preferences' property="value(pager-style)" ignore="true"/>"><portlet:pager name="pager" pattern="baidu2" /></div>
</logic:equal>
<%@page import="com.fulong.longcon.repository.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.servlet.jsp.PageContext" %>
<%@page import="com.fulong.portal.core.ServletResponseWrapperInclude" %>
<%!
public void getChildren(ServletRequest request,ServletResponse response,PageContext pageContext,com.fulong.longcon.repository.Node node,String propName,String path){
	try{
		request = (HttpServletRequest)request;
		while(request instanceof HttpServletRequestWrapper){
			request =(HttpServletRequest)((HttpServletRequestWrapper)request).getRequest();
		}
		JspWriter out = pageContext.getOut(); 
		NodeIterator<Node> children = node.getNodes(propName);
		if(children.hasNext()){
			out.println("<ul>");
		}
		while(children.hasNext()){
			Node child = children.nextNode();
			request.setAttribute("com.fulong.longcon.Content",child);
			out.println("<li id='"+child.getID()+"'>");
			((HttpServletRequest)request).getSession().getServletContext().getRequestDispatcher(path).include(request,new ServletResponseWrapperInclude(response,out));
			getChildren(request,response,pageContext,child,propName,path);
			out.println("</li>");
			request.setAttribute("com.fulong.longcon.Content",null);
			if(!children.hasNext()){
				out.println("</ul>");
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
%>


