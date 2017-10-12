<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
共计<bean:write name="portlet.tag.pager" property="count"/>项内容，当前为<bean:write name="portlet.tag.pager" property="nextPage"/>/<bean:write name="portlet.tag.pager" property="pageCount"/>页<logic:present name="portlet.tag.first">，<a href="<bean:write name="portlet.tag.first"/>" target="_self">首页</a></logic:present><logic:present name="portlet.tag.prev">，<a href="<bean:write name="portlet.tag.prev"/>" target="_self">上一页</a></logic:present><logic:present name="portlet.tag.next">，<a href="<bean:write name="portlet.tag.next"/>" target="_self">下一页</a></logic:present><logic:present name="portlet.tag.last">，<a href="<bean:write name="portlet.tag.last"/>" target="_self">末页</a></logic:present>
