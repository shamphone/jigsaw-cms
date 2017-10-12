<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择模板文件</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script Language='Javascript' src='<html:rewrite page="/script/ajax.js" module="/common"/>' type="text/javascript"></script>  
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smtemplate.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smsitefactory.js"/>"></script>
<base target="_self">
</head>
<body>
	<form action="doImport.do" method="post" onsubmit="return validate(this)" enctype="multipart/form-data">
		<div style="margin-top:20px;">模板文件：<input type="file" name="zipFile" style="width:300px"/></div>
		<div class="operation">
			<button id="btnOk" type="submit" onclick="ok(this.form)" class="commonbut">确定</button>
			<button id="btnCancel" type="button" onclick="window.close();" class="commonbut">取消</button>
		</div>
	</form>
	<script type="text/javascript">
	function validate(oForm){
		var zipFilePath = oForm.zipFile.value;
		if(zipFilePath==null||zipFilePath==""){
			alert("请选择一个正确的模板文件导入！");
			return false;
		} 
		var temps = zipFilePath.split(".");
        if(temps.length<2||temps[temps.length-1]!="tpl"){
            alert("文件格式不正确，请重新选择正确的模板文件！");
            return false;
        }
        var temps1 = temps[0].split("\\");
        var zipFileName = temps1[temps1.length-1];
        var template = siteFactory.getTemplate(zipFileName);
       	if(template!=null){
         	if(!confirm("模板已存在，确认覆盖吗？")){
         		return false;
         	}
       	}
	}
	function ok(oForm){


	}
	</script>
</body>
</html>