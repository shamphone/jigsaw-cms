<%@ page contentType="text/html; charset=UTF-8"%><%@ taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@ taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<div id='demo<%=(String)request.getAttribute("javax.portlet.id")%>'
	style='overflow:hidden;width:<bean:write name="preferences" property="value(width)" ignore="true"/>;'
	align="center">
<table border="0" align="center" cellpadding="1" cellspacing="1"
	cellspace="0">
	<tr>
		<td valign="top"
			id='marquePic1<%=(String)request.getAttribute("javax.portlet.id")%>'>
		<table width='100%' border='0' cellspacing='0'>
			<tr id="marTR<%=(String)request.getAttribute("javax.portlet.id")%>">

			</tr>
		</table>
		</td>
		<td
			id='marquePic2<%=(String)request.getAttribute("javax.portlet.id")%>'
			valign="top"></td>
	</tr>
</table>
</div>
<script type="text/javascript">
try{
	var theTable<%=(String)request.getAttribute("javax.portlet.id")%> = document.getElementById('<bean:write name="preferences" property="value(propertyId)" />');
	if(theTable<%=(String)request.getAttribute("javax.portlet.id")%>!=null){
		for(var i=0;i<theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length;i++){
			var oTR<%=(String)request.getAttribute("javax.portlet.id")%> = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i];
			var newTD<%=(String)request.getAttribute("javax.portlet.id")%> = marTR<%=(String)request.getAttribute("javax.portlet.id")%>.insertCell(-1);
			newTD<%=(String)request.getAttribute("javax.portlet.id")%>.noWrap = "noWrap";
			for(j=0;j<oTR<%=(String)request.getAttribute("javax.portlet.id")%>.cells.length;j++){
				newTD<%=(String)request.getAttribute("javax.portlet.id")%>.innerHTML = newTD<%=(String)request.getAttribute("javax.portlet.id")%>.innerHTML + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[j].innerHTML ;
			}
		}
	}
	var speed<%=(String)request.getAttribute("javax.portlet.id")%>='<bean:write name="preferences" property="value(speed)" ignore="true"/>';
	marquePic2<%=(String)request.getAttribute("javax.portlet.id")%>.innerHTML=marquePic1<%=(String)request.getAttribute("javax.portlet.id")%>.innerHTML;
	function Marquee<%=(String)request.getAttribute("javax.portlet.id")%>(){ 
		if(demo<%=(String)request.getAttribute("javax.portlet.id")%>.scrollLeft>=marquePic1<%=(String)request.getAttribute("javax.portlet.id")%>.scrollWidth){ 
			demo<%=(String)request.getAttribute("javax.portlet.id")%>.scrollLeft=0;
		}else{ 
			demo<%=(String)request.getAttribute("javax.portlet.id")%>.scrollLeft++;
		} 
	} 
	var MyMar<%=(String)request.getAttribute("javax.portlet.id")%>=setInterval(Marquee<%=(String)request.getAttribute("javax.portlet.id")%>,speed<%=(String)request.getAttribute("javax.portlet.id")%>) 
	demo<%=(String)request.getAttribute("javax.portlet.id")%>.onmouseover=function() {clearInterval(MyMar<%=(String)request.getAttribute("javax.portlet.id")%>)} 
	demo<%=(String)request.getAttribute("javax.portlet.id")%>.onmouseout=function() {MyMar<%=(String)request.getAttribute("javax.portlet.id")%>=setInterval(Marquee<%=(String)request.getAttribute("javax.portlet.id")%>,speed<%=(String)request.getAttribute("javax.portlet.id")%>)}
}catch(e){
	alert("系统出错（单行向左滚动占位符有误）！");
} 
</script>

