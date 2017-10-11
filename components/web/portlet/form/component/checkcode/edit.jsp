<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
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
                        <td class="formTitle">图片高度</td>
                        <td class="formComponent">
                            <html:text property="preference(height)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">图片宽度</td>
                        <td class="formComponent">
                            <html:text property="preference(width)"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">输入框样式</td>
                        <td class="formComponent">
                            <html:text property="preference(inputStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(inputStyle)'],'')">选择...</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle">列表样式</td>
                        <td class="formComponent">
                            <html:text property="preference(ULStyle)"/><button type="button" class="commonbut" onclick="doSelectStyle(this.form.elements['preference(ULStyle)'],'')">选择...</button>
                        </td>
                    </tr>
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
                </table>
            </fieldset>
            <div class="toolbar">
              <button type="submit">保存</button>
              <button type="button" onclick="window.parent.close()">取消</button>
            </div>
            </td>
        </tr></portlet:form>
</table>
