var MovePortlet = {
move :function (oForm, formURL, categories, bChoose,buttonName) {
	if (checkNull(oForm.elements['node'])) {
		var ids = '';
		if (bChoose) {
			var arr = CMSDialog.NodeDefinitionSelector(
					'no-properties-scheme', categories, true, true,
					true);
			if(arr != null)
			ids += '&categorys=' + arr[0].ID;
		} else {
				ids += '&categorys=' + categories[0];
		}
		ids += "&selfURL="+window.location.href;
		if(ids != ''){
    		  oForm.action=formURL+ids;
    		  alert(buttonName+"成功！");
	      	  oForm.submit();
    	  }
	} else {
		alert('请选择内容。');
	}},
singleMove:function(formURL,categories,bChoose,buttonName){
		var ids = '';
		if (bChoose) {
			var arr = CMSDialog.NodeDefinitionSelector(
					'no-properties-scheme', categories, true, true, true);
			if(arr != null){
				ids += '&categorys=' + arr[0].ID;
				ids += "&selfURL="+window.location.href;
				alert(buttonName+"成功！");
				window.location = formURL + ids;
			}
		} else {
				ids += '&categorys=' + categories[0];
				ids += "&selfURL="+window.location.href;
			alert(buttonName+"成功！");
			window.location = formURL + ids;
		}
	}
}