document.write( '<script type="text/javascript" src="/ide/common/script/common.js"></script>' );
document.write( '<script type="text/javascript" src="/ide/common/script/ajax.js"></script>' );
document.write( '<script type="text/javascript" src="/ide/cms/classes/cmsdialog.js"></script>' );
document.write( '<script type="text/javascript" src="/ide/site/dialog.js"></script>' );
 
      /** 
  *超文本编辑器
  **/
  function advancedText(oControl)
  {
    var ret = CMSDialog.RichTextEditor(oControl.value);
    if (ret)
      oControl.value = ret;
  }
      
  /**
  *编辑页面片段
  **/
  function editClipFile(path,oCategory,oFrame, formDefinition, styleSheets){
  	  path = '/'+ window.parent.dialogArguments.template.name+ path;
	  var definitionId=null;
	  if(oCategory!=null){
		  definitionId=oCategory.value;
	  }
    var ret=SiteDialog.editClipFile(path,definitionId, formDefinition, styleSheets);
    if(ret!=null&&oFrame!=null){
    	oFrame.contentWindow.location.href=oFrame.contentWindow.location.href+"&a="+Math.random();
    }
  }

  /**
  *添加过滤器
  **/
  function newFilter(oCategory,oSelect){
    var filter=CMSDialog.FilterEditor(oCategory.value);
    if(filter!=null){
      var newOption=document.createElement("option");
      newOption.value=filter.value;
      if(document.all){
      	 newOption.text=filter.name;
     	 oSelect.add(newOption);
      }else{
      	 newOption.textContent=filter.name;
     	 oSelect.add(newOption,null);
      }
    }
  }
  
  /**
  *添加支持搜索大纲条件的过滤器
  **/
  function newFilter_Search(oCategory,oSelect,searchDefID){
    var filter=CMSDialog.SearchFilterEditor(oCategory.value,searchDefID);
    if(filter!=null){
      var newOption=document.createElement("option");
      newOption.value=filter.value;
      if(document.all){
      	newOption.text=filter.name;
      	oSelect.add(newOption);
      }else{
      	newOption.textContent=filter.name;
     	oSelect.add(newOption,null);
      }
    }
  }

 /**
  *
  *选择栏目
  **/
  function chooseColumn(defaultTemplateId,oPath, oName){
  	var path = oPath.value;
  	var templateName = defaultTemplateId;
  	if(path==null||path==""){
  		path = "/" + templateName + "/index.jsp";
  	}
	var channel = SiteDialog.selectChannel(templateName,path,true,true,"选择跳转页面 ",["clip","script","style"]);
	if(channel!=null){
		if(oPath!=null){
			//oPath.value ="<%=request.getContextPath()%>/"+channel.name;
			oPath.value = channel.path;
		}
		if(oName!=null){
			oName.value = channel.template.displayName+"."+channel.displayName;
		}
	}
  }

  function parseTemplate(path){
  	 var splits = path.split('/');
	 if(splits.length>2){
	 	return splits[1];
	 }
	 return null;
  }
  function parseChannel(path){
  	 var splits = path.split('/');
	 if(splits.length>1){
	 	return path.substring(path.indexOf(splits[2])-1,path.length);
	 }
	 return null;
  }
  
  /**
  *
  *添加分类
  **/
  function addNodeDefinition(oSelect,bSingle){
    var arr = CMSDialog.NodeDefinitionSelector(null,null,bSingle);
    if(arr!=null){
    if(arr.length>0){
    	for(i=0;i<arr.length;i++){
    	if(!findSelectItem(oSelect,arr[i].ID)){
			var newOption=document.createElement("option");
			newOption.value=arr[i].ID;
			if(document.all){
				newOption.text=arr[i].name;
				oSelect.add(newOption);
			}else{
				newOption.textContent=arr[i].name;
				oSelect.add(newOption,null);
			}
		}
    	}
    }
    }
  }
  /**
  *
  *选择分类
  **/
  function searchNodeDefinition(oID, oName,clearObj){
  //clearObj为需要清空内容的对象
  if(clearObj != null && clearObj !="undefine"){
  clearObj.value="";
  }
    var ret = CMSDialog.NodeDefinitionSelector('no-properties-scheme', null, true, false, false);     
    if(ret!=null){
      if(oID!=null){
      oID.value = ret.ID;
      }
      if(oName!=null)
      oName.value = ret.name;
    }
  }

  /**
  *
  *选择内容
  **/
  function doSelectFilterProperty(_select){
    if(_select.value.length!=0){
      var categoryId=_select.form.elements['preference(category)'].value;
      searchContent(categoryId);
    }
  }

  /**
  *
  *选择内容
  **/
  function searchContent(oName,oValue){
    var categoryId=document.getElementsByName("preference(category)")[0].value;
    var arr = CMSDialog.NodeSelector(categoryId);
    if(arr!=null){
      oName = arr.name;
      oValue = arr.ID;
    }
  }

  /**
  *
  *选择属性
  **/
  function searchPropertyDefinition(oCategory,oID, oName, excludes,hasChild){
    var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,excludes);
    if(hasChild == "true"){
    	if(!arr.hasChild()){
    		alert("该分类不含属性");
    		return;
    	}
    }else{
    	if(arr!=null&&arr.ID!=null){
      oName.value = arr.name;
      oID.value = arr.ID;
    }
    }
   /*
    if(arr!=null&&arr.ID!=null){
      oName.value = arr.name;
      oID.value = arr.ID;
    }*/
  }
  
  /**
  *
  *添加属性
  **/
  function addPropertyDefinition(oCategory, oSelect, excludes){
    var arr =  CMSDialog.PropertyDefinitionSelector(oCategory.value,excludes);
    if(arr!=null){
    var newOption=document.createElement("option");
			newOption.value=arr.ID;
			if(document.all){
			newOption.text=arr.name;
			oSelect.add(newOption);	
			}else{
			newOption.textContent=arr.name;
			oSelect.add(newOption,null);
			}
    }
  }

/**
  *	  自动往过滤器中添加搜索大纲中的过滤条件
  **/
  function autoAddFilterPattern(oCategory,oSelect,searchDefID){
	  var req = getXMLHttpRequest();
	  var url= Globals.contextPath + '/cms/autoAddFilter.do?defID='+oCategory+'&searchDefID='+searchDefID+'&equal='+encodeURI("等于")+'&more='+encodeURI("大于")+'&less='+encodeURI("小于") + '&timestamp=' + Math.random();;
  req.open("POST",url, false);
  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
  req.send(null);
  if(req.status!=200){
    alert("无法获取链接："+url);
    return;
  }
  var resXML=req.responseXML;
  if(resXML!=null){
	  removeAll(oSelect);
	  var properties=resXML.getElementsByTagName("parameter");
  for(var i=0;i<properties.length;i++){
  	if(document.all){
  			var name=properties[i].getElementsByTagName("name")[0].text;
			var value=properties[i].getElementsByTagName("value")[0].text;
			var newOption=document.createElement("option");
			newOption.value=value;
			newOption.text=name;
			oSelect.add(newOption);	
  	}else{
			var name=properties[i].getElementsByTagName("name")[0].textContent;
			var value=properties[i].getElementsByTagName("value")[0].textContent;
			var newOption=document.createElement("option");
			newOption.value=value;
			newOption.textContent=name;
			oSelect.add(newOption,null);
  	}
		  }
	  }
  }
/**
 * 选择活动
 */
  function doSelectActivity(oProcess, oActivity,oName,oTemplate){
	  var ret = showDialog( Globals.contextPath + '/process/activity/selector.jsp',oTemplate,620,450);
	  if(ret!=null){
		  oProcess.value = ret.definition.ID;
		  oActivity.value = ret.activity.ID;
		  oName.value=ret.definition.name +"."+ret.activity.name;
	  }
  }
  /**
   * 显示活动的全称
   */
  function showActivityFullName(oProcess, oActivity, oName){
	  if(oProcess.value.length == 0)
		  return;
	  if(oActivity.value.length == 0)
		  return;
	  var req = getXMLHttpRequest();
	  var url=Globals.contextPath + '/process/activity/fullname.do?timestamp=' + Math.random();
	  url = url+"&processID="+oProcess.value;
	  url = url +"&activityID="+oActivity.value;
	  req.open("POST",url, false);
	  req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	  req.send(null);
	  if(req.status == 200)
		  oName.value = req.responseText;
  }
  
 /**
  *清空按钮
  **/
  function clearTxt(oPreference,oName){
  if (oPreference != null) {
      oPreference.value="";
      }
      oName.value="";
  }
  
   	function validateForward(oForm){
		if(oForm.elements["preference(type)"].value=="link"||oForm.elements["preference(type)"].value=="index"){
			if(oForm.elements["preference(forwardField)"]!=null){
				if(oForm.elements["preference(forwardField)"].value!=""){
					return true;
				}
			}
			if(oForm.elements["preference(channel)"].value==""){
				alert("跳转到不能为空！");
				return false;
			}
		}
		return true;
	}