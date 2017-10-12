<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="message_frame">
  <tiles:put name="message">修改内容分类成功</tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
      var item=ContentFrame.getSelectedIndexItem();
      item.setTitle('<bean:write name="dict" property="displayName"/>');
    </script>
  </tiles:put>
</tiles:insert>

