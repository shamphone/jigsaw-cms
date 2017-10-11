<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="javascript">
  <script type="text/javascript" language="javascript">
		window.close();
	</script>
  </tiles:put>
  <tiles:put name="dialog">
  <p align="center">该分类已经存在</p></tiles:put>
</tiles:insert>
