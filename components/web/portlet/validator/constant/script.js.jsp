<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%
/**
 *
 *数据校验类,用来在客户端完成对表单和控件的实时校验
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf
 * @version 2.0
 *
 *
**/
%>
var Validator=new Object();

Validator.components=new Array();
Validator.forms=new Array();
Validator.Rules = new Array();
/**
 * 添加规则，在validator/script.js.jsp中调用
 *@ param formName 表单名称
 *@ param compName	组件名称
 *@ param	rule	验证规则，即对应的函数名称
 *@ param	value	校验值
 *
*/
Validator.AddRule		=	function(formName,compName,spanTrue, spanFalse,rule,value){
    if(this.forms[formName]==null)
       this.forms[formName]=new Array();
    if(this.components[formName+compName]==null)
       this.components[formName+compName]=new Array();
//    if(value!=null)
//    value = value.replace(/\\/g,'\\\\');
    var funcComp= new Object();
    funcComp.rule = rule;
    funcComp.form = document.formName;
    funcComp.component =compName;
    funcComp.spanTrue = document.getElementById(spanTrue);
    funcComp.spanFalse = document.getElementById(spanFalse);
    funcComp.value = value;
	this.components[formName+compName].push(funcComp);	 
	this.forms[formName].push(funcComp);
}

/**
 * 检验控件的数据是否满足要求，当用户填写好组件数据后，调用这个方法来做数据验证，同时显示实时提示信息
 *@ param	oComp	被校验的组件
 *
*/
Validator.ValidateComponent		=	function(oComp, validator ,isfile,file){
    if(validator==null)
		validator=oComp.name;
    var formName=oComp.form.name; 
	if(this.components[formName+validator]==null)
		return true;
		
	var theRules=this.components[formName+validator];
	for(i=0;i<theRules.length;i++){
		  theRules[i].spanTrue.style.display = "";
		  theRules[i].spanFalse.style.display = "none";		
	}
	if(isfile && isfile == "asayfile"){
		for(i=0;i<theRules.length;i++){
			if(theRules[i].rule=="Required"&&!Validator.Rules[theRules[i].rule](oComp.form, theRules[i].component,isfile,file)){
			  theRules[i].spanTrue.style.display = "none";
			  theRules[i].spanFalse.style.display = "";		
			}
		}
	}else{
		for(i=0;i<theRules.length;i++){
			if(!Validator.Rules[theRules[i].rule](oComp.form, theRules[i].component,theRules[i].value)){
			  theRules[i].spanTrue.style.display = "none";
			  theRules[i].spanFalse.style.display = "";		
			}
			//alert("theRules["+i+"].rule="+theRules[i].rule+"@@theRules[i].value="+theRules[i].value+"@@oComp.form="+oComp.form+"@@theRules["+i+"].component="+theRules[i].component+"@@Validator.Rules[theRules[i].rule](oComp.form, theRules[i].component,theRules[i].value)="+Validator.Rules[theRules[i].rule](oComp.form, theRules[i].component,theRules[i].value))		
		}
	}
}
Validator.alert = function(oComp,valid){	
	var validId=oComp.form.name+"_"+oComp.name+"_"+"1";
	var inValidId=oComp.form.name+"_"+oComp.name+"_"+"0";
	if(valid){
		document.all(validId).style.display='';
		document.all(inValidId).style.display='none';
	}else{	
		document.all(validId).style.display='none';
		document.all(inValidId).style.display='';
	}
}
/**
 *
 * 对表单中所有待校验的参数进行校验，在表单提交时调用
*/
Validator.ValidateForm	=	function(oForm){

	/*
	* 添加异步上传占位符 校验兼容
	*/
    if(this.forms[oForm.name]==null)
        return true;
    var theRules = this.forms[oForm.name];
    var valid = true;
	for(i=0;i<theRules.length;i++){
		  theRules[i].spanTrue.style.display = "";
		  theRules[i].spanFalse.style.display = "none";		
	}
	for(i=0;i<theRules.length;i++){
		if(theRules[i].rule=="Required" && oForm.elements[theRules[i].component]!=null && oForm.elements[theRules[i].component].tagName=="SELECT" && oForm.elements[theRules[i].component].previousSibling!=null && typeof(oForm.elements[theRules[i].component].previousSibling)!="undefined" && oForm.elements[theRules[i].component].previousSibling.value=="asfileforform"){
			if(!Validator.Rules[theRules[i].rule](oForm, theRules[i].component,'asayfile',oForm.elements[theRules[i].component])){
			  theRules[i].spanTrue.style.display = "none";
			  theRules[i].spanFalse.style.display = "";
			  valid = false;	
			}
		}else if(!Validator.Rules[theRules[i].rule](oForm, theRules[i].component,theRules[i].value)){
			  theRules[i].spanTrue.style.display = "none";
			  theRules[i].spanFalse.style.display = "";
			  valid = false;	
		}	
	}
    return valid;
}
/**
 * 验证不为空
 * @param oForm 表单
 * @param name：控件名称
 * 添加 异步上传按钮过滤
 */
Validator.Rules['Required']	=	function (oForm, name ,isfile,asyfile){
	var oComp =oForm.elements[name];
	if(oComp==null)
		return false;
	if(typeof(isfile)!="undefined" && isfile=="asayfile" && typeof(oComp)!="undefined" && oComp.tagName=="SELECT" && typeof(asyfile)!="undefined"){
		for(var i=0;i<oComp.options.length;i++){
			if(oComp.options[i].value == "clear"){
				oComp.removeChild(oComp.options[i]);
			}else if((oComp.options[i].value !=null)&& (oComp.options[i].value.length>0)){
				return true;
			}
		}
	}else{
		if((oComp.value==null)||(oComp.value.length==0))
		return false;
	}
    var value=oComp.value.trim();
	var valid=true;
    if((value==null)||(value.length==0)) valid = false;
//    Validator.alert(oComp,valid);
    return valid;
}

/**
 * 验证掩码
 * @param oForm 表单
 * @param name：控件名称
 * @param mask	校验码
 */
Validator.Rules['Mask']		=	function (oForm, name, mask) {
	var oComp =oForm.elements[name]; 
	if(oComp==null)
		return false;
    var value=oComp.value.trim();
    if(typeof(value)=='undefined'){
    	value = oComp[0].value;
    }
	return new RegExp(mask,"ig").test(value);
}
/**
 * 验证最大值
 * @param oForm 表单
 * @param name：控件名称
 * @param max	最大值
 */
Validator.Rules['Max']	=	function (oForm, name, max){
	var oComp =oForm.elements[name]; 
    var value=oComp.value;
    if(typeof(value)=='undefined'){
    	value = oComp[0].value
    }
	var valid=true;
    if((value==null)||(value.length==0)) return true;
	var intValue=parseInt(value);
	if(isNaN(intValue))
		valid = false;
    else
    	valid = parseInt(value)<=parseInt(max);
//    Validator.alert(oComp,valid);
    return valid;
}
/**
 * 验证最小值
 * @param oForm 表单
 * @param name：控件名称
 * @param max	最小值
 */
Validator.Rules['Min']	=	function (oForm, name,min){
	var oComp =oForm.elements[name]; 
    var value=oComp.value;
    if(typeof(value)=='undefined'){
    	value = oComp[0].value;
    }
	var valid=true;
    if((value==null)||(value.length==0)) return true;
	var intValue=parseInt(value);
	if(isNaN(intValue))
		valid = false;
    else
    	valid = parseInt(value)>=parseInt(min);
//    Validator.alert(oComp,valid);
    return valid;
}
/**
 * 验证最大长度
 * @param oForm 表单
 * @param name：控件名称
 * @param max	最大值
 */
Validator.Rules['MaxLength']	=	function (oForm, name, max){
	var oComp =oForm.elements[name]; 
    var value=oComp.value;
    if(typeof(value)=='undefined'){
    	value = oComp[0].value
    }
	var valid=true;
    if((value==null)||(value.length==0)) 
     valid= true;
    else
     valid = value.length<=parseInt(max);
//    Validator.alert(oComp,valid);
    return valid;
}
/**
 * 验证最小长度
 * @param oForm 表单
 * @param name：控件名称
 * @param max	最小值
 */
Validator.Rules['MinLength']	=	function (oForm, name,min){
	var oComp =oForm.elements[name]; 
    var value=oComp.value;
    if(typeof(value)=='undefined'){
    	value = oComp[0].value;
    }
	var valid=true;
    //if((value==null)||(value.length==0))
     //valid = true;
    //else
	if(value!=null&&value!=""){
		valid = value.length>=parseInt(min);
	}
//    Validator.alert(oComp,valid);
    return valid;
}