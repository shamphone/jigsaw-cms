<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<ul class='<bean:write name="preferences" property="value(ULStyle)" ignore="true"/>'>
<li><input type="text" class='<bean:write name="preferences" property="value(inputStyle)" ignore="true"/>' name="<bean:write name='javax.portlet.id'/>checkcode" id="<bean:write name='javax.portlet.id'/>checkcode" value="" onblur="Validator.ValidateComponent(this, this.name)"></li>
<li><img alt="看不清，换一张" onclick="changeImage();" style="cursor:pointer" id="authCodeImage" border=0 height='<bean:write name="preferences" property="value(height)" ignore="true"/>' width='<bean:write name="preferences" property="value(width)" ignore="true"/>' src='/components/portlet/form/component/checkcode/authCodeImage.jsp'/></li>
<li>
<span id="<bean:write name='javax.portlet.id'/>_0" style="display:none" <logic:notEmpty name='preferences' property='value(error-style)'>class=<bean:write name='preferences' property='value(error-style)'/></logic:notEmpty>><bean:write name="preferences" property="value(error-tips)"/></span>
<span id="<bean:write name='javax.portlet.id'/>_1" <logic:notEmpty name='preferences' property='value(tips-style)'>class=<bean:write name='preferences' property='value(tips-style)'/></logic:notEmpty>><bean:write name="preferences" property="value(tips)"/></span>
</li>
</ul>
<script type="text/javascript">
function changeImage()
{
  document.getElementById("authCodeImage").src='/components/portlet/form/component/checkcode/authCodeImage.jsp?timeStamp='+ new Date().getTime();
}
Validator.AddRule(document.getElementById("<bean:write name='javax.portlet.id'/>checkcode").form.name,'<bean:write name="javax.portlet.id"/>checkcode','<bean:write name="javax.portlet.id"/>_1','<bean:write name="javax.portlet.id"/>_0','checkcode');
</script>