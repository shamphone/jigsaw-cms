<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<div id="pagerDiv"><span id="countSpan">共计<bean:write name="com.fulong.pager" property="count"/>项内容&nbsp;当前为<bean:write name="com.fulong.pager" property="nextPage"/>/<bean:write name="com.fulong.pager" property="pageCount"/>页</span><span id="pageSpan"><logic:present name="portlet.tag.first">&nbsp;<a target="<bean:write name="portlet.tag.target"/>" href="<bean:write name="portlet.tag.first"/>">首页</a></logic:present><logic:present name="portlet.tag.prev">&nbsp;<a target="<bean:write name="portlet.tag.target"/>" href="<bean:write name="portlet.tag.prev"/>">上一页</a></logic:present><logic:present name="portlet.tag.next">&nbsp;<a target="<bean:write name="portlet.tag.target"/>" href="<bean:write name="portlet.tag.next"/>">下一页</a></logic:present><logic:present name="portlet.tag.last">&nbsp;<a target="<bean:write name="portlet.tag.target"/>" href="<bean:write name="portlet.tag.last"/>">末页</a></logic:present></span></div>