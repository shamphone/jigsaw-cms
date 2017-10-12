<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert page="/site/dialogFrame.jsp">
  <tiles:put name="title">新建文件夹</tiles:put>
  <tiles:put name="content">
    <table width="100%" cellpadding="2" cellspacing="2" border="0">
      <html:form action="/save.do" method="POST">
        <tr>
          <td height="200px" valign="middle">
            <fieldset style="margin:10px 10px 10px 10px;height:100px;width:90%;">
              <legend>新建文件夹</legend>
              <input type="hidden" name="folder" value="<bean:write name='folder' property="path"/>"/>
              <div align="center">
                <strong>文件夹名称：</strong>
                <bean:write name="folder" property="path"/>/<input type="text" name="newName"/>
                <input type="button" class="commonbut" id="new" onclick="doSave(this)" value="创建"/>
                <input type="button" class="commonbut" id="back" value="返回" onclick="window.location='<html:rewrite page='/folderItems.do'/>?folder=<bean:write name='folder' property="path"/>'"  />
              </div>
            </fieldset>
          </td>
        </tr>
      </html:form>
    </table>
    <script type="text/javascript" language="javascript">
      function doSave(submitter){
        submitter.disabled=true;
        submitter.form.submit();
      }
      </script>
      </tiles:put>
    </tiles:insert>
