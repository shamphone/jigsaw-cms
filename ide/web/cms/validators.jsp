<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>

<script type="text/javascript" language="javascript">
///生成验证脚本//
var definitions<bean:write name='com.fulong.form.name'/>=new Object();
<logic:iterate name="properties" id="property">
<bean:define id="definitionId" name="property" property="declaringNodeDefinition.ID" type="java.lang.String" />
<bean:define id="propId" name="property" property="ID" type="java.lang.String" />
definitions<bean:write name='com.fulong.form.name'/>["<%= definitionId.replace("-", "_") + "_" + propId%>"]=new Array(""<logic:iterate name="property" property="valueConstraints" id="constraint">,"<bean:write name="constraint"/>"</logic:iterate>);
</logic:iterate>


function check<bean:write name='com.fulong.form.name'/>PropertyValue(oValue,property){
  try{
    if(validate<bean:write name='com.fulong.form.name'/>(oValue,property)){
      validate<bean:write name='com.fulong.form.name'/>Success(property);
      return true;
    }
    else{
      validate<bean:write name='com.fulong.form.name'/>Failed(property);
      return false;
    }
  }catch(e){alert(e.message)}

}


/**
* 执行验证
**/
function validate<bean:write name='com.fulong.form.name'/>(oValue,property){
  var constraints=definitions<bean:write name='com.fulong.form.name'/>[property];
  var exp;
 for(i=1;constraints!=null&&i<constraints.length;i++){
    <logic:iterate id="validator" name="validators">
    exp=/<bean:write name="validator" property="pattern"/>/;
    if(constraints[i].match(exp)){
      if(!<bean:write name="validator" property="jsFunction" filter="false"/>(oValue,property,'<bean:write name="categoryID" ignore="true"/>','<bean:write name="contentID" ignore="true"/>','<html:rewrite module="" page="/"/><bean:write name="validator" property="url"/>','<bean:write name="definitionID" ignore="true"/>'))
      {
        return false;
      }
    }
    </logic:iterate>
  }
  return true;
}

function check<bean:write name='com.fulong.form.name'/>All(oForm){
<logic:iterate name="properties" id="property">
<bean:define id="definitionId" name="property" property="declaringNodeDefinition.ID" type="java.lang.String" />
<bean:define id="propId" name="property" property="ID" type="java.lang.String" />
  if(parseValues(oForm,'<%= definitionId.replace("-", "_") + "_" + propId%>').length==0){
    if(!doCheckValue(oForm.elements['<%= definitionId.replace("-", "_") + "_" + propId%>'].value,'<%= definitionId.replace("-", "_") + "_" + propId%>',oForm))
    return false;
  }else{
    if(!doCheckValue(parseValues(oForm,'<%= definitionId.replace("-", "_") + "_" + propId%>'),'<%= definitionId.replace("-", "_") + "_" + propId%>',oForm))
    return false;
  }
</logic:iterate>
return true;
}

</script>

<cms:unique name='<%=request.getAttribute("validators").toString()%>'>
<script type="text/javascript" language="javascript">
/**
*验证值oValue是否满足property所定义的校验，验证后调用相应的方法
*@param oValue 值
*@param property 属性标识
*/
function doCheckValue(oValue, property, oForm){
    if(oForm==null)
     oForm=document.forms[0];
    var id=oForm.id;
    if((id==null)||(id.length==0)){
      id=oForm.name;
    }
    if(id==null)
    {
      id="";
    }
    return eval("check"+id+"PropertyValue(oValue,property)");
}

<logic:iterate id="validator" name="validators">
    <bean:write name="validator" property="javascript" filter="false"/>
</logic:iterate>
/**
* 辅助方法:获取多选框的值
*/
function parseValues(oForm, name){
    var values=new Array();
    var elems=oForm.elements[name];
    for(i=0;i<elems.length;i++){
        if(elems[i].checked)
        values.push(elems[i].value);
    }
    return values;
}

</script>
</cms:unique>
