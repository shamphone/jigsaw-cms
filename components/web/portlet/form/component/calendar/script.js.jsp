<%@ page contentType="text/html; charset=UTF-8" %><%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- import the calendar script -->
document.write('<script type="text/javascript" src="/ide/common/calendar/calendar.js"></script>');
<!-- import the language module -->
document.write('<script type="text/javascript" src="/ide/common/calendar/lang/cn_utf8.js"></script>');
document.write('<link rel="stylesheet" type="text/css" media="all" href="/ide/common/calendar/skins/aqua/theme.css" title="Aqua" />');
<!-- helper script that uses the calendar -->

// This function gets called when the end-user clicks on some date.
function selectedCalendarComponent(cal, date) {
  cal.sel.value = date; // just update the date in the input field.
  if (cal.dateClicked && cal.sel)
    // if we add this call we close the calendar on single-click.
    // just to exemplify both cases, we are using this only for the 1st
    // and the 3rd field, while 2nd and 4th will still require double-click.
    cal.callCloseHandler();
}

// And this gets called when the end-user clicks on the _selected_ date,
// or clicks on the "Close" button.  It just hides the calendar without
// destroying it.
function closeHandlerCalendarComponent(cal) {
  cal.hide();                        // hide the calendar
//  cal.destroy();
  _dynarch_popupCalendar = null;
}

// This function shows the calendar under the element having the given id.
// It takes care of catching "mousedown" signals on document and hiding the
// calendar if the click was outside.
function showCalendarComponent(oObject, format, showsTime, showsOtherMonths) {
  var el = oObject.previousSibling;
  if (_dynarch_popupCalendar != null) {
    // we already have some calendar created
    _dynarch_popupCalendar.hide();                 // so we hide it first.
  } else {
    // first-time call, create the calendar.
    var cal = new Calendar(1, null, selectedCalendarComponent, closeHandlerCalendarComponent);
    // uncomment the following line to hide the week numbers
    // cal.weekNumbers = false;
    if (typeof showsTime == "string") {
      cal.showsTime = true;
      cal.time24 = (showsTime == "24");
    }
    if (showsOtherMonths) {
      cal.showsOtherMonths = true;
    }
    _dynarch_popupCalendar = cal;                  // remember it in the global var
    cal.setRange(1900, 2070);        // min/max year allowed.
    cal.create();
  }
  _dynarch_popupCalendar.setDateFormat(format);    // set the specified date format
  _dynarch_popupCalendar.parseDate(el.value);      // try to parse the text in field
  _dynarch_popupCalendar.sel = el;                 // inform it what input field we use

  // the reference element that we pass to showAtElement is the button that
  // triggers the calendar.  In this example we align the calendar bottom-right
  // to the button.

  _dynarch_popupCalendar.showAtElement(el.nextSibling, "Br");        // show the calendar
}
<!--
// If this handler returns true then the "date" given as
// parameter will be disabled.  In this example we enable
// only days within a range of 10 days from the current
// date.
// You can use the functions date.getFullYear() - - returns the year
// as 4 digit number, date.getMonth() - - returns the month as 0..11,
// and date.getDate() - - returns the date of the month as 1..31, to
// make heavy calculations here.  However, beware that this function
// should be very fast, as it is called for each day in a month when
// the calendar is (re)constructed.
function isDisabled(date) {
var MINUTE = 60 * 1000;
var HOUR = 60 * MINUTE;
var DAY = 24 * HOUR;
var WEEK = 7 * DAY;
  var today = new Date();
  return (Math.abs(date.getTime() - today.getTime()) / DAY) > 10;
}
-->
<!--setActiveStyleSheet(document.getElementById("defaultTheme"), "Aqua");-->


