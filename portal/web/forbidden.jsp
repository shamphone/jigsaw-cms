<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert page="/site/framework.jsp">
  <tiles:put name="title">无权访问！</tiles:put>
  <tiles:put name="content">
    <h2>无权访问！</h2>
    <div class="tips">
   </strong>您无权浏览本内容,如有疑问，请您与客户支持联系,电话:<strong>010-68574511</strong>
    </div>
    <div class="operation">
      <input type="button" onclick="window.close()" value="关闭"/>
      </div>
</tiles:put>
</tiles:insert>
