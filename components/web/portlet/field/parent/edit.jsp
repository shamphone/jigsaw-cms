<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <input type="hidden" name="defaultCategoryId" value="<bean:write name='defaultCategory' property="ID" ignore="true"/>"/>
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
          <option value="1">重复内容</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle" valign="top">内容来源</td>
              <td class="formComponent" valign="top">
                <table border=0 cellpadding=0 cellspacing=0>
                  <tr>
                    <td>
                      <html:radio style="width:25px" styleId="b1" property="preference(contentType)" value="default">
                        <label for="b1">使用URL参数指定内容</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b2" property="preference(contentType)" value="user"><label for="b2">使用当前登录用户</label></html:radio>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <html:radio style="width:25px" styleId="b3" property="preference(contentType)" value="site"><label for="b3">使用当前网站所属用户</label></html:radio>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td nowrap="nowrap">展示样式</td>
                <td class="formComponent" nowrap="nowrap">
                	<html:text property="preference(style)"/>
                	<button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(style)'],'')">选择...</button>
                </td>
              </tr>
            </table>
          </fieldset>
            <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formTitle">重复内容</td>
                  <html:hidden property="preference(category)"/>
                  <html:hidden property="preference(contextName)"/>
                  <td class="formComponent" width="80%"><button type="button" onclick="editClipFile('<bean:write name="path"/>',form.elements['preference(category)'],document.getElementById('editorFrame'))">修改...</button></td>
                </tr>
                <tr>
                  <td colspan="2">
                    <iframe scrolling="yes" marginheight="0" marginwidth="0" frameborder="0" src='<bean:write name="clipPath"/>?javax.portlet.page.mode=view' width="350px" height="280px" id="editorFrame">                </iframe>
                  </td>
                </tr>
              </table>
            </fieldset>
          <div class="toolbar">
                <button type="button" onclick="save(this.form)">保存</button>
                <button type="button" onclick="window.parent.close()"/>取消</button>
          </div>
        </td>
      </tr>
    </portlet:form>
  </table>
  <script type="text/Javascript" language="Javascript">
  document.getElementsByName('preference(contextName)')[0].value=window.parent.dialogArguments.template.name;
  function save(form){
	  form.submit();
  }
    </script>
  <script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
