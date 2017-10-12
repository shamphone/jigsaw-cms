<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="EXPIRES" content="0"/>
<meta http-equiv="Pragma" Content="No-cach" />
<TITLE> 流程定义 </TITLE>
<link rel="stylesheet" type="text/css" href="style.css"/>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language=Javascript type="text/Javascript" src="inc/contextMenu/context.js"></script>
<script language=Javascript type="text/Javascript" src="inc/webflow.js"></script>
<script language=Javascript type="text/Javascript" src="inc/function.js"></script>
<script language=Javascript type="text/Javascript" src="inc/shiftlang.js"></script>
<script language=Javascript type="text/Javascript" src="inc/movestep.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="script.js"></script>
<logic:present name="graphicValue">
<xml id="oXML" ><bean:write name="graphicValue" ignore="true" filter="false"/></xml>
</logic:present>
<logic:notPresent name="graphicValue"><bean:write name="graphicValue" ignore="true" filter="false"/>	
<xml id="oXML" src="blank_process.bpml.xml"></xml>
</logic:notPresent>
</HEAD>
<BODY onload='PMEditor.loadFromXML()' oncontextmenu="cleancontextMenu();return false;" scroll="no">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<INPUT TYPE="hidden" name=FlowXML onpropertychange='if(AUTODRAW) redrawVML();'>
<TR>
<TD  valign=top align=left>
<div id="processDesign" onclick="cleancontextMenu();PMEditor.showProcessInfo();return false;" oncontextmenu='flowContextMenu();return false;'>
<v:group id="FlowVML" coordsize="3000,2500"></v:group></div>
</TD>
<td valign="top" align="left" id="processInfo">
		<!-- ---------------------------------------------------------------------------------------- -->
		<fieldset style="border:0px;">
		流程ID：<br />
		<input type="text" name="processID" id="processID" value='' size="25"/><br /><br />
		流程名称：<br />
		<input type="text" name="processName" id="processName" value='' size="25"/><br /><br />
		流程描述：<br />
		<textarea rows="7" cols="20" id="description" name="description" style="width:180px;"></textarea>
		</fieldset>
		<!-- ------------------------------------------------------------------------------------------- -->
		<fieldset style="display:none;border:0px;">
		转移ID：<br />
		<input type="hidden" name="ActionIdhidden" id="ActionIdhidden" value=''"/>
		<input type="text" name="ActionId" id="ActionId" value='' size="25" onchange="checkTransitionId(this.value)"/><br />
		转移名称：<br />
		<input type="text" name="ActionText" id="ActionText" value='' size="25"/><br />
		转移类型:<br />
		<SELECT NAME="transType" class=txtput style="width:185px">
			<option value="NONE">无条件转移</option>
			<option value="CONDITION">条件转移</option>
			<option value="OTHERWISE">其他</option>
			<option value="EXCEPTION">发生异常时转移</option>
			<option value="DEFAULTEXCEPTION">缺省异常转移</option>
		</SELECT>
		起始活动:<br />
		<SELECT NAME="From" class=txtput style="width:185px" onchange="changeStep()"></SELECT>
		目的活动:<br />
		<SELECT NAME="To" class=txtput style="width:185px" onchange="changeStep()"></SELECT>
		内容分类：<br />
		<input type="hidden" name="category" id="category" />                  			
		<input type="text" name="categoryName" readonly="readonly" value="" class="txtput" size="25"/><br />
 		<button class="commonbut" id="searchN" onclick="searchNodeDefinition(document.getElementById('category'),document.getElementById('categoryName'))">选择...</button>
		<input type="hidden" name="condition" id="condition"/><br />
		转移条件：<br />
		<select multiple="multiple" name="filterPatterns" size="5" style="width:185px;"></select><br />
		<button class="commonbut" onclick="newFilter(document.getElementById('category'),document.getElementById('filterPatterns'));setCondition(document.getElementById('filterPatterns'),document.getElementById('condition'));">添 加</button>
  		<button class="commonbut" onclick="deleteOption(document.getElementById('filterPatterns'));setCondition(document.getElementById('filterPatterns'),document.getElementById('condition'));">删 除</button>
		<!-- 转移条件：<br />
		<textarea rows="7" cols="20" id="transitionCondition" name="transitionCondition" style="width:180px;"></textarea>
		 -->
		 
		 </fieldset>
		 <!-- ------------------------------------------------------------------------------------------------- -->
		<fieldset style="display:none;border:0px;">
		活动ID：<br />
		<input type="hidden" name="activityIDhidden" id="activityIDhidden" value=''/>
		<input type="text" name="activityID" id="activityID" value='' size="25" onchange="checkActivityId(this.value)"/><br /><br />
		活动名称：<br />
		<input type="text" name="activityName" id="activityName" value='' size="25"/><br /><br />
		活动描述：<br />
		<textarea rows="7" cols="20" id="activityDescription" name="activityDescription" style="width:180px;"></textarea>
		<label id="serviceLabel" style="display:none">选择服务:<br /></label>
		<select name="service" id="service" style="display:none;width:200px;">
				<option value="0">无</option>
				<logic:iterate id="service" name="services">
					<option title='<bean:write name="service" property="value"/>' value='<bean:write name="service" property="key"/>'><bean:write name="service" property="value"/></option>
				</logic:iterate>
				</select>&nbsp;<button id="serviceConf" onclick="editService()" style="display:none">配置参数</button>
		<input type="hidden" id="paramsInput" name="paramsInput" value=""/>
		<input type="hidden" id="activityType" name="activityType">
		<input type="hidden" id="Height" name="Height">
		<input type="hidden" id="Width" name="Width">
		<input type="hidden" id="XCoordinate" name="XCoordinate">
		<input type="hidden" id="YCoordinate" name="YCoordinate">
		</fieldset>
	</td>	
</TR>
</table>
</BODY>
</HTML>
