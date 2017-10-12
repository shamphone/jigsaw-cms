<%@page contentType="text/html; charset=GBK"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<html>
<head>
<meta http-equiv="refresh" content="10;GetAllUserInfo.do" />
<title>视频会议后台管理系统</title>

<script type="text/javascript">
function selectUser(string) {
	window.open("getDetail.do?"+string);
}
function checkPage(form,totalPageNum){
  if(form.targetPage.value > totalPageNum){
	alert('指定的页数不存在');
	form.targetPage.value = '';
	return false;
  }
  return true;
}
</script>
<link href="css/stylejq.css" rel="stylesheet" type="text/css" />

</head>

<body>
<form name="getAllUser" method="POST"
	action="<html:rewrite page="/GetAllUserInfo.do?act=goto"/>">
<table bgcolor="#FFFFFF" width="100%" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td valign="top" height="440px" width="100%">
		<table width="98%" border="1" align="center" cellpadding="2"
			cellspacing="0" bordercolorlight="#CCCCCC" bordercolordark="#FFFFFF">
			<tr>
				<th bgcolor="#d1e3fb" width="40%">姓 名</th>
				<th bgcolor="#d1e3fb">登录时间</th>
			</tr>
			<logic:iterate name="UserInfo" id="user" indexId="index">
				<tr onmouseover="this.bgColor='#dcf0f9'">
					<td align="center"><bean:write name="user" property="lastname" /><bean:write
						name="user" property="firstname" /></td>
					<td align="center"><bean:write name="user"
						property="entertime" /></td>
				</tr>
			</logic:iterate>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table cellpadding="1" cellspacing="0">
			<tfoot>
				<tr>
					<td align="right" colspan="10"><span style="font-size: 12px;">
					总共<bean:write name="totalNum" />条记录 当前为<bean:write name="curPage" />/<bean:write
						name="totalPage" />页 <logic:greaterThan name="curPage" value="1">
						，<a href="<html:rewrite page="/GetAllUserInfo.do"/>?act=first">首页</a>，
						<a
							href="<html:rewrite page="/GetAllUserInfo.do"/>?act=pre&curPage=<bean:write name="curPage"/>">上一页</a>
					</logic:greaterThan>
					<%if (Integer.parseInt((String) request.getAttribute("curPage")) < Integer.parseInt((String) request.getAttribute("totalPage"))) { %>
					，<a href="<html:rewrite page="/GetAllUserInfo.do"/>?act=next&curPage=<bean:write name="curPage"/>">下一页</a>
					，<a href="<html:rewrite page="/GetAllUserInfo.do"/>?act=last">末页</a>
					<%}%>
					跳转到 <input size="1" name="targetPage" /> <input type="submit"
						id="go" onclick="return checkPage(this.form,<bean:write name="totalPage"/>);"
						value="跳转" /> </span>
					</td>
				</tr>
			</tfoot>
		</table>
		</td>
	</tr>
</table>


</form>
</body>
</html>
