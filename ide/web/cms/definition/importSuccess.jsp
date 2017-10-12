<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">导入完成！</tiles:put>
<script language="javascript">
 window.onload = function(){
        if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
        	 document.body.style.overflow = "hidden";
        }
    }
 </script>
	<tiles:put name="dialog">
		<textarea rows="21" cols="58" readonly="readonly" wrap="soft"><html:messages id="msg" message="true"><bean:write name="msg" /><%= "\r\n"%></html:messages>
		</textarea>
		<div class="operation">
		<button type="button" onclick="doClose()" id="btnCancel">确定</button>
		</div>
	</tiles:put>
	<tiles:put name="javascript">
		<script type="text/Javascript" language="Javascript">
	function doClose() {
		var ret = new Object();
		window.close();
	}
		</script>
	</tiles:put>
</tiles:insert>
