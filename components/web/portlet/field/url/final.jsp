<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
  <script type="text/Javascript" language="Javascript">
  var theUrl<%=(String)request.getAttribute("javax.portlet.id")%> = '<bean:write name="url" ignore="true"/>';
  if(theUrl<%=(String)request.getAttribute("javax.portlet.id")%>!=null){
	  if(theUrl<%=(String)request.getAttribute("javax.portlet.id")%>.indexOf('http://')!=-1){
		  window.location = theUrl<%=(String)request.getAttribute("javax.portlet.id")%>;
		  //alert("a:" + a);
	  }else{
		  window.location = "http://" + theUrl<%=(String)request.getAttribute("javax.portlet.id")%>;
		  //alert("b:" + b);
	  }
  }  
  </script>