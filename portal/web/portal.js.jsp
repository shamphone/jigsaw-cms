<%@page contentType="text/javascript; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<%/*** 提供给最终页面用的脚本,包括数据校验等.一些在占位符里公共的脚本也放在这个文件中. 注意 占位符的脚本的初始化要特别注意，防止脚本被加载两次   ********/%>
document.write( '<script type="text/javascript" src="/ide/common/script/common.js"></script>' );
document.write( '<script type="text/javascript" src="/ide/common/script/ajax.js"></script>' );
document.write( '<script type="text/javascript" src="/ide/common/script/dateFormat.js"></script>' );
document.write( '<script type="text/javascript" src="/ide/cms/classes/cmsdialog.js"></script>' );
<!-- document.write( '<script type="text/javascript" src="/ide/site/dialog.js"></script>' );  -->
<logic:iterate id="portletCategory" name="com.fulong.longcon.portlet.container" property="portletCategories" indexId="categoryIndex"><logic:iterate id="portletDefinition" name="portletCategory" property="portletDeginitions"><logic:present name="portletDefinition" property="initParameterConfig(script-file)"><bean:define id="scriptPath" type="String" name="portletDefinition" property="initParameterConfig(script-file).value"/><cms:unique name='<%= scriptPath %>'>
document.write( '<script type="text/javascript" src="/component<%= ""+scriptPath %>"></script>' );</cms:unique></logic:present></logic:iterate></logic:iterate>
