<%@page contentType="text/html; charset=UTF-8"%><%@taglib
	uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib
	uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib
	uri="/WEB-INF/struts-html.tld" prefix="html"%>
<script language="javascript"
	src="/components/portlet/form/component/text/suggest/script.js"
	type="text/javascript"></script>
<input id='<%=(String)request.getAttribute("javax.portlet.id")%>Text'
	type="text" autocomplete="off"
	<logic:notEmpty name="preferences" property="value(style)">class="<bean:write name="preferences" property="value(style)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(tabindex)">tabindex="<bean:write name="preferences" property="value(tabindex)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(size)">size="<bean:write name="preferences" property="value(size)"/>"</logic:notEmpty>
	<logic:notEmpty name="preferences" property="value(maxLength)">maxLength="<bean:write name="preferences" property="value(maxLength)"/>"</logic:notEmpty>
	<logic:notEmpty name="displayName">value="<bean:write name="displayName" ignore="true"/>"</logic:notEmpty>
	name='<bean:write name="preferences" property="value(propertyId)" ignore="true"/>'
	onkeydown="if(event.keyCode==13){event.keyCode=0;event.returnValue=false;}"
	onkeyup='suggestText<%=(String)request.getAttribute("javax.portlet.id")%>(this)'
	onblur='Validator.ValidateComponent(this, this.name);loseInputFocus<%=(String)request.getAttribute("javax.portlet.id")%>(this)' />
<div id='<%=(String)request.getAttribute("javax.portlet.id")%>Area'
	style="display: none;"></div>
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
				if(document.all){
					oObject.value = <%=(String)request.getAttribute("javax.portlet.id")%>oTR.cells[0].innerText;
				}else{
					oObject.value = <%=(String)request.getAttribute("javax.portlet.id")%>oTR.cells[0].textContent.trim();
					return;
				}
				<%=(String)request.getAttribute("javax.portlet.id")%>oTR = null;				
				divArea.style.display="none";
			}
		}else{
			var req = getXMLHttpRequest();
			var url='/ide/cms/getSuggestWord.do?portletID='+'<%=(String)request.getAttribute("javax.portlet.id")%>'+'&categoryID='+'<bean:write name="preferences" property="value(category)" ignore="true"/>'+'&keyword='+oObject.value.trim()+'&suggestPropID='+'<bean:write name="preferences" property="value(suggestPropId)" ignore="true"/>'+'&lucene='+'<bean:write name="preferences" property="value(lucene)"/>';
			<logic:iterate id="condition" name="conditions">
		      url = url + '&conditions=' + '<bean:write name="condition" ignore="true"/>';
		      </logic:iterate>
		      req.open("post",encodeURI(url),false);		      
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
		if(navigator.userAgent.indexOf("Firefox")>=0){
			document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = <%=(String)request.getAttribute("javax.portlet.id")%>oTR.cells[0].textContent.trim();
		}else{
			document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = <%=(String)request.getAttribute("javax.portlet.id")%>oTR.cells[0].innerText;
		}
	}
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
		if(navigator.userAgent.indexOf("Firefox")>=0){
			document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = oTR.cells[0].textContent.trim();
		}else{
			document.getElementById('<%=(String)request.getAttribute("javax.portlet.id")%>Text').value = oTR.cells[0].innerText;
		}	
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
</script>