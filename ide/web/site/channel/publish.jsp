<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">发布页面</tiles:put>
  <tiles:put name="javascript">
  	<script language="Javascript" type="text/javascript">
  		function OnSubmit($form) {
  	  		if(!document.getElementsByName("channels")[0].value){
				alert("请选择要发布的页面!");
				return false;
  	  		}
  	  		$form.elements["btn1"].disabled = true;
  	  		$form.elements["btnOk"].disabled = true;
  	  		$form.elements["btnCancel"].disabled = true;
  	  		$form.submit();
  		}
  		function MySubmit($form) {
 			selectAll($form.channels);
  	  		$form.elements["btn1"].disabled = true;
  	  		$form.elements["btnOk"].disabled = true;
  	  		$form.elements["btnCancel"].disabled = true;
  	  		OnSubmit($form);
  		}
  		
  	</script>
  </tiles:put>
  <tiles:put name="dialog">
    <form action="doPublish.do">
      <table width="100%" cellpadding="2" cellspacing="2" border="0">
        <input type="hidden" name="templateID" value="<bean:write name='template' property="ID"/>"/>
        <tr>
          <td>
            <logic:equal value="true" name="defaultChannel" property="published">当前页面已发布，请选择待发布的其他页面：</logic:equal>
            <logic:notEqual value="true" name="defaultChannel" property="published">当前页面未发布，请选择待发布的页面：</logic:notEqual>
          </td>
        </tr>
        <tr>
          <td>
            <select name="channels" multiple="multiple" style="width:380px; height:200px">
              		<logic:iterate id="channel" name="channelTree">
						<logic:notEqual value="true" name="channel" property="published">
							<logic:equal value="${defaultChannel}" name="channel" >
								<option value="<bean:write name='channel' property='ID'/>">
									<bean:write name="channel" property="displayName" />
									(<bean:write name="channel" property="contextPath" />)
								</option>
							</logic:equal>
							<logic:notEqual value="${defaultChannel}" name="channel" >
								<logic:equal value="false" name="channel" property="writing">
									<option  value="<bean:write name='channel' property='ID'/>">
										<bean:write name="channel" property="displayName" /> 
										(<bean:write name="channel" property="contextPath" />)
									</option>
								</logic:equal>
							</logic:notEqual>
						</logic:notEqual>
					</logic:iterate>
            </select>
            <script type="text/javascript">
              setSelectValue(document.getElementsByName("channels")[0],"<bean:write name='defaultChannel' property='ID'/>");
              </script>
              </td>
            </tr>
          </table>
          <div class="operation">
          	<button id="btn1" type="button" class="commonbut" onclick="MySubmit(this.form)" style="padding:0px; margin-right:152px">全部发布</button>
            <button id="btnOk" type="button" class="commonbut" onclick="OnSubmit(this.form)">发布</button>
            <button id="btnCancel" type="button" onclick="window.close()" class="commonbut">取消</button>
          </div>
        </form>
      </tiles:put>
    </tiles:insert>
