<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>上传文件</title>
    <style type="text/css">
      body, a, table, div, span, td, th, input, select{font:9pt;font-family: Verdana, Arial, Helvetica, sans-serif;}
      body {padding:5px}
      .formTips{display:none}
    </style>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
 </head>
 <body>
   <table border=0 cellpadding=0 cellspacing=0 align=center>
     <html:form action="/upload.do"  enctype="multipart/form-data" method="post">
       <tr>
         <td>
           <fieldset><legend>上传文件</legend>
             <table border=0 cellpadding=5 cellspacing=0>
               <tr>
                 <td>目标文件夹:</td>
                 <td>
                   <html:select property="folder" style="width:200px">
                     <fulong:treeSelect name="folderList" objectId="iFolder">
                       <bean:define id="id" name="iFolder" property="path"/>
                       <option value="<%= ""+id%>" title="<bean:write name="iFolder" property="displayPath(&nbsp;&nbsp;)" filter="false"/>">
                         <logic:equal name="iFolder" property="depth" value="1">
                           我的资源库
                         </logic:equal>
                         <logic:greaterThan name="iFolder" property="depth" value="1">
                           <bean:write name="iFolder" property="displayPath(&nbsp;&nbsp;)" filter="false"/>
                         </logic:greaterThan>
                       </option>
                     </fulong:treeSelect>
                   </html:select>
                 </td>
               </tr>
               <tr>
                 <td>文件1:</td>
                 <td><html:file property="file[0]"/></td>
               </tr>
               <tr>
                 <td>文件2:</td>
                 <td><html:file property="file[1]"/></td>
               </tr>
               <tr>
                 <td>文件3:</td>
                 <td><html:file property="file[2]"/></td>
               </tr>
               <tr>
                 <td>文件4:</td>
                 <td><html:file property="file[3]"/></td>
               </tr>
               <tr>
                 <td>文件5:</td>
                 <td><html:file property="file[4]"/></td>
               </tr>
             </table>
           </fieldset>
         </td>
       </tr>
       <tr><td height=5></td></tr>
       <tr>
         <td align=right>
           <button class="commonbut" id="commonbut" onclick="save(this)">确认</button>
           &nbsp;&nbsp;
           <button class="commonbut" id="back" onclick="window.close()">取消</button>
         </td>
       </tr>
     </html:form>
   </table>

  <html:javascript  formName="resourceForm" bundle="resource"/>
  <script type="text/javascript" language="javascript">
    function save(submitter){
      if(validateResourceForm(submitter.form)){


          submitter.form.submit();
          submitter.disabled=true;
      }
    }
    </script>
  </body>
</html>
