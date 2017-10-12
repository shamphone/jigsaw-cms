<%@page contentType="text/html; charset=utf-8"%>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%>

<style type="text/css">
<!--
body,td,th {}
-->
#product_listpage td{padding-right:50px;padding-left:50px;padding-top:10px;}

.time{margin-left:8px;}

.ulright_1 ul{line-height:24px;
           list-style-image:url(../images/cssimages/dot.gif);
           margin-left:30px;
           margin-bottom:6px;}
.ulright_1 li{}

.ulright_3 ul{line-height:24px;
           list-style-image:url(../images/cssimages/dot.gif);
           margin-left:30px;
           margin-bottom:6px;}
.ulright_3 li{}

.ulright_4 ul{line-height:20px;
           list-style-image:url(../images/cssimages/dot.gif);
           margin-left:30px;
           margin-bottom:6px;}
.ulright_4 li{}

.ulright_4 ul{line-height:20px;
           list-style-image:url(../images/cssimages/dot.gif);
           margin-left:30px;
           margin-bottom:6px;}
.ulright_4 li{}

.righttdborder ul{line-height:20px;
                  list-style-image:url(../images/cssimages/dot.gif);
                  margin-left:22px;
                  margin-top:5px;
                  margin-bottom:10px;}
.righttdborder li{overflow:hidden;
                  text-overflow:ellipsis;
                  white-space:nowrap;
                  width:228px;}

.search{ list-style-type:none; margin:0px; padding:0px;}
.null{ display:none; }
</style>

<table width="770" height="68" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="386">
      <fulong:portlet id="pc_logo" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//logo.jspf</fulong:value>
          </fulong:preference>
      </fulong:portlet>
    </td>
    <td width="391" valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="toprighttable">
        <tr>
          <td width="9">
		  <!--
            <fulong:portlet id="ci_topImage1" type="common_image">
              <fulong:preference>
                <fulong:name>url</fulong:name>
                <fulong:value>#</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>target</fulong:name>
                <fulong:value>_self</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>source</fulong:name>
                <fulong:value>/资源文件/图片/index_02.gif</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>width</fulong:name>
                <fulong:value>9</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>height</fulong:name>
                <fulong:value>20</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
            <img src="../images/itimages/index_02.gif" width="9" height="20" alt="">
          </td>
          <td>
<SCRIPT language=JavaScript>

var y=new Date();
var gy=y.getYear();
var dName=new Array("星期天","星期一","星期二","星期三","星期四","星期五","星期六");
var mName=new Array("01","02","03","04","05","06","07","08","09","10","11","12");
{document.write("<span class=date>",y.getYear(),"</span>","年","<span class=enb>",mName[y.getMonth()],"</span>","月","<span class=enb>",y.getDate(),"</span>","日&nbsp;&nbsp;&nbsp;",dName[y.getDay()]);
}

</SCRIPT>          </td>
          <td width="12">
		  <!--
            <fulong:portlet id="ci_topImage2" type="common_image">
              <fulong:preference>
                <fulong:name>url</fulong:name>
                <fulong:value>#</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>target</fulong:name>
                <fulong:value>_self</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>source</fulong:name>
                <fulong:value>/资源文件/图片/index_06.gif</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>width</fulong:name>
                <fulong:value>11</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>height</fulong:name>
                <fulong:value>10</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
            <img src="../images/itimages/index_06.gif" width="11" height="10" alt="">
          </td>
          <td>
		  <!--
            <fulong:portlet id="tl_topLink1" type="text_link">
              <fulong:preference>
                <fulong:name>url</fulong:name>
                <fulong:value>#</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>text</fulong:name>
                <fulong:value>文字链接</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>target</fulong:name>
                <fulong:value>_self</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
		  <a href="../pages/index.pgp">首页</a>
		  </td>
          <td width="12">
		  <!--
            <fulong:portlet id="ci_topImage3" type="common_image">
              <fulong:preference>
                <fulong:name>url</fulong:name>
                <fulong:value>#</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>target</fulong:name>
                <fulong:value>_self</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>source</fulong:name>
                <fulong:value>/资源文件/图片/index_08.gif</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>width</fulong:name>
                <fulong:value>11</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>height</fulong:name>
                <fulong:value>10</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
		<img src="../images/itimages/index_08.gif" width="11" height="10" alt="">
          </td>
          <td>
		  <!--
            <fulong:portlet id="tl_topLink2" type="text_link">
              <fulong:preference>
                <fulong:name>url</fulong:name>
                <fulong:value>#</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>text</fulong:name>
                <fulong:value>文字链接</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>target</fulong:name>
                <fulong:value>_self</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
			<a href="../pages/ch_06.pgp">联系我们</a>
          </td>
        </tr>
      </table>
      <table width="100%" height="48" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="25" colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td width="140" align="right">
		  <!--
            <fulong:portlet id="st_search" type="simple_text">
              <fulong:preference>
                <fulong:name>text</fulong:name>
                <fulong:value>搜索</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
          </td>
          <td align="center">
            <fulong:portlet id="sf_topForm" type="search_form">
              <fulong:preference>
                <fulong:name>field-style</fulong:name>
                <fulong:value>searchinput</fulong:value>
              </fulong:preference>
            </fulong:portlet>			
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="770" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6" class="toptdbg01"></td>
  </tr>
  <tr>
    <td height="30" class="toptdbg02">
      <fulong:portlet id="t_nav" type="transverse">
        <fulong:preference>
          <fulong:name>separat</fulong:name>
          <fulong:value>|</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>target</fulong:name>
          <fulong:value>_self</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>rule</fulong:name>
          <fulong:value>subordinate</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>folder_style</fulong:name>
          <fulong:value>navlink</fulong:value>
        </fulong:preference>
      </fulong:portlet>
    </td>
  </tr>
  <tr>
    <td height="1" bgcolor="#FFFFFF">    </td>
  </tr>
</table>
<!-- end of head -->
<table width="770" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="205" class="lefttd">
	<fulong:portlet id="pc_identity" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//公司形象图片.jspf</fulong:value>
          </fulong:preference>
      </fulong:portlet>
	  </td>
    <td width="565" class="toptitle">
      <fulong:portlet id="pc_topTitle" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//首页标语.jspf</fulong:value>
          </fulong:preference>
      </fulong:portlet>
    </td>
  </tr>
</table>
<table width="770" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="205" valign="top" class="lefttd">
      <table width="205" border="0" cellspacing="0" cellpadding="0" class="lefttablebg1">
        <tr>
          <td height="26">
		  <!--
            <fulong:portlet id="st_pcName1" type="simple_text">
              <fulong:preference>
                <fulong:name>text</fulong:name>
                <fulong:value>公司介绍</fulong:value>
              </fulong:preference>
              <fulong:preference>
                <fulong:name>display_style</fulong:name>
                <fulong:value>lefttitle</fulong:value>
              </fulong:preference>
            </fulong:portlet>
			-->
			<span class="lefttitle">公司介绍</span>
			</td>
        </tr>
        <tr>
          <td>
            <fulong:portlet id="pc_pcContent1" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//公司简介.jspf</fulong:value>
          </fulong:preference>
           </fulong:portlet>
          </td>
        </tr>
        <tr>
          <td>
            <fulong:portlet id="pc_ad1" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//广告列表1.jspf</fulong:value>
          </fulong:preference>
            </fulong:portlet>
          </td>
        </tr>
        <tr>
          <td>
            <fulong:portlet id="pc_ad2" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//广告列表2.jspf</fulong:value>
          </fulong:preference>
		    </fulong:portlet>
          </td>
        </tr>
      </table>
    </td>
    <td width="6">&nbsp;</td>
    <td width="559" valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <fulong:portlet id="pc_introduction" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//首页图片新闻+摘要_300.jspf</fulong:value>
          </fulong:preference>
		    </fulong:portlet>
          </td>
        </tr>
      </table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <fulong:portlet id="pc_main_ad" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//首页主要广告_558.jspf</fulong:value>
          </fulong:preference>
	  </fulong:portlet>
    </td>
  </tr>
</table>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <fulong:portlet id="t_newsChannelName1" type="transverse">
        <fulong:preference>
          <fulong:name>separator</fulong:name>
          <fulong:value> </fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>target</fulong:name>
          <fulong:value>_self</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>rule</fulong:name>
          <fulong:value>custom</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>folder_style</fulong:name>
          <fulong:value>righttitle</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>number</fulong:name>
          <fulong:value>1</fulong:value>
        </fulong:preference>
      </fulong:portlet>
    </td>
    <td width="8" rowspan="3">&nbsp;</td>
    <td>
      <fulong:portlet id="t_newsChannelName2" type="transverse">
        <fulong:preference>
          <fulong:name>separator</fulong:name>
          <fulong:value> </fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>target</fulong:name>
          <fulong:value>_self</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>rule</fulong:name>
          <fulong:value>custom</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>folder_style</fulong:name>
          <fulong:value>righttitle</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>number</fulong:name>
          <fulong:value>1</fulong:value>
        </fulong:preference>
      </fulong:portlet>
    </td>
  </tr>
  <tr>
    <td class="righttdborder">
      <fulong:portlet id="nl_newsChannel1" type="news_list">
        <fulong:preference>
          <fulong:name>more_text</fulong:name>
          <fulong:value>更多...</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>more</fulong:name>
          <fulong:value>more</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>com</fulong:name>
          <fulong:value>column1</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>li</fulong:name>
          <fulong:value>column1text</fulong:value>
        </fulong:preference>
      </fulong:portlet>
    </td>
    <td class="righttdborder">
      <fulong:portlet id="nl_newsChannel2" type="news_list">
        <fulong:preference>
          <fulong:name>more_text</fulong:name>
          <fulong:value>更多...</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>more</fulong:name>
          <fulong:value>more</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>com</fulong:name>
          <fulong:value>column1</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>li</fulong:name>
          <fulong:value>column1text</fulong:value>
        </fulong:preference>
      </fulong:portlet>
    </td>
  </tr>
</table>
    </td>
  </tr>
</table>

</td></tr></table><table width="770" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="footertable">
  <tr>
    <td width="260" height="100" align="center">
      <p class="countertext">        您是第
        <fulong:portlet id="sc_bottom" type="site_counter"></fulong:portlet>
        位访问者

    </td>
    <td>
      <p class="footer">
        <fulong:portlet id="pc_copyright" type="page_clip">
          <fulong:preference>
            <fulong:name>source</fulong:name>
            <fulong:value>/clips//版权信息.jspf</fulong:value>
          </fulong:preference>
        </fulong:portlet>

    </td>
  </tr>
</table>
<!-- End ImageReady Slices -->
