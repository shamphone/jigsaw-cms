

var xhr = getXMLHttpRequest();
function regist(username,password,areaS,gender,email,commonname,userID,groupID)
{
var url = "/ssi/regist.do?username="+ username +"&password="+password+"&area="+areaS+"&gender="+gender+"&email="+email+"&commonname="+commonname+"&userID="+userID+"&groupID="+groupID;
//var url = "/ssi/checkUsername.do?username="+username;
sendRequest(xhr,url,ajaxExcute);
//alert("`````````````````")
}
function ajaxExcute()
{if ((xhr.readyState==4)&&(xhr.status==200)){
  //alert("ok1");
}
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function getXMLHttpRequest(){
  http_request = false;
  //开始初始化XMLHttpRequest对象
  if (window.XMLHttpRequest) { //Mozilla 浏览器，IE7
    http_request = new XMLHttpRequest();
      if (http_request.overrideMimeType) {//设置MiME类别
        http_request.overrideMimeType('text/xml');
      }
  } else if (window.ActiveXObject) { // IE6浏览器
    try {
      http_request = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
      try {
        http_request = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (e) {}
    }
  }

  if (!http_request) { // 异常，创建对象实例失败
    window.alert("不能创建XMLHttpRequest对象实例.");
    return null;
  }
//  http_request.setRequestHeader("Content-Type","text/html; charset=UTF-8");
  return http_request;
}

function sendRequest(http_request,url,handler) {//初始化、指定处理函数、发送请求的函数
  http_request.onreadystatechange = handler;
  // 确定发送请求的方式和URL以及是否同步执行下段代码
  //http_request.setHeader("Cache-Control", "no-cache, must-revalidate");
  http_request.open("post", url, true);
  http_request.send(null);
}
