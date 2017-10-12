<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="list_frame">
  <tiles:put name="title">数据字典</tiles:put>
  <tiles:putList name="pathes">
    <tiles:add>数据字典</tiles:add>
    <tiles:add>字典项设置</tiles:add>
  </tiles:putList>
  <tiles:put name="tabbar">
    <tiles:insert page="/cms/dict/tabbar.jsp" flush="false">
      <tiles:put name="dictId"><bean:write name="dictId"/></tiles:put>
    </tiles:insert>
  </tiles:put>
  <tiles:put name="listPanel">
    <table id="listTable" width="100%" cellpadding="2" cellspacing="0" >
      <thead>
        <tr>
          <logic:iterate id="pd" name="propertyDefinitions">
            <th><bean:write name="pd" property="name"/></th>
          </logic:iterate>
        </tr>
      </thead>
      <tbody>
        <logic:iterate id="dict" name="dicts">
          <tr id="<bean:write name="dict" property="ID" ignore="true"/>" parentId="<bean:write name="dict" property="parentEntry.ID" ignore="true"/>" level="<bean:write name="dict" property="level"/>">
          <logic:iterate id="pd" name="propertyDefinitions">
            <bean:define id="pdId" name="pd" property="ID" type="String"/>
            <td><cms:node name="dict" propertyName="<%=pdId %>" ignore="true"/>&nbsp;</td>
          </logic:iterate>
          </tr>
        </logic:iterate>
      </tbody>
    </table>
   </tiles:put>
   <tiles:put name="pager">
     <fulong:pager />
   </tiles:put>
   <tiles:put name="toolbar">
     <button type="button" name="createButton" onclick="doCreate()" id="new">新建</button>
     <button type="button" name="editButton" onclick="doEdit()" id="edit" disabled="disabled">修改</button>
     <button type="button" name="deleteButton" onclick="doDelete()" id="delete" disabled="disabled">删除</button>
     <button type="button" name="upLevelButton" onclick="doLevelUp()" id="upLevel" disabled="disabled">升级</button>
     <button type="button" name="downLevelButton" onclick="doLevelDown()" id="downLevel" disabled="disabled">降级</button>
     <button type="button" name="upButton" onclick="doUp()" id="up" disabled="disabled">上移</button>
     <button type="button" name="downButton" onclick="doDown()" id="down" disabled="disabled">下移</button>
   </tiles:put>
   <tiles:put name="javascript">
     <script language="JavaScript" type="text/Javascript">
       var oTable;
       var dictId = '<bean:write name="dictId"/>';
       var separator = ".";
       ListTable.OnRowSelected = function(oRow){
         if (ListTable.SelectedRows.length > 1) {
           document.all("createButton").disabled = true;
           document.all("upLevelButton").disabled = true;
           document.all("downLevelButton").disabled = true;
           document.all("upButton").disabled = true;
           document.all("downButton").disabled = true;
         } else {
           document.all("createButton").disabled = false;
           document.all("upLevelButton").disabled = !canLevelUp(oRow);
           document.all("downLevelButton").disabled = !canLevelDown(oRow);
           document.all("upButton").disabled = !canUp(oRow);
           document.all("downButton").disabled = !canDown(oRow);
         }
         if (document.getElementById("listTable").rows.length > ListTable.SelectedRows.length + 1)
           document.all("deleteButton").disabled = false;
         else
           document.all("deleteButton").disabled = true;
         document.all("editButton").disabled = false;
         doEdit();
       }

       ListFrame.OnInit = function(){
         oTable = document.getElementById("listTable");
         var selectedIdSeries = '<bean:write name="selectedIds" ignore="true"/>';
         if (selectedIdSeries.trim() == "")
           return;
         var selectedIds = selectedIdSeries.split(",");
         var topRow = null;
         for (var i=0; i<selectedIds.length; i++) {
           var sRow = document.getElementById(selectedIds[i]);
           if (sRow != null){
             ListTable._OnClickRow(sRow, true);
             if (i == 0)
             topRow = sRow;
           }
         }
         if (topRow != null){
           topRow.scrollIntoView(false);
         }
       }

       function doCreate() {
         var url = "createDictDetails.do?parentId=";
         if (ListTable.SelectedRows.length == 0)
           url += "<bean:write name='dictId' ignore='true' />";
         else
           url += ListTable.SelectedRow.parentId;
         ListFrame.ClickButton(document.all("createButton"), url);
       }

       function doEdit() {
         var url = "editDictDetails.do?id=" + ListTable.SelectedRow.id + "&dictId=" + ListTable.SelectedRow.parentId;
         ListFrame.ClickButton(document.all("editButton"), url);
       }

       function doDelete() {
         var params = "";
         for (var i=0; i<ListTable.SelectedRows.length; i++)
           params += "&id=" + ListTable.SelectedRows[i].id;
         var url = "deleteDictDetailsConfirm.do?dictId=" + ListTable.SelectedRow.parentId + params;
         ListFrame.ClickButton(document.all("deleteButton"), url);
       }

       function doLevelUp() {
         document.location = 'upDictDetail.do?rootId=<bean:write name="dictId"/>&dictId=' + ListTable.SelectedRow.id;
       }

       function doLevelDown() {
         document.location = 'downDictDetail.do?rootId=<bean:write name="dictId"/>&dictId=' + ListTable.SelectedRow.id;
       }

       function doUp() {
         document.location = 'changeDictDetail.do?rootId=<bean:write name="dictId"/>&dictId=' + ListTable.SelectedRow.id + '&orient=up';
       }

       function doDown() {
         document.location = 'changeDictDetail.do?rootId=<bean:write name="dictId"/>&dictId=' + ListTable.SelectedRow.id + '&orient=down';
       }

       function canUp(oRow) {
         return findLastBrotherRow(oRow) != null;
       }

       function canDown(oRow) {
         return findNextBrotherRow(oRow) != null;;
       }

       function canLevelUp(oRow) {
         return document.getElementById(oRow.parentId) != null;
       }

       function canLevelDown(oRow) {
         return findLastBrotherRow(oRow) != null;
       }

       function findLastBrotherRow(oRow) {
         var lastBrotherRow = null;
         if (oRow.rowIndex > 1) {
           var table = document.getElementById("listTable");
           for (var i=oRow.rowIndex-1; i>0; i--) {
             var lastRow = table.rows[i];
             if (lastRow.level < oRow.level)
             break;
             if (lastRow.parentId == oRow.parentId) {
               lastBrotherRow = lastRow;
               break;
             }
           }
         }
         return lastBrotherRow;
       }

       function findNextBrotherRow(oRow) {
         var nextBrotherRow = null;
         var table = document.getElementById("listTable");
         for (var i=oRow.rowIndex+1; i<table.rows.length; i++) {
           var nextRow = table.rows[i];
           if (nextRow.level < oRow.level)
             break;
           if (nextRow.parentId == oRow.parentId) {
             nextBrotherRow = nextRow;
             break;
           }
         }
         return nextBrotherRow;
       }
       /*
        *
        */
       function getFirstRow(table) {
         var oRow = null;
         var tHeadRowCount = getTHeadRowCount(table);
         var tFootRowCount = getTFootRowCount(table);
         if (tHeadRowCount + tFootRowCount < table.rows.length)
         oRow = table.rows[tHeadRowCount];
         return oRow;
       }
       /*
        *
        */
       function getFinalRow(table) {
         var oRow = null;
         var tHeadRowCount = getTHeadRowCount(table);
         var tFootRowCount = getTFootRowCount(table);
         if (tHeadRowCount + tFootRowCount < table.rows.length)
         oRow = table.rows[table.rows.length - tFootRowCount - 1];
         return oRow;
       }
       window.parent.main2.rows='200,*';
     </script>
   </tiles:put>
</tiles:insert>
