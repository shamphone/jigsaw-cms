<%@page contentType="text/xml; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><?xml version="1.0" encoding="UTF-8"?>
<messages>
<html:messages id="message" message="true">
  	<message><bean:write name="message"/></message>
</html:messages>
</messages>