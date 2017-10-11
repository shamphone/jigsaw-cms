<%@ page contentType="text/javascript; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>

/**
 * 验证验证码是否输入正确
 * @param oForm 表单
 * @param name：控件名称
*  @param pattern:日期格式
 */
Validator.Rules['checkcode'] = function (oForm, inputTextName)
{
    var req = getXMLHttpRequest();
    var url = '/components/portlet/form/component/checkcode/authCodeReturn.jsp?date='+(new Date());
    req.open("GET",url,false);            
    req.send(null);          
    var latestcode = req.responseText;
    latestcode = latestcode.trim();
    var inputcode = oForm.elements[inputTextName].value; 
    inputcode = inputcode.trim();
    return latestcode == inputcode ;
}