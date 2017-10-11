<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%><%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<logic:empty name="preferences" property="value(imgsrc)">
<logic:present name="nodeID">
<button <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>  onclick="document.location='<bean:write name='preferences' property='value(channel)'/>?contentId=<bean:write name='nodeID'/>';"><bean:write name="preferences" property="value(value)"/></button>
</logic:present>
</logic:empty>
<logic:notEmpty name="preferences" property="value(imgsrc)">
<logic:present name="nodeID">
<img <logic:notEmpty name="preferences" property="value(imgstyle)">class="<bean:write name="preferences" property="value(imgstyle)"/>"</logic:notEmpty><logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty> onclick="document.location='<bean:write name="preferences" property="value(channel)"/>?contentId=<bean:write name='nodeID'/>';"<logic:notEmpty name="preferences" property="value(imgsrc)">src="<bean:write name="preferences" property="value(imgsrc)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(imghwidth)">width="<bean:write name="preferences" property="value(imgwidth)"/>"</logic:notEmpty> <logic:notEmpty name="preferences" property="value(imgheight)">height="<bean:write name="preferences" property="value(imgheight)"/>"</logic:notEmpty>/>
</logic:present>
</logic:notEmpty>
