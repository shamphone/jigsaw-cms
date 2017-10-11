<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title></title>
        <base target="_self">
        <meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
		<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/xmlhttp.js"/>"></script>
        <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
 	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
        <script language="Javascript" type="text/javascript">
      function addCategroy(form){
        var url='<bean:write name="fromURL"/>service/content/common/addRemoteCategory.do?id='+GetRadioObject(form.categorys).value+'&categoryName='+GetRadioObject(form.categorys).categoryName+'&randnum=' + Math.random();
        window.location.href=url;
        //showModalDialog(url,"","dialogWidth:620px;dialogHeight:350px;help:no;scrollbars:yes;status:no");
        ///window.close();
      }
    </script>
    <script language="Javascript" type="text/javascript">
			
			window.onload = function(){
				if(!document.all){
					document.getElementById("toolbar").style.top = document.body.clientHeight-40;
					document.getElementById("toolbar").style.left = 105;
					document.getElementById("toolbar").style.textAlign = "center";
					document.getElementById("pannelSelect").style.height = document.body.clientHeight-2;
					document.body.style.overflow = "hidden"
				}
			}
			document.onkeydown = function() {
				var btn;
				if (event.keyCode == 13) {
					var src = event.srcElement;
				 	if (!src.tagName || src.tagName.toLowerCase() != "textarea") 
				  		btn = document.getElementById("btnOk");
				} else if (event.keyCode == 27) {
				 	btn = document.getElementById("btnCancel");
				}
				if (btn && !btn.disabled) {
					btn.click();
					if (event.keyCode == 13)
						return false;
				}
			}
        </script>

   <base target="_self"/>
    </head>
    <body>
    <form action="attrMatch.do">
    <table width="99%" class="sheetClass" cellpadding="0" cellspacing="0" border="0" id="importTable">
      <tr>
        
        <td>
          <div style="width:100%;height:280px;overflow-y:scroll;border:2px inset #f1f1f1;background-color:white">
            <fulong:xtree name="categoryTree" nodeId="categoryNode">
              <fulong:xtreeText>
                <input style="height:13px;" type="radio" name="categorys" id="<bean:write name="categoryNode" property="ID"/>" value="<bean:write name="categoryNode" property="ID"/>" categoryName='<bean:write name="categoryNode" property="name"/>'/><label for="<bean:write name="categoryNode" property="ID"/>"><bean:write name="categoryNode" property="name"/></label>
              </fulong:xtreeText>
            </fulong:xtree>
          </div>
      </tr>
    </table>
    <div class="operation">
      <button type="button" class="commonbut" id="btnOk" onclick="addCategroy(this.form)">确定</button>
      <button type="button" class="commonbut" id="btnCancel" onclick="window.close()">取消</button>
    </div>
    </form>
   </body>
</html>
