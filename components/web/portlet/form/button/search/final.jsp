<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="pt"%>
<logic:empty name="preferences" property="value(imgsrc)"><button type="button" id="<bean:write name='javax.portlet.id'/>" <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> onclick="fun<bean:write name='javax.portlet.id'/>(this.form)"><bean:write name="preferences" property="value(value)"/></button></logic:empty>
<logic:notEmpty name="preferences" property="value(imgsrc)">
<button id="<bean:write name='javax.portlet.id'/>h" style="display:none;"></button>
<image type="image" id="<bean:write name='javax.portlet.id'/>" <logic:notEmpty name="preferences" property="value(imgstyle)">class="<bean:write name="preferences" property="value(imgstyle)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> onclick="fun<bean:write name='javax.portlet.id'/>(document.getElementById('<bean:write name='javax.portlet.id'/>h').form)" <logic:notEmpty name="preferences" property="value(imgsrc)">src="<bean:write name="preferences" property="value(imgsrc)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(imghwidth)">width="<bean:write name="preferences" property="value(imgwidth)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(imgheight)">height="<bean:write name="preferences" property="value(imgheight)"/>"</logic:notEmpty>/></logic:notEmpty>
<script type="text/Javascript" language="Javascript">
function fun<bean:write name='javax.portlet.id'/>(oForm){
	var url = "";
	if('<bean:write name="preferences" property="value(forwardSelf)"/>' == "true"){
		url =window.location.href;
	}else{
		url ='<bean:write name="preferences" property="value(channel)"/>?1=1'
	}
	var conditions = new Array();
	<logic:present name="preferences" property="values(conditions)"><logic:iterate id="condition" name="preferences" property="values(conditions)">conditions.push('<bean:write name="condition"/>');</logic:iterate></logic:present>
	for(var i =0;i<conditions.length;i++){
		url+="&conditions="+conditions[i];
	}
	oForm.action=encodeURI(url);
	if(oForm.fireEvent("onsubmit")){
		oForm.submit();
	}
}
</script>