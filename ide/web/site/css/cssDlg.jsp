<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/dialogFrame.jsp">
  <tiles:put name="title">管理样式</tiles:put>
  <tiles:putList name="stylesheets">
    <tiles:add><html:rewrite page="/style.css"/></tiles:add>
  </tiles:putList>
  <tiles:put name="content">
    <logic:notEmpty name="cssRuleList">
      <table cellpadding="2" cellspacing="2" border="0" id="cssTable">
        <html:form action="/deleteRule.do" method="POST">
          <input type="hidden" name="id" value="<bean:write name='css' property="name"/>" />
          <tr>
            <td>
              <bean:parameter id="selectedSelector" name="selector" value="donoselector"/>
              <select id="selector" size="23" name="selector" onchange="doSelect(this)">
                <logic:iterate id="rule" name="cssRuleList">
                  <option <logic:match name="rule" property="selectorText" value='<%= ""+selectedSelector %>' >selected="selected"</logic:match> id="<bean:write name="rule"  property="selectorText"/>" value="<bean:write name="rule"  property="selectorText"/>" title="<bean:write name="rule" property="style.text"/>">
                  <bean:write name="rule"  property="selectorText"/></option>
                </logic:iterate>
              </select>
            </td>
            <td>
              <fieldset>
                <legend>预览效果</legend>
                <div id="previewDiv">
                  <ul id="previewUL">
                    <li>这里是正常文字的预览效果</li>
                    <li><a href="#">这里是超链接的预览效果</a></li>
                    <li><span>这里是块的预览效果</span></li>
                    <li>这里是正常文字的预览效果</li>
                    <li><a href="#">这里是超链接的预览效果</a></li>
                    <li><span>这里是块的预览效果</span></li>
                  </ul>
                </div>
              </fieldset>
              <fieldset>
                <legend>样式内容</legend>
                <div id="cssTextDiv">
                  <span id="cssText"></span>
                </div>
              </fieldset>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="operation">
              <input type="button" value="新建.." onclick="insert(this)"/>
              <input name="editButton" type="button" value="修改.." onclick="doEdit(this)" disabled="disabled"/>
              <input name="deleteButton" type="button" value="删除" onclick="doDelete(this)" disabled="disabled"/>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="operation">
              <input type="button" value="确定" onclick="doOK(this)"/>
            </td>
          </tr>
        </html:form>
      </table>
    </logic:notEmpty>
  </tiles:put>
</tiles:insert>
<script language="javascript" type="text/javascript">
  function doOK(submitter){
    var oSel=submitter.form.elements["selector"];
    if(oSel.selectedIndex>=0){
      var ret=new Object();
      var opt=oSel.options[oSel.selectedIndex];
      ret.selector=opt.value;
      ret.text=opt.title;
      ret.styleClass=parseClass(opt.value);
      window.returnValue=ret;
    }
     window.close();
  }
  function parseClass(value){
    var selector=new String(value);
      var start=selector.indexOf(".");
      if(start<0) return "";
      var end=selector.substr(start+1).search(new RegExp("\\W","g"));
      if(end<0) return selector.substr(start+1);
      return selector.substring(start+1).substr(0,end);
  }
  function doSelect(submitter){
    document.all.previewUL.style.cssText=submitter.options[submitter.selectedIndex].title;
    document.all.cssText.innerText=submitter.options[submitter.selectedIndex].title;
    submitter.form.editButton.disabled=false;
    submitter.form.deleteButton.disabled=false;
  }
  function doDelete(submitter){
    if(confirm('请确认删除已选中样式？')){
      submitter.form.action="<html:rewrite page='/deleteRuleDlg.do'/>";
      submitter.disabled=false;
      submitter.form.submit();
    }
  }

  function insert(submitter){
    var url="<html:rewrite page='/createRuleDlg.do'/>?id=<fulong:encode name='css' property='name'/>";
    var feature="dialogWidth:300px;dialogHeight:200px;status:no;";
    var ret=window.showModalDialog(url,"",feature);
    if(ret!=null)
    window.navigate("<html:rewrite page='/cssDlg.do'/>?id="+submitter.form.elements["id"].value);
  }
  function doEdit(submitter){
    var oSel=document.all("selector");
    var selector=oSel.options[oSel.selectedIndex].value;
    var url="<html:rewrite page='/editRuleDlg.do'/>?css=<fulong:encode name='css' property='name'/>";
    url=url+"&selector="+selector;
    var feature="dialogWidth:560px;dialogHeight:480px;status:no;";
    var ret=window.showModalDialog(url,"",feature);
    if(ret!=null){
      var reloadURL="<html:rewrite page='/cssDlg.do'/>?id="+encodeURIComponent(submitter.form.elements["id"].value);
      reloadURL=reloadURL+"&selector="+ret.selector;
      window.navigate(reloadURL);
    }
  }

  function viewSource(submitter){
    submitter.form.action="<html:rewrite page='/CSSSource.do'/>";
    submitter.form.submit();
  }

  </script>
