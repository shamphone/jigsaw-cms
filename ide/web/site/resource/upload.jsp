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
var doRefresh = '<%= request.getParameter("refresh")%>';
function doInit(){
	if(doRefresh == "true"){
		var node = window.parent.oTree.getSelected();
		node.click();
	}
};
function doSubmit(oBtn){
	var oForm = oBtn.form;
	oBtn.disabled = true;
	if(window.parent.oTree.getSelected()==null){
		alert("请选择要上传到的文件夹")
		var filereset = document.getElementById("filereset");
		filereset.innerHTML = "<input type=\"file\" name=\"file[0]\" style=\"width:500px\"/>";
		oBtn.disabled = false;
		return;
	}else{
		oForm.elements["folder"].value = window.parent.oTree.getSelected().resource.getContextPath();
	}
	oForm.elements["template"].value = window.parent.oTemplate.name;
	oForm.submit();
}
</script>
</head>
<body style="background:buttonface;padding: 0px; margin: 0px" onload="doInit()">
<form action="upload.do" enctype="multipart/form-data" method="post" style="padding: 0px; margin: 0px"><span id="filereset"><input type="file" name="file[0]" style="width:500px"/></span>
<input type="hidden" name="template" /><input type="hidden" name="folder" value="/"/> 
<input type="button" value="上传" onclick="doSubmit(this)"/></form>
</body>
</html>
