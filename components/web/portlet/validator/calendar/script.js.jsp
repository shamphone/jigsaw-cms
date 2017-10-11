<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
/**
 * 验证日期格式
 * @param oForm 表单
 * @param name：控件名称
*  @param pattern:日期格式
 */
Validator.Rules['Calendar']	=	function (oForm, name, pattern){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
    var value=oComp.value;
     if(typeof(value)=='undefined'){
    	value = oComp[0].value
    }
	var valid=true;
    if((value==null)||(value.length==0))
    	valid = true;
    else{
    	if (!pattern)
    		valid = false;
    	else {
    		var re;
    		if(pattern=="noTime")
    			re = /^\d{4}(-\d{2}){2}$/;
    		else if(pattern=="hasTime"){
    			re = /^\d{4}(-\d{2}){2} \d{2}(:\d{2}){2}$/;
    		}else if(pattern=="timeToMinute"){
    			re = /^\d{4}(-\d{2}){2} \d{2}(:\d{2}){1}$/;
    		}
    			
            valid = re.test(value);
        }
	}
    return valid;
}

/**
 * 验证日期最小值
 * @param oForm 表单
 * @param name：控件名称
 * @param minDate:日期最小值
 */
Validator.Rules['minDate']	=	function (oForm, name, minDate){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
    var value=oComp.value;
     if(typeof(value)=='undefined'){
    	value = oComp[0].value
    }
	var valid=true;
    if((value==null)||(value.length==0))
    	valid = true;
    else{
    	if(minDate==null||minDate.length==0){
    		valid = true;
    	}else{
    		var arr = value.split(/[-:\s]/);
    		var minDateArr = minDate.split(/[-:\s]/);
    		if(arr.length < 3){
    			valid = false;
    		}else{
    			var dateTemp = new Date(arr[0],arr[1],arr[2]);
    			var sDate = new Date(minDateArr[0],minDateArr[1],minDateArr[2]);
    			if(dateTemp==null){
    				valid = false;
    			}else{
    				if(dateTemp>=sDate){
    					valid = true;
    				}else{
    					valid = false;
    				}
    			}
    		}
    	}
	}
    return valid;
}

/**
 * 验证日期最大值
 * @param oForm 表单
 * @param name：控件名称
 * @param maxDate:日期最大值
 */
Validator.Rules['maxDate']	=	function (oForm, name, maxDate){
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
    var value=oComp.value;  
     if(typeof(value)=='undefined'){
    	value = oComp[0].value
    }  
	var valid=true;
    if((value==null)||(value.length==0))
    	valid = true;
    else{
    	if(maxDate==null||maxDate.length==0){
    		valid = true;
    	}else{
    		var arr = value.split(/[-:\s]/);
    		var maxDateArr = maxDate.split(/[-:\s]/);
    		if(arr.length < 3){
    			valid = false;
    		}else{
    			var dateTemp = new Date(arr[0],arr[1],arr[2]);
    			var lDate = new Date(maxDateArr[0],maxDateArr[1],maxDateArr[2]);
    			if(dateTemp==null){
    				valid = false;
    			}else{
    				if(dateTemp<=lDate){
    					valid = true;
    				}else{
    					valid = false;
    				}
    			}
    		}
    	}
	}
    return valid;
}