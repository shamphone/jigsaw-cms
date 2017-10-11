<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<bean:define id="preferences" name="preferences" type="javax.portlet.PortletPreferences"/>
<%
String titleHeight = preferences.getValue("title_height", "");
String height = preferences.getValue("height", "300");
String width = preferences.getValue("width", "300");
String time = preferences.getValue("time", "6000");
%>
<script type="text/javascript">
try{
var theTable<%=(String)request.getAttribute("javax.portlet.id")%> = document.getElementById('<bean:write name="preferences" property="value(propertyId)" />');
if(theTable<%=(String)request.getAttribute("javax.portlet.id")%>!=null){
	var imgUrl<%=(String)request.getAttribute("javax.portlet.id")%>=new Array(theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length);
	var imgLink<%=(String)request.getAttribute("javax.portlet.id")%>=new Array(theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length);
	var imgText<%=(String)request.getAttribute("javax.portlet.id")%>=new Array(theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length);
	for(var i=0;i<theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length;i++){
		var images = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[2].childNodes;
		var imagesfc = null;
		var img<%=(String)request.getAttribute("javax.portlet.id")%> = null;
		for(var j = 0;j<images.length;j++){
			if(images[j].nodeType == 1){
				imagesfc = images[j];
				var imagesfcs = imagesfc.childNodes;
				for(var m=0;m<imagesfcs.length;m++){
					if(imagesfcs[m].nodeType==1){
						var liages = imagesfcs[m].childNodes;
						for(var n=0;n<liages.length;n++){
							if(liages[n].nodeType==1){
								img<%=(String)request.getAttribute("javax.portlet.id")%> = liages[n];
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
		var path<%=(String)request.getAttribute("javax.portlet.id")%> = img<%=(String)request.getAttribute("javax.portlet.id")%>.src;
		imgUrl<%=(String)request.getAttribute("javax.portlet.id")%>[i] = path<%=(String)request.getAttribute("javax.portlet.id")%>;
		if(navigator.userAgent.indexOf("Firefox")>=0){
			imgLink<%=(String)request.getAttribute("javax.portlet.id")%>[i] = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[1].textContent;
			imgText<%=(String)request.getAttribute("javax.portlet.id")%>[i] = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[0].textContent;
		}else{
			imgLink<%=(String)request.getAttribute("javax.portlet.id")%>[i] = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[1].innerText;
			imgText<%=(String)request.getAttribute("javax.portlet.id")%>[i] = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[0].innerText;
		}
		imgText<%=(String)request.getAttribute("javax.portlet.id")%>[i] = imgText<%=(String)request.getAttribute("javax.portlet.id")%>[i].replace(/"/g,   "'");
	}

	var tt;
	
	var widths=<%=width%>; //设置幻灯片宽度

	var heights=<%=height%>; //设置幻灯片高度

	var title_Height = <%=titleHeight%>; //设置幻灯片标题高度

	var counts=imgLink<%=(String)request.getAttribute("javax.portlet.id")%>.length; //设置幻灯片数量

	var time = <%=time%>; //设置图片的变化时间

	var nn=1;

	var key=0;

	function change_img()

	{if(key==0){key=1;}

	else if(document.all)

	{document.getElementById("pic").filters[0].Apply();document.getElementById("pic").filters[0].Play(duration=2);}

	eval('document.getElementById("pic").src=imgUrl<%=(String)request.getAttribute("javax.portlet.id")%>[nn-1];');

	eval('document.getElementById("url").href=imgLink<%=(String)request.getAttribute("javax.portlet.id")%>[nn-1];');

	eval('document.getElementById("te").href=imgLink<%=(String)request.getAttribute("javax.portlet.id")%>[nn-1];');

	eval('document.getElementById("te").innerText=imgText<%=(String)request.getAttribute("javax.portlet.id")%>[nn-1];');

	for (var i=1;i<=counts;i++){document.getElementById("xxjdjj"+i).className='axx';}

	document.getElementById("xxjdjj"+nn).className='bxx';

	nn++;if(nn>counts){nn=1;}

	//设置图片切换间隔时间
	tt=setTimeout('change_img()',6000)};

	function changeimg(n){nn=n;window.clearInterval(tt);change_img();}

	document.write('<style>');

	document.write('.axx{padding:1px 10px;border-left:#cccccc 1px solid;}');

	document.write('a.axx:link,a.axx:visited{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#333;}');

	document.write('a.axx:active,a.axx:hover{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#666;}');

	document.write('.bxx{padding:1px 7px;border-left:#cccccc 1px solid;}');

	document.write('a.bxx:link,a.bxx:visited{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#D34600;}');

	document.write('a.bxx:active,a.bxx:hover{text-decoration:none;color:#fff;line-height:12px;font:9px sans-serif;background-color:#D34600;}');

	document.write('</style>');

	document.write('<div style="width:'+widths+'px;height:'+heights+'px;overflow:hidden;text-overflow:clip;">');

	document.write('<div><a id="url"><img id="pic" style="border:0px;filter:progid:dximagetransform.microsoft.wipe(gradientsize=1.0,wipestyle=4, motion=forward)" width='+widths+' height='+heights+' /></a></div>');
	
	document.write('<div style="filter:alpha(style=1,opacity=10,finishOpacity=80);background: #888888;width:100%-2px;text-align:right;top:-12px;position:relative;margin:1px;height:12px;padding:0px;margin:0px;border:0px;">');

	for(var i=1;i<counts+1;i++){document.write('<a href="javascript:changeimg('+i+');" id="xxjdjj'+i+'" class="axx" target="_self">'+i+'</a>');}

	document.write('</div></div>');

	document.write("<div style='line-height:"+title_Height+"px"+";height:"+ title_Height+"px" +";width:"+widths+"px;valign:center;' class=\"<bean:write name="preferences" property="value(style)"/>\"><center><a id='te' style='font-weight: bold'></a></center></div>");

	change_img();

		
}else{
	//alert("表格重复器装配有误，请修改！");
}
}catch(e){
	//alert("表格重复器装配有误，请修改！");
}
</script>

