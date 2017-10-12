<%@page contentType="text/html; charset=UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
    <div id="oListPanel">
      <tiles:getAsString name="listPanel"/>
    </div>
    <div id="gotobar" style="background-color:scrollbar;height:26px;text-align:right;">
        <tiles:getAsString name="pager" ignore="true"/>
    </div>
    </body>
</html>
