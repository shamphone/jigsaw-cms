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
        <tiles:add>
          <logic:present name="dict">
            <bean:write name="dict" property="name" ignore="true"/>
          </logic:present>
        </tiles:add>
        <tiles:add>修改字典</tiles:add>
    </tiles:putList>
  <tiles:put name="body">
    <html:form action="updateDict.do">
      <input type="hidden" name="dictID" value="<bean:write name="dict" property="ID" ignore="true"/>"/>
      <html:hidden property="identify"/>
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0" border="1">
      <tr>
        <th scope="row">标 识 符</th>
        <td><bean:write name="dictForm" property="identify"/>
        </td>
      </tr>
        <tr>
          <th scope="row">显示名称</th>
          <td>
            <html:text property="name" size="32" maxlength="32"/>
            <span class="formTips"><span class="emphasize">*</span>数据字典的显示名称，2-32个字符，可以使用中文。</span>
          </td>
        </tr>
        <tr>
          <th scope="row">简要描述</th>
          <td>
            <div class="formTips">请使用1000个以内字符简要描述这个数据字典的作用。</div>
          </td>
        </tr>
        <tr>
          <th scope="row"></th>
          <td>
            <html:textarea property="description" cols="80" rows="10"></html:textarea>
          </td>
        </tr>
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
    </script>
  </tiles:put>
</tiles:insert>
