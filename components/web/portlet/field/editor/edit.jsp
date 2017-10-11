<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<script language="javascript" src="/ide/cms/portlet/editor/script.js" type="text/javascript" ></script>
<style type="text/css">
        .searchSelectors{ float:left; width:130px;}
        .searchSelectors select{ width:130px; height:250px;}
        .searchTools{ float:left; width:60px;}
</style>
<portlet:form action="save" onsubmit="do_submit(this)">
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td class="pannelDiv">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected"><bean:message key="com.fulong.cms.portlet.editor.edit.option0.baseSet"/></option>
                    <option value="1"><bean:message key="com.fulong.cms.portlet.editor.edit.option2.formAttrubite"/></option>
                </select>
            </td>
            <td><fieldset>
                <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle1.contentSort"/></td>
                        <td class="formComponent" valign="top">
                            <html:select property="preference(category)" onchange="doSelectCategory(this,this.form.elements['preferences(canditions)'])">
                            <logic:iterate id="node" name="categories">
                                <bean:define id="nodeID" name="node" property="node.ID" type="java.lang.String"/>
                                <html:option value="<%= nodeID %>"><fulong:repeater name="node" property="depth">&nbsp;&nbsp;</fulong:repeater> <bean:write name="node" property="node.displayName" ignore="true"/></html:option>
                            </logic:iterate>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle2.resultCommit"/></td>
                    <td class="formComponent" valign="top">
                            <html:select property="preference(result-channel)">
                  <logic:iterate id="node" name="channels">
                    <bean:define id="nodeID" name="node" property="node.ID" type="java.lang.String"/>
                    <html:option value="<%= nodeID %>"><fulong:repeater name="node" property="depth">&nbsp;&nbsp;</fulong:repeater> <bean:write name="node" property="node.displayName" ignore="true"/></html:option>
                  </logic:iterate>
                                </html:select>
</td>
                    </tr>
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle3.button-text"/></td>
                        <td class="formComponent" valign="top"><html:text property="preference(button-text)"/></td>
                    </tr>
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle4.button-style"/> </td>
                        <td class="formComponent" valign="top"><html:text property="preference(button-style)"/><input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.formTitle5.button"/> 'onclick="selectStyle(this.form.elements['preference(button-style)'])"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle5.list-style"/> </td>
                        <td class="formComponent" valign="top"><html:text property="preference(list-style)"/><input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.formTitle5.button"/> ' onclick="selectStyle(this.form.elements['preference(list-style)'])"/> </td>
                    </tr>
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle6.field-style"/> </td>
                        <td class="formComponent" valign="top"><html:text property="preference(field-style)"/><input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.formTitle5.button"/> ' onclick="selectStyle(this.form.elements['preference(field-style)'])"/> </td>
                    </tr>
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle7.component-style"/> </td>
                        <td class="formComponent" valign="top"><html:text property="preference(component-style)"/><input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.formTitle5.button"/> ' onclick="selectStyle(this.form.elements['preference(component-style)'])"/> </td>
                    </tr>
                    <tr>
                        <td class="formTitle" valign="top"><bean:message key="com.fulong.cms.portlet.editor.edit.formTitle8.tips-style"/> </td>
                        <td class="formComponent" valign="top"><html:text property="preference(tips-style)"/><input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.formTitle5.button"/> ' onclick="selectStyle(this.form.elements['preference(tips-style)'])"/> </td>
                    </tr>
                </table>
            </fieldset><fieldset style="display:none">
                    <table width="100%" cellpadding="2" cellspacing="2" border="0">
                        <tr>
                            <td colspan="3"><bean:message key="com.fulong.cms.portlet.editor.edit.fieldset1.showSeaech"/>：</td>
                        </tr>
                        <tr>
                            <td class="searchSelectors"><html:select property="preferences(canditions)" multiple="true" >
                                <logic:present name="fields">
                                    <html:options collection="fields" property="ID" labelProperty="name"/>
                                </logic:present>
                            </html:select>
                        </td>
                        <td valign="middle">
                            <input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.fieldSet1.button1.add"/>--&gt' onclick="nav_move2(this.form.elements['preferences(canditions)'],this.form.elements['preferences(fields)'])"/>
                           <br /><br />
                            <input type="button" value='&lt-- <bean:message key="com.fulong.cms.portlet.editor.edit.fieldSet1.button2.delete"/>'onclick="nav_removeSelected(this.form.elements['preferences(fields)'])"/>
                        </td>
                        <td class="searchSelectors">
                            <html:select property="preferences(fields)" multiple="true">
                            <logic:present name="selectedFields">
                                <html:options collection="selectedFields" property="ID" labelProperty="name"/>
                            </logic:present>
                        </html:select></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td><input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.fieldset1.button3.up"/> ' onclick="nav_moveUpSelected(this.form.elements['preferences(fields)'])"/>
                            <input type="button" value='<bean:message key="com.fulong.cms.portlet.editor.edit.fieldset1.button4.down"/> ' onclick="nav_moveDownSelected(this.form.elements['preferences(fields)'])"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div class="toolbar">
                <input type="submit" value='<bean:message key="com.fulong.cms.portlet.editor.edit.toolbar.submit.save"/> '/>
            </div>
        </td>
    </tr>
</table>
</portlet:form>
