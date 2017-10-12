<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">复制分类</tiles:put>
  <tiles:put name="dialog"><p align="center"><font size="2">复制内容分类成功</font></p></tiles:put>
<tiles:put name="javascript">
    <script language="JavaScript" type="text/Javascript">
    	var result=new Object();
    	result.ID='<bean:write name="category" property="ID"/>';
    	result.name='<bean:write name="category" property="name"/>';
    	result.isChildrenCategory='<bean:write name="isChildrenCategory"/>';
    	window.returnValue=result;
    	window.close();
    </script>
</tiles:put>
</tiles:insert>
