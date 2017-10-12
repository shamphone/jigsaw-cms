<%--for Quota Present --%>
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
    <tiles:put name="categoryId"><bean:write name="categoryID" ignore="true"/></tiles:put>
    <tiles:put name="root"><bean:write name="nodeDefinition" property="protected"/></tiles:put>
  </tiles:insert>
</tiles:put>

<tiles:put name="listPanel">
  <table id="listTable" width="100%" cellpadding="2" cellspacing="0">
    <thead>
      <tr>
        <th width="20px">&nbsp;</th>
        <th width="320px">对象</th>
        <th>配额</th>
      </tr>
    </thead>
    <tbody>
        <logic:iterate id="priAndQuo" name="priAndQuos"  scope="request" indexId="Index">
        <tr id="<bean:write name="priAndQuo" property="principal.name"/>">
           <td width="20px"><%=Index.intValue()+1%></td>
          <td width="350px"><cms:node name="priAndQuo" property="principal" propertyName="commonname"/>&nbsp;</td>
          <td width="350px">
          <logic:equal value="noLimite" name="priAndQuo" property="quota.unit">不限</logic:equal>
          <logic:equal value="daily" name="priAndQuo" property="quota.unit">
            <bean:write name="priAndQuo" property="quota.count"/>&nbsp;条/日
          </logic:equal>
          <logic:equal value="weekly" name="priAndQuo" property="quota.unit">
            <bean:write name="priAndQuo" property="quota.count"/>&nbsp;条/周
          </logic:equal>
          <logic:equal value="monthly" name="priAndQuo" property="quota.unit">
            <bean:write name="priAndQuo" property="quota.count"/>&nbsp;条/月
          </logic:equal>
          <logic:equal value="quarterly" name="priAndQuo" property="quota.unit">
            <bean:write name="priAndQuo" property="quota.count"/>&nbsp;条/季度
          </logic:equal>
          <logic:equal value="yearly" name="priAndQuo" property="quota.unit">
            <bean:write name="priAndQuo" property="quota.count"/>&nbsp;条/年
          </logic:equal>
          </td>
         </tr>
            </logic:iterate>
        </table>
    </tbody>
  </table>

<script language="javascript" type="text/Javascript">
ListFrame.OnInit = function(){
          var sRow = document.getElementById('<%=request.getParameter("selectedPrincipalID")%>');
          var table = document.getElementById("listTable");
          if (sRow != null){
          ListTable._OnClickRow(sRow);
          sRow.top = table.top + 10;
          }
        }
        ListTable.OnRowSelected = function(oRow){
          var editbut=document.getElementById("editbut");
          ListFrame.ClickButton(editbut,ListTable.URLBuilder("gotoEditQuota.do","principalIDs")+"&categoryId=<bean:write name="categoryID" ignore="true"/>");
          ListFrame.DisableButton('editbut',false);
          ListFrame.DisableButton('delbut',false);
        }
        window.parent.main2.rows='285,*';
        ListFrame.SelectedTabID="btnCategoryQuota";

</script>
</tiles:put>
  <tiles:put name="toolbar">
      <button type="button" onclick=ListFrame.ClickButton(this,ListTable.URLBuilder("gotoInsertQuota.do")+"&categoryId=<bean:write name="categoryID" ignore="true"/>") id="addbut">添加</button>
      <button type="button"  disabled="disabled" onclick=ListFrame.ClickButton(this,ListTable.URLBuilder("gotoEditQuota.do","principalIDs")+"&categoryId=<bean:write name="categoryID" ignore="true"/>") id="editbut">修改</button>
      <button type="button"  disabled="disabled"  onclick=ListFrame.ClickButton(this,ListTable.URLBuilder("gotoDelQuota.do","principalIDs")+"&categoryId=<bean:write name="categoryID" ignore="true"/>") id="delbut">删除</button>
  </tiles:put>
</tiles:insert>
