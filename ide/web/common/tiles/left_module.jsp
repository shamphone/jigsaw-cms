<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%><%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<div class="moduleName">
   <tiles:getAsString name="title"/>
</div>
<div class="submodule" style="display:none">
    <tiles:getAsString name="module_tree" ignore="true"/><tiles:importAttribute name="submodules" ignore="true"/><logic:present name="submodules">
        <ul><logic:iterate id="module" name="submodules">
            <li><bean:write name="module" filter="false"/></li>
        </logic:iterate></ul></logic:present>
    </div>
