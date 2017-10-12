<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert page="/common/tiles/left_frame.jsp">
  <tiles:put name="title">网站管理</tiles:put>
  <tiles:put name="content">
    <fulong:checkRole role="ROLE_ADMIN">
      <tiles:insert definition="left_module" flush="false">
        <tiles:put name="title">网站模板管理</tiles:put>
        <tiles:put name="module_tree">
        <ul class="tree">
          <li>
            管理模板类别
            <ul>
              <logic:iterate id="category" name="categories" indexId="index">
                <li><a class="close" onclick="setFocus(this)" href="template/templateList.do?categoryId=<bean:write name="category" property="ID"/>"><bean:write name="category" property="displayName"/>模板(<bean:write name="category" property="templateCount"/>)</a></li>
              </logic:iterate>
            </ul>
          </li>
        </tiles:put>
      </tiles:insert>
      <tiles:insert definition="left_module" flush="false">
        <tiles:put name="title">网站管理</tiles:put>
        <tiles:put name="module_tree">
          <ul class="tree">
            <logic:iterate id="cg" name="cgs" indexId="indexID">
              <li><a class="close" onclick="setFocus(this)" href="management/siteList.do?categoryID=<bean:write name="cg" property="ID" ignore="true"/>"><bean:write name="cg" property="displayName" ignore="true"/>网站(<bean:write name="cg" property="siteCount"/>)</a></li>
            </logic:iterate>
            <li><a class="close" onclick="setFocus(this)" href="management/siteList.do">网站搜索</a></li>
          </ul>
        </tiles:put>
      </tiles:insert>
    </fulong:checkRole>
    <fulong:checkRole role="ROLE_ORG">
      <tiles:insert definition="left_module" flush="false">
        <tiles:put name="title">自助建站管理</tiles:put>
        <tiles:put name="module_tree">
          <ul class="tree">
            <li><a class="close" target="_blank" onclick="setFocus(this)" href="http://<bean:write name="site" property="domain" ignore="true"/>">网站预览</a></li>
            <li><a class="close" onclick="setFocus(this)" href="changeTemplate.do">更换模板</a></li>
          </ul>
        </tiles:put>
      </tiles:insert>
    </fulong:checkRole>
  </tiles:put>
</tiles:insert>

    <%--
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="navigatorTable">
                <tr>
                    <td class="moduleName">网站管理</td>
                </tr>
               </logic:equal>
            <logic:equal value="true" name="isOrg">
                <tr>
                    <td class="moduleName">自助建站管理</td>
                </tr>
                <tr style="display:none">
                    <td>
                        <div class="submodule">
                            <logic:present name="site" property="domain">
                                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="lefttable">
                                    <tr>
                                        <td width="20" align="center"><html:img module="/common" page="/images/dian.gif"/></td>
                                        <td width="165">
                                            <a target="_blank" onclick="setFocus(this)" href="http://<bean:write name="site" property="domain" ignore="true"/>">网站预览</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="20" align="center"><html:img module="/common" page="/images/dian.gif"/></td>
                                        <td width="165"><a onclick="setFocus(this)" href="changeTemplate.do">更换模板</a> </td>
                                    </tr>
                                </table>
                            </logic:present>
                        </div>
                    </td>
                </tr>
            </logic:equal>
        </table>
    </body>
</html>--%>


