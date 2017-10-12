<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="list_frame">
    <tiles:put name="tabbar">
        <tiles:insert page="/cms/tabbar.jsp" flush="false">
            <tiles:put name="categoryId"><bean:write name="categoryId" ignore="true"/></tiles:put>
            <tiles:put name="root"><bean:write name="category" property="nodeDefinition.protected"/></tiles:put>
        </tiles:insert>
    </tiles:put>
  <tiles:put name="listPanel">
    <table id="listTable" width="100%" cellpadding="2" cellspacing="0">
      <thead>
        <tr>
          <th width="20px">&nbsp;</th>
          <th width="80px">操作</th>
          <th>授权对象</th>
        </tr>
      </thead>
      <tbody>
        <tr id="manage">
          <td>1</td>
            <td>
              管理
            </td>
            <td>
              <logic:iterate id="member" name="managementAuth" property="members" indexId="m">
              <logic:present name="member">
                <cms:node name="member" propertyName="commonname"/>
              </logic:present>
              </logic:iterate>&nbsp;
            </td>
        </tr>
      <logic:iterate id="authorization" name="authorizations" indexId="index">
        <tr id="<bean:write name="authorization" property="permission.actions"/>">
          <td><%=index.intValue()+2%></td>
            <td>
              <bean:define id="commandID" name="authorization" property="permission.actions" type="String"/>
              <bean:write name="processDefinition" property='<%="activity("+commandID+").name"%>'/>
            </td>
            <td>
              <logic:iterate id="member" name="authorization" property="members" indexId="m">
              <logic:present name="member">
                <%if(m.intValue()!=0)out.print(",");%><cms:node name="member" propertyName="commonname"/>
              </logic:present>
              </logic:iterate>&nbsp;
            </td>
        </tr>
      </logic:iterate>
      </tbody>
    </table>
      <script language="javascript" type="text/Javascript">
        ListFrame.OnInit = function()
        {
          var sRow = document.getElementById('<%=request.getParameter("selectedCommandID")%>');
          var table = document.getElementById("listTable");
          if (sRow != null)
          {
            ListTable._OnClickRow(sRow);
            sRow.top = table.top + 10;
          }
        }
        ListTable.OnRowSelected = function(oRow)
        {
          var editButton=document.getElementById("editbut");
          ListFrame.ClickButton(editButton,ListTable.URLBuilder("editAuth.do","commandID")+"&categoryID=<bean:write name="categoryId" ignore="true"/>");
          ListFrame.DisableButton('editbut',false);
        }
        window.parent.main2.rows='285,*';
        ListFrame.SelectedTabID="btnAuthorization";
      </script>
    </tiles:put>
    <tiles:put name="toolbar">
      <button type="button" onclick=ListFrame.ClickButton(this,ListTable.URLBuilder("editAuth.do","commandID")+"&categoryID=<bean:write name="categoryId" ignore="true"/>") id="editbut">修改</button>
    </tiles:put>
</tiles:insert>
