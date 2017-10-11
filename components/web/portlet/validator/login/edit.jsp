<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST">
        <html:hidden property="preference(form)" styleId="formName"/>
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                </select></td>
                <td><fieldset>
                    <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                    <tr>
                        <td class="formTitle">提示文字</td>
                        <td class="formComponent">
                            <html:text property="preference(tips)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">提示样式</td>
                        <td class="formComponent">
                            <html:text property="preference(tips-style)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(tips-style)'],'')">选择...</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">错误提示</td>
                        <td class="formComponent">
                            <html:text property="preference(error-tips)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">错误样式</td>
                        <td class="formComponent">
                            <html:text property="preference(error-style)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(error-style)'],'')">选择...</button>
                        </td>
                    </tr>
                    <tr>
                		<td class="formTitle"></td>
                		<td class="formComponent"><html:checkbox style="width:25px" property="preference(isAlert)" styleId="a1" value="true"/><label for="a1">以弹出方式进行错误提示</label></td>
              		</tr>
                </table>
            </fieldset>
            <div class="toolbar">
              <button type="submit">保存</button>
              <button type="button" onclick="window.parent.close()">取消</button>
            </div>
            </td>
        </tr></portlet:form>
</table>
