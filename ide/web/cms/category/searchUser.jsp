<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>搜索用户</title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/style.css"/>">
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/stat.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/area.jsp"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/date.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/industry.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/sortControl.js"/>"></script>
  </head>

  <body>
    <div style="margin-top:20px">
      <html:form action="/doSearchUser.do" method="POST">
        <table width="100%" border="1" cellpadding="0" cellspacing="0">
          <tr>
            <th scope="row">用户名称</th>
            <td>
              <html:text property="name" size="40" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <button type="button" onclick="check(this.form)" class="commonbut" id="search">搜索</button>
            </td>
          </tr>
        </table>
        <logic:present name="principals">
          <logic:equal value="0" name="principals" property="size">
            <div align="center" style="margin-top:100px;margin-bottom:100px">
              没有找到任何搜索结果！
            </div>
          </logic:equal>
          <logic:notEqual value="0" name="principals" property="size">
            <div style="height:30px;padding-left:15px;padding-top:20px">搜索结果</div>
            <table width="99%" border="1" cellpadding="0" cellspacing="0" class="tableClass">
              <tr>
                <th width="80px">选择</th>
                <th width="160px">用户名称</th>
                <th width="160px">真实姓名</th>
                <th>电子邮箱</th>
              </tr>
            </table>
            <div style="height:370px;overflow-y: scroll;">
              <table id="table1" width="96%" border="1" cellpadding="0" cellspacing="0" class="tableClass">
                <logic:iterate id="prin" name="principals">
                  <tr id="<bean:write name="prin" property="id" ignore="true"/>">
                  <bean:define id="principalID" name="prin" property="id"/>
                  <%String temp = (String)principalID;%>
                  <td width="80px"><html:radio property="ID" value="<%=temp%>">&nbsp;</html:radio></td>
                  <td width="160px"><bean:write name="prin" property="username" ignore="true"/></td>
                  <td width="160px"><bean:write name="prin" property="commonname" ignore="true"/></td>
                  <td><bean:write name="prin" property="email" ignore="true"/></td>
                </tr>
              </logic:iterate>
            </table>
          </div>
        </logic:notEqual>
      </logic:present>
      <div class="operation">
        <button type="button" onclick="ok(this)" class="commonbut" id="tijiao">确定</button>
        <button type="button" onclick="window.close()" class="commonbut" id="back">取消</button>
      </div>
    </html:form>
  </div>
  <script language="JavaScript" type="text/Javascript">
    function check(form){
      disableButton();
      if(document.getElementById('name').value!=null&&
      document.getElementById('name').value!=""){
        form.submit();
      }else{
        alert("请输入用户名称！")
        enableButton();
      }
    }
    function ok(submitter){
      var item = submitter.form.ID;
      var No;
      var ret=new Object();
      if(!item){
        alert('请搜索并选择一个用户！');
        return;
      }
      if(GetRadioObject(item)!=null){
        for(i=0;i<table1.rows.length;i++){
          if(table1.rows[i].id==GetRadioObject(item).value){
            No = i ;
          }
        }
        ret.ID=GetRadioObject(item).value;
        ret.commonname = table1.rows[parseInt(No)].cells[2].innerText;
        window.returnValue=ret;
        window.close();
      }
      else{
        alert('请选择一个用户！');
      }
    }
    </script>
    </body>
  </html>
