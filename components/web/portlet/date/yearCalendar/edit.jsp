<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form  action="save" method="POST">
    <tr>
      <td class="pannelDiv">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">表格设置</option>
          <option value="1">重复内容</option>
        </select></td>
        <td>
          <fieldset>
            <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td nowrap="nowrap">单元格衬距</td>
                  <td><html:text  property="preference(cellspacing)" size="8" onblur="validateInt(this)"/></td>
                  <td>间距</td>
                  <td><html:text  property="preference(cellpadding)" size="8" onblur="validateInt(this)"/></td>
                </tr>
                <tr>
                  <td>边框粗细</td>
                  <td><html:text  property="preference(border)" size="8" onblur="validateInt(this)"/></td>
                  <td>颜色</td>
                  <td><html:text  property="preference(bordercolor)" size="8"/></td>
                </tr>
                <tr>
                  <td>表格宽度</td>
                  <td><html:text  property="preference(width)" size="8"/></td>
                  <td>高度</td>
                  <td><html:text  property="preference(height)" size="8"/></td>
                </tr>
                <tr>
                  <td nowrap="nowrap">表格行数</td>
                  <td><html:text property="preference(row)" size="8" onblur="validateInt(this)"/></td>
                  <td nowrap="nowrap">表格列数</td>
                  <td><html:text property="preference(column)" size="8" onblur="validateInt(this)"/></td>
                </tr>
                <tr>
                  <td>表格样式类</td>
                  <td colspan="3"><html:text  property="preference(table-style)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(table-style)'])">选择...</button>
                  </td>
                </tr>            
                <tr>
                  <td>表格样式</td>
                  <td colspan="3"><html:text  property="preference(style)" size="30"/>
                  </td>
                </tr>
                <tr>
                  <td>当月样式类</td>
                  <td colspan="3"><html:text  property="preference(today-style)"/>
                  <button type="button" class="commonbut" onclick="selectStyle(this.form.elements['preference(today-style)'])">选择...</button>
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td colspan="3">从第<html:text property="preference(startloc)" style="width:60px" onblur="validateInt(this),checkRange(this)"/>条开始展示</td>
                </tr>
              </table>
            </fieldset>
            <fieldset style="display:none">
              <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                <tr>
                  <td class="formTitle">重复内容</td>
                  <td class="formComponent" width="80%"><bean:parameter id="formDefinition" name="formDefinition" value="" />
                  <html:hidden property="preference(contextName)"/>
          			<button  type="button" onclick="editClipFile('<bean:write name="path"/>',form.elements['preference(category)'],document.getElementById('editorFrame'),'<bean:write name="formDefinition" ignore="true"/>',window.parent.dialogArguments.styleSheets)">修改...</button>
                  </td>
                </tr>
                <tr>
                  <td colspan="2">
                    <iframe scrolling="yes" marginheight="0" marginwidth="0" frameborder="1" src='<bean:write name="clipPath"/>?javax.portlet.page.mode=view' width="350px" height="280px" id="editorFrame">                </iframe>
                  </td>
                </tr>
              </table>
            </fieldset>
            <div class="toolbar">
          		<input type="submit" value="保存"/>
          		<button type="button" onclick="window.parent.close()">取消</button>
            </div>
      </td>
    </tr>
  </portlet:form>
  </table>
<script type="text/javascript" language="javascript">
  document.getElementsByName('preference(contextName)')[0].value=window.parent.dialogArguments.template.name;
  function checkRange(startloc){
	    var arr = startloc.value;
		if(arr == 0 || arr > 12){
			alert("只能输入1-12之间的整数");
			startloc.value='1';
		}
	  }
	function validateInt(o){
		  if(validatorInteger(o)==false){
		  	o.value = 0;
		  }
	}
</script>