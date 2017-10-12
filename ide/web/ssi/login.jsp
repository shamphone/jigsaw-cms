<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<html>
<head>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script></head>
<body bgcolor="#ffffff">
<form action="">
  用户名：
  <input id="username" type="text" onblur="checkUser()"/>
</form>

<script type="text/javascript">
var xhr = null;
function checkUser()
{
 var username = document.getElementById("username").value;
 if(username == "" || username == null)
 alert("用户名不能为空");
 else if(username != "" || username != null)
 {
  xhr = getXMLHttpRequest();
  var url = '<html:rewrite module="/ssi" page="/checkUsername.do"/>?username='+username;
  sendRequest(xhr,url,ajaxExcute);
 }
}

function ajaxExcute()
{
    if((xhr.readyState==4)&&(xhr.status==200))
    {
     var data=xhr.responseText;
     if(data!=null&&data!="")
      {
       if(data == "false")
       {
         alert("对不起，该用户名已被使用！");
         return;
       }
       else alert("该用户名可以使用！");
       }
    }
}

</script></body>
</html>
