package com.fulong.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class RandomGifAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            Exception { //决定生成一个多长的字符串
        int len = 4;
        int width = 55;
        int height = 20;

        //取得一个指定长度随机字符串，包括数字和字母
        String randomStr = RandomStringUtils.random(len, true, false);

        //对session赋值。
        request.getSession(true).setAttribute("random", randomStr);

        //输出图片到浏览器
        response.setContentType("image/gif");
        //设置图片
        BufferedImage image = new BufferedImage(width, height,
                                                BufferedImage.TYPE_INT_RGB);
        Graphics gra = image.getGraphics();
        //设置背景色
        gra.setColor(Color.white);
        gra.fillRect(1, 1, 53, 18);
        //设置字体色
        gra.setColor(Color.BLACK);
        Font mFont = new Font("serif", 2, 16);
        gra.setFont(mFont);
        //画图
        char c;
        for (int i = 0; i < randomStr.length(); i++) {
            c = randomStr.charAt(i);
            gra.drawString(String.valueOf(c), i * 12 + 3, 15);
        }
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.
                getOutputStream());
        encoder.encode(image);

        response.getOutputStream().flush();
        response.getOutputStream().close();
        return null;
    }
}
