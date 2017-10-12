<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="message_frame">
  <tiles:put name="message">创建内容分类成功</tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
      var url="<html:rewrite page='/dict/dictDetail.do' module='/cms'/>?dictId=<bean:write name='dict' property='ID'/>";
      ContentFrame.getSelectedModule().addItem('<bean:write name="dict" property="displayName"/>', url,ContentFrame.getSelectedModule().Tree);
    </script>
  </tiles:put>
</tiles:insert>
