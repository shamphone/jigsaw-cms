<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<script type="text/javascript">
    var _oRet = [];
    _oRet[0] = '<bean:write name="path" ignore="true"/>';
    _oRet[1] = '<bean:write name="name" ignore="true"/>';
    _oRet[2] = '<bean:write name="id" ignore="true"/>';
    top.returnValue = _oRet;
    top.close();
</script>
</head>
</html>
