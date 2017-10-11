  <%@ page contentType="text/javascript; charset=UTF-8" %>
  <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
 document.write( '<script type="text/javascript" src="/ide/common/script/stack.js"></script>' );
  var xmlIndustry ;
 try
    {
   // Firefox, Opera 8.0+, Safari
    xmlIndustry=new XMLHttpRequest();
    }
 catch (e)
    {

  // Internet Explorer
   try
      {
      xmlIndustry=new ActiveXObject("Msxml2.XMLHTTP");
      }
   catch (e)
      {

      try
         {
         xmlIndustry=new ActiveXObject("Microsoft.XMLHTTP");
         }
      catch (e)
         {
         alert("您的浏览器不支持AJAX！");
         return false;
         }
      }
    }
 }
  
  function setCommonValue_Portlet(curLevel,classification,displayProp,valueProp,value){  //自适应级数的级联菜单
	  var stack = new Stack();
	  stack.Push(value);
	  var cValue = value;
	  while(cValue!=null){
		  var ret = findParentNode_Portlet(cValue);
		  if(ret != null&& ret!='1000000000000'){
		  	stack.Push(ret);
		  	cValue = ret;
		  }else{
		   break;
		  }
	  }
	  var i=1;
	  while(!stack.IsEmpty()){
	  var cLevel = document.getElementById(classification+i);
 	  setSelectValue_portlet(cLevel,stack.Pop());
 	  changeCommonLevelNodeID(cLevel,classification,displayProp,valueProp)
 	  i++;
 	  }
  }
  function findParentNode_Portlet(value){
	  var req = getXMLHttpRequest();
	  var url = '/ide/common/getParentNode.do?contentID='+value;
	  req.open("GET",url, false);
	  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	  req.send(null);
	  if(req.status!=200){
	  alert("无法获取链接："+url);
	  return;
	  }
	  return req.responseText.length>0?req.responseText:null;
  }  
    
    function changeCommonLevelNodeID(curLevel,classification,displayProp,valueProp){  //自适应级数的级联菜单
    while(curLevel.nextSibling.tagName=="SELECT"){
    curLevel.nextSibling.parentNode.removeChild(curLevel.nextSibling);
    //curLevel.nextSibling.removeNode(true);
    }
    var curName=curLevel.name;
    var nextLevelName = "";
    var commonName=curName.substr(0,curName.length-1);
    var curLevelNum=parseInt(curName.charAt(curName.length-1));
    if(!isNaN(curLevelNum)){
    var nextLevelNum=curLevelNum+1;
    nextLevelName=commonName+nextLevelNum;
    }else{
    nextLevelName=curName+1;
    }
    var nextLevel = document.createElement("select");
    curLevel.parentNode.insertBefore(nextLevel,curLevel.nextSibling)
    //curLevel.insertAdjacentElement("afterEnd", nextLevel)
    nextLevel.id=nextLevelName;
    nextLevel.name=nextLevelName;
    nextLevel.onchange=function(){changeCommonLevelNodeID(this,classification,displayProp,valueProp)};
    curLevelTemp = curLevel.options[curLevel.selectedIndex].id;
      if(!classification){
        classification="classification";
      }
      (classification,curLevel.id);
    var newOption=document.createElement("option");
    newOption.id="";
    newOption.value="";
    newOption.text="请选择...";
    if(document.all){
    	nextLevel.add(newOption);
    }else{
    	nextLevel.add(newOption,null);
    }
    //nextLevel.add(newOption);
    populate_select_portlet("/ide/cms/ajaxEntryPortlet.do?parent_code=" + curLevelTemp +"&displayProp="+displayProp+"&valueProp="+valueProp,nextLevel,false);
    if(nextLevel.options.length==1){
    nextLevel.parentNode.removeChild(nextLevel);
    }
    //changeLevel(nextLevel);
}

  
  /**
* 从指定的url中获取数值来填充oSelect指定的select控件的指,用于级联下拉占位符使用。
*/
function populate_select_portlet(url,oSelect,remove,selected){
  var req = getXMLHttpRequest();
  if (remove){
    removeAll(oSelect);
  }
  req.open("GET",url, false);
  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
  req.send(null);
  if(req.status!=200){
  alert("无法获取链接："+url);
  return;
  }
  var resXML=req.responseXML;
  var properties=resXML.getElementsByTagName("parameter");
  for(var i=0;i<properties.length;i++){
       var id=properties[i].getElementsByTagName("ID")[0].text;
        var name=properties[i].getElementsByTagName("name")[0].text;
        var value=properties[i].getElementsByTagName("value")[0].text;
        var newOption=document.createElement("option");
        newOption.id=id;
        newOption.value=value;
        newOption.text=name;
        if(selected!=null&&selected==id){
        	newOption.selected=true;
        }
        if(document.all){
    		oSelect.add(newOption);
	    }else{
	    	oSelect.add(newOption,null);
	    }
      }
}

function setSelectValue_portlet(oSelect,value){
              var u = oSelect.options;
              for(var i=0;i<u.length;i++){
                if(u[i].value==value){
                u[i].selected=true;
                }
              }
}
