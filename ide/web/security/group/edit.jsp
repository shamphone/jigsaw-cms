<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改组</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<html:javascript formName="groupForm"/>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ListTable.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>

<base target="_self" />
</head>
<body>
<html:form action="Update.do" enctype="multipart/form-data" method="POST">
<div>请输入组名：</div>
<html:text property="groupname" size="40" maxlength="16"/>
<html:hidden  property="nodeID" />
<html:hidden  property="parentNodeId" />
	<div class="operation">
	<button id="btnOk" type="button" onclick="check(this.form)" class="commonbut">保存</button>
	<button id="btnCancel" type="button" onclick="_close()" class="commonbut">取消</button>
	</div>
</html:form>
<script type="text/javascript">
function checkGroupname(oform){
	var groupname = oform.groupname.value;
	var nodeID = oform.nodeID.value;
	var parentNodeId = oform.parentNodeId.value;
			var xmlhttp = getXMLHttpRequest();
			var urlTemp = '<html:rewrite module="/security/group" page="/validateUnique.do"/>?value=' + encodeURI(groupname) + '&property=title&parentNodeID='+ parentNodeId +'&nodeID='+ nodeID;
			xmlhttp.open("Post",urlTemp,false);
			xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
			xmlhttp.send(null);
			if(xmlhttp.responseText == "true"){
				return true;
			}else{
				alert("当前父组下已经存在此子组！");
				return false;
			}
}
function check(form){
  if (validateGroupForm(form)){
		if(checkGroupname(form)){
      form.submit();
  }
  else{
	   return false;
  }
  }else{
		return false;
	}
}
function _close(){
	if(document.all){
		window.close();
	}else{
		window.parent.close();
	}
}
</script>
</body>
</html>
