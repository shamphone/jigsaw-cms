<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %>
<%!Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	String sRand = request.getParameter("code");
	//字体设置
	String name = request.getParameter("name"); 							//字体名
	int charWidth = 13; 													//字符宽度
	if(request.getParameter("charWidth")!=null){
		try {
			charWidth = Integer.parseInt(request.getParameter("charWidth"));
		} catch (NumberFormatException e) {
			//ignore
		}
	}
	
	int width = charWidth * sRand.length() + 6;
	int height = 20;
	
	BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	
	Graphics g = image.getGraphics();
	
	Random random = new Random();
	
	g.setColor(getRandColor(200, 250));
	g.fillRect(0, 0, width, height);
	g.setFont(new Font(name, Font.PLAIN, 18));
	g.setColor(new Color(255, 255, 255));
	g.drawRect(0, 0, width - 1, height - 1);

	g.setColor(getRandColor(160, 200));
	for (int i = 0; i < 155; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x, y, x + xl, y + yl);
	}

	for (int i = 0; i < sRand.length(); i++) {
		String rand = sRand.substring(i, i + 1);
		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
		g.drawString(rand, charWidth * i + 6, height-4);
	}

	session.setAttribute("rand", sRand);
	g.dispose();

	ServletOutputStream outStream = response.getOutputStream();
	ImageIO.write(image, "JPEG", outStream);
	outStream.flush();
	outStream.close();
	out.clear();
	out = pageContext.pushBody();
%>

