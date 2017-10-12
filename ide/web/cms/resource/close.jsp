<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<script type="text/javascript">
   var ret=new Object();
   ret='<%=request.getContextPath()%><%=request.getParameter("path")%>';

   var oInput=window.opener.document.getElementById("txtUrl");
   if(oInput!=null){
       oInput.value=ret;
   }
   window.returnValue=ret;
   window.close();
</script>
</body>
</html>
