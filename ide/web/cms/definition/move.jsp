<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="javascript">
    <html:javascript formName="categoryForm"/>
    <script language="JavaScript" type="text/Javascript">
    function check(form){
      disableButton();
      if(true){
        form.submit();
      }else{
        enableButton();
      }
    }
    function setSelect(input){
        var imgs = input.parentElement.parentElement.getElementsByTagName("img");
        imgs[imgs.length-1].click();
      }
    WebFXTreeAbstractNode.prototype.focus = function() {
    	if ((webFXTreeHandler.selected) && (webFXTreeHandler.selected != this)) { webFXTreeHandler.selected.deSelect(); }
    	webFXTreeHandler.selected = this;
    	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }
    	document.getElementById(this.id + '-anchor').className = 'selected';
    	document.getElementById(this.id + '-anchor').focus();
    	document.getElementById(this.ID).checked=true;
    	if (webFXTreeHandler.onSelect) { webFXTreeHandler.onSelect(this); }
    }
    window.onload = function(){
           if(navigator.userAgent.toLowerCase().indexOf("firefox")>=0){
           	 document.body.style.overflow = "hidden";
           }
       }
    </script>
  </tiles:put>
  <tiles:put name="title">转移分类</tiles:put>
  <tiles:put name="dialog">
    <html:form action="doMove.do">
        <table width="100%" cellpadding="1" cellspacing="0" border="0">
                <input type="hidden" name="categoryID" value="<bean:write name='categoryID' ignore="true"/>"/>
                <logic:iterate id="id" name="categoryForm" property="IDs">
                    <input type="hidden" value="<bean:write name="id"/>" name="IDs">
                </logic:iterate>
                <tr>
                    
                    <td bgcolor="white">
                      <div  class="insetDiv" style="height:245px">
                      <fulong:xtree name="categoryTree" nodeId="categoryNode">
                        <fulong:xtreeText>
                          <bean:define id="ID" name="categoryNode" property="ID" type="String"/>
                          <html:radio onclick="setSelect(this)" style="height:13px;" property="otherCategoryID" value="<%=ID%>" styleId="<%=ID%>"><bean:write name="categoryNode" property="name"/></html:radio>
                      </fulong:xtreeText>
                      </fulong:xtree>
                      </div>
                    </td>
                </tr>
            </table>
            <div class="operation">
                <button type="button" onclick="check(this.form)" id="btnOk">确定</button>
                <button type="button" onclick="window.close()" id="btnCancel">取消</button> 
            </div>
        </html:form>
    </tiles:put>
</tiles:insert>
