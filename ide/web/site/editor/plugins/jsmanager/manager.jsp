<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Javascript 脚本编辑器</title>
		<style type="text/css">
			body {margin:0px; overflow:hidden;}
			button {border:1px ridge transparent; padding:0px; background-color:transparent;}
			textarea {width:100%; height:100%; margin:0px; }
			#panel {width:100%; height:100%; border:1px solid #77776F; overflow:scroll; }
			#toolbar {background-color:#EFEFDE; margin:0px; height:30px; border-bottom:1px solid #77776F; font-size:13px; padding-left:10px;}
			#editArea {height:100%;}
			#list {width:200px;}
		</style>
		<script src='<html:rewrite module="/common" page="/script/ajax.js"/>' type="text/javascript"></script>
		<script src='manager.js.jsp' type="text/javascript"></script>
		<script type="text/javascript">
			var path = '<bean:write name="relativePath"/>';
			function _InitList() {
				var list = document.getElementById("list");
				var option;
				<logic:iterate id="file" name="files" indexId="ind">
					option = document.createElement("option");
					option.text = '<bean:write name="file" property="name"/>';
					option.value = '<bean:write name="file" property="name"/>';
					<logic:present name="fileName">
						<logic:equal value='${fileName}'  name="file" property="name">
							option.selected = true;
						</logic:equal>
				 	</logic:present>
					list.add(option);
				</logic:iterate>
			}
		</script>
	</head>
	<body>
		<table id="panel" cellpadding="0" cellspacing="0">
			<tr>
				<td id="toolbar" colspan="2">
					当前文件：<select id="list" onchange="_OnSelectChange(this.value)"></select>&nbsp;&nbsp;
					<button style="background:url(<html:rewrite module="/common" page="/images/new2.gif"/>) no-repeat top left; padding-left:12px;" onclick="_Create()" onmouseover="this.style.borderColor='#ACA899'" onmouseout="this.style.borderColor='transparent'">新建</button>
					<button style="background:url(<html:rewrite module="/common" page="/images/delete.png"/>) no-repeat top left; padding-left:12px;" onclick="_Delete()" onmouseover="this.style.borderColor='#ACA899'" onmouseout="this.style.borderColor='transparent'">删除</button>
					<button style="background:url(<html:rewrite module="/common" page="/images/save2.gif"/>) no-repeat top left; padding-left:12px;" onclick="_Save()" onmouseover="this.style.borderColor='#ACA899'" onmouseout="this.style.borderColor='transparent'">保存</button>
					<button style="background:url(<html:rewrite module="/common" page="/images/search.gif"/>) no-repeat top left; padding-left:7px;" onclick="_Find()" onmouseover="this.style.borderColor='#ACA899'" onmouseout="this.style.borderColor='transparent'">查找/替换</button>
				</td>
			</tr>
			<tr>
				<td>
					<textarea id="editArea" valign="top" height="100%"></textarea>
				</td>
			</tr>
		</table>
	</body>
</html>