<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="content_frame">
  <tiles:put name="title">数据字典</tiles:put>
      <tiles:putList name="pathes">
        <tiles:add>数据字典</tiles:add>
        <tiles:add>新建字典</tiles:add>
    </tiles:putList>
  <tiles:put name="body">
    <html:form action="insertDict.do">
    <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0" border="1">
      <tr>
        <th scope="row">显示名称</th>
        <td>
          <bean:define id="defaultName" type="java.lang.String">数据字典<bean:write name="prefix"/></bean:define>
          <html:text property="name" size="32" maxlength="32" value='<%= "数据字典"+java.util.Calendar.getInstance().get(java.util.Calendar.MILLISECOND) %>'/>
          <span class="formTips"><span class="emphasize">*</span>数据字典的显示名称，2-32个字符，可以使用中文。</span>
        </td>
      </tr>
      <tr>
        <th scope="row">简要描述</th>
        <td><div class="formTips">请使用1000个以内字符简要描述这个数据字典的作用。</div></td>
      </tr>
      <tr>
        <th scope="row"></th>
        <td>
          <html:textarea property="description" cols="80" rows="10"></html:textarea>
        </td>
      </tr>
      <!--tr>
        <th scope="row">文件导入</th>
        <td><input name="file" type="file" size="60" />
          <div class="tips">如果存在包含该字典项的Excel文件，请在这里导入这个文件。</div>
        </td>
      </tr-->
    </table>
    <div class="operation">
      <button type="button" onclick="check(this.form)" class="commonbut" id="tijiao">保存</button>
    </div>
    </html:form>
    <html:javascript formName="dictForm"/>
    <script language="JavaScript" type="text/Javascript">
      function check(form){
        if(validateDictForm(form))
        form.submit();
      }
      var msg = '<html:messages id="message" message="true"><bean:write name="message"/></html:messages>';
      if(msg!='')
      alert(msg);
    </script>
  </tiles:put>
</tiles:insert>
