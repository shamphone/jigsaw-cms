<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<<tiles:insert definition="dialog_frame">
  <tiles:put name="title">资源管理</tiles:put>
  <tiles:put name="dialog">
      <div class="block">
        <h2>样式列表</h2>
        <p class="helptext">以下为在本网站中使用的样式列表。</p>
        <table border="0" cellpadding="0" cellspacing="0" id="mainTable">
          <thead>
            <tr>
              <td>选择</td>
              <td>样式文件名称</td>
            </tr>
          </thead>
          <logic:notEmpty name="csses">
            <logic:iterate id="css" name="csses" >
              <tr>
                <td class="en11">
                  <input type="checkbox" name="name" value="<bean:write  name="css" property="name"/>">
                </td>
                <td class="en11"><a href="css.do?id=<fulong:encode  name="css" property="contextPath"/>"><bean:write name="css" property="name"/></a></td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
        <p class="operation">
          <input type="checkbox" name="checkbox2" value="checkbox" onclick="selectAll()">全选
            <input name="submit2" type="button" class="commonbut" onclick="deleteCSS(this)" value="删除选择样式文件">
            </p>

          </div>

          <div class="block">
            <h2>新增样式文件</h2>
            <ul class="commonul"><li>为本网站添加一个新的样式文件，<input name="button" type="button" class="commonbut" onclick="newCSS(this)" value="新增样式文件" /></li></ul>
          </div></html:form>
        </tiles:put>
      </tiles:insert>

        <script language="JavaScript" type="text/Javascript">
          var checkflag = "false";
          var checkflag2 = "false";
          function selectAll() {
            var field=document.getElementsByName("name");
            if(field == null) return;
            var flag="false";
            if (checkflag == "false") {
              for (i = 0; i < field.length; i++) {
                field[i].checked = true;
                flag="true";
              }
              checkflag = "true";
            }else {
              for (i = 0; i < field.length; i++) {
                field[i].checked = false;
                flag="true";
              }
              checkflag = "false";
            }
            if ((checkflag2 == "false") && (flag=="false")) {
              field.checked = true;
              checkflag2 = "true";
            }else if ((checkflag2 == "true") && (flag=="false")){
              field.checked=false;
              checkflag2 = "false";
            }
          }

          function deleteCSS(submitter){
            if(confirm('将永久删除您所选择的样式文件，这个操作无法恢复，并将使所有使用这个文件的页面无法正常显示，请确认删除已选中样式文件。')){
              submitter.disabled=true;
              submitter.form.submit();
            }
          }
          function newCSS(submitter){
            submitter.form.action="<html:rewrite page='/createCSS.do' module="/site/css"/>";
            submitter.disabled=true;
            submitter.form.submit();
          }
          </script>
