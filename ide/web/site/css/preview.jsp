<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html>
  <head>
    <title>预览样式效果</title>
    <link  type="text/css" rel="stylesheet" href="<bean:write name="css" property="file.URL"/>"/>
  </head>
  <body>
    <h1>缺省标题1样式</h1>
    <h2>缺省标题2样式</h1>
    <h3>缺省标题3样式</h1>
    <h4>缺省标题4样式</h1>
    缺省正文样式缺省正文样式<br />
    <span>正文块</span><span>正文块</span><br />
    <a href="#">这是超链接的缺省效果</a><br />
    <p>段落样式段落样式段落样式</p>
    <p>段落样式段落样式段落样式</p>
  <ul>
    <li>列表样式列表样式列表样式</li>
    <li>列表样式列表样式列表样式</li>
    <li>列表样式列表样式列表样式</li>
    <li>列表样式列表样式列表样式</li>
  </ul>
      <logic:iterate id="rule" name="cssRuleList">
        <div style="<bean:write name="rule" property="style.cssText"/>">
        <logic:present name="rule" property="style.properties">
          <logic:iterate id="property" name="rule" property="style.properties" >
            <span><bean:write name="property" property="name"/>:<bean:write name="property" property="value"/>;</span>
          </logic:iterate>
        </logic:present>
        </div>
    </logic:iterate>

</body>
</html>
