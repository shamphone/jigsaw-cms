<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
<head>
<style type="text/css">
	html, body, ul, ol, li, p, h1, h2, h3, h4, h5, h6, form, fieldset, table, td, img {
		margin:0;
		padding:0;
		border:0;
	}
	body, td, p, div, li, select, input, textarea {
		font-size:12px;
	}
	body {
		color:#000;
		line-height:20px;
	}
	
	.footer1{
		width: 206px;
	}
	.left1{
		width: 16px;
	}
	.right1{
		width: 14px;
	}
	.resolution1 {
		width: 170px;
		height:	220px; 		
	}
	
	.footer2{
		width: 281px;
	}
	.left2{
		width: 22px;
	}
	.right2{
		width: 19px;
	}
	.resolution2{
		width: 240;
		height:	320 		
	}
		
	.footer3{
		width: 560px;
	}
	.left3{
		width: 43px;
	}
	.right3{
		width: 37px;
	}
	.resolution3 {
		width: 480px;
		height:	640px; 		
	}
	
	.footer4{
		width: 161px;
	}
	.left4{
		width: 12px;
	}
	.right4{
		width: 11px;
	}
	.resolution4 {
		width: 128px;
		height:	128px; 		
	}
	
	.footer5{
		width: 161px;
	}
	.left5{
		width: 12px;
	}
	.right5{
		width: 11px;
	}
	.resolution5 {
		width: 128px;
		height:	160px; 		
	}
	
	.footer6{
		width: 212px;
	}
	.left6{
		width: 15px;
	}
	.right6{
		width: 14px;
	}
	.resolution6 {
		width: 176px;
		height:	208px; 		
	}
	
	.footer7{
		width: 246px;
	}
	.left7{
		width: 19px;
	}
	.right7{
		width: 16px;
	}
	.resolution7 {
		width: 208px;
		height:	320px; 		
	}
	
</style>
<script Language='Javascript' src='<html:rewrite page="/script/common.js" module="/common"/>' type="text/javascript"></script>
<script type="text/javascript" src="<html:rewrite page="/classes/smtemplate.js" module="/site"/>"></script>
<script type="text/javascript" language="javascript">
	function init(){
		<bean:parameter id="resolution" name="resolution" />
		var resolution = "<bean:write name='resolution'/>";
		resolution = new Resolution(resolution);
		adjustIframeSize(resolution);
	}

	function adjustIframeSize(resolution){
		var width = resolution.getWidth();
		var height = resolution.getHeight();
		var i = 1;
		if(width==176&&height==220){
			i = 1;
		}else if(width==240&&height==320){
			i = 2;
		}else if(width==480&&height==640){
			i = 3;
		}else if(width==128&&height==128){
			i = 4;
		}else if(width==128&&height==160){
			i = 5;
		}else if(width==176&&height==208){
			i = 6;
		}else if(width==208&&height==320){
			i = 7;
		}
		document.getElementById("mobile").style.display = "block";
		var header = document.getElementById("header");
		var footer = document.getElementById("footer");
		var left = document.getElementById("left");
		var right = document.getElementById("right");
		var middle = document.getElementById("middle");
		header.className = footer.className = "footer" + i;
		left.className = "left" + i;
		right.className = "right" + i;
		middle.className = "resolution" + i;
	}
</script>
</head>
<body onload="init()">
<div id="mobile" style="display: none;position: absolute;left: 38%;">
<div><img id="header" src="<html:rewrite page="/images/mobile_header.gif" module="/common"/>" class="footer2"/></div> 
<table cellspacing="0" cellpadding="0" bordercolor="red" > 
	<tr valign="top" > 
		<td class="left2" id="left"><img src="<html:rewrite page="/images/mobile_left.gif" module="/common"/>" width="100%" height="100%" /></td> 
		<td id="middle" class="resolution2">
			<bean:parameter id="url" name="url"/>
			<iframe width="100%" height="100%" id="oIframe" src="<bean:write name='url'/>" scrolling="Auto" frameborder="0">
			</iframe>
		</td>
		<td class="right2" id="right"><img src="<html:rewrite page="/images/mobile_right.gif" module="/common"/>" width="100%" height="100%" /></td> 
	</tr>
</table>
<div><img id="footer" src="<html:rewrite page="/images/mobile_footer.gif" module="/common"/>" class="footer2"/></div> 
</div>
</body>
</html>
