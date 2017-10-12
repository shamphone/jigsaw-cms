<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="dialog">	
		<div align="center" style="line-height:200px; vertical-align:middle;">转移内容分类成功</div>
		<div class="operation">
                <button type="button" onclick="doClose()" id="btnCancel">确定</button> 
            </div>
	</tiles:put>
	<tiles:put name="javascript">
		<script type="text/Javascript" language="Javascript">
		function doClose(){
			var ret = new Object();
			ret.parent = '<bean:write name="parent" property="ID"/>';
			ret.children = new Array();
			window.returnValue = ret;
			window.close();
		}
    //window.parent.document.frames("index").location.reload();
    </script>
	</tiles:put>
</tiles:insert>
