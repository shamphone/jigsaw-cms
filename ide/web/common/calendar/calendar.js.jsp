<%@ page contentType="text/javascript; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- import the calendar script -->
document.write('<script type="text/javascript" src="<html:rewrite module="/common" page="/calendar/calendar.js"/>"></script>');
<!-- import the language module -->
document.write('<script type="text/javascript" src="<html:rewrite module="/common" page="/calendar/lang/cn_utf8.js"/>"></script>');
document.write('<link rel="stylesheet" type="text/css" media="all" href="<html:rewrite module="/common" page="/calendar/skins/aqua/theme.css"/>" title="Aqua" />');
<!-- helper script that uses the calendar -->
var _dynarch_popupCalendar=null;

function showCalendar(btn, el, format, showsTime, showsOtherMonths) {
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