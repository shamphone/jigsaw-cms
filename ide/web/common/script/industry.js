 document.write( '<script type="text/javascript" src="/ide/common/script/stack.js"></script>' );
 var xmlIndustry ;
 if(document.all){
 	xmlIndustry=new ActiveXObject("Microsoft.XMLHTTP");
 }else{
 	 xmlIndustry=new XMLHttpRequest();
 }
  function changeLevel(curLevel,classification){  //自适应级数的级联菜单
    var curName=curLevel.name;
    var commonName=curName.substr(0,curName.length-1);
    var curLevelNum=parseInt(curName.charAt(curName.length-1));
    var nextLevelNum=curLevelNum+1;
    var nextLevelName=commonName+nextLevelNum;
    var nextLevel = document.createElement("select");
   curLevel.parentNode.insertBefore(nextLevel,curLevel.nextSibling);
   // curLevel.insertAdjacentElement("beforeEnd", nextLevel)
    nextLevel.id=nextLevelName;
    nextLevel.onchange="changeCommonLevel(this,'"+classification+"','"+type+"')";
    if(!nextLevel)
    {
      if(!classification){
        classification="classification";
      }
      setClassification(classification,commonName);
      return; //如果是最后一级，返回；
    }
    curLevelTemp = curLevel.options[curLevel.selectedIndex].value;
    removeAll(nextLevel);
    var newOption=document.createElement("option");
    newOption.value="";
    newOption.text="请选择...";
    if(document.all){
    	nextLevel.add(newOption);
    }else{
    	nextLevel.add(newOption,null);
    }
    populate_select("/ide/cms/ajaxEntry.do?type=siccode&parent_code=" + curLevelTemp,nextLevel,false);
    changeLevel(nextLevel);
}
  function setCommonValue(curLevel,classification,type,value){  //自适应级数的级联菜单
	  var stack = new Stack();

	  stack.Push(value);

	  var cValue = value;
	  while(cValue!=null){
		  var ret = findParentNode(cValue);
		  if(ret != null&& ret!='1000000000000'){
		  	stack.Push(ret);
		  	cValue = ret;
		  }else{
		   break;
		  }
	  }

	  var i=1;
	  while(!stack.IsEmpty()){
	  var cLevel = document.getElementById(classification+i);
 	  if(cLevel) {
 	  setSelectValue(cLevel,stack.Pop());
 	  changeCommonLevel(cLevel,classification,type)
 	  } else{
		   break;
		  }
 	  i++;
 	  }
  }
  function findParentNode(value){
	  var req = getXMLHttpRequest();
	  var url = '/ide/common/getParentNode.do?contentID='+value;
	  req.open("GET",url, false);
	  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	  req.send(null);
	  if(req.status!=200){
	  alert("无法获取链接："+url);
	  return;
	  }
	  return req.responseText.length>0?req.responseText:null;
  }
  function changeCommonLevel(curLevel,classification,type){  //自适应级数的级联菜单
    while(curLevel.nextSibling.tagName=="SELECT"){
    	curLevel.nextSibling.parentNode.removeChild(curLevel.nextSibling);
    //curLevel.nextSibling.removeNode(true);
    }
    var curName=curLevel.name;
    var nextLevelName = "";
    var commonName=curName.substr(0,curName.length-1);
    var curLevelNum=parseInt(curName.charAt(curName.length-1));
    if(!isNaN(curLevelNum)){
    var nextLevelNum=curLevelNum+1;
    nextLevelName=commonName+nextLevelNum;
    }else{
    nextLevelName=curName+1;
    }
    var nextLevel = document.createElement("select");
    curLevel.parentNode.insertBefore(nextLevel,curLevel.nextSibling);
    nextLevel.id=nextLevelName;
    nextLevel.name=nextLevelName;
    nextLevel.onchange=function(){changeCommonLevel(this,classification,type)};
    curLevelTemp = curLevel.options[curLevel.selectedIndex].value;
      if(!classification){
        classification="classification";
      }
      setClassification(classification,curLevel.id);
    var newOption=document.createElement("option");
    newOption.value="";
    newOption.text="请选择...";
    if(navigator.userAgent.indexOf("Firefox")>=0){
    	nextLevel.add(newOption,null);
    }else{
    	nextLevel.add(newOption);
    }
    populate_select("/ide/cms/ajaxEntry.do?type="+type+"&parent_code=" + curLevelTemp,nextLevel,false);
    if(nextLevel.options.length==1){
    	nextLevel.parentNode.removeChild(nextLevel);
    	//nextLevel.removeNodetrue);
    }
    //changeLevel(nextLevel);
}
  function changeLevel1(level1S,level2S,level3S,level4S){
  var level1 = document.getElementById( (level1S==null)?"level1":level1S );
  var level2 = document.getElementById( (level2S==null)?"level2":level2S );
      level1Temp = level1.options[level1.selectedIndex].value;
      removeAll(level2);
      var newOption=document.createElement("option");
      newOption.value="";
      newOption.text="请选择...";
      //level2.add(newOption);
      if(navigator.userAgent.indexOf("Firefox")>=0){
    	level2.add(newOption,null);
      }else{
    	level2.add(newOption);
      }
      populate_select("/ide/cms/ajaxEntry.do?type=siccode&parent_code=" + level1Temp,level2,false);
      changeLevel2(level2S,level3S,level4S);
  }
  function changeLevel2(level2S,level3S,level4S)
  {
  var level2 = document.getElementById( (level2S==null)?"level2":level2S );
  var level3 = document.getElementById( (level3S==null)?"level3":level3S );
      level2Temp = level2.options[level2.selectedIndex].value;
      removeAll(level3);
      var newOption=document.createElement("option");
      newOption.value="";
      newOption.text="请选择...";
     // level3.add(newOption);
     if(navigator.userAgent.indexOf("Firefox")>=0){
    	level3.add(newOption,null);
      }else{
    	level3.add(newOption);
      }
      populate_select("/ide/cms/ajaxEntry.do?type=siccode&parent_code=" + level2Temp,level3,false);
      changeLevel3(level3S,level4S);
  }
  function changeLevel3(level3S,level4S)
  {
  var level3 = document.getElementById( (level3S==null)?"level3":level3S );
  var level4 = document.getElementById( (level4S==null)?"level4":level4S );
      level3Temp = level3.options[level3.selectedIndex].value;
      removeAll(level4);
      var newOption=document.createElement("option");
      newOption.value="";
      newOption.text="请选择...";
     // level4.add(newOption);
     if(navigator.userAgent.indexOf("Firefox")>=0){
    	level4.add(newOption,null);
      }else{
    	level4.add(newOption);
      }
      populate_select("/ide/cms/ajaxEntry.do?type=siccode&parent_code=" + level3Temp,level4,false);
  }
  function setClassification(name,level4S)
  {
  var level4 = document.getElementById( (level4S==null)?"level4":level4S );
  var classification = document.getElementById( name );
  if(classification)
  classification.value=level4.value;
  }
  function setLevel1(value,level1,level2,level3,level4){
      document.getElementById( level1 ).options.value=value;
      changeLevel1(level1,level2,level3,level4);
  }
  function setLevel2(value,level2,level3,level4){
      document.getElementById( level2 ).options.value=value;
      changeLevel2(level2,level3,level4);
  }
  function setLevel3(value,level3,level4){
      document.getElementById( level3 ).options.value=value;
      changeLevel3(level3,level4);
  }
  function setLevel4(value,level4){
      document.getElementById( level4 ).options.value=value;
  }

  function setSelected(table,level1,level2,level3,level4){
  var srcTR;
  if(level1 != null && level1 != ""){
      srcTR=getTR(table,level1,1);
      srcTR.getElementsByTagName("img")[0].click();
  }
  if(level2 != null && level2 != ""){
      srcTR=getTR(table,level2,2);
      srcTR.getElementsByTagName("img")[0].click();
  }
  if(level3 != null && level3 != ""){
      srcTR=getTR(table,level3,3);
      srcTR.getElementsByTagName("img")[0].click();
  }
  if(level4 != null && level4 != ""){
      srcTR=getTR(table,level4,4);
  }
      setFocus(srcTR.id);
      srcTR.getElementsByTagName("a")[0].focus();
  }

  function allIndustry(){
   setFocus();
   top.frames['result'].allIndustry();
  }
  /*
  function setValue(value){
   srcTR=window.event.srcElement.parentNode.parentNode;
   srcTR.className="selected";
   if (srcTR.status=="closed"){
      srcTR.getElementsByTagName("img")[0].click();
   }
   pTR = getTR(srcTR.parentNode,srcTR.parentid,srcTR.level-1);
   if (pTR.id == '0'){
       top.frames['result'].setIndustry(srcTR.id);
   }
   else{
       ppTR = getTR(srcTR.parentNode,pTR.parentid,pTR.level-1);
       if (ppTR.id == '0'){
           parent.frames['result'].setIndustry(pTR.id,srcTR.id);
       }
       else {
           pppTR = getTR(srcTR.parentNode,ppTR.parentid,ppTR.level-1);
           if (pppTR.id == '0'){
               parent.frames['result'].setIndustry(ppTR.id,pTR.id,srcTR.id);
           }
           else {
               ppppTR = getTR(srcTR.parentNode,pppTR.parentid,pppTR.level-1);
               if (ppppTR.id == 0){
                   parent.frames['result'].setIndustry(pppTR.id,ppTR.id,pTR.id,srcTR.id);
               }
           }
       }
   }
  }
  */

  function getParentTR(srcTR){
   srcTable = srcTR.parentNode;
   rows= srcTable.rows;
   for (i = 0 ;i <rows.length; i++){
      if( rows[i].id == srcTR.parentid){
          return rows[i];
      }
   }
  }

  function writelevel1(path,table){
  xmlIndustry.async="false"
  xmlIndustry.load(path+"/common/industry.xml")
  newRow = table.insertRow();
  newRow.level="0";
  newRow.parentid="-1";
  newRow.className="treeShow";
  newRow.id="0";
  newRow.status="open";
  newTD = newRow.insertCell();
  newTD.runtimeStyle.paddingLeft = "0ex";
  newTD.className="treeUnselected";
  newTD.innerHTML = "<img src=\"images/minus.gif\" class=\"treeSign\" onclick=\"toggle('0')\"><a href='#' onclick=allIndustry()>全部行业</a>";

  var ele = xmlIndustry.getElementsByTagName("level1").length
  for (var i=0;i<ele;i++){
  var name = xmlIndustry.getElementsByTagName("level1").item(i).attributes[0].value;
  var value = xmlIndustry.getElementsByTagName("level1").item(i).attributes[1].value;
  newRow = table.insertRow();
  newRow.level="1";
  newRow.parentid="0";
  newRow.className="treeShow";
  newRow.id=value;
  newRow.status="closed";
  newTD = newRow.insertCell();
  newTD.runtimeStyle.paddingLeft = "1ex";
  newTD.className="treeUnselected";
  newTD.innerHTML = "<img src=\"images/plus.gif\" class=\"treeSign\" onclick=\"toggle('"+
  value+"')\"><a onclick='setFocus()' target=result href='/member/newDoSearchOrgByArea.do?sort=CREATETIME&order=desc&level1="+value+"')>"+name+"</a>";
  //writelevel2(value,table);
  }
  }
  function writelevel(parentTR)
  {
  var table = parentTR.parentNode;
  var id = parentTR.id;
  var level = Number(parentTR.level);
  var index = parentTR.rowIndex;
  var selectStr = "";
  if (level==1){
  selectStr+="/industry//level1[@value='"+id+"']/*";
  }
  else if (level==2){
  selectStr+="/industry//level1//level2[@value='"+id+"']/*";
  }
  else if (level==3){
  selectStr+="/industry//level1//level2//level3[@value='"+id+"']/*";
  }
  var ele = xmlIndustry.selectNodes(selectStr);
  for (var i=0;i<ele.length;i++){
  var name = ele[i].attributes[0].value
  var value = ele[i].attributes[1].value
  newRow = table.insertRow(index+1);
  newRow.level=level+1;
  newRow.parentid=id;
  newRow.className="treeShow";
  newRow.id=value;
  newRow.status="closed";
  newTD = newRow.insertCell();
  newTD.runtimeStyle.paddingLeft = level+1+"ex";
  newTD.className="treeUnselected";
  var html = "";
  if (level!=3){
      html += "<img src=\"images/plus.gif\" class=\"treeSign\" onclick=\"toggle('"+value+"')\"><a onclick='setFocus()' target='result' href='/member/newDoSearchOrgByArea.do?sort=CREATETIME&order=desc&level1="+value+"'>"+name+"</a>";
  }
  else {
      html += "<img src=\"images/dot.gif\"><a onclick='setFocus()' target='result' href='/member/newDoSearchOrgByArea.do?sort=CREATETIME&order=desc&level1="+value+"'>"+name+"</a>";
  }
  newTD.innerHTML = html;
  }
  }
  function writeTable(path,table,cellLength){
      xmlIndustry.async="false"
      xmlIndustry.load(path+"/common/industry.xml")
      var ele = xmlIndustry.getElementsByTagName("level1").length
      newRow = table.insertRow();
      newRow.style.verticalAlign="text-top";
      for (var i=0;i<ele;i++){
          var name = xmlIndustry.getElementsByTagName("level1").item(i).attributes[0].value;
          var value = xmlIndustry.getElementsByTagName("level1").item(i).attributes[1].value;
          var count ="";
          try {
              count=eval(value);
          }
          catch (e) {
              count="0";
          }
          var row = table.rows[table.rows.length-1];
              if(row.cells.length<cellLength){
                  newTD = row.insertCell();
                  newTD.width="50%";
                  newTD.innerHTML = "<a href='industrySearch.jsp?level1="+value+"'>"+
                  name+"</a>("+count+")"+"<br/>"+getLevel2(value);
              }
              else {
                  newRow = table.insertRow();
                  newRow.style.verticalAlign="text-top";
                  newTD = newRow.insertCell();
                  newTD.width="50%";
                  newTD.innerHTML = "<a class='level2' href='industrySearch.jsp?level1="+value+"'>"+
                  name+"</a>("+count+")"+"<br/>"+getLevel2(value);
              }
      }
  }
  function getLevel2(level1){
  var ele = xmlIndustry.selectNodes("/industry//level1[@value='"+level1+"']/*");
  var retStr="";
  for (i=0;i<ele.length&&i<5;i++){
  name =ele[i].attributes[0].value
  value =ele[i].attributes[1].value
  retStr += "<a class='level2' href='industrySearch.jsp?level1="+level1+"&level2="+value+"'>"+name+"</a>&nbsp;"
  }
  if(ele.length>5){
  retStr+=" ...";
  }
  return retStr;
  }
