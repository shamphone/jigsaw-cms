<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<tiles:useAttribute id="dictId" name="dictId"/>
<button id="btnDictDetail" onclick="ListFrame.ClickTabButton(this,'../dict/dictDetail.do?dictId=<bean:write name="dictId"/>', false);">类别子项</button>
<button id="btnCreateDict" onclick="ListFrame.ClickTabButton(this,'../dict/creatDict.do', true);">新建类别</button>
<button id="btnEditDict"   onclick="ListFrame.ClickTabButton(this,'../dict/modifyDict.do?dictID=<bean:write name="dictId"/>', true);">修改类别</button>
<button id="btnDeleteDict" onclick="ListFrame.ClickTabButton(this,'../dict/deleteDictConfirm.do?dictID=<bean:write name="dictId"/>', true);">删除类别</button>
<button id="btnDefinition" onclick="ListFrame.ClickTabButton(this,'../definition/definition.do?dictId=<bean:write name="dictId"/>',false);ListFrame.CleanContent()">格式设计</button>
