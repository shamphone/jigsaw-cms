<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
    <tiles:put name="dialog">
        <html:form action="insertCategory.do">
            <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td align="left" height="20px">分类名称</td></tr>
                        <tr>
                        <td align="left" height="20px"><bean:define id="categoryName" type="java.lang.String">新内容分类</bean:define>
                            <html:text property="name" size="39" maxlength="32" value="<%= categoryName %>" title="分类的显示名称，2-32个字符，可以使用中文。"/>
                            </td>
                        </tr>
                    </table>
                    <div class="operation">
                        <button type="button" onclick="check(this.form)" id="btnOk">保存</button>
                        <button type="button" onclick="window.close()" id="btnCancel">取消</button>
                    </div>
                </html:form>
            </tiles:put>
            <tiles:put name="javascript">
                <html:javascript formName="categoryForm"/>
                <script language="JavaScript" type="text/Javascript">
                    function check(form){
                        disableButton();
                        if(validateMaxLength(form)){
                            form.submit();
                        }else{
                            enableButton();
                        }
                    }
                 
                    </script>
                    </tiles:put>
                </tiles:insert>
