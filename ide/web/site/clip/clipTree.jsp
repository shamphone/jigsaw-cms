<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
	<body style="padding: 0xp;margin: 0px;">
		<div class="insetDiv" id="channelTree" style="width: 100%;height: 100%;"></div>
	</body>
</html>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/channeltree.js"/>"></script>
  <script language="JavaScript" type="text/Javascript">
	  window.attachEvent("onload",initChannelTree);

	  var channelTree;
	  
	  function initChannelTree(){
		  	var oTemplate = window.dialogArguments.dialogArguments.template;
			channelTree = new CHANNELTree(oTemplate,['script','index','style']);
			document.getElementById("channelTree").innerHTML = channelTree.toString();	
			channelTree.expand();
			top.channelTree = channelTree;
	  }

	  function CHANNELTree_OnClickChannel(path){
      };
      function CHANNELTree_OnClickFolder(template, contextPath){
   	  };

	  WebFXTreeAbstractNode.prototype.getText = function(){
	      	if(this.element){
	      		var id = this.element.path;
	        	var name = this.element.displayName;
	        	var text ='<input type="radio" style="height:12px;width:14px" name="channels" ';
	        	if(this.element.type!='clip'){
	        		text += ' disabled = disabled ';
	        	}
	        	text +=' value="'+id+ '" id="'+id+ '" />';
	        	text +='<label for="'+id+'">'+name+'</label>';
	        	return text;
	      	}else{
	          	return this.text;
	      	}
  	  };

	  	function getChannel(){
	  		var node = channelTree.getSelected();
	        if(node&&node.isEnable()){
	      	    return node.element;
	        }
	        return null;
	    }
	  
  </script>
