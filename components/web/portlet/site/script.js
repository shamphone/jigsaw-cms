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
