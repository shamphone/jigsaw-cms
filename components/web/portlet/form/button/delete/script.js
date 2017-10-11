var DeletePortlet = {
	doDelete :function(oForm,formURL){
	if(checkNull(oForm.elements['node'])){
		if(confirm('确认删除内容')){
			oForm.action=formURL;
			alert("删除成功！");
			return true;
		}else{
			return false;
		}
	} else{
		alert('请选择内容。');
		return false;
	}
},
singleDelete:function(oForm,formURL,id){
	if(confirm('确认删除这条内容')){
		oForm.action=formURL+"&node="+id;
		alert("删除成功！");
		oForm.submit();
	}
}
}
