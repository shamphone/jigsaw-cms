<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">删除分类 </tiles:put>
	<tiles:put name="dialog">
		<p align="left"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个操作将删除当前内容分类及所属内容，并且不可恢复!</font></p><p align="center"><font size="3">确认删除？</font></p>
		<div class="operation">
		<button onclick="doDelete()" id="btnOk">确定</button>
		<button onclick="cancelDel()" id="btnCancel">取消</button>
		<script type="text/javascript">
		function doDelete(){
			var url='deleteCategory.do?categoryID=<bean:write name="categoryID"/>';
			var oReq=new HttpRequest(url);
			var data=oReq.Get();
			if(data=='success'){
				  window.returnValue=0;
					window.close();
			}
			else if(data=='1'){
				alert('该分类下存在子分类，不能删除');
				window.returnValue=4;
				}
			else if(data=='2'){
				alert('该分类下存在内容，不能删除');
				window.returnValue=4;
				}
			else if(data=='3'){
				alert('该分类被其他分类引用，不能删除');
				window.returnValue=4;
				}
			else{
				alert('删除失败');
				window.returnValue=4;
				}
		}

		function cancelDel()
		{
			window.returnValue=4;
			window.close();
		}
		
		</script>
	</tiles:put>
</tiles:insert>
