<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html>
    <head>
        <title>插入占位符</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="EXPIRES" content="0"/>
        <meta http-equiv="Pragma" Content="No-cach" />
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/list.css"/>" />
        <link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/dialog.jsp"/>">
        <script type="text/javascript" language="javascript">
            var oEditor = window.dialogArguments;
            var FCKLang = oEditor.FCKLang;
            var FCKPortlets = oEditor.FCKPortlets ;
            var FCK = oEditor.FCK;
            function doBodyInit(){
                oEditor.FCKLanguageManager.TranslatePage(document);
                var posfix=""+new Date().getTime();
                posfix=posfix.substring(posfix.length-6);
                portletForm.portletID.value="pt"+posfix;
                portletForm.pannelSelect.selectedIndex=0;
                portletForm.portletType[0].options[0].selected = true;
                selectIntroduction(portletForm.portletType[0]);
            }

            function Ok(){
                if((portletForm.portletID.value==null)||(portletForm.portletID.value.length==0))
                {
                    alert("占位符标识不能为空");
                    return;
                }
                for(var j=0;j<portletForm.portletType.length;j++){
                    for(var i=0;i<portletForm.portletType[j].options.length;i++){
                        if(portletForm.portletType[j].style.display != "none")
                         if(portletForm.portletType[j].options[i].selected){
                        	 FCKPortlets.Add(portletForm.portletType[j].options[i].value,portletForm.portletID.value,portletForm.portletType[j].options[i].getAttribute("title"));
                         }
                     }
                     }
                window.close();
            }

            function selectIntroduction($select){
                var obj=$select.options[$select.selectedIndex];
            	description.innerHTML=obj.getAttribute("introduction");
            }
            </script>
            <script language="javascript" src="<html:rewrite page='/common/script/common.js' module=''/>" type="text/javascript"></script>
            <script language="javascript" src="<html:rewrite page='/common/script/ajax.js' module=''/>" type="text/javascript"></script>
            <script language="javascript" src="/portal/script.js" type="text/javascript" ></script>
        </head>
        <body  onload="doBodyInit()"    >
        <div style="font-size:22px;">插入占位符</div>
            <table cellpadding="0" cellspacing="0" border="0"><form action="" id="portletForm">
                <input type="hidden" name="selectedPortlet"/>
                <input type="hidden" name="portletID" id="portletID"/>
                <tr>
                    <td valign="top">
                        <select style="width:110px" id="pannelSelect" name="pannelSelect" onchange="selectPanelPortlet(this)" size="15">
                            <logic:iterate id="portletCategory" name="com.fulong.longcon.portlet.container" property="portletCategories" indexId="categoryIndex">
                                <option value="<bean:write name="categoryIndex" format="#" />"><bean:write name="portletCategory" property="displayName(zh)" /></option>
                            </logic:iterate>
                        </select>
                    </td>
                    <td valign="top"><logic:iterate id="portletCategory" name="com.fulong.longcon.portlet.container" property="portletCategories" indexId="categoryIndex">
                    

                           
                        <select  style="width:280px;display:<logic:notEqual name="categoryIndex" value="0">none</logic:notEqual>" id="portletType" name="portletType" onchange="selectIntroduction(this)" size="15">
                            <logic:iterate id="portletDefinition" name="portletCategory" property="portletDeginitions" indexId="categoryIndex">
                                <option introduction="<bean:write name="portletDefinition" property="description(zh)"/>"  title="<bean:write name="portletDefinition" property="displayName(zh)"/>" value="<bean:write name="portletDefinition" property="name"/>"><bean:write name="portletDefinition" property="displayName(zh)" /></option>
                            </logic:iterate>
                        </select>
                        
                          <!--
                        <table width="100%" cellpadding="2" cellspacing="2" border="0">
                            <logic:iterate id="portletDefinition" name="portletCategory" property="portletDeginitions" length="12">
                                <tr>
                                    <td class="formTitle">
                                    <input onclick="description.innerHTML='<bean:write name="portletDefinition" property="description(zh)"/>'" style="width:25px" type="radio" id="<bean:write name="portletDefinition" property="name"/>" value="<bean:write name="portletDefinition" property="name"/>" title="<bean:write name="portletDefinition" property="displayName(zh)"/>" name="portletType"/><label for="<bean:write name="portletDefinition" property="name"/>"><bean:write name="portletDefinition" property="displayName(zh)"/></label></td>
                                    </tr></logic:iterate>
                                </table>
                           </td>
                           <td valign="top">
                        <table width="100%" cellpadding="2" cellspacing="2" border="0">
                            <logic:iterate id="portletDefinition" name="portletCategory" property="portletDeginitions" offset="12">
                                <tr>
                                    <td class="formTitle"><input onclick="description.innerHTML='<bean:write name="portletDefinition" property="description(zh)"/>'" style="width:25px" type="radio" id="<bean:write name="portletDefinition" property="name"/>" value="<bean:write name="portletDefinition" property="name"/>" title="<bean:write name="portletDefinition" property="displayName(zh)"/>" name="portletType"/>
                                        <label for="<bean:write name="portletDefinition" property="name"/>"><bean:write name="portletDefinition" property="displayName(zh)"/></label></td>
                                    </tr></logic:iterate>
                                </table>
                                -->

                       </logic:iterate>
                        </td>
                    </tr>
                </form></table>
                <fieldset class="insetDiv" style="height:100px;width:380px;width:390px\9;" >
                <span id="description"></span>
                </fieldset>
                <div class="operation">
	       		<button type="button" onclick="Ok()" class="commonbut">确定</button>
	          	<button type="button" onclick="window.close()" class="commonbut">取消</button>
                </div>
                </body>
            </html>
