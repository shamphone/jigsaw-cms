<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>CooLink即时通讯模块后台管理系统</title>
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/demos.css"/>">
	</head>
	
	<script type="text/javascript">
	function getUsers() 
	{
		var selectedGroupId = document.getElementsByTagName("*").groupId.value;
		var userId = document.getElementsByTagName("*").userId.value;
		var title = document.getElementsByTagName("*").title.value;
		var desc = document.getElementsByTagName("*").desc.value;
		var startTime = document.getElementsByTagName("*").startTime.value;
		var endTime = document.getElementsByTagName("*").endTime.value;
		
		var participants = "";
		var members = document.getElementsByTagName("input"); 
		if(members.length){ 
			for(var i=0; i<members.length; i++) 
				if(members[i].checked)
					participants = participants + members[i].value +",";
		}
		else { 
			if(members.checked)
				participants =  members;
		} 
		
		document.getElementsByTagName("*").userId.length=0;
		
		document.location.href='edit.do?groupId='+selectedGroupId+'&userId='+userId+'&title='+title+'&desc='+desc+'&startTime='+startTime+'&endTime='+endTime+'&participants='+participants;
	}

	function selectALl() 
	{ 
		var code_Values = document.getElementsByTagName("input"); 
		if(code_Values.length){ 
			for(var i=0; i<code_Values.length; i++) 
				code_Values[i].checked = true; 
		}
		else { 
			code_Values.checked = true; 
		} 
	} 
	
	function selectNone() 
	{ 
		var code_Values = document.getElementsByTagName("input"); 
		if(code_Values.length){ 
			for(var i=0; i<code_Values.length; i++) 
				code_Values[i].checked = false; 
		}
		else { 
			code_Values.checked = false; 
		} 
	} 

	function reverse() 
	{ 
		var code_Values = document.getElementsByTagName("input"); 
		if(code_Values.length){ 
			for(var i=0; i<code_Values.length; i++) 
				code_Values[i].checked = !code_Values[i].checked; 
		}
		else { 
			code_Values.checked = !code_Values[i].checked; 
		} 
	} 

	var i = 0;
	function addFile(dvID, inputNamePrefix)
	{
		var dv = document.getElementById(dvID);
		
		var file = document.createElement("input");
		file.type = "file";
		file.id = inputNamePrefix + "(" + i + ")";
		file.name = inputNamePrefix + "(" + i + ")";
		
		dv.appendChild(file);

		var btn = document.createElement("input");
		btn.type = "button";
		btn.id = btn.name = "btn" + i;
		btn.value = "删除" ;

		btn.onclick = function() {
			var b = document.getElementById(btn.id);
			dv.removeChild(b.nextSibling); //remove <BR>
			dv.removeChild(b.previousSibling); //file
			dv.removeChild(b); //btn
		}

		dv.appendChild(btn);

		dv.appendChild(document.createElement("BR"));

		i++;
	}
				
	</script>
	
	<body style="height: 100%; margin: 0; padding: 0;">

		<div style="height:48px; width:100%; background:url('<html:rewrite page="/images/dialogtitle.gif"/>')">
			<table width="95%" border="0" cellpadding="6" cellspacing="0">
				<tr>
					<td>
						<font size="2" color="#173891">修改普通会议</font>
					</td>
					<td align="right" >
						<img src="<html:rewrite page="/images/viewrightimg.gif"/>" />
					</td>
				</tr>
			</table>
		</div>
		
		<br/>
		<html:messages id="message">
			<font color="#FF0000"><bean:write name="message"/></font>
		</html:messages>
		
		<html:form action="/modify.do" focus="title" method="post" onsubmit="return validateEditForm(this);" enctype="multipart/form-data" >
		
			<table width="100%" align="center" border="0" cellspacing="0" cellpadding="3" bgcolor="#F9FBFE">
				<tr>
					<td align="right"><font color="#173891">会议名称</font><font color="#FF0000">*</font></td>
					<td><html:text property="title" size="24" maxlength="16" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">开始时间</font><font color="#FF0000">*</font></td>
					<td><html:text property="startTime" size="24" maxlength="20"/>（时间格式：2010-10-12 15:36）</td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">结束时间</font><font color="#FF0000">*</font></td>
					<td><html:text property="endTime" size="24" maxlength="20"/>（时间格式：2010-10-12 15:36）</td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">会议描述</font></td>
					<td><html:textarea property="desc" cols="48" rows="5" /></td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">主持人</font><font color="#FF0000">*</font></td>
					<td>
						<html:select property="groupId" style="width: 156px;"  onchange="getUsers()">
							<html:optionsCollection name="groups" label="name" value="id" /> 
						</html:select>  
						<html:select property="userId" style="width: 156px;">
							<html:optionsCollection name="users" label="name" value="id" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="right"><font color="#173891">参会人员</font><font color="#FF0000">*</font></td>
					<td>
						<a href="#" onclick="selectALl()">全选</a>						
						<a href="#" onclick="selectNone()">全不选</a>	
						<a href="#" onclick="reverse()">反选</a>					
					</td>
				</tr>
				<logic:iterate id="user" name="members">
					<tr>
						<td></td>
						<td>
							<html:multibox property="participants">
								<bean:write name="user" property="id"></bean:write>
							</html:multibox>
							<bean:write name="user" property="name"></bean:write>
						</td>
					</tr>
				</logic:iterate>
				<logic:notEmpty name="documents">
					<tr>
						<td align="right"><font color="#173891">选择要删除的文档</font></td>
						<td></td>
					</tr>
					<logic:iterate id="doc" name="documents">
						<tr>
							<td></td>
							<td>
								<html:multibox property="delFiles">
									<bean:write name="doc" property="path"></bean:write>
								</html:multibox>
								<bean:write name="doc" property="fileName"></bean:write>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<tr>
					<td align="right">上传文档</td>
					<td>
						<input type="button" value="添加" onclick="addFile('dvDocs','file')"/>
					</td>
				</tr>
			</table>
			
			<div id="dvDocs"></div>	
			
			<table width="100%" align="center" cellspacing="0" cellpadding="8">
				<tr>
					<td align="center">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="submit" value="修改" class="blueButton">
						<input style="color:#173891;background:url(<html:rewrite page="/images/sbl.gif"/>);border:none;width:60;height:22" type="button" name="cancel" id="ButtonOK" value="取消">
					</td>
				</tr>
			</table>
			
		</html:form>
	</body>
	
	<html:javascript formName="/modify"/>
	
</html:html>
