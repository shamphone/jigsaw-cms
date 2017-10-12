<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
  <logic:iterate id="childFile" name="childFolders" indexId="fileIndex">
    <div class="folderGrid"  title="<bean:write name='childFile' property='name'/>" onclick="doSelect(this,'<bean:write name="childFile" property="path"/>')" ondblclick="LoadItems('<bean:write name="childFile" property="path"/>')">
      <div class="fileIcon"><img alt='<bean:write name="childFile" property="name"/>' src='<html:rewrite module="/common" page="/images/doctype/folderpic.gif"/>' width="80" height="80" /></div>
      <div class="fileName"><bean:write name="childFile" property="name"/></div>
    </div>
  </logic:iterate>
  <logic:iterate id="childFile" name="childFiles" indexId="fileIndex">
    <div class="folderGrid"  title="<bean:write name="childFile" property="name"/>" onclick="doSelect(this,'<bean:write name="childFile" property="path"/>')" ondblclick="window.open('<html:rewrite page="" module=""/><bean:write name="childFile" property="path"/>','_blank')">
      <div class="fileIcon" onmousemove="showEnlargeImage(this)" onmouseout="hideEnlargeImage(this)"><img id="img<bean:write name='fileIndex' format='#'/>" alt='<bean:write name="childFile" property="name"/>' src='<logic:equal value="true" name="childFile" property="pic"><html:rewrite page="" module=""/><bean:write name="childFile" property="path"/></logic:equal><logic:equal value="false" name="childFile" property="pic">../../common/images/doctype/<bean:write name="childFile" property="suffix" ignore="true"/>.gif</logic:equal>' onload ="resizeImg(this)" style="display:none" align="absmiddle" /></div>
      <div class="fileName"><bean:write name="childFile" property="name"/></div>
    </div>
  </logic:iterate>
