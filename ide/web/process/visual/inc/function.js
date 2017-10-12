
//-------------------下拉列表函数集----------------------------------------------------------------------------

//取select下拉列表单值
function getSelectValue(listName){
   if(listName.selectedIndex!=-1){return listName.options[listName.selectedIndex].value}
   else{return ''}
}

//取select下拉列表单文本
function getSelectText(listName){
   if(listName.selectedIndex!=-1){return listName.options[listName.selectedIndex].innerText}
   else{return ''}
}

//添加下拉列表Option
function addSelectOption(listName,optText,optValue,optSelected){
  var oOption = document.createElement("OPTION")
  listName.options.add(oOption)
  oOption.innerText = optText
  oOption.value = optValue
  oOption.selected = optSelected
  
}

//清空列表
function emptyList(list){
  for(var i=0;list.length=0;i++){list.remove(i)}
}

//复制列表1到列表2
function copyList(list1,list2){
  emptyList(list2)
  for (i=0;i<list1.length ;i++ )
  {
	 addSelectOption(list2,list1.options[i].innerText,list1.options[i].value)
  }
}

//选择多选列表所有项
function selectListAll(listName){
	for (var i=0;i<listName.length ;i++ )
	{
		listName.options[i].selected = true
	}
}

//不选择列表任何项
function selectListNone(listName){
	listName.selectedIndex = -1
}

//List2list:列表1到列表2互选的基本操作函数
function List2list_addOne(list1,list2){
  var a = list1
  var b = list2
  
  if (a.selectedIndex!=-1){
	 var flag = '0'
	 for(var i=0;i<b.length;i++){
	   if(b.options[i].value==a.options[a.selectedIndex].value) flag = '1'
	 }

	 if(flag == '0'){
	   var oOption = document.createElement("OPTION")
	   b.options.add(oOption)
	   oOption.innerText = a.options[a.selectedIndex].innerText
	   oOption.value = a.options[a.selectedIndex].value	 
	 }	
	 a.remove(a.selectedIndex)  
  }
}

function List2list_delOne(list1,list2){
  List2list_addOne(list2,list1)
}

function List2list_addAll(list1,list2){
  var a = list1
  var b = list2

  for(var i=0;i<a.length;i++){
	 var flag = '0'
	 for(var j=0;j<b.length;j++){
	   if(b.options[j].value==a.options[i].value) flag = '1'
	 }

	 if(flag == '0'){
	   var oOption = document.createElement("OPTION")
	   b.options.add(oOption)
	   oOption.innerText = a.options[i].innerText
	   oOption.value = a.options[i].value	  
	 }	   	  
  }
  emptyList(a)
}

function List2list_delAll(list1,list2){
  List2list_addAll(list2,list1)
}

//-------------------单选框、复选框函数----------------------------------------------------------------------------

//取单选框的值
function getRadioGroupValue(RadioGroup){
   for(var i=0;i<RadioGroup.length;i++)
      {if (RadioGroup[i].checked){return RadioGroup[i].value}}
   return ""
}

//选中单选框
function setRadioGroupValue(RadioGroup,SetValue){
   if (SetValue!='')
   {
	  for(var i=0;i<RadioGroup.length;i++)
      {if (RadioGroup[i].value == SetValue){RadioGroup[i].checked = true;return true}}
	  return false
   }
   else {return false}   
}

//取单个复选框的值
function getSingleCheckBoxValue(checkBoxName){
  if(checkBoxName.checked){return checkBoxName.value}
  else{return ""}
}

//选中单个复选框
function setSingleCheckBoxValue(checkBoxName,setValue){
  if (setValue!='')
   {
	  if (checkBoxName.value == setValue){checkBoxName.checked = true;return true}
	  else{checkBoxName.checked = false;return false}
   }
   else {return false}  
}

//------------------------字符串处理函数-------------------------------------------------------------------------

//去掉字符串左边的空格
function Ltrim(str)
{
   return str.replace(/ +/,"")
}

//去掉字符串右边的空格
function Rtrim(str)
{
   return str.replace(/ +$/,"")
}

//将字符串转换成整数，空字符串转换为0
function STR2Int(Str){
  if(Str==''){return 0}
  else{return parseInt(Str)}
}

//转换时间为'00'格式:如'5'转换为'05'
function Trans00Format(time){
  var timeStr = ''+time
  if(timeStr.length==1){return '0'+timeStr}
  else{return timeStr}
}

//------------------------键盘输入时的辅助函数---------------------------------------------------------------------

//键盘输入时检查是否是数字0-9
function checkNumberHelp(obj,AlertTxt){
  if(obj.value!=''){
	  if(!validCharCheck(obj.value,'0123456789')){if(AlertTxt!=''){alert(AlertTxt)};try{obj.focus()}catch(e){};obj.value='';return false;}
  }
}

//键盘输入时检查数值范围
function checkIntRangeHelp(obj,DefaultValue,AlertTxt,MaxInt,MinInt){
  if(obj.value=='' || isNaN(parseInt(obj.value))) {obj.value = DefaultValue;return false}
  obj.value = parseInt(parseFloat(obj.value))+''
  if(MaxInt!=null){
	  if(MaxInt < parseInt(obj.value)){
		  if(AlertTxt!='') alert(AlertTxt)
		  obj.value = DefaultValue
		  try{obj.focus()}catch(e){}
		  return false
      }
  }
  if(MinInt!=null){
	  if(MinInt > parseInt(obj.value)){
		  if(AlertTxt!='') alert(AlertTxt)
		  obj.value = DefaultValue 
		  try{obj.focus()}catch(e){}
		  return false
      }
  }
}

//键盘输入时帮助清空缺省值
function quickInputHelp(obj,defaultValue){
	if(obj.value==defaultValue) obj.value = ''
}

//键盘输入时，当输入一定长度后自动输入框转移焦点。如输入序列号的自动移焦。发生在onkeyup事件中。
function autoBlurHelp(inputObj,inputLength,focusObj){
  if (inputObj.value.length == inputLength) {inputObj.blur();focusObj.focus()}
}

//
function editCellHelp (CellId,Cell,ClassName,Maxlength,DefaultValue,AlertTxt,MaxNumber,MinNumber) {
   if (CellId!='' && CellId!=null){
     if (document.all) {
       var sizeText = Maxlength==''?Cell.innerText.length:Maxlength
       Cell.innerHTML = '&nbsp;<INPUT ID="'+CellId+'" class="'+ClassName+'" maxlength="'+Maxlength+'"  onclick="event.cancelBubble = true;" onchange="checkIntRangeHelp(this,'+DefaultValue+',\''+AlertTxt+'\','+MaxNumber+','+MinNumber+')";setCellHelp(this.parentElement, this.value)" onblur="setCellHelp(this.parentElement, this.value)" value="'+Cell.innerText+'" size="'+sizeText+'">'
       document.all(CellId).focus()
       document.all(CellId).select()
     }
     else if (document.getElementById) {
            Cell.normalize()
            var input = document.createElement('INPUT')
            input.setAttribute('value', Cell.firstChild.nodeValue)
            input.setAttribute('size', Cell.firstChild.nodeValue.length)
            input.onchange = function (evt) { 
               setCellHelp(this.parentNode, this.value) }
            input.onclick = function (evt) { 
               evt.cancelBubble = true
               if (evt.stopPropagation) evt.stopPropagation() }
            Cell.replaceChild(input, Cell.firstChild)
            input.focus()
            input.select()
     }
   }
}

function setCellHelp (Cell, Value) {
   if (document.all)
      Cell.innerText = Value;
   else if (document.getElementById)
      Cell.replaceChild(document.createTextNode(Value), Cell.firstChild);
}

//----------------------有效性检查函数Check-----------------------------------------------------------------------------

//检查目标字串是否都使用合法字符集
function validCharCheck(objStr,Letters){  
  for (var i=0; i<objStr.length; i++){
   var CheckChar = objStr.charAt(i)
   if (Letters.indexOf(CheckChar) == -1) return false
  }
  return true
}

//检查目标字串是否使用了非法字符集
function novalidCharCheck(objStr,Letters){
  for (var i=0; i<objStr.length; i++){
   var CheckChar = objStr.charAt(i)
   if (Letters.indexOf(CheckChar) >= 0) return false
  }
  return true
}

//检查数值范围
function intRangeCheck(objStr,MaxInt,MinInt){  
  if(objStr=='') return false
  if(MaxInt!=null){
	  if(MaxInt < parseInt(objStr)){		 
		  return false
      }
  }
  if(MinInt!=null){
	  if(MinInt > parseInt(objStr)){		  
		  return false
      }
  }
  return true
}


//目标字串是否都使用合法字符集的函数
function validStrCheck(obj,validStr,AlertTxt){

	if (obj.value!='' && !validCharCheck(obj.value,validStr))
	{
		if (AlertTxt!='') alert(AlertTxt)
		obj.value = '';try{obj.focus()}catch(e){};return false
	}
	else {return true}

}

//目标字串是否使用非法字符的函数
function novalidStrCheck(obj,novalidStr,AlertTxt){
	if (obj.value!='' && !novalidCharCheck(obj.value,novalidStr))
	{
		if (AlertTxt!='') alert(AlertTxt)
		obj.value = '';try{obj.focus()}catch(e){};return false
	}
	else {return true}
}

//目标字串是否为空的函数
function emptyStrCheck(obj,AlertTxt){
	if (obj.value=='')
	{
		if (AlertTxt!='') alert(AlertTxt)
		obj.value = '';try{obj.focus()}catch(e){};return false
	}
	else {return true}
}

//检查IP地址格式
function validIPCheck(obj,objTxt){
  var ipStr = obj.value
  if (ipStr == '') {alert(objTxt+' - ip 地址不能为空!');obj.value = '';try{obj.focus()}catch(e){};return false;}

  var flag = true
  var ip = ipStr.split('.')
  if (ip.length!=4) {alert(objTxt+' - ip 地址不正确!'); flag = false}
  else{
    for (var i=0;i<ip.length;i++){
	   var j = i+1
	   if (!validCharCheck(ip[i],'0123456789')) {alert(objTxt+' - ip 地址段 ('+j+') 必须为整数。');flag = false;break}
	   if (!intRangeCheck(ip[i],255,0)) { alert(objTxt+' - ip 地址段 ('+j+') 数值有误!\nip 地址段范围：0-255 。');flag = false;break}	
	}
  }
  if (flag==false) {obj.value = '';try{obj.focus()}catch(e){};}	
  return flag
}

//检查电子邮件格式
function validEmailCheck(email){
}


//-------------------------------------------------------------------------------------------------------------