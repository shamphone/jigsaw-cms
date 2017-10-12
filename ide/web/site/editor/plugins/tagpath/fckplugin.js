/**
* 显示节点路径的插件,可以显示当前用户选择的节点到根节点的所有父节点。
* 1. 用户在编辑器里面点击任何一个位置，即同时显示从这个位置所在标签到BODY标签的路径
* 2. 在标签路径上点击时，显示在编辑器里面被选中的标签内容
*/
//当前高亮的span
var FCKTagPath_SelectedSpan = null;

var FCKTagPath = function()
{
}

//先创建div 在给div添加样式 ,添加W3C支持方法 modified by lkl 
FCKTagPath.prototype.Create = function( parentElement )
{
    if(document.createStyleSheet){
    	var doc=parentElement.document;
    	var div=doc.createElement("<span id='hotSelections' style='height:20px;'></span>");
        var style=document.createStyleSheet();
        style.addRule("#hotSelections span","cursor:pointer; font-size:10px; height:16px; vertical-align:middle;border:1px solid #c0c0c0;text-align:center;");
        style.addRule("#hotSelections .selected","background-color:#c0c0c0;");
    }else{
    	var doc=parentElement.ownerDocument;
    	var div = doc.createElement("span");
    	div.id = "hotSelections";
    	div.style.height = "20px";
    	//对其子节点的样式进行添加 待写
    }
    parentElement.appendChild(div);

 }

FCKTagPath.prototype.RefreshState = function()
{
    FCKTagPath_SelectedSpan=null;
    FCK.Events.AttachEvent( 'OnSelectionChange', FCKTagPath_TrackTag) ;
}

FCKTagPath.prototype.Click = function()
{
}

FCKTagPath.prototype.Enable = function()
{
	this.RefreshState() ;
}

FCKTagPath.prototype.Disable = function()
{
 hotSelections.innerHTML="";
}
/**
 * 当用户在路径显示上选中某个span时，高亮显示对应的内容
 * @tagIndex 这个内容标签对应的序号
 */
FCKTagPath_SelectTag =function(oSpan,tagIndex){
    FCKTagPath_SelectElement(oSpan,FCK.EditorDocument.all(tagIndex));
}



/**
 * 将标签路径选择的控件高亮显示
 */
function FCKTagPath_SelectElement(oSpan, elem){
  if(FCKTagPath_SelectedSpan!=null)
    FCKTagPath_SelectedSpan.className="";

  FCK.EditorDocument.selection.empty();
  try{
  var r=FCK.EditorDocument.body.createControlRange();
  r.add(elem);
  r.select();
  }catch(e){
  var r=FCK.EditorDocument.body.createTextRange();
  r.moveToElementText(elem);
  r.select();
  }
  FCKTagPath_SelectedSpan=oSpan;
  FCKTagPath_SelectedSpan.className="selected";

}

/**
 * 当用户在编辑器里面点击某个位置时，系统更新路径显示
 */
FCKTagPath_TrackTag= function (){
  FCKTagPath_SelectedSpan=null;
  var elem=FCKSelection.GetParentElement();
  var htmlcode="";
  while(( elem!= null)&& (elem.tagName!="BODY"))
  {

    if(elem.className=='portletWindow'){
    //如果当前选中了占位符标签内部,则选择整个占位符
    htmlcode="<SPAN onmouseover=\"FCKTagPath_TrackMouseOver(this)\" onmouseout=\"FCKTagPath_TrackMouseOut(this)\" onclick=\"FCKTagPath_SelectTag(this,"+elem.sourceIndex+")\">&lt"+elem.title+"("+elem.id+")&gt</SPAN>";
    }
//      FCKTagPath_SelectElement(elem);

    else
      htmlcode="<SPAN onmouseover=\"FCKTagPath_TrackMouseOver(this)\" onmouseout=\"FCKTagPath_TrackMouseOut(this)\" onclick=\"FCKTagPath_SelectTag(this,"+elem.sourceIndex+")\">&lt"+elem.tagName+"&gt</SPAN>&nbsp;&nbsp;"+htmlcode;

      elem=elem.parentElement;
  }
  hotSelections.innerHTML=htmlcode;
  //hilight the last span;
  if(FCKTagPath_SelectedSpan!=null){
  FCKTagPath_SelectedSpan=hotSelections.children[hotSelections.children.length-1];
  FCKTagPath_SelectedSpan.className="selected";
  }
}
/**
 * 鼠标经过时高亮
 */
function FCKTagPath_TrackMouseOver(oElem){
   oElem.className="selected";
}
/**
 * 鼠标移出时，非选中的就恢复
 */
function FCKTagPath_TrackMouseOut(oElem){
  if(FCKTagPath_SelectedSpan!=oElem)
   oElem.className="";
}

FCKToolbarItems.RegisterItem( 'TagPath'	, new FCKTagPath( ) ) ;
