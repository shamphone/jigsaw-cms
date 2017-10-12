<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">编辑分类 </tiles:put>
	<tiles:put name="dialog">
		<html:form action="updateCategory.do" onsubmit="return check(this);">
			<table width="100%" cellpadding="0"
				cellspacing="0" border="0">
				<html:hidden property="categoryID" />
				<tr>
					<td height="20px">分类名称</td>
					</tr>
					<tr>
					<td height="20px"><html:text property="name" size="39" maxlength="32"
						title="分类的显示名称，2-32个字符，可以使用中文。" /></td>
				</tr>
			</table>
			<div class="operation">
			<button type="submit">保存</button>
			<button type="button" onclick="window.close()" id="btnCancel">取消</button>
			</div>
		</html:form>
		<html:javascript formName="categoryForm" />
		<script language="JavaScript" type="text/Javascript">
          function check(form){
            if(form.name.value.trim()!=""){
				  window.returnValue=0;
				  return true;
            }else{
                alert("内容分类名称不能为空");
                return false;
            }
          }
          </script>
	</tiles:put>
</tiles:insert>
