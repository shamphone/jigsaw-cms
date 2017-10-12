<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="list_frame">
  <tiles:put name="title">数据字典</tiles:put>
   <tiles:putList name="pathes">
        <tiles:add>数据字典</tiles:add>
    </tiles:putList>
  <tiles:put name="listPanel">
    <table width="100%" border="1" cellpadding="2" cellspacing="0" id="listTable">
      <tr>
        <th width="20px"></th>
        <th width="156px">名称</th>
        <th width="200px">标识</th>
        <th width="220px">描述</th>
        <th scope="col">操作</th>
      </tr>
      <logic:iterate id="dict" name="dicts" indexId="index">
        <tr>
            <td width="20px"><%= (1+index.intValue()) %></td>
          <td width="156px"><a href="dictDetail.do?dictID=<bean:write name="dict" property="ID" ignore="true"/>"><bean:write name="dict" property="name" ignore="true"/></a></td>
          <td width="200px"><bean:write name="dict" property="value" ignore="true"/></td>
          <td width="220px"><bean:write name="dict" property="label" ignore="true"/></td>
          <td nowrap="nowrap"><a href="modifyDict.do?dictID=<bean:write name="dict" property="ID" ignore="true"/>">修改</a> <a href="dictDetail.do?dictID=<bean:write name="dict" property="ID" ignore="true"/>"> 设置字典项</a> <a href="javascript:del(<bean:write name="dict" property="ID" ignore="true"/>)">删除</a></td>
        </tr>
      </logic:iterate>
    </table>
    </div>
    <div class="operation">
      <button type="button" onclick="window.location='creatDict.do'" class="commonbut" id="new">新建</button>
    </div>
    <script language="JavaScript" type="text/Javascript">
      function del(dictID){
        if(confirm('确认删除这个字典表？'))
          document.location = "delDict.do?dictID="+dictID;
      }
    </script>
  </tiles:put>
</tiles:insert>
