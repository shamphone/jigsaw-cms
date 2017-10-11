<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

/**
 * 全选
 */
 function do_submit(oForm){
  selectAll(oForm.elements("preferences(fields)"));
  return true;
 }
function doSelectCategory(oCategory,oField){
var categoryId=oCategory.options[oCategory.selectedIndex].value;
if((categoryId!=null)&&(categoryId.length>0)){
    populate_select("/ide/cms/properties.do?category="+categoryId+"&timestamp"+new Date(),oField,true);
    }
}


  function nav_moveUpSelected(oSelect){
    if(oSelect.selectedIndex>0){
      var selected=oSelect.selectedIndex;
      var opt=oSelect.options[selected];
      oSelect.options.remove(selected);
      oSelect.options.add(opt,selected-1);
    }
  }
  function nav_moveDownSelected(oSelect){
    var selected=oSelect.selectedIndex;
    if((selected>=0)&&(selected<oSelect.options.length-1)){
      var opt=oSelect.options[selected];
      oSelect.options.remove(selected);
      oSelect.options.add(opt,selected+1);
    }
  }
function nav_removeSelected(oSelect){
    for(var i=0;i<oSelect.options.length;i++){
      if(oSelect.options[i].selected){
        oSelect.options.remove(i);
        i--;
      }
    }
  }
   function nav_move2(fromSelect,toSelect){
    for(var i=0;i<fromSelect.options.length;i++){
      var fromOp=fromSelect.options[i];
      if((fromOp.selected)&&(!optionExists(fromOp.value,toSelect))){
        var newOp=document.createElement("option");
        newOp.value=fromOp.value;
        newOp.text=fromOp.text;
        toSelect.options.add(newOp);
      }
    }
  }
