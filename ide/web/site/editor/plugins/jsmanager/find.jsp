<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查找</title>
		<style type="text/css">
			body {padding:10px; }
			body * {font-size:12px;}
		</style>
		<script src='<html:rewrite module="/common" page="/script/common.js"/>' type="text/javascript"></script>
		<script src='<html:rewrite module="/common" page="/script/ajax.js"/>' type="text/javascript"></script>
		<script src='find.js' type="text/javascript"></script>
	</head>
	<body bgcolor="#EFEFDE">
		<table cellpadding="0" cellspacing="0" height="100%" width="100%">
			<tr>
				<td nowrap="nowrap">
					查找：<input id="findText" size="30" />&nbsp;
					<button onclick="_Find(1000000000)">下一个</button>
					<button onclick="_Find(-1000000000)">上一个</button>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap">
					替换：<input id="replaceText" size="30" />&nbsp;
					<button id="replaceButton" onclick="_Replace()" disabled="true">替　换</button>
					<button onclick="_ReplaceAll()">全替换</button>
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" id="wholeWord"><label for="wholeWord">全字匹配</label>
					<input type="checkbox" id="caseSensitive"><label for="caseSensitive">区分大小写</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<button onclick="window.close();">取消</button>
				</td>
			</tr>
		</table>
	</body>
</html>