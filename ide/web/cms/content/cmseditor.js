var CMSEditor = new Object();

//原来的方法将td里所有的input都复制增加，当原有input不止一个
//时将一次增加多个，现在采取参数决定复制对象个数的方法，
//type为属性类型
//bat为是否批量
// by mali 2010-6-25
CMSEditor_addValue = function(oBtn, tBody, type ,bat,propID){
	//var oTD = oBtn.parentElement;
	//var oTR = oTD.parentElement;
	//var oInput = tBody.cloneNode(true);
	//tBody.appendChild(oNewTR);	
	//var oInput = tBody.childNodes[0].innerHTML;
	//alert(tBody.childNodes.innerHTML);
	//tBody.appendChild(oInput);	
	//for(var i=0; i<objNum; i++){
	//	alert(tBody.item[objNum].innerHTML);
	//}
	var oInput;
	var newInput;
	var text = "清空此属性值";
	switch (type) {
	case 1:
	case 3:
	case 4:
		if(!bat){
			oInput = tBody.getElementsByTagName("input")[0];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
		}else{
			oInput = tBody.getElementsByTagName("input")[0];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			oInput = tBody.getElementsByTagName("input")[1];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			tBody.innerHTML = tBody.innerHTML+text;
		}
		break;
	case 8:
		if(!bat){
			oInput = tBody.getElementsByTagName("input")[0];
			newInput = oInput.cloneNode(true);
			newInput.name = "file("+parseInt(Math.random()*999+100)+propID+")";
			tBody.appendChild(newInput);
		}else{
			oInput = tBody.getElementsByTagName("input")[0];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			oInput = tBody.getElementsByTagName("input")[1];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			tBody.innerHTML = tBody.innerHTML+text;
		}
		break;
	case 10:
		if(!bat){
			oInput = tBody.getElementsByTagName("textarea")[0];
			newInput = oInput.cloneNode(true);
			newInput.name = "file("+parseInt(Math.random()*999+100)+propID+")";
			tBody.appendChild(newInput);
		}else{
			oInput = tBody.getElementsByTagName("textarea")[0];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			oInput = tBody.getElementsByTagName("textarea")[1];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			tBody.innerHTML = tBody.innerHTML+text;
		}
		break;
	default:
		if(!bat){
			oInput = tBody.getElementsByTagName("input")[0];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			oInput = tBody.getElementsByTagName("input")[1];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
		}else{
			oInput = tBody.getElementsByTagName("input")[0];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			oInput = tBody.getElementsByTagName("input")[1];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			oInput = tBody.getElementsByTagName("input")[2];
			newInput = oInput.cloneNode(true);
			tBody.appendChild(newInput);
			tBody.innerHTML = tBody.innerHTML+text;
		}	
		break;
	}
}

CMSEditor_removeValue = function(oBtn, tBody , type,bat){
	//var oTD = oBtn.parentElement;
	//var oTR = oTD.parentElement;
	//if(tBody.rows.length ==1)
	//	return;	
	//oTR.removeNode(true);
	switch (type) {
	case 1:
	case 3:
	case 4:
	case 8:
		if(bat){
			if(tBody.getElementsByTagName("input").length <= 2)
				return;
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
		}else{
			if(tBody.getElementsByTagName("input").length == 1)
				return;
			tBody.removeChild(tBody.lastChild);
		}
		break;
	case 10:
		if(bat){
			if(tBody.getElementsByTagName("textarea").length <= 2)
				return;
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
		}else{
			if(tBody.getElementsByTagName("textarea").length == 1)
				return;
			tBody.removeChild(tBody.lastChild);
		}
		break;
	default:
		if(bat){
			if(tBody.getElementsByTagName("input").length <= 3)
				return;
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
		}else{
			if(tBody.getElementsByTagName("input").length <= 2)
				return;
			tBody.removeChild(tBody.lastChild);
			tBody.removeChild(tBody.lastChild);
		}
		break;
	}
}

CMSEditor_checkLong= function(oComp){
	if(oComp.value.length==0)
		return ;
	var value;
	try{
		value = oComp.value = parseInt(oComp.value);
		
	}catch(e){
		alert("请输入整数值");
	}	
	var	MIN_LONG = -9223372036854775808;		//java中long型数据最小值
	var	MAX_LONG = 9223372036854775807;
	if(value>=MAX_LONG||value<=MIN_LONG){
		alert("数字越界!"); 
		oComp.value="";
		oComp.focus();
		return false;
	}
}

CMSEditor_checkDouble= function(oComp){
	if(oComp.value.length==0)
		return ;
	var value;
	try{
		value = oComp.value = parseFloat(oComp.value);
	}catch(e){
		alert("请输入浮点数值");
	}
	var	ORACLE_MIN_NUMBER = 1.0e-130;		
	var	ORACLE_MAX_NUMBER = 9.99e125;			//	oracle中number型最大值
	if(value>=ORACLE_MAX_NUMBER||value<=ORACLE_MIN_NUMBER){
		alert("数字越界!"); 
		oComp.value="";
		oComp.focus();
	}
}

CMSEditor_checkDate= function(oComp){
	if(oComp.value.length==0)
		return ;
	try{
		new Date(oComp.value);
	}catch(e){
		alert("请输入浮点数值");
	}	
}

CMSEditor_selectNode = function(oInput){
	var ret = CMSDialog.NodeSelector();
	if(ret!=null)
		oInput.value = ret.id;
}

function showCalendar(btn, el, format, showsTime, showsOtherMonths) {
  if (_dynarch_popupCalendar != null) {
    _dynarch_popupCalendar.hide();
  } else {
    var cal = new Calendar(1, null, editor_selected, editor_closeHandler);
    if (typeof showsTime == "string") {
      cal.showsTime = true;
      cal.time24 = (showsTime == "24");
    }
    if (showsOtherMonths) {
      cal.showsOtherMonths = true;
    }
    _dynarch_popupCalendar = cal;
    cal.setRange(1900, 2070);
    cal.create();
  }
  _dynarch_popupCalendar.setDateFormat(format);
  _dynarch_popupCalendar.parseDate(el.value);
  _dynarch_popupCalendar.sel = el;
  _dynarch_popupCalendar.showAtElement(btn, "Br");

  return false;
}
function editor_selected(cal, date) {
  cal.sel.value = date;
  if (cal.dateClicked )
    cal.callCloseHandler();
}

function editor_closeHandler(cal) {
  cal.hide();
  _dynarch_popupCalendar = null;
}



