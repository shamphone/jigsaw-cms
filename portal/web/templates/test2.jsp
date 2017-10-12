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
	<meta name="robots" content="index,follow" />
	<title></title>
</head>
<body bgcolor="#FFFFFF">
<div id="container">
  这里是结果：
 <fulong:portlet id="html" type="html"><fulong:preference><fulong:name>text</fulong:name><fulong:value>我国奇迹 adfasdfasf</fulong:value></fulong:preference></fulong:portlet>
内容域占位符:

 计数器
 <fulong:portlet id="counter" type="counter">
 </fulong:portlet>
 新闻列表:
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
      </fulong:portlet>
</div>
</body>
</html>
