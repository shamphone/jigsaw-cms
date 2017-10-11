<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
document.write( '<script type="text/javascript" src="/ide/common/script/ajax.js"></script>' );
var nodeSelect = {
    multipleNodeSelect: function(category,oSelect,proName,left,searchText,orderfield,selectedProps,sort,length){
    if(selectedProps==null||selectedProps==''){
    selectedProps = 'title';
    }
    if(searchText==null||searchText==''){
    searchText = 'false';
    }
    if(left==null||left==''){
    left = 'false';
    }
    if(orderfield==null||orderfield==''){
    orderfield = 'title';
    }
    if(sort==null||sort==''){
    sort = 'asc';
    }
      var arr = CMSDialog.NodeSelector(category,true,left,searchText,orderfield,selectedProps,sort);
        if(arr!=null && arr.length>0){
          for(var i=0;i<arr.length;i++){
            if(!findSelectItem(oSelect,arr[i].id)){
                var newOption=document.createElement("option");
                newOption.value=arr[i].id;
                if(length!=null&&length!=''){
                newOption.text=arr[i][proName].substring(0,length)+"..." ;
                } else {
                newOption.text=arr[i][proName];
                }
                if(navigator.userAgent.indexOf("Firefox")>=0){oSelect.add(newOption,null);}else{oSelect.add(newOption);}
            }
          }
          selectAll(oSelect);
        }
    },
     singleNodeSelect: function(category,oName,oID,proName,left,searchText,orderfield,selectedProps,sort){
     if(selectedProps==null||selectedProps==''){
    selectedProps = 'title';
    }
    if(searchText==null||searchText==''){
    searchText = 'false';
    }
    if(left==null||left==''){
    left = 'false';
    }
    if(orderfield==null||orderfield==''){
    orderfield = 'title';
    }
    if(sort==null||sort==''){
    sort = 'asc';
    }
      var arr = CMSDialog.NodeSelector(category,false,left,searchText,orderfield,selectedProps,sort);
      if(arr!=null){
            if(oID!=null){
            	oID.value = arr.id;
            }
            if(oName!=null){
                oName.value = arr[proName];
            }
        }
    },
    selectNodeSelect: function(oCcategory,oSelect,property,id){
    	var url = "/ide/cms/contents.do?category="+oCcategory.value+"&property="+property;
    	populate_select(url,oSelect,true,id);
    },
    user: function(oSelect){
                var newOption=document.createElement("option");
                newOption.value="user";
                newOption.text="当前用户";
                oSelect.add(newOption);
                // 修正当前用户选不中的错误   modified by mali 2010-8-27 
                selectAll(oSelect);
    },
    addFixNode: function(parentID,category,oSelect,proName){
      var arr = CMSDialog.NodeSelector(category,true);
        if(arr!=null && arr.length>0){
          for(var i=0;i<arr.length;i++){
            if(!findSelectItem(oSelect,arr[i].id)){
            	var req = getXMLHttpRequest();
				var url='/ide/cms/addfixForPortlet.do?parentID='+parentID+'&fixProID='+proName+'&childID='+arr[i].id;
      			req.open("post",encodeURI(url),false);
      			req.send(null);
      			var data=req.responseText;
      			if(data!=null&&data=="true"){
       	  			var newOption=document.createElement("option");
                	newOption.value=arr[i].id;
                	newOption.text=arr[i][proName];
                	if(navigator.userAgent.indexOf("Firefox")>=0){oSelect.add(newOption,null);}else{oSelect.add(newOption);}
         		}                
            }
          }
        }
    },
    createFixNode: function(parentID,category,childDef,oSelect,proName){
      disableButton();
      var arr = CMSDialog.NodeCreator(parentID,category,childDef,proName);
        if(arr!=null ){
			var newOption=document.createElement("option");
                newOption.value=arr.id;
                newOption.text=arr.displayName;
                if(navigator.userAgent.indexOf("Firefox")>=0){oSelect.add(newOption,null);}else{oSelect.add(newOption);}         
        }
        enableButton();
    },
    modifyNode: function(oSelect,proID){
      disableButton();
      var oOptions = oSelect.options;
      var nodeID = "";
	  var cont = 0;
      for (var i=0; i<oOptions.length; i++) {
		if (oOptions[i].selected){
			nodeID = oOptions[i].value;
			cont = i;
			break;		
		}
    }
      var arr = CMSDialog.NodeModifier(nodeID,proID);
        if(arr!=null){
			oOptions[cont].value = arr.id;
			oOptions[cont].text = arr.displayName;  
        }
        enableButton();
   },
   upShiftNode: function(parentID,fixPropID,oSelect){
      var oOptions = oSelect.options;
      var nodeID = "";
	  var cont = 0;
      for (var i=1; i<oOptions.length; i++) {
		if (oOptions[i].selected){
			nodeID = oOptions[i].value;
			cont = i;
			break;		
		}
    }
    if (cont != 0) {
        disableButton();
    }
      var req = getXMLHttpRequest();
	  var url='/ide/cms/upCompositeNode.do?parentID='+parentID+'&fixPropID='+fixPropID+'&childID='+oOptions[cont].value+'&timeprama=' + Math.random();
      req.open("post",encodeURI(url),false);
      req.send(null);
      var data=req.responseText;
      if(data!=null&&data=="true"){
      		if(navigator.userAgent.indexOf("Firefox")>=0){
			   swapNode(oOptions[cont],oOptions[cont-1]);
			}else{
			     oOptions[cont].swapNode(oOptions[cont - 1]);
			}
       	  enableButton();
      }
   },
   downLowerNode: function(parentID,fixPropID,oSelect){
      var oOptions = oSelect.options;
      var nodeID = "";
	  var cont = 0;
      for (var i=1; i<oOptions.length; i++) {
		if (oOptions[i].selected){
			nodeID = oOptions[i].value;
			cont = i;
			break;		
		}
    }
        if (cont != oOptions.length-1) {
            disableButton();
        }
      var req = getXMLHttpRequest();
	  var url='/ide/cms/downCompositeNode.do?parentID='+parentID+'&fixPropID='+fixPropID+'&childID='+oOptions[cont].value+'&timeprama=' + Math.random();
      req.open("post",encodeURI(url),false);
      req.send(null);
      var data=req.responseText;
      if(data!=null&&data=="true"){
      		if(navigator.userAgent.indexOf("Firefox")>=0){
			   swapNode(oOptions[cont],oOptions[cont+1]);
			}else{
			     oOptions[cont].swapNode(oOptions[cont + 1]);
			}
       	  enableButton();
      }
   },
   delFixNode: function(oSelect){
   if(confirm("确认是否删除！")){
   //disableButton();
      var oOptions = oSelect.options;
      var nodeID = "";
	  var IDs = "";
      for (var i=0; i<oOptions.length; i++) {
		if (oOptions[i].selected){
			IDs = IDs + "&IDs=" + oOptions[i].value;	
		}
    }
    
      var req = getXMLHttpRequest();
	  var url='/ide/cms/delFixNodes.do?1=1'+IDs+'&timeprama=' + Math.random();
      req.open("post",encodeURI(url),false);
      req.send(null);
      var data=req.responseText;
      if(data!=null&&data=="true"){
		 for (var j=0; j<oOptions.length; ) {
			var oOption = oOptions[j];
			if (oOption.selected)
				oOption.parentNode.removeChild(oOption);
			else
				j++;
		}
		oSelect.fireEvent("onchange");
		enableButton();
      }
   }
   },
   
   singleCustomerSelect: function(oName,oID,oChannel,width,height,proName){
		if(oChannel!=null&&oChannel!=""){
		    	if(width==null||width==''){
		    	width = '670px';
		    	}
				if(height ==null || height==""){
				height = '570px';
				}
				var url = oChannel;
				var ds = "dialogWidth:"+width+"px;dialogHeight:"+height+"px;help:no;scrollbars:yes;status:yes;resizable:yes";
				var arr = showModalDialog(url,window,ds);
				if(arr!=null){
			            if(oID!=null && arr.id){
			            	oID.value = arr.id;
			            }
			            if(oName!=null && arr.title){
			                oName.value = arr.title;
			            }
		       	}
	    }
    },
multipleCustomerSelect: function(oSelect,length,oChannel,width,height){
	if(oChannel!=null&&oChannel!=""){
    	if(width==null||width==''){
    	width = '670px';
    	}
		if(height ==null || height==""){
		height = '570px';
		}
		var url = oChannel;
		var ds = "dialogWidth:"+width+"px;dialogHeight:"+height+"px;help:no;scrollbars:yes;status:yes;resizable:yes";
		var arr = showModalDialog(url,window,ds);
		if(arr!=null && arr.length>0){
          for(var i=0;i<arr.length;i++){
            if(!findSelectItem(oSelect,arr[i].id)){
                var newOption=document.createElement("option");
                newOption.value=arr[i].id;
                if(length!=null&&length!=''){
                newOption.text=arr[i].title.substring(0,length)+"..." ;
                } else {
                newOption.text=arr[i].title;
                }

                if(navigator.userAgent.indexOf("Firefox")>=0){oSelect.add(newOption,null);}else{oSelect.add(newOption);}
            }
          }
        }
        	selectAll(oSelect);
        	return arr;
	}
        
    }
      
}