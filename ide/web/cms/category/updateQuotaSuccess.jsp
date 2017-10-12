<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="message_frame">
  <tiles:put name="message">配额更新成功</tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
    if ("<%=request.getParameter("selectedPrincipalID")%>" == null){
      window.parent.document.frames("list").location = 'categoryQuota.do?categoryID=<%=request.getParameter("categoryID")%>';
    }
    else
    window.parent.document.frames("list").location = 'categoryQuota.do?categoryID=<%=request.getParameter("categoryID")%>&selectedPrincipalID=<%=request.getParameter("selectedPrincipalID")%>';
    </script>
  </tiles:put>
</tiles:insert>
