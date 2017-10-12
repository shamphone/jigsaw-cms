        var xhr = null;
        var isusername = "false";
        function checkUsernameFinal(userForm)      //提交信息时的验证
        {//alert(isusername)
        if(isusername == "false")
        alert("您的用户名还未正确填写！");
        else
        {
          check(userForm);
        }
        }
        function checkUsername(un,ssiurl)     //填写用户名时的验证
        { alert("this is localhost");
          var username = un;
          if(username == "" || username == null)
          alert("用户名不能为空");
          else if(username != "" || username != null)
          {
            xhr = getXMLHttpRequest();
            //var url = '<html:rewrite module="/ssi" page="/checkUsername.do"/>?username='+username;

            var url = "http://192.168.0.218/ssi/checkUsername.do?username="+username;
            //alert("remote check run:"+url)
            sendRequest(xhr,url,ajaxExcute);
            }
        }

        function ajaxExcute()
        { alert("xhr.readyState="+xhr.readyState+",xhr.status="+xhr.status);
          if((xhr.readyState==4)&&(xhr.status==200))
          { //alert("remote feeback run");
            var data = xhr.responseText;
            if(data != null && data != "")
            {
              if(data == "false")
              { document.getElementById("username.tips").style.color="red";
                document.getElementById("username.tips").innerText="*该用户名已被注册，请重新填写用户名！";
                isusername = "false";
                //alert("对不起，该用户名已被使用！");
              }
              else
              {
                document.getElementById("username.tips").style.color="green";
                document.getElementById("username.tips").innerText="*该用户名可以使用。";
                isusername = "true";
                //alert("该用户名可以使用！");
              }
            }
          }
        }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
