<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/dialogFrame.jsp">
  <tiles:put name="title">添加样式</tiles:put>
  <tiles:putList name="stylesheets">
    <tiles:add><html:rewrite page="/style.css"/></tiles:add>
  </tiles:putList>
  <tiles:put name="content">
    <table cellpadding="2" cellspacing="2" border="0" width="200px">
      <html:form  action="/saveRuleDlg.do" method="post">
        <html:hidden property="cssID" name="cssRuleForm"/>
        <html:hidden property="ruleSource" value=""/>
        <tr>
          <td class="formTitle">选择器类型</td>
          <td class="formComponent">
            <html:select property="type">
              <html:option value="#">ID选择器(#)</html:option>
              <html:option value=".">类选择器(.)</html:option>
              <html:option value="">标签选择器</html:option>
              <html:option value="">其他选择器</html:option>
            </html:select>
          </td>
        </tr>
        <tr>
          <td class="formTitle">选择器名称</td>
          <td class="formComponent"><html:text property="selector" /></td>
        </tr>
        <tr>
          <td colspan="2" class="operation">
            <input type="button" class="commonbut" onclick="save(this)" value="确定"/>
            <input type="button" class="commonbut" onclick="window.close()" value="返回"/>
          </td>
        </tr>
      </html:form>
    </table>
  </tiles:put>
</tiles:insert>
<html:javascript formName="selectorForm" bundle="css"/>
<script language="javascript" type="text/Javascript">
  function save(submitter){
    if(validateSelectorForm(submitter.form)){
      submitter.disabled=true;
      submitter.form.submit();
    }
  }
  </script>
