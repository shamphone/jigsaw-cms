<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
/**
 * 验证唯一
 * @param oForm 表单
 * @param name：控件名称
*  @param definition:内容库
 */
Validator.Rules['Unique']	=	function (oForm, name, definition){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
    var value=oComp.value
    if(typeof(value)=='undefined'){
    	value = oComp[0].value
    }  
	var valid=true;
    if((value==null)||(value.length==0))
    	valid = true;
    else{
		var xmlhttp = getXMLHttpRequest();
		var urlTemp = '/ide/common/validateUnique.do?categoryID=' + definition + '&value=' + value + '&property=' + name + '&contentID=' + oForm.node;
		xmlhttp.open("Post",urlTemp,false);
		xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
		xmlhttp.send(null);
		valid = (xmlhttp.responseText == "true");
	}
//    Validator.alert(oComp,valid);
    return valid;
}

