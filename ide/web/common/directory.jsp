<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="default_main_frame">
  <tiles:put name="body">

<table style="margin:50px" width="641" height="281" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td background="images/messagebg.gif">
      <table width="600" border="0" align="center" cellpadding="0" cellspacing="0" style="font-weight:bold; font-size:16px;">
        <tr>
          <td width="145" align="center"><img src="images/error.gif" width="95" height="97" /></td>
          <td width="455"><p>您无权访问此页面 </p>
            <p>您注册的用户正在被审核，或者没有被批准，或者已经被删除</p><p>请换个用户<a href="logout.do">请点击此处</a>登入，或者联系管理员！ </p></td>
        </tr>
      </table></td>
  </tr>
</table>
  </tiles:put>
</tiles:insert>
