<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">发布页面</tiles:put>
  <tiles:put name="javascript">
    <script type="text/javascript" language="javascript">
    var channels=new Array();
    <logic:present name="channels">
      <logic:iterate id="channel" name="channels">
        channels.push('<bean:write name="channel" />');
      </logic:iterate>
    </logic:present>
    window.returnValue=channels;
    </script>
  </tiles:put>
  <tiles:put name="dialog">
    <div align="center" style="padding-top:100px;"> 发布成功！</div>
    <div class="operation">
            <button type="button" onclick="window.close()" class="commonbut" id="btnOk">确定</button>
          </div>
    </tiles:put>
    </tiles:insert>
