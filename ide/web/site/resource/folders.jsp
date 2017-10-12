<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">
	<logic:notEmpty name="title"><bean:write name="title"/></logic:notEmpty>
	<logic:empty name="title">选择文件夹</logic:empty>
  </tiles:put>
  <tiles:put name="dialog">
    <table width="100%" cellpadding="5" cellspacing="4" border="0">
      <script type="text/javascript">
        var selectedPath = '/sites/<bean:write name="templateName" ignore="true"/>';
        function doSelect(path){
        	selectedPath=path;
        }
        function ok(){
            window.returnValue=selectedPath;
            window.close();
        }
      </script>
      <tr>
        <td width="200px"><div class="insetDiv" style="width:100%;height:200px">
          <fulong:xtree name="folderList" nodeId="node" treeNode="treeNode">
            <fulong:xtreeText><bean:write name='node' property='name'/></fulong:xtreeText>
            <fulong:xtreeAction>javascript:doSelect(&quot;<bean:write name='node' property='path'/>&quot;);</fulong:xtreeAction>
            <fulong:xtreeIcon><html:rewrite module="/common" page="/xtree/images/foldericon.png"/></fulong:xtreeIcon>
            <fulong:xtreeSOpenIcon><html:rewrite module="/common" page="/xtree/images/openfoldericon.png"/></fulong:xtreeSOpenIcon>
          </fulong:xtree></div>
        </td>
      </tr>
    </table>
    <div class="operation">
      <!--span id="debugInfo"></span-->
      <button onclick="ok()">确定</button>
      <button onclick="window.close();">取消</button>
    </div>
  </tiles:put>
</tiles:insert>

