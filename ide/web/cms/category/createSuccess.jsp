<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="dialog"><br>
		<p align="center"><font size="2">创建内容分类成功</font></p>
		<div class="operation">
		<button type="button" onclick="window.close()" id="btnOk">确定</button>
		</div>
	</tiles:put>

	<tiles:put name="javascript">
		<script type="text/Javascript" language="Javascript">
    var name="<bean:write name='category' property='name'/>";
    var url="<html:rewrite page='/content/contents.do' module='/cms'/>?category=<bean:write name='category' property='ID'/>";
   // ContentFrame.getSelectedModule().addItem(name,url);
    </script>
	</tiles:put>
</tiles:insert>
