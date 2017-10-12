<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改栏目属性</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
<link rel="stylesheet" type="text/css" href="property.css" />
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/xtree/tree.js.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ListTable.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/process" page="/visual/classes/pms.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/process" page="/visual/classes/pmdefinition.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/process" page="/visual/classes/pmactivity.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/process" page="/visual/classes/pmtransition.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="property.js"></script>
<base target="_self" />
</head>
<body onload="doInit()">
<table cellpadding="0" cellspacing="0" border="0">
	<form name="channelForm">
	<tr>
		<td class="pannelDiv"><select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
			<option value="0" selected="selected">基本属性</option>
			<option value="1">样式设置</option>
			<option value="2">脚本设置</option>
			<option value="3">Meta设置</option>
		</select></td>
		<td valign="top">
		<fieldset><table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
			<tr>
				<td nowrap="nowrap">页面名称</td>
				<td><input type="text" name="name"  readonly="readonly" disabled="disabled"/></td>
			</tr>
			<tr>
				<td nowrap="nowrap">&nbsp;</td>
				<td><div style="color: red;">
				注意：栏目显示名称只有在发布后才能生效
				</div></td>
			</tr>
			<tr>
				<td nowrap="nowrap">显示名称</td>
				<td><input type="text" name="displayName" title="在页面以及客户端浏览器上显示的页面名称" /></td>
			</tr>
			<tr>
				<td>关联分类</td>
				<td colspan="3"><input type="hidden" name="definitionID" />
				<input type="text" name="definitionName" readonly="readonly"/>
				<button name="btnCategory" class="btnMore" onclick="chooseCategory(this.form);">选择...</button>
				</td>
			</tr>
			<tr>
				<td>栏目标题</td>
				<td><input type="text" onselect="setCaret(this)" onclick="setCaret(this)" onkeyup="setCaret(this)" id="title">
					<button type="button" class="btnMore"  onclick="selectProperty(document.getElementById('definitionID').value,document.getElementById('title'),['0','2','8','9','10'])">选择...</button>
				</td>
			</tr>
			<tr>
				<td>关联活动</td>
				<td>
					<input type="hidden" id="activityId"/>
					<input disabled="disabled"  type="text" id="activityName">
					<button type="button" class="btnMore"  onclick="doSelectActivity(document.getElementById('activityId'),document.getElementById('activityName'),oTemplate)" >选择...</button>
				</td>
			</tr>
		</table></fieldset>
		<fieldset style="display:none"><table border="0" cellpadding="2" cellspacing="2" align="left" width="100%" id="styleTable">
		<tbody>
		<tr><td>页面上使用的样式：</td>	</tr>		
		<tr><td><select name="currentStyles" style="width:320px;height:120px" multiple="multiple"></select></td>	</tr>
		<tr><td><button type="button" onclick="moveOptions(this.form.allStyles, this.form.currentStyles)"> ^ 添加</button><button type="button" onclick="deleteOption(this.form.currentStyles)"> v 删除</button> <button type="button" onclick="upperShift(this.form.all('currentStyles'))">上移</button><button type="button" onclick="lowerShift(this.form.all('currentStyles'))">下移</button></td>	</tr>
		<tr><td><select name="allStyles" style="width:320px;height:120px" multiple="multiple"></select></td>	</tr>
		</tbody>
		</table></fieldset>		
		<fieldset style="display:none"><table border="0" cellpadding="2" cellspacing="2" align="left" width="100%" id="styleTable">
		<tbody>
		<tr><td>页面上使用的脚本：</td>	</tr>		
		<tr><td><select name="currentScripts" style="width:320px;height:120px" multiple="multiple"></select></td>	</tr>
		<tr><td><button type="button" onclick="moveOptions(this.form.allScripts, this.form.currentScripts)"> ^ 添加</button> <button type="button" onclick="deleteOption(this.form.currentScripts)"> v 删除</button> <button type="button" onclick="upperShift(this.form.all('currentScripts'))">上移</button><button type="button" onclick="lowerShift(this.form.all('currentScripts'))">下移</button> </td>	</tr>
		<tr><td><select name="allScripts" style="width:320px;height:120px" multiple="multiple"></select></td>	</tr>
		</tbody>
		</table></fieldset>				
		<fieldset style="display:none">		
		<table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
			<tr>
				<td>字符集</td>
				<td><input readonly="readonly" type="text" name="content-type" title="设定页面使用的字符集，用以说明主页制作所使用的文字已经语言，浏览器会根据此来调用相应的字符集显示page内容。"  /></td>
				</tr>
			<tr>
				<td>关键字</td>
				<td><input type="text" name="keywords" title="关键字设置将有利于某些常见的搜索引擎对这个页面作处理。" /></td>
				</tr>
				<tr>
				<td>作 者</td>
				<td><input type="text" name="author" title="告诉搜索引擎你的站点的制作的作者。" /></td>
			</tr>
			<tr>
				<td>爬虫设置</td>
				<td><select name="robots" title="设置搜索引擎将如何处理这个页面。">
					<option value="">缺省设置</option>
					<option value="all">允许</option>
					<option value="noindex">禁止</option>
				</select></td>
				</tr><tr>				
				<td>刷新设置</td>
				<td><input type="text" name="refresh" title="页面刷新时间间隔。" /></td>
			</tr>
			<tr>
				<td>到期时间</td>
				<td><input type="text" name="expires" title="页面将在这个日期后更新，设置一个比当前时间更早的时间将使用户每一次访问都需要重新获取页面。" /></td>
				</tr><tr>				
				<td>缓存设置</td>
				<td><input type="text" name="pragma" title="设定禁止浏览器从本地机的缓存中调阅页面内容，设定后一旦离开网页就无法从Cache中再调出." /></td>
			</tr>
			<tr>
				<td>进入特效</td>
				<td><select name="pageEnter" title="进入页面时的特殊效果。">
					<option value="">无</option>
					<option value="progid:DXImageTransform.Microsoft.Barn(duration=2, motion='out', orientation='vertical')">打开</option>
					<option value="progid:DXImageTransform.Microsoft.Blinds(direction='down')">百叶窗</option>
					<option value="progid:DXImageTransform.Microsoft.CheckerBoard(duration=5, direction='left')">棋盘</option>
					<option value="progid:DXImageTransform.Microsoft.Fade(duration=2)">渐变消失</option>
					<option value="progid:DXImageTransform.Microsoft.gradientWipe(duration=3, gradientsize=0.5)">插除</option>
					<option value="progid:DXImageTransform.Microsoft.Inset()">插入</option>
					<option value="progid:DXImageTransform.Microsoft.Iris(duration=3)">辐射</option>
					<option value="progid:DXImageTransform.Microsoft.Pixelate(duration=3, enabled='false')">马赛克</option>
					<option value="progid:DXImageTransform.Microsoft.RadialWipe(duration=2)">辐射插除</option>
					<option value="progid:DXImageTransform.Microsoft.RandomBars(duration=5)">随机线</option>
					<option value="progid:DXImageTransform.Microsoft.RandomDissolve(duration=3)">溶解</option>
					<option value="progid:DXImageTransform.Microsoft.Slide(duration=3, bands='8')">滑块</option>
					<option value="progid:DXImageTransform.Microsoft.Spiral(duration=3, GridSizeX=25, GridSizeY=25)">螺旋</option>
					<option value="progid:DXImageTransform.Microsoft.Stretch(duration=3)">拉伸</option>
					<option value="progid:DXImageTransform.Microsoft.Strips(duration=5, motion='rightdown')">锯齿边覆盖</option>
					<option value="progid:DXImageTransform.Microsoft.Wheel(duration=2, spokes=8)">辐条</option>
					<option value="progid:DXImageTransform.Microsoft.Zigzag(duration=3, GridSizeX=25, GridSizeY=25)">Z字型插除</option>
				</select></td>
				</tr><tr> 				
				<td>离开特效</td>
				<td><select name="pageExit" title="离开页面时的特殊效果。">
					<option value="">无</option>
					<option value="progid:DXImageTransform.Microsoft.Barn(duration=2, motion='out', orientation='vertical')">打开</option>
					<option value="progid:DXImageTransform.Microsoft.Blinds(direction='down')">百叶窗</option>
					<option value="progid:DXImageTransform.Microsoft.CheckerBoard(duration=5, direction='left')">棋盘</option>
					<option value="progid:DXImageTransform.Microsoft.Fade(duration=2)">渐变消失</option>
					<option value="progid:DXImageTransform.Microsoft.gradientWipe(duration=3, gradientsize=0.5)">插除</option>
					<option value="progid:DXImageTransform.Microsoft.Inset()">插入</option>
					<option value="progid:DXImageTransform.Microsoft.Iris(duration=3)">辐射</option>
					<option value="progid:DXImageTransform.Microsoft.Pixelate(duration=3, enabled='false')">马赛克</option>
					<option value="progid:DXImageTransform.Microsoft.RadialWipe(duration=2)">辐射插除</option>
					<option value="progid:DXImageTransform.Microsoft.RandomBars(duration=5)">随机线</option>
					<option value="progid:DXImageTransform.Microsoft.RandomDissolve(duration=3)">溶解</option>
					<option value="progid:DXImageTransform.Microsoft.Slide(duration=3, bands='8')">滑块</option>
					<option value="progid:DXImageTransform.Microsoft.Spiral(duration=3, GridSizeX=25, GridSizeY=25)">螺旋</option>
					<option value="progid:DXImageTransform.Microsoft.Stretch(duration=3)">拉伸</option>
					<option value="progid:DXImageTransform.Microsoft.Strips(duration=5, motion='rightdown')">锯齿边覆盖</option>
					<option value="progid:DXImageTransform.Microsoft.Wheel(duration=2, spokes=8)">辐条</option>
					<option value="progid:DXImageTransform.Microsoft.Zigzag(duration=3, GridSizeX=25, GridSizeY=25)">Z字型插除</option>
				</select></td>
			</tr>
		</table>
		</fieldset>
		<div class="toolbar">
		<button type="button" id="btnOk" onclick="doOK()">确定</button>
		<button type="button" id="btnCancel" onclick="window.close();">取消</button>
		</div>
		</td>
	</tr>
</table>
</body>
</html>


