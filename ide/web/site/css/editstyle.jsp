<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/site/framework.jsp"> <tiles:put name="content">
<div id="body">
<ul>
      <li "body_text">样式列表</li>
      <li "body_table"><table width="100%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#CCCCCC">
          <tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
            <td>选择</td>
            <td>样式名称</td>
            <td>预览</td>
          </tr>
          <tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
            <td><span class="style3">
              <input type="checkbox" name="checkbox4" value="checkbox">
            </span></td>
            <td>style1 </td>
            <td>这里是样式效果</td>
          </tr>
          <tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
            <td><span class="style3">
              <input type="checkbox" name="checkbox42" value="checkbox">
            </span></td>
            <td>style2</td>
            <td>这里是样式效果</td>
          </tr>
          <tr bordercolor="#FFFFFF" bgcolor="#FFFFFF">
            <td><span class="style3">
              <input type="checkbox" name="checkbox43" value="checkbox">
            </span></td>
            <td>style3</td>
            <td>这里是样式效果</td>
          </tr>
        </table>			</td>
          </tr>
      </table>

            <li id="body_checkbox"><span class="style3">
              <input type="checkbox" name="checkbox44" value="checkbox">
              全选
            </span></li>
			<li id="body_add"><html:button value="添加新样式"  ></html:button></li>
			<li id="body_delete"><html:button value="删除选择样式"  ></html:button></li>
			</ul>
</tiles:put>
<tiles:put name="help">
 <div id="help">
    <ul>
    <li><img src="../images/spacer.gif" width="210" height="1"></li>
       <li id="help_img"><img src="../images/helpcenter/help_38.gif" width="200" height="20"></li>
       <li class="help_text">浏览可用的网站及网站列表。“共享对象”可以创建基于所有独立网站及网站共享的内容...</span>
       <li class="help_text"><span class="strong_font">新增内容库: </span>增加一个新的内容库...</li>
       <li class="help_text"><span class="strong_font">删除内容库: </span>删除已经存在的内容库.</li>
       <li class="help_text"><a href="#" onClick="javascript:window.open('../public/sethelp.asp','','width=510 height=230');">帮助的设置>></a></li>
    </ul>
</div>
 </tiles:put>
 </tiles:insert>
