<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">复制分类 </tiles:put>
  <tiles:put name="javascript">
    <script language="JavaScript" type="text/Javascript">
    function check(form){
      disableButton();
      if(form.name.value!=""){
        form.submit();
        
      }else{
        alert('请填写名称')
        enableButton();
      }
    }
    </script>
  </tiles:put>
  <tiles:put name="dialog">
    <html:form action="doCopyCategory.do">
      <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
      <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td>分类名称</td>
          </tr>
          <tr>
          <td><html:text property="name" size="39" maxlength="32" title="分类的显示名称，2-32个字符，可以使用中文"/></td>
        </tr>
        <tr>
        <logic:present name="noInherit">
          <td><html:checkbox  disabled="true" property="childrenCategory">包含子分类</html:checkbox></td>
          </logic:present>
          <logic:notPresent name="noInherit">
                    <td><html:checkbox property="childrenCategory">包含子分类</html:checkbox></td>
          </logic:notPresent>
        </tr>
      </table>
      <div class="operation">
                        <button type="button" onclick="check(this.form)" id="btnOk">保存</button>
                        <button type="button" onclick="window.close()" id="btnCancel">取消</button>
                    </div>
    </html:form>
  </tiles:put>
</tiles:insert>
