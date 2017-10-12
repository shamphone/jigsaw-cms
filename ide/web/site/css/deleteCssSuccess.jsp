<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<script type="text/javascript">
    var _oRet = [];
    _oRet[0] = '<bean:write name="id" ignore="true"/>';
    top.index.DeleteCssSuccess(_oRet);
</script>
</head>
<body>
  <center>删除成功</center>
</body>
</html>
