<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="javascript">
    <script language="JavaScript" type="text/javascript">
      var selectedStyle="none";
      function doSelected(oSelect){
        var style=oSelect.options[oSelect.selectedIndex].css;
        oCode.value=style;
        selectedStyle=oSelect[oSelect.selectedIndex].value;
        var orgin=oPrev;
        var text=oPrev.innerHTML;
        var oNew=document.createElement("<div id='oPrev' style=\'"+style+"\'></div>");
        oNew.innerHTML=text;
        orgin.replaceNode(oNew);
      }
      function doClose(){
        var periodPos=selectedStyle.lastIndexOf (".");
        selectedStyle=selectedStyle.substring(periodPos+1,selectedStyle.length);
        var reg=new RegExp("\\W");
        var pos=selectedStyle.search(reg);
        if(pos>0)
        selectedStyle=selectedStyle.substring(0,pos);
        window.returnValue=selectedStyle;
        window.close();
      }
      </script>
      </tiles:put>
      <tiles:put name="dialog">
    <script language="JavaScript" type="text/javascript">
      document.body.onload = function(){
        var styleSheets=window.dialogArguments.styleSheets;
        if(styleSheets!=null)
        for(i=0;i<styleSheets.length;i++){
          var oSheet=styleSheets[i];
          if(oSheet.href!='/longcon/portal/style.css'){
            var rules=oSheet.rules;
            for(r=0;r<rules.length;r++){
              var rule=rules[r];
              var opt=document.createElement("option");
              opt.value=rule.selectorText;
              opt.text=rule.selectorText;
              opt.title=rule.selectorText;
              opt.css=rule.style.cssText;
              styleList.options.add(opt);
            }
          }
        }
      }
      </script>
      <table width="100%" cellspacing="3" cellpadding="2" border="0">
        <tr>
          <td valign="top">
            <div><span>样式</span></div>
            <select multiple="multiple" size="20" id="styleList" style="width:120px" onchange="doSelected(this)">
              <option value="" css="">无样式</option>
            </select>
          </td>
          <td valign="top"><table width="100%" cellspacing="0" cellpadding="2" border="0">
              <tr>
                <td>样式代码</td>
              </tr>
              <tr>
                <td><textarea id="oCode" cols="20" rows="5" style="width:240px;height:100px;"></textarea></td>
              </tr>
              <tr>
                <td>预览</td>
              </tr>
              <tr>
                <td><div style='color:#c0c0c0;background-color:white;border:2px inset;height:200px;width:240px;padding:2px 2px 2px 2px;overflow:hidden'>
                 LongCon系列软件采用国际先进的技术，如网站内容管理系统的技术和功能达到了国内领先、国际先进水平。
                 <div id="oPrev">
                 另外由于该系列软件来源于实际的项目，特别符合电子政务、企业信息化的需要。软件设计中考虑了多种应用的需要，软件适应面广，实施方便。
                 </div>
                 中科辅龙公司有常设的专业培训和服务队伍，能解决软件应用中遇到的个中问题，并指导用户开展应用；软件还可根据用户需要定制。

                </div></td>
              </tr>
              </table></td>
        </tr>
        </table>
        <div class="operation">
          <button type="submit">新增样式...</button>
          <button type="submit"onclick="doClose()">确定</button>
          <button type="submit"onclick="window.close()">取消</button>
        </div>
      </tiles:put>
    </tiles:insert>
