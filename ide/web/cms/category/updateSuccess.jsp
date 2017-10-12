<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="dialog"><br><p align="center"><font size="3">修改内容分类成功</font></p></tiles:put>
  <div class="operation">
                        <button type="button" onclick="window.close()" id="btnOK">确定</button>
                        <button type="button" onclick="window.close()" id="btnCancel">确定</button>
                    </div>
  <tiles:put name="javascript">
    <script type="text/Javascript" language="Javascript">
     setTimeout("window.close()",2000);
    </script>
  </tiles:put>
</tiles:insert>

