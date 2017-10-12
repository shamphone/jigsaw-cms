<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>免费体验卡</title>
<style type="text/css">
<!--
body {
	margin: 0px;
	background-color: #f4f4f4;
	line-height:22px;
}
body,td,th {
	font-size: 12px;
}
a {
	font-size: 12px;
	color: #0000FF;
}
a:visited {
	color: #0000FF;
}
a:hover {
	color: #000066;
}
-->
</style></head>

<body>
<table width="588" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="images/top1.gif" width="613" height="12" /></td>
  </tr>
  <tr>
    <td valign="top" background="images/cen.gif"><table width="580" border="0" align="center" cellpadding="0" cellspacing="0" style=" margin-top:10px;">
      <tr>
        <td width="110" rowspan="4"><img src="images/card.gif" width="109" height="110" /></td>
        <td width="106" valign="bottom"><img src="images/gxn.gif" width="99" height="41" /></td>
        <td width="364" height="79" valign="bottom" style="font-size:14px">成功获得了体验卡。</td>
      </tr>
      <tr>
        <td height="0" colspan="2" valign="middle" ></td>
        </tr>
      <tr>
        <td height="0" colspan="2" valign="middle" ></td>
      </tr>
      <tr>
        <td height="23" colspan="2" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="1" bgcolor="#919191"></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td colspan="2">　　　　　　　体验卡卡号: <font color="#009900"><bean:write name="newCard" property="id"/></font></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td colspan="2">　　　　　　　体验卡验证码: <font color="#009900">123456 </font></td>
      </tr>
    </table>
      <table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>使用本卡需知：<a href="#">详细</a></td>
      </tr>
      <tr>
          <td>·请牢记体验卡卡号和体验卡验证码，您可以使用这个免费体验卡进行注册激活<br />
              ·中小在线网络服务的具体内容由中小在线根据实际情况提供<br />
              ·例如自助建站、域名注册、在线支付、搜索、论坛(BBS)、电子邮件、发表新闻评论等
          </td>
      </tr>
      <tr>
          <td align="center">
              <input type="button"  onclick="ok()" value="确定" />
          </td>
      </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td><img src="images/bot.gif" width="613" height="14" /></td>
  </tr>
</table>
  <html:javascript formName="registerForm"/>
  <script type="text/javascript">
  function ok(){
      opener.document.getElementById('registerForm').username.value='<bean:write name="newCard" property="id"/>';
      opener.document.getElementById('registerForm').password.value='123456';
      opener.document.getElementById( "err_username" ).innerHTML='<img src=/common/images/icon_right_19x19.gif>';
      opener.document.getElementById( "err_password" ).innerHTML='<img src=/common/images/icon_right_19x19.gif>';
      window.close();
  }
  </script>
</body>
</html>
