<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<script language="javascript" src="/components/portlet/form/component/text/suggest/script.js" type="text/javascript" ></script>
<%java.util.List<String> list = (java.util.List<String>)request.getAttribute("values"); %>
<logic:iterate id="property" name="properties" indexId="index">
<logic:notEmpty name="property">
<input type="hidden" name="<bean:write name="property"/>" id='<%=(String)request.getAttribute("javax.portlet.id")+index.intValue()%>Hidden' value="<%= list.get(index.intValue()) %>"/>
</logic:notEmpty>
</logic:iterate>
<input  id='<%=(String)request.getAttribute("javax.portlet.id")%>Text' type="text" autocomplete="off"  <logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty> 
<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>  
<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty> 
<logic:notEmpty name="preferences" property="value(maxLength)">maxLength="<bean:write name="preferences" property="value(maxLength)"/>"</logic:notEmpty> 
<logic:notEmpty name="displayName">value="<bean:write name="displayName"/>"</logic:notEmpty> 
  onkeydown="if(event.keyCode==13){event.keyCode=0;event.returnValue=false;}" onkeyup='suggestText<%=(String)request.getAttribute("javax.portlet.id")%>(this)' onblur='Validator.ValidateComponent(this, this.name);loseInputFocus<%=(String)request.getAttribute("javax.portlet.id")%>(this)'/>
 <div id='<%=(String)request.getAttribute("javax.portlet.id")%>Area' style="display:none;"></div>
<logic:present name="preferences" property="value(hasButton)"><logic:notEqual value="false" name="preferences" property="value(hasButton)"><button id='<%=(String)request.getAttribute("javax.portlet.id")%>button' <logic:notEmpty name="preferences" property="value(buttonStyle)">class="<bean:write name="preferences" property="value(buttonStyle)"/>"</logic:notEmpty> onclick="selectText('<%=(String)request.getAttribute("javax.portlet.id")%>')">选择</button></logic:notEqual></logic:present>
<script type="text/javascript" language="javascript">
var <%=(String)request.getAttribute("javax.portlet.id")%>oTR = null;
function suggestText<%=(String)request.getAttribute("javax.portlet.id")%>(oObject){
	var divArea = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Area');	
	if(oObject.value!=null&&oObject.value!=""){
		if(event.keyCode == 38 || event.keyCode == 40){			
			if(divArea.style.display=="block"){
				setTRFocus<%=(String)request.getAttribute("javax.portlet.id")%>(); 
			}
		}else if(event.keyCode == 13){
			if(<%=(String)request.getAttribute("javax.portlet.id")%>oTR!=null){
				<logic:iterate id="property" name="properties" indexId="index2">
				document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")+index2.intValue()+"Hidden"%>').value = "";
				</logic:iterate>
				var finalValue = <%=(String)request.getAttribute("javax.portlet.id")%>oTR.cells[0].innerText;
				var finalValues = finalValue.split(" ");
				if(finalValues!=null){
					for(var u=0;u<finalValues.length;u++){
						document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>'+u+'Hidden').value = finalValues[u];
					}
				}
				var displayName = "";
				<logic:iterate id="property" name="properties" indexId="index3">
				displayName = displayName+document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")+index3.intValue()+"Hidden"%>').value + " ";
				</logic:iterate>
				document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = displayName.trim();
				divArea.style.display="none";
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR = null;
			}
		}else{
			var tempValue = oObject.value.trim();
			var tempValues = tempValue.split(" ");
			if(tempValues!=null&&tempValues.length>0){
				tempValue = tempValues[tempValues.length-1];
			}			
			var req = getXMLHttpRequest();
			var url='/ide/cms/getSuggestWord.do?portletID='+'<%=(String)request.getAttribute("javax.portlet.id")%>'+'&categoryID='+'<bean:write name="preferences" property="value(category)" ignore="true"/>'+'&keyword='+tempValue+'&suggestPropID='+'<bean:write name="preferences" property="value(suggestPropId)" ignore="true"/>'+'&lucene='+'<bean:write name="preferences" property="value(lucene)"/>' + '&timestamp=' + Math.random();
		      req.open("get",encodeURI(url),false);
		      req.send(null);
		      var data=req.responseText;	   
		      if(data!=null&&data!="faild"){	
		    	  divArea.style.position="absolute";
		    	  divArea.style.backgroundColor="#ffffff"
			      var w = 0;
			      var h = 0;
			      var m = oObject;
			      while(m.offsetParent)
					{
						w += m.offsetLeft;
						h += m.offsetTop;
						m = m.offsetParent;
					}			      
		    	  divArea.style.top=h+oObject.offsetHeight;
		    	  divArea.style.left=w;
		       	  divArea.style.width = oObject.offsetWidth;
		       	  divArea.innerHTML = data;
		       	  divArea.style.display="block";
		      }else{
		    	  divArea.style.display="none";
		  		  <%=(String)request.getAttribute("javax.portlet.id")%>oTR = null; 
		      }
		}		
	}else{		
		divArea.style.display="none";
		<%=(String)request.getAttribute("javax.portlet.id")%>oTR = null;
	}
}
function loseInputFocus<%=(String)request.getAttribute("javax.portlet.id")%>(oObject){
	if(<%=(String)request.getAttribute("javax.portlet.id")%>oTR!=null){
		<logic:iterate id="property" name="properties" indexId="index1">
		document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")+index1.intValue()+"Hidden"%>').value = "";
		</logic:iterate>
		var finalValue = <%=(String)request.getAttribute("javax.portlet.id")%>oTR.cells[0].innerText;
		var finalValues = finalValue.split(" ");
		if(finalValues!=null){
			for(var u=0;u<finalValues.length;u++){
				var oHiddenInput = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>'+u+'Hidden');
				if(oHiddenInput!=null){ //兼容只绑定了一个属性的情况
					oHiddenInput.value = finalValues[u];
				}				
			}
		}	
	}
	if(document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value==""){
		<logic:iterate id="property" name="properties" indexId="index2">
		document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")+index2.intValue()+"Hidden"%>').value = "";
		</logic:iterate>
	}
	var displayName = "";
	<logic:iterate id="property" name="properties" indexId="index1">
	displayName = displayName+document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")+index1.intValue()+"Hidden"%>').value + " ";
	</logic:iterate>
	document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = displayName.trim();
	var divArea = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Area');
	if(divArea!=null){
		<%=(String)request.getAttribute("javax.portlet.id")%>oTR = null;
		divArea.style.display="none";
	}
}
function getFocus<%=(String)request.getAttribute("javax.portlet.id")%>(oTR){
	<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTR;
	 oTR.style.backgroundColor='#f0f0f0';
}
function loseFocus<%=(String)request.getAttribute("javax.portlet.id")%>(oTR){
	 oTR.style.backgroundColor='#ffffff';
	 <%=(String)request.getAttribute("javax.portlet.id")%>oTR = null;
}
function selectTR<%=(String)request.getAttribute("javax.portlet.id")%>(oTR){
	if(oTR!=null){
		document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = oTR.cells[0].innerText;	
		var divArea = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Area');
		if(divArea!=null){
			divArea.style.display="none";
		}
	}
}
function setTRFocus<%=(String)request.getAttribute("javax.portlet.id")%>(){	
	var oTable = document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Table');
	if(oTable!=null&&oTable.rows.length!=0){
		if (event.keyCode == 38){
			if(<%=(String)request.getAttribute("javax.portlet.id")%>oTR!=null){
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#ffffff';				
				if(<%=(String)request.getAttribute("javax.portlet.id")%>oTR.rowIndex==0){
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTable.rows[oTable.rows.length-1];
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#f0f0f0';
				}else{
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTable.rows[<%=(String)request.getAttribute("javax.portlet.id")%>oTR.rowIndex-1];
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#f0f0f0';
				}				
			}else{
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTable.rows[oTable.rows.length-1];
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#f0f0f0';
			}
		}else if(event.keyCode == 40){
			if(<%=(String)request.getAttribute("javax.portlet.id")%>oTR!=null){
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#ffffff';
				if(<%=(String)request.getAttribute("javax.portlet.id")%>oTR.rowIndex==(oTable.rows.length-1)){
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTable.rows[0];
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#f0f0f0';
				}else{
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTable.rows[<%=(String)request.getAttribute("javax.portlet.id")%>oTR.rowIndex+1];
					<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#f0f0f0';
				}				
			}else{
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR = oTable.rows[0];
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR.style.backgroundColor='#f0f0f0';
			}
		}
	}
	
}

function selectText(portletID){
	var categoryID = '<bean:write name="preferences" property="value(category)" ignore="true"/>';
	var propID = '<bean:write name="preferences" property="value(suggestPropId)"/>';
	var arr = CMSDialog.DictSelector(categoryID,true,propID);
    if(arr!=null){
    	var finalValues = arr.ID.split(" ");
		if(finalValues!=null){
			for(var u=0;u<finalValues.length;u++){
				document.getElementById(portletID+u+'Hidden').value = finalValues[u];
			}
		}	
		document.getElementById(portletID+'Text').value = arr.ID.trim();
    }
}
</script>