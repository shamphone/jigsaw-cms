<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/site/css/framework.jsp">
  <tiles:put name="content">
<html:javascript formName="cssRuleForm" bundle="css"/>
      <h2><bean:write name="stylesheet" property="name"/>&nbsp;( <bean:write name="cssRuleForm" property="selector"/> )</h2>
    <html:form  action="/updateRule.do" method="POST" >
      <html:hidden property="cssID"/>
      <html:hidden property="type"/>
      <html:hidden property="selector"/>
      <p class="operation">
        <input type="button" disabled="disabled" class="commonbut" id="editorInput" onclick="editorMode(this)" value="编辑器"/>
        <input type="button" class="commonbut" id="sourceInput" onclick="sourceMode(this)" value="源代码"/>
        <input type="button" class="commonbut" id="previewInput" onclick="previewMode(this)" value="预览效果"/>
        <input type="button" class="commonbut" onclick="save(this)" value="保存"/>
        <input type="button" class="commonbut" onclick="window.location='css.do?id=<fulong:encode name='cssRuleForm' property='cssID'/>'" value="返回样式编辑"/>
      </p>
      <div class="block">
        <ul  id="editor">
          <li>文字格式
            <ul>
              <li>字体颜色:<html:text property="property(color)" /> </li>

              <li>字体类型：<html:select property="property(font-family)">
                <html:option value="">请选择字体</html:option>
                <html:options collection="fonts" property="value" labelProperty="label"/>
              </html:select>
            </li>
            <li>字体大小: <html:text property="property(font-size)"/></li>
            <li>字体样式：<html:select property="property(font-style)" >
              <html:option value="">请选择</html:option>
              <html:option value="nomal">正常</html:option>
              <html:option value="italic">正斜体</html:option>
              <html:option value="oblique">反斜体</html:option>
            </html:select></li>
            <li>文字效果:<html:select property="property(text-decroation)">
              <html:option value="">请选择</html:option>
              <html:option value="none">无</html:option>
              <html:option value="underline">下划线</html:option>
              <html:option value="overline">顶划线</html:option>
              <html:option value="line-through">中划线</html:option>
              <html:option value="blink">闪烁</html:option>
            </html:select></li>
            <li>行   距: <html:text property="property(line-height)"/></li>
            <li>文字粗细: <html:select property="property(font-weight)" >
              <html:option value="">请选择</html:option>
              <html:option value="normal">normal</html:option>
              <html:option value="bold">bold</html:option>
              <html:option value="border">border</html:option>
              <html:option value="lighter">lighter</html:option>
              <html:option value="100">100</html:option>
              <html:option value="200">200</html:option>
              <html:option value="300">300</html:option>
              <html:option value="400">400</html:option>
              <html:option value="500">500</html:option>
              <html:option value="600">600</html:option>
              <html:option value="700">700</html:option>
              <html:option value="800">800</html:option>
              <html:option value="900">900</html:option>
            </html:select></li>
            <li>文字转换：<html:select property="property(text-transform)" >
              <html:option value="">请选择</html:option>
              <html:option value="capitalize">（英文）第一个字母大写</html:option>
              <html:option value="uppercase">（英文）全部转换成大写字母</html:option>
              <html:option value="lowercase">（英文）全部转换成小写字母</html:option>
              <html:option value="none">无</html:option>
            </html:select>
          </li>
        </ul>
      </li>
      <li>背景设定
        <ul>
          <li>背景色: <html:text property="property(background-color)"/></li>
          <li>背景图:  <html:file property="property(background-image)"/></li>
          <li>固定背景: <html:select property="property(background-attachment)" >
            <html:option value="">请选择</html:option>
            <html:option value="fixed">固定</html:option>
            <html:option value="scroll">添加滚动条</html:option>
          </html:select></li>
          <li>背景图像重复方式<html:select property="property(background-repeat)" >
            <html:option value="">请选择</html:option>
            <html:option value="no-repeat">无重复</html:option>
            <html:option value="repeat">纵横向上都重复</html:option>
            <html:option value="repeat-x">横向(x轴)重复</html:option>
            <html:option value="repeat-y">纵向（y轴）重复</html:option>
          </html:select></li>
        </ul>
      </li>
      <li>段落格式
        <ul>
          <li>文字间距：<html:text property="property(word-spacing)"/></li>
          <li>字符间距：<html:text property="property(letter-spacing)"/></li>
          <li>水平对齐方式： <html:select property="property(text-align)">
            <html:option value="">请选择</html:option>
            <html:option value="left">左对齐</html:option>
            <html:option value="right">右对齐</html:option>
            <html:option value="center">居中对齐</html:option>
            <html:option value="justify">两端对齐</html:option>
          </html:select></li>
          <li>垂直对齐方式：<html:select property="property(vertiacl-align)" >
            <html:option value="">请选择</html:option>
            <html:option value="baseline">基线对齐</html:option>
            <html:option value="top">顶端对齐</html:option>
            <html:option value="middle">居中对齐</html:option>
            <html:option value="bottom">底部对齐</html:option>
            <html:option value="sub">下标</html:option>
            <html:option value="super">上标</html:option>
            <html:option value="text-top">段落顶端对齐</html:option>
            <html:option value="text-bottom">段落底部对齐</html:option>
          </html:select></li>
          <li>文本缩进： <html:text property="property(text-indent)"/></li>
          <li>空格处理：  <html:select property="property(white-space)" >
            <html:option value="">请选择</html:option>
            <html:option value="normal">可以在空格处换行</html:option>
            <html:option value="pre">保留输入中的空格</html:option>
            <html:option value="nowrap">不换行</html:option>
          </html:select></li>
          <li>显示方式： <html:select property="property(display)" >
            <html:option value="">请选择</html:option>
            <html:option value="inline">正常显示</html:option>
            <html:option value="block">显示成块</html:option>
            <html:option value="list-item">显示成列表</html:option>
          </html:select></li>
          <li>当内容超过显示区域：<html:select property="property(overflow)">
            <html:option value="">请选择</html:option>
            <html:option value="visible">超长文字仍可见</html:option>
            <html:option value="hidden">不显示超长文字</html:option>
            <html:option value="scroll">显示滚动条</html:option>
            <html:option value="auto">自动处理</html:option>
          </html:select></li>
        </ul>
      </li>
      <li>边框格式
        <ul>
          <li>边框颜色:<html:text property="property(border-color)"/></li>
          <li>边框样式:<html:select property="property(border-style)">
            <html:option value="">请选择</html:option>
            <html:option value="none">无</html:option>
            <html:option value="solid">实线</html:option>
            <html:option value="dashed">破折号</html:option>
            <html:option value="dotted">虚线</html:option>
            <html:option value="double">双线</html:option>
            <html:option value="groove">凹线</html:option>
            <html:option value="ridge">凸线</html:option>
            <html:option value="reset">开端</html:option>
            <html:option value="outset">嵌入</html:option>
          </html:select></li>
          <li>边框宽度:<html:text property="property(border-size)"/></li>
          <li>上边框:<html:text property="property(border-top)"/></li>
          <li>下边框:<html:text property="property(border-bottom)"/></li>
          <li>左边框:<html:text property="property(border-left)"/></li>
          <li>右边框:<html:text property="property(border-right)"/></li>
        </ul>
      </li>
      <li>定位
        <ul>
          <li>宽度：<html:text property="property(width)"/></li>
          <li>高度：<html:text property="property(height)"/></li>
          <li>环绕样式： <html:select property="property(float)">
            <html:option value="">请选择</html:option>
            <html:option value="left">左对齐</html:option>
            <html:option value="right">右对齐</html:option>
            <html:option value="none">无</html:option>
          </html:select></li>
          <li>空白：
            <ul>
              <li>顶部空白：<html:text property="property(margin-top)"/></li>
              <li>底部空白：<html:text property="property(margin-bottom)"/></li>
              <li>左侧空白：<html:text property="property(margin-left)"/></li>
              <li>右侧空白：<html:text property="property(margin-right)"/></li>
            </ul>
          </li>
          <li>填充：
            <ul>
              <li>顶部填充：<html:text property="property(padding-top)"/></li>
              <li>底部填充：<html:text property="property(padding-bottom)"/></li>
              <li>左侧填充：<html:text property="property(padding-left)"/></li>
              <li>右侧填充：<html:text property="property(padding-right)"/></li>
            </ul>
          </li>
          <li>是否显示： <html:select property="property(visibility)">
            <html:option value="">请选择</html:option>
            <html:option value="visible">显示</html:option>
            <html:option value="hidden">不显示</html:option>
            <html:option value="inherit">和父结点一样</html:option>
          </html:select>
        </li>
        <li>定位方式：<html:select property="property(position)">
          <html:option value="">请选择</html:option>
          <html:option value="absolute">绝对</html:option>
          <html:option value="relative">相对</html:option>
        </html:select></li>
        <li>顶端定位：<html:text property="property(top)"/></li>
        <li>底部定位：<html:text property="property(bottom)"/></li>
        <li>左侧定位：<html:text property="property(left)"/></li>
        <li>右侧定位：<html:text property="property(right)"/></li>
        <li>Z-顺序：<html:text property="property(z-index)"/></li>
        <li>裁减：<html:text property="property(clip)"/></li>
      </ul>
    </li>
    <li>列表样式
      <ul>
        <li>编号方式： <html:select property="property(list-style-type)">
          <html:option value="">请选择</html:option>
          <html:option value="disc">实心圆点</html:option>
          <html:option value="circle">空心圆点</html:option>
          <html:option value="square">实心方块</html:option>
          <html:option value="decimal">1,2,3,4...</html:option>
          <html:option value="lower-roman">i,ii,iii,iv...</html:option>
          <html:option value="up-roman">I,II,III,IV...</html:option>
          <html:option value="lower-alpha">a,b,c,d...</html:option>
          <html:option value="up-alpha">A,B,C,D...</html:option>
          <html:option value="none">无编号</html:option>
        </html:select></li>
        <li>编号图片： <html:file property="property(list-style-image)"/></li>
        <li>编号缩进方式： <html:select property="property(list-style-position)">
          <html:option value="">请选择</html:option>
          <html:option value="inside">嵌入</html:option>
          <html:option value="outside">突出</html:option>
        </html:select></li>
      </ul>
    </li>
    <li>其他效果：
      <ul>
        <li>鼠标形状：<html:select property="property(cursor)">
          <html:option value="">请选择</html:option>
          <html:option value="hand">手</html:option>
          <html:option value="crosshair">十字形</html:option>
          <html:option value="text">文字输入提示</html:option>
          <html:option value="wait">等待状态</html:option>
          <html:option value="default">缺省</html:option>
          <html:option value="help">帮助</html:option>
        </html:select>
      </li>
      <li>滤镜：  <html:text property="property(filter)"/></li>
    </ul>
  </li>
</ul>
</div>
</div>
<div class="block" id="sourceCode" style="display:none">
  <textarea name="ruleSource" id="ruleSource" cols="80" rows="30"></textarea>
</div>

<div class="block" id="previewDiv" style="display:none">
  <span id="previewCSS">
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
    <p>这是预览的效果。</p>
  </span>
</div>
</html:form>
</tiles:put>
</tiles:insert>
<script language="javascript" type="text/Javascript">
  function save(submitter){
    if(validateCssRuleForm(submitter.form)){
      submitter.disabled=true;
      if(sourceCode.style.display=='none')
      this.synSource(submitter.form);
      submitter.form.submit();
    }

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
    var splits=theForm.ruleSource.value..toLowerCase().split(";");
    for(i=0;i<splits.length-1;i++){
      if((splits[i]!=null)&&(splits[i].length>5)){
        var style=splits[i].split(":");
        if(style.length==2){
          var name=style[0];
          var value=style[1];
          name=name.replace("\r","");
          name=name.replace("\n","");
          var oElem=document.getElementsByName("property("+name+")");
          if(oElem.length>0){
            oElem[0].value=value;
          }

        }
      }
    }

  }
  function synSource(theForm){
    var elements=theForm.elements;
    var src="";
    for(i=0;i<elements.length;i++){
      if(elements[i].name.substring(0,8)=="property"){
        var name=elements[i].name.substring(9,elements[i].name.length-1);
        var value=elements[i].value;
        if((value!=null)&&(value.length>0))
        src=src+"\r\n"+name+":"+value+";";
      }
    }
    theForm.ruleSource.value=src;
  }
  </script>
