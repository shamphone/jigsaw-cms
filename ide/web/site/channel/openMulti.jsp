<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title"><bean:write name="template" property="displayName"/></tiles:put>
  <tiles:put name="javascript">
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
    <script type="text/javascript" language="javascript" src="<html:rewrite page='/dialog.js' module='/site'/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/webdav.js"/>"></script>    
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smchannel.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smtemplate.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smresource.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/smfolder.js"/>"></script>
    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/channeltree.js"/>"></script>
    <script type="text/javascript" language="javascript">
	var defaultChannels = new Array();
	<logic:iterate id="contextPath" name="defaultChannel">
		defaultChannels.push('<bean:write name="contextPath"/>');
	</logic:iterate>
    
	WebFXTreeAbstractNode.prototype.getText = function(){
		switch(this.nodeType){
			case 'CHANNEL':
				var displayName = this.element.displayName;
				var name = this.element.name;
				var text='<input type="checkbox" onclick="CHANNELTree_OnClick(\''+this.id+'\')" name="channelID" value="'+name+
        			'" id="'+this.id+'" style="height:13px;"><label for="'+this.id+'">'+displayName+'</label>';
        			return text;
			default:
				return this.text;	
	}
};
    
      window.onload = function(){
        top.document.title= window.dialogArguments + "-"+window.document.title;
        document.getElementById("ok").disabled=true;
        doInit();
      };
      function doInit(){
    		var oTemplate = new SMTemplate("<bean:write name='template' property='name'/>","<bean:write name='template' property='displayName'/>","<bean:write name='template' property='resolution'/>");
    		var channelTree = new CHANNELTree(oTemplate);
    		document.getElementById("channelTree").innerHTML = channelTree.toString();
    		channelTree.expand();
      };
      function changeTemplate(){
        var template=SiteDialog.selectTemplate();
        if(template!=null&&template.length>0){
          reload(template[0].ID);
        }
      }
      function reload(templateId){
          var url='<html:rewrite page="/open.do?templateId=" module="/site/channel"/>'+templateId;
          url = url+"&openTemplate=true";
          url = url + "&title=<%= request.getParameter("title")%>";
          url = url+"&multi=true&time="+new Date().getTime();
          oRefresh.href= url;
          oRefresh.click();
      }
      function ok(){
          window.top.returnValue=selectedChannels;
          window.top.close();
      }
      </script>
      </tiles:put>
      <tiles:put name="dialog">
      	<a href="#" id="oRefresh" style="display:none">doRefresh</a>
       	<div id="channelTree" style="width:100%;height:96.6%;background-color:#ffffff;border:2px inset;overflow:scroll;padding-top:1px; ">
		</div>
        <div class="operation">
          <logic:present parameter="openTemplate"><button type="button" onclick="changeTemplate()" style="margin-left:0px;margin-right:66px; padding:0px">打开其它网站模板...</button></logic:present>
          <button type="button" onclick="ok()"  id="ok">确定</button>
          <button type="button" onclick="window.close()">取消</button>
        </div>
      </tiles:put>
    </tiles:insert>
