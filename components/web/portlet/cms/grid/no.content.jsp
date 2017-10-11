<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<table width="100%" cellpadding="2" cellspacing="2" class="<bean:write  name='preferences' property="value(list-style)" ignore="true"/>"  id="<bean:write name='javax.portlet.id'/>">
<tr><td>这是内容表格占位符。</td></tr>
</table>
<div>共计200项内容，当前为2/5页，<a href="#">首页</a>，<a href="#">上一页</a>，<a href="#">下一页</a>，<a href="#">末页</a>
</div>
