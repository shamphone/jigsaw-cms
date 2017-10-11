<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><input type="checkbox" value="nodes" onclick='selectAll<%=(String)request.getAttribute("javax.portlet.id")%>(this)'/>
<script type="text/Javascript" language="Javascript">
var flag<%=(String)request.getAttribute("javax.portlet.id")%>=false;
function selectAll<%=(String)request.getAttribute("javax.portlet.id")%>(oCheckbox){
  var form=oCheckbox.form;
  if(form!=null){
	  flag<%=(String)request.getAttribute("javax.portlet.id")%> = (flag<%=(String)request.getAttribute("javax.portlet.id")%>==true)?false:true;
	  selectCheckBoxAll(form.node,flag<%=(String)request.getAttribute("javax.portlet.id")%>);
  }else{
	  alert("找不到复选框所在的form");
	  return;
  }
  
}
</script>
