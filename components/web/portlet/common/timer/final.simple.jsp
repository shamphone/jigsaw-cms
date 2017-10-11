<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<span id="dSpan<bean:write name='javax.portlet.id'/>"></span><bean:message key="com.fulong.portal.portlet.timer.finalNormal.day" bundle="portalTimer"/>
<script type="text/javascript">
var _timer = new _timer();
_timer.setTime('<bean:write name="preferences" property="value(time)"/>');
_timer.renderD(document.getElementById('dSpan<bean:write name="javax.portlet.id"/>'),_timer);
</script>
