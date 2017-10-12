<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/WEB-INF/fulong-portal.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>new title</title>


<LINK rel=stylesheet type="text/css" href=style3.css></head>
<body bgcolor="#FFFFFF">
<div id="container">
  <table width="100%" border="1" cellpadding="2" cellspacing="2">
   <tr>
      <td>网站

     列表:</td>
      <td><fulong:portlet id="siteList" type="siteindex">
      </fulong:portlet></td>
   </tr>    <tr>
      <td>html<span class="Frame-Row4-04-R">代码</span>占位副:</td>
      <td><fulong:portlet id="html" type="html"><fulong:preference><fulong:name>text</fulong:name><fulong:value>my new text1</fulong:value><fulong:value>my new text2</fulong:value><fulong:value>my new text3</fulong:value></fulong:preference><fulong:preference><fulong:name>text2</fulong:name><fulong:value>my new html text</fulong:value></fulong:preference><fulong:preference><fulong:name>text3</fulong:name><fulong:value>my 3text1</fulong:value><fulong:value>my 3text2</fulong:value></fulong:preference></fulong:portlet></td>
   </tr>
         <tr>
      <td>html代码:</td>
      <td><fulong:portlet id="html2" type="html"><fulong:preference><fulong:name>text</fulong:name><fulong:value>my new text1</fulong:value><fulong:value>my new text2</fulong:value><fulong:value>my new text3</fulong:value></fulong:preference><fulong:preference><fulong:name>text2</fulong:name><fulong:value>my new html text</fulong:value></fulong:preference><fulong:preference><fulong:name>text3</fulong:name><fulong:value>my 3text1</fulong:value><fulong:value>my 3text2</fulong:value></fulong:preference></fulong:portlet></td>
      </tr>
    <tr>
      <td>搜索表单:</td>
      <td><fulong:portlet id="searchform" type="search_form"></fulong:portlet></td>
      </tr>
  <tr>
      <td>相关链接:</td>
      <td><fulong:portlet id="relative" type="relative"></fulong:portlet></td>
      </tr>
    <tr>
      <td>计数器:</td>
      <td><fulong:portlet id="counter" type="counter"><fulong:preference><fulong:name>type</fulong:name><fulong:value>site</fulong:value></fulong:preference><fulong:preference><fulong:name>font_style</fulong:name><fulong:value></fulong:value></fulong:preference></fulong:portlet></td>
      </tr>
 <tr>
      <td>重

      复器:</td>
      <td><fulong:portlet id="nl_newsChannel3" type="content-repeater">
        <fulong:preference> <fulong:name>more_text</fulong:name> <fulong:value>更多...</fulong:value> </fulong:preference> <fulong:preference> <fulong:name>more</fulong:name> <fulong:value>more</fulong:value> </fulong:preference> <fulong:preference> <fulong:name>block</fulong:name> <fulong:value>2</fulong:value> </fulong:preference> <fulong:preference> <fulong:name>repository-id</fulong:name> <fulong:value>20070101</fulong:value> </fulong:preference> <fulong:preference> <fulong:name>block-fields</fulong:name> <fulong:value>title</fulong:value> <fulong:value>area</fulong:value> </fulong:preference> <fulong:preference> <fulong:name>com</fulong:name> <fulong:value>column1</fulong:value> <fulong:value>column1</fulong:value> </fulong:preference> <fulong:preference> <fulong:name>li</fulong:name> <fulong:value>column1text</fulong:value> <fulong:value>column1text</fulong:value> </fulong:preference>
      </fulong:portlet></td>
      </tr>
   <tr>
      <td>新闻表格:</td>
      <td><fulong:portlet id="nl_newsChannel3" type="content-grid">
        <fulong:preference>
          <fulong:name>more_text</fulong:name>
          <fulong:value>更多...</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>more</fulong:name>
          <fulong:value>more</fulong:value>
        </fulong:preference>
          <fulong:preference>
          <fulong:name>block</fulong:name>
          <fulong:value>2</fulong:value>
        </fulong:preference>
           <fulong:preference>
          <fulong:name>repository-id</fulong:name>
          <fulong:value>20070101</fulong:value>
        </fulong:preference>
           <fulong:preference>
          <fulong:name>block-fields</fulong:name>
          <fulong:value>title</fulong:value>
          <fulong:value>area</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>com</fulong:name>
          <fulong:value>column1</fulong:value>
          <fulong:value>column1</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>li</fulong:name>
          <fulong:value>column1text</fulong:value>
          <fulong:value>column1text</fulong:value>
        </fulong:preference>
      </fulong:portlet></td>
      </tr>
    <tr>
      <td>新闻列表:</td>
      <td><fulong:portlet id="nl_newsChannel2" type="news_list">
        <fulong:preference>
          <fulong:name>more_text</fulong:name>
          <fulong:value>更多...</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>more</fulong:name>
          <fulong:value>more</fulong:value>
        </fulong:preference>
          <fulong:preference>
          <fulong:name>block</fulong:name>
          <fulong:value>2</fulong:value>
        </fulong:preference>
           <fulong:preference>
          <fulong:name>repository-id</fulong:name>
          <fulong:value>20070101</fulong:value>
        </fulong:preference>
           <fulong:preference>
          <fulong:name>block-fields</fulong:name>
          <fulong:value>title</fulong:value>
          <fulong:value>area</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>com</fulong:name>
          <fulong:value>column1</fulong:value>
          <fulong:value>column1</fulong:value>
        </fulong:preference>
        <fulong:preference>
          <fulong:name>li</fulong:name>
          <fulong:value>column1text</fulong:value>
          <fulong:value>column1text</fulong:value>
        </fulong:preference>
      </fulong:portlet></td>
      </tr>

    <tr>
      <td>内容域占位符:</td>
      <td><fulong:portlet id="field" type="field"></fulong:portlet></td>
      </tr>
  <tr>
      <td>内容编辑器占位符:</td>
      <td><fulong:portlet id="editor" type="editor"></fulong:portlet></td>
      </tr>
        <tr>
      <td>URL:</td>
      <td><fulong:portlet id="URL" type="URL"><fulong:preference><fulong:name>url</fulong:name><fulong:value>http://www.google.cn</fulong:value></fulong:preference></fulong:portlet></td>
      </tr>
    </table>
</div></body>
</html>
