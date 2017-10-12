<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">导入模板</tiles:put>
  <tiles:put name="javascript">
    <script type="text/javascript" language="javascript">
		function validate($form){
			if (!trim($form.templateFile.value)) {
				alert("请选择文件");
				return false;
			}
			return true;
		}
    </script>
      </tiles:put>
      <tiles:put name="dialog">
		<html:form action="/doImport.do" enctype="multipart/form-data" onsubmit="return validate(this)">
			<table border=0 cellpadding=2 cellspacing=0 align=center width="100%">
				<html:hidden property="path" />
				<html:hidden property="templateName" />
				<html:hidden property="type" />
				<tr>
					<td>请上传页面模板HTML文件：</td>
				</tr>
				<tr>
					<td><html:file property="templateFile" style="width:384px"/></td>
				</tr>
			</table>
			<div class="operation">
				<button type="submit" class="commonbut" id="tijiao">确定</button>
				<button onclick="window.close()" class="commonbut" id="back">取消</button>
			</div>
		</html:form>
	</tiles:put>
</tiles:insert>
