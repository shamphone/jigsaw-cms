<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<script language="javascript" src="/ide/common/script/portlet.js" type="text/javascript" ></script>
<portlet:form action="save" method="POST" onsubmit="return submit_navigator(this)">
  <table cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td class="pannelDiv" rowspan="2"><select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
        <option value="0" selected="selected"><bean:message key="com.fulong.site.portlet.horizonal.edit.option0.channelSet" bundle="siteHorizonal"/></option>
        <option value="1"><bean:message key="com.fulong.site.portlet.horizonal.edit.option1.styleSet" bundle="siteHorizonal"/></option>
      </select></td>
      <td><fieldset>
        <table cellpadding="2" cellspacing="2" border="0" class="formTable">
          <tr>
            <th align="left"><bean:message key="com.fulong.site.portlet.horizonal.edit.chosenAbleChannel" bundle="siteHorizonal"/></th>
            <th></th>
            <th align="left"><bean:message key="com.fulong.site.portlet.horizonal.edit.currentChannel" bundle="siteHorizonal"/></th>
          </tr>
          <tr>
            <td  class="navSelectors"><select name="canditions" multiple="multiple" size="18">
              <logic:iterate id="cand" name="channels">
                <option value="<bean:write name="cand" property="name" ignore="true"/>">
                <fulong:repeater name="cand" property="depth">&nbsp;</fulong:repeater>
                <bean:write name="cand" property="displayName" ignore="true"/></option>
              </logic:iterate>
            </select></td>
            <td class="navTools">
            <button type="button" onclick="moveOption(this.form.elements['canditions'],this.form.elements['preferences(channels)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.fieldset1.button1.add" bundle="siteHorizonal"/>--&gt</button>
            <button type="button" onclick="deleteOption(this.form.elements['preferences(channels)'],this.form.elements['canditions'])">&lt--<bean:message key="com.fulong.site.portlet.horizonal.edit.fieldset1.button2.delete" bundle="siteHorizonal"/></button>
              </td>
            <td class="navSelectors"><html:select property="preferences(channels)" multiple="multiple" size="16">
              <html:options collection="selectedChannels" property="name" labelProperty="displayName"/>
            </html:select><br />
            <button type="button" onclick="upperShift(this.form.elements['preferences(channels)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.fieldset1.button3.up" bundle="siteHorizonal"/></button>
            <button type="button" onclick="lowerShift(this.form.elements['preferences(channels)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.fieldset1.button4.down" bundle="siteHorizonal"/></button>
          </td>
        </tr>
      </table>
    </fieldset><fieldset style="display:none">
      <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
        <tr>
          <td></td>
          <td class="formComponent"><html:checkbox  style="width:25px" property="preference(target)" value="_blank"/><bean:message key="com.fulong.site.portlet.horizonal.edit.fieldset1.openInNewWindows" bundle="siteHorizonal"/></td>
        </tr>
        <tr>
          <td class="formTitle"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle1.tableStyle" bundle="siteHorizonal"/></td>
          <td class="formComponent"><html:text property="preference(list-style)"/>
          <button type="button" onclick="selectStyle(this.form.elements['preference(list-style)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle1.tableStyle.choose" bundle="siteHorizonal"/></button>
          </td>
        </tr>
        <tr>
          <td class="formTitle"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle2.channelStyle" bundle="siteHorizonal"/></td>
          <td class="formComponent"><html:text property="preference(page-style)"/>
          <button type="button" onclick="selectStyle(this.form.elements['preference(page-style)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle2.channelStyle.choose" bundle="siteHorizonal"/></button>
          </td>
        </tr>
        <tr>
          <td class="formTitle"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle3.chosenStyle" bundle="siteHorizonal"/></td>
          <td class="formComponent"><html:text property="preference(page-style-selected)"/>
          <button type="button" onclick="selectStyle(this.form.elements['preference(page-style-selected)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle3.chosenStyle.choose" bundle="siteHorizonal"/></button>
          </td>
        </tr>
        <tr>
          <td class="formTitle"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle4.separator" bundle="siteHorizonal"/></td>
          <td class="formComponent"><html:text property="preference(seperator)"/></td>
        </tr>
        <tr>
          <td class="formTitle"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle5.separatorStyle" bundle="siteHorizonal"/></td>
          <td class="formComponent"><html:text property="preference(seperator-style)"/>
          <button type="button" onclick="selectStyle(this.form.elements['preference(seperator-style)'])"><bean:message key="com.fulong.site.portlet.horizonal.edit.formTitle5.separatorStyle.choose" bundle="siteHorizonal"/></button>
          </td>
        </tr>
      </table>
    </fieldset></td></tr>
    <tr><td>
    <div class="toolbar">
       <button type="submit" onClick="submit_navigator(this.form)">保存</button>
       <button type="button" onclick="window.parent.close()">取消</button>
    </div>
  </td>
</tr>
</table>
</portlet:form>
<script type="text/javascript">
  function submit_navigator(oForm){
    selectAll(oForm.elements["preferences(channels)"]);
    return true;
  }
   function moveOption(fromSelect,toSelect){
     var selected = new Array();;
    for(var i=0;i<fromSelect.options.length;i++){
      var fromOp=fromSelect.options[i];
      if((fromOp.selected)&&(!optionExists(fromOp.value,toSelect))){
        var newOp=document.createElement("option");
        newOp.value=fromOp.value;
        newOp.text=fromOp.text;
        toSelect.options.add(newOp);
      }
    }
  }
  </script>
