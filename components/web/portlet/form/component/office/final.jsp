<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="pt"%>
<div style="display: none;width:<bean:write name="preferences" property="value(width)"/>;" id="error_tip_<%=request.getAttribute("javax.portlet.id")%>">您的机器未安装activex控件或office软件</div>
<object classid="clsid:00460182-9E5E-11d5-B7C8-B8269041DD57" id="oframe<%=request.getAttribute("javax.portlet.id")%>" 
<logic:notEmpty name="preferences" property="value(width)">
	width="<bean:write name="preferences" property="value(width)"/>" 
</logic:notEmpty>
<logic:notEmpty name="preferences" property="value(height)">
	height="<bean:write name="preferences" property="value(height)"/>"
</logic:notEmpty>
<logic:notEmpty name="preferences" property="value(style)">
	style="<bean:write name="preferences" property="value(style)"/>"
</logic:notEmpty>
 	CodeBase="/components/portlet/form/component/office/dsoframer.cab#version=2,3,0,1" viewastext>
		 <param NAME="Titlebar" VALUE="0">
		 <logic:empty name="preferences" property="value(Toolbars)">
		 	 <param NAME="Toolbars" VALUE="0">
		 </logic:empty>
         <logic:notEmpty name="preferences" property="value(Menubar)">
         	 <param NAME="Menubar" VALUE="1">
         </logic:notEmpty>
 		 <param NAME="_ExtentX" VALUE="19659">   
         <param NAME="_ExtentY" VALUE="3387">
         <param name="BorderStyle" value="0">
         <param name="TitlebarColor" value="52479">
         <param name="TitlebarTextColor" value="0">  
</object>
<input value="<bean:write name="path" ignore="true"/>" id="input_<%=request.getAttribute("javax.portlet.id")%>" type="hidden" name="<bean:write name="preferences" property="value(propertyId)" ignore="true"/>">

	<script type="text/javascript">
	window.onload = function(){
		var errorTip = document.getElementById("error_tip_<%=request.getAttribute("javax.portlet.id")%>");
		
		//初始化dsoframer
		var docName, dso_<%=request.getAttribute("javax.portlet.id")%>;
		try{
			dso_<%=request.getAttribute("javax.portlet.id")%> = new DsoFramer(document.getElementById('oframe<%=request.getAttribute("javax.portlet.id")%>'));
		}catch(e){
			errorTip.style.display = "";
			return;
		}
		
		<logic:notEmpty name="path">
			docName = "<bean:write name="name"/>";
			var bSuccess = dso_<%=request.getAttribute("javax.portlet.id")%>.openServer("<bean:write name="absolutePath"/>");
			if(!bSuccess){
				if(dso_<%=request.getAttribute("javax.portlet.id")%>.getOfficeVersion()=="2003"){
					errorTip.innerHTML = "检测到您的机器正在使用office2003,可能是由于版本过低导致打开失败";
					errorTip.style.display = "";
				}
			}
		</logic:notEmpty>
		<logic:empty name="path">
			docName = 'office.<%=request.getAttribute("javax.portlet.id")%>.'+new Date().getTime()+".doc";
			dso_<%=request.getAttribute("javax.portlet.id")%>.createNew("");
		</logic:empty>

		//追加表单提交事件，表单提交前，先提交word内容，把返回的路径赋值给text域
		var actual = document.getElementById("input_<%=request.getAttribute("javax.portlet.id")%>");
		if(actual.form.attachEvent){
			actual.form.attachEvent("onsubmit",(function(dso,actual,docName){
				return function(){
					var path = dso.save(docName);
					actual.value = path;
				}
			})(dso_<%=request.getAttribute("javax.portlet.id")%>,actual,docName));
			//离开页面前，先关闭dsoframer
			window.attachEvent("onbeforeunload",function(){
				dso_<%=request.getAttribute("javax.portlet.id")%>.close();
			})
		}else{
			actual.form.addEventListener("onsubmit",(function(dso,actual,docName){
				return function(){
					var path = dso.save(docName);
					actual.value = path;
				}
			})(dso_<%=request.getAttribute("javax.portlet.id")%>,actual,docName));
			//离开页面前，先关闭dsoframer
			window.addEventListener("onbeforeunload",function(){
				dso_<%=request.getAttribute("javax.portlet.id")%>.close();
			})
		}

	}

		
		
	</script>
