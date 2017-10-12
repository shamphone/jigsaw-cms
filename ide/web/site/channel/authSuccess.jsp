<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">页面授权</tiles:put>
  <tiles:put name="javascript">
  </tiles:put>
  <tiles:put name="dialog">
    <div align="center" style="padding-top:100px;"> 授权成功！</div>
    <div class="operation">
            <button type="button" onclick="window.close()" class="commonbut" id="tijiao">确定</button>
          </div>
    </tiles:put>
    </tiles:insert>
