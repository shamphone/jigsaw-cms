<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员管理系统--获取帮助</title>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 2999 21:29:02 GMT">
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/main2.css"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/stat.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/area.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/data.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/industry.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/sortControl.js"/>"></script>
</head>
<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="headerBack">
  <tr>
    <td width="21%" rowspan="2"><img width="216" height="75" border="0" src="<html:rewrite module="" page="/images/logo.gif"/>" ></img></td>
    <td width="46%" height="35">&nbsp;</td>
    <td width="33%">&nbsp;</td>
  </tr>
  <tr>
    <td height="40" align="center">

    </td>
    <td valign="bottom">
      <table width="253" height="33" border="0" align="right" cellpadding="0" cellspacing="0" id="headerBackButtom" >
        <tr>
          <td width="28"><img src="../member/images/space.gif" border="0" width="1" height="1"/></td>
          <td width="161" >
              <%
              if(request.getUserPrincipal()!=null)

              {String userID = (String)request.getUserPrincipal().getName();%>
              欢迎您，
              <fulong:name name="principal"></fulong:name>
              <%}%>
          </td>
          <td width="64">
              <%
              if(request.getUserPrincipal()!=null)
              {%>
              <a href="../member/logout.do?fromURL=<%=request.getParameter("fromURL")%>"><img src="../member/images/sme1_041.gif" border=""  width="50" height="22"/></a><img src="../member/images/space.gif" width="1" height="1"/>
               <%}%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
 <div id="maincontent">
        <table width="100%"  border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td valign="top" width="80px"><img src="images/help48.gif"/></td>
            <td align="left"><h2 class="cssHeader">使用帮助</h2>
            </td>
          </tr>
        </table>
        <hr size="1"/>
        <ul id="comment">
          <li class="title">什么是<fulong:config name="system.name"/>会员管理系统，其用途是...</li>
          <li class="text"><fulong:config name="system.name"/>会员管理系统是一种登录联机服务。利用该服务，您将可以通过您的用户名和密码登录到<fulong:config name="system.name"/>及其任何一个相关协作站点和应用系统。因此您无需记住每个网站的不同的登录名和密码，实现了统一数据管理、单点登录。</li>
        </ul>
        <ul id="comment">
          <li class="title">如何成为<fulong:config name="system.name"/>会员管理系统的会员，以获得统一的登录凭证？ </li>
          <li class="text">您可以通过如下两种方式注册<fulong:config name="system.name"/>会员管理系统的会员：</li>
          <li class="text">1. 通过在<fulong:config name="system.name"/>会员管理系统网站注册。 </li>
          <li class="text">2. 通过在<fulong:config name="system.name"/>会员管理系统 参与站点注册，该站点会将您自动重定向到 <fulong:config name="system.name"/>会员管理系统的用户注册页面。 </li>
          <li class="text">3. 注册时，所有带星号开始的信息属性为必须填写内容。 </li>
        </ul>
        <ul id="comment">
            <li class="title">如何使用<fulong:config name="system.name"/>会员管理系统，它为会员提供了哪些功能？</li>
            <li class="text">会员登录到<fulong:config name="system.name"/>会员管理系统后，有三个功能模块： </li>
            <li class="text">1.缺省进入“个人信息”，可以浏览、修改当前会员信息，以及查找其他会员用户信息。 </li>
            <li class="text">2.进入“我的机构”，可以注册、查找、分类浏览机构信息，即各种单位信息。机构是一种独立的小组，创建机构需要填写必要的单位信息，机构管理者可以查找添加会员到本机构中，则会员获得这个机构在各系统中的相应权限。一个会员可以注册、管理、属于多个机构。</li>
            <li class="text">3.注册机构后，在自助建站应用系统中可以为机构创建网站，网站自动的获得了机构的所有信息。否则只能创建会员个人网站，只自动获取会员个人信息。</li>
        </ul>
        <ul id="comment">
            <li class="title">现在有哪些系统在使用<fulong:config name="system.name"/>会员管理系统？</li>
        </ul>
      </div>
	  <table bgcolor="#ede9d7" width="100%" height="45" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <table align="center" width="250" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>
            <a href="/common/items.jsp">使用条款</a>
            |
            <a href="/common/link.jsp">联系方式</a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
