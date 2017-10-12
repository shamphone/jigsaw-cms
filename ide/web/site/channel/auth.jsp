<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>权限设置</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<base target="_self" />   
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="create.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/objects.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmstree.js"/>"></script>   
    <script language="Javascript" type="text/Javascript">
    	var FCK = window.dialogArguments.FCK;
    	var oTemplate = window.dialogArguments.oTemplate;
    	var allows = getAllow();
    	var tree;
    	var repository = new Repository();
    	var definition = repository.getNodeDefinition("principal-scheme");
    	WebFXTreeAbstractNode.prototype.getText = function(){
        	if(this.element){
	        	var id = this.element.getID();
	        	var name = this.element.getName();
	        	var disabled = !document.all("openBtn").checked;
	        	var text ='<input type="checkbox" style="height:12px;width:14px" name="principals" ';
	        	if(disabled)
	        		text +='disabled="disabled"';
        		if(allows!=null&&allows.contains(id)){
        			text +=' checked="checked" ';
        		}
	        	text +=' value="'+id+ '" id="'+id+ '" />';
	        	text +='<label for="'+id+'">'+name+'</label>';
	        	return text;
        	}else{
            	return this.text;
        	}
    	};

    	
    	function setAllow(allow){
        	if(allow==null){
        		removeAllow();
        		return;
        	}
        	allow = allow.join(",");
			if(/<site:html(.*?)allow=\"([^\"]*)\"(.*?)/i.test(FCK.Html)){
				FCK.Html = FCK.Html.replace(/(<site:html(.*?)allow=\")([^\"]*)(\"(.*?))/i,"$1"+allow+"$4");
			}else{
				FCK.Html = FCK.Html.replace(/(<site:html(.*?))(>)/i,"$1 allow=\""+allow+"\"$3");
			}
		}
		function removeAllow(){
			if(/<site:html(.*?)allow=\"[^\"]*\"(.*?)/i.test(FCK.Html)){
				FCK.Html = FCK.Html.replace(/(<site:html(.*?))(allow=\"[^\"]*\")(.*?)/i,"$1$4");
			}
		}
		function getAllow(){
			if(/<site:html(.*?)allow=\"([^\"]*)\"(.*?)/i.test(FCK.Html)){
				var s = RegExp["$2"];
				return s.split(",");
			}
			return null;
		}
		function initTree(){
	   		tree = new CMSTree(definition);
			document.all("d1").innerHTML = tree.toString();
			tree.expand(); 
		}
		function initAuth(){
			var secure = document.getElementById("openBtn");
			secure.checked = allows?true:false;
		}
		function doInit(){
			initAuth();
			initTree();
		}
		function saveAuth(){
			var secure = document.getElementById("openBtn");
			var category = null;
			if(secure.checked){
				category = new Array();
				var checkboxes = document.getElementsByName("principals");
		        for(var i=0;i<checkboxes.length;i++){
		            if( checkboxes[i].checked){
		            	category.push(checkboxes[i].value);
		            }
		        }
			}
			setAllow(category);
			window.close();
		}
		function _Grant() {
	    	var ret = SiteDialog.Permission(oTemplate)
	    	if (ret)
	    		top.Reload();
		}
		function synSelection(){
	        var checkboxes = document.getElementsByName("principals");
	        for(var i=0;i<checkboxes.length;i++)
	             checkboxes[i].disabled = !document.all("openBtn").checked;
	    }
    </script>
</head>
<body onload="doInit()">
      <table width="100%" cellpadding="2" cellspacing="2" border="0">
        <tr>
          <td>
            <input style="width:14px;height:12px" type="checkbox" value="true" id="openBtn" name="secure" onclick="synSelection()"/><label for="openBtn">用户必需登入方可访问这个页面。</label>
          </td>
        </tr>
        <tr>
          <td>
             <div id="d1" class="insetDiv" style="height:180px;width:100%">
             </div>
           </td>           
            </tr>
          </table>
          <div class="operation">
            <!--  <button onclick="_Grant()">用户授权...</button> -->
            <button onclick="saveAuth()">确定</button>
            <button onclick="window.close()">取消</button>
          </div>
     </body>
     </html>