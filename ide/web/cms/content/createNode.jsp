<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">修改分类</tiles:put>
  <tiles:put name="dialog">
  <form action="insertNode.do">
  	<bean:define id="propID" name="proID"/>
  	<input type="hidden" name="parentID" value="<bean:write name="parentID" ignore="true"/>"/>
  	<input type="hidden" name="fixPropID" value="<bean:write name="fixPropID" ignore="true"/>"/>
  	<input type="hidden" name="proID" value="<bean:write name="proID" ignore="true"/>"/>
  	<input type="hidden" name="childDef" value="<bean:write name="childDef" ignore="true"/>"/>
    <div align="left" style="margin-left:20px;">请输入分类名称：<input type="text" name="prop" value=""  size="34">  
    </div>  
    <div class="operation">
    <button  onclick="oSubmit(this)">确定</button>&nbsp;&nbsp;<button onclick="window.close();">取消</button>
    </div>
  </form>
  <script type="text/Javascript" language="Javascript">
function oSubmit(oButton)
{
	var oForm = oButton.form;
	if(oForm!=null)
	{
		var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i");
		if(oForm.prop.value.trim()==null||pat.test(oForm.prop.value.trim())==true)
		{
            if(oForm.prop.value.trim()==null)
			    alert("名称不能为空!");
            else
                alert("分类名称有非法字符!");
		}
        else
		    oForm.submit();		
	}
}
</script>
</tiles:put>
</tiles:insert>
