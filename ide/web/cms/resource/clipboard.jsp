<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert page="/site/dialogFrame.jsp">
  <tiles:put name="title">剪贴板信息</tiles:put>
  <tiles:put name="content">
    <table width="100%" cellpadding="2" cellspacing="2" border="0">
      <tr>
        <td>
          <fieldset><legend>剪贴板信息</legend>
            用户点击“剪切”、“复制”按钮之后，系统将选择的文件放置到剪切板中。这里显示剪贴板里面所有的内容。
            <table width="100%" cellpadding="2" cellspacing="2" border="0">
            <logic:present cookie="clipboard">
              <bean:cookie id="clipboard" name="clipboard"/>
              <bean:cookie id="operationCookie" name="operation"/>
              <bean:define id="folders" name="clipboard" property="value" type="java.lang.String"/>
              <bean:define id="operation" name="operationCookie" property="value" type="java.lang.String"/>
              <tr>
                <td>当前是处于<% if("cut".equals(operation)){ %>剪切<%}else{%>复制<%}%>状态</td>
              </tr>
                <%
                String[] paths=folders.split("%3B");
                for(int i=0;i<paths.length;i++){
                  %>
                  <tr><td><%= paths[i] %></td></tr>
                  <%} %>
              <tr>
                <td><input type="button" class="commonbut" onclick="emptyClipboard()" value="清空剪贴板并关闭"/></td>
              </tr>
              </logic:present>

              <logic:notPresent cookie="clipboard">
                <tr><td height="80" align="center" valign="middle">当前的剪切板中没有任何文件。</td></tr>
              </logic:notPresent>
            </table>
            </fieldset>
          </td>
        </tr>
        <tr>
          <td><input type="button" class="commonbut" onclick="window.location='folderItems.do'" value="关闭"/>         </td>
        </tr>
      </table>


      <script type="text/javascript" language="javascript">
        var refreshed=false;
        function emptyClipboard(){
          deleteCookie("clipboard");
          deleteCookie("operation");

        }
        function closeMe(){
          window.returnValue=refreshed;
          window.close();
        }
        </script>
        </tiles:put>
      </tiles:insert>

