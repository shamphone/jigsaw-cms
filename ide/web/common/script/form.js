
var CCheckBoxGroup    =   function(items){
    this.items= items;
}
/**
 * 选中的值
 */
CCheckBoxGroup.prototype.Values   =   function(){
    if(this.items==null)
        return null;
    var values=new Array();
   try{
        for(var i=0;i<this.items.length;i++){
            if(this.items[i].checked)
                values.push(this.items[i].value);
        }
    }catch(e){
        if(this.items.checked)
            values.push(this.items.value);
    }
    return values;
}
/**
 * 转换成查询字符串
 */
CCheckBoxGroup.prototype.ToParamString   = function(){
    var values="";
    try{
        for(var i=0;i<this.items.length;i++){
            if(this.items[i].checked)
                values+="&"+this.items[i].name+"="+this.items[i].value;
        }
        return values;
    }catch(e){
        if(this.items.checked)
            return this.items.name+"="+this.items.value;
    }
    return "";

}
/**
 * 转移到第pageNo页
 */
    function turnPage(form,pageNo){
       if(pageNo==null||pageNo==""){
    pageNo = 1 ;
    }
       form.pageNo.value=pageNo;
       form.submit();
    }
/**
* 选择排序
*/
  function  changeOrderBy(form){
     form.pageNo.value=1;
     form.submit();
  }
/**
* 按钮设置为不可选
*/
  function  disableButton(){
   var buttons=document.body.getElementsByTagName("button");
   for(i=0;i<buttons.length;i++){
     buttons[i].disabled="true";
   }
  }
/**
* 按钮设置为可选
*/
  function  enableButton(){
   var buttons=document.body.getElementsByTagName("button");
   for(i=0;i<buttons.length;i++){
     buttons[i].disabled="";
   }
  }
