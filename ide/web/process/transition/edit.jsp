<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>活动属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="../style.css" />
<script language="Javascript" type="text/Javascript" src="../script.js"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite page='/script/common.js' module='/common'/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite page='/classes/cmsdialog.js' module='/cms'/>"></script>
<script language="Javascript" type="text/Javascript">
	var PMEditor = parent.frames["mainFrame"].PMEditor;
	var PMDefinition = parent.frames["mainFrame"].PMEditor.definition;
	var transition = PMEditor.selectedObject;
	var definitionID = PMDefinition.getParameters().getValue("definitionID");
	window.onload = function() {
		actForm["ID"].value = transition.getId();
		actForm["name"].value = transition.getName();
		actForm["description"].value = transition.getDescription();
		for ( var i = 0; i < PMDefinition.activities.length; i++) {
			var act = PMDefinition.activities[i];
			addOption(actForm["from"], act.getId(), act.getName());
			addOption(actForm["to"], act.getId(), act.getName());
		}
		if(transition.getFrom()!=null)
			actForm["from"].value = (transition.getFrom().getId());
		if(transition.getTo()!=null)
			actForm["to"].value = (transition.getTo().getId());
		actForm["type"].value = transition.getType();
		var conditions = transition.getCondition();
		if (conditions != null&&conditions != ""){
			var splits = conditions.split("AND");
			for (i = 0; i < splits.length; i++) {
				addOption(actForm["filterPatterns"], splits[i], splits[i]);
			}
		}
	};
	function updateName(name) {
		transition.setName(name);
	}
	function updateFrom(id) {
		transition.setFrom(PMDefinition.getActivity(id));
		transition.relocate();
		transition.hilight();
	}
	function updateTo(id) {
		transition.setTo(PMDefinition.getActivity(id));
		transition.relocate();
		transition.hilight();
	}
	function updateType(type) {
		transition.setType(type);
	}
	function updateDesc(desc) {
		transition.setDescription(desc);
	}
	function updateCondition(cond) {
		transition.setCondition(cond);
	}

	function addOption(oSel, value, text) {
		var opt = document.createElement("option");
		opt.value = value;
		opt.text = text;
		opt.title = text;
		oSel.options.add(opt);
	}
	function updateCondition() {
		var oSel = document.getElementById('filterPatterns');
		var cond = '';
		for ( var i = 0; i < oSel.options.length; i++) {
			if (oSel.options[i].value.length > 0) {
				if (cond.length > 0)
					cond = cond + ' AND ';
				cond = cond + oSel.options[i].value;
			}
		}
		transition.setCondition(cond);
	}

	function addFilter() {
		var oSelect = document.getElementById('filterPatterns');	
	        var filter=CMSDialog.FilterEditor(definitionID);
	        if(filter!=null){
	          var newOption=document.createElement("option");
	          newOption.value=filter.value;
	          newOption.text=filter.name;
	          oSelect.add(newOption);
	        }
  
		updateCondition();
	}
	function delFilter() {
		var oSel = document.getElementById('filterPatterns');
		oSel.options.remove(oSel.selectedIndex);
		updateCondition();
	}
</script>
</head>
<body>
<table cellpadding="2" cellspacing="0" border="0">
	<form name="actForm">
	<tr>
		<td valign="top">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<th>ID：</th>
			</tr>
			<tr>
				<td><input type="text" name="ID" size="15" readonly="readonly" /></td>
			</tr>
			<tr>
				<th>名称：</th>
			</tr>
			<tr>
				<td><input type="text" name="name" size="15" onblur="updateName(this.value)" /></td>
			</tr>

		</table>
		</td>
		<td valign="top">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<th>起始活动：</th>
			</tr>
			<tr>
				<td><select name="from" onchange="updateFrom(this.options[this.selectedIndex].value)"></select></td>
			</tr>
			<tr>
				<th>目标活动：</th>
			</tr>
			<tr>
				<td><select name="to" onchange="updateTo(this.options[this.selectedIndex].value)"></select></td>
			</tr>
		</table>
		</td>
		<td valign="top">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<th>类型：</th>
			</tr>
			<tr>
				<td><SELECT name="type" onchange="updateType(this.value)">
					<option value="NONE">无条件转移</option>
					<option value="CONDITION">条件转移</option>
					<option value="OTHERWISE">其他</option>
					<option value="EXCEPTION">发生异常时转移</option>
					<option value="DEFAULTEXCEPTION">缺省异常转移</option>
				</SELECT></td>
			</tr>		
		</table>
		</td>
		
		<td valign="top">
		<table cellpadding="2" cellspacing="0" border="0">
			<tr>
				<th align="left">条件：</th>
			</tr>
			<tr>
				<td><select multiple="multiple" name="filterPatterns" size="3" style="width: 185px;"></select><br /></td>
			</tr>
			<tr>
				<td>
						<button class="commonbut" onclick="addFilter();">添 加</button>
						<button class="commonbut" onclick="	delFilter();">删 除</button>				
				</td>
			</tr>
		</table>

		</td>

		<td valign="top">
		<table cellpadding="2" cellspacing="0" border="0">

			<tr>
				<th>描述：</th>
			</tr>
			<tr>
				<td><textarea rows="4" cols="30" name="description" onchange="updateDesc(this.value)"></textarea></td>
			</tr>

		</table>

		</td>

	</tr>
	</form>
</table>
</body>
</html>
