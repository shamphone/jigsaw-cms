<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="title">创建RSS页面</tiles:put>
  <tiles:put name="javascript">
  	<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
	<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>" />
	<link rel="stylesheet" type="text/css" href="<html:rewrite module="/site" page="/channel/create.css"/>" />
 	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/dialog.jsp"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/classes/site.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/cmsdialog.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/filterParser.js.jsp"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/cms" page="/classes/objects.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/portlet.js"/>"></script>
	<script language="Javascript" type="text/Javascript" src="rss.js"></script>
	<script language="Javascript" type="text/Javascript" src="create.js"></script>
	<base target="_self" />
    <style type="text/css">
      .errorTip {color:red}
      .indent {margin-left:6px}
      .title {text-align:right}
      select {width:120px}
    </style>
  </tiles:put>
  <tiles:put name="dialog">
    <form>
    <table cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="pannelDiv">
		<select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
			<option value="0" selected="selected">基本设置</option>
			<option value="1">item设置</option>
			<option value="2">样式设置</option>
			<option value="3">image设置</option>
			<option value="4">高级设置</option>
		</select></td>
		<td valign="top">
		<fieldset>
      <table border="0" style="margin-top: 10px;" cellpadding="1" cellspacing="1" align="center" width="100%">
        <tr>
          <td nowrap="nowrap">栏目名称<span style="color: red;">&nbsp;*</span></td>
          <td>
            <input type="text" value="<bean:write name="name"/>" onchange="validateChannel(this.form)" style="IME-MODE: disabled" name="name" maxlength="32" title="系统中的ID及文件名称，全模板唯一。"/>
          </td>
        </tr>
        <tr>
       	 	<td>显示名称<span style="color: red;">&nbsp;*</span></td>
	        <td>
	           <input type="text" value="<bean:write name="displayName"/>" name="displayName" title="能响应该频道的网站栏目URL"/>
	        </td>
        </tr>
        <tr>
          <td>频道语言<span style="color: red;">&nbsp;*</span></td>
          <td>
          	 <select style="width: 205px;" name="language" title="频道所使用的语言">
              <logic:iterate id="language" name="languages">
                <bean:define id="ID" name="language" property="ID" type="String"/>
                <option value="<%=ID%>"><cms:node name="language" propertyName="title"/></option>
              </logic:iterate>
            </select>
          </td>
        </tr>
        <tr>
        <td nowrap="nowrap">关联分类<span style="color: red;">&nbsp;*</span></td>
          <td>
          	<input id="definitionID" type="hidden" name="definitionID">
            <input id="title" type="text" size="26" name="title" readonly="readonly"/>
         	<input type="button" class="btnMore" onclick="selectCategory(document.getElementById('definitionID'),document.getElementById('title'))" value="选择...">
          </td>
        </tr>
        <tr>
        	<td>频道URL<span style="color: red;">&nbsp;*</span></td>
	          <td>
	            <input type="text" name="link" title="能响应该频道的网站栏目URL"/>
	          </td>
	        <td>&nbsp;</td>
        </tr>
        <tr>
          <td valign="top">频道描述<span style="color: red;">&nbsp;*</span></td>
          <td >
            <textarea name="description" rows="4" cols="23" title="对该频道的描述信息"></textarea>
          </td>
        </tr>
        <!-- 
        <tr>
          <td nowrap="nowrap">负责人Email</td>
          <td>
            <input type="text" name="managingEditor" title="内容负责人的Email"/>
          </td>
        </tr>
        <tr>
          <td nowrap="nowrap">技术人员Email</td>
          <td>
             <input type="text" name="webMaster" title="技术人员的Email"/>
          </td>
        </tr>
        <tr>
          <td>所属分类</td>
          <td>
            <input type="text" name="category" title="定义频道所属的一个或多个种类"/>
          </td>
        </tr>
      <tr>
          <td nowrap="nowrap">生成频道程序名</td>
          <td>
             <input type="text" name="generator" title="生成该频道的程序名称"/>
          </td>
        </tr>
         -->  
         <tr>
          <td>文档URL</td>
          <td>
            <input type="text" name="docs" title="指向rss格式文档的url地址"/>
          </td>
        </tr>
        <tr>
          <td>缓存时间</td>
          <td>
             <input type="text" name="ttl" title="cache的有效保存时间。"/>
          </td>
        </tr>
        <tr>
        	<td>版权声明</td>
	        <td>
	        	<input type="text" name="copyright" title="版权声明"/>
	        </td>
        </tr>
      </table>
      </fieldset>
       <fieldset style="display: none;">
      <table border="0" cellpadding="2" cellspacing="2" align="left" width="100%" id="styleTable">
		<tbody>
		<tr>
			<td>item数<span style="color: red;">&nbsp;*</span></td>
			<td>
				<input id="itemNum" value="20" type="text" name="itemNum">
			</td>
		</tr>
		<tr>
			<td>标题<span style="color: red;">&nbsp;*</span></td>
			<td>
				<input type="hidden" name="itemTitle" id="itemTitle">
				<input type="text" readonly="readonly" name="itemTitleName" id="itemTitleName">
				<input type="button" class="btnMore"  onclick="selectProperty(document.getElementById('definitionID').value,document.getElementById('itemTitle'),document.getElementById('itemTitleName'))" value="选择...">
			</td>
		</tr>		
		<tr>
			<td>超链接<span style="color: red;">&nbsp;*</span></td>
			<td>
				<input id="itemLink" type="hidden" name="itemLink">
				<input id="itemLinkName" type="text" readonly="readonly" name="itemLinkName" id="link">
				<input type="button" class="btnMore"  onclick="selectChannel(document.getElementById('itemLink'),document.getElementById('itemLinkName'))" value="选择...">
			</td>
		</tr>			
		<tr>
			<td>描述<span style="color: red;">&nbsp;*</span></td>
			<td>
				<input type="hidden" name="itemDescription" id="itemDescription">
				<input type="text" readonly="readonly" name="itemDescriptionName" id="itemDescriptionName">
				<input type="button" class="btnMore"  onclick="selectProperty(document.getElementById('definitionID').value,document.getElementById('itemDescription'),document.getElementById('itemDescriptionName'))" value="选择...">
			</td>
		</tr>
		<tr>
			<td>作者</td>
			<td>
				<input id="itemAuthor" type="hidden" name="itemAuthor">
				<input id="itemAuthorName" type="text" readonly="readonly" name="itemAuthorName">
				<input type="button" class="btnMore"  onclick="selectProperty(document.getElementById('definitionID').value,document.getElementById('itemAuthor'),document.getElementById('itemAuthorName'))" value="选择...">
			</td>
		</tr>		
		<tr>
			<td>发布时间</td>
			<td>
				<input id="itemPubDate" type="hidden" name="itemPubDate">
				<input id="itemPubDateName" type="text" readonly="readonly" name="itemPubDateName">
				<input type="button" class="btnMore"  onclick="selectProperty(document.getElementById('definitionID').value,document.getElementById('itemPubDate'),document.getElementById('itemPubDateName'))" value="选择...">
			</td>
		</tr>
		</tbody>
		</table>
      </fieldset>
      <fieldset style="display: none;">
      		<table border="0" cellpadding="2" cellspacing="2" align="left" width="100%" id="styleTable">
		<tbody>
		<tr><td>页面上使用的样式：</td>	</tr>		
		<tr><td><select id="currentStyles" name="style" style="width:320px;height:120px" multiple="multiple"></select></td>	</tr>
		<tr><td><button onclick="moveOptions(this.form.allStyles, this.form.currentStyles)"> ^ 添加</button><button onclick="deleteOption(this.form.currentStyles)"> v 删除</button> <button onclick="upperShift(this.form.all('currentStyles'))">上移</button><button onclick="lowerShift(this.form.all('currentStyles'))">下移</button></td>	</tr>
		<tr><td><select id="allStyles" name="allStyles" style="width:320px;height:120px" multiple="multiple"></select></td>	</tr>
		</tbody>
		</table>
      </fieldset>
      <fieldset style="display: none;">
        <table border="0" cellpadding="2" cellspacing="2" align="left" width="100%" id="styleTable">
			<tbody>
				<tr>
					<td colspan="2"><input name="img" onclick="click_chk(this)" class="checkbox" type="checkbox" id="img"/><label for="img">包含image</label></td>
				</tr>		
				<tr>
					<td>图片<span id="imageLinkTip" style="color: red;display: none;">&nbsp;*</span></td>
					<td>
						<input disabled="disabled" type="text" name="imageLink" id="imageLink">
						<input type="button" class="btnMore"  onclick="selectImage(document.getElementById('imageLink'))" value="选择...">
					</td>
				</tr>
				<tr>
					<td width="50px">标题<span id="imageTitleTip" style="color: red;display: none;">&nbsp;*</span></td>
					<td>
						<input type="text" name="imageTitle" id="imageTitle">
					</td>
				</tr>		
				<tr>
					<td>描述<span id="imageDescriptionTip" style="color: red;display: none;">&nbsp;*</span></td>
					<td>
						<input type="text" name="imageDescription" id="imageDescription">
					</td>
				</tr>		
			</tbody>
		</table>
      </fieldset>
      <fieldset style="display: none;">
	      <table border="0" cellpadding="0" cellspacing="0" width="100%">
	      		<tr>
                <td width="60px">&nbsp;</td>
                <td><input type="checkbox" style="width:25px" name="recursive" id="recursive" value="true"/><label for="recursive">包含子分类</label></td>
              </tr>
              <tr>
                <td></td>
                <td><input type="checkbox"  style="width:25px" name="global" id="global" value="true"/><label for="global">从本系统所有网站的内容库中抽取</label></td>
              </tr>
	   			<tr>
	              <td>排序属性</td>
	              <td><input type="hidden" name="orderField"/>
	                <input type="text" name="fieldName" readonly="readonly" />
	                <button type="button" class="commonbut" id="search" onclick="selectProperty(form.elements['definitionID'].value,form.elements['orderField'],form.elements['fieldName'])">选择...</button>
	                <button type="button" class="commonbut" id="clear" onclick="clearTxt(form.elements['orderField'],form.elements['fieldName'])">清空</button>
	                </td>
	             </tr>
	             <tr>
	               <td></td>
	               <td>
		               <input id="ascOrder" type="radio" style="width:25px" value="ASC" name="orderStyle"><label for="ascOrder">升序</label> &nbsp;&nbsp;
		               <input id="descOrder" checked="checked" type="radio"  style="width:25px" value="DESC" name="orderStyle"/><label for="descOrder">降序</label></td>
	             </tr>
	             <tr>
                  <td colspan="2" class="formComponent">
                 	<span >过滤条件<span><br/>
                  	<select multiple="multiple" id="filterPatterns" name="filterPatterns" size="10" style="width:350px;">
                    </select>
                    <div style="margin-top:5px;" align="right">
                    	<button type="button" class="commonbut" onclick="newFilter(form.elements['definitionID'],form.elements['filterPatterns'])">添 加</button>
                    	<button type="button" class="commonbut" onclick="deleteOption(form.elements['filterPatterns'])">删 除</button>
                    </div>                    
				  </td>
                </tr>
	      </table>
      </fieldset>
      <div class="toolbar">
		<button id="btnOk" onclick="doOK()">确定</button>
		<button id="btnCancel" onclick="window.close();">取消</button>
		</div>
      </td>
	</tr>
</table>
    </form>
  </tiles:put>
</tiles:insert>
