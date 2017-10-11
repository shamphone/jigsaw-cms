<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<ul class="<bean:write  name='preferences' property="value(list-style)" ignore="true"/>"  id="<bean:write name='javax.portlet.id'/>">
<li>这是内容列表占位符。</li>
<li>这是内容列表占位符。</li>
<li>这是内容列表占位符。</li>
<li>这是内容列表占位符。</li>
</ul>
<div>共计200项内容，当前为2/5页，<a href="#">首页</a>，<a href="#">上一页</a>，<a href="#">下一页</a>，<a href="#">末页</a>
</div>
