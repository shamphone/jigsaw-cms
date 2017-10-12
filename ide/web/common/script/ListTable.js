
/**
 * 列表框架页中对列表的建模
 */
var ListTable = {
    SelectedRow    :    null,
    SelectedRows   :    new Array(),
    List      :    null,
    definitionID    :   null,
    subDefinitionId    :   null,
    SelectedRowClassName  :   "selectedRow", //选中的列的样式
    ActiveRowClassName  :   "activeRow", //高亮的列的样式

    /**
     * 初始化
     */
     Init:function(oTable){
        for(i=0;i<oTable.tBodies.length;i++){
            for(j=0;j<oTable.tBodies[i].rows.length;j++){
                var oRow=oTable.tBodies[i].rows[j];
                oRow.onmouseover=function(){ListTable._OnMouseOverRow(this);};
                oRow.onmouseout=function(){ListTable._OnMouseOutRow(this);};
                oRow.onclick=function(){ListTable._OnClickRow(this);};
            }
        }
    },
    /**
    * 页面上可以覆盖实现这个方法来完成对列选中事件的处理
    */
    OnRowSelected : function(oRow){
    },
    /**
    * 页面上可以覆盖实现这个方法来完成对列删除事件的处理
    */
    OnRowDeleted : function(oRows){
    },

    /**
    * 高亮行
    */
    HilightRow   :  function(rowId){
            document.getElementById(rowId).className=this.ActiveRowClassName;
    },
    /**
    * 检查是否已经选中行
    */
    SelectedCheck    :   function(){
        if(ListFrame.SelectedRow!=null){
            return true;
        }else{
            alert("请选择内容！");
            return false;
        }
    },
    /**
     * 上移选中的行
     */
    MoveRowsUp  :   function(){
        for(var i=0;i<this.SelectedRows.length;i++){
            var num=this.SelectedRows[i].rowIndex;
            if(num>0)
                this.List.moveRow(num,num-1);
        }
    },
    /**
     * 下移选中的行
     */
    MoveRowsDown  :   function(){
        for(var i=0;i<this.SelectedRows.length;i++){
            var num=this.SelectedRows[i].rowIndex;
            if(num>0)
                this.List.moveRow(num,num-1);
        }
    },
    /**
     * 删除选定的行
     */
    DeleteRows   :   function(){
        for(var i=0;i<this.SelectedRows.length;i++)
            this.List.deleteRow(this.SelectedRows[i]);
        this.OnRowDeleted(this.SelectedRows);
    },
    /**
    * 高亮当前选中的列
    */
    _OnClickRow    :   function(oRow, ctrlKey){
        oRow.className=this.SelectedRowClassName;
        if(navigator.userAgent.indexOf("Firefox")>0){
        	for(var i=0;i<this.SelectedRows.length;i++){
                if(this.SelectedRows[i]!=oRow)
                this.SelectedRows[i].className="";
            }
            this.SelectedRow=oRow;
            this.SelectedRows.length=0;
            this.SelectedRows.push(oRow);
        }else{
        	if(window.event.ctrlKey || ctrlKey){
                this.SelectedRows.push(oRow);
                this.SelectedRow=oRow;
            }else{
                for(var j=0;j<this.SelectedRows.length;j++){
                    if(this.SelectedRows[j]!=oRow)
                    this.SelectedRows[j].className="";
                }
                this.SelectedRow=oRow;
                this.SelectedRows.length=0;
                this.SelectedRows.push(oRow);
            }
        }
        this.OnRowSelected(oRow);
    },
    /**
    * 鼠标移动时高亮
    */
    _OnMouseOverRow   :  function(oRow){
        if(!this.SelectedRows.contains(oRow)){
            oRow.className=this.ActiveRowClassName;
        }
    },
    /**
    * 鼠标退出时恢复
    */
    _OnMouseOutRow   :   function(oRow){
        if(!this.SelectedRows.contains(oRow)){
            oRow.className="";
        }
    },
    URLBuilder :   function(action,par){
        var parameter='';
        for(var i=0;i<this.SelectedRows.length;i++){
            parameter=parameter+"&"+par+'='+this.SelectedRows[i].id;
        }
        if(parameter.length>0)
        parameter="?"+parameter.substring(1,parameter.length);
        else
        parameter="?tmp=";
        return action+parameter;
    }
};
