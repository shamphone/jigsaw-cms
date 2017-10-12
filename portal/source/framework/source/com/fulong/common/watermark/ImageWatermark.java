package com.fulong.common.watermark;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author xiaming
 * @version 1.0
 */
public class ImageWatermark {
	public ImageWatermark() {
	}

	/**
	 * 把水印图片附加到目标图片上
	 * 
	 * @param targetImg
	 *            --目标图片(Path)
	 * @param pressImg
	 *            --水印图片(Path)
	 * @param x
	 *            --(水印图片的左上角)X坐标
	 * @param y
	 *            --(水印图片的左上角)Y坐标
	 * @param scale
	 *            --水印图片缩放比例
	 * @param alpha
	 *            --水印透明度(范围：[0.0, 1.0])
	 * @throws FileNotFoundException
	 *             、IOException
	 */
	public void pressImage(String targetImg, String pressImg, int x, int y,
			double scale, float alpha) throws FileNotFoundException,
			IOException {
		// 目标文件
		File src_file = new File(targetImg);
		Image src = null;
		src = ImageIO.read(src_file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = null; // 原来是Graphics，因使用setComposite方法，改为Graphics2D
		try {
			g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);

			// 水印文件
			File src_mark_file = new File(pressImg);
			Image src_mark = null;
			src_mark = ImageIO.read(src_mark_file);

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_mark, x, y, width, height, null);
			// 水印文件结束
		} finally {
			g.dispose();
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(targetImg);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
		} finally {
			out.close();
		}
	}

	/**
	 * 重载：图片水印
	 * 
	 * @param targetImg
	 *            --目标图片(Path)
	 * @param pressImg
	 *            --水印图片(Path)
	 * @param x
	 *            --(水印图片的左上角)X坐标
	 * @param y
	 *            --(水印图片的左上角)Y坐标
	 * @throws FileNotFoundException
	 *             、IOException
	 */
	public void pressImage(String targetImg, String pressImg, int x, int y)
			throws FileNotFoundException, IOException {
		pressImage(targetImg, pressImg, x, y, 1.0, 1.0F); // 默认:水印不缩放、不透明
	}

	/**
	 * 把水印文字附加到目标图片上
	 * 
	 * @param targetImg
	 *            --目标图片(Path)
	 * @param pressText
	 *            --水印文字
	 * @param x
	 *            --(水印文字之左上角)X轴向偏移量
	 * @param y
	 *            --(水印文字之左上角)Y轴向偏移量
	 * @param fontColor
	 *            --水印字体颜色
	 * @param fontName
	 *            --水印字体名
	 * @param fontStyle
	 *            --水印字体样式
	 * @param fontSize
	 *            --水印字体大小
	 * @param alpha
	 *            --水印透明度(范围：[0.0, 1.0])
	 * @throws FileNotFoundException
	 *             、IOException
	 */
	public void pressText(String targetImg, String pressText, int x, int y,
			Color fontColor, String fontName, int fontStyle, int fontSize,
			float alpha) throws FileNotFoundException, IOException {
		File _file = new File(targetImg);
		Image src = ImageIO.read(_file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = null; // 原来是Graphics，因使用setComposite方法，改为Graphics2D
		try {
			g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(fontColor);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawString(pressText, x, y + fontSize);
		} finally {
			g.dispose();
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
		} finally {
			out.close();
		}
	}

	/**
	 * 重载：文字水印
	 * 
	 * @param targetImg
	 *            --目标图片(Path)
	 * @param pressText
	 *            --水印文字
	 * @param x
	 *            --(水印文字之左上角)X轴向偏移量
	 * @param y
	 *            --(水印文字之左上角)Y轴向偏移量
	 * @throws FileNotFoundException
	 *             、IOException
	 */
	public void pressText(String targetImg, String pressText, int x, int y)
			throws FileNotFoundException, IOException {
		pressText(targetImg, pressText, x, y, Color.BLUE, "宋体", Font.PLAIN, 20,
				1.0F); // 默认:水印文字为蓝色、宋体、普通、20磅、不透明
	}

	/*
	 * public static void main(String[] args) { //测试文字水印 ImageWatermark mark =
	 * new ImageWatermark(); try{
	 * mark.pressText("D:/Workspace Java/JBuilder/Watermark/src/word.jpg",
	 * "中科辅龙w", 0, 0); }catch(Exception e){ } //测试图像水印 try{
	 * mark.pressImage("D:/Workspace Java/JBuilder/Watermark/src/word.jpg",
	 * "D:/Workspace Java/JBuilder/Watermark/src/地&城铁线路.bmp", 0, 0, 1.0, 0.5F);
	 * }catch(Exception e){ } } //
	 */

}
