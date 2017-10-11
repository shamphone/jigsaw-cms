<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@page import="javax.portlet.PortletPreferences" %>
<table id='<%=(String)request.getAttribute("javax.portlet.id")%>' style="width:<bean:write name="preferences" property="value(width)"/>px" cellspacing="0" cellpadding="0" border="0">
<%java.util.List<String> list = (java.util.List<String>)request.getAttribute("displayValues"); %><tr><td colspan="2">
<iframe id='<%=(String)request.getAttribute("javax.portlet.id")%>Iframe' name='<%=(String)request.getAttribute("javax.portlet.id")%>Iframe' style="margin:0px;padding:0px;width:<bean:write name="preferences" property="value(width)"/>px" allowtransparent="true" scrolling="no" frameborder=0  height="30px" src='/ide/common/fileUpload.do?portletID=<%=(String)request.getAttribute("javax.portlet.id")%>&owner=<bean:write name="preferences" property="value(owner)" />'></iframe>
</td></tr><tr><td>
<input type="hidden" value="asfileforform" name="asfileforform"/><select onblur="Validator.ValidateComponent(this, this.name,'asayfile',this)" id='<%=(String)request.getAttribute("javax.portlet.id")%>Select' name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>" multiple="multiple" style="width:100%">
<logic:present name="values">
<logic:iterate id="value" name="values" indexId="index"><option value="<bean:write name="value" ignore="true"/>"><%= list.get(index.intValue()) %></option></logic:iterate>
</logic:present>
</select></td><td style="width:60px"><button id="<%=(String)request.getAttribute("javax.portlet.id")%>delButton" onclick="delAsynFile('<%=(String)request.getAttribute("javax.portlet.id")%>')"  style="font-size:12px;width:55px;height:23px;margin-left:5px;line-height:18px;">删除</button></td></tr>
<script type="text/javascript" language="javascript">
document.all('<%=(String)request.getAttribute("javax.portlet.id")%>Select').style.width = (<bean:write name="preferences" property="value(width)"/> - 60)+"px";
var aForm = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>'+'delButton').form;
var fun<bean:write name='javax.portlet.id'/>=function(){
	if(aForm['<bean:write name="preferences" property="value(propertyId)" />'].options[0]!=null)
		selectAll(aForm['<bean:write name="preferences" property="value(propertyId)" />']);
	else{
		aForm['<bean:write name="preferences" property="value(propertyId)" />'].style.display = "none";
		addOption(aForm['<bean:write name="preferences" property="value(propertyId)" />'],"clear","");	
		selectAll(aForm['<bean:write name="preferences" property="value(propertyId)" />']);
		aForm['<bean:write name="preferences" property="value(propertyId)" />'].style.display = "inline";
	}
	};
	if(aForm.attachEvent){
		aForm.attachEvent("onsubmit",fun<bean:write name='javax.portlet.id'/>);
	}else{
		aForm.addEventListener("submit",fun<bean:write name='javax.portlet.id'/>,false);
	}
</script></table>