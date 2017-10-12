<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@page import="com.fulong.longcon.repository.Node"%>
<script language="javascript" src="<html:rewrite page="/script/common.js" module="/common"/>" type="text/javascript" ></script>
<script language="javascript" src="<html:rewrite page="/script/portlet.js" module="/common"/>" type="text/javascript" ></script>
<%
	String propID = "title";
com.fulong.longcon.repository.PropertyDefinition prop = (com.fulong.longcon.repository.PropertyDefinition)request.getAttribute("suggestProp");
if(prop!=null){
	propID = prop.getID();
}
%>
<logic:present name="getAll">
<form action="">
<div align="center" style="height:280px;overflow-y: scroll;border-bottom:1px solid #c0c0c0;width:100%;">
<table id="<bean:write name="portletID" ignore="true"/>Table" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
<logic:iterate id="content" name="contents" length="20">
		<tr id='<bean:write name="content" property="ID" ignore="true"/>' >
		<%Node parent = (Node)content;
				String title = "";
				while(parent.getParent()!=null){
					try{
						if(parent.getParent().getID().equals("1000000000000")){
							break;
						}						
						title = parent.getParent().getProperty(propID).getString()+" "+ title ;
						parent = parent.getParent();						
					}catch(Exception ex){
						
					}					
				}
				try{
					title = title+((Node)content).getProperty(propID).getString();
				}catch(Exception ex){
					
				}				
				%>
		<td>
		<input name="oRadio" type="radio" value="<%=title.trim()%>"/>
		</td>
			<td>
				
				<%=title.trim()%></td>
		</tr>
	</logic:iterate>
</table>
<div id="gotobar">                          
<iframe id="tipsFrame" width="400" height="400" frameborder="1" scrolling="auto" style="display:none;z-index:10;position:absolute;left:0px;top:0px" ></iframe>        
<fulong:pager target="_self"/> </div>
</div>
<div align="center" style="margin-top:10px;"><button onclick="selectThisText(this)">确定</button><button onclick="window.close()">取消</button></div>
</form>
</logic:present>
<logic:notPresent name="getAll">
<table id="<bean:write name="portletID" ignore="true"/>Table" border="0" cellpadding="0" cellspacing="0" width="100%">
<logic:iterate id="content" name="contents" length="10">
		<tr id='<bean:write name="content" property="ID" ignore="true"/>' onmouseover="getFocus<bean:write name="portletID" ignore="true"/>(this)" onmouseout="loseFocus<bean:write name="portletID" ignore="true"/>(this)">
			<td>
				<%Node parent = (Node)content;
				String title = "";
				while(parent.getParent()!=null){
					try{
						if(parent.getParent().getID().equals("1000000000000")){
							break;
						}						
						title = parent.getParent().getProperty(propID).getString()+" "+ title ;
						parent = parent.getParent();						
					}catch(Exception ex){
						
					}					
				}
				try{
					title = title+((Node)content).getProperty(propID).getString();
				}catch(Exception ex){
					
				}				
				%>
				<%=title.trim()%></td>
		</tr>
	</logic:iterate>
</table>
</logic:notPresent>	
<script language="JavaScript" type="text/Javascript">
function selectThisText(oButton){
	var oOForm = oButton.form;	
	if(oOForm!=null){
		var oRadio = oOForm.oRadio;
		if(oRadio!=null){
			var oValue = GetRadioValue(oRadio);
				var ret=new Object();
			  ret.ID = oValue;
			  window.returnValue=ret;
			  window.close();
		}
	}
}
</script>

