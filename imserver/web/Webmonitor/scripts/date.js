function changeMonth(){
var year = document.getElementById( "year" ).value;
var month = document.getElementById( "month" ).value;
var date = document.getElementById( "date" ).options;
var length=0;
if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
length=31;
}
else if(month==4||month==6||month==9||month==11){
length=30;
}
else{
if(year%4==0){
length=29;
}
else{
length=28;
}
}
date.length=1;
for (i=1;i<=length;i++){
date[i]=new Option(i,i);
}
}
function fillYear(year,begin,to){
var years = document.getElementById(year).options;
var beginYear = (begin==null)?2000:begin;
var endYear = (to==null)?2050:to;
var length = years.length = endYear-beginYear;
for (i=1;i<=length;i++){
    years[i]=new Option(i+beginYear,i+beginYear);
}
}

function fillMonth(year,month){
var years = document.getElementById(year);
var months = document.getElementById(month).options;
if(years.value==""){
    months.length=1;
    return;
}
var length = months.length = 12;
for (i=1;i<=9;i++){
    months[i]=new Option(i,"0"+i);
}
for (i=10;i<=12;i++){
    months[i]=new Option(i,i);
}
}

function fillDate(year,month,date){
var year = document.getElementById(year).value;
var month = document.getElementById(month).value;
var date = document.getElementById(date).options;
if(year=="" && month==""){
    date.length=1;
    return;
}
var length=0;
if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
length=31;
}
else if(month==4||month==6||month==9||month==11){
length=30;
}
else{
if(year%4==0){
length=29;
}
else{
length=28;
}
}
date.length=1;
for (i=1;i<=9;i++){
    date[i]=new Option(i,"0"+i);
}
for (i=10;i<=length;i++){
    date[i]=new Option(i,i);
}
}
      function setDate(item,value){
        var par = document.getElementById(item).options;
        var length = par.length
        for (i=1;i<length;i++){
          if (par[i].value==value)
          par[i].selected=true;
        }
      }
  function creatDateString(year,month,date){
    var par;
    var tmp_year = year;
    var tmp_month = month;
    var tmp_date = date;
    if (tmp_year == ""){
      return "";
    }
    if (tmp_month == ""){
      tmp_month = "01";
    }
    if (tmp_date == ""){
      tmp_date = "01";
    }
    par = tmp_year + "-" + tmp_month + "-" + tmp_date;
    return par;
  }

    function buildMonth(year, month, day){
      var selectedYear = year.value;
      var months = month.options;
      var days = day.options;
      if (selectedYear == "") {
        months.length = 1;
        days.length = 1;
        return;
      }
      months.length = 13;
      days.length = 1;
      for (i = 1; i <= 12; i++) {
        months[i] = new Option(i,i);
      }
    }

    function buildDay(year, month, day) {
      var selectedYear = year.value;
      var selectedMonth = month.value;
      var days = day.options;
      if (selectedYear == "" && selectedMonth == "") {
        days.length = 1;
      }
      var length = 0;
      if (selectedMonth == 1 || selectedMonth == 3 ||
          selectedMonth == 5 || selectedMonth == 7 ||
          selectedMonth == 8 || selectedMonth == 10 ||
          selectedMonth == 12) {
        length = 31;
      } else if (selectedMonth == 4 || selectedMonth == 6 ||
                 selectedMonth == 9 || selectedMonth == 11) {
        length = 30;
      } else if (selectedYear%4 == 0) {
        length = 29;
      } else {
        length = 28;
      }
      days.length = length + 1;
      for (var i = 1; i <= length; i++) {
        days[i] = new Option(i, i);
      }
    }

function initArray(){
  this.length=initArray.arguments.length;
  for(var i=0;i<this.length;i++)
    this[i+1]=initArray.arguments[i];
}

function today(){
today=new Date();
var hours = today.getHours();
var minutes = today.getMinutes();
var seconds = today.getSeconds();
var timeValue = ((hours >12) ? hours -12 :hours);
timeValue += ((minutes < 10) ? "<BLINK>:</BLINK>0" : "<BLINK>:</BLINK>") + minutes;
timeValue += (hours >= 12) ? "<I><B>pm</B></I>" : "<B><I>am</I></B>";

var d=new initArray("星期日","星期一","星期二","星期三","星期四","星期五","星期六"); 
alert('hehe');
document.write("",today.getYear(),"年","",today.getMonth()+1,"月","",today.getDate(),"日 ",d[today.getDay()+1]);
}
