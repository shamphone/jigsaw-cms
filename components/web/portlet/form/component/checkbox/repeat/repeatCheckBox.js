function isCreateClearValue(oCheckbox){
	var checkboxName = oCheckbox.name;
	var oForm = oCheckbox.form;
	var oCheckboxs = oForm.elements[checkboxName];
	var hasChecked = false;
	var oHiddenInput = document.getElementById(checkboxName+"_h");
	for(var i=0;i<oCheckboxs.length;i++){
		var ooCheckbox = oCheckboxs[i];
		if(checkNull(oCheckbox)){
			hasChecked = true;
			break;
		}
	}
	if(hasChecked){
		if(oHiddenInput!=null){
			oHiddenInput.parentNode.removeChild(oHiddenInput);
		}
	}else{
		if(oHiddenInput==null){
			oHiddenInput = document.createElement("input");
			oHiddenInput.name=checkboxName;
			oHiddenInput.id=checkboxName+"_h";
			oHiddenInput.type="hidden";
			oHiddenInput.value="clear";
			oCheckbox.parentNode.appendChild(oHiddenInput);
		}
	}
}