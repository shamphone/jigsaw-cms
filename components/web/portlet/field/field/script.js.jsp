<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
function doSelectCategory(oCategory,oContents,oFields){
var categoryId=oCategory.options[oCategory.selectedIndex].value;
if(categoryId.length==0)
	categoryId=selRepos.form.elements["defaultCategoryId"].value;
if(categoryId.length>0){
populate_select("/ide/cms/contents.do?category="+categoryId,oContents,true);
if(oContents.selectedIndex==0)
  addOption(oContents,"","使用缺省内容",0);
populate_select("/ide/cms/properties.do?category="+categoryId,oFields,true);
addOption(oFields,"","请选择...",0);
var getMethod = function (){
populate_fix_select("/ide/cms/fixProperties.do?category="+categoryId,oFields,false);
}
window.setTimeout(getMethod,4000);
}else{
removeAll(oContents);
removeAll(oFields);
}
}
function doSynFormats(oCategory,oField,oFormat){
var categoryId=oCategory.options[oCategory.selectedIndex].value;
if(categoryId.length==0)
	categoryId=oCategory.form.elements["defaultCategoryId"].value;
if(categoryId.length>0){
  if(oField.selectedIndex>0){
  var field=oField.options[oField.selectedIndex].value;
  populate_select("/ide/cms/formats.do?isFeild=isFeild&category="+categoryId+"&property="+field,oFormat,true);
  }
  }
}