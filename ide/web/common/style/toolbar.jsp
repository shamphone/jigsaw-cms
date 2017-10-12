<%@ page contentType="text/css; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
body
{
	overflow:visible;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;	
	background-color:buttonface;
	vertical-align:middle;
	font-size: 12px;
}
#DLGToolbar20{
  border-bottom:2px groove;
  margin: 1px 1px 1px 1px;
  padding: 0px 0px 4px 0px;
  height: 30px;
  line-height:30px;
  vertical-align : middle;
}


.seperator{
    margin: 1px 3px 1px 3px;
    padding: 0px 1px 0px 1px;
    height: 20px;
    line-height:20px;
    vertical-align : middle;
    border-left:1px outset buttonface;
    display:inline-block;
}
.DLGButton
{
    margin: 1px 2px 1px 2px;
    padding: 0px 1px 0px 1px;
    height: 20px;
    line-height:20px;
    vertical-align : middle;
    border: buttonface 1px solid;
    cursor:pointer;
    display:inline-block;
    behavior: url(<html:rewrite module="/common" page="/style/dlgbutton.htc"/>);
}
.DLGButton img{
  border: 0px;
  vertical-align:middle;
  margin-right:2px;
}

.DLGButtonDisabled
{
   color: graytext;
}

.DLGButtonDisabled img
{
    filter: gray() alpha(opacity=50);
    opacity: 0.30; 
}
.DLGButton_On_Over
{
    border: #0A246A 1px solid;
    background-color: #B6BDD2;
}



.ToogleButton
{
    margin: 1px 2px 1px 2px;
    padding: 0px 5px 0px 5px;
    height: 20px;
    line-height:20px;
    vertical-align : middle;
    border: #0A246A 1px solid;
    cursor:pointer;
    display:inline-block;
}

.ToogleButtonDown{
	background-color:#ffffff;
    border: #0A246A 1px solid;	
}
.ToogleButton img{
  border: 0px;
  vertical-align:middle;
  margin-right:2px;
}


.userInfo{
   position:absolute; 
   right:5px;
    margin: 1px 0px 1px 0px;
    padding: 0px 1px 0px 1px;
    height: 20px;
    line-height:20px;
    vertical-align : middle;
    border: buttonface 1px solid;
    display:inline-block;
}
