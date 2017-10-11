<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script language="javascript" src="/ide/common/script/common.js" type="text/javascript" ></script>
<script type="text/javascript" language="javascript">
		window.onload = function(){
			var sec = document.getElementsByName('preference(propertyId)')[0];
		  	var options = sec.options;
          	var fckDocument = window.parent.dialogArguments.FCKDocument;
      	  	if(fckDocument!=null){
      	  	  	var root = fckDocument.documentElement.cloneNode(true);
      	  		var portlets=root.getElementsByTagName("DIV");
                for(var i=0;i<portlets.length;i++){
                    if((portlets[i].className=='portletWindow') && (portlets[i].getAttribute("type")=='content-tableXrepeater')){
                        var portletId = portlets[i].id;
                        var portletName = portlets[i].getAttribute("title");
                        var option = document.createElement("option");
            			option.id = portletId;
            			option.value = portletId;
            			if(document.all){
            				option.text = portletName+portletId; 
            				options.add(option);	
            			}else{
            				option.textContent = portletName+portletId; 
            				sec.add(option,null);
            			}
                    }
                }
      	  	}
			setSelectValue(document.getElementsByName('preference(propertyId)')[0],'<bean:write name="preferences" property="value(propertyId)" />');
		}
</script>
<table cellpadding="0" cellspacing="0" border="0">
    <portlet:form action="save" method="POST">
        <html:hidden property="preference(form)" styleId="formName"/>
        <tr>
            <td class="pannelDiv" rowspan="2">
                <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
                    <option value="0" selected="selected">基本设置</option>
                </select></td>
                <td><fieldset>
                    <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
                        <tr>
                            <td class="formTitle">搭配占位符</td>
                            <td class="formComponent">
                                <html:select property="preference(propertyId)" styleId="propertyId" style="width:200px;">
                            	</html:select>
                        	</td>
                    	</tr>
                    	<tr>
		                  <td class="formTitle">滚动方向</td>
		                  <td class="formComponent">
		                    <html:select property="preference(direction)">
		                      <html:option value="left">向左</html:option>
		                      <html:option value="right">向右</html:option>
		                      <html:option value="up">向上</html:option>
		                      <html:option value="down">向下</html:option>
		                    </html:select>
		                  </td>
		                </tr>
		                <tr>
		                  <td class="formTitle">滚动方式</td>
		                  <td class="formComponent">
		                    <html:select property="preference(behavior)">
		                      <html:option value="scroll">连续滚动</html:option>
		                      <html:option value="slide">滑动一次</html:option>
		                      <html:option value="alternate">来回滚动</html:option>
		                    </html:select>
		                  </td>
		                </tr>
		                <tr>
		                  <td class="formTitle">循环次数</td>
		                  <td class="formComponent"><html:text property="preference(loop)" onblur="validatorInteger(this)"></html:text></td>
		                </tr>
		                <tr>
		                  <td class="formTitle">滚动速度</td>
		                  <td class="formComponent"><html:text property="preference(scrollamount)" onblur="validatorInteger(this)"></html:text></td>
		                </tr>
		                <tr>
		                  <td class="formTitle">停顿时间</td>
		                  <td class="formComponent"><html:text property="preference(scrolldelay)" onblur="validatorInteger(this)"></html:text></td>
		                </tr>
		                <tr>
		                  <td class="formTitle">滚动区域高度</td>
		                  <td class="formComponent"><html:text property="preference(rollheight)"></html:text></td>
		                </tr>
		                <tr>
		                  <td class="formTitle">滚动区域宽度</td>
		                  <td class="formComponent"><html:text property="preference(rollwidth)"></html:text></td>
		                </tr>
               </table>
            </fieldset>
            <div class="toolbar">
              <input type="submit" value="保存"/>
              <button type="button" onclick="window.parent.close()">取消</button>
            </div>
            </td>
        </tr></portlet:form>
</table>
