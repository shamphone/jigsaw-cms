<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/dialogFrame.jsp">
  <tiles:put name="title">增加样式文件</tiles:put>
  <tiles:putList name="stylesheets">
    <tiles:add><html:rewrite page="/style.css"/></tiles:add>
  </tiles:putList>
<tiles:put name="content">
<html:form action="/saveCSSDlg.do" enctype="multipart/form-data" method="POST">
  <table cellpadding="2" cellspacing="2" border="0">
    <tr>
      <td><input type="radio" name="create" value="true"/></td>
      <td>创建空样式文件：</td>
      <td><html:text property="cssID" onclick="form.create[0].checked=true"/></td>
    </tr>
    <tr>
      <td><input type="radio" name="create" value="false"/></td>
      <td>上传样式文件：</td>
      <td><html:file property="file" onclick="form.create[1].checked=true"/></td>
    </tr>
    <tr>
      <td></td>
      <td>
        <input type="button" class="commonbut" onclick="save(this)" value="确认"/>
        <input type="button" class="commonbut" onclick="window.location='indexDlg.do'" value="返回"/>
      </td>
    </tr>
  </table>
</html:form>
</tiles:put>
</tiles:insert>
<html:javascript formName="cssForm" bundle="css"/>
<script language="javascript" type="text/Javascript">
  function save(submitter){
    if(validateCssForm(submitter.form)){
      submitter.disabled=true;
      submitter.form.submit();
    }
  }
</script>
