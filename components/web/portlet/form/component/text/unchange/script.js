function suggestText(oObject){
	if(oObject.value!=null&&oObject.value!=""){
		var req = getXMLHttpRequest();
		var url='/ide/cms/getSuggestWord.do';
	      req.open("get",encodeURI(url),false);
	      req.send(null);
	      var data=req.responseText;
	      if(data!=null){
	       	  var divArea = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Area');
	       	  divArea.style.display="block";
	       	  divArea.style.width = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').size*7.75;
	       	  divArea.insertAdjacentHTML("afterBegin",data);
	      }
	}
}

function getFocus(oTR){
	 oTR.style.backgroundColor='#f0f0f0';
}
function loseFocus(oTR){
	 oTR.style.backgroundColor='#ffffff';
}