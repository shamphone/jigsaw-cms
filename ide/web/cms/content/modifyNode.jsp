<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">修改分类</tiles:put>
  <tiles:put name="dialog">
  <form action="updateNode.do">
  	<bean:define id="propID" name="proID"/>
  	<input type="hidden" name="nodeID" value="<bean:write name="oNode" property="ID" ignore="true"/>"/>
  	<input type="hidden" name="proID" value="<bean:write name="proID" ignore="true"/>"/>
    <div>请输入分类名称：</div>
    <input type="text" name="prop" value="<cms:node name="oNode" propertyName='<%=(String)propID %>' ignore="true"/>"  size="34">    
    <div class="operation">
    <button onclick="oSubmit(this)">确定</button>&nbsp;&nbsp;<button onclick="window.close();">取消</button>
    </div>
  </form>
  <script type="text/Javascript" language="Javascript">
function oSubmit(oButton){
	var oForm = oButton.form;
	if(oForm!=null){
		if(oForm.prop.value==null||oForm.prop.value==""){
			alert("名称不能为空！");
		}else{
			oForm.submit();
		}
	}
}
</script>
</tiles:put>
</tiles:insert>
