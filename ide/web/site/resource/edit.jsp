<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%
/**
 * 重命名文件或文件夹
 */
%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">重命名</tiles:put>
  <tiles:put name="dialog">
      <html:form action="/update.do" method="POST" onsubmit="return _Validate(this)">
        <table width="100%" cellpadding="2" cellspacing="2" border="0">
        <input type="hidden" name="templateID" value="<%= request.getParameter("templateID") %>"/>
        <input type="hidden" name="path" value="<bean:write name='path'/>"/>
        <tr>
          <td>新名称:<input id="fileName" type="text" name="newName" size="32" maxlength="20" value="<bean:write name='name'/>"/></td>
        </tr>
      </table>
      <div class="operation">
        <button class="commonbut" id="submit" type="submit">确定</button>
        <button class="commonbut" onclick="window.close()">取消</button>
      </div>
    </html:form>
  </tiles:put>
  <tiles:put name="javascript">
    <script type="text/javascript" language="javascript">
	    window.onload = function() {
	        document.getElementById("fileName").select();
	    }
      	function _GetLegalFileName($input) {
      		var fileName = $input.value = $input.value.trim();
      		if (!fileName) {
      			alert("文件名不能为空");
      			return null;
      		}
      		var reg1 = /^[^\\\/:\*\?\"<>\|&#]+$/;
      		if (!reg1.test(fileName)) {
      			alert("文件名不能包含以下字符：\n \\ / : * ? < > | & #");
      			return null;
      		}
      		var reg2 = /^[^\.]+.*$/;
      		if (!reg2.test(fileName)) {
      			alert("文件名不能为空");
      			return null;
      		}
      		var reg3 = /^(.+?)\.+$/;
      		var temp = fileName.match(reg3);
      		if (temp)
      			fileName = temp[1];
      		return fileName;
      	}
      	function _Validate($form) {
      		var text = document.getElementById("fileName");
      		var fileName = _GetLegalFileName(text);
      		if (!fileName) {
      			text.select();
      			return false;
      		}
      		$form.all("submit").disabled=true;
            return true;
      	}
      </script>
      </tiles:put>
    </tiles:insert>
