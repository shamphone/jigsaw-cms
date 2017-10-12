<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert definition="dialog_frame" >
  <tiles:put name="title">编辑样式</tiles:put>
  <tiles:put name="javascript">
	  <script language="Javascript" type="text/Javascript" src="<html:rewrite module="/site" page="/dialog.js"/>"></script>
	  <script language="Javascript" type="text/Javascript" src="style.js.jsp"></script>
  </tiles:put>
  <tiles:put name="dialog">
  <OBJECT id="dlgHelper" CLASSID="clsid:3050f819-98b5-11cf-bb82-00aa00bdce0b" width="0px" height="0px"></OBJECT>
    <table cellpadding="0" cellspacing="0" border="0" id="styleTable">
        <input type="hidden" name="cssID"/>
        <input type="hidden" name="type"/>
        <input type="hidden" name="selector"/>
        <tr>
          <td>
            <select id="pannelSelect" name="pannelSelect" onchange="selectPanel(this)" size="18">
              <option value="0" selected="selected">文字格式</option>
              <option value="1">背景设定</option>
              <option value="2">段落格式</option>
              <option value="3">边框格式</option>
              <option value="4">空白填充</option>
              <option value="5">定位</option>
              <option value="6">列表样式</option>
              <option value="7">其他效果</option>
              
            </select>
          </td>
          <td id="styleFields" style="width:300px;height:300px"><fieldset  id="editor">
            <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
              <tr height="28px"><td>字体颜色</td><td><input type="text" onchange="synStyle(this)" id="property(color)" style="width:130px"/> <button class="button" onclick="callColorDlg(document.getElementById('property(color)'))">选择...</button>
               </td></tr>
              <tr height="28px"><td>字体类型</td><td><select onchange="synStyle(this)" id="property(font-family)" style="width:130px">
                <option value="">请选择字体</option>
                <options collection="fonts" property="value" labelProperty="label"/>
              </select>
            </td></tr>
            <tr height="28px"><td>字体大小 </td><td><input type="text"  onchange="synStyle(this)" id="property(font-size)" style="width:130px"/></td></tr>
            <tr height="28px"><td>字体样式</td><td><select onchange="synStyle(this)" id="property(font-style)" style="width:130px">
              <option value="">请选择</option>
              <option value="normal">正常</option>
              <option value="italic">正斜体</option>
              <option value="oblique">反斜体</option>
            </select></td></tr>
            <tr height="28px"><td>文字效果</td><td><select onchange="synStyle(this)" id="property(text-decoration)" style="width:130px">
              <option value="">请选择</option>
              <option value="none">无</option>
              <option value="underline">下划线</option>
              <option value="overline">顶划线</option>
              <option value="line-through">中划线</option>
              <%-- <option value="blink">闪烁</option> --%>
            </select></td></tr>
            <tr height="28px"><td>行   距</td><td><input type="text"  onchange="synStyle(this)" id="property(line-height)" style="width:130px"/></td></tr>
            <tr height="28px"><td>文字粗细</td><td><select onchange="synStyle(this)" id="property(font-weight)"  style="width:130px">
              <option value="">请选择</option>
              <option value="normal">正常</option>
              <option value="bold">粗体</option>
              <option value="bolder">加粗</option>
              <option value="lighter">细体</option>
            </select></td></tr>
            <tr height="28px"><td>文字转换</td><td><select onchange="synStyle(this)" id="property(text-transform)"  style="width:130px">
              <option value="">请选择</option>
              <option value="capitalize">（英文）第一个字母大写</option>
              <option value="uppercase">（英文）全部转换成大写字母</option>
              <option value="lowercase">（英文）全部转换成小写字母</option>
              <option value="none">无</option>
            </select>
          </td></tr>
          <tr></tr>
        </table>
      </fieldset>
      <fieldset>
        <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
          <tr height="28px"><td>背景色</td><td><input type="text"  onchange="synStyle(this)" id="property(background-color)" style="width:130px"/><button class="commonbut" onclick="callColorDlg(document.getElementById('property(background-color)'))">选择...</button></td></tr>
          <tr height="28px"><td>背景图</td><td><input type="text"  id="property(background-image)" style="width:130px"/><button class="commonbut" onClick="selectResourse('property(background-image)')"/>选择...</button></td></tr>
          <tr height="28px"><td>固定背景</td><td> <select onchange="synStyle(this)" id="property(background-attachment)"  style="width:130px">
            <option value="">请选择</option>
            <option value="fixed">固定</option>
            <option value="scroll">添加滚动条</option>
          </select></td></tr>
          <tr height="28px"><td>图像重复</td><td><select onchange="synStyle(this)" id="property(background-repeat)"  style="width:130px">
            <option value="">请选择</option>
            <option value="no-repeat">无重复</option>
            <option value="repeat">纵横向上都重复</option>
            <option value="repeat-x">横向(x轴)重复</option>
            <option value="repeat-y">纵向（y轴）重复</option>
          </select></td></tr>
          <tr height="28px"><td>图像位置</td><td>
          	<input type="text"  onchange="synStyle(this)" id="property(background-position)" style="width:130px"/></td>
          </tr>
          <tr></tr>
        </table>
      </fieldset>
      <fieldset>
        <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable"  style="width:300px;height:285px">
          <tr height="28px"><td>文字间距</td><td><input type="text" onchange="synStyle(this)" id="property(word-spacing)" style="width:130px"/></td></tr>
          <tr height="28px"><td>字符间距</td><td><input type="text"  onchange="synStyle(this)" id="property(letter-spacing)" style="width:130px"/></td></tr>
          <tr height="28px"><td>水平对齐</td><td><select onchange="synStyle(this)" id="property(text-align)" style="width:130px">
            <option value="">请选择</option>
            <option value="left">左对齐</option>
            <option value="right">右对齐</option>
            <option value="center">居中对齐</option>
            <option value="justify">两端对齐</option>
          </select></td></tr>
          <tr height="28px"><td>垂直对齐</td><td><select onchange="synStyle(this)" id="property(vertical-align)"  style="width:130px">
            <option value="">请选择</option>
            <option value="baseline">基线对齐</option>
            <option value="top">顶端对齐</option>
            <option value="middle">居中对齐</option>
            <option value="bottom">底部对齐</option>
            <option value="sub">下标</option>
            <option value="super">上标</option>
            <option value="text-top">段落顶端对齐</option>
            <option value="text-bottom">段落底部对齐</option>
          </select></td></tr>
          <tr height="28px"><td>文本缩进</td><td><input type="text"  onchange="synStyle(this)" id="property(text-indent)" style="width:130px"/></td></tr>
          <tr height="28px"><td>空格处理</td><td> <select onchange="synStyle(this)" id="property(white-space)"  style="width:130px">
            <option value="">请选择</option>
            <option value="normal">可以在空格处换行</option>
            <option value="pre">保留输入中的空格</option>
            <option value="nowrap">不换行</option>
          </select></td></tr>
          <tr height="28px"><td>显示方式</td><td><select onchange="synStyle(this)" id="property(display)"  style="width:130px">
            <option value="">请选择</option>
            <option value="inline">正常显示</option>
            <option value="none">不显示</option>
            <option value="block">显示成块</option>
            <option value="list-item">显示成列表</option>
          </select></td></tr>
          <tr height="28px"><td>显示区域</td><td><select onchange="synStyle(this)" id="property(overflow)" style="width:130px">
            <option value="">请选择</option>
            <option value="visible">超长文字仍可见</option>
            <option value="hidden">不显示超长文字</option>
            <option value="scroll">显示滚动条</option>
            <option value="auto">自动处理</option>
          </select></td></tr>
          <tr></tr>
        </table>
      </fieldset>
      <fieldset>
        <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
          <tr height="28px"><td>边框颜色</td><td><input type="text"  onchange="synStyle(this)" id="property(border-color)" style="width:130px"/><button class="button"  onclick="callColorDlg(document.getElementById('property(border-color)'))">选择...</button></td></tr>
          <tr height="28px"><td>边框样式</td><td><select onchange="synStyle(this)" id="property(border-style)"  style="width:130px">
            <option value="">请选择</option>
            <option value="none">无</option>
            <option value="solid">实线</option>
            <option value="dashed">破折号</option>
            <option value="dotted">虚线</option>
            <option value="double">双线</option>
            <option value="groove">凹线</option>
            <option value="ridge">凸线</option>
            <option value="reset">开端</option>
            <option value="outset">嵌入</option>
          </select></td></tr>
          <tr height="28px"><td>边框宽度</td><td><input type="text"  onchange="synStyle(this)" id="property(border-size)" style="width:130px"/></td></tr>
          <tr height="28px"><td>上边框</td><td><input type="text"  onchange="synStyle(this)" id="property(border-top)" style="width:130px"/></td></tr>
          <tr height="28px"><td>下边框</td><td><input type="text" onchange="synStyle(this)" id="property(border-bottom)" style="width:130px"/></td></tr>
          <tr height="28px"><td>左边框</td><td><input type="text" onchange="synStyle(this)" id="property(border-left)" style="width:130px"/></td></tr>
          <tr height="28px"><td>右边框</td><td><input type="text" onchange="synStyle(this)" id="property(border-right)" style="width:130px"/></td></tr>
        <tr></tr>
        </table>
      </fieldset>
      <fieldset>
        <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
          <tr height="28px"><td>顶部空白</td><td><input type="text"  onchange="synStyle(this)" id="property(margin-top)" style="width:130px"/></td></tr>
          <tr height="28px"><td>底部空白</td><td><input type="text" onchange="synStyle(this)" id="property(margin-bottom)" style="width:130px"/></td></tr>
          <tr height="28px"><td>左侧空白</td><td><input type="text" onchange="synStyle(this)" id="property(margin-left)" style="width:130px"/></td></tr>
          <tr height="28px"><td>右侧空白</td><td><input type="text" onchange="synStyle(this)" id="property(margin-right)" style="width:130px"/></td></tr>
          <tr height="28px"><td>顶部填充</td><td><input type="text" onchange="synStyle(this)" id="property(padding-top)" style="width:130px"/></td></tr>
          <tr height="28px"><td>底部填充</td><td><input type="text" onchange="synStyle(this)" id="property(padding-bottom)" style="width:130px"/></td></tr>
          <tr height="28px"><td>左侧填充</td><td><input type="text" onchange="synStyle(this)" id="property(padding-left)" style="width:130px"/></td></tr>
          <tr height="28px"><td>右侧填充</td><td><input type="text" onchange="synStyle(this)" id="property(padding-right)" style="width:130px"/></td></tr>
       <tr></tr> 
        </table>
      </fieldset>

      <fieldset>
        <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
          <tr height="28px"><td>宽度</td><td><input type="text" onchange="synStyle(this)" id="property(width)" style="width:130px"/></td></tr>
          <tr height="28px"><td>高度</td><td><input type="text" onchange="synStyle(this)" id="property(height)" style="width:130px"/></td></tr>
          <tr height="28px"><td>环绕样式</td><td> <select onchange="synStyle(this)" id="property(float)" style="width:130px">
            <option value="">请选择</option>
            <option value="left">左对齐</option>
            <option value="right">右对齐</option>
            <option value="none">无</option>
          </select></td></tr>
          <tr height="28px"><td>是否显示 </td><td><select onchange="synStyle(this)" id="property(visibility)" style="width:130px">
            <option value="">请选择</option>
            <option value="visible">显示</option>
            <option value="hidden">不显示</option>
            <option value="inherit">和父结点一样</option>
          </select>
        </td></tr>
        <tr height="28px"><td>定位方式</td><td><select onchange="synStyle(this)" id="property(position)" style="width:130px">
          <option value="">请选择</option>
          <option value="absolute">绝对</option>
          <option value="relative">相对</option>
        </select></td></tr>
        <tr height="28px"><td></td><td>
          上<input type="text"  onchange="synStyle(this)" id="property(top)" size="2"/>
          左<input type="text" onchange="synStyle(this)" id="property(left)" size="2"/>
        </td></tr>
        <tr height="28px"><td>Z-顺序</td><td><input type="text" onchange="synStyle(this)" id="property(z-index)" style="width:130px"/></td></tr>
        <tr height="28px"><td>裁减</td><td><input type="text" onchange="synStyle(this)" id="property(clip)" style="width:130px"/></td></tr>
      <tr></tr>
      </table>
    </fieldset>
    <fieldset>
      <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
        <tr height="28px"><td>编号方式</td><td><select onchange="synStyle(this)" id="property(list-style-type)" style="width:130px">
          <option value="">请选择</option>
          <option value="disc">实心圆点</option>
          <option value="circle">空心圆点</option>
          <option value="square">实心方块</option>
          <option value="decimal">1,2,3,4...</option>
          <option value="lower-roman">i,ii,iii,iv...</option>
          <option value="up-roman">I,II,III,IV...</option>
          <option value="lower-alpha">a,b,c,d...</option>
          <option value="up-alpha">A,B,C,D...</option>
          <option value="none">无编号</option>
        </select></td></tr>
        <tr height="28px"><td>编号图片</td><td><input type="text" id="property(list-style-image)" style="width:130px"/><input type=button class="commonbut" onClick="selectResourse('property(list-style-image)')" value="选择. . ."/></td></tr>
        <tr height="28px"><td>缩进方式</td><td><select onchange="synStyle(this)" id="property(list-style-position)" style="width:130px">
          <option value="">请选择</option>
          <option value="inside">嵌入</option>
          <option value="outside">突出</option>
        </select></td></tr>
        <tr></tr>
      </table>
    </fieldset>
    <fieldset>
      <table cellpadding="0" cellspacing="0" border="0" id="styleItemTable" style="width:300px;height:285px">
        <tr height="28px"><td>鼠标形状</td><td><select onchange="synStyle(this)" id="property(cursor)" style="width:130px">
          <option value="">请选择</option>
          <option value="hand">手</option>
          <option value="crosshair">十字形</option>
          <option value="text">文字输入提示</option>
          <option value="wait">等待状态</option>
          <option value="default">缺省</option>
          <option value="help">帮助</option>
        </select>
      </td></tr>
      <tr height="28px"><td>行为</td><td><select onchange="synStyle(this)" id="property(behavior)" style="width:130px">
        <option value="">无</option>
        <bean:define id="htc1">url(<html:rewrite page="/htc/table_shuffle.htc" module=""/>)</bean:define>
        <option value='<%= ""+htc1 %>'>交替显示表格列</option>
        </select>
      </td></tr>
      <tr height="28px"><td>滤镜</td><td><select onchange="synStyle(this)" id="property(filter)" style="width:130px">
        <option value="">无</option>
        <option value="progid:DXImageTransform.Microsoft.Barn(duration=2, motion='out', orientation='vertical')">打开</option>
        <option value="progid:DXImageTransform.Microsoft.Blinds(direction='down')">百叶窗</option>
        <option value="progid:DXImageTransform.Microsoft.CheckerBoard(duration=5, direction='left')">棋盘</option>
        <option value="progid:DXImageTransform.Microsoft.Fade(duration=2)">渐变消失</option>
        <option value="progid:DXImageTransform.Microsoft.gradientWipe(duration=3, gradientsize=0.5)">插除</option>
        <option value="progid:DXImageTransform.Microsoft.Inset()">插入</option>
        <option value="progid:DXImageTransform.Microsoft.Iris(duration=3)">辐射</option>
        <option value="progid:DXImageTransform.Microsoft.Pixelate(duration=3, enabled='false')">马赛克</option>
        <option value="progid:DXImageTransform.Microsoft.RadialWipe(duration=2)">辐射插除</option>
        <option value="progid:DXImageTransform.Microsoft.RandomBars(duration=5)">随机线</option>
        <option value="progid:DXImageTransform.Microsoft.RandomDissolve(duration=3)">溶解</option>
        <option value="progid:DXImageTransform.Microsoft.Slide(duration=3, bands='8')">滑块</option>
        <option value="progid:DXImageTransform.Microsoft.Spiral(duration=3, GridSizeX=25, GridSizeY=25)">螺旋</option>
        <option value="progid:DXImageTransform.Microsoft.Stretch(duration=3)">拉伸</option>
        <option value="progid:DXImageTransform.Microsoft.Strips(duration=5, motion='rightdown')">锯齿边覆盖</option>
        <option value="progid:DXImageTransform.Microsoft.Wheel(duration=2, spokes=8)">辐条</option>
        <option value="progid:DXImageTransform.Microsoft.Zigzag(duration=3, GridSizeX=25, GridSizeY=25)">Z字型插除</option>
      </select></td></tr>
      <tr></tr>
    </table>
  </fieldset>
</td>
</tr>
</table>
<div class="operation">
    <button onclick="_Ok()">确定</button>
    <button onclick="window.close()">取消</button>
  </div>
<script language="javascript" type="text/Javascript">
	document.all.pannelSelect.selectedIndex=0;
	selectPanel(document.all.pannelSelect);
	
	function _Ok() {
		window.returnValue = _GetStyle();
		window.close();
	}
	window.onload = function() {
		//获取系统字体
		var cnt = dlgHelper.fonts.count;
		var fontFamily=document.getElementById('property(font-family)');
		for (var i = 1; i < cnt; i++)
			fontFamily.options[fontFamily.options.length] = new Option(dlgHelper.fonts(i),dlgHelper.fonts(i));
		////回显css设置
		var style = window.dialogArguments.css;
		if (style) {
			var ele;
			for (var e in style) {
				if (!style[e])
					continue;
				ele = document.getElementById("property(" + e + ")");
				if (ele)
					ele.value = style[e];
			}
		}
	}
	function selectPanel(oSelect){
		var index = oSelect.options[oSelect.selectedIndex].value;
		var fieldsets = document.all.styleFields.getElementsByTagName("fieldset");
		for(i=0; i<fieldsets.length; i++)
			fieldsets[i].style.display = "none";
		fieldsets[parseInt(index)].style.display = "";
	}
	function synStyle(submitter){
		//synSource(submitter.form);
		// document.all.previewUL.style.cssText=document.all.ruleSource.value;
	}
	function selectResourse(oInput) {
		var templateId=window.dialogArguments.templateId;
		var url='<html:rewrite module="/site/resource" page="/index.do"/>?';
		url+='templateID='+templateId;
		var ret=showModalDialog(url,"","dialogWidth:780px;dialogHeight:600px;help:no;scrollbars:yes;status:no");
		if (ret) {
			var imgPath=ret[0];
			if(imgPath!=null)
			document.getElementById(oInput).value='url('+imgPath+')';
		}
	}
  	function _GetStyle(){
		var style = new Style();

		////拼装文字格式CSS
		var sColor=document.getElementById('property(color)').value;
		var sFontFamily=document.getElementById('property(font-family)').value;
		var sFontSize=document.getElementById('property(font-size)').value;
		var sFontStyle=document.getElementById('property(font-style)').value;
		var sTextDecoration=document.getElementById('property(text-decoration)').value;
		var sLineHeight=document.getElementById('property(line-height)').value;
		var sFontWeight=document.getElementById('property(font-weight)').value;
		var sTextTransform=document.getElementById('property(text-transform)').value;
		if (sColor) style['color'] = sColor;
		if (sFontFamily) style['font-family'] = sFontFamily;
		if (sFontSize) style['font-size'] = sFontSize;
		if (sFontStyle) style['font-style'] = sFontStyle;
		if (sTextDecoration) style['text-decoration'] = sTextDecoration;
		if (sLineHeight) style['line-height'] = sLineHeight;
		if (sFontWeight) style['font-weight'] = sFontWeight;
		if (sTextTransform) style['text-transform'] = sTextTransform;

		/////拼装背景设定CSS
		var sBackgroundColor=document.getElementById('property(background-color)').value;
		var sBackgroundImage=document.getElementById('property(background-image)').value;
		var sBackgroundAttachment=document.getElementById('property(background-attachment)').value;
		var sBackgroundRepeat=document.getElementById('property(background-repeat)').value;
		var sBackgroundPosition=document.getElementById('property(background-position)').value;
		if (sBackgroundColor) style['background-color'] = sBackgroundColor;
		if (sBackgroundImage) style['background-image'] = sBackgroundImage;
		if (sBackgroundAttachment) style['background-attachment'] = sBackgroundAttachment;
		if (sBackgroundRepeat) style['background-repeat'] = sBackgroundRepeat;
		if (sBackgroundPosition) style['background-position'] = sBackgroundPosition;

		////拼装段落格式CSS
		var sWordSpacing=document.getElementById("property(word-spacing)").value;
		var sLetterSpacing=document.getElementById("property(letter-spacing)").value;
		var sTextAlign=document.getElementById("property(text-align)").value;
		var sVertiaclAlign=document.getElementById("property(vertical-align)").value;
		var sTextIndent=document.getElementById("property(text-indent)").value;
		var sWhiteSpace=document.getElementById("property(white-space)").value;
		var sDisplay=document.getElementById("property(display)").value;
		var sOverflow=document.getElementById("property(overflow)").value;
		if (sWordSpacing) style['word-spacing'] = sWordSpacing;
		if (sLetterSpacing) style['letter-spacing'] = sLetterSpacing;
		if (sTextAlign) style['text-align'] = sTextAlign;
		if (sVertiaclAlign) style['vertical-align'] = sVertiaclAlign;
		if (sTextIndent) style['text-indent'] = sTextIndent;
		if (sWhiteSpace) style['white-space'] = sWhiteSpace;
		if (sDisplay) style['display'] = sDisplay;
		if (sOverflow) style['overflow'] = sOverflow;

		//拼装边框格式CSS
		var sBorderColor=document.getElementById("property(border-color)").value;
		var sBorderStyle=document.getElementById("property(border-style)").value;
		var sBorderSize=document.getElementById("property(border-size)").value;
		var sBorderTop=document.getElementById("property(border-top)").value;
		var sBorderBottom=document.getElementById("property(border-bottom)").value;
		var sBorderLeft=document.getElementById("property(border-left)").value;
		var sBorderRight=document.getElementById("property(border-right)").value;
		if(sBorderColor) style['border-color'] = sBorderColor;
		if(sBorderStyle) style['border-style'] = sBorderStyle;
		if(sBorderSize) style['border-size'] = sBorderSize;
		if(sBorderTop) style['border-top'] = sBorderTop;
		if(sBorderBottom) style['border-bottom'] = sBorderBottom;
		if(sBorderLeft) style['border-left'] = sBorderLeft;
		if(sBorderRight) style['border-right'] = sBorderRight;

		////拼装空白填充css
		var sMarginTop=document.getElementById("property(margin-top)").value;
		var sMarginBottom=document.getElementById("property(margin-bottom)").value;
		var sMarginLeft=document.getElementById("property(margin-left)").value;
		var sMarginRight=document.getElementById("property(margin-Right)").value;
		var sPaddingTop=document.getElementById("property(padding-top)").value;
		var sPaddingBottom=document.getElementById("property(padding-bottom)").value;
		var sPaddingLeft=document.getElementById("property(padding-left)").value;
		var sPaddingRight=document.getElementById("property(padding-right)").value;
		if (sMarginTop) style['margin-top'] = sMarginTop;
		if (sMarginBottom) style['margin-bottom'] = sMarginBottom;
		if (sMarginLeft) style['margin-left'] = sMarginLeft;
		if (sMarginRight) style['margin-right'] = sMarginRight;
		if (sPaddingTop) style['padding-top'] = sPaddingTop;
		if (sPaddingBottom) style['padding-bottom'] = sPaddingBottom;
		if (sPaddingLeft) style['padding-left'] = sPaddingLeft;
		if (sPaddingRight) style['padding-right'] = sPaddingRight;

		//拼装定位css
		var sWidth=document.getElementById("property(width)").value;
		var sHeight=document.getElementById("property(height)").value;
		var sFloat=document.getElementById("property(float)").value;
		var sVisibility=document.getElementById("property(visibility)").value;
		var sPosition=document.getElementById("property(position)").value;
		var sTop=document.getElementById("property(top)").value;
		var sLeft=document.getElementById("property(left)").value;
		var sZindex=document.getElementById("property(z-index)").value;
		var sClip=document.getElementById("property(clip)").value;
		if (sWidth) style['width'] = sWidth;
		if (sHeight) style['height'] = sHeight;
		if (sFloat) style['float'] = sFloat;
		if (sVisibility) style['visibility'] = sVisibility;
		if (sPosition) style['position'] = sPosition;
		if (sTop) style['top'] = sTop;
		if (sLeft) style['left'] = sLeft;
		if (sZindex) style['z-index'] = sZindex;
		if (sClip) style['clip'] = sClip;

		//拼装列表样式css
		var sListStyleType=document.getElementById("property(list-style-type)").value;
		var sListStyleImage=document.getElementById("property(list-style-image)").value;
		var sListStylePosition=document.getElementById("property(list-style-position)").value;
		if (sListStyleType) style['list-style-type'] = sListStyleType;
		if (sListStyleImage) style['list-style-image'] = sListStyleImage;
		if (sListStylePosition) style['list-style-position'] = sListStylePosition;
		
		//其它效果css
		var sCursor=document.getElementById("property(cursor)").value;
		var sBehavior=document.getElementById("property(behavior)").value;
		var sFilter=document.getElementById("property(filter)").value;
		var sFloat=document.getElementById("property(float)").value;
		if (sCursor) style['cursor'] = sCursor;
		if (sBehavior) style['behavior'] = sBehavior;
		if (sFilter) style['filter'] = sFilter;
		return style;
	}

	//颜色选择器
	function  callColorDlg(oInput){
		//display  color  dialog  box
		var  sColor  =  dlgHelper.ChooseColorDlg();
		//change  decimal  to  hex
		sColor  =  sColor.toString(16);
		//add  extra  zeroes  if  hex  number  is  less  than  6  digits
		if  (sColor.length  <  6)  {
			var  sTempString  =  "000000".substring(0,6-sColor.length);
			sColor  =  sTempString.concat(sColor);
		}
		//change  color  of  the  text  
		oInput.value=sColor;
	}

/*  function synSource(theForm){
    var elements=theForm.elements;
    var src="";
    for(i=0;i<elements.length;i++){
      if((elements[i].name!=null)&&(elements[i].name.substring(0,8)=="property")){
        var name=elements[i].name.substring(9,elements[i].name.length-1);
        var value=elements[i].value;
        if((value!=null)&&(value.length>0))
        src=src+"\r\n"+name+":"+value+";";
      }
    }
    theForm.ruleSource.value=src;
  }
  function UploadImport(dEL){
    var url = "<html:rewrite module='' page='/common/upload.jsp' />";
    var arr = showModalDialog(url,window,"dialogWidth:350px;dialogHeight:80px;help:no;scroll:yes;status:no");
    if (arr) {
      dEL.value="url("+arr+")";
      synStyle(dEL);
    }
  }
  function save(submitter){
    submitter.disabled=true;
    submitter.form.submit();
  }
  function editorMode(submitter){
    submitter.form.editorInput.disabled=true;
    submitter.form.sourceInput.disabled=false;
    submitter.form.previewInput.disabled=false;
    synEditor(submitter.form);
    sourceCode.style.display="none";
    editor.style.display="";
    previewDiv.style.display="none";
  }

  function sourceMode(submitter){
    submitter.form.editorInput.disabled=false;
    submitter.form.sourceInput.disabled=true;
    submitter.form.previewInput.disabled=false;
    synSource(submitter.form);
    sourceCode.style.display="";
    editor.style.display="none";
    previewDiv.style.display="none";
  }
  function previewMode(submitter){
    submitter.form.editorInput.disabled=false;
    submitter.form.sourceInput.disabled=false;
    submitter.form.previewInput.disabled=true;
    if(sourceCode.style.display=='none')
    this.synSource(submitter.form);
    previewCSS.style.cssText=submitter.form.ruleSource.value;
    previewDiv.style.display="";
    sourceCode.style.display="none";
    editor.style.display="none";
  }
  function synEditor(theForm){
    var splits=theForm.ruleSource.value.toLowerCase().split(";");
    for(i=0;i<splits.length-1;i++){
      if((splits[i]!=null)&&(splits[i].length>5)){
        var style=splits[i].split(":");
        if(style.length==2){
          var name=style[0];
          var value=style[1];
          name=name.replace("\r","");
          name=name.replace("\n","");
          alert(name);
          var oElem=document.getElementsByName("property("+name+")");
          if(oElem.length>0){
            oElem[0].value=value;
          }

        }
      }
    }
    document.all.previewUL.style.cssText=theForm.ruleSource.value;
  }
*/
  </script>
  </tiles:put>
  </tiles:insert>
