<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择导入方式</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<base target="_self" />
    <script language="JavaScript" type="text/Javascript">
    function doInit(){
        var sets = window.dialogArguments.CMS.ImporterSet;
        var items = window.dialogArguments.CMSImporterItems;
        for(var i=0;i<sets.length;i++){
            var option = document.createElement("option");
            option.value = sets[i];
            if(document.all){
            	option.text = items.GetItem(sets[i]).name;
	            option.desc = items.GetItem(sets[i]).desc;
	            document.getElementById('importers').options.add(option);
	            document.getElementById('importers').options[0].selected = true;
       			document.getElementById("desc").innerText = document.getElementById('importers').options[0].desc;
            }else{
            	option.textContent = items.GetItem(sets[i]).name;
	            option.setAttribute("desc",items.GetItem(sets[i]).desc);
	            document.getElementById('importers').add(option,null);
	            document.getElementById('importers').options[0].selected = true;
       			document.getElementById("desc").textContent = document.getElementById('importers').options[0].getAttribute("desc");
            }
        }  
        document.body.style.overflow = "hidden";      
    }
    function showDesc(oSel){
        document.getElementById("desc").innerText = oSel.options[oSel.selectedIndex].desc;
    }
    function check(form){
      window.returnValue = document.getElementById('importers').value;
      window.close();
    }
    </script>
</head>
<body onload="doInit()">
<table width="100%" border="0" cellpadding="2" cellspacing="0">
    <form action="#" name="expForm">
    <tr>
    <td>请选择导入方式：</td>
    </tr>
<tr><td>
    <select id="importers" name="importers" multiple="multiple" style="width:480px;height:240px;" onchange="showDesc(this)">
    </select>
    </td></tr>
    <tr>
    <td valign="top">
    <fieldset style="width:100%;height:80px">
    <div id="desc" style="padding:3px 3px 3px 3px;"></div>
    </fieldset>
    </td>
    </tr>
    <tr>
    <td>
      <div class="operation">
                        <button type="button" onclick="check(this.form)" id="btnOk">确定</button>
                        <button type="button" onclick="window.close()" id="btnCancel">取消</button>
                    </div>
                    </td>
    </tr>
    </form>
    </table>
</body>
</html>
