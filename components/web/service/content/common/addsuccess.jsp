<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">添加远程分类</tiles:put>
<tiles:put name="dialog">

</tiles:put>
<tiles:put name="javascript">
<script type="text/Javascript" language="Javascript">
  window.onload = function()
      {
	  var result=new Object();
      result.id='<bean:write name="id"/>';
      result.categoryName='<bean:write name="categoryName"/>';
      window.returnValue=result;
      window.close();
      }
  </script>
  </tiles:put>
  </tiles:insert>
