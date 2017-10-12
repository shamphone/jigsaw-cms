package com.fulong.webdav.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.RequestUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xml.sax.SAXException;

import com.fulong.common.FileUtils;
import com.fulong.webdav.utils.FastHttpDateFormat;
import com.fulong.webdav.utils.MD5Encoder;
import com.fulong.webdav.utils.URLEncoder;
import com.fulong.webdav.utils.XMLWriter;

/**
 * 
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

public abstract class WebDavServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2051633315286242404L;
	private LockManager lockManager;
	private static final Log log = LogFactory.getLog(WebDavServlet.class);
	/**
	 * Simple date format for the creation date ISO representation (partial).
	 */
	private static final SimpleDateFormat creationDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

	private static String statusOK = new String("HTTP/1.1 "
			+ WebdavStatus.SC_OK + " "
			+ WebdavStatus.getStatusText(WebdavStatus.SC_OK));

	private static String statusNotFound = new String("HTTP/1.1 "
			+ WebdavStatus.SC_NOT_FOUND + " "
			+ WebdavStatus.getStatusText(WebdavStatus.SC_NOT_FOUND));
	private static String supportedLocks = "<lockentry>"
			+ "<lockscope><exclusive/></lockscope>"
			+ "<locktype><write/></locktype>" + "</lockentry>" + "<lockentry>"
			+ "<lockscope><shared/></lockscope>"
			+ "<locktype><write/></locktype>" + "</lockentry>";

	static {
		creationDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	// ----------------------------------------------------- Instance Variables

	/**
	 * The debugging detail level for this servlet.
	 */
	private int debug = 0;

	/**
	 * The input buffer size to use when serving resources.
	 */
	private int input = 2048;

	/**
	 * Should we generate directory listings?
	 */
	private boolean listings = true;

	/**
	 * Read only flag. By default, it's set to true.
	 */
	private boolean readOnly = true;

	/**
	 * The output buffer size to use when serving resources.
	 */
	private int output = 2048;

	/**
	 * MD5 message digest provider.
	 */
	private static MessageDigest md5Helper;

	/**
	 * The MD5 helper object for this class.
	 */
	private static final MD5Encoder md5Encoder = new MD5Encoder();

	/**
	 * Array containing the safe characters set.
	 */
	private static URLEncoder urlEncoder;

	// ----------------------------------------------------- Static Initializer

	/**
	 * GMT timezone - all HTTP dates are on GMT
	 */
	static {
		urlEncoder = new URLEncoder();
		urlEncoder.addSafeCharacter('-');
		urlEncoder.addSafeCharacter('_');
		urlEncoder.addSafeCharacter('.');
		urlEncoder.addSafeCharacter('*');
		urlEncoder.addSafeCharacter('/');
	}

	/**
	 * MIME multipart separation string
	 */
	private static final String mimeSeparation = "CATALINA_MIME_BOUNDARY";

	// --------------------------------------------------------- Public Methods

	/**
	 * Finalize this servlet.
	 */
	public void destroy() {
	}

	// -------------------------------------------------------------- Constants

	private static final String METHOD_PROPFIND = "PROPFIND";
	private static final String METHOD_PROPPATCH = "PROPPATCH";
	private static final String METHOD_MKCOL = "MKCOL";
	private static final String METHOD_COPY = "COPY";
	private static final String METHOD_MOVE = "MOVE";
	private static final String METHOD_LOCK = "LOCK";
	private static final String METHOD_UNLOCK = "UNLOCK";

	/**
	 * Default depth is infite.
	 */
	public static final int INFINITY = 3; // To limit tree browsing a bit

	/**
	 * Create a new lock.
	 */
	private static final int LOCK_CREATION = 0;

	/**
	 * Refresh lock.
	 */
	private static final int LOCK_REFRESH = 1;

	/**
	 * Default lock timeout value.
	 */
	private static final int DEFAULT_TIMEOUT = 3600;

	/**
	 * Maximum lock timeout.
	 */
	private static final int MAX_TIMEOUT = 604800;

	/**
	 * Default namespace.
	 */
	private static final String DEFAULT_NAMESPACE = "DAV:";

	/**
	 * Secret information used to generate reasonably secure lock ids.
	 */
	private String secret = "longcon";

	/**
	 * 获取用户请求的文件
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param path
	 *            String
	 * @return ResourceInfo
	 * @throws ServletException
	 */
	protected abstract ResourceInfo getRequestResourceInfo(
			HttpServletRequest req, String path) throws ServletException;

	protected BeanFactory getBeanFactory() {
		return WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
	}

	/**
	 * 初始化，解析并处理输入参数 Initialize this servlet.
	 */
	public void init() throws ServletException {

		// Set our properties from the initialization parameters
		String value = null;
		try {
			value = getServletConfig().getInitParameter("debug");
			debug = Integer.parseInt(value);
		} catch (Throwable t) {
			;
		}
		try {
			value = getServletConfig().getInitParameter("input");
			input = Integer.parseInt(value);
		} catch (Throwable t) {
			;
		}
		try {
			value = getServletConfig().getInitParameter("listings");
			listings = (new Boolean(value)).booleanValue();
		} catch (Throwable t) {
			;
		}
		try {
			value = getServletConfig().getInitParameter("readonly");
			if (value != null)
				readOnly = (new Boolean(value)).booleanValue();
		} catch (Throwable t) {
			;
		}
		try {
			value = getServletConfig().getInitParameter("output");
			output = Integer.parseInt(value);
		} catch (Throwable t) {
			;
		}

		try {
			value = getServletConfig().getInitParameter("secret");
			if (value != null)
				secret = value;
		} catch (Throwable t) {
			;
		}

		// Sanity check on the specified buffer sizes
		if (input < 256)
			input = 256;
		if (output < 256)
			output = 256;

		if (debug > 0) {
			log("DefaultServlet.init:  input buffer size=" + input
					+ ", output buffer size=" + output);
		}

		// Load the MD5 helper used to calculate signatures.
		try {
			md5Helper = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException();
		}

		this.lockManager = new LockManager();

	}

	/**
	 * 获取用户请求的文件
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return File
	 * @throws ServletException
	 */
	protected ResourceInfo getRequestResourceInfo(HttpServletRequest req)
			throws ServletException {
		String path = this.getRelativePath(req);
		return this.getRequestResourceInfo(req, path);
	}

	/**
	 * Show HTTP header information.
	 */
	@SuppressWarnings("unchecked")
	protected void showRequestInfo(HttpServletRequest req) {

		log.debug("Longcon DAV Request Info");

		// Show generic info
		log.debug("Encoding : " + req.getCharacterEncoding());
		log.debug("Length : " + req.getContentLength());
		log.debug("Type : " + req.getContentType());
		StringBuffer debug = new StringBuffer("Parameters");

		Enumeration<String> parameters = req.getParameterNames();

		while (parameters.hasMoreElements()) {
			String paramName = (String) parameters.nextElement();
			String[] values = req.getParameterValues(paramName);
			debug.append(paramName + " : ");
			for (int i = 0; i < values.length; i++) {
				debug.append(values[i] + ", ");
			}
		}
		log.debug(debug);

		log.debug("Protocol : " + req.getProtocol());
		log.debug("Address : " + req.getRemoteAddr());
		log.debug("Host : " + req.getRemoteHost());
		log.debug("Scheme : " + req.getScheme());
		log.debug("Server Name : " + req.getServerName());
		log.debug("Server Port : " + req.getServerPort());
		debug = new StringBuffer("Attributes");

		Enumeration<String> attributes = req.getAttributeNames();

		while (attributes.hasMoreElements()) {
			String attributeName = (String) attributes.nextElement();
			debug.append(attributeName + " : ");
			debug.append(req.getAttribute(attributeName).toString());
		}
		log.debug(debug);
		// Show HTTP info
		log.debug("HTTP Header Info");

		log.debug("Authentication Type : " + req.getAuthType());
		log.debug("HTTP Method : " + req.getMethod());
		log.debug("Path Info : " + req.getPathInfo());
		log.debug("Path translated : " + req.getPathTranslated());
		log.debug("Query string : " + req.getQueryString());
		log.debug("Remote user : " + req.getRemoteUser());
		log.debug("Requested session id : " + req.getRequestedSessionId());
		log.debug("Request URI : " + req.getRequestURI());
		log.debug("Context path : " + req.getContextPath());
		log.debug("Servlet path : " + req.getServletPath());
		log.debug("User principal : " + req.getUserPrincipal());

		debug = new StringBuffer("Headers : ");

		Enumeration<String> headers = req.getHeaderNames();

		while (headers.hasMoreElements()) {
			String headerName = (String) headers.nextElement();
			debug.append(headerName + " : ");
			debug.append(req.getHeader(headerName));
		}
		log.debug(debug);

	}

	/**
	 * 返回用户请求的相对文件路径。 这里使用UTF-8对用户请求路径进行解码。在Windows平台上已测试，在其他平台上可能有兼容性问题。
	 * 
	 * @param request
	 *            The servlet request we are processing
	 */
	private String getRelativePath(HttpServletRequest request) {

		// Are we being processed by a RequestDispatcher.include()?
		if (request.getAttribute("javax.servlet.include.request_uri") != null) {
			String result = (String) request
					.getAttribute("javax.servlet.include.path_info");
			if (result == null)
				result = (String) request
						.getAttribute("javax.servlet.include.servlet_path");
			if ((result == null) || (result.equals("")))
				result = "/";
			return (result);
		}

		String result = null;
		try {
			result = java.net.URLDecoder.decode(request.getRequestURI(),
					"UTF-8");
			String contextPath = java.net.URLDecoder.decode(request
					.getContextPath(), "UTF-8");
			if (contextPath != null)
				result = result.substring(contextPath.length());
		} catch (UnsupportedEncodingException ex) {
			result = request.getRequestURI();
			String contextPath = request.getContextPath();
			if (contextPath != null)
				result = result.substring(contextPath.length());
		}

		return (result);

	}

	/**
	 * Process a GET request for the specified resource.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		doGetResource(request, response, true);

	}

	/**
	 * Process a HEAD request for the specified resource.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// Serve the requested resource, without the data content
		doGetResource(request, response, false);

	}

	/**
	 * Process a POST request for the specified resource.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

	/**
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param resourceInfo
	 *            ResourceInfo
	 * @return boolean
	 * @throws IOException
	 */
	private boolean checkIfHeaders(HttpServletRequest request,
			HttpServletResponse response, ResourceInfo resourceInfo)
			throws IOException {
		if (resourceInfo.getLastModified() != null) {
			return checkIfMatch(request, response, getETag(resourceInfo))
					&& checkIfModifiedSince(request, response, resourceInfo
							.getLastModified().getTime())
					&& checkIfNoneMatch(request, response,
							getETag(resourceInfo))
					&& checkIfUnmodifiedSince(request, response, resourceInfo
							.getLastModified().getTime());
		} else {
			return checkIfMatch(request, response, getETag(resourceInfo))
					&& checkIfNoneMatch(request, response,
							getETag(resourceInfo));
		}
	}

	/**
	 * Return a context-relative path, beginning with a "/", that represents the
	 * canonical version of the specified path after ".." and "." elements are
	 * resolved out. If the specified path attempts to go outside the boundaries
	 * of the current context (i.e. too many ".." path elements are present),
	 * return <code>null</code> instead.
	 * 
	 * @param path
	 *            Path to be normalized
	 */
	private static String normalize(String path) {

		if (path == null)
			return null;

		// Create a place for the normalized path
		String normalized = path;

		if (normalized == null)
			return (null);

		if (normalized.equals("/."))
			return "/";

		// Normalize the slashes and add leading slash if necessary
		if (normalized.indexOf('\\') >= 0)
			normalized = normalized.replace('\\', '/');
		if (!normalized.startsWith("/"))
			normalized = "/" + normalized;

		// Resolve occurrences of "//" in the normalized path
		while (true) {
			int index = normalized.indexOf("//");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 1);
		}

		// Resolve occurrences of "/./" in the normalized path
		while (true) {
			int index = normalized.indexOf("/./");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 2);
		}

		// Resolve occurrences of "/../" in the normalized path
		while (true) {
			int index = normalized.indexOf("/../");
			if (index < 0)
				break;
			if (index == 0)
				return (null); // Trying to go outside our context
			int index2 = normalized.lastIndexOf('/', index - 1);
			normalized = normalized.substring(0, index2)
					+ normalized.substring(index + 3);
		}

		// Return the normalized path that we have completed
		return (normalized);

	}

	/**
	 * URL rewriter.
	 * 
	 * @param path
	 *            Path which has to be rewiten
	 */
	private static String rewriteUrl(String path) {
		return urlEncoder.encode(path);
	}

	/**
	 * Serve the specified resource, optionally including the data content.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param content
	 *            Should the content be included?
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
	private void doGetResource(HttpServletRequest request,
			HttpServletResponse response, boolean content) throws IOException,
			ServletException {

		// Identify the requested resource path
		ResourceInfo resource = this.getRequestResourceInfo(request);
		if (resource == null) {
			log.error("Unable to find resource for " + request.getRequestURI());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (!resource.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request
					.getRequestURI());
			return;
		}
		String path = resource.getPath();
		// If the resource is not a collection, and the resource path
		// ends with "/" or "\", return NOT FOUND
		if (!resource.isDirectory()) {
			if (path.endsWith("/") || (path.endsWith("\\"))) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, request
						.getRequestURI());
				return;
			}
		}

		if (resource.isDirectory()) {
			if (path.endsWith("/") || (path.endsWith("\\"))) {
				path = path + "/";
			}
			request.setAttribute("path", path);
			this.doGetDirectory(request, response, resource, content);
		} else
			this.doGetFile(request, response, resource, content);

	}

	/**
	 * 获取文件
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param content
	 *            boolean
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doGetFile(HttpServletRequest request,
			HttpServletResponse response, ResourceInfo requestFile,
			boolean content) throws IOException, ServletException {

		// Checking If headers
		boolean included = (request
				.getAttribute(Globals.INCLUDE_CONTEXT_PATH_ATTR) != null);
		if (!included && !checkIfHeaders(request, response, requestFile)) {
			return;
		}

		// Find content type.
		String contentType =
		// modified by mali
		getServletContext().getMimeType(requestFile.getName().toLowerCase());

		Vector<Range> ranges = parseRange(request, response, requestFile);

		// ETag header
		response.setHeader("ETag", getETag(requestFile));

		// Last-Modified header
		if (requestFile.getLastModified() != null) {
			response.setHeader("Last-Modified", toHttpDate(requestFile
					.getLastModified().getTime()));
		}
		// String fileName =
		// java.net.URLEncoder.encode(requestFile.getDisplayName(),"UTF-8");
		// response.addHeader("Content-Disposition", "inline;filename=\"" +
		// fileName + "\";");
		ServletOutputStream ostream = null;
		PrintWriter writer = null;
		try {
			if (content) {
				// Trying to retrieve the servlet output stream
				try {
					ostream = response.getOutputStream();
				} catch (IllegalStateException e) {
					// If it fails, we try to get a Writer instead if we're
					// trying to serve a text file
					if ((contentType == null)
							|| (contentType.startsWith("text"))) {
						writer = response.getWriter();
					} else {
						throw e;
					}
				}
				if (((ranges == null) || (ranges.isEmpty()))
						&& (request.getHeader("Range") == null)) {
					// Set the appropriate output headers
					if (contentType != null) {
						response.setContentType(contentType);
					}
					long size = 0;
					if (content) {
						try {
							response.setBufferSize(output);
						} catch (IllegalStateException e) {
							// Silent catch
						}
						if (ostream != null) {
							InputStream is = null;
							try {
								is = requestFile.read();
								if (is != null)
									size = FileUtils.copy(is, ostream);
							} catch (Exception ex) {
								this.log("Error in render output file :"
										+ ex.getMessage());
								return;
							} finally {
								if (is != null)
									is.close();
								if (ostream != null)
									ostream.close();
								ostream = null;
							}
						} else {
							InputStream is = null;
							try {
								is = requestFile.read();
								size = FileUtils.copy(
										new InputStreamReader(is), writer);
							} catch (Exception ex) {
								this.log("Error in render output file :"
										+ ex.getMessage());
								return;
							} finally {
								if (is != null)
									is.close();
								if (ostream != null)
									ostream.close();
								ostream = null;
							}
						}
					}
					if ((!requestFile.isDirectory()) && (size >= 0)) {
						response.setContentLength((int) size);
					}
				} else {

					if ((ranges == null) || (ranges.isEmpty()))
						return;

					// Partial content response.

					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

					if (ranges.size() == 1) {

						Range range = (Range) ranges.elementAt(0);
						response.addHeader("Content-Range", "bytes "
								+ range.getStart() + "-" + range.getEnd() + "/"
								+ range.getLength());
						response.setContentLength((int) (range.getEnd()
								- range.getStart() + 1));

						if (contentType != null) {
							response.setContentType(contentType);
						}

						if (content) {
							try {
								response.setBufferSize(output);
							} catch (IllegalStateException e) {
								// Silent catch
							}
							if (ostream != null) {
								InputStream is = null;
								try {
									is = requestFile.read();
									FileUtils.copy(is, ostream, range
											.getStart(), range.getEnd());
								} catch (Exception ex) {
									this.log("Error in render output file :"
											+ ex.getMessage());
									return;
								} finally {
									if (is != null)
										is.close();
								}

							} else {
								InputStream is = null;
								try {
									is = requestFile.read();
									FileUtils.copy(new InputStreamReader(is),
											writer, range.getStart(), range
													.getEnd());
								} finally {
									if (is != null)
										is.close();
								}
							}
						}

					} else {
						response
								.setContentType("multipart/byteranges; boundary="
										+ mimeSeparation);
						if (content) {
							try {
								response.setBufferSize(output);
							} catch (IllegalStateException e) {
								// Silent catch
							}
							if (ostream != null) {
								copy(requestFile, ostream, ranges.elements(),
										contentType);
							} else {
								copy(requestFile, writer, ranges.elements(),
										contentType);
							}
						}
					}
				}
			}
		} finally {
			if (writer != null)
				writer.close();
			if (ostream != null)
				ostream.close();
		}

	}

	/**
	 * 获取目录
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param content
	 *            boolean
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doGetDirectory(HttpServletRequest request,
			HttpServletResponse response, ResourceInfo requestFile,
			boolean content) throws IOException, ServletException {
		// Skip directory listings if we have been configured to
		// suppress them
		if (!listings) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request
					.getRequestURI());
			return;
		}
		if (content) {
			request.setAttribute("folder", requestFile);
			request.getRequestDispatcher("/directory.jsp").forward(request,
					response);
		}
	}

	/**
	 * Parse the content-range header.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @return Range
	 */
	private Range parseContentRange(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// Retrieving the content-range header (if any is specified
		String rangeHeader = request.getHeader("Content-Range");

		if (rangeHeader == null)
			return null;

		// bytes is the only range unit supported
		if (!rangeHeader.startsWith("bytes")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		rangeHeader = rangeHeader.substring(6).trim();

		int dashPos = rangeHeader.indexOf('-');
		int slashPos = rangeHeader.indexOf('/');

		if (dashPos == -1) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		if (slashPos == -1) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		Range range = new Range();

		try {
			range.setStart(Long.parseLong(rangeHeader.substring(0, dashPos)));
			range.setEnd(Long.parseLong(rangeHeader.substring(dashPos + 1,
					slashPos)));
			range.setLength(Long.parseLong(rangeHeader.substring(slashPos + 1,
					rangeHeader.length())));
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		if (!range.validate()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		return range;

	}

	/**
	 * Parse the range header.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @return Vector of ranges
	 */
	private Vector<Range> parseRange(HttpServletRequest request,
			HttpServletResponse response, ResourceInfo requestFile)
			throws IOException {

		// Checking If-Range
		String headerValue = request.getHeader("If-Range");

		if (headerValue != null) {

			long headerValueTime = (-1L);
			try {
				headerValueTime = request.getDateHeader("If-Range");
			} catch (Exception e) {
				;
			}

			String eTag = getETag(requestFile);
			long lastModified = requestFile.getLastModified().getTime();

			if (headerValueTime == (-1L)) {

				// If the ETag the client gave does not match the entity
				// etag, then the entire entity is returned.
				if (!eTag.equals(headerValue.trim()))
					return null;

			} else {

				// If the timestamp of the entity the client got is older than
				// the last modification date of the entity, the entire entity
				// is returned.
				if (lastModified > (headerValueTime + 1000))
					return null;

			}

		}

		long fileLength = requestFile.getLength();

		if (fileLength == 0)
			return null;

		// Retrieving the range header (if any is specified
		String rangeHeader = request.getHeader("Range");

		if (rangeHeader == null)
			return null;
		// bytes is the only range unit supported (and I don't see the point
		// of adding new ones).
		if (!rangeHeader.startsWith("bytes")) {
			response.addHeader("Content-Range", "bytes */" + fileLength);
			response
					.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			return null;
		}

		rangeHeader = rangeHeader.substring(6);

		// Vector which will contain all the ranges which are successfully
		// parsed.
		Vector<Range> result = new Vector<Range>();
		StringTokenizer commaTokenizer = new StringTokenizer(rangeHeader, ",");

		// Parsing the range list
		while (commaTokenizer.hasMoreTokens()) {
			String rangeDefinition = commaTokenizer.nextToken().trim();

			Range currentRange = new Range();
			currentRange.setLength(fileLength);

			int dashPos = rangeDefinition.indexOf('-');

			if (dashPos == -1) {
				response.addHeader("Content-Range", "bytes */" + fileLength);
				response
						.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return null;
			}

			if (dashPos == 0) {

				try {
					long offset = Long.parseLong(rangeDefinition);
					currentRange.setStart(fileLength + offset);
					currentRange.setEnd(fileLength - 1);
				} catch (NumberFormatException e) {
					response
							.addHeader("Content-Range", "bytes */" + fileLength);
					response
							.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
					return null;
				}

			} else {

				try {
					currentRange.setStart(Long.parseLong(rangeDefinition
							.substring(0, dashPos)));
					if (dashPos < rangeDefinition.length() - 1)
						currentRange.setEnd(Long.parseLong(rangeDefinition
								.substring(dashPos + 1, rangeDefinition
										.length())));
					else
						currentRange.setEnd(fileLength - 1);
				} catch (NumberFormatException e) {
					response
							.addHeader("Content-Range", "bytes */" + fileLength);
					response
							.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
					return null;
				}

			}

			if (!currentRange.validate()) {
				response.addHeader("Content-Range", "bytes */" + fileLength);
				response
						.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return null;
			}

			result.addElement(currentRange);
		}

		return result;
	}

	/**
	 * Check if the if-match condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param resourceInfo
	 *            File object
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	private boolean checkIfMatch(HttpServletRequest request,
			HttpServletResponse response, String eTag) throws IOException {

		String headerValue = request.getHeader("If-Match");
		if (headerValue != null) {
			if (headerValue.indexOf('*') == -1) {

				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");
				boolean conditionSatisfied = false;

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(eTag))
						conditionSatisfied = true;
				}

				// If none of the given ETags match, 412 Precodition failed is
				// sent back
				if (!conditionSatisfied) {
					response
							.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
					return false;
				}

			}
		}
		return true;

	}

	/**
	 * Check if the if-modified-since condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param resourceInfo
	 *            File object
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	private boolean checkIfModifiedSince(HttpServletRequest request,
			HttpServletResponse response, long lastModified) throws IOException {
		try {
			long headerValue = request.getDateHeader("If-Modified-Since");
			// long lastModified = resourceInfo.lastModified();
			if (headerValue != -1) {

				// If an If-None-Match header has been specified, if modified
				// since
				// is ignored.
				if ((request.getHeader("If-None-Match") == null)
						&& (lastModified <= headerValue + 1000)) {
					// The entity has not been modified since the date
					// specified by the client. This is not an error case.
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return false;
				}
			}
		} catch (IllegalArgumentException illegalArgument) {
			return true;
		}
		return true;

	}

	/**
	 * Check if the if-none-match condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param resourceInfo
	 *            File object
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	private boolean checkIfNoneMatch(HttpServletRequest request,
			HttpServletResponse response, String eTag) throws IOException {

		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {

			boolean conditionSatisfied = false;

			if (!headerValue.equals("*")) {

				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(eTag))
						conditionSatisfied = true;
				}

			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {

				// For GET and HEAD, we should respond with
				// 304 Not Modified.
				// For every other method, 412 Precondition Failed is sent
				// back.
				if (("GET".equals(request.getMethod()))
						|| ("HEAD".equals(request.getMethod()))) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return false;
				} else {
					response
							.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * Check if the if-unmodified-since condition is satisfied.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param resourceInfo
	 *            File object
	 * @return boolean true if the resource meets the specified condition, and
	 *         false if the condition is not satisfied, in which case request
	 *         processing is stopped
	 */
	private boolean checkIfUnmodifiedSince(HttpServletRequest request,
			HttpServletResponse response, long lastModified) throws IOException {
		try {
			// long lastModified = resourceInfo.lastModified();
			long headerValue = request.getDateHeader("If-Unmodified-Since");
			if (headerValue != -1) {
				if (lastModified > (headerValue + 1000)) {
					// The entity has not been modified since the date
					// specified by the client. This is not an error case.
					response
							.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
					return false;
				}
			}
		} catch (IllegalArgumentException illegalArgument) {
			return true;
		}
		return true;

	}

	/**
	 * 复制文件到输出流中
	 * 
	 * @param file
	 *            File
	 * @param ostream
	 *            ServletOutputStream
	 * @param ranges
	 *            Enumeration
	 * @param contentType
	 *            String
	 * @throws IOException
	 */
	private void copy(ResourceInfo file, ServletOutputStream ostream,
			Enumeration<Range> ranges, String contentType) throws IOException {
		while (ranges.hasMoreElements()) {
			Range currentRange = (Range) ranges.nextElement();

			// Writing MIME header.
			ostream.println();
			ostream.println("--" + mimeSeparation);
			if (contentType != null)
				ostream.println("Content-Type: " + contentType);
			ostream.println("Content-Range: bytes " + currentRange.getStart()
					+ "-" + currentRange.getEnd() + "/"
					+ currentRange.getLength());
			ostream.println();
			InputStream is = null;
			try {
				is = file.read();
				FileUtils.copy(is, ostream, currentRange.getStart(),
						currentRange.getEnd());
			} finally {
				if (is != null)
					is.close();
			}
		}
		ostream.println();
		ostream.print("--" + mimeSeparation + "--");
		ostream.close();

	}

	/**
	 * Copy the contents of the specified input stream to the specified output
	 * stream, and ensure that both streams are closed before returning (even in
	 * the face of an exception).
	 * 
	 * @param resourceInfo
	 *            The ResourceInfo object
	 * @param writer
	 *            The writer to write to
	 * @param ranges
	 *            Enumeration of the ranges the client wanted to retrieve
	 * @param contentType
	 *            Content type of the resource
	 * @exception IOException
	 *                if an input/output error occurs
	 */
	private void copy(ResourceInfo file, PrintWriter writer,
			Enumeration<Range> ranges, String contentType) throws IOException {
		try {
			while (ranges.hasMoreElements()) {

				Range currentRange = (Range) ranges.nextElement();

				// Writing MIME header.
				writer.println();
				writer.println("--" + mimeSeparation);
				if (contentType != null)
					writer.println("Content-Type: " + contentType);
				writer.println("Content-Range: bytes "
						+ currentRange.getStart() + "-" + currentRange.getEnd()
						+ "/" + currentRange.getLength());
				writer.println();
				InputStream is = null;
				try {
					is = file.read();
					FileUtils.copy(new InputStreamReader(is), writer,
							currentRange.getStart(), currentRange.getEnd());
				} finally {
					if (is != null)
						is.close();
				}
			}

			writer.println();
			writer.print("--" + mimeSeparation + "--");
			writer.close();
		} finally {
			if (writer != null)
				writer.close();
		}

	}

	// ------------------------------------------------------ Protected Methods

	/**
	 * Handles the special WebDAV methods.
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String method = req.getMethod();
		log.debug(method + ":" + req.getRequestURI());
		// this.showRequestInfo(req);
		try {
			if (method.equals(METHOD_PROPFIND)) {
				doPropfind(req, resp);
			} else if (method.equals(METHOD_PROPPATCH)) {
				doProppatch(req, resp);
			} else if (method.equals(METHOD_MKCOL)) {
				doMkcol(req, resp);
			} else if (method.equals(METHOD_COPY)) {
				doCopy(req, resp);
			} else if (method.equals(METHOD_MOVE)) {
				doMove(req, resp);
			} else if (method.equals(METHOD_LOCK)) {
				doLock(req, resp);
			} else if (method.equals(METHOD_UNLOCK)) {
				doUnlock(req, resp);
			} else {
				// DefaultServlet processing
				super.service(req, resp);
			}
		} catch (ServletException ex) {
			log.error(ex.getMessage(), ex);
			throw ex;

		} catch (IOException ex2) {
			log.error(ex2.getMessage(), ex2);
			throw ex2;
		} catch (RuntimeException ex3) {
			log.error(ex3.getMessage(), ex3);
			throw ex3;
		}
	}

	/**
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.addHeader("DAV", "1,2");

		StringBuffer methodsAllowed = determineMethodsAllowed(this
				.getRequestResourceInfo(req));

		resp.addHeader("Allow", methodsAllowed.toString());
		resp.addHeader("MS-Author-Via", "DAV");

	}

	/**
	 * PROPFIND Method.
	 */
	private void doPropfind(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// resp.setStatus(WebdavStatus.SC_MULTI_STATUS);
		// resp.setContentType("text/xml; charset=UTF-8");
		// req.getRequestDispatcher("/common/webdav.jsp").include(req,resp);

		ResourceInfo resource = this.getRequestResourceInfo(req);
		if (resource == null) {
			log.error("resource:" + req.getRequestURI() + " not found.");
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String path = resource.getPath();
		if (!listings) {

			// Get allowed methods
			StringBuffer methodsAllowed = determineMethodsAllowed(resource);

			resp.addHeader("Allow", methodsAllowed.toString());
			resp.sendError(WebdavStatus.SC_METHOD_NOT_ALLOWED);
			return;
		}

		if (path.endsWith("/"))
			path = path.substring(0, path.length() - 1);

		if ((path.toUpperCase().startsWith("/WEB-INF"))
				|| (path.toUpperCase().startsWith("/META-INF"))) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		// Properties which are to be displayed.

		// Propfind depth
		int depth = this.parseLockDepth(req);

		PropfindParser parser = null;
		try {
			parser = new PropfindParser(req.getInputStream());
		} catch (ParserConfigurationException ex) {
			log.error(ex.getMessage(), ex);
			resp.sendError(WebdavStatus.SC_BAD_REQUEST);
			return;
		} catch (SAXException ex) {
			log.error(ex.getMessage(), ex);
			resp.sendError(WebdavStatus.SC_BAD_REQUEST);
			return;
		}
		int type = parser.getType();
		Collection<?> properties = parser.getRequestProperties();

		log.debug(req.getMethod() + ":type=" + type + ";depth=" + depth
				+ ";path=" + path);
		boolean exists = resource.exists();
		if (!exists) {
			if (this.lockManager.isLockNullPath(path)) {
				resp.setStatus(WebdavStatus.SC_MULTI_STATUS);
				resp.setContentType("text/xml; charset=UTF-8");
				XMLWriter generatedXML = new XMLWriter(resp.getWriter());
				generatedXML.writeXMLHeader();

				generatedXML.writeElement(null, "multistatus"
						+ generateNamespaceDeclarations(), XMLWriter.OPENING);
				this.parseLockNullProperties(req, generatedXML, resource, type,
						properties);
				generatedXML.writeElement(null, "multistatus",
						XMLWriter.CLOSING);

				generatedXML.sendData();

			} else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, path);

		} else {

			resp.setStatus(WebdavStatus.SC_MULTI_STATUS);
			resp.setContentType("text/xml; charset=UTF-8");

			// Create multistatus object
			XMLWriter generatedXML = new XMLWriter(resp.getWriter());
			generatedXML.writeXMLHeader();

			generatedXML.writeElement(null, "multistatus"
					+ generateNamespaceDeclarations(), XMLWriter.OPENING);
			if (depth == 0)
				parseProperties(req, resource, generatedXML, type, properties);
			else {
				for (Iterator<ResourceInfo> iterator = resource.getChildren(); iterator
						.hasNext();) {
					ResourceInfo child = (ResourceInfo) iterator.next();
					parseProperties(req, child, generatedXML, type, properties);
					if (child.isDirectory()) {
						Enumeration<?> lockNullResourcesList = this.lockManager
								.getLockNullResources(child.getPath());
						while (lockNullResourcesList.hasMoreElements()) {
							String lockNullPath = (String) lockNullResourcesList
									.nextElement();
							ResourceInfo lockNullResource = this
									.getRequestResourceInfo(req, lockNullPath);
							parseLockNullProperties(req, generatedXML,
									lockNullResource, type, properties);
						}

					}
				}
				parseProperties(req, resource, generatedXML, type, properties);

			}
			generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);
			// log.trace(generatedXML.toString());
			generatedXML.sendData();
		}

	}

	/**
	 * PROPPATCH Method.
	 */
	private void doProppatch(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		if (isLocked(req)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return;
		}

		resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);

	}

	/**
	 * MKCOL Method.
	 */
	private void doMkcol(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		if (isLocked(req)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return;
		}

		String path = getRelativePath(req);

		if ((path.toUpperCase().startsWith("/WEB-INF"))
				|| (path.toUpperCase().startsWith("/META-INF"))) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		ResourceInfo file = this.getRequestResourceInfo(req);
		boolean exists = file.exists();

		// Can't create a collection if a resource already exists at the given
		// path
		if (exists) {
			// Get allowed methods
			StringBuffer methodsAllowed = determineMethodsAllowed(file);

			resp.addHeader("Allow", methodsAllowed.toString());

			resp.sendError(WebdavStatus.SC_METHOD_NOT_ALLOWED);
			return;
		}
		/*
		 * if (req.getInputStream().available() > 0) { DocumentBuilder
		 * documentBuilder = getDocumentBuilder(); try { Document document =
		 * documentBuilder.parse (new InputSource(req.getInputStream())); //
		 * TODO : Process this request body
		 * resp.sendError(WebdavStatus.SC_NOT_IMPLEMENTED); return;
		 * 
		 * } catch (SAXException saxe) { // Parse error - assume invalid content
		 * resp.sendError(WebdavStatus.SC_BAD_REQUEST); return; } }
		 */

		boolean result = file.mkdirs();

		if (!result) {
			resp.sendError(WebdavStatus.SC_CONFLICT, WebdavStatus
					.getStatusText(WebdavStatus.SC_CONFLICT));
		} else {
			resp.setStatus(WebdavStatus.SC_CREATED);
			// Removing any lock-null resource which would be present
			this.lockManager.removeLock(path);
		}

	}

	/**
	 * DELETE Method.
	 */
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		ResourceInfo requestFile = this.getRequestResourceInfo(req);

		deleteResource(requestFile, req, resp);

	}

	/**
	 * Process a POST request for the specified resource.
	 * 
	 * @param req
	 *            The servlet request we are processing
	 * @param resp
	 *            The servlet response we are creating
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (isLocked(req)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return;
		}

		if (readOnly) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		ResourceInfo resource = this.getRequestResourceInfo(req);

		boolean exists = resource.exists();

		Range range = parseContentRange(req, resp);

		// Append data specified in ranges to existing content for this
		// resource - create a temp. file on the local filesystem to
		// perform this operation
		// Assume just one range is specified for now
		if (range != null)
			resource.write(req.getInputStream(), range.getStart(), range
					.getLength());

		else
			resource.write(req.getInputStream());

		resource.setContentType(this.getServletContext().getMimeType(
				resource.getPath()));

		if (req.getUserPrincipal() != null)
			resource.setCreator(req.getUserPrincipal());

		if (exists) {
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {
			resp.setStatus(HttpServletResponse.SC_CREATED);
		}

		String path = getRelativePath(req);

		// Removing any lock-null resource which would be present
		this.lockManager.removeLock(path);

	}

	/**
	 * COPY Method.
	 */
	private void doCopy(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		ResourceInfo resource = this.getRequestResourceInfo(req);

		copyResource(resource, req, resp);
		// Removing any lock-null resource which would be present at
		// the destination path
		this.lockManager.removeLock(resource.getPath());
	}

	/**
	 * MOVE Method.
	 */
	private void doMove(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		if (isLocked(req)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return;
		}

		ResourceInfo resource = this.getRequestResourceInfo(req);

		moveResource(resource, req, resp);

	}

	/**
	 * 获取lockDuration
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @return int
	 */
	private int parseLockDuration(HttpServletRequest req) {
		int lockDuration = DEFAULT_TIMEOUT;
		String lockDurationStr = req.getHeader("Timeout");
		if (lockDurationStr == null) {
			lockDuration = DEFAULT_TIMEOUT;
		} else {
			int commaPos = lockDurationStr.indexOf(",");
			// If multiple timeouts, just use the first
			if (commaPos != -1) {
				lockDurationStr = lockDurationStr.substring(0, commaPos);
			}
			if (lockDurationStr.startsWith("Second-")) {
				lockDuration = (new Integer(lockDurationStr.substring(7)))
						.intValue();
			} else {
				if (lockDurationStr.equalsIgnoreCase("infinity")) {
					lockDuration = MAX_TIMEOUT;
				} else {
					try {
						lockDuration = (new Integer(lockDurationStr))
								.intValue();
					} catch (NumberFormatException e) {
						lockDuration = MAX_TIMEOUT;
					}
				}
			}
			if (lockDuration == 0) {
				lockDuration = DEFAULT_TIMEOUT;
			}
			if (lockDuration > MAX_TIMEOUT) {
				lockDuration = MAX_TIMEOUT;
			}
		}
		return lockDuration;

	}

	private int parseLockDepth(HttpServletRequest req) {
		int depth = INFINITY;
		String depthStr = req.getHeader("Depth");

		if (depthStr == null) {
			depth = INFINITY;
		} else {
			if (depthStr.equals("0")) {
				depth = 0;
			} else {
				depth = INFINITY;
			}
		}
		return depth;

	}

	private String getLockToken(HttpServletRequest req, LockInfo lock) {
		String lockTokenStr = req.getServletPath() + "-" + lock.type + "-"
				+ lock.scope + "-" + req.getUserPrincipal() + "-" + lock.depth
				+ "-" + lock.owner + "-" + lock.tokens + "-" + lock.expiresAt
				+ "-" + System.currentTimeMillis() + "-" + secret;
		return md5Encoder.encode(md5Helper.digest(lockTokenStr.getBytes()));

	}

	/**
	 * LOCK Method.
	 */
	private void doLock(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		if (isLocked(req)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return;
		}

		LockInfo lock = new LockInfo();

		lock.depth = this.parseLockDepth(req);

		// Parsing timeout header

		int lockDuration = this.parseLockDuration(req);
		lock.expiresAt = System.currentTimeMillis() + (lockDuration * 1000);

		LockInfoParser parser;
		try {
			parser = new LockInfoParser(req.getInputStream());
		} catch (ParserConfigurationException ex) {
			resp.sendError(WebdavStatus.SC_BAD_REQUEST);
			return;
		} catch (SAXException ex) {
			resp.sendError(WebdavStatus.SC_BAD_REQUEST);
			return;
		}
		lock.owner = parser.getLockOwner();
		lock.type = parser.getLockType();
		lock.scope = parser.getLockScope();
		int lockRequestType = parser.getLockRequestType();

		String path = getRelativePath(req);
		ResourceInfo requestFile = this.getRequestResourceInfo(req);
		lock.path = path;

		boolean exists = requestFile.exists();

		// Enumeration locksList = null;

		if (lockRequestType == LOCK_CREATION) {

			// Generating lock id

			String lockToken = this.getLockToken(req, lock);

			if ((exists) && (requestFile.isDirectory())
					&& (lock.depth == INFINITY)) {

				// Locking a collection (and all its member resources)

				// Checking if a child resource of this collection is
				// already locked
				Collection<?> lockPaths = this.lockManager.lockCollection(lock);

				if (!lockPaths.isEmpty()) {

					// 如果文件夹中有文件被锁定，则发送被锁定错误
					// We generate a multistatus error report

					Iterator<?> lockPathsList = lockPaths.iterator();

					resp.setStatus(WebdavStatus.SC_CONFLICT);

					XMLWriter generatedXML = new XMLWriter();
					generatedXML.writeXMLHeader();

					generatedXML.writeElement(null, "multistatus"
							+ generateNamespaceDeclarations(),
							XMLWriter.OPENING);

					while (lockPathsList.hasNext()) {
						generatedXML.writeElement(null, "response",
								XMLWriter.OPENING);
						generatedXML.writeElement(null, "href",
								XMLWriter.OPENING);
						generatedXML.writeText(this.getAbsoluteURL(req,
								(String) lockPathsList.next()));
						generatedXML.writeElement(null, "href",
								XMLWriter.CLOSING);
						generatedXML.writeElement(null, "status",
								XMLWriter.OPENING);
						generatedXML.writeText("HTTP/1.1 "
								+ WebdavStatus.SC_LOCKED
								+ " "
								+ WebdavStatus
										.getStatusText(WebdavStatus.SC_LOCKED));
						generatedXML.writeElement(null, "status",
								XMLWriter.CLOSING);

						generatedXML.writeElement(null, "response",
								XMLWriter.CLOSING);
					}

					generatedXML.writeElement(null, "multistatus",
							XMLWriter.CLOSING);

					Writer writer = resp.getWriter();
					writer.write(generatedXML.toString());
					writer.close();

					return;

				}

				boolean addLock = true;

				LockInfo currentLock = this.lockManager
						.getSharedLock(lock.path);
				if (currentLock != null) {

					if (currentLock.isExclusive()) {
						resp.sendError(WebdavStatus.SC_LOCKED);
						return;
					} else {
						if (lock.isExclusive()) {
							resp.sendError(WebdavStatus.SC_LOCKED);
							return;
						}
					}

					currentLock.tokens.addElement(lockToken);
					lock = currentLock;
					addLock = false;

				}

				if (addLock) {
					lock.tokens.addElement(lockToken);
					this.lockManager.addCollectionLock(lock);
				}

			} else {

				// Locking a single resource

				// Retrieving an already existing lock on that resource
				LockInfo presentLock = (LockInfo) this.lockManager
						.getResourceLock(lock.path);
				if (presentLock != null) {

					if ((presentLock.isExclusive()) || (lock.isExclusive())) {
						// If either lock is exclusive, the lock can't be
						// granted
						resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
						return;
					} else {
						presentLock.tokens.addElement(lockToken);
						lock = presentLock;
					}

				} else {

					lock.tokens.addElement(lockToken);
					this.lockManager.addResourceLock(lock);

					// Checking if a resource exists at this path
					exists = requestFile.exists();
					if (!exists) {

						this.lockManager.createLockNULL(lock.path);

					}
					// Add the Lock-Token header as by RFC 2518 8.10.1
					// - only do this for newly created locks
					resp.addHeader("Lock-Token", "<opaquelocktoken:"
							+ lockToken + ">");
				}

			}

		}

		if (lockRequestType == LOCK_REFRESH) {

			String ifHeader = req.getHeader("If");
			if (ifHeader == null)
				ifHeader = "";

			// Checking resource locks

			if (lock != null) {
				LockInfo toRenew = this.lockManager.getResourceLock(path);
				if (toRenew.checkIfHeader(ifHeader)) {
					toRenew.expiresAt = lock.expiresAt;
					lock = toRenew;
				}
			}

			// Checking inheritable collection locks
			LockInfo toRenew = this.lockManager.checkCollectionLock(path,
					ifHeader);
			if (toRenew != null) {
				toRenew.expiresAt = lock.expiresAt;
				lock = toRenew;
			}
		}

		// Set the status, then generate the XML response containing
		// the lock information
		XMLWriter generatedXML = new XMLWriter();
		generatedXML.writeXMLHeader();

		generatedXML.writeElement(null, "prop"
				+ generateNamespaceDeclarations(), XMLWriter.OPENING);

		generatedXML.writeElement(null, "lockdiscovery", XMLWriter.OPENING);

		lock.toXML(generatedXML);

		generatedXML.writeElement(null, "lockdiscovery", XMLWriter.CLOSING);

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);

		resp.setStatus(WebdavStatus.SC_OK);

		resp.setContentType("text/xml; charset=UTF-8");

		Writer writer = resp.getWriter();
		writer.write(generatedXML.toString());

		writer.close();

	}

	/**
	 * UNLOCK Method.
	 */
	private void doUnlock(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (readOnly) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return;
		}

		if (isLocked(req)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return;
		}

		String path = getRelativePath(req);

		String lockTokenHeader = req.getHeader("Lock-Token");
		if (lockTokenHeader == null)
			lockTokenHeader = "";

		// Checking resource locks

		this.lockManager.removeLock(path, lockTokenHeader);

		// Checking inheritable collection locks

		resp.setStatus(WebdavStatus.SC_NO_CONTENT);

	}

	// -------------------------------------------------------- Private Methods

	/**
	 * Generate the namespace declarations.
	 */
	private String generateNamespaceDeclarations() {
		return " xmlns=\"" + DEFAULT_NAMESPACE + "\"";
	}

	/**
	 * Check to see if a resource is currently write locked. The method will
	 * look at the "If" header to make sure the client has give the appropriate
	 * lock tokens.
	 * 
	 * @param req
	 *            Servlet request
	 * @return boolean true if the resource is locked (and no appropriate lock
	 *         token has been found for at least one of the non-shared locks
	 *         which are present on the resource).
	 */
	private boolean isLocked(HttpServletRequest req) {

		String path = getRelativePath(req);

		String ifHeader = req.getHeader("If");
		if (ifHeader == null)
			ifHeader = "";

		String lockTokenHeader = req.getHeader("Lock-Token");
		if (lockTokenHeader == null)
			lockTokenHeader = "";

		return this.lockManager.isLocked(path, ifHeader + lockTokenHeader);

	}

	/**
	 * Copy a resource.
	 * 
	 * @param req
	 *            Servlet request
	 * @param resp
	 *            Servlet response
	 * @return boolean true if the copy is successful
	 */
	private boolean copyResource(ResourceInfo srcResource,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Parsing destination header
		ResourceInfo destResource = this.parseDestinationResource(req);
		if (destResource == null) {
			resp.sendError(WebdavStatus.SC_BAD_REQUEST);
			return false;
		}
		if (destResource.isReserved()) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		if (srcResource.isReserved()) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		if (destResource.equals(srcResource)) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		// Parsing overwrite header

		boolean overwrite = this.parseOverwrite(req);

		boolean exists = destResource.exists();

		if (overwrite) {

			// Delete destination resource, if it exists
			if (exists) {
				if (deleteResource(destResource, req, resp)) {
					resp.setStatus(WebdavStatus.SC_NO_CONTENT);
					return false;
				}
			} else {
				resp.setStatus(WebdavStatus.SC_CREATED);
			}

		} else {

			// If the destination exists, then it's a conflict
			if (exists) {
				resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
				return false;
			}

		}
		destResource.copy(srcResource);

		return true;

	}

	/**
	 * Copy a resource.
	 * 
	 * @param req
	 *            Servlet request
	 * @param resp
	 *            Servlet response
	 * @return boolean true if the copy is successful
	 */
	private boolean moveResource(ResourceInfo srcResource,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Parsing destination header
		ResourceInfo destResource = this.parseDestinationResource(req);
		if (destResource == null) {
			resp.sendError(WebdavStatus.SC_BAD_REQUEST);
			return false;
		}
		if (destResource.isReserved()) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		if (srcResource.isReserved()) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		if (destResource.equals(srcResource)) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		// Parsing overwrite header

		boolean overwrite = this.parseOverwrite(req);

		boolean exists = destResource.exists();

		if (overwrite) {

			// Delete destination resource, if it exists
			if (exists) {
				if (deleteResource(destResource, req, resp)) {
					resp.setStatus(WebdavStatus.SC_NO_CONTENT);
					return false;
				}
			} else {
				resp.setStatus(WebdavStatus.SC_CREATED);
			}

		} else {

			// If the destination exists, then it's a conflict
			if (exists) {
				resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
				return false;
			}

		}
		srcResource.move(destResource);

		return true;

	}

	/**
	 * 获取复制的目标路径,相对于context的路径
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 * @throws ServletException
	 */
	private ResourceInfo parseDestinationResource(HttpServletRequest req)
			throws ServletException {
		String destinationPath = req.getHeader("Destination");

		if (destinationPath == null) {
			return null;
		}

		// Remove url encoding from destination
		destinationPath = RequestUtil.URLDecode(destinationPath, "UTF8");

		int protocolIndex = destinationPath.indexOf("://");
		if (protocolIndex >= 0) {
			// if the Destination URL contains the protocol, we can safely
			// trim everything upto the first "/" character after "://"
			int firstSeparator = destinationPath
					.indexOf("/", protocolIndex + 4);
			if (firstSeparator < 0) {
				destinationPath = "/";
			} else {
				destinationPath = destinationPath.substring(firstSeparator);
			}
		} else {
			String hostName = req.getServerName();
			if ((hostName != null) && (destinationPath.startsWith(hostName))) {
				destinationPath = destinationPath.substring(hostName.length());
			}

			int portIndex = destinationPath.indexOf(":");
			if (portIndex >= 0) {
				destinationPath = destinationPath.substring(portIndex);
			}

			if (destinationPath.startsWith(":")) {
				int firstSeparator = destinationPath.indexOf("/");
				if (firstSeparator < 0) {
					destinationPath = "/";
				} else {
					destinationPath = destinationPath.substring(firstSeparator);
				}
			}
		}

		// Normalise destination path (remove '.' and '..')
		destinationPath = normalize(destinationPath);

		String contextPath = req.getContextPath();
		if ((contextPath != null) && (destinationPath.startsWith(contextPath))) {
			destinationPath = destinationPath.substring(contextPath.length());
		}
		/*
		 * String pathInfo = req.getPathInfo(); if (pathInfo != null) { String
		 * servletPath = req.getServletPath(); if ((servletPath != null) &&
		 * (destinationPath.startsWith(servletPath))) { destinationPath =
		 * destinationPath .substring(servletPath.length()); } }
		 */
		return this.getRequestResourceInfo(req, destinationPath);

	}

	/**
	 * Delete a resource.
	 * 
	 * @param path
	 *            Path of the resource which is to be deleted
	 * @param req
	 *            Servlet request
	 * @param resp
	 *            Servlet response
	 * @param setStatus
	 *            Should the response status be set on successful completion
	 */
	private boolean deleteResource(ResourceInfo requestFile,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (requestFile.isReserved()) {
			resp.sendError(WebdavStatus.SC_FORBIDDEN);
			return false;
		}

		String ifHeader = req.getHeader("If");
		if (ifHeader == null)
			ifHeader = "";

		String lockTokenHeader = req.getHeader("Lock-Token");
		if (lockTokenHeader == null)
			lockTokenHeader = "";

		if (isLocked(ifHeader, lockTokenHeader)) {
			resp.sendError(WebdavStatus.SC_LOCKED);
			return false;
		}

		boolean exists = requestFile.exists();

		if (!exists) {
			resp.sendError(WebdavStatus.SC_NOT_FOUND);
			return false;
		}

		Hashtable<String, Integer> errors = new Hashtable<String, Integer>();
		String path = requestFile.getPath();
		if (requestFile.isFile()) {
			if ((path.toUpperCase().startsWith("/WEB-INF"))
					|| (path.toUpperCase().startsWith("/META-INF"))) {
				errors.put(path, new Integer(WebdavStatus.SC_FORBIDDEN));
			}
			if (!requestFile.delete()) {
				errors.put(path, new Integer(
						WebdavStatus.SC_INTERNAL_SERVER_ERROR));
			}
		} else {
			deleteCollection(requestFile, ifHeader, lockTokenHeader, errors);
		}

		if (!errors.isEmpty()) {

			sendReport(req, resp, errors);
			return false;

		}

		return true;

	}

	/**
	 * Send a multistatus element containing a complete error report to the
	 * client.
	 * 
	 * @param req
	 *            Servlet request
	 * @param resp
	 *            Servlet response
	 * @param errorList
	 *            List of error to be displayed
	 */
	private void sendReport(HttpServletRequest req, HttpServletResponse resp,
			Map<String, Integer> errorList) throws ServletException,
			IOException {

		resp.setStatus(WebdavStatus.SC_MULTI_STATUS);

		XMLWriter generatedXML = new XMLWriter();
		generatedXML.writeXMLHeader();

		generatedXML.writeElement(null, "multistatus"
				+ generateNamespaceDeclarations(), XMLWriter.OPENING);

		Iterator<String> pathList = errorList.keySet().iterator();
		while (pathList.hasNext()) {

			String errorPath = (String) pathList.next();
			int errorCode = ((Integer) errorList.get(errorPath)).intValue();

			generatedXML.writeElement(null, "response", XMLWriter.OPENING);

			generatedXML.writeElement(null, "href", XMLWriter.OPENING);
			generatedXML.writeText(this.getAbsoluteURL(req, errorPath));
			generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText("HTTP/1.1 " + errorCode + " "
					+ WebdavStatus.getStatusText(errorCode));
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "response", XMLWriter.CLOSING);

		}

		generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);

		Writer writer = resp.getWriter();
		writer.write(generatedXML.toString());
		writer.close();

	}

	/**
	 * 解析Overwrite参数
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @return boolean
	 */
	private boolean parseOverwrite(HttpServletRequest req) {
		boolean overwrite = true;
		String overwriteHeader = req.getHeader("Overwrite");

		if (overwriteHeader != null) {
			if (overwriteHeader.equalsIgnoreCase("T")) {
				overwrite = true;
			} else {
				overwrite = false;
			}
		}
		return overwrite;

	}

	/**
	 * Propfind helper method.
	 * 
	 * @param req
	 *            The servlet request
	 * @param resources
	 *            Resources object associated with this context
	 * @param generatedXML
	 *            XML response to the Propfind request
	 * @param path
	 *            Path of the current resource
	 * @param type
	 *            Propfind type
	 * @param propertiesVector
	 *            If the propfind type is find properties by name, then this
	 *            Vector contains those properties
	 */
	private void parseProperties(HttpServletRequest req, ResourceInfo resource,
			XMLWriter generatedXML, int type, Collection<?> propertiesVector)
			throws ServletException, IOException {
		String path = resource.getPath();

		// Exclude any resource in the /WEB-INF and /META-INF subdirectories
		// (the "toUpperCase()" avoids problems on Windows systems)
		if (resource.isReserved())
			return;

		generatedXML.writeElement(null, "response", XMLWriter.OPENING);

		// Generating href element
		generatedXML.writeElement(null, "href", XMLWriter.OPENING);

		String href = path;
		if ((resource.isDirectory()) && (!href.endsWith("/")))
			href += "/";

		generatedXML.writeText(this.getAbsoluteURL(req, rewriteUrl(href)));

		generatedXML.writeElement(null, "href", XMLWriter.CLOSING);

		switch (type) {

		case PropfindParser.FIND_ALL_PROP:
			renderProperties(resource, generatedXML);

			break;

		case PropfindParser.FIND_PROPERTY_NAMES:

			renderPropertyNames(resource, generatedXML);

			break;

		case PropfindParser.FIND_BY_PROPERTY:
			renderProperties(resource, generatedXML, propertiesVector);

			break;

		}

		generatedXML.writeElement(null, "response", XMLWriter.CLOSING);

	}

	/**
	 * Propfind helper method. Dispays the properties of a lock-null resource.
	 * 
	 * @param resources
	 *            Resources object associated with this context
	 * @param generatedXML
	 *            XML response to the Propfind request
	 * @param path
	 *            Path of the current resource
	 * @param type
	 *            Propfind type
	 * @param propertiesVector
	 *            If the propfind type is find properties by name, then this
	 *            Vector contains those properties
	 */
	private void parseLockNullProperties(HttpServletRequest req,
			XMLWriter generatedXML, ResourceInfo resource, int type,
			Collection<?> propertiesVector) throws ServletException {
		// Exclude any resource in the /WEB-INF and /META-INF subdirectories
		// (the "toUpperCase()" avoids problems on Windows systems)
		String path = resource.getPath();
		if (path.toUpperCase().startsWith("/WEB-INF")
				|| path.toUpperCase().startsWith("/META-INF"))
			return;

		generatedXML.writeElement(null, "response", XMLWriter.OPENING);

		// Generating href element
		generatedXML.writeElement(null, "href", XMLWriter.OPENING);

		/*
		 * String absoluteUri = req.getRequestURI(); String relativePath =
		 * getRelativePath(req); String toAppend =
		 * path.substring(relativePath.length()); if (!toAppend.startsWith("/"))
		 * toAppend = "/" + toAppend;
		 * 
		 * 
		 * generatedXML.writeText(rewriteUrl(normalize(absoluteUri +
		 * toAppend)));
		 */
		generatedXML.writeText(this.getAbsoluteURL(req, path));
		generatedXML.writeElement(null, "href", XMLWriter.CLOSING);

		String resourceName = path;
		int lastSlash = path.lastIndexOf('/');
		if (lastSlash != -1)
			resourceName = resourceName.substring(lastSlash + 1);

		switch (type) {

		case PropfindParser.FIND_ALL_PROP:

			renderLockNullProperties(resource, generatedXML);

			break;

		case PropfindParser.FIND_PROPERTY_NAMES:

			renderLockNullPropertyNames(resource, generatedXML);

			break;

		case PropfindParser.FIND_BY_PROPERTY:

			renderLockNullProperties(resource, generatedXML, propertiesVector);

			break;

		}

		generatedXML.writeElement(null, "response", XMLWriter.CLOSING);

	}

	/**
	 * Determines the methods normally allowed for the resource.
	 * 
	 */
	private StringBuffer determineMethodsAllowed(ResourceInfo file) {

		StringBuffer methodsAllowed = new StringBuffer();
		boolean exists = file.exists();

		if (!exists) {
			methodsAllowed.append("OPTIONS, MKCOL, PUT, LOCK");
			return methodsAllowed;
		}

		methodsAllowed.append("OPTIONS, GET, HEAD, POST, DELETE, TRACE");
		methodsAllowed.append(", PROPPATCH, COPY, MOVE, LOCK, UNLOCK");

		if (listings) {
			methodsAllowed.append(", PROPFIND");
		}

		if (file.isFile()) {
			methodsAllowed.append(", PUT");
		}

		return methodsAllowed;
	}

	/**
	 * Deletes a collection.
	 * 
	 * @param resources
	 *            Resources implementation associated with the context
	 * @param path
	 *            Path to the collection to be deleted
	 * @param errorList
	 *            Contains the list of the errors which occurred
	 */
	private void deleteCollection(ResourceInfo file, String ifHeader,
			String lockTokenHeader, Map<String, Integer> errors) {
		String path = file.getPath();
		if ((path.toUpperCase().startsWith("/WEB-INF"))
				|| (path.toUpperCase().startsWith("/META-INF"))) {
			errors.put(path, new Integer(WebdavStatus.SC_FORBIDDEN));
			return;
		}

		for (Iterator<?> iterator = file.getChildren(); iterator.hasNext();) {
			ResourceInfo ncPair = (ResourceInfo) iterator.next();
			String childName = path;
			if (!childName.equals("/"))
				childName += "/";
			childName += ncPair.getName();

			if (this.lockManager
					.isLocked(childName, ifHeader + lockTokenHeader)) {

				errors.put(childName, new Integer(WebdavStatus.SC_LOCKED));

			} else {

				if (ncPair.isDirectory()) {
					deleteCollection(ncPair, ifHeader, lockTokenHeader, errors);
				}
				if (!ncPair.delete())
					errors.put(childName, new Integer(
							WebdavStatus.SC_INTERNAL_SERVER_ERROR));

			}

		}

		file.delete();
	}

	/**
	 * 渲染全部的属性
	 * 
	 * @param generatedXML
	 *            XMLWriter
	 */
	private void renderLockNullProperties(ResourceInfo resource,
			XMLWriter generatedXML) {
		LockInfo lock = this.lockManager.getLock(resource.getPath());
		generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
		generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

		generatedXML.writeProperty(null, "creationdate",
				getISOCreationDate(lock.getCreationDate().getTime()));
		generatedXML.writeElement(null, "displayname", XMLWriter.OPENING);
		generatedXML.writeData(resource.getName());
		generatedXML.writeElement(null, "displayname", XMLWriter.CLOSING);
		generatedXML.writeProperty(null, "getlastmodified", FastHttpDateFormat
				.formatDate(lock.getCreationDate().getTime(), null));
		generatedXML.writeProperty(null, "getcontentlength", String.valueOf(0));
		generatedXML.writeProperty(null, "getcontenttype", "");
		generatedXML.writeProperty(null, "getetag", "");
		generatedXML.writeElement(null, "resourcetype", XMLWriter.OPENING);
		generatedXML.writeElement(null, "lock-null", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "resourcetype", XMLWriter.CLOSING);

		generatedXML.writeProperty(null, "source", "");

		String supportedLocks = "<lockentry>"
				+ "<lockscope><exclusive/></lockscope>"
				+ "<locktype><write/></locktype>" + "</lockentry>"
				+ "<lockentry>" + "<lockscope><shared/></lockscope>"
				+ "<locktype><write/></locktype>" + "</lockentry>";
		generatedXML.writeElement(null, "supportedlock", XMLWriter.OPENING);
		generatedXML.writeText(supportedLocks);
		generatedXML.writeElement(null, "supportedlock", XMLWriter.CLOSING);

		this.lockManager
				.generateLockDiscovery(resource.getPath(), generatedXML);

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "status", XMLWriter.OPENING);
		generatedXML.writeText(statusOK);
		generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

	}

	/**
	 * 渲染部分属性
	 * 
	 * @param generatedXML
	 *            XMLWriter
	 * @param propertiesVector
	 *            Collection
	 */
	private void renderLockNullProperties(ResourceInfo resource,
			XMLWriter generatedXML, Collection<?> propertiesVector) {
		String path = resource.getPath();
		LockInfo lock = this.lockManager.getLock(resource.getPath());
		Vector<String> propertiesNotFound = new Vector<String>();

		// Parse the list of properties

		generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
		generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

		Iterator<?> properties = propertiesVector.iterator();

		while (properties.hasNext()) {

			String property = (String) properties.next();

			if (property.equals("creationdate")) {
				generatedXML.writeProperty(null, "creationdate",
						getISOCreationDate(lock.getCreationDate().getTime()));
			} else if (property.equals("displayname")) {
				generatedXML.writeElement(null, "displayname",
						XMLWriter.OPENING);
				generatedXML.writeData(resource.getName());
				generatedXML.writeElement(null, "displayname",
						XMLWriter.CLOSING);
			} else if (property.equals("getcontentlanguage")) {
				generatedXML.writeElement(null, "getcontentlanguage",
						XMLWriter.NO_CONTENT);
			} else if (property.equals("getcontentlength")) {
				generatedXML.writeProperty(null, "getcontentlength", (String
						.valueOf(0)));
			} else if (property.equals("getcontenttype")) {
				if (resource.getContentType() != null)
					generatedXML.writeProperty(null, "getcontenttype", resource
							.getContentType());
				else
					generatedXML.writeProperty(null, "getcontenttype", "");

			} else if (property.equals("getetag")) {
				generatedXML.writeProperty(null, "getetag", this
						.getETag(resource));
			} else if (property.equals("getlastmodified")) {
				generatedXML.writeProperty(null, "getlastmodified",
						FastHttpDateFormat.formatDate(lock.getCreationDate()
								.getTime(), null));
			} else if (property.equals("resourcetype")) {
				generatedXML.writeElement(null, "resourcetype",
						XMLWriter.OPENING);
				generatedXML.writeElement(null, "lock-null",
						XMLWriter.NO_CONTENT);
				generatedXML.writeElement(null, "resourcetype",
						XMLWriter.CLOSING);
				// } else if (property.equals("source")) {
				// generatedXML.writeProperty(null, "source", "");
			} else if (property.equals("supportedlock")) {

				generatedXML.writeElement(null, "supportedlock",
						XMLWriter.OPENING);
				generatedXML.writeText(supportedLocks);
				generatedXML.writeElement(null, "supportedlock",
						XMLWriter.CLOSING);
			} else if (property.equals("lockdiscovery")) {
				if (!this.lockManager.generateLockDiscovery(path, generatedXML))
					propertiesNotFound.addElement(property);
			} else {
				propertiesNotFound.addElement(property);
			}

		}

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "status", XMLWriter.OPENING);
		generatedXML.writeText(statusOK);
		generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

		Enumeration<String> propertiesNotFoundList = propertiesNotFound
				.elements();

		if (propertiesNotFoundList.hasMoreElements()) {

			generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
			generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

			while (propertiesNotFoundList.hasMoreElements()) {
				generatedXML.writeElement(null, (String) propertiesNotFoundList
						.nextElement(), XMLWriter.NO_CONTENT);
			}

			generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText(statusNotFound);
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

		}

	}

	/**
	 * 渲染系统支持的属性名称
	 * 
	 * @param generatedXML
	 *            XMLWriter
	 */
	private void renderLockNullPropertyNames(ResourceInfo resource,
			XMLWriter generatedXML) {

		generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
		generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

		generatedXML.writeElement(null, "creationdate", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "displayname", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "getcontentlanguage",
				XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "getcontentlength",
				XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "getcontenttype", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "getetag", XMLWriter.NO_CONTENT);
		generatedXML
				.writeElement(null, "getlastmodified", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "resourcetype", XMLWriter.NO_CONTENT);
		// generatedXML.writeElement(null, "source",
		// XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "lockdiscovery", XMLWriter.NO_CONTENT);

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "status", XMLWriter.OPENING);
		generatedXML.writeText(statusOK);
		generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

	}

	private static String toHttpDate(long date) {
		return FastHttpDateFormat.formatDate(date, null);
	}

	private String getETag(ResourceInfo resource) {
		if (resource.getLastModified() != null) {
			return "W/\"" + resource.getLength() + "-"
					+ resource.getLastModified().getTime() + "\"";
		} else {
			return "W/\"" + resource.getLength() + "\"";
		}
	}

	/**
	 * 渲染正常文件的属性
	 * 
	 * @param generatedXML
	 *            XMLWriter
	 */
	private void renderProperties(ResourceInfo resource, XMLWriter generatedXML) {
		generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
		generatedXML.writeElement(null, "prop", XMLWriter.OPENING);
		generatedXML.writeElement(null, "name", XMLWriter.OPENING);
		generatedXML.writeData(resource.getName());
		generatedXML.writeElement(null, "name", XMLWriter.CLOSING);
		if (resource.getCreationDate() != null)
			generatedXML.writeProperty(null, "creationdate",
					getISOCreationDate(resource.getCreationDate().getTime()));

		generatedXML.writeElement(null, "displayname", XMLWriter.OPENING);
		generatedXML.writeData(resource.getDisplayName());
		generatedXML.writeElement(null, "displayname", XMLWriter.CLOSING);
		String parentName = this.getParentName(resource);
		if (parentName != null) {
			generatedXML.writeElement(null, "parentname", XMLWriter.OPENING);
			generatedXML.writeData(parentName);
			generatedXML.writeElement(null, "parentname", XMLWriter.CLOSING);

		}
		generatedXML.writeProperty(null, "ishidden", "" + resource.isHidden());
		generatedXML.writeProperty(null, "isreadonly", ""
				+ resource.isReadonly());
		generatedXML.writeProperty(null, "isroot", "" + resource.isRoot());

		if (!resource.isDirectory()) {
			generatedXML.writeProperty(null, "getlastmodified",
					toHttpDate(resource.getLastModified().getTime()));
			generatedXML.writeProperty(null, "getcontentlength", String
					.valueOf(resource.getLength()));
			String contentType = resource.getContentType();
			if (contentType == null)
				contentType = this.getServletContext().getMimeType(
						resource.getPath());
			if (contentType != null) {
				generatedXML.writeProperty(null, "getcontenttype", contentType);
			}
			generatedXML.writeProperty(null, "getetag", getETag(resource));
			generatedXML.writeElement(null, "resourcetype",
					XMLWriter.NO_CONTENT);
		} else {
			generatedXML.writeElement(null, "resourcetype", XMLWriter.OPENING);
			generatedXML.writeElement(null, "collection", XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "resourcetype", XMLWriter.CLOSING);
		}

		// generatedXML.writeProperty(null, "source", "");

		String supportedLocks = "<lockentry>"
				+ "<lockscope><exclusive/></lockscope>"
				+ "<locktype><write/></locktype>" + "</lockentry>"
				+ "<lockentry>" + "<lockscope><shared/></lockscope>"
				+ "<locktype><write/></locktype>" + "</lockentry>";
		generatedXML.writeElement(null, "supportedlock", XMLWriter.OPENING);
		generatedXML.writeText(supportedLocks);
		generatedXML.writeElement(null, "supportedlock", XMLWriter.CLOSING);

		this.lockManager
				.generateLockDiscovery(resource.getPath(), generatedXML);

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "status", XMLWriter.OPENING);
		generatedXML.writeText(statusOK);
		generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);
	}

	/**
	 * 渲染正常文件的属性
	 * 
	 * @param generatedXML
	 *            XMLWriter
	 * @param propertiesVector
	 *            Collection
	 */
	private void renderProperties(ResourceInfo resource,
			XMLWriter generatedXML, Collection<?> propertiesVector) {
		Vector<String> propertiesNotFound = new Vector<String>();

		// Parse the list of properties

		generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
		generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

		Iterator<?> properties = propertiesVector.iterator();

		while (properties.hasNext()) {

			String property = (String) properties.next();

			if (property.equals("creationdate")) {
				if (resource.getCreationDate() != null)
					generatedXML.writeProperty(null, "creationdate",
							getISOCreationDate(resource.getCreationDate()
									.getTime()));
				else
					propertiesNotFound.addElement(property);
			} else if (property.equals("name")) {
				generatedXML.writeElement(null, "name", XMLWriter.OPENING);
				generatedXML.writeData(resource.getName());
				generatedXML.writeElement(null, "name", XMLWriter.CLOSING);
			} else if (property.equals("displayname")) {
				generatedXML.writeElement(null, "displayname",
						XMLWriter.OPENING);
				generatedXML.writeData(resource.getDisplayName());
				generatedXML.writeElement(null, "displayname",
						XMLWriter.CLOSING);
			} else if (property.equals("getcontentlanguage")) {
				if (resource.isDirectory()) {
					propertiesNotFound.addElement(property);
				} else {
					generatedXML.writeElement(null, "getcontentlanguage",
							XMLWriter.NO_CONTENT);
				}
			} else if (property.equals("getcontentlength")) {
				if (resource.isDirectory()) {
					propertiesNotFound.addElement(property);
				} else {
					generatedXML.writeProperty(null, "getcontentlength",
							(String.valueOf(resource.getLength())));
				}
			} else if (property.equals("isroot")) {
				generatedXML.writeProperty(null, "isroot", ""
						+ resource.isRoot());
			} else if (property.equals("iscollection")) {
				generatedXML.writeProperty(null, "iscollection", ""
						+ resource.isDirectory());
			}

			else if (property.equals("ishidden")) {
				generatedXML.writeProperty(null, "ishidden", ""
						+ resource.isHidden());
			} else if (property.equals("isreadonly")) {
				generatedXML.writeProperty(null, "isreadonly", ""
						+ resource.isReadonly());
			} else if (property.equals("getcontenttype")) {
				if (resource.isDirectory()) {
					propertiesNotFound.addElement(property);
				} else {
					generatedXML.writeProperty(null, "getcontenttype", resource
							.getContentType());
				}
			} else if (property.equals("getetag")) {
				if (resource.isDirectory()) {
					propertiesNotFound.addElement(property);
				} else {
					generatedXML.writeProperty(null, "getetag",
							getETag(resource));
				}
			} else if (property.equals("getlastmodified")) {
				generatedXML.writeProperty(null, "getlastmodified", toHttpDate(resource.getLastModified().getTime()));
			} else if (property.equals("resourcetype")) {
				if (resource.isDirectory()) {
					generatedXML.writeElement(null, "resourcetype",
							XMLWriter.OPENING);
					generatedXML.writeElement(null, "collection",
							XMLWriter.NO_CONTENT);
					generatedXML.writeElement(null, "resourcetype",
							XMLWriter.CLOSING);
				} else {
					generatedXML.writeElement(null, "resourcetype",
							XMLWriter.NO_CONTENT);

				}
			} else if (property.equals("parentname")) {
				String name = this.getParentName(resource);
				if (name != null) {
					generatedXML.writeElement(null, "parentname",
							XMLWriter.OPENING);
					generatedXML.writeData(name);
					generatedXML.writeElement(null, "parentname",
							XMLWriter.CLOSING);
				} else
					propertiesNotFound.addElement(property);
			} else if (property.equals("supportedlock")) {
				supportedLocks = "<lockentry>"
						+ "<lockscope><exclusive/></lockscope>"
						+ "<locktype><write/></locktype>" + "</lockentry>"
						+ "<lockentry>" + "<lockscope><shared/></lockscope>"
						+ "<locktype><write/></locktype>" + "</lockentry>";
				generatedXML.writeElement(null, "supportedlock",
						XMLWriter.OPENING);
				generatedXML.writeText(supportedLocks);
				generatedXML.writeElement(null, "supportedlock",
						XMLWriter.CLOSING);
			} else if (property.equals("lockdiscovery")) {
				if (!this.lockManager.generateLockDiscovery(resource.getPath(),
						generatedXML))
					propertiesNotFound.addElement(property);
			} else {
				propertiesNotFound.addElement(property);
			}

		}

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "status", XMLWriter.OPENING);
		generatedXML.writeText(statusOK);
		generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

		Enumeration<String> propertiesNotFoundList = propertiesNotFound.elements();

		if (propertiesNotFoundList.hasMoreElements()) {

			generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
			generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

			while (propertiesNotFoundList.hasMoreElements()) {
				generatedXML.writeElement(null, (String) propertiesNotFoundList
						.nextElement(), XMLWriter.NO_CONTENT);
			}

			generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText(statusNotFound);
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

		}
	}

	/**
	 * 渲染正常文件的属性名
	 * 
	 * @param generatedXML
	 *            XMLWriter
	 */
	private void renderPropertyNames(ResourceInfo resource,
			XMLWriter generatedXML) {
		generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
		generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

		generatedXML.writeElement(null, "creationdate", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "displayname", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "name", XMLWriter.NO_CONTENT);

		if (!resource.isDirectory()) {
			generatedXML.writeElement(null, "getcontentlanguage",
					XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getcontentlength",
					XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getcontenttype",
					XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getetag", XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getlastmodified",
					XMLWriter.NO_CONTENT);
		}
		generatedXML.writeElement(null, "resourcetype", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "source", XMLWriter.NO_CONTENT);
		generatedXML.writeElement(null, "lockdiscovery", XMLWriter.NO_CONTENT);

		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "status", XMLWriter.OPENING);
		generatedXML.writeText(statusOK);
		generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);
	}

	/**
	 * 判断资源是否被锁定
	 * 
	 * @param ifHeader
	 *            String
	 * @param lockToken
	 *            String
	 * @return boolean
	 */
	private boolean isLocked(String ifHeader, String lockToken) {
		return this.lockManager.isLocked(ifHeader, lockToken);
	}

	private String getAbsoluteURL(HttpServletRequest request, String path)
			throws ServletException {
		try {
			return RequestUtils.absoluteURL(request, path).toString();
		} catch (MalformedURLException ex) {
			throw new ServletException(ex);
		}
	}

	private static String getISOCreationDate(long creationDate) {
		StringBuffer creationDateValue = new StringBuffer(creationDateFormat
				.format(new Date(creationDate)));

		return creationDateValue.toString();
	}

	private String getParentName(ResourceInfo resource) {
		if (resource.isRoot())
			return null;
		if (resource.getParent() != null)
			return resource.getParent().getName();
		return null;
	}

};
