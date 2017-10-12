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
    <title>修改</title>
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
    <div align="center" style="margin-top:20px">
      <fieldset>
        <legend>修改内容</legend>
        <textarea id="messageStr" cols="50" rows="6" style="overflow:auto"></textarea>
        <div class="operation">
          <button type="button" onclick="ok()" class="commonbut" id="tijiao">提交</button>
          <button type="button" onclick="window.close()" class="commonbut" id="back">取消</button>
        </div>
      </fieldset>
    </div>
    <script language="JavaScript" type="text/Javascript">
    document.getElementById("messageStr").value = '<bean:write name="messageString"  filter="true"/>';
      function ok(){
        var contentID = '<bean:write name="contentID"/>';
        var messageStr = document.getElementById("messageStr").value;
        if(messageStr==""){
          alert("回答内容不能为空！");
          return false;
        }
        var param = "messageStr="+encodeURIComponent(messageStr);
        var req=getXMLHttpRequest();
        var url= '<html:rewrite module="/common" page="/updateMesContent.do"/>?contentID='+contentID+"&timeStamp=" + new Date().getTime();
        req.open("POST",url,false);
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        req.send(param);
        if((req.readyState==4)&&(req.status==200)){
          var data=req.responseText;
          if(data!=null&&data!=""){
            if(data=="true"){
              alert("修改成功！");
              window.returnValue=data;
              window.close();
            }else{
              alert("修改失败！");
            }
          }
        }else{
          alert("修改失败！");
        }
      }
    </script>
    </body>
  </html>
