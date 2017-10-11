<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST" onsubmit="doSubmit(this)">
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                </select></td>
                <td><fieldset>
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
                            <td class="formTitle">关联活动</td>
                            <td class="formComponent"><html:hidden property="preference(process)"/><html:hidden property="preference(activity)"/>
                            <input type="text" name="activityName" /><button type="button" class="commonbut" onclick="doSelectActivity(this.form.elements['preference(process)'],this.form.elements['preference(activity)'],this.form.elements['activityName'])">选择...</button>
                            </td>
                        </tr>
                </table>
                </fieldset>
                <div class="toolbar">
          		<button type="submit">保存</button>
          		<button type="button" onclick="window.parent.close()">取消</button></div></td>
                </tr></portlet:form>
            </table>
            <script type="text/Javascript" src='/ide/common/script/portlet.js'></script>
            <script type="text/javascript" language="javascript">
            function doSubmit(oForm){
              return true;
            }
            </script>
