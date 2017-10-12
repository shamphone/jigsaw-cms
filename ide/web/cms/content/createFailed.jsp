<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="message_frame">
  <tiles:put name="message">属性<%=request.getParameter("contentID")%>创建/编辑内容失败</tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
    //setTimeout("window.close()",2000);
    //window.parent.document.frames('list').location.reload();
    </script>
  </tiles:put>
</tiles:insert>
<form>
   <td>
     <center> 
       <input type="button" onclick="window.close()" value="关闭" style="width:45px;height:25px">
     </center> 
  </td>
</form>