<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">创建属性</tiles:put>
  
  <tiles:put name="dialog">
    <div align="center" style="padding-top:100px;"> 该属性ID已存在！</div>
    <div class="operation">
            <button type="button" onclick="window.close()" >确定</button>
          </div>
    </tiles:put>
    </tiles:insert>
