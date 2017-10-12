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
<title>会员管理系统--联系方式</title>
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
              欢迎您,
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

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="235" rowspan="2" valign="top" id="leftNav">&nbsp;    </td>
<td height="5" colspan="2"><table width="100%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="7" height="5" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_06.gif"/>" height="5" width="7" alt=""></td>
    <td align="center" id="contentTop" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="1" width="1" alt=""></td>
    <td width="7" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_10.gif"/>" height="5" width="9" alt=""></td>
  </tr>
</table></td>
</tr>
<tr>
  <td colspan="2" bgcolor="#FFFFFF" valign="top"><table width="100%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="7" align="center" id="contentLeft" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="1" width="1" alt=""></td>
      <td height="510" id="rightArea" align="left" valign="top">
        <div id="maincontent">
          <table border="0" cellspacing="0" cellpadding="3">
            <tr>
              <td valign="top"><img src="<html:rewrite module="/common" page="/images/count-service.gif"/>" alt=""></img></td>
              <td><h2 class="cssHeader"><fulong:config name="system.name"/>会员管理</h2>
                <span class="tips">&nbsp;</span>
              </td>
            </tr>
          </table>
        <ul id="comment">
          <li class="title">联系方式</li>
          <li class="text">通讯地址：北京西城区月坛南街26号院1号楼5014室</li>
          <li class="text">电话：010-68574511</li>
          <li class="text">传真：010-68579942</li>
          <li class="text">email:web@sme.gov.cn</li>
        </ul>
      </div>
      </td>
      <td width="7" align="center" id="contentRight" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="8" width="8"></td>
    </tr>
  </table>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="7" height="7" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_18.gif"/>/member/images/sme1_18.gif" height="7" width="7" alt=""></td>
      <td align="center" id="contentButtom" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="1" width="1"></td>
      <td width="7" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_22.gif"/>" height="7" width="8" alt=""></td>
    </tr>
  </table></td>
</tr>
</table>
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
