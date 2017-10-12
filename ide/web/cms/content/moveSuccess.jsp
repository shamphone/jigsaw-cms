<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
<tiles:put name="title">转移内容</tiles:put>
  <tiles:put name="dialog">
 <p align="center"><font size="3">移动内容成功</font> </p>
  <div class="operation">
        <button type="button" onclick="window.close()" class="commonbut" id="btnOk">确定</button>
        <button type="button" onclick="window.close()" class="commonbut" id="btnCancel">取消</button>
      </div>
  </tiles:put>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
    setTimeout("window.close()",2000);
    </script>
  </tiles:put>
</tiles:insert>
