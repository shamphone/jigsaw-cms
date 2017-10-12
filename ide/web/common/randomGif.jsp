<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.awt.*,java.awt.image.*,org.apache.commons.lang.*,javax.servlet.*,javax.servlet.http.*,com.sun.image.codec.jpeg.*"%>
<%
  //决定生成一个多长的字符串
  int len = 4;
  int width = 55;
  int height = 20;

  //取得一个指定长度随机字符串，包括数字和字母
  String randomStr = RandomStringUtils.random(len , true , false);

  //对session赋值。
  request.getSession(true).setAttribute("random",randomStr);

  //输出图片到浏览器
  response.setContentType("image/gif");
  //设置图片
  BufferedImage image=new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
  Graphics gra=image.getGraphics();
  //设置背景色
  gra.setColor(Color.white);
  gra.fillRect(1,1,53,18);
  //设置字体色
  gra.setColor(Color.BLACK);
  Font mFont = new Font("serif",2,16);
  gra.setFont(mFont);
  //画图
  char c;
  for(int i=0;i<randomStr.length();i++)
  {
   c = randomStr.charAt(i);
   gra.drawString(String.valueOf(c) , i*12 + 3 ,15);
  }
  JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(response.getOutputStream());
  encoder.encode(image);
  response.getOutputStream().close();

%>
