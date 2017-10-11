<%@ page contentType="text/html; charset=UTF-8" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script>
try{
	var time<%=(String)request.getAttribute("javax.portlet.id")%> = '<bean:write name="preferences" property="value(time)" ignore="true"/>';
	var marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>=new Array();   //滚动新闻
	var theTable<%=(String)request.getAttribute("javax.portlet.id")%> = document.getElementById('<bean:write name="preferences" property="value(propertyId)" />');
	if(theTable<%=(String)request.getAttribute("javax.portlet.id")%>!=null){
		for(i=0;i<theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows.length;i++){
			var oTR<%=(String)request.getAttribute("javax.portlet.id")%> = theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i];
			marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[i] = "";
			for(j=0;j<oTR<%=(String)request.getAttribute("javax.portlet.id")%>.cells.length;j++){				
				marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[i] = marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[i] + theTable<%=(String)request.getAttribute("javax.portlet.id")%>.rows[i].cells[j].innerHTML ;
			}
			marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[i] = marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[i] + '<br>';
		}
	}
	var marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>=new Array();  //定义一些常用而且要经常用到的变量
	var marqueeId<%=(String)request.getAttribute("javax.portlet.id")%>=0;
	var marqueeDelay<%=(String)request.getAttribute("javax.portlet.id")%>=time<%=(String)request.getAttribute("javax.portlet.id")%> * 1000;
	var marqueeHeight<%=(String)request.getAttribute("javax.portlet.id")%>='<bean:write name="preferences" property="value(height)" ignore="true"/>';
	var marqueeWidth<%=(String)request.getAttribute("javax.portlet.id")%>='<bean:write name="preferences" property="value(width)" ignore="true"/>';
	//接下来的是定义一些要使用到的函数
	function initMarquee<%=(String)request.getAttribute("javax.portlet.id")%>() {
	    var str=marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[0];
	    document.write('<div id='+'marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>'+' style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;height:'+marqueeHeight<%=(String)request.getAttribute("javax.portlet.id")%>+'px;width:'+marqueeWidth<%=(String)request.getAttribute("javax.portlet.id")%>+'px;" onmouseover="clearInterval('+'marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>[0]'+')" onmouseout="'+'marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>[0]'+'=setInterval(\'startMarquee<%=(String)request.getAttribute("javax.portlet.id")%>()\','+'marqueeDelay<%=(String)request.getAttribute("javax.portlet.id")%>)'+'"><div>'+str+'</div></div>');
	    marqueeId<%=(String)request.getAttribute("javax.portlet.id")%>++;
	    marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>[0]=setInterval('startMarquee<%=(String)request.getAttribute("javax.portlet.id")%>()',marqueeDelay<%=(String)request.getAttribute("javax.portlet.id")%>);
	    }
	function startMarquee<%=(String)request.getAttribute("javax.portlet.id")%>() {
	    var str=marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>[marqueeId<%=(String)request.getAttribute("javax.portlet.id")%>];
	        marqueeId<%=(String)request.getAttribute("javax.portlet.id")%>++;
	    if(marqueeId<%=(String)request.getAttribute("javax.portlet.id")%>>=marqueeContent<%=(String)request.getAttribute("javax.portlet.id")%>.length) marqueeId<%=(String)request.getAttribute("javax.portlet.id")%>=0;
	    if(marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.childNodes.length==1) {
	        var nextLine=document.createElement('DIV');
	        nextLine.innerHTML=str;
	        marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.appendChild(nextLine);
	        }
	    else {
	        marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.childNodes[0].innerHTML=str;
	        marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.appendChild(marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.childNodes[0]);
	        marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.scrollTop=0;
	        }
	    clearInterval(marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>[1]);
	    marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>[1]=setInterval('scrollMarquee<%=(String)request.getAttribute("javax.portlet.id")%>()',20);
	    }
	function scrollMarquee<%=(String)request.getAttribute("javax.portlet.id")%>() {
	    marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.scrollTop++;
	    if(marqueeBox<%=(String)request.getAttribute("javax.portlet.id")%>.scrollTop%marqueeHeight<%=(String)request.getAttribute("javax.portlet.id")%>==(marqueeHeight<%=(String)request.getAttribute("javax.portlet.id")%>-1)){
	        clearInterval(marqueeInterval<%=(String)request.getAttribute("javax.portlet.id")%>[1]);
	        }
	    }
	initMarquee<%=(String)request.getAttribute("javax.portlet.id")%>();
}catch(e){
	alert("系统出错（单行向上滚动占位符有误）！");
}

</script>
