<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择内容分类</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../classes/objects.js"></script>
<script language="Javascript" type="text/Javascript" src="../classes/cmstree.js"></script>
 <script language="JavaScript" type="text/Javascript">
 	var repository= new Repository();
 	var defId = window.dialogArguments.definition;
 	if(defId == null)
 	 	defId = "no-properties-scheme";	 
 	var definition = repository.getNodeDefinition(defId);
 	var tree;
 	var multiple = window.dialogArguments.multiple;
 	function doInit(){ 		 
 		 tree = new CMSTree(definition);
 		 document.getElementById("navcontainer").innerHTML = tree.toString();
 		 tree.expand();	 
 	} 		  	  	
 </script>    
  	<script language="JavaScript" type="text/Javascript">
  	WebFXTreeAbstractNode.prototype.getText = function(){
  	  	if(this.element == null)
  	  	  	return "";
  	  	if(multiple){
  	  	  	var text = "<input type='checkbox' name='definition' label='" +this.element.getName()+ "' id='"+this.element.getID()+"' value='"+this.element.getID()+"'/>";
  	  	  	text +="<label for='"+this.element.getID()+"'>"+this.element.getName()+"</label>";
  	  	  	return text;
  	  	}else{
  	  	  	var text = "<input type='radio' name='definition' label='" +this.element.getName()+ "' id='"+this.element.getID()+"' value='"+this.element.getID()+"'/>";
  	  	  	text +="<label for='"+this.element.getID()+"'>"+this.element.getName()+"</label>";
  	  	  	return text;
  	  	  	
  	  	}
  	};
	</script>
    <script language="JavaScript" type="text/Javascript">
      var selectedID=new Array();
      function setSelect(input){
        var imgs = input.parentElement.parentElement.getElementsByTagName("img");
        imgs[imgs.length-1].click()
      }
      function ok(aForm){
        var results=new Array();
        for(var i=0;i<aForm.elements['definition'].length;i++){
              var elem =aForm.elements['definition'][i]; 
            if(elem.checked){
               var obj = new Object();
               obj.ID = elem.value;
               obj.name = elem.getAttribute("label");
               results.push(obj);
            }
          }
        if(multiple)
          window.parent.returnValue=results;       
        else{
            if(results.length >0)
                window.parent.returnValue = results[0];
        }          
        if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
            window.parent.close();
        }else{
       	 window.close();
        }
      }
      function _close(){
         if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
             window.parent.close();
         }else{
        	 window.close();
         }
      }
     </script>
</head>
<body onload="doInit()"><form action="#">
      <table width="100%" class="sheetClass" cellpadding="2" cellspacing="0">
        <tr>
          <td>
            <div class="insetDiv" style="height:198px" id="navcontainer">
            </div>
          </td>
        </tr>
      </table>
      <div class="operation">
        <button type="button" onclick="ok(this.form)" class="commonbut" id="tijiao">确定</button>
        <button type="button" onclick="_close()" class="commonbut" id="back">取消</button>
      </div>   
      </form>
</body>
</html>