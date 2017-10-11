<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
/**
 * 验证小于
 * @param oForm 表单
 * @param name：控件名称
 */
Validator.Rules['FieldLess']	=	function (oForm, name, another){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
	var oAnother = oForm.elements[another];
	var value1 = oComp.value;
	var value2 = oAnother.value;
	if(typeof(value1)=='undefined'){
    	value1 = oComp[0].value
    }  
    if(typeof(value2)=='undefined'){
    	value2 = oAnother[0].value
    } 
	return Validator._compare(value1, value2);
}
/**
 * 验证大于
 * @param oForm 表单
 * @param name：控件名称
 */
Validator.Rules['FieldGreater']	=	function (oForm, name, another){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
	var oAnother = oForm.elements[another];
	var value1 = oComp.value;
	var value2 = oAnother.value;
	if(typeof(value1)=='undefined'){
    	value1 = oComp[0].value
    }  
    if(typeof(value2)=='undefined'){
    	value2 = oAnother[0].value
    }
	return !Validator._compare(value1,value2);
}

/**
 * 验证等于
 * @param oForm 表单
 * @param name：控件名称
 */
Validator.Rules['FieldEquals']	=	function (oForm, name, another){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
	var oAnother = oForm.elements[another];
	var value1 = oComp.value;
	var value2 = oAnother.value;
	if(typeof(value1)=='undefined'){
    	value1 = oComp[0].value
    }  
    if(typeof(value2)=='undefined'){
    	value2 = oAnother[0].value
    }
	if(value1 == null || value1.length ==0 )
		return false;
	if(value2 == null || value2.length ==0 )
		return true;
	return oAnother.value.indexOf(value1)==0 && oComp.value.indexOf(value2)==0;		
}

Validator._compare = function(value1, value2){
	if(value1 == null || value1.length ==0 )
		return false;
	if(isNaN(parseInt(value1))){
		try{
		 return parseInt(value1)<parseInt(value2);
		}finally{}
		try{
		 return parseFloat(value1)<parseFloat(value2);
		} finally{}
	}else{
		try{
		var minDateArr = value1.split(/[-:\s]/);
		var maxDateArr = value2.split(/[-:\s]/);
		var minDate = new Date();
		var maxDate = new Date();
		if(minDateArr.length==3){
			minDate = new Date(minDateArr[0],minDateArr[1],minDateArr[2]);
		}else if(minDateArr.length==6){
			minDate = new Date(minDateArr[0],minDateArr[1],minDateArr[2],minDateArr[3],minDateArr[4],minDateArr[5]);
		}
		if(maxDateArr.length==3){
			maxDate = new Date(maxDateArr[0],maxDateArr[1],maxDateArr[2]);
		}else if(minDateArr.length==6){
			maxDate = new Date(maxDateArr[0],maxDateArr[1],maxDateArr[2],maxDateArr[3],maxDateArr[4],maxDateArr[5]);
		}
		return minDate<maxDate;
		} finally{}
	}
	return false;
}


