function showCalendar(btn, id, format, showsTime, showsOtherMonths) {
  var el = id;
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
  _dynarch_popupCalendar.showAtElement(el, "Br");

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