 var AutoLogin = {
      checkForm : function (){ 
            var loginWay = document.getElementById('loginWay');
            if(loginWay.checked){
                    setCookie("username",loginWay.form.elements('user-username').value);
                    setCookie("password",loginWay.form.elements('user-password').value);
                }
                else{
                    deleteCookie("username");
                    deleteCookie("password");
                }
		},
	checkCookie : function(){
            var oForm = document.getElementById('loginWay').form; 
            var username=getCookie("username");
            var password=getCookie("password");
            if(username!=null&&username!=''){
                    oForm.elements('user-username').value=username;
                    oForm.elements('user-password').value=password;
                    oForm.loginWay.checked=true;
                    oForm.submit();
            }
	}
}