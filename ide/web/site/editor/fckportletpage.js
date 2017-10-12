/**
 * 模板页面
 * @param contextPath: webppp的路径http://www.xxx.com/context
 * @param pageFile：页面文件所在路径
 */
var FCKPortletPage =  function(contextPath,pageFile){
    this.contextPath=contextPath;
    this.pageFile=pageFile;
    this.previewFile=pageFile+".bak.bak";
    this.workingFile=pageFile+".bak";
    this.node=null; //缺省的节点参数
    this.definition=null; //缺省的内容定义参数
}

/**
 * 加载编辑状态的页面
 */
FCKPortletPage.prototype.LoadEditingHTML = function(){
    var url=this.contextPath +this.workingFile;
    alert(url)
    if(url.indexOf('?')>0)
      url=url+'&';
    else
      url=url+"?";
    url=url+"javax.portlet.page.mode=view&timestamp="+new Date().getTime();
    var req = getXMLHttpRequest();
    var editingData;
    req.open("GET",url,false);
    req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
    req.send(null);
    if (req.status==200)
        editingData=req.responseText;
      else
        editingData=null;

    return editingData;
};


/**
 * 发布内容
 */
FCKPortletPage.prototype.Publish = function() {
  var req = getXMLHttpRequest();
  var url= 'publishPage.do';
  url=url+"?path="+encodeURIComponent(currentPath)+"&timeStamp=" + new Date().getTime();
  req.open("GET",url,false);
  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
  req.send(null);
 if (req.status==200)
 alert("发布成功！");
      else
 alert("发布失败，错误代码"+req.status+".");

}

/**
 * 恢复内容
 */
FCKPortletPage.prototype.Rollback = function () {
  if(!confirm("确实要放弃当前的修改,恢复到最近发布的版本?"))
    return;
  var req = getXMLHttpRequest();
  var url= 'restorePage.do';
  url=url+"?path="+encodeURIComponent(currentPath)+"&timeStamp=" + new Date().getTime();
  req.open("GET",url,false);
  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
  req.send(null);
  if (req.status==200){
    alert("恢复成功！");
    window.location.reload();
  }
 else
    alert("恢复失败，请和系统管理员联系！");
}
/**
 * 删除页面
 */
FCKPortletPage.prototype.DeleteChannel = function() {
  if(currentPath.indexOf("index.jsp")>0) {
  alert("这是首页页面,不能删除!");
  return;
  }

  if ( confirm( "删除页面将是无法恢复的操作,请确定！" ) ){
      document.location="deleteChannel.do?path="+encodeURIComponent(currentPath);
  }
}
