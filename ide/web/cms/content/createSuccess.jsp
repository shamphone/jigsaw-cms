<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="message_frame">
<tiles:put name="title">保存成功</tiles:put>
  <tiles:put name="message">
  <p align="center"><font size="3">内容保存成功</font></p>
  
  </tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
    if(document.all){
    	window.close();
    }else{
    	window.parent.close();
    }
	window.opener.top.frames['list'].location.reload();
    </script>
  </tiles:put>
</tiles:insert>
