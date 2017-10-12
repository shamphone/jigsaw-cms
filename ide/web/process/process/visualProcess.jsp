<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>可视化编辑流程</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />

<script language="Javascript" type="text/Javascript">
     function loadFromXML(){
    	 var strElement;
    	 var newPoint;
    	 var x = 0;
    	 var y = 0;
		<logic:present name="activities">
         <logic:iterate id="activity" name="activities">
         strElement = "<v:RoundRect style='top:"+x+";left:"+y+"width:100;height:100;position:absolute;'><v:shadow on='T' type='single' color='#b3b3b3' offset='5px,5px'/><v:TextBox inset='5pt,5pt,5pt,5pt' style='font-size:10.2pt;'>"+'<bean:write name="activity" property="name" ignore="true"/>'+"</v:TextBox></v:RoundRect>";
         x = x+20;
         y = y+20;
         newPoint = document.createElement(strElement);
         FlowVML.insertBefore(newPoint);
         alert('<bean:write name="activity" property="name" ignore="true"/>');
         </logic:iterate>
         <logic:iterate id="transition" name="transitions">
         </logic:iterate>
         </logic:present>
    	 }    
    </script>
</head>
<body onload='loadFromXML();'>
<TABLE border=0>
	<TR>
		<TD width="1000" height="500">
		<TABLE cellspacing="0" cellpadding="0" class="panel_style">
			<TR>
				<TD width="1000" height="500" bgcolor="#EFF2F5" valign="top" align="left">
					<v:group ID="FlowVML" style="left:19;top:56;width:1000px;height:500px;" coordsize="2500,2200">
				</v:group></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</body>
</html>
