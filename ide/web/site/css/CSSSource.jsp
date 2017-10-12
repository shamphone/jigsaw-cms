<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/site/css/framework.jsp">
  <tiles:put name="content">
    <html:javascript formName="cssSourceForm" bundle="css"/>
    <div class="block">
      <h2><bean:write name='cssStyleSheet' property="name"/></h2>
      <html:form action="/updateCSS.do"  method="post">
 <p class="operation">
            <input type="button" class="commonbut"  onclick="window.location='css.do?id=<fulong:encode name="cssStyleSheet" property="contextPath"/>'" value="样式编辑器"/>
            <input type="button" disabled="disabled" class="commonbut" onclick="viewSource(this)" value="编辑源代码"/>
             <input type="button" class="commonbut" onclick="save(this)" value="保存"/>
            <input type="button" disabled="disabled" class="commonbut"   onclick="insert(this)" value="添加新样式"/>
            <input type="button"  disabled="disabled" class="commonbut"  onclick="del(this)" value="删除选择样式"/>
          </p>
        <html:hidden property="cssID" name="cssForm"/>
        <html:textarea property="source" cols="90" rows="30" name="cssForm" >
        </html:textarea>

          </html:form>
        </div>
      </tiles:put>
    </tiles:insert>
<script language="javascript" type="text/Javascript">
 function save(submitter){
     if(validateCssSourceForm(submitter.form)){
      submitter.disabled=true;
      submitter.form.submit();
    }

 }
 function return2CSS(submitter){
    window.location="<html:rewrite page='/css.do'/>?id=<fulong:encode name='cssForm' property='cssID'/>";
 }
  </script>

