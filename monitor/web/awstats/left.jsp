<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang='cn'>
<head>
<meta name="robots" content="noindex,nofollow">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>统计网站 ${pageContext.request.serverName} (2009-09) - left</title>
<link rel="stylesheet" type="text/css" href="/ide/common/style/left.css" />
<link rel="stylesheet" type="text/css" href="/ide/common/xtree/xtree.css" />
<script language="Javascript" type="text/Javascript" src="/ide/common/xtree/xtree.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/xtree/tree.js.jsp"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/script/ajax.js"></script>
<script language="Javascript" type="text/Javascript" src="/ide/common/module/modules.js.jsp"></script>
<script type="text/javascript">
function doInit(){
	if(!document.all){
		document.getElementById("navcontainer").style.height = document.body.clientHeight-55;
	}
	var oUL = document.getElementById("tabnav");
	var tree = WebFXTree_ConvertUL(oUL);
	tree.text = "统计网站${pageContext.request.serverName}";
	document.getElementById("navcontainer").innerHTML=tree.toString();
	CModuleCollection.render(document.getElementById("modules"),"log");
}
</script>
<base target="mainright" />
</head>
<body onload="doInit()">
<div id="blockTitle">访问量分析</div>
<div id="navcontainer">
<ul id="tabnav">
<li><a href="../cgi-bin/awstats.pl?framename=mainright#top" target="mainright">摘要</a></li>
<li>按参观时间<ul>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#month" target="mainright">按月历史统计</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#daysofmonth" target="mainright">按日期统计</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#daysofweek" target="mainright">按星期</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#hours" target="mainright">每小时浏览次数</a></li></ul></li>
<li>按参观者<ul>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=alldomains" target="mainright">国家或地区</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=allhosts" target="mainright">主机</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=lasthosts" target="mainright">最近参观日期</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=unknownip" target="mainright">无法反解译的IP地址</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=allrobots" target="mainright">搜索引擎网站的机器人</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=lastrobots" target="mainright">最近参观日期</a></li></ul></li>
<li>浏览器统计<ul>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#sessions" target="mainright">每次参观所花时间</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#filetypes" target="mainright">文件类别</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=urldetail" target="mainright">存取次数</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=urlentry" target="mainright">入站处</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=urlexit" target="mainright">出站处</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#os" target="mainright">操作系统</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=osdetail" target="mainright">版本</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=unknownos" target="mainright">无法得知</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#browsers" target="mainright">浏览器</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=browserdetail" target="mainright">版本</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=unknownbrowser" target="mainright">无法得知</a></li></ul></li>
<li>反相链接<ul>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#referer" target="mainright">来源网址</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=refererse" target="mainright">由那些搜索引擎转介</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=refererpages" target="mainright">由那些其他网站转介</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#keys" target="mainright">搜索</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=keyphrases" target="mainright">用以搜索的短语</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=keywords" target="mainright">用以搜索的关键词</a></li></ul></li>
<li>其他<ul>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#misc" target="mainright">其他</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright#errors" target="mainright">HTTP 错误码</a></li>
<li><a href="../cgi-bin/awstats.pl?framename=mainright&amp;output=errors404" target="mainright">找不到的网页</a></li></ul></li></ul></div>
<div id="modules" align="right"></div>
</body>
</html>
