<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
    <tiles:put name="title">高级文字编辑器</tiles:put>
    <tiles:put name="javascript">
        <script type="text/javascript" language="javascript">
            window.onload = function(){
                if (window.parent.dialogArguments)
                document.forms[0]['richtext'].value = window.parent.dialogArguments.text;
            }
            </script>
            </tiles:put>
            <tiles:put name="dialog">
                <form action="#">
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                            <td>
                                <input type="hidden" name="richtext"/>
                                <iframe name="rt" src="<html:rewrite module="/common" page="/editor/portletUsedEditor.jsp"/>?InstanceName=richtext" width="500" height="200" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
                            </td>
                        </tr>
                        <tr>
                            <td align="right" valign="middle">
                                <button onclick="window.rt.UpdateLinkedField(); window.returnValue=this.form['richtext'].value; window.close();">确定</button>&nbsp;
                                <button onclick="window.close();">取消</button>&nbsp;
                            </td>
                        </tr>
                    </table>
                </form>
            </tiles:put>
            </tiles:insert>
