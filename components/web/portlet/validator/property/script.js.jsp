<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
/**
 * 验证相等
 * @param oForm： 表单
 * @param name：控件名称
*  @param value: 值
 */
Validator.Rules['equal']	=	function (oForm, name, value){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
	var oValue = value;
	var vlu = oComp.value
	if(typeof(vlu)=='undefined'){
    	vlu = oComp[0].value
    }  
	if(vlu == null && oValue == null )
		return true;
	if(vlu == null || oValue ==null )
		return false;
		
	return oValue == vlu;	
}
Validator.Rules['pwequal']	=	function (oForm, name, value){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
	var oValue = URLencode(value);
	var val = oComp.value;
	if(typeof(val)=='undefined'){
    	val = oComp[0].value
    } 
	var pw = URLencode(val);
		var valid=true;
	    var xmlhttp = getXMLHttpRequest();
		var urlTemp = '/ide/common/validatePropertyValue.do?password=' + pw+ '&oValue=' + oValue;
		xmlhttp.open("post",urlTemp,false);
		xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		xmlhttp.setRequestHeader("If-Modified-Since","0"); //不缓存Ajax
		xmlhttp.send(null);
		valid = (xmlhttp.responseText == "true");
		return valid;

}
function URLencode(sStr) 
{
    return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');
}
