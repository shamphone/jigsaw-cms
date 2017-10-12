<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/ide/common/style/toolbar.jsp" />
<title>系统管理</title>
<script language="javascript">
	window.onload = function(){
		if(!document.all){
			var spans = document.getElementsByClassName("DLGButton");
			for(var i=0;i<spans.length;i++){
				if(navigator.userAgent.indexOf("Firefox")>=0){
					spans[i].onmousemove = function(){
						this.className="DLGButton DLGButton_On_Over";
					};
					spans[i].onmouseout = function(){
						this.className="DLGButton";
					};
				}
			}	
		}
	}
</script>
</head>
<body bgcolor="buttonface">
<div id="DLGToolbar20">
<span class="DLGButton" onclick="top.location='../system/index.jsp'"><img alt="" src="toolbar/images/sysmgn.png" border="0"/><span>网站服务器管理</span></span>
<span class="DLGButton" onclick="top.location='../dbmanage/index.jsp'"><img alt="" src="toolbar/images/dbserver.png" border="0"/><span>数据库管理</span></span>
<span class="DLGButton" onclick="top.location='../awstats/index.jsp'"><img alt="" src="toolbar/images/log.png" border="0"/><span>日志分析</span></span>
<!-- span class="seperator"></span> 
<span class="DLGButton" onclick="top.location='../cms/index.jsp'"><img alt="" src="toolbar/images/cms.png" border="0"/><span>数据模型管理</span></span>
<span class="DLGButton" onclick="top.location='../site/'"><img alt="" src="toolbar/images/site.png" border="0"/><span>网站设计</span></span>
<span class="DLGButton" onclick="top.location='../security/index.jsp'"><img alt="" src="toolbar/images/ums.png" border="0"/><span>组织模型管理</span></span>
<span class="DLGButton" onclick="top.location='../process/'"><img alt="" src="toolbar/images/workflow.png" border="0"/><span>流程设计</span></span>
<span class="DLGButton"onclick="top.location='../service/'"><img alt="" src="toolbar/images/service.png" border="0"/><span>服务管理</span></span-->
</div>
</body>
</html>