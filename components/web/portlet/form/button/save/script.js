var SavePortlet = {
		save :function(oForm,formURL,category,buttonName){
			if(checkNull(oForm.elements['node'])){	
				oForm.action=formURL+"&definition="+category+"&selfURL="+window.location.href;
				alert(buttonName+"成功！");
				if(oForm.onsubmit()){
					oForm.submit();
				}
			} else{
				alert('请选择内容。');
			}
		},
		singleSave : function(hasID,oForm,formURL,saveLimit,contentId,porltetId,buttonName){
			switch (saveLimit) {
			//每台PC只能保存一次
			case "PCOnly":
				if(!this.validateOnly(porltetId)){
					alert("请勿重复操作！");
					return false;
				}
				break;
			//每台PC24小时内只能保存一次
			case "PCPerDate":
				if(!this.validatePerDate(porltetId)){
					alert("请勿重复操作！");
					return false;
				}
				break;
			default:
				break;
			}
			var definition;
			if(document.all){
				definition = oForm.definition;
			}else{
				definition = oForm.getAttribute("definition");
			}
			if(definition.type!=null){
				//alert("保存成功！");
				oForm.action=formURL+"&definition="+definition.value+"&contentId="+contentId+"&hasID="+hasID+"&selfURL="+encodeURIComponent(window.location.href);
			} else{
				//alert("保存成功！");
				oForm.action=formURL+"&definition="+definition+"&contentId="+contentId+"&hasID="+hasID+"&selfURL="+encodeURIComponent(window.location.href);
			}
			if(oForm.fireEvent("onsubmit")){
				oForm.submit();
			}
		},
		validateOnly:function(porltetId){
			if(getCookie(porltetId+'.only.save')=="") {
				setCookie(porltetId+'.only.save',true);
				return true;
			} else {
				return false;
			}
		},
		validatePerDate:function(porltetId){
			var curDate = new Date();
			if(getCookie(porltetId+'.per.date.save')=="") {
				setCookie(porltetId+'.per.date.save',curDate.getTime());
			} else {
				var time = parseInt(getCookie(porltetId+'.per.date.save'));
				var saveDate = new Date(time);
				if(this.isOneDate(curDate, saveDate)){
					return false;
				}else{
					deleteCookie(porltetId+'.per.date.save');
					setCookie(porltetId+'.per.date.save',curDate.getTime());
				}
			}
			return true;
		},
		//判断两个日期是否在同一天
		isOneDate:function(date1,date2){
			return (date1.getYear()==date2.getYear())
			&&(date1.getMonth()==date2.getMonth())
			&&(date1.getDate()==date2.getDate());
		}
}