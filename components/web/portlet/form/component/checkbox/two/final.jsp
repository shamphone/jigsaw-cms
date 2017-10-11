<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<script language="javascript" src="<html:rewrite page="/portlet/form/component/suggestText/script.js" module=""/>" type="text/javascript" ></script>
<bean:define id="properties" name="preferences" property="values(filter-patterns)" type="java.lang.String[]"/>
<%if(properties[0]!=null&&!properties[0].equals("")){%>
	<ul <logic:notEmpty name="preferences" property="value(firstStyle)">class="<bean:write name="preferences" property="value(firstStyle)"/>"</logic:notEmpty>>		
		<logic:empty name="preferences" property="value(value0)">  
		    <logic:iterate id="node" name="nodes" indexId="ind">
		      <li>
		        <input id="<%= properties[0] %><bean:write name="ind"/>" type="checkbox" value="<cms:node name="node" propertyName="title" ignore="true"/>" name="<%= properties[0] %>" />
		        <label for="<%= properties[0] %><bean:write name="ind"/>"><cms:node name="node" propertyName="title" ignore="true"/></label>
		        <%if(properties[1]!=null&&!properties[1].equals("")){%>
		        <ul <logic:notEmpty name="preferences" property="value(secondStyle)">class="<bean:write name="preferences" property="value(secondStyle)"/>"</logic:notEmpty>>
		        	<bean:define id="children" name="node" property="nodes"/>
		        	
		        		<logic:iterate id="childNode" name="children" indexId="ind1">
		        			<li>
			        			<input id="<%= properties[0] %><bean:write name="ind"/>*<%= properties[1] %><bean:write name="ind1"/>" type="checkbox" value="<cms:node name="childNode" propertyName="title" ignore="true"/>" name="<%= properties[1] %>" onclick="selectParent(this)"/>
			        			<label for="<%= properties[0] %><bean:write name="ind"/>*<%= properties[1] %><bean:write name="ind1"/>"><cms:node name="childNode" propertyName="title" ignore="true"/></label>
		        			</li>
		        		</logic:iterate>		        	
		        </ul>
		        <%} %>
		      </li>
		    </logic:iterate>
		</logic:empty>
		<logic:notEmpty name="preferences" property="value(value0)">
			<bean:define id="values1" name="preferences" property="values(value0)" type="String[]"/>  
			<logic:iterate id="node" name="nodes" indexId="ind">
			  <fulong:define id="theTitle1" name="node" propertyName="title" type="String"/>
		      <li>
		      	<% boolean hasthis1 = false;
		      	for(int i=0; i<values1.length;i++){%>
		      		<fulong:equal name="node" propertyName="title" value="<%= values1[i] %>">		      							
						<%hasthis1 = true;%>		      							
				</fulong:equal>
		      	<%}
		      	if(hasthis1){%>
		      		<input checked="true" id="<%= properties[0] %><bean:write name="ind"/>" type="checkbox" value="<cms:node name="node" propertyName="title" ignore="true"/>" name="<%= properties[0] %>" />
		      	<%}else{%>
		      		<input id="<%= properties[0] %><bean:write name="ind"/>" type="checkbox" value="<cms:node name="node" propertyName="title" ignore="true"/>" name="<%= properties[0] %>" />
		      	<%}%> 		        
		        <label for="<%= properties[0] %><bean:write name="ind"/>"><cms:node name="node" propertyName="title" ignore="true"/></label>
		        <ul <logic:notEmpty name="preferences" property="value(secondStyle)">class="<bean:write name="preferences" property="value(secondStyle)"/>"</logic:notEmpty>>		        	
		        	<bean:define id="children" name="node" property="nodes"/>
		        	<%if(properties[1]!=null&&!properties[1].equals("")){%>
		        		<logic:empty name="preferences" property="value(value1)">
			        		<logic:iterate id="childNode" name="children" indexId="ind1">
			        			<li>
				        			<input id="<%= properties[0] %><bean:write name="ind"/>*<%= properties[1] %><bean:write name="ind1"/>" type="checkbox" value="<cms:node name="childNode" propertyName="title" ignore="true"/>" name="<%= properties[1] %>" onclick="selectParent(this)" />
				        			<label for="<%= properties[0] %><bean:write name="ind"/>*<%= properties[1] %><bean:write name="ind1"/>"><cms:node name="childNode" propertyName="title" ignore="true"/></label>
			        			</li>
			        		</logic:iterate>
		        		</logic:empty>
		        		<logic:notEmpty name="preferences" property="value(value1)">
		        			<bean:define id="values2" name="preferences" property="values(value1)" type="String[]"/>
		        			<logic:iterate id="childNode" name="children" indexId="ind1">
		        				<fulong:define id="theTitle2" name="childNode" propertyName="title" type="String"/>
		        				<li>
		        					<% boolean hasthis2 = false;
		      						for(int i=0; i<values2.length;i++){%>
		      						<fulong:equal name="childNode" propertyName="title" value="<%= values2[i] %>">		      							
		      								<%hasthis2 = true;%>		      							
		      						</fulong:equal>
		      						<%}
							      	if(hasthis2){%>
							      		<input checked="true" id="<%= properties[0] %><bean:write name="ind"/>*<%= properties[1] %><bean:write name="ind1"/>" type="checkbox" value="<cms:node name="childNode" propertyName="title" ignore="true"/>" name="<%= properties[1] %>" onclick="selectParent(this)" />
							      	<%}else{%>
							      		<input id="<%= properties[0] %><bean:write name="ind"/>*<%= properties[1] %><bean:write name="ind1"/>" type="checkbox" value="<cms:node name="childNode" propertyName="title" ignore="true"/>" name="<%= properties[1] %>" onclick="selectParent(this)" />
							      	<%}%>				        			
				        			<label for="<%= properties[1] %><bean:write name="ind1"/>"><cms:node name="childNode" propertyName="title" ignore="true"/></label>
			        			</li>
			        		</logic:iterate>
		        		</logic:notEmpty>
		        	<%}%>
		        </ul>
		      </li>
		    </logic:iterate>
		</logic:notEmpty>
	</ul>
<%}%>
<script type="text/javascript" language="javascript">
function selectParent(oCheckbox){
	var oID = oCheckbox.id;
	var IDs = oID.split("*");
	var parentID = IDs[0];
	if(parentID!=null){
		var parentObject = document.getElementById(parentID);
		if(parentObject!=null){
			if(oCheckbox.checked){
				parentObject.checked = true;
			}
		}
	}
	
}
</script>