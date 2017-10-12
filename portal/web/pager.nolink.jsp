<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
共计<bean:write name="portlet.tag.pager" property="count"/>项内容<logic:notEqual value="0" name="portlet.tag.pager" property="pageCount">，当前为<bean:write name="portlet.tag.pager" property="nextPage"/>/<bean:write name="portlet.tag.pager" property="pageCount"/>页</logic:notEqual><logic:present name="portlet.tag.first">，<span style="color:#3300FF;cursor:pointer;">首页</span></logic:present><logic:present name="portlet.tag.prev">，<span style="color:#3300FF;cursor:pointer;">上一页</span></logic:present><logic:present name="portlet.tag.next">，<span style="color:#3300FF;cursor:pointer;">下一页</span></logic:present><logic:present name="portlet.tag.last">，<span style="color:#3300FF;cursor:pointer;">末页</span></logic:present>
