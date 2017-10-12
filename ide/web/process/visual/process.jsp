<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="EXPIRES" content="0" />
<meta http-equiv="Pragma" Content="No-cach" />
<TITLE>流程定义</TITLE>
<link rel="stylesheet" type="text/css" href="style.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="pmsconfig.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmspin.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmdefinition.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmactivity.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmeditor.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmtransition.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmparameters.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmvmlproperties.js"></script>
<script language="Javascript" type="text/Javascript" src="classes/pmevents.js"></script>
<xml id="oXML" src="../process/source.do?id=<%=request.getParameter("processID")%>&timestamp=<%= System.currentTimeMillis() %>"></xml>
</HEAD>
<BODY onload='PMEditor.init()' onkeydown="PMKBEventHandlers.fire()">
<div id="VMLEditor"  onclick="PMEditor_FocusOnProcess(this)">
	<v:shapetype id="laure" coordorigin="-120 50" coordsize="240 240" o:master="True" style="top:1;left:1;width:50;height:50" path="m 1,28 l -143,168, 1,308, 140,168 x e"></v:shapetype>
</div>
<v:group id="FlowVML" coordsize="30000,25000" style="top:1px;left:1px;width:6000px;height:5000px;position:absolute;">
</v:group><textarea id="CodeEditor" rows="100" cols="100" style="display:none" wrap="off"></textarea>
</BODY>
</HTML>
