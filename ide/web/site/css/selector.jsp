<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame">
  <tiles:put name="dialog">
  <div>样式名称（命名规范请参考css官方文档）</div>
  <div style="margin-top:6px;"><input type="text" size="40" name="selector" /></div>
        <div class="operation">
          <button type="submit"onclick="doClose()">确定</button>
          <button type="submit"onclick="window.close()">取消</button>
        </div>
  </tiles:put>
<tiles:put name="javascript">
<script language="javascript" type="text/Javascript">
  window.onload = function() {
	  var defaultSelector = window.top.dialogArguments;
	  window.body.overflow = "hidden";
	  if (defaultSelector)
	  	document.getElementById("selector").value = defaultSelector;
  }
  function doClose(){
	  var name = document.getElementById("selector").value;
	  if (!name) {
		  alert("请输入样式名称");
		  return;
	  }
	  window.returnValue = name;
	  window.close();
  }
</script>
</tiles:put>
 </tiles:insert>
