package com.fulong.report;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRWrappingSvgRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.util.JRTypeSniffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>
 * Title: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Description: 龙驭网站管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @version 1.0
 */
public class ImageServlet extends BaseServlet {

	private static final long serialVersionUID = -2285953709950391398L;

	public static final String IMAGE_NAME_REQUEST_PARAMETER = "image";
	private static final Log log = LogFactory.getLog(ImageServlet.class);

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String imageName = request.getParameter(IMAGE_NAME_REQUEST_PARAMETER);
		if ("px".equals(imageName)) {
			response.sendRedirect(request.getContextPath()
					+ "/common/images/report/pixel.GIF");
			return;
		}
		// long count = sc++;
		byte[] imageData = null;
		String imageMimeType = null;

		// log.trace("" + count + "begin service for:" +
		// request.getRequestURL());
		String reportId = request.getParameter("reportId");
		Map parameters = Collections.synchronizedMap(new HashMap(request
				.getParameterMap()));
		// parameters.put("ReportTitle", "Address Report");
		long start = 0;
		if (log.isDebugEnabled())
			start = System.currentTimeMillis();
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = this.reports.genReport(reportId, parameters);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		// log.trace("[" + (System.currentTimeMillis() - start) +
		// "]Jasper Print generated.");
		if (log.isDebugEnabled())
			start = System.currentTimeMillis();

		List jasperPrints = new Vector();
		jasperPrints.add(jasperPrint);
		JRPrintImage image = JRHtmlExporter.getImage(jasperPrints, imageName);

		JRRenderable renderer = image.getRenderer();
		if (renderer.getType() == JRRenderable.TYPE_SVG) {
			renderer = new JRWrappingSvgRenderer(renderer, new Dimension(image
					.getWidth(), image.getHeight()),
					JRElement.MODE_OPAQUE == image.getMode() ? image
							.getBackcolor() : null);
		}

		imageMimeType = JRTypeSniffer.getImageMimeType(renderer.getImageType());

		try {
			imageData = renderer.getImageData();
		} catch (JRException e) {
			throw new ServletException(e);
		}

		if (imageData != null && imageData.length > 0) {
			if (imageMimeType != null) {
				response.setHeader("Content-Type", imageMimeType);
			}
			response.setContentLength(imageData.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			try {
				ouputStream.write(imageData, 0, imageData.length);
				ouputStream.flush();
			} finally {
				ouputStream.close();
			}
		}

		log.trace("[" + (System.currentTimeMillis() - start)
				+ "] Image generated for: " + reportId);

	}

}
