<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="portlet"%>
<link rel="stylesheet" type="text/css" href="/ide/common/calendar/skins/aqua/theme.css" />
<script type="text/javascript" src="/ide/common/calendar/calendar.js"></script>
<script type="text/javascript" src="/ide/common/calendar/lang/cn_utf8.js"></script>
<script type="text/javascript" src="/components/portlet/common/timer/edit.js"></script>
<portlet:form action="save" method="POST">
  <table cellpadding="0" cellspacing="0" border="0">
  <tr>
      <td class="pannelDiv" rowspan="2">
        <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="10">
          <option value="0" selected="selected"><bean:message key="com.fulong.portal.portlet.timer.edit.option0.baseSet" bundle="portalTimer"/></option>
        </select></td>
        <td><fieldset>
          <table width="100%" cellpadding="2" cellspacing="2" border="0" class="formTable">
            <tr>
              <td class="formTitle"><bean:message key="com.fulong.portal.portlet.timer.edit.formTitle1.baseTime" bundle="portalTimer"/></td>
              <td class="formComponent">
                <html:text property="preference(time)" style="165px;"/>
                <input style="width:30px" type="reset" value=" ... " onclick="return showCalendar(this, form.elements['preference(time)'], '%Y-%m-%d %H:%M:%S', '24', true);" />
                <br />范例:年-月-日 时:分:秒
              </td>
            </tr>
            <tr>
              <td class="formTitle">显示格式</td>
              <td class="formComponent">
              <html:select property="preference(type)" style="width:173px">
              <html:option value="normal">28天 13小时 4分钟 36秒</html:option>
              <html:option value="simple">28天</html:option>
              </html:select>
              </td>
            </tr>
          </table>
        </fieldset>        
            <div class="toolbar">
	       		<button type="button" onclick="strDateTime(form.elements['preference(time)'],this.form)">保存</button>
	          	<button type="button" onclick="window.parent.close()">取消</button>
            </div></td>
      </tr>
    </table>
	</portlet:form>
	  <script type="text/Javascript" language="Javascript">
	function strDateTime(str,form)
	{
	var temp = str.value;
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = temp.match(reg); 
	if(r==null) { 
		alert("基准时间格式错误"); 
		}
	else if (r[3]==0 || r[4]==0 || r[3]>12 || r[4]>31|| r[5]>24 || r[6]>60 || r[7]>60)
	{
	alert("时间格式不对");
	}
	else {
        form.submit();
	}
	}
	</script>
