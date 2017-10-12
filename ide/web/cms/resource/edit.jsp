<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert page="/site/dialogFrame.jsp">
  <tiles:put name="title">修改文件夹</tiles:put>
  <tiles:put name="content">
    <html:form action="/update.do" method="POST">
      <input type="hidden" name="folder" value="<bean:write name='folder' property="path"/>"/>
      <table width="100%" cellpadding="2" cellspacing="2" border="0">
        <tr>
          <td align="right">旧名称：</td>
          <td align="left"><bean:write name="folder" property="name"/></td>
        </tr>
        <tr>
          <td align="right">新名称：</td>
          <td align="left"><input type="text" name="newName"/></td>
        </tr>
        <tr>
          <td></td>
          <td>    <em>注意：修改文件夹名称将有可能导致这个文件夹中的文件在页面上的原有的链接无法正常使用。</em></td>
        </tr>
        <tr>
          <td></td>
          <td align="right">
            <input type="button" class="commonbut" id="edit" onclick="doEdit(this)" value="修改"/>
            <input type="button" class="commonbut" id="back" value="返回" onclick="window.location='<html:rewrite page='/folderItems.do'/>?folder=<bean:write name='folder' property="path"/>'"  />
          </td>
        </tr>
      </table>
    </html:form>
    <script type="text/javascript" language="javascript">
      function doEdit(submitter){
        if(confirm("修改文件夹名称将有可能导致这个文件夹中的文件在页面上的原有的链接无法正常使用,确认修改?")){
          submitter.disabled=true;
          submitter.form.submit();
        }
      }
      </script>
      </tiles:put>
    </tiles:insert>
