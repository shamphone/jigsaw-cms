
/*
* 这个包不直接使用，请使用remote.js.jsp
*
*/
var HttpRequest = function (url){
    this.XMLHttp=getXMLHttpRequest();
    this.params= "";
    this.url=url;
};
/**
 * 设置远程地址
 */
HttpRequest.prototype.SetURL    =   function(url){
    this.url=url;
};
/**
 * 添加参数
 */
HttpRequest.prototype.AddParam  =   function(name, value){
    if(this.params.length>0)
        this.params+="&";
    this.params+=name+"="+encodeURIComponent(value);
};
/**
 * 发送请求
 */
HttpRequest.prototype.Send  =    function(method){
	if(method == null)
		method = "GET";
    var path=this.url;
    if(path.indexOf("?")<0)
        path+="?";
    path+=this.params;
    // 加随机数防止缓存
    if (path.indexOf("?") > 0)
    {
    	path += "&randnum=" + Math.random();
    }
    else
    {
    	path += "?randnum=" + Math.random();
    }    
    this.XMLHttp.open(method, path, false);
    this.XMLHttp.setRequestHeader("Content-Type","text/html; charset=UTF-8");
    this.XMLHttp.send(null);
    return (this.XMLHttp.status==200);
};

/**
 * 发送请求
 */
HttpRequest.prototype.GetHTML  =    function(){
	return this.XMLHttp.responseText;
};

/**
 * Get模式调用
 */
HttpRequest.prototype.Get=      function(){
    if(this.Send("GET"))
    return this.XMLHttp.responseText;
    else
    return null;
};

/**
 * 获取错误信息
 */
HttpRequest.prototype.GetErrorMessage=      function(){
	return decodeURIComponent(this.XMLHttp.getResponseHeader("message"))
};


/**
 * 获取XMLHttpRequest对象实例
 */
function getXMLHttpRequest(){
  http_request = false;
  // 开始初始化XMLHttpRequest对象
  if (window.XMLHttpRequest) { // Mozilla 浏览器，IE7
    http_request = new XMLHttpRequest();
      if (http_request.overrideMimeType) {// 设置MiME类别
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
// http_request.setRequestHeader("Content-Type","text/html; charset=UTF-8");
  return http_request;
};

/**
 * 发送请求
 */
function sendRequest(http_request,url,handler) {// 初始化、指定处理函数、发送请求的函数
  http_request.onreadystatechange = handler;
  // 确定发送请求的方式和URL以及是否同步执行下段代码
  // http_request.setHeader("Cache-Control", "no-cache, must-revalidate");
  http_request.open("post", url, true);
  http_request.send(null);
};



/* XMLHttpRequest Object Pool
 *
 * @author    legend <legendsky@hotmail.com>
 * @link      http://www.ugia.cn/?p=85
 * @Copyright www.ugia.cn
 */
var XMLHttp = {
    _objPool: [],
    _getInstance: function ()
    {
        for (var i = 0; i < this._objPool.length; i ++)
        {
            if (this._objPool[i].readyState == 0 || this._objPool[i].readyState == 4)
            {
                return this._objPool[i];
            }
        }
        // IE5中不支持push方法
    this._objPool[this._objPool.length] = this._createObj();
    return this._objPool[this._objPool.length - 1];
},
//初始化XMLHttpRequest
_createObj: function ()
{
    if (window.XMLHttpRequest)
    {
        var objXMLHttp = new XMLHttpRequest();
    }
    else
    {
        var MSXML = ['MSXML2.XMLHTTP.5.0', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
        for(var n = 0; n < MSXML.length; n ++)
        {
            try
            {
                var objXMLHttp = new ActiveXObject(MSXML[n]);
                break;
            }
            catch(e)
            {
            }
        }
     }
    // mozilla某些版本没有readyState属性
    if (objXMLHttp.readyState == null)
    {
        objXMLHttp.readyState = 0;
        objXMLHttp.addEventListener("load", function ()
            {
                objXMLHttp.readyState = 4;
                if (typeof objXMLHttp.onreadystatechange == "function")
                {
                    objXMLHttp.onreadystatechange();
                }
            },  false);
    }
    return objXMLHttp;
},
// 发送请求(方法[post,get], 地址, 数据, 回调函数)
sendReq: function (method, url, data, callback,id)
{
    var objXMLHttp = this._getInstance();
    with(objXMLHttp)
    {
        try
        {
            // 加随机数防止缓存
            if (url.indexOf("?") > 0)
            {
                url += "&randnum=" + Math.random();
            }
            else
            {
                url += "?randnum=" + Math.random();
            }
            open(method, url, true);
            // 设定请求编码方式
            setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
                send(data);
                onreadystatechange = function ()
                {
                    if (callback!=null&&objXMLHttp.readyState == 4 && (objXMLHttp.status == 200 || objXMLHttp.status == 304))
                    {
                        callback(objXMLHttp,id);
                    }
                }
            }
            catch(e)
            {
                alert(e.message);
            }
        }
    }
};

var Remote = new Object();
/**
 * 测试该地址是否是有效的Coolink系统地址
 */
Remote.testURL	=	function(url){
	var req = getXMLHttpRequest();
	    var myurl = Globals.redirector + encodeURIComponent(url+"/site/name.do");
	    req.open("GET", myurl ,false);
	    req.send(null);
	    if(req.status == 200){       
	    	alert("测试成功，连接到网站："+req.responseText);
	    	return req.responseText;
	    }else{
            alert("网址不正确或者不存在，请重新输入！错误代码："+ req.statusText);
            return null;
	    }		
};
