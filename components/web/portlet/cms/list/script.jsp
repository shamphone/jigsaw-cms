<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

      function searchNodeDefinition(){
        url = "../../common/modalWrapper.jsp?title="
        +encodeURIComponent(encodeURIComponent("选择分类"))+
        "&url="+encodeURI('../cms/dialog/searchNodeDefinition.do?ID=no-properties-scheme');
        var arr = showModalDialog(url,window,"dialogWidth:320px;dialogHeight:270px;help:no;scrollbars:yes;status:no");
        if(arr!=null){
          document.getElementById("categoryName").value = arr.name;
          document.getElementsByName("preference(category)")[0].value = arr.ID;
          var oSelFilter = document.getElementsByName("preference(filter-fields)")[0];
          populate_select("<html:rewrite page='/enumProperties.do' module='/cms'/>?category="+arr.ID+"&timestamp"+new Date(),oSelFilter,true);
          addOption(oSelFilter,"", "请选择属性...",0);
        }
      }

      function doSelectFilterProperty(_select){
        if(_select.value.length!=0){
          var categoryId=_select.form.elements['preference(category)'].value;
          searchContent(categoryId);
        }
      }

      function searchContent(categoryId){
        url = "../../common/modalWrapper.jsp?title="
        +encodeURIComponent(encodeURIComponent("选择内容"))+
        "&url="+encodeURI('../cms/content/searchCont.do?category='+categoryId);
        var arr = showModalDialog(url,window,"dialogWidth:600px;dialogHeight:570px;help:no;scrollbars:yes;status:no");
        if(arr!=null){
          document.getElementById("fieldValue").value = arr.name;
          document.getElementsByName("preference(filter-values)")[0].value = arr.ID;
        }
      }
var oSels0= new Object();
var oSels1= new Object();
var oSels2= new Object();
var oSels3= new Object();
var oSels4= new Object();
var oSels5= new Object();
var oSels6= new Object();
var oSels7= new Object();
var oSels8= new Object();
var oSels9= new Object();
var oSels10= new Object();
var oSels11= new Object();
var oSels12= new Object();

var categoryId = "";
/**
 * list/edit.jsp
 */
 function list_syn_format(oField){
  var oRepos=oField.form.elements["repository-id"];
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
var oSelFilter=oSelect.form.elements['preference(filter-fields)'];
//var oSelOrder=oSelect.form.elements['preference(order-field)'];
var oSels0=oSelect.form.elements['arrayPreference(block-fields).value[0]'];
var oSels1=oSelect.form.elements['arrayPreference(block-fields).value[1]'];
var oSels2=oSelect.form.elements['arrayPreference(block-fields).value[2]'];
var oSels3=oSelect.form.elements['arrayPreference(block-fields).value[3]'];
var oSels4=oSelect.form.elements['arrayPreference(block-fields).value[4]'];
var oSels5=oSelect.form.elements['arrayPreference(block-fields).value[5]'];
var oSels6=oSelect.form.elements['arrayPreference(block-fields).value[6]'];
var oSels7=oSelect.form.elements['arrayPreference(block-fields).value[7]'];
var oSels8=oSelect.form.elements['arrayPreference(block-fields).value[8]'];
var oSels9=oSelect.form.elements['arrayPreference(block-fields).value[9]');
var oSels10=oSelect.form.elements['arrayPreference(block-fields).value[10]'];
var oSels11=oSelect.form.elements['arrayPreference(block-fields).value[11]'];
var oSels12=oSelect.form.elements['arrayPreference(block-fields).value[12]'];
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
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels8,true);
    addOption(oSels8, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels9,true);
    addOption(oSels9, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels10,true);
    addOption(oSels10, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels11,true);
    addOption(oSels11, "","请选择属性...",0);
    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels12,true);
    addOption(oSels12, "","请选择属性...",0);

}
}
/**
 * 更改了内容分类，并填充工作流状态
 */
function getMethod (oFields,categoryId){
populate_fix_select("<html:rewrite page='/cms/fixProperties.do' module=''/>?category="+categoryId,oFields,false);
}
function doSelectCategoryState(oSelect){
var oSelFilter=oSelect.form.elements['preference(filter-fields)'];
//var oSelOrder=oSelect.form.elements['preference(order-field]'];
 oSels0=oSelect.form.elements['arrayPreference(block-fields).value[0]'];
 oSels1=oSelect.form.elements['arrayPreference(block-fields).value[1]'];
 oSels2=oSelect.form.elements['arrayPreference(block-fields).value[2]'];
 oSels3=oSelect.form.elements['arrayPreference(block-fields).value[3]'];
 oSels4=oSelect.form.elements['arrayPreference(block-fields).value[4]'];
 oSels5=oSelect.form.elements['arrayPreference(block-fields).value[5]'];
 oSels6=oSelect.form.elements['arrayPreference(block-fields).value[6]'];
 oSels7=oSelect.form.elements['arrayPreference(block-fields).value[7]'];
 oSels8=oSelect.form.elements['arrayPreference(block-fields).value[8]'];
 oSels9=oSelect.form.elements['arrayPreference(block-fields).value[9]'];
 oSels10=oSelect.form.elements['arrayPreference(block-fields).value[10]'];
 oSels11=oSelect.form.elements['arrayPreference(block-fields).value[11]'];
 oSels12=oSelect.form.elements['arrayPreference(block-fields).value[12]'];
 categoryId=oSelect.options[oSelect.selectedIndex].value;
if((categoryId!=null)&&(categoryId.length>0)){
//    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSelOrder,true);
//    addOption(oSelOrder, "","请选择属性...",0);
    populate_select("<html:rewrite page='/enumProperties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSelFilter,true);
    addOption(oSelFilter,"ID", "内容ID",1);
    addOption(oSelFilter,"", "请选择属性...",0);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels0,true);
    addOption(oSels0,"ID", "内容ID",1);
    addOption(oSels0, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels0,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels1,true);
    addOption(oSels1,"ID", "内容ID",1);
    addOption(oSels1, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels1,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels2,true);
    addOption(oSels2,"ID", "内容ID",1);
    addOption(oSels2, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels2,categoryId)",4000);


    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels3,true);
    addOption(oSels3,"ID", "内容ID",1);
    addOption(oSels3, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels3,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels4,true);
    addOption(oSels4,"ID", "内容ID",1);
    addOption(oSels4, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels4,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels5,true);
    addOption(oSels5,"ID", "内容ID",1);
    addOption(oSels5, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels5,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels6,true);
    addOption(oSels6,"ID", "内容ID",1);
    addOption(oSels6, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels6,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels7,true);
    addOption(oSels7,"ID", "内容ID",1);
    addOption(oSels7, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels7,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels8,true);
    addOption(oSels8,"ID", "内容ID",1);
    addOption(oSels8, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels8,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels9,true);
    addOption(oSels9,"ID", "内容ID",1);
    addOption(oSels9, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels9,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels10,true);
    addOption(oSels10,"ID", "内容ID",1);
    addOption(oSels10, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels10,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels11,true);
    addOption(oSels11,"ID", "内容ID",1);
    addOption(oSels11, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels11,categoryId)",4000);

    populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),oSels12,true);
    addOption(oSels12,"ID", "内容ID",1);
    addOption(oSels12, "","请选择属性...",0);
    window.setTimeout("getMethod(oSels12,categoryId)",4000);

    var stateSelect = oSelect.form.elements['preference(state)'];
    var stateSelect1 = oSelect.form.elements['preference(toState)'];
    if(stateSelect!=null){
      populate_select("<html:rewrite page='/states.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),stateSelect,true);
      addOption(stateSelect, "","请选择状态...",0);
    }
    if(stateSelect1!=null){
      populate_select("<html:rewrite page='/states.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),stateSelect1,true);
      addOption(stateSelect1, "","请选择状态...",0);
    }
    var editFieldsSelect = oSelect.form.elements['preferences(canditions)'];
    if(editFieldsSelect!=null){
      populate_select("<html:rewrite page='/properties.do' module='/cms'/>?category="+categoryId+"&timestamp"+new Date(),editFieldsSelect,true);
    }
  var editFieldsSelected = oSelect.form.elements['preferences(editFields)'];
   if(editFieldsSelected!=null){
   var opt = editFieldsSelected.options;
     for(j=0;j<opt.length;j++){
        opt.options.remove(j);
        j--;
     }
   }
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
      oSelect.removeChild(options[selected]);
      oSelect.options.add(opt,selected-1);
    }
  }
  function nav_moveDownSelected(oSelect){
    var selected=oSelect.selectedIndex;
    if((selected>=0)&&(selected<oSelect.options.length-1)){
      var opt=oSelect.options[selected];
     oSelect.removeChild(opt);
      oSelect.options.add(opt,selected+1);
    }
  }
function nav_removeSelected(oSelect){
    for(var i=0;i<oSelect.options.length;i++){
      if(oSelect.options[i].selected){
       oSelect.removeChild(options[i]);
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
        if(document.all){
        newOp.text=fromOp.text;
        toSelect.options.add(newOp);
        }else{
        newOp.textContent=fromOp.textContent;
        toSelect.add(newOp,null);
        }
      }
    }
  }
