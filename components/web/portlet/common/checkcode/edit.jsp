<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/passport.tld" prefix="passport"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
  	<tr>
  		<td>
        <div class="toolbar">
          <input type="submit" value='保存'/>
          <input type="button" value='取消' onclick="window.parent.close()"/>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
