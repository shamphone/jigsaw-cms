<%@ page contentType="text/javascript; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
function shiftNode(channelName,nodeID,processID,activityID){
	if(nodeID!=null&&nodeID!=""){
		var req = getXMLHttpRequest();
		var url='/ide/cms/content/sortNode.do?shift=true&contentId='+nodeID +"&processID="+processID +"&activityID="+activityID + '&timeprama=' + Math.random();
	      req.open("get",encodeURI(url),false);
	      req.send(null);
	      var data=req.responseText;
	      if(data!=null&&data=="true"){
	      //用来判断是否跳转会原页面
	      	if('<bean:write name="preferences" property="value(forwardSelf)"/>' == "true"){
	      		window.location.reload();
	      	}else{
	      	  if(channelName!=null&&channelName!=""){
	    		  document.location = channelName;
	    	  }else{
	    		  document.location.reload(); 
	    	  }	  
	      	}     	  
	      }
	}else{
		alert("内容不存在！");
	}
}
function downNode(channelName,nodeID,processID,activityID){
	if(nodeID!=null&&nodeID!=""){
		var req = getXMLHttpRequest();
		var url='/ide/cms/content/sortNode.do?down=true&contentId='+nodeID+"&processID="+processID +"&activityID="+activityID + '&timeprama=' + Math.random();
	      req.open("get",encodeURI(url),false);
	      req.send(null);
	      var data=req.responseText;
	      if(data!=null&&data=="true"){
	      if('<bean:write name="preferences" property="value(forwardSelf)"/>' == "true"){
	      		window.location.reload();
	      }else{
	      		  if(channelName!=null&&channelName!=""){
	    		  		document.location = channelName;
		    	  }else{
		    		 	document.location.reload(); 
		    	  }	 
	      		}       	  
	      }
	}else{
		alert("内容不存在！");
	}
}
function sortNode(channelName,nodeID,processID,activityID){
	if(nodeID!=null&&nodeID!=""){
		var oInput = document.getElementById(nodeID+"Input");
		if(oInput!=null){
			var oNum = oInput.value;
			//if((oNum==null)||(oNum.length==0)||isNaN(parseInt(oNum))||(parseInt(oNum)<1))){
		    	//alert('请输入大于1的数字');
		   // }else {
		    	var req = getXMLHttpRequest();
				var url='/ide/cms/content/sortNode.do?contentId='+nodeID+'&orderNum='+oNum +"&processID="+processID +"&activityID="+activityID + '&timeprama=' + Math.random();
			      req.open("get",encodeURI(url),false);
			      req.send(null);
			      var data=req.responseText;
			      if(data!=null&&data=="true"){
			     	 if('<bean:write name="preferences" property="value(forwardSelf)"/>' == "true"){
	      					window.location.reload();
	      			 }else{
	      			 	  if(channelName!=null&&channelName!=""){
			    		  	document.location = channelName;
				    	  }else{
				    		  document.location.reload(); 
				    	  }		
	      			 }     	  
			      }
		    //}
		}		
	}else{
		alert("内容不存在！");
	}
}