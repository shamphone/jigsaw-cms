<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择活动</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="../style.css" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="../visual/pmsconfig.js"></script>
<script language="Javascript" type="text/Javascript" src="../visual/classes/pms.js"></script>
<script language="Javascript" type="text/Javascript" src="../visual/classes/pmdefinition.js"></script>
<script language="Javascript" type="text/Javascript" src="../visual/classes/pmactivity.js"></script>
<script language="Javascript" type="text/Javascript" src="../visual/classes/pmtransition.js"></script>
<script language="Javascript" type="text/Javascript" src="../visual/classes/pmparameters.js"></script>
<script language="Javascript" type="text/Javascript">
	function doInit() {
		var processes = PMS.getProcesses(window.dialogArguments);
		for(var i=0;i<processes.length;i++){
			var opt = document.createElement("option");
			opt.value = processes[i].ID;
			if(document.all){
				opt.text = processes[i].Name;
				processForm["processes"].options.add(opt);
			}else{
				opt.textContent = processes[i].Name;
				processForm["processes"].add(opt,null);
			}
		}
	};
	function showActivities(oSelect){
		var id = oSelect.options[oSelect.selectedIndex].value;
		var definition = PMS.getProcess(id);
		var activities=definition.activities;
		removeAll(processForm["process"]);
		for(var i=0;i<activities.length;i++){
			var opt = document.createElement("option");
			if(document.all){
				opt.text  = activities[i].getName();
				opt.desc = activities[i].getDescription();
				processForm["process"].options.add(opt);
			}else{
				opt.textContent  = activities[i].getName();
				opt.setAttribute("desc",activities[i].getDescription());
				processForm["process"].add(opt,null);
			}
			opt.value = activities[i].getId();
		}
	};
	function showTips(oSelect){
		document.getElementById("tips").innerHTML = oSelect.options[oSelect.selectedIndex].getAttribute("desc");
	};
	function doOK(){
		if(processForm["process"].selectedIndex<0){
			alert("请选择活动！");
			return;			
		}
		var ret=new Object();
		ret.definition = new Object();
		if(document.all){
			ret.definition.name = processForm["processes"].options[processForm["processes"].selectedIndex].text;
		}else{
			ret.definition.name = processForm["processes"].options[processForm["processes"].selectedIndex].textContent;
		}
		ret.definition.ID = processForm["processes"].options[processForm["processes"].selectedIndex].value;
		ret.activity = new Object();
		if(document.all){
			ret.activity.name = processForm["process"].options[processForm["process"].selectedIndex].text;
		}else{
			ret.activity.name = processForm["process"].options[processForm["process"].selectedIndex].textContent;
		}
		ret.activity.ID = processForm["process"].options[processForm["process"].selectedIndex].value;
		window.returnValue = ret;
		window.close();
	};
</script>
</head>
<body onload="doInit()">
<table cellpadding="2" cellspacing="0" border="0">
	<form name="processForm">
	<tr>
		<td valign="top">选择流程</td>
		<td valign="top">请选择活动</td>
	</tr>
	<tr>
		<td valign="top"><select name="processes" style="width:300px" size="15" onchange="showActivities(this)"></select></td>
		<td valign="top"><select name="process"  size="15" style="width:300px" onchange="showTips(this)"></select></td>
	</tr>
	<tr>
		<td valign="top" colspan="2"><div id="tips" style="width:100%;border:1px inset;height:100px;padding:3px 3px 3px 3px"></div></td>
	</tr>
	</form>
</table>
 <div class="operation">
        <button  type="button" onclick="doOK()">确定</button>
        <button id="btnCancel" type="button" onclick="window.close()">取消</button>
      </div>
</body>
</html>