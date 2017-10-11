<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<table border="0" cellpadding="2" cellspacing="2" class="formTable">
        <tr><td><span  class="formTitle">标题</span></td></tr>
        <tr><td class="formComponent"><input type="text" readonly="readonly"/></td></tr>
        <tr><td><span  class="formTitle">文本</span></td></tr>
        <tr><td class="formComponent"><textarea cols="20" rows="20"></textarea></td></tr>
    </table>
    <div class="operation">
        <input type="button" value="保存" disabled="disabled"/>
    </div>
