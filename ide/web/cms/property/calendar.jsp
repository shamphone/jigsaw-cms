<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<logic:notPresent name="renderJavascript"><script type="text/javascript">
var themePath='<html:rewrite page="/calendar/skins/aqua/theme.css" module="/common"/>';
document.writeln("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\""+themePath+"\" title=\"Aqua\" />");
var  calendarJs='<html:rewrite page="/calendar/calendar.js" module="/common"/>';
document.writeln("<script type=\"text/javascript\" src=\""+calendarJs+"\"><\/script>");
var langJs='<html:rewrite page="/calendar/lang/cn_utf8.js" module="/common"/>';
document.writeln("<script type=\"text/javascript\" src=\""+langJs+"\"><\/script>");
function showCalendar(btn, id, format, showsTime, showsOtherMonths) {
  var el = document.getElementById(id);
  if (_dynarch_popupCalendar != null) {
    _dynarch_popupCalendar.hide();
  } else {
    var cal = new Calendar(1, null, editor_selected, editor_closeHandler);
    if (typeof showsTime == "string") {
      cal.showsTime = true;
      cal.time24 = (showsTime == "24");
    }
    if (showsOtherMonths) {
      cal.showsOtherMonths = true;
    }
    _dynarch_popupCalendar = cal;
    cal.setRange(1900, 2070);
    cal.create();
  }
  _dynarch_popupCalendar.setDateFormat(format);
  _dynarch_popupCalendar.parseDate(el.value);
  _dynarch_popupCalendar.sel = el;
  _dynarch_popupCalendar.showAtElement(btn, "Br");

  return false;
}
function editor_selected(cal, date) {
  cal.sel.value = date;
  if (cal.dateClicked )
    cal.callCloseHandler();
}

function editor_closeHandler(cal) {
  cal.hide();
  _dynarch_popupCalendar = null;
}
 </script></logic:notPresent><input type="text" name='<bean:write name="propertyId" ignore="true"/>'  class="textComponent" value="<bean:write name="propertyValue" ignore="true"/>" id='<bean:write name="propertyId" ignore="true"/>'/><input style="width:30px" type="reset" value=" ... " onclick="return showCalendar(this, '<bean:write name="propertyId" ignore="true"/>', '%Y-%m-%d', null, true);" />
