<%@page contentType="text/html; charset=utf-8" %><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 2999 21:29:02 GMT" />
        <link rel="stylesheet" href="<html:rewrite page='/portal/style.css.jsp' module=''/>"/>
        <title>修改成功!</title>
<script type="text/javascript" language="javascript">
    function closeWindow(){
        var portlet = new Object();
        portlet.html = portletHTML.innerHTML;
        portlet.xml = '<'+'fulong:portlet id="'+ '<%= request.getParameter("portlet.window.owner") %>' +'" type="'+'<%= request.getParameter("portlet.type") %>'+'">';
        portlet.xml += portletXML.innerHTML;
        portlet.xml += '</'+'fulong:portlet>';
        window.parent.returnValue=portlet;
        window.parent.close();
    }
    </script>
    </head>
    <body background="menu" style="border:0px;padding:0 0 0 0;margin:0 0 0 0;background-color:menu">
        <table width="100%" border="0">
            <tr>
                <td align="center" height="300px">修改成功!</td>
            </tr>
            <tr>
                <td align="center"><input class="commonbut" type=button value="确定" onclick="closeWindow()"/>
                </td>
            </tr>
        </table>
        <xml id="portletXML" style="display: none;"><bean:write name="javax.portlet.preferences" filter="false"/></xml>
        <pre id="portletHTML" style="display:none">
        <%
        request.setAttribute("com.fulong.longcon.site.Channel",session.getAttribute("com.fulong.longcon.site.Channel"));
        request.setAttribute("com.fulong.longcon.site.SiteTemplate",session.getAttribute("com.fulong.longcon.site.SiteTemplate"));
        session.setAttribute("com.fulong.longcon.site.Channel",null);
        session.setAttribute("com.fulong.longcon.site.SiteTemplate",null);
        %>
        <fulong:portlet id='<%= request.getParameter("portlet.window.owner") %>' type='<%= request.getParameter("portlet.type") %>'>
        <logic:present name="javax.portlet.preferences" property="map" scope="session">
            <logic:iterate id="preference"  name="javax.portlet.preferences"  property="map" scope="session">
                <fulong:preference>
                    <fulong:name><bean:write name="preference" property="key"/></fulong:name>
                    <logic:notEqual value="java.lang.String"  name="preference" property="value.class.name">
                        <logic:iterate id="value" name="preference" property="value">
                            <fulong:value><bean:write name="value" ignore="true" filter="false"/></fulong:value>
                        </logic:iterate>
                    </logic:notEqual>
                    <logic:equal value="java.lang.String"  name="preference" property="value.class.name">
                        <fulong:value><bean:write  name="preference" property="value" ignore="true" filter="false"/></fulong:value>
                    </logic:equal>
                </fulong:preference>
            </logic:iterate>
        </logic:present>
    </fulong:portlet>
        </pre>        
    </body>
</html>
