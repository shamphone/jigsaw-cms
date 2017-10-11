/*
 * ajax加载完页面后执行所加载页面中的js脚本
 */
function excuteJavaScript(ajaxLoadedData){
	// 第一步：匹配加载的页面中是否含有js
	var regDetectJs = /<script(.|\n)*?>(.|\n|\r\n)*?<\/script>/ig;
	var jsContained = ajaxLoadedData.match(regDetectJs); 
	// 第二步：如果包含js，则一段一段的取出js再加载执行
	if(jsContained) { 
		// 分段取出js正则 
		var regGetJS = /<script(.|\n)*?>((.|\n|\r\n)*)?<\/script>/im;  
		// 按顺序分段执行js 
		var jsNums = jsContained.length; 
		for (var i=0; i<jsNums; i++) {  
			var jsSection = jsContained[i].match(regGetJS);   
			if(jsSection[2]) {   
				if(window.execScript) {    
					// 给IE的特殊待遇    
					window.execScript(jsSection[2]);   
				} else {    
					// 给其他大部分浏览器用的    
					window.eval_r(jsSection[2]);   
				}  
			} 
		}
	}
}

function getAjaxClipIntoArea(clipURL,areaID){
	if(clipURL!=null&&clipURL!=""){
		var xmlhttp = getXMLHttpRequest();
		xmlhttp.open("Post",clipURL,false);
		xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
		xmlhttp.send(null);
		document.getElementById(areaID).innerHTML=xmlhttp.responseText;
		excuteJavaScript(document.getElementById(areaID).innerHTML);
	}else{
		document.getElementById(areaID).innerText="片段地址有误！";
	}
}