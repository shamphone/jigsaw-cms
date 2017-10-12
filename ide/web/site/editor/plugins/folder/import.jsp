<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上传文件</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<base target="_self" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="property.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
      <script type="text/javascript" language="javascript">
      var Editor = window.dialogArguments.Editor;
      function doInit(){
          document.all("folder").value = Editor.channelTree.getSelected().element.getContextPath();
          document.all("template").value = Editor.template.name;
      }
        function save(submitter){
            submitter.form.submit();
            submitter.disabled=true;
        }
        </script>
</head>
<body onload="doInit()">
   <form action="/ide/site/resource/import.do"  enctype="multipart/form-data" method="post">
      <table width="100%" cellpadding="2" cellspacing="2" border="0" align="center">
      <input type="hidden" name="template" />
        <tr>
          <td width="80" align="right"  nowrap="nowrap">目标文件夹:</td>
          <td><input class="disabledInput" type="text"  name="folder" readonly="readonly" size="40" /> </td>
          <td><input type="checkbox" id="upload" name="upload" value="true"/><label for="upload">上传水印图片</label></td>
          </tr>
        </table>
        <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
          <tr>
            <td  width="20px"><hr size="1" align="left" width="100%"/></td>
            <td width="50px" align="center">逐个导入</td>
            <td valign="middle"><hr size="1" align="left" width="100%"/></td>
          </tr>
        </table>
        <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
          <tr>
            <td width="80" align="right" nowrap="nowrap">文件1:</td><td><input type="file" contentEditable="false" name="file[0]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件2:</td><td><input type="file" contentEditable="false" name="file[1]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件3:</td><td><input type="file" contentEditable="false" name="file[2]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件4:</td><td><input type="file" contentEditable="false" name="file[3]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件5:</td><td><input type="file" contentEditable="false" name="file[4]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件6:</td><td><input type="file" contentEditable="false" name="file[5]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件7:</td><td><input type="file" contentEditable="false" name="file[6]" size="40"/></td>
          </tr>
          <tr>
            <td align="right" >文件8:</td><td><input type="file" contentEditable="false" name="file[7]" size="40"/></td>
          </tr>
        </table>
        <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
          <tr>
            <td  width="20px"><hr size="1" align="left" width="100%"/></td>
            <td width="90px" align="center">导入zip压缩包</td>
            <td valign="middle"><hr size="1" align="left" width="100%"/></td>
          </tr>
        </table>
        <div style="color:red"><html:errors/></div>
        <table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
          <tr>
            <td width="80" align="right" >压缩文件：</td><td><input type="file" contentEditable="false" name="zip" size="40"/></td>
          </tr>
        </table>
        <div class="operation">
          <button class="commonbut" onclick="save(this)">确定</button>
          <button class="commonbut" onclick="window.close()">取消</button>
        </div>
      </form>
      </body>      
      </html>
      

