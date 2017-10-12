<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">选择片段</tiles:put>
  <tiles:put name="dialog"><form action="#">
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
			<tr>
				<td width="50%">页面片段</td>
				<td>绑定分类</td>
			</tr>
			<tr>
				<td width="50%">
					<iframe src="clipTree.jsp" id="channelFrame" width="100%"  height="320px"></iframe>
				</td>
				<td>
				<div class="insetDiv" id="principalTree" style="height: 320px"></div>
					 	
				</td>
			</tr>
		</table>
    <div class="operation">
      <button type="button" onclick=
	ok(this.form);
class="commonbut" id="tijiao">确定</button>
      <button type="button" onclick="window.close()" class="commonbut" id="back">取消</button>
    </div></form>
</tiles:put>
<tiles:put name="javascript">
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/objects.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmstree.js"/>"></script>   
  <script language="JavaScript" type="text/Javascript">
  	  var channelTree, principalTree;
	  window.attachEvent("onload",initPrincipalTree);

	  WebFXTreeAbstractNode.prototype.getText = function(){
      	if(this.element){
	        	var id = this.element.getID();
	        	var name = this.element.getName();
	        	var text ='<input type="radio" style="height:12px;width:14px" name="principals" ';
	        	text +=' value="'+id+ '" id="'+id+ '" />';
	        	text +='<label for="'+id+'">'+name+'</label>';
	        	return text;
      	}else{
          		return this.text;
      	}
  	 };
	  
	  function initPrincipalTree(){
			var repository = new Repository();
			var definition = repository.getNodeDefinition("principal-scheme");
			principalTree = new CMSTree(definition);
			document.getElementById("principalTree").innerHTML = principalTree.toString();
			principalTree.expand();
	  }

	  function getDefinition(){
		  	var node = principalTree.getSelected();
		  	if(node!=null){
	      	    return node.element;
		  	}
			return null;
	  }
	 
</script>
  <script language="JavaScript" type="text/Javascript">
	function ok(aForm) {
		if ((channelTree != null)) {
			var definition = getDefinition();
			if(definition==null){
				alert('请选择分类！');
				return;
			}
			var channel = document.getElementById("channelFrame").contentWindow.getChannel();
			if(channel==null){
				alert('请选择栏目！');
				return;
			}
			
			var ret = new Object();
			ret.name = channel.displayName + '(' + definition.getName()+ ')';
			ret.value = channel.contextPath + '=' + definition.getID();
			window.returnValue = ret;
			window.close();
		}
	}
</script>
</tiles:put>
</tiles:insert>
