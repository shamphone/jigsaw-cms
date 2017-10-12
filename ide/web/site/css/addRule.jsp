<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/site/css/framework.jsp">
  <tiles:put name="content">
<html:javascript formName="selectorForm" bundle="css"/>
<html:form  action="/saveRule.do" method="post">
  <html:hidden property="cssID" name="cssRuleForm"/>
<div class="block">
  <h2><bean:write name="cssRuleForm" property="cssID"/></h2>
  <h2>选择器名称</h2>
<ul>
    <li>选择器类型:
      <ul>
     <li><html:radio property="type"   value="#"/>ID选择器，为ID属性等于指定值的元素设置属性，请在“选择器名称”中输入ID值。</li>
     <li><html:radio property="type"   value="."/>类选择器，为class属性等于指定值的元素设置属性，请在“选择器名称”中输入类值。</li>
     <li><html:radio property="type"   value=""/>标签选择器，指定的标签设置属性，请在“选择器名称”中输入标签值。</li>
     <li><html:radio property="type"   value="#"/>其他选择器，请在“选择器名称”中输入自定义的选择器值</li>
        </ul>
        </li>
   <li>选择器名称:<html:text property="selector" /></li>
        </ul>
</div>
<div class="block" id="sourceCode" style="display:none">
  <textarea name="ruleSource" id="ruleSource" cols="80" rows="30">{}</textarea>
</div>
<p class="operation">
  <input type="button" class="commonbut" onclick="save(this)" value="下一步"/>
  </p>
</html:form>
<script language="javascript" type="text/Javascript">
  function save(submitter){
    if(validateSelectorForm(submitter.form)){
      submitter.disabled=true;
      submitter.form.submit();
    }
  }
</script>
</tiles:put>
 </tiles:insert>
