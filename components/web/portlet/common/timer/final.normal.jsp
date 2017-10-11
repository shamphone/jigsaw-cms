<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<span id="dSpan<bean:write name='javax.portlet.id'/>"></span><bean:message key="com.fulong.portal.portlet.timer.finalNormal.day" bundle="portalTimer"/>
<span id="hSpan<bean:write name='javax.portlet.id'/>"></span><bean:message key="com.fulong.portal.portlet.timer.finalNormal.hour" bundle="portalTimer"/>
<span id="mSpan<bean:write name='javax.portlet.id'/>"></span><bean:message key="com.fulong.portal.portlet.timer.finalNormal.minute" bundle="portalTimer"/>
<span id="sSpan<bean:write name='javax.portlet.id'/>"></span><bean:message key="com.fulong.portal.portlet.timer.finalNormal.second" bundle="portalTimer"/>
<script type="text/javascript">
var _timer<bean:write name='javax.portlet.id'/> = new _timer();
_timer<bean:write name='javax.portlet.id'/>.setTime('<bean:write name="preferences" property="value(time)"/>');
_timer<bean:write name='javax.portlet.id'/>.renderD(document.getElementById('dSpan<bean:write name="javax.portlet.id"/>'),_timer<bean:write name='javax.portlet.id'/>);
_timer<bean:write name='javax.portlet.id'/>.renderH(document.getElementById('hSpan<bean:write name="javax.portlet.id"/>'),_timer<bean:write name='javax.portlet.id'/>);
_timer<bean:write name='javax.portlet.id'/>.renderM(document.getElementById('mSpan<bean:write name="javax.portlet.id"/>'),_timer<bean:write name='javax.portlet.id'/>);
_timer<bean:write name='javax.portlet.id'/>.renderS(document.getElementById('sSpan<bean:write name="javax.portlet.id"/>'),_timer<bean:write name='javax.portlet.id'/>);
</script>
