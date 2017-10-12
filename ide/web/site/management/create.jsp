<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>
<tiles:insert definition="dialog_frame">
	<tiles:put name="title">创建网站</tiles:put>
	<tiles:put name="javascript">
		<link rel="stylesheet" type="text/css" media="all" href='<html:rewrite module="/common" page="/calendar/skins/aqua/theme.css"/>' title="Aqua" />
  		<html:javascript formName="createSiteForm"/>
	    <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
		<script type="text/javascript" src='<html:rewrite page="/classes/cmsdialog.js" module="/cms"/>'></script>
		<script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/calendar.js"/>'></script>
		<script type="text/javascript" src='<html:rewrite module="/common" page="/calendar/lang/cn_utf8.js"/>'></script>
		<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
		<script type="text/javascript" language="javascript">
		    function showCalendar(id, format, showsTime, showsOtherMonths) {
		      var el = document.getElementById(id);
		      if (_dynarch_popupCalendar != null) {
		        _dynarch_popupCalendar.hide();
		      } else {
		        var cal = new Calendar(1, null, selected, closeHandler);
		        if (typeof showsTime == "string") {
		          cal.showsTime = true;
		          cal.time24 = (showsTime == "24");
		        }
		        if (showsOtherMonths) {
		          cal.showsOtherMonths = true;
		        }
		        _dynarch_popupCalendar = cal;
		        cal.setRange(1900, 2070);
		        cal.create();
		      }
		      _dynarch_popupCalendar.setDateFormat(format);
		      _dynarch_popupCalendar.parseDate(el.value);
		      _dynarch_popupCalendar.sel = el;
		      _dynarch_popupCalendar.showAtElement(el.nextSibling, "Br");
		
		      return false;
		    }
		    function selected(cal, date) {
		      cal.sel.value = date;
		      cal.callCloseHandler();
		    }
		    function closeHandler(cal) {
		      cal.hide();                        // hide the calendar
		      _dynarch_popupCalendar = null;
		    }

			
		    
		</script>
		<script type="text/javascript" language="javascript">
			function toggleButton(state) {
				document.getElementById("btnOk").disabled = state == "disable";
			}
		</script>
		<script type="text/javascript" language="javascript">
		    function selectOwner($valueControl, $textControl) {
		    	var user=CMSDialog.NodeSelector('principal-scheme');
		    	if(user) {
		    		$valueControl.value=user['id'];
		    		$textControl.value=user['user-commonname'];
		    	}
		    }
		    function selectTemplate(templateIDs,navigateTemplateIDs) {
			    var options = templateIDs.options;
			    var templates = new Array();
			    for(var i=0;i<options.length;i++){
			    	templates.push(options[i].value);
			    }
		        var ret = SiteDialog.selectTemplate(null,templates);
		        if(ret) {
		        	 templateIDs.options.length = 0;
		        	 navigateTemplateIDs.options.length = 0;
		        	 templates = new Array();
		        	 for(var i=0;i<ret.length;i++){
			        	templates.push(ret[i].ID);
					 }
		        	 var navigateRet = SiteDialog.selectNavigateTemplate(templates);
		        	 if(navigateRet!=null&&navigateRet.length>0){
		        		 var  navigateTemplates = new Array();
		        		 for(var i=0;i<navigateRet.length;i++){
			        		 navigateTemplates.push(navigateRet[i].ID);
			        		 addOption(navigateTemplateIDs,navigateRet[i].ID,navigateRet[i].displayName);
			        	 }
		        		 for(var i=0;i<ret.length;i++){
			        		addOption(templateIDs,ret[i].ID,ret[i].displayName);
						 }
		        	 }
	            }
		    }
		    
		    function validateDomain($domain){
	        	if (!$domain.value) 
		        	return;
				var url='<html:rewrite page="/management/validateDomain.do" module="/site"/>?domain=' + $domain.value + "&timestamp=" + Math.random();
				var request = new HttpRequest(url);
				if (request.Get() == "domainExits") {
					alert("您所指定的域名已被使用，请重新输入");
					toggleButton("disable");
					$domain.select();
					return;
				}
				toggleButton("enable");
		    }
		    function validate ($form) {
			    var templateIDs = document.getElementById("templateIDs");
			    var navigateTemplateIDs = document.getElementById("navigateTemplateIDs");
			    if(templateIDs.options.length==0){
					alert("请选择模板！");
					return false;
			    }
			    if(navigateTemplateIDs.options.length==0){
					alert("请选择导航模板！");
					return false;
			    }
				if(validateCreateSiteForm($form)) {
					toggleButton("disable");
					selectAll(templateIDs);
					selectAll(navigateTemplateIDs);
					return true;
				}
				return false;
	        }
			function _Valid() {
				var code = event.keyCode;
				if(navigator.userAgent.indexOf("Firefox")>=0){
					return code==0||(code>=97 && code<=122) || (code>=48 && code<=57) || (code>=65 && code<=90) || code==8 || code==9 || code==45 || code==46;;
				}else{
					return (code>=97 && code<=122) || (code>=48 && code<=57) || (code>=65 && code<=90) || code==8 || code==9 || code==45 || code==46;
				}
				//return (code>=97 && code<=122) || (code>=48 && code<=57) || (code>=65 && code<=90) || code==8 || code==9 || code==45 || code==46;
			}
			window.onload = function(){
				document.forms[0].elements["domain"].select();
			}
			function _ok(oForm){
				if( !validate(oForm)){
					return false;
				}
				window.parent.returnValue = "success";
				
				document.forms[0].submit();
				if(document.all){
					window.close();
				}else{
					window.parent.close();
				}
			}
			function _close(){
				if(document.all){
					window.close();
				}else{
					window.parent.close();
				}
			}
    	</script>
	</tiles:put>
	<tiles:put name="dialog">
		<html:form action="insert.do" method="post">
  	    <div style="color:red; margin-left:6px"><html:errors/></div>
			<table width="100%" cellpadding="2" cellspacing="2" border="0">
				<tr>
					<td>网站域名</td>
					<td><html:text property="domain" onchange="validateDomain(this)"  disabled="false" maxlength="255" title="只能输入半角的字母、数字、小数点和减号" onkeypress="return _Valid();" size="18" style="ime-mode:disabled" ></html:text></td>
					<td>网站名称</td>
					<td><html:text property="displayName" size="18" maxlength="16" title="输入的字符数在4-16之间"/></td>
				</tr>
				<tr>
					<td>建站用户</td>
					<td>
						<html:hidden property="ownerID" styleId="ownerId"/>
						<html:text  property="ownerName" styleId="ownerName" size="18" readonly="true"/>
						<button type="button" onclick="selectOwner(this.form.elements['ownerID'], this.form.elements['ownerName'])">选择...</button>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td style="vertical-align: top">网站模板</td>
					<td>
						<html:select style="width: 122px;" multiple="true" size="8" property="templateIDs" styleId="templateIDs">
							
						</html:select>
						<button type="button" style="vertical-align: top" onclick="selectTemplate(this.form.elements['templateIDs'],this.form.elements['navigateTemplateIDs'])">选择...</button>
					</td>
					<td style="vertical-align: top">导航模板</td>
					<td>
					<html:select style="width: 122px;" multiple="true" size="8" property="navigateTemplateIDs" styleId="navigateTemplateIDs">
						
					</html:select></td>
				</tr>
				<tr style="display:none;">
					<th>网站状态</th>
					<td>
						<html:radio styleId="radio1" property="state" value="active"/><label for="radio1">激活网站</label>
						<html:radio styleId="radio2" property="state" value="inactive"/><label for="radio2">终止运行</label>
					</td>
				</tr>
			</table>
			<div class="operation">
				<button type="button" id="btnOk" onclick="_ok(this.form)">确定</button>
				<button type="button" id="btnCancel" onclick="_close()">取消</button>
			</div>
		</html:form>
	</tiles:put>
</tiles:insert>
