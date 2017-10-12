<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="default_main_frame">
 <tiles:putList name="pathes">
        <tiles:add>内容库设计</tiles:add>
        <tiles:add>内容分类</tiles:add>
        <logic:present name="parentCategories"><logic:iterate id="parentCategory" name="parentCategories">
        <tiles:add>
        <a href="categoryList.do?categoryID=<bean:write name="parentCategory" property="ID" ignore="true"/>">
        <bean:write name="parentCategory" property="displayName" ignore="true"/></a>
        </tiles:add>
        </logic:iterate></logic:present>
        <tiles:add>设置共享</tiles:add>
      </tiles:putList>
    <tiles:put name="body">
    <table width="100%" border="1" cellpadding="2" cellspacing="0" class="tableClass">
      <tr>
        <th width="20px"></th>
        <th width="450px">网站</th>
        <th width="80px">下载</th>
        <th width="80px">投稿</th>
        <th>操作</th>
      </tr>
    </table>
    <div  class="contentBlock">
      <table id="table1" width="98%" border="1" cellpadding="2" cellspacing="0" class="tableClass">
        <logic:iterate id="shareSite" name="shareSites" indexId="index">
          <logic:present name="shareSite">
            <bean:define id="shareSite1" name="shareSite" type="String"/>
            <tr>
              <td width="18px"><%= (1+index.intValue()) %></td>
              <td width="450px"><bean:write name="shareSite1" ignore="true"/></td>
              <bean:define id="readable" name="category" property='<%="canRead("+shareSite1+")"%>'/>
              <bean:define id="writeable" name="category" property='<%="canWrite("+shareSite1+")"%>'/>
              <td width="80px">
                <logic:equal value="true" name="readable">
                  Y
                </logic:equal>
                <logic:equal value="false" name="readable">
                  X
                </logic:equal>
              </td>
              <td width="80px">
                <logic:equal value="true" name="writeable">
                  Y
                </logic:equal>
                <logic:equal value="false" name="writeable">
                  X
                </logic:equal>
              </td>
              <td><a href="#" onclick="del()">删除</a></td>
            </tr>
          </logic:present>
        </logic:iterate>
      </table>
    </div>

    <table width="100%" border="1" cellpadding="2" cellspacing="0" class="tableClass">
      <form action="">
        <tr>
          <th width="20px"></th>
          <td width="450px"><input name="siteName" type=text size="40" value="http://"/></td>
          <td width="80px"><input type=checkbox name="share" value="download" /> 下载</td>
          <td width="80px"><input type=checkbox name="share" value="post" /> 投稿</td>
          <td><button type="button" onclick="insert()" class="commonbut" id="new">添加</button></td>
        </tr>
      </form>
    </table>

    <div class="operation">
      <button type="button" onclick="window.location='javascript:history.go(-1)'" class="commonbut" id="back">返回</button>
    </div>
    <script language="JavaScript" type="text/Javascript">
      function insert(){
        var curTR;
        var siteName = document.getElementById("siteName").value;
        var form1 = document.forms[0];
        var auth = form1.share;
        var readable = "false";
        var writeable = "false";

        if(auth[0].checked){
          readable = "true";
        }
        if(auth[1].checked){
          writeable = "true";
        }
        if(siteName!=null&&siteName!=""&&siteName!="http://"){
          if(checkExist()){
              curTR = table1.insertRow();
              var req = getXMLHttpRequest();
              var callback=function(){
                if ((req.readyState==4)&&(req.status==200)){
                  var data=req.responseText;
                  if(data!=null&&data!=""){
                    curTR.setAttribute("id",data);
                  }
                }
              }
              var url = "insertShare.do?categoryID="+<bean:write name="categoryID"/>+"&siteName="+siteName+"&readable="+readable+"&writeable="+writeable;
              sendRequest(req, url, callback);
              var newTd0 = curTR.insertCell();
              newTd0.innerText = table1.rows.length;
              newTd0.width = "20px";
              var newTd1 = curTR.insertCell();
              newTd1.innerText = siteName;
              newTd1.width = "450px";
              var newTd2 = curTR.insertCell();
              if(auth[0].checked){
                newTd2.innerText = "Y";
              }else{
                newTd2.innerText = "X";
              }
              newTd2.width = "80px";
              var newTd3 = curTR.insertCell();
              if(auth[1].checked){
                newTd3.innerText = "Y";
              }else{
                newTd3.innerText = "X";
              }
              newTd3.width = "80px";
              var newTd4 = curTR.insertCell();
              newTd4.innerHTML = '<a href="#" onclick="del()">删除</a>'
              document.getElementById("siteName").value='';
              auth[0].checked = false;
              auth[1].checked = false;
          }else{
            alert("已经对该网站设置了共享！");
          }
        }else{
          alert("请输入一个网站！");
        }
      }
      function del(){
        var curTR = window.event.srcElement.parentElement.parentElement;
        var siteName = curTR.cells[1].innerText;
        if(confirm('确认删除网站共享？')) {
          var req = getXMLHttpRequest();
          var callback=function(){
            if ((req.readyState==4)&&(req.status==200)){
              var data=req.responseText;
              if(data!=null&&data!=""){
                //alert("删除成功！");
              }
            }
          }
          var url = "delShare.do?categoryID="+<bean:write name="categoryID"/>+"&siteName="+siteName;
          sendRequest(req, url, callback);
          table1.deleteRow(curTR.rowIndex);
        }
      }
      function checkExist(){
        var  newName = document.getElementById("siteName").value;
        for(i=0;i<table1.rows.length;i++){
          if(newName==table1.rows[i].cells[1].innerText){
            return false;
          }
        }
        return true;
      }
      </script>
    </tiles:put>
</tiles:insert>
