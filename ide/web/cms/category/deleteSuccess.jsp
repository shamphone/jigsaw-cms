<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="dialog"><p align="center"><font size="3">删除内容分类成功</font></p></tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
  //    var item=ContentFrame.getSelectedIndexItem();
  //    item.remove();
  //    ContentFrame.getSelectedIndexItem().deSelect();
    </script>
  </tiles:put>
</tiles:insert>

