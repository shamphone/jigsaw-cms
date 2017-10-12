<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>正在执行....</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>

<script type="text/javascript">
  var bStop = false;
  var count = new Date().getTime();
  var timer = null;
function doInit(){
	timer = setInterval(loadMessages,2000); 
}
function loadMessages(){
	var url ="${pageContext.request.contextPath}/service/messages.do?msgCode="+window.dialogArguments+"&count="+(count++);
	var req = getXMLHttpRequest();
    req.open("GET",url,false);  	
    req.send(null);	    
	/**
	 * luobin modified in 2010-3-17
	 * bug
	 * 修改目的：没有返回信息
	 * 修改描述：/service/messages.jsp里一些空格导致req.responseXML得到一个空对象，删除/service/messages.jsp中的一些空格后正常
	 */
    var oXML = req.responseXML;
    var nodes = oXML.getElementsByTagName("message");    
    for(var i=0;i<nodes.length;i++){
	    var oDiv = document.createElement("div");
	    oDiv.noWrap= true;
	    oDiv.innerHTML = nodes[i].childNodes[0].nodeValue;
	    document.getElementById("messages").appendChild(oDiv);
    }
	
}
function ok(){
	clearInterval(timer);
}
</script>
<base target="_self" />
</head>
<body onload="doInit()">
<div id="messages" style="height: 350px;width:99%;" class="insetDiv"></div>
<div class="operation">
<button type="button" onclick="window.returnValue=true;window.close()" class="commonbut" id="btnOK">重试</button>
<button type="button" onclick="window.close()" class="commonbut" id="btnOK">关闭</button>
</div>
</body>
</html>
