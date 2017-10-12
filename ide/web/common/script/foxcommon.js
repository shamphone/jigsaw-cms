/*为兼容 IE和firefox 的event对象，重写event*/
function __firefox(){
	HTMLElement.prototype.__defineGetter__("runtimeStyle", __element_style); 
	window.constructor.prototype.__defineGetter__("event", __window_event); 
	Event.prototype.__defineGetter__("srcElement", __event_srcElement); 
} 

function __element_style()
{ 
	return this.style; 
} 

function __window_event()
{ 
	return __window_event_constructor(); 
} 
function __event_srcElement()
{ 
	return this.target; 
} 
function __window_event_constructor()
{ 
	if(document.all)
	{ 
	   return window.event; 
	} 
	var _caller = __window_event_constructor.caller; 
	while(_caller!=null)
	{ 
	   var _argument = _caller.arguments[0]; 
	   if(_argument){ 
		    var _temp = _argument.constructor; 
		    if(_temp.toString().indexOf("Event")!=-1){ 
		       return _argument; 
		    } 
	   } 
	   _caller = _caller.caller; 
	} 
	return null; 
} 

if(window.addEventListener){ 
	__firefox(); 
}
/*兼容火狐下innerText*/
HTMLElement.prototype.__defineGetter__("innerText", 
function(){
    var anyString = "";
    var childS = this.childNodes;
    for(var i=0; i<childS.length; i++) {
        if(childS[i].nodeType==1)
            //anyString += childS[i].tagName=="BR" ? "\n" : childS[i].innerText;
            anyString += childS[i].innerText;
        else if(childS[i].nodeType==3)
            anyString += childS[i].nodeValue;
    }
    return anyString;
} 
); 
HTMLElement.prototype.__defineSetter__("innerText", 
function(sText){
    this.textContent=sText; 
} ); 
/*end firefox add by lkl*/