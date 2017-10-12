<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">选择文件</tiles:put>
  <tiles:put name="javascript">
  <script type="text/javascript" src="fileSelector.js.jsp?id=1"></script>
  <style>
	.waitPrompt {
		background-color: #3a6ea5;
		color: #ffffff;
		width: 300px;
		padding: 10px 25px;
		border: 1px solid black;
	}
  </style>
  </tiles:put>
  <tiles:put name="dialog">
  <div id="formPanel">
    <form action="<html:rewrite page='/uploadResource.do' module='/cms/resource'/>" id="fm"  enctype="multipart/form-data"  method="post" target="hidden_frame" >
      <table cellspacing="0" cellpadding="0" width="100%" border="0" height="100%">
        <tr>
          <td class="thead" id="dialogTitle">文件资源</td>
        </tr>
        <tr class="tbody" align="center">
          <td>
            <table>
              <tr>
                <td nowrap="nowrap">
                  <input type="radio" id="r1" name="fileSource" onclick="_RadioClick('upload');" checked="true" value="0"/><label for="r1">从本地上传：</label>
                </td>
                <td><input class="inputFile" type="file" id="file" name="file" size="40"></td>
              </tr>
              <tr>
                <td nowrap="nowrap">
                  <input type="radio" id="r2" name="fileSource" onclick="_RadioClick('network');" value="1"/><label for="r2">从服务器选择：</label>
                </td>
                <td>
                  <input type="text" id="txtNetwork" name="network" readonly="true" class="inputText" size="40"/>
                  <button onclick="BrowseResources(_fileType, document.getElementById('txtNetwork'));" id="btnBrowse" name="btnBrowse" disabled="true" class="inputButton">选择. . .</button>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="right" class="tfoot">
            <button onclick="Ok()" id="btnOk" class="button">确定</button>
            &nbsp;
            <button onclick="window.close()" class="button">取消</button>
          </td>
        </tr>
      </table>
    </form>
    <iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
    </div>
    <div id="tipPanel" style="width:100%; height:100%; text-align:center; display:none; padding-top:30px;">
    	<marquee class="waitPrompt" behavior="alternate" scrollAmount="5" >正在上传，请稍等...</marquee>
    </div>
</tiles:put>
</tiles:insert>