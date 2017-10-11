<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>认证码输入页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
</head>
<body>

<img id="authCodeImage" border=0 src="/components/portlet/form/component/checkcode/authCodeImage.jsp">
<span onclick="changeImage();" style="cursor:pointer"><font id="authCodeChangeImagetext" color="blue">看不清？换一张</font></span>
<br>
<font id="authCodePleaseText">请输入以上认证码：</font>
<br>
<input type="text" id="authCodeInputCheck" value="" onblur="checkit()">
&nbsp;
<span id="authCodeInfo"></span>

<script type="text/javascript">
var checkcodepassed = "false";          //实际应用时可通过该变量来判断验证码是否输入正确，false为输入错误，true为输入正确
function changeImage()
{
  document.getElementById("authCodeImage").src = "/components/portlet/form/component/checkcode/authCodeImage.jsp?date="+(new Date());
}

 String.prototype.Trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function checkit()
{
  var req = null;
  try{
    req = new XMLHttpRequest();
  }catch(error){
    try{
      req = new ActiveXObject("Microsoft.XMLHTTP");
    }catch(error){return false;}
  }
 	req.onreadystatechange = function ajaxExcute()
  {
	   if((req.readyState==4)&&(req.status==200))
	   {
		  var latestcode = req.responseText;
		  latestcode = latestcode.Trim();
		  var inputcode = document.getElementById("authCodeInputCheck").value;
		  inputcode = inputcode.Trim();
		  if(latestcode != inputcode)
		  {
			checkcodepassed = "false";
		    //alert("验证码输入不正确，请重新输入！");  
			document.getElementById("authCodeInfo").innerHTML = '<font color="red">验证码输入错误，请重新输入！</font>';
			//document.getElementById("codeImage").src = "authCodeImage.jsp?date="+(new Date());    //当输入错误时验证码自动刷新
		  }else{
			checkcodepassed = "true";
		    document.getElementById("authCodeInfo").innerHTML = '<font color="green">验证码输入正确！</font>';
		  }
	   }
  }     //设置回调处理函数
 var url = "/components/portlet/form/component/checkcode/authCodeReturn.jsp?date="+(new Date());                 //发送请求的路径
 req.open("GET",url);             //打开连接
 req.send(null);          //发送请求
}


</script>

</body>
</html>