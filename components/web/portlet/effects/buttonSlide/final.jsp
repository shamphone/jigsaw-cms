<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<%
String width = preferences.getValue("width", "240");
String height = preferences.getValue("height", "150");
%>
<DIV id='buttonSlide<%=(String)request.getAttribute("javax.portlet.id")%>'></DIV>
<SCRIPT type=text/javascript>
try{
	var flashAddress = '/components/portlet/effects/buttonSlide/pz1.swf';
	var pics_b<%=(String)request.getAttribute("javax.portlet.id")%>="";
	var pics_s<%=(String)request.getAttribute("javax.portlet.id")%>="";	
	var mylinks<%=(String)request.getAttribute("javax.portlet.id")%>="";
	var texts<%=(String)request.getAttribute("javax.portlet.id")%>="";
	var texts_con<%=(String)request.getAttribute("javax.portlet.id")%>="";

	var theTable<%=(String)request.getAttribute("javax.portlet.id")%> = document.getElementById('<bean:write name="preferences" property="value(propertyId)" />');
	if(theTable<%=(String)request.getAttribute("javax.portlet.id")%>!=null){
		var rowNum<%=(String)request.getAttribute("javax.portlet.id")%> = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length;
		if(rowNum<%=(String)request.getAttribute("javax.portlet.id")%>>0){
			texts<%=(String)request.getAttribute("javax.portlet.id")%> =  theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[0].cells[0].innerText;
			mylinks<%=(String)request.getAttribute("javax.portlet.id")%> =  theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[0].cells[1].innerText;
			pics_b<%=(String)request.getAttribute("javax.portlet.id")%> =  theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[0].cells[2].firstChild.src;
			pics_s<%=(String)request.getAttribute("javax.portlet.id")%> =  theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[0].cells[2].firstChild.src;
			texts_con<%=(String)request.getAttribute("javax.portlet.id")%> =  theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[0].cells[3].innerText;
			if(rowNum<%=(String)request.getAttribute("javax.portlet.id")%>>1){				
				for(i=1;i<rowNum<%=(String)request.getAttribute("javax.portlet.id")%>;i++){
					texts<%=(String)request.getAttribute("javax.portlet.id")%> = texts<%=(String)request.getAttribute("javax.portlet.id")%> + "|" + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[0].innerText;
					mylinks<%=(String)request.getAttribute("javax.portlet.id")%> = mylinks<%=(String)request.getAttribute("javax.portlet.id")%> + "|" + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[1].innerText;
					pics_b<%=(String)request.getAttribute("javax.portlet.id")%> = pics_b<%=(String)request.getAttribute("javax.portlet.id")%> + "|" + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[2].firstChild.src;
					pics_s<%=(String)request.getAttribute("javax.portlet.id")%> = pics_s<%=(String)request.getAttribute("javax.portlet.id")%> + "|" + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[2].firstChild.src;
					texts_con<%=(String)request.getAttribute("javax.portlet.id")%> = texts_con<%=(String)request.getAttribute("javax.portlet.id")%> + "|" + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[3].innerText;
					
				}
			}
		}
	}
	var flashObject<%=(String)request.getAttribute("javax.portlet.id")%> = new sohuFlash(flashAddress,"sohuFlashID01",'<%= width%>','<%= height%>',"5","#ffffff");
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addParam("quality", "high");
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addParam("salign", "t");
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addVariable("b",pics_b<%=(String)request.getAttribute("javax.portlet.id")%>);
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addVariable("p",pics_s<%=(String)request.getAttribute("javax.portlet.id")%>);	
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addVariable("l",mylinks<%=(String)request.getAttribute("javax.portlet.id")%>);
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addVariable("icon",texts<%=(String)request.getAttribute("javax.portlet.id")%>);	
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.addVariable("icon2",texts_con<%=(String)request.getAttribute("javax.portlet.id")%>);
	flashObject<%=(String)request.getAttribute("javax.portlet.id")%>.write('buttonSlide<%=(String)request.getAttribute("javax.portlet.id")%>');
}catch(e){
	alert("特效占位符出错");
}

</SCRIPT>