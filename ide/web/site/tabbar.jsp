<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-site.tld" prefix="site"%>
<tiles:useAttribute id="categoryId" name="categoryId"/>
<button id="btnTemplateList" onclick="ListFrame.ClickTabButton(this,'../template/templateList.do?categoryId=<bean:write name="categoryId" />',false);">模板列表</button>
<button id="btnCreate" onclick="ListFrame.ClickTabButton(this,'../template/addTemplateCategory.do',true);">新建模板类别</button>
<button id="btnEdit" onclick="ListFrame.ClickTabButton(this,'../template/updateCategory.do?categoryId=<bean:write name="categoryId" />',true);">修改模板类别</button>
<button id="btnDelete" onclick="ListFrame.ClickTabButton(this,'../template/delTemplateCategoryConfirm.do?categoryId=<bean:write name="categoryId" />',true);">删除模板类别</button>

