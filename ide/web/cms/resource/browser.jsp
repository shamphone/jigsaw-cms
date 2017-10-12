<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <title>资源浏览器</title>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
    <style type="text/css">
    	body {padding-left:15px; background-color:#ECE9D8;}
    	iframe {border:1px solid #7F9DB9;}
    	.toolbar {font-size:10pt;}
    	.active {border:1px solid #7F9DB9; }
    	#uploadButton, #downloadButton, #deleteButton {cursor:pointer; padding-left:18px; vertical-align:text-bottom; padding:1px 2px 0px 18px; }
    	#uploadButton {background:url(<html:rewrite module="/common" page="/images/export.gif"/>) no-repeat top left;}
    	#downloadButton {background:url(<html:rewrite module="/common" page="/images/import.gif"/>) no-repeat top left;}
    	#deleteButton {background:url(<html:rewrite module="/common" page="/images/delete.gif"/>) no-repeat top left;}
    	#days {width:200px;}
    </style>
    <script language="javascript" type="text/javascript" src="browser.js"></script>
    <script language="javascript" type="text/javascript">
		resourceType = '<bean:write name="resourceType" ignore="true"/>';
    </script>
  </head>
  <body>
  	<table width="100%">
  		<tr>
  			<td>
			  	<table width="100%">
			  		<tr class="toolbar">
			  			<td>
			  				<span id="uploadButton" onclick="upload()" onmouseover="this.className='active'" onmouseout="this.className=''">上传</span>
			  				<span id="downloadButton" onclick="download()" onmouseover="this.className='active'" onmouseout="this.className=''">下载</span>
			  				<span id="deleteButton" onclick="Delete()" onmouseover="this.className='active'" onmouseout="this.className=''">删除</span>
			  			</td>
			  			<td align="right">
					        <span>排序：</span>
					        <select id="selSortBasis" onchange="refresh()">
					          <option id="fileName" value="fileName"><span>名称</span></option>
					          <option id="fileSize" value="fileSize"><span>大小</span></option>
					          <option id="fileType" value="fileType"><span>类型</span></option>
					          <option id="createdTime" value="createdTime"><span>创建时间</span></option>
					        </select>
					        <select id="selSortType" onchange="refresh()">
					          <option id="asc" value="asc"><span>升序</span></option>
					          <option id="desc" value="desc"><span>降序</span></option>
					        </select>
					        <span>查看：</span>
					        <select id="selRenderPattern" onchange="refresh()">
					          <option value="thumbnail"><span>缩略图</span></option>
					          <option value="detail"><span>详细信息</span></option>
					          <option value="icon"><span>图标</span></option>
					        </select>
			  			</td>
			  		</tr>
			  	</table>
			  	<hr>
		  	</td>
	  	</tr>
  		<tr>
  			<td>
			  	<table width="100%">
			  		<tr>
			  			<td width="200px" valign="top">
			  				<select id="days" size="31" onclick="if (this.value) document.all('viewer').src='<html:rewrite module="/cms/resource" page="/view.do"/>?timestamp=' + new Date().getTime() + '&day=' + this.value + '&resourceType=' + resourceType;">
			  					<logic:iterate id="day" name="days" type="String[]">
			  						<option value="<%=day[0] %>"><%=day[1]%></option>
			  					</logic:iterate>
			  				</select>
			  			</td>
			  			<td>
			  				<iframe name="viewer" width="100%" scrolling="no" height="500px" frameborder="0" src="javascript:void(0)"></iframe>
			  			</td>
			  		</tr>
			  	</table>
		  	</td>
	  	</tr>
	  	<tr>
	  		<td align="right">
	  			<hr>
			  	<button onclick="ok()">确 定</button>&nbsp;<button onclick="window.close()">取 消</button>
			</td>
	  	</tr>
  	</table>
  </body>
</html>
