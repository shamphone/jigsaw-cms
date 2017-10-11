<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fp"%>
<fp:form action="submit" enctype="multipart/form-data" method="POST" styleId='<%=(String)request.getAttribute("javax.portlet.id")%>'>
    <input type="hidden" name="category" value="<bean:write name='category' property='ID'/>"/>
    <table border="0" cellpadding="2" cellspacing="2" class="<bean:write name="preferences" property="value(table-style)"/>">
        <logic:iterate id="property" name="fields">
            <bean:define id="propID" name="property" property="ID" type="java.lang.String" />
            <tr>
                <td>
                    <span  class="<bean:write name="preferences" property="value(field-style)"/>"> <bean:write name="property" property="name"/></span>
                    <logic:present name='<%= "errors."+propID %>'><span class="<bean:write name="preferences" property="value(errors-style)"/>" id="<bean:write name="property" property="ID"/>.tips"><bean:write name="property" property="description" ignore="true"/> </span></logic:present>
                    <logic:notPresent name='<%= "errors."+propID %>'><span class="<bean:write name="preferences" property="value(tips-style)"/>" id="<bean:write name="property" property="ID"/>.tips"><bean:write name="property" property="description" ignore="true"/> </span></logic:notPresent>
                </td>
            </tr>
            <tr>
                <td class="<bean:write name="preferences" property="value(component-style)"/>">
                    <cms:propertyEditor definition="property" propValues='<%= propID+".values" %>'/>
                    </td>
                </tr>
            </logic:iterate>
        </table>
        <div class="operation">
            <input type="submit" value="<bean:write name="preferences" property="value(button-text)"/>" class="<bean:write name="preferences" property="value(button-style)"/>"/>
        </div>
    </fp:form>
<cms:nodeValidator name="category" property="nodeDefinition" formName='<%=(String)request.getAttribute("javax.portlet.id")%>'>
    <script type="text/Javascript" language="Javascript">
        //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
        function validate<%=(String)request.getAttribute("javax.portlet.id")%>Success(property){
            //    document.all(property+".tips").innerHTML="填写正确";
            document.all(property+".tips").className="<bean:write name="preferences" property="value(tips-style)"/>";
        }
        //在应用时，必须包含这两个脚本文件，用来处理对属性property验证成功或者失败时的回调
        function validate<%=(String)request.getAttribute("javax.portlet.id")%>Failed(property){
            document.all(property+".tips").className="<bean:write name="preferences" property="value(errors-style)"/>";

        }
        </script>
</cms:nodeValidator>
