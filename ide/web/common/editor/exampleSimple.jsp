<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"  %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html>
<head>
  <title>简单多格式文本测试页面</title>
</head>
<body>
  <form action="#">
    <input type="hidden" name="richtext" value="&lt;font class=&quot;font&quot;&gt;　　温家宝在全国城镇居民基本医疗保险试点工作座谈会上指出&lt;p&gt;　　高度重视 精心组织
			扎扎实实搞好城镇居民基本医疗保险试点&lt;/p&gt;
			&lt;p&gt;　　全国城镇居民基本医疗保险试点工作会议7月23日至24日在北京召开。 &lt;br&gt;
			 &lt;br&gt;
			　　23日下午，中共中央政治局常委、国务院总理温家宝与出席会议的部分代表进行了座谈，听取他们对试点工作的意见和建议，并作了重要讲话。中共中央政治局委员、国务院副总理、国务院城镇居民基本医疗保险部际联席会议组长吴仪出席会议并讲话。&lt;/font&gt;&lt;/div&gt;
		&lt;/font&gt;"/>
    <iframe src="simpleEditor.do?InstanceName=richtext" width="960" height="500" frameborder="0" marginheight="0" marginwidth="0" scrolling="no">
    </iframe>
  </form>
</body>
</html>
