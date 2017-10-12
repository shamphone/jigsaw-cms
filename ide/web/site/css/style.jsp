<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%/**
  * 编辑style对话框。
  * 输入：
  *   1. selector: 当前选择器，用来显示在标题上。
  *   2. style:当前选择器对应的style，用来初始化表单数据。
  *   3. templateID：当前编辑的模板的ID  
  * 输出：如果按取消按钮，则不输出。否则输出：
  *   1. selector: 当前选择器
  *   2. style:当前选择器对应的style，即编辑后的style。   
  * 注意： 
  *   本页面内容参考visual studio.net的样式选择器进行排版布局。
  *   页面上用到选择图片或者文件的操作，调用资源管理器
  *
  **/ 
%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="dialog">
    <form action="#" id="styleForm">
      <div class="block">
        <ul  id="editor">
          <li>文字格式
            <ul>
              <li>字体颜色:<input type="text" name="color" /> </li>

              <li>字体类型：<select name="font-family">
                <option value="">请选择字体</option>
              </select>
            </li>
            <li>字体大小: <input type="text" name="font-size"/></li>
            <li>字体样式：<select name="font-style" >
              <option value="">请选择</option>
              <option value="nomal">正常</option>
              <option value="italic">正斜体</option>
              <option value="oblique">反斜体</option>
            </select></li>
            <li>文字效果:<select name="text-decroation">
              <option value="">请选择</option>
              <option value="none">无</option>
              <option value="underline">下划线</option>
              <option value="overline">顶划线</option>
              <option value="line-through">中划线</option>
              <option value="blink">闪烁</option>
            </select></li>
            <li>行   距: <input type="text" name="line-height"/></li>
            <li>文字粗细: <select name="font-weight" >
              <option value="">请选择</option>
              <option value="normal">normal</option>
              <option value="bold">bold</option>
              <option value="border">border</option>
              <option value="lighter">lighter</option>
              <option value="100">100</option>
              <option value="200">200</option>
              <option value="300">300</option>
              <option value="400">400</option>
              <option value="500">500</option>
              <option value="600">600</option>
              <option value="700">700</option>
              <option value="800">800</option>
              <option value="900">900</option>
            </select></li>
            <li>文字转换：<select name="text-transform" >
              <option value="">请选择</option>
              <option value="capitalize">（英文）第一个字母大写</option>
              <option value="uppercase">（英文）全部转换成大写字母</option>
              <option value="lowercase">（英文）全部转换成小写字母</option>
              <option value="none">无</option>
            </select>
          </li>
        </ul>
      </li>
      <li>背景设定
        <ul>
          <li>背景色: <input type="text" name="background-color"/></li>
          <li>背景图:  <input type="file" name="background-image"/></li>
          <li>固定背景: <select name="background-attachment" >
            <option value="">请选择</option>
            <option value="fixed">固定</option>
            <option value="scroll">添加滚动条</option>
          </select></li>
          <li>背景图像重复方式<select name="background-repeat" >
            <option value="">请选择</option>
            <option value="no-repeat">无重复</option>
            <option value="repeat">纵横向上都重复</option>
            <option value="repeat-x">横向(x轴)重复</option>
            <option value="repeat-y">纵向（y轴）重复</option>
          </select></li>
        </ul>
      </li>
      <li>段落格式
        <ul>
          <li>文字间距：<input type="text" name="word-spacing"/></li>
          <li>字符间距：<input type="text" name="letter-spacing"/></li>
          <li>水平对齐方式： <select name="text-align">
            <option value="">请选择</option>
            <option value="left">左对齐</option>
            <option value="right">右对齐</option>
            <option value="center">居中对齐</option>
            <option value="justify">两端对齐</option>
          </select></li>
          <li>垂直对齐方式：<select name="vertiacl-align" >
            <option value="">请选择</option>
            <option value="baseline">基线对齐</option>
            <option value="top">顶端对齐</option>
            <option value="middle">居中对齐</option>
            <option value="bottom">底部对齐</option>
            <option value="sub">下标</option>
            <option value="super">上标</option>
            <option value="text-top">段落顶端对齐</option>
            <option value="text-bottom">段落底部对齐</option>
          </select></li>
          <li>文本缩进： <input type="text" name="text-indent"/></li>
          <li>空格处理：  <select name="white-space" >
            <option value="">请选择</option>
            <option value="normal">可以在空格处换行</option>
            <option value="pre">保留输入中的空格</option>
            <option value="nowrap">不换行</option>
          </select></li>
          <li>显示方式： <select name="display" >
            <option value="">请选择</option>
            <option value="inline">正常显示</option>
            <option value="block">显示成块</option>
            <option value="list-item">显示成列表</option>
          </select></li>
          <li>当内容超过显示区域：<select name="overflow">
            <option value="">请选择</option>
            <option value="visible">超长文字仍可见</option>
            <option value="hidden">不显示超长文字</option>
            <option value="scroll">显示滚动条</option>
            <option value="auto">自动处理</option>
          </select></li>
        </ul>
      </li>
      <li>边框格式
        <ul>
          <li>边框颜色:<input type="text" name="border-color"/></li>
          <li>边框样式:<select name="border-style">
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
          </select></li>
          <li>边框宽度:<input type="text" name="border-size"/></li>
          <li>上边框:<input type="text" name="border-top"/></li>
          <li>下边框:<input type="text" name="border-bottom"/></li>
          <li>左边框:<input type="text" name="border-left"/></li>
          <li>右边框:<input type="text" name="border-right"/></li>
        </ul>
      </li>
      <li>定位
        <ul>
          <li>宽度：<input type="text" name="width"/></li>
          <li>高度：<input type="text" name="height"/></li>
          <li>环绕样式： <select name="float">
            <option value="">请选择</option>
            <option value="left">左对齐</option>
            <option value="right">右对齐</option>
            <option value="none">无</option>
          </select></li>
          <li>空白：
            <ul>
              <li>顶部空白：<input type="text" name="margin-top"/></li>
              <li>底部空白：<input type="text" name="margin-bottom"/></li>
              <li>左侧空白：<input type="text" name="margin-left"/></li>
              <li>右侧空白：<input type="text" name="margin-right"/></li>
            </ul>
          </li>
          <li>填充：
            <ul>
              <li>顶部填充：<input type="text" name="padding-top"/></li>
              <li>底部填充：<input type="text" name="padding-bottom"/></li>
              <li>左侧填充：<input type="text" name="padding-left"/></li>
              <li>右侧填充：<input type="text" name="padding-right"/></li>
            </ul>
          </li>
          <li>是否显示： <select name="visibility">
            <option value="">请选择</option>
            <option value="visible">显示</option>
            <option value="hidden">不显示</option>
            <option value="inherit">和父结点一样</option>
          </select>
        </li>
        <li>定位方式：<select name="position">
          <option value="">请选择</option>
          <option value="absolute">绝对</option>
          <option value="relative">相对</option>
        </select></li>
        <li>顶端定位：<input type="text" name="top"/></li>
        <li>底部定位：<input type="text" name="bottom"/></li>
        <li>左侧定位：<input type="text" name="left"/></li>
        <li>右侧定位：<input type="text" name="right"/></li>
        <li>Z-顺序：<input type="text" name="z-index"/></li>
        <li>裁减：<input type="text" name="clip"/></li>
      </ul>
    </li>
    <li>列表样式
      <ul>
        <li>编号方式： <select name="list-style-type">
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
        </select></li>
        <li>编号图片： <input type="file" name="list-style-image"/></li>
        <li>编号缩进方式： <select name="list-style-position">
          <option value="">请选择</option>
          <option value="inside">嵌入</option>
          <option value="outside">突出</option>
        </select></li>
      </ul>
    </li>
    <li>其他效果：
      <ul>
        <li>鼠标形状：<select name="cursor">
          <option value="">请选择</option>
          <option value="hand">手</option>
          <option value="crosshair">十字形</option>
          <option value="text">文字输入提示</option>
          <option value="wait">等待状态</option>
          <option value="default">缺省</option>
          <option value="help">帮助</option>
        </select>
      </li>
      <li>滤镜：  <input type="text" name="filter"/></li>
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
</form>
</tiles:put>
<tiles:put name="javascript">
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
      synSource(submitter.form);
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
          var oElem=document.getElementsByName(name);
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
  </tiles:put>
</tiles:insert>