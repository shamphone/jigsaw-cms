<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong" %>
<tiles:insert page="/dialogFrame.jsp">
  <tiles:put name="title">选择样式</tiles:put>
  <tiles:putList name="stylesheets">
    <tiles:add><html:rewrite page="/style.css"/></tiles:add>
  </tiles:putList>
  <tiles:put name="content">
    <html:form action="/deleteCSSDlg.do" method="POST">
      <table width="95%" cellpadding="2" cellspacing="2" border="0">
        <tr>
          <td>样式文件</td>
          <td>预览效果</td>
        </tr>
        <tr>
          <td rowspan="2">
            <select name="name" size="23" id="cssSelector" onchange="preview(this)">
              <logic:notEmpty name="csses">
                <logic:iterate id="css" name="csses" >
                  <option value="<bean:write  name="css" property="contextPath"/>"><bean:write  name="css" property="name"/></option>
                </logic:iterate>
              </logic:notEmpty>
            </select>
          </td>
          <td>
              <iframe frameborder="1" id="previewCSS" scrolling="no"></iframe>
          </td>
        </tr>
        <tr>
          <td align="right">
            <input name="submit2" type="button" class="commonbut" onclick="deleteCSS(this)" value="删除样式">
              <input name="button" type="button" class="commonbut" onclick="newCSS(this)" value="新增样式" />
            </td>
          </tr>
        <tr>
          <td></td>
          <td align="right">
            <input name="dook" type="button" class="commonbut" onclick="doOK(this)" value="确认">
              <input name="docancel" type="button" class="commonbut" onclick="doCancel(this)" value="取消" />
            </td>
          </tr>
        </table>
      </html:form>
    </tiles:put>
  </tiles:insert>
  <script language="JavaScript" type="text/Javascript">
  function doOK(submitter){
    var ret=new Object();
    var oSel=submitter.form.elements["name"];
    ret.path=oSel.options[oSel.selectedIndex].value;
    window.returnValue=ret;
    window.close();
  }
  function doCancel(submitter){
    window.close();
  }
    var checkflag = "false";
    var checkflag2 = "false";
    function preview(oSel){
      var path=oSel.options[oSel.selectedIndex].value;
      document.all("previewCSS").contentWindow.location="<html:rewrite page='/preview.do?css='/>"+encodeURIComponent(path);
    }
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
      window.location="<html:rewrite page='/createCSSDlg.do' module='/site/css'/>";
    }
    </script>
