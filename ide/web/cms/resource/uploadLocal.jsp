<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>文件上传</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<base target="_self"/>
<script type="text/javascript">
var bFinish = '<%= request.getParameter("finish")%>';
function doInit(){
	var FCKConfig = parent.oEditor.FCKConfig;
	if(FCKConfig.ImageBrowserType!=undefined&&FCKConfig.ImageBrowserType=="DB"){
		document.forms["uploadForm"].elements["upload"].value = "DB";
	}else{
		document.forms["uploadForm"].elements["template"].value = window.parent.oEditor.oTemplate.name;
		document.forms["uploadForm"].elements["upload"].value = "FILE";
	}
	if(bFinish=="true"){
		parent.txtUrl.value = '<%= request.getAttribute("filePath")%>';
		parent.document.getElementById("btnBrowseLocal").disabled = false;
	}
};

function BrowseLocalFile(oFile){
	parent.document.getElementById("btnBrowseLocal").disabled = true;
	oFile.form.submit();
}

</script>
</head>
<body style="background:buttonface;padding: 0px; margin: 0px" onload="doInit()">
<form name="uploadForm" action="/ide/cms/resource/uploadLocal.do" enctype="multipart/form-data" method="post" style="padding: 0px; margin: 0px">
<span id="filereset">
<input type="file" name="file[0]" onpropertychange="BrowseLocalFile(this)" style="width:500px"/>
</span>
<input type="hidden" name="template" />
<input type="hidden" name="upload" />
<input type="hidden" name="folder" value="/images"/> 
</form>
</body>
</html>
