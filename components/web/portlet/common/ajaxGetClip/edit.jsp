<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script type="text/Javascript" src='/ide/common/script/portlet.js" '></script>
<table cellpadding="0" cellspacing="0" border="0">
  <portlet:form action="save" method="POST">
    <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected">基本设置</option>
        </select>
      </td>
      <td>
        <fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle">页面片段</td>
              <td>
                <html:hidden property="preference(source)"/>
                <input type="text" name="sourceName" readonly="readonly" value="<bean:write name="channel" property="displayName" ignore="true"/>" />
                <button type="button" class="commonbut" id="searchN" onclick="selectorChannel(this.form.elements['preference(source)'],this.form.elements['sourceName'],'<bean:write name="siteTemplate" property="name"/>','<bean:write name="channel" property="name"/>')">选择...</button>
              </td>
            </tr>
            <tr>
              <td class="formTitle" valign="top">权限设置</td>
              <td nowrap="nowrap"  class="selectionEditor">              
                 <table cellpadding="0" cellspacing="0" border="0" >
                 <tr><td>
                <select multiple="multiple" name="preferences(clip-paths)" size="5" style="width:210px;">
                  <logic:present name="mappings">
                    <logic:iterate id="mapping" name="mappings">
                      <option value='<bean:write name="mapping" property="value" filter="false"/>'><bean:write name="mapping" property="label" filter="false"/></option>
                    </logic:iterate>
                  </logic:present>
                </select>
                    </td><td valign="top">
                  <button type="button" class="commonbut" onclick="newClip(form.elements['preferences(clip-paths)'],'<bean:write name="siteTemplate" property="name"/>')">添加...</button><br/>
                  <button type="button" class="commonbut" onclick="upperShift(this.form.elements['preferences(clip-paths)'])">上 移</button><br/>
                  <button type="button" class="commonbut" onclick="lowerShift(this.form.elements['preferences(clip-paths)'])">下 移</button><br/>
                  <button type="button" class="commonbut" onclick="deleteOption(form.elements['preferences(clip-paths)'])">删 除</button>
                </td></tr>
                </table>
              </td>
            </tr>
          </table>
        </fieldset>
        <div class="toolbar">
        	<button type="submit" onClick="staticaldoSaveConfig(this.form)">保存</button>
        	<button type="button" onclick="window.parent.close()">取消</button>
        </div>
      </td>
    </tr>
  </portlet:form>
</table>
<script language="javascript" src="/ide/site/dialog.js" type="text/javascript" ></script>
      <script type="text/Javascript" language="Javascript">
      /**
       *添加片段
       **/
       function newClip(oSelect,siteTemplate){
         var filter=clipPathEditor();
         if(filter!=null){
           var newOption=document.createElement("option");
           newOption.value=filter.value;
           newOption.text=filter.name;
           oSelect.add(newOption);
         }
       }
       function clipPathEditor(definitionID){
     	  var url='/ide/site/clip/clips.jsp';
     	  return showModalDialog(url,window,"dialogWidth:440px;dialogHeight:400px;help:no;scrollbars:yes;status:no");
       }

       
       /**
        * 选择类型为页面片段的栏目
        */
       function selectorChannel(oID,oName,templateId, channelId){
     	var arr = SiteDialog.selectChannel(templateId, channelId,true,false,null,['index','content','style','script']);
     	if(arr!=null){
    		if(oID!=null){
    			oID.value = arr.contextPath;
    		}
    		if(oName!=null){
    			oName.value = arr.template.displayName+"."+arr.displayName;
    		}
    	}
       }
      function staticaldoSaveConfig(oForm){
          selectAll(oForm.elements['preferences(clip-paths)'])
      }
 
      </script>
