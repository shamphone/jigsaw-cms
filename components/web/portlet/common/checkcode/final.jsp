<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table>
<tr>
<td>
<img id="authCodeImage" border=0 src="/passport/checkcode/authCodeImage.jsp">
&nbsp;
<span onclick="changeImage();" style="cursor:pointer"><font id="authCodeChangeImagetext" color="blue">换一张</font></span>
&nbsp;<br>
<input type="text" id="authCodeInputCheck" value="" onblur="checkit()"><br>
<span style="display:none" id="authCodeInfo"></span>
</td>
</tr>
</table>

<script language="Javascript" type="text/Javascript">
var checkcodepassed = "false";          //实际应用时可通过该变量来判断验证码是否输入正确，false为输入错误，true为输入正确
function changeImage()
{
  document.getElementById("authCodeImage").src = "/passport/checkcode/authCodeImage.jsp?date="+(new Date());
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
     //设置回调处理函数
  var url = '<html:rewrite module="/passport"  page="/checkcode/authCodeReturn.jsp"/>?date='+(new Date());                 //发送请求的路径
  req.open("GET",url,false);             //打开连接
  req.send(null);          //发送请求
  var latestcode = req.responseText;
  latestcode = latestcode.Trim();
  var inputcode = document.getElementById("authCodeInputCheck").value;
  inputcode = inputcode.Trim();
  if(latestcode != inputcode)
  {
     checkcodepassed = "false";
     alert("验证码输入不正确，请重新输入！");  
 	return false;
  }else{
    checkcodepassed = "true";
    return true;
  }
 }
</script>

