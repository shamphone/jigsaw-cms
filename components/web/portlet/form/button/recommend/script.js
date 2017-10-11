//增加对多分类修改 by mali 2010-7-9
var RecommandPortlet = {
	recommand : function doRecommand(oForm, formURL, categories, bChoose,buttonName) {
		if (checkNull(oForm.elements['node'])) {
			var ids = '';
			if (bChoose) {
				var arr = CMSDialog.NodeDefinitionSelector(
						'no-properties-scheme', categories, false, true,
						true);
				if (arr != null) {
					for (i = 0; i < arr.length; i++) {
						ids += '&category=' + arr[i].ID;
					}
					 ids += "&selfURL="+window.location.href;
				}
			} else {
				for (i = 0; i < categories.length; i++) {
					ids += '&category=' + categories[i];
				}
				 ids += "&selfURL="+window.location.href;
			}
			if(ids != ''){
	    		  oForm.action=formURL+ids;
	    		  alert(buttonName+"成功！");
		      	  oForm.submit();
	    	  }
		} else {
			alert('请选择内容。');
		}
	},
	singleRecommand : function(formURL, categories, bChoose,buttonName) {
		var ids = '';
		if (bChoose) {
			var arr = CMSDialog.NodeDefinitionSelector(
					'no-properties-scheme', categories, false, true, true);
			if (arr != null) {
				for (i = 0; i < arr.length; i++) {
					ids += '&category=' + arr[i].ID;
				}
				 ids += "&selfURL="+window.location.href;
				alert(buttonName+"成功！");
				window.location = formURL + ids;
			}
		} else {
			for (i = 0; i < categories.length; i++) {
				ids += '&category=' + categories[i];
			}
			ids += "&selfURL="+window.location.href;
			alert(buttonName+"成功！");
			window.location = formURL + ids;
		}
	}
}
