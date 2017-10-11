<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
/**
 * list/edit.jsp
 */
 function list_syn_format(oField){
  var oRepos=oField.form.elements("repository-id");
  var oFormat=getSiblingElement(oField,"block-formats");
  if(oField.selectedIndex>0){
  var repos=oRepos.options[oRepos.selectedIndex].value;
  var field=oField.options[oField.selectedIndex].value;
   populate_select("<html:rewrite page='/formats.do' module=''/>?repository="+repos+"&field="+field,oFormat);
  }
}
function doSubmit(form){
 return true;
}
/**
 * 更改了内容分类
 */
function doSelectCategory(oSelect){
var oSelFilter=oSelect.form.elements('preference(filter-field)');
//var oSelOrder=oSelect.form.elements('preference(order-field)');
var oSels0=oSelect.form.elements('arrayPreference(column-fields).value[0]');
var oSels1=oSelect.form.elements('arrayPreference(column-fields).value[1]');
var oSels2=oSelect.form.elements('arrayPreference(column-fields).value[2]');
var oSels3=oSelect.form.elements('arrayPreference(column-fields).value[3]');
var oSels4=oSelect.form.elements('arrayPreference(column-fields).value[4]');
var oSels5=oSelect.form.elements('arrayPreference(column-fields).value[5]');
var oSels6=oSelect.form.elements('arrayPreference(column-fields).value[6]');
var oSels7=oSelect.form.elements('arrayPreference(column-fields).value[7]');
var categoryId=oSelect.options[oSelect.selectedIndex].value;
if((categoryId!=null)&&(categoryId.length>0)){
//    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSelOrder,true);
//    addOption(oSelOrder, "","请选择属性...",0);
    populate_select("<html:rewrite page='/enumProperties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSelFilter,true);
    addOption(oSelFilter,"", "请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels0,true);
    addOption(oSels0, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels1,true);
    addOption(oSels1, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels2,true);
    addOption(oSels2, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels3,true);
    addOption(oSels3, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels4,true);
    addOption(oSels4, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels5,true);
    addOption(oSels5, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels6,true);
    addOption(oSels6, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels7,true);
    addOption(oSels7, "","请选择属性...",0);

}
}


function doSelectCategoryState(oSelect){
var oSelFilter=oSelect.form.elements('preference(filter-field)');
//var oSelOrder=oSelect.form.elements('preference(order-field)');
var oSels0=oSelect.form.elements('arrayPreference(column-fields).value[0]');
var oSels1=oSelect.form.elements('arrayPreference(column-fields).value[1]');
var oSels2=oSelect.form.elements('arrayPreference(column-fields).value[2]');
var oSels3=oSelect.form.elements('arrayPreference(column-fields).value[3]');
var oSels4=oSelect.form.elements('arrayPreference(column-fields).value[4]');
var oSels5=oSelect.form.elements('arrayPreference(column-fields).value[5]');
var oSels6=oSelect.form.elements('arrayPreference(column-fields).value[6]');
var oSels7=oSelect.form.elements('arrayPreference(column-fields).value[7]');
var categoryId=oSelect.options[oSelect.selectedIndex].value;
if((categoryId!=null)&&(categoryId.length>0)){
//    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSelOrder,true);
//    addOption(oSelOrder, "","请选择属性...",0);
    populate_select("<html:rewrite page='/enumProperties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSelFilter,true);

    addOption(oSelFilter,"ID", "内容ID",1);
    addOption(oSelFilter,"", "请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels0,true);

    addOption(oSels0,"ID", "内容ID",1);
    addOption(oSels0, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels1,true);

    addOption(oSels1,"ID", "内容ID",1);
    addOption(oSels1, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels2,true);

    addOption(oSels2,"ID", "内容ID",1);
    addOption(oSels2, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels3,true);

    addOption(oSels3,"ID", "内容ID",1);
    addOption(oSels3, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels4,true);

    addOption(oSels4,"ID", "内容ID",1);
    addOption(oSels4, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels5,true);

    addOption(oSels5,"ID", "内容ID",1);
    addOption(oSels5, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels6,true);

    addOption(oSels6,"ID", "内容ID",1);
    addOption(oSels6, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels7,true);

    addOption(oSels7,"ID", "内容ID",1);
    addOption(oSels7, "","请选择属性...",0);


    var stateSelect = oSelect.form.elements('preference(state)');
    var stateSelect1 = oSelect.form.elements('preference(toState)');
    if(stateSelect!=null){
      populate_select("<html:rewrite page='/states.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),stateSelect,true);
      addOption(stateSelect, "","请选择状态...",0);
      stateSelect.value="end";
    }
    if(stateSelect1!=null){
      populate_select("<html:rewrite page='/states.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),stateSelect1,true);
      addOption(stateSelect1, "","请选择状态...",0);
    }
    var editFieldsSelect = oSelect.form.elements('preferences(canditions)');
    if(editFieldsSelect!=null){
      populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),editFieldsSelect,true);
    }
  var editFieldsSelected = oSelect.form.elements('preferences(editFields)');
   if(editFieldsSelected!=null){
   var opt = editFieldsSelected.options;
     for(j=0;j<opt.length;j++){
        opt.options.remove(j);
        j--;
     }
   }
}
}



function doSelectFilterProperty(oCategory,oProperty,oValue){
 var categoryId=oCategory.options[oCategory.selectedIndex].value;
 var propertyId=oProperty.options[oProperty.selectedIndex].value;
 if((categoryId!=null)&&(categoryId.length>0)&&(propertyId!=null)&&(propertyId.length>0)){
  populate_select("<html:rewrite page='/enumValues.do' module='/cms'/>?category="+categoryId+"&property="+propertyId+"&timestamp"+new Date(),oValue,true);
 }
}
function doChangeFormat(oCategory,oProperty,oFormat){
 var categoryId=oCategory.options[oCategory.selectedIndex].value;
 var propertyId=oProperty.options[oProperty.selectedIndex].value;
 if((categoryId!=null)&&(categoryId.length>0)&&(propertyId!=null)&&(propertyId.length>0)){
  populate_select("<html:rewrite page='/formats.do' module='/cms'/>?category="+categoryId+"&property="+propertyId+"&timestamp"+new Date(),oFormat,true);
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
        //newOp.selected = "true";
      }
    }
  }
