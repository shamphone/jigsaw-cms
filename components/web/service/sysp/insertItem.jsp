<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert page="/service/framework.jsp">
	<tiles:put name="javascript">
		<script language="Javascript" type="text/Javascript"
			src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
			<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
			<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
		<script type="text/javascript" language="javascript">
		function selectCategory(objID,objName){
			  var definition=CMSDialog.NodeDefinitionSelector();
				if(definition!=null){
			   if(definition.length>0){
			       for(var j=0;j<definition.length;j++){
			    	   objID.value=definition[j].ID;
			    	   objName.value=definition[j].name;
			       }
			     }
			  }
			}
		function selectProperty(categoryID,objID,objName){
			if(categoryID==null){
				alert("请选择分类！");
				return;
				}
			    var result=CMSDialog.PropertyDefinitionSelector(categoryID);
			    if(result!=null){
			    	objID.value=result.ID;
			    	objName.value=result.name;
			      }
		}
</script>
	</tiles:put>
	<tiles:put name="form">
	<html:form action="save.do" method="POST" >
	<html:hidden property="processID" styleId="processID"/>
	<html:hidden property="activityID" styleId="activityID"/>
	<html:hidden property="serviceID" styleId="serviceID"/>
		<tr>
			<td class="pannelDiv" valign="top"><select id="pannelSelect" name="pannelSelect" onchange="Service.SelectPanel(this)" size="10">
					<option value="0">基本信息</option>
			</select></td>
			<td id="tdFieldsets" valign="top" align="center"><fieldset style="display: none" class="fieldPanel">
			没有可配置的参数
			</fieldset>
			<div id="toolbar">
			<button type="submit" onclick="Service.OK(this.form)">确定</button>
			<button type="button" onclick="Service.Cancel(this.form)">取消</button>
			</div>
			</td>
		</tr>
	</html:form>
	</tiles:put>
</tiles:insert>