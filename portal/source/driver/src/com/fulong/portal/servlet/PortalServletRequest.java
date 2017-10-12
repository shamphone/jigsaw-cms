package com.fulong.portal.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fulong.portal.core.Constants;
import com.fulong.portal.utils.Enumerator;

/**
 * 对输出流通道的控制，是由PortalRenderResponse来控制。在不需要输出时，Response通道被关闭，
 * 即调用页面上的JspWriter来输出时并不产生任何实际的输出。
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
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
 * @author Lixf
 * @version 1.0
 */
public class PortalServletRequest extends HttpServletRequestWrapper {

	@SuppressWarnings("unchecked")
	private Map parameters = null;

	public PortalServletRequest(HttpServletRequest request)
			throws ServletException, IOException {
		super(request);
		this.parseParameters();
	}

	/**
	 * Override the <code>getParameter()</code> method of the wrapped request.
	 * 
	 * @param name
	 *            Name of the requested parameter
	 */
	public String getParameter(String name) {

		Object value = parameters.get(name);
		if (value == null) {
			return (null);
		} else if (value instanceof String[]) {
			return (((String[]) value)[0]);
		} else if (value instanceof String) {
			return ((String) value);
		} else {
			return (value.toString());
		}
	}

	/**
	 * Override the <code>getParameterMap()</code> method of the wrapped
	 * request.
	 */
	@SuppressWarnings("unchecked")
	public Map getParameterMap() {
		return (parameters);
	}

	/**
	 * Override the <code>getParameterNames()</code> method of the wrapped
	 * request.
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		return (new Enumerator(parameters.keySet()));

	}

	/**
	 * Override the <code>getParameterValues()</code> method of the wrapped
	 * request.
	 * 
	 * @param name
	 *            Name of the requested parameter
	 */
	public String[] getParameterValues(String name) {

		Object value = parameters.get(name);
		if (value == null) {
			return ((String[]) null);
		} else if (value instanceof String[]) {
			return ((String[]) value);
		} else if (value instanceof String) {
			String values[] = new String[1];
			values[0] = (String) value;
			return (values);
		} else {
			String values[] = new String[1];
			values[0] = value.toString();
			return (values);
		}
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	private void parseParameters() throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest) this.getRequest();
		// String cipher =
		// this.getRequest().getParameter(Constants.REQUEST_PORTAL_STATE);
		// parameters = new
		// HashMap(ParametersCipher.getInstance().decode(cipher,
		// httpReq.getCharacterEncoding()));
		parameters = new LinkedHashMap(httpReq.getParameterMap());
		if (ServletFileUpload.isMultipartContent(httpReq)) {
			parameters.putAll(this.parserMultipart(httpReq));
		}
		// reqParams.remove(Constants.REQUEST_PORTAL_STATE);
	}

	/**
	 * 处理Multipart的参数
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map parserMultipart(HttpServletRequest request)
			throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		}
		ParameterMap map = new ParameterMap();
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) {
				map.addParameter(item.getFieldName(), item.getString("UTF-8"));
			} else {
				map.addParameter(item.getFieldName(), item);
			}
		}

		return map;
	}

	/*
	 * protected FormFile createDiskFile(FileItem item) throws IOException { if
	 * (item instanceof DiskFileItem) { DiskFileItem element = (DiskFileItem)
	 * item; if (element.getSize() != 0) { DiskFile theFile = new
	 * DiskFile(element.getStoreLocation().getAbsolutePath());
	 * theFile.setContentType(element.getContentType());
	 * theFile.setFileName(element.getName());
	 * theFile.setFileSize((int)element.getSize()); return theFile;
	 * 
	 * } } else { MultipartElement element = (MultipartElement) item; File
	 * tempFile = element.getFile(); if (tempFile.exists()) { DiskFile theFile =
	 * new DiskFile(tempFile.getAbsolutePath());
	 * theFile.setContentType(element.getContentType());
	 * theFile.setFileName(element.getFileName()); theFile.setFileSize((int)
	 * tempFile.length()); return theFile; } } return null; }
	 */
	/**
	 * 是否是Action请求,带有Action请求的query中必须包含portlet_action参数。
	 * 
	 * @return boolean
	 */
	public boolean isAction() {
		String firer = getParameter(Constants.REQUESTS_ACTION_FIRER);
		if (firer == null) {
			firer = getParameter(Constants.REQUESTS_ACTION_FIRER_TYPE);
		}
		return firer != null;
	}

	/**
	 * 是否将Preferences也发送过来了
	 * 
	 * @return boolean
	 */
	public boolean hasPreferences() {
		return (getParameter(Constants.REQUESTS_PREFERENCES) != null);
	}

	/**
	 * 获取当前最大化窗口的占位符
	 * 
	 * @return String
	 */
	public String getWindowOwner() {
		return this.getParameter(Constants.REQUEST_WINDOW_OWNER);
	}

	public String getAggregationPath() {
		String path = this.getHttpRequest().getServletPath();
		return path;
	}

	private HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) this.getRequest();
	}

	public BufferedReader getReader() throws IOException {
		return this.getRequest().getReader();
	}

}
