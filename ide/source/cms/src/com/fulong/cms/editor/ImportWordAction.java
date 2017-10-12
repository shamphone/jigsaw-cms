package com.fulong.cms.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.util.FileCopyUtils;

import com.fulong.cms.content.ContentBaseAction;
import com.fulong.cms.form.UploadFileForm;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.site.Site;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lixf, lichengzhao
 * @version 2.0
 */
public class ImportWordAction extends ContentBaseAction {
	

	protected Log log = LogFactory.getLog(this.getClass());
	private static final String STATUS_KEY = "importword";
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UploadFileForm uploadForm = (UploadFileForm) form;
		FormFile file = uploadForm.getFile();
		
		// 没有文件或导入空文件时直接返回
		if (file == null || file.getFileSize() == 0)
			return mapping.findForward("success");

		ActionMessages errors = new ActionMessages();
		HttpSession session = request.getSession();
		session.setAttribute(STATUS_KEY, "0");
		int format = 10 ; //wdFormatFilteredHTML;
		// 兼容8.3格式的文件系统
		File zipFile = File.createTempFile("f"+Math.abs(new Random().nextInt(1000000)), 
				"."+FilenameUtils.getExtension(file.getFileName()));
		
		// 上传文件
		InputStream inStream = null;
		OutputStream outStream = null;
		try{
			inStream = file.getInputStream();
			outStream = new FileOutputStream(zipFile);
			IOUtils.copy(inStream, outStream);
		} catch(Exception ex) {
			log.error("Upload Word File Failed", ex);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("import.word.file.upload.failed", ex.getMessage()));
			this.saveErrors(request, errors);
			return mapping.findForward("failed");
		} finally {
			this.closeStream(outStream);
			this.closeStream(inStream);
			this.destroyFormFile(file);
		}
		session.setAttribute(STATUS_KEY, "1");

		// 解压缩zip文档
		File wordFile = null;
		if (zipFile.getName().toLowerCase().endsWith(".zip")) {
			try {
				wordFile = this.decompress(zipFile);
			} catch (IOException ex) {
				log.error("Upzip File " + file.getFileName() + " Failed",ex);
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("import.word.zip.failed"));
				this.saveErrors(request, errors);
				return mapping.findForward("failed");
			} finally {
				this.forceDelete(zipFile);
			}
		}
		session.setAttribute(STATUS_KEY, "2");
		
		// 如果上传的不是zip文件
		if (wordFile == null)
			wordFile = zipFile;

		// 将word转换成html；
		// 是否保留office标记
		if (request.getParameter("reserve") != null)
			format = 8;// wdFormatHTML
		File htmlFile = null;
		try {
			if (request.getParameter("image") == null)
				htmlFile = this.getWordUtils(request).wordToHtml(wordFile, format);
			else
				htmlFile = this.getWordUtils(request).wordToImage(wordFile, format);
		} catch (Exception ex) {
			log.error("Convert Word File Failed", ex);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("import.word.convert.failed"));
			this.saveErrors(request, errors);
			return mapping.findForward("failed");
		} finally {
			this.getWordUtils(request).recycle(wordFile);
		}
		session.setAttribute(STATUS_KEY, "3");

		// 清理文档格式
		Parser parser = new Parser(htmlFile.getPath());
		WordFilter filter=new WordFilter(htmlFile.getParentFile(),request,response);
		NodeList nodes = null;
		try {
			nodes=parser.parse(filter);
		} catch (ParserException ex) {
			log.error("Clean Word Document Failed", ex);
			this.forceDelete(new File(htmlFile.getAbsolutePath().substring(0, htmlFile.getAbsolutePath().lastIndexOf(".") + 1) + "files"));
			this.forceDelete(htmlFile);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("import.word.read.html.failed"));
			this.saveErrors(request, errors);
			return mapping.findForward("failed");
		}
		log.trace("parsing html file "+ htmlFile.getPath());
		if(nodes.size()>0) {
			String body= ((BodyTag)nodes.elementAt(0)).getChildrenHTML();
			body = body.replace("\"", "\\\"");
			body = body.replace("'", "\\'");
			body = body.replace(System.getProperty("line.separator"), " ");
		    request.setAttribute("body", body);
		}
		session.setAttribute(STATUS_KEY, "4");
		
		this.forceDelete(new File(htmlFile.getAbsolutePath().substring(0, htmlFile.getAbsolutePath().lastIndexOf(".") + 1) + "files"));
		this.forceDelete(htmlFile);
		session.setAttribute(STATUS_KEY, "5");	
		session.removeAttribute(STATUS_KEY);
		return mapping.findForward("success");
	}

	private File decompress(File zipFile) throws Exception {
		ZipFile zip = new ZipFile(zipFile);
		ZipEntry entry = (ZipEntry) zip.getEntries().nextElement();
		InputStream is = zip.getInputStream(entry);
		byte[] content = FileCopyUtils.copyToByteArray(is);
		File descFile = File.createTempFile("w"+Math.abs(new Random().nextInt(1000000)), ".doc");
		FileUtils.writeByteArrayToFile(descFile, content);
		return descFile;
	}

	class WordFilter implements NodeFilter {
		private HttpServletRequest request;
		private HttpServletResponse response;
		private File folder;
		public WordFilter(File folder,HttpServletRequest request,HttpServletResponse response){
			this.folder=folder;
			this.request=request;
			this.response=response;		
		}

		public boolean accept(org.htmlparser.Node node) {
			if(node instanceof BodyTag){
				return true;
			}
			if(node instanceof Tag){
				Tag tag=(Tag)node;
				tag.removeAttribute("lang");
				tag.removeAttribute("language");
				tag.removeAttribute("onmouseover");
				tag.removeAttribute("onmouseout");
			}
			if(node instanceof ImageTag){
				ImageTag tag=(ImageTag)node;
				File imageFile = new File(folder, tag.getAttribute("src"));
				try {
					String newPath = this.saveImageToDB(imageFile,request);
					tag.setAttribute("src", newPath);				
				} catch (Exception ex) {
					log.warn(ex.getMessage(),ex);
				}
			}
			return false;
		}
		
		private String saveImageToDB(File imageFile,HttpServletRequest request) throws Exception {
			String path = null;
			//Node user = getCurrentUser(request, response);
			/**
			 * modified by liuzijun at 17:00 2010-10-28,to change the parentNode of the image resources and to change returnValue of the resourcePath;
			 */
			Site site = getCurrentSite(request, response);
			if(site!=null){
				Node user = site.getOwner();
				if(user!=null){
					String baseName = FilenameUtils.getBaseName(imageFile.getName());
					String extensionName = FilenameUtils.getExtension(imageFile.getName());
					String newFileName = baseName + (System.currentTimeMillis() % 1000000) + "." + extensionName;
					Node root = user.getNode("resources");
					if(root == null){
						root =user.addNode(getRepository(request).getDefinitionManager().getDefinition("resource-scheme"),"resources");
					}
					while (root.getNode(newFileName) != null)
						newFileName = baseName + (System.currentTimeMillis() % 1000000) + "." + extensionName;
					Node newResource = root.addNode(getRepository(request).getDefinitionManager().getDefinition("resource-scheme"), newFileName);
					newResource.setProperty("resource-content", new FileInputStream(imageFile));
					newResource.setProperty("mime", getServlet().getServletContext().getMimeType(newFileName));
					newResource.setProperty("createdTime", Calendar.getInstance());
					newResource.setProperty("length", newResource.getProperty("resource-content").getLength());
					//path = request.getContextPath() + "/resources/" + user.getID() + "/" + newResource.getName();
					//path = path.replace("\\", "/");
					path = "/portal"+newResource.getPath();
					log.trace("save image file "+ imageFile.getPath()+" to " + path);
				}
			}
			return path;
		}
	}

/*
 * Clean this IE tag
 */
/*
private String cleanWord(String content, boolean ignoreFont,
		boolean removeStyles) {
	Matcher m = null;

	content = content.substring(content.indexOf("<body"), content
			.lastIndexOf("</body>"));
	content = Pattern.compile("<body[^>]*>", MATCHER_ALL_FLAGS).matcher(
			content).replaceAll("");

	content = Pattern.compile("<o:p>\\s*</o:p>", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	content = Pattern.compile("<!\\[if\\s+!vml\\]>", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");
	content = Pattern.compile("<!\\[endif\\]>", MATCHER_ALL_FLAGS).matcher(
			content).replaceAll("");

	// Remove mso-xxx styles.
	content = Pattern
			.compile("\\s*mso-[^:]+:[^;\"']+;?", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	// Remove mso-xxx styles.
	content = Pattern.compile("\\s*mso-[^:]+:\"[^\"]*\";?",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("");

	// Remove margin styles.
	content = Pattern.compile("\\s*MARGIN: 0cm 0cm 0pt\\s*\"",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("\"");

	content = Pattern.compile("\\s*TEXT-INDENT: 0cm\\s*;",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("");
	content = Pattern.compile("\\s*TEXT-INDENT: 0cm\\s*\"",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("\"");

	content = Pattern.compile("\\s*TEXT-ALIGN: [^\\s;]+;?\"",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("\"");

	content = Pattern.compile("\\s*PAGE-BREAK-BEFORE: [^\\s;]+;?\"",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("\"");

	content = Pattern.compile("\\s*FONT-VARIANT: [^\\s;]+;?\"",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("\"");

	content = Pattern
			.compile("\\s*tab-stops:[^;\"']*;?", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	content = Pattern.compile("\\s*tab-stops:[^\"']*", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	// Remove FONT face attributes.
	if (ignoreFont) {
		content = Pattern
				.compile("\\s*face=\"[^\"']*\"", MATCHER_ALL_FLAGS)
				.matcher(content).replaceAll("");
		content = Pattern.compile("\\s*face=[^ >]*", MATCHER_ALL_FLAGS)
				.matcher(content).replaceAll("");

		content = Pattern.compile("\\s*FONT-FAMILY:[^;\"']*;?",
				MATCHER_ALL_FLAGS).matcher(content).replaceAll("");
	}

	// Remove Class attributes
	m = Pattern.compile("<(\\w[^>]*) class=([^ |>]*)([^>]*)",
			MATCHER_ALL_FLAGS).matcher(content);
	while (m.find())
		content = content.replace(m.group(), "<" + m.group(1) + m.group(3));

	// Remove styles.
	if (removeStyles) {
		m = Pattern.compile("<(\\w[^>]*) style=\"([^\"']*)\"([^>]*)",
				MATCHER_ALL_FLAGS).matcher(content);
		while (m.find())
			content = content.replace(m.group(), "<" + m.group(1)
					+ m.group(3));
	}

	content = Pattern.compile("\\s*v:shapes=\"[^\"]*\"", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	// Remove empty styles.
	content = Pattern.compile("\\s*style=\"\\s*\"", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");
	content = Pattern.compile("\\s*style='\\s*'", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	content = Pattern.compile("<SPAN\\s*[^>]*>\\s*&nbsp;\\s*</SPAN>",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("&nbsp;");

	content = Pattern.compile("<SPAN\\s*[^>]*></SPAN>", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	// Remove Lang attributes
	m = Pattern.compile("<(\\w[^>]*) lang=([^ |>]*)([^>]*)",
			MATCHER_ALL_FLAGS).matcher(content);
	while (m.find())
		content = content.replace(m.group(), "<" + m.group(1) + m.group(3));

	m = Pattern.compile("<SPAN\\s*>(.*?)</SPAN>", MATCHER_ALL_FLAGS)
			.matcher(content);
	while (m.find())
		content = content.replace(m.group(), m.group(1));

	m = Pattern.compile("<FONT\\s*>(.*?)</FONT>", MATCHER_ALL_FLAGS)
			.matcher(content);
	while (m.find())
		content = content.replace(m.group(), m.group(1));

	// Remove XML elements and declarations
	content = Pattern.compile("<\\?xml[^>]*>", MATCHER_ALL_FLAGS).matcher(
			content).replaceAll("");

	// Remove Tags with XML namespace declarations: <o:p><\/o:p>
	content = Pattern.compile("</\\?\\w+:[^>]*>", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	// Remove comments [SF BUG-1481861].
	content = Pattern.compile("<\\!--.*?-->", MATCHER_ALL_FLAGS).matcher(
			content).replaceAll("");

	content = Pattern.compile("<(U|I|STRIKE)>&nbsp;</\1>",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("&nbsp;");

	content = Pattern.compile("<H\\d>\\s*<H\\d>", MATCHER_ALL_FLAGS)
			.matcher(content).replaceAll("");

	// Remove "display:none" tags.
	content = Pattern.compile(
			"<(\\w+)[^>]*\\sstyle=\"[^\"']*DISPLAY\\s?:\\s?none(.*?)</\1>",
			MATCHER_ALL_FLAGS).matcher(content).replaceAll("");

	// Remove language tags
	m = Pattern.compile("<(\\w[^>]*) language=([^ |>]*)([^>]*)",
			MATCHER_ALL_FLAGS).matcher(content);
	while (m.find())
		content = content.replace(m.group(), "<" + m.group(1) + m.group(3));

	// Remove onmouseover and onmouseout events (from MS Word comments
	// effect)
	m = Pattern.compile("<(\\w[^>]*) onmouseover=\"([^\"']*)\"([^>]*)",
			MATCHER_ALL_FLAGS).matcher(content);
	while (m.find())
		content = content.replace(m.group(), "<" + m.group(1) + m.group(3));

	m = Pattern.compile("<(\\w[^>]*) onmouseout=\"([^\"']*)\"([^>]*)",
			MATCHER_ALL_FLAGS).matcher(content);
	while (m.find())
		content = content.replace(m.group(), "<" + m.group(1) + m.group(3));
*/
	/*
	 * if ( FCKConfig.CleanWordKeepsStructure ) // The original <Hn> tag
	 * send from Word is something like this: <Hn
	 * style="margin-top:0px;margin-bottom:0px"> m =
	 * Pattern.compile("<H(\\d)([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<h" + m.group(1) + ">");
	 * 
	 * // Word likes to insert extra <font> tags, when using MSIE. (Wierd).
	 * m = Pattern.compile("<(H\\d)><FONT[^>]*>(.*?)</FONT></\1>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<" + m.group(1) + ">" + m.group(2) + "</"
	 * + m.group(1) + ">");
	 * 
	 * m = Pattern.compile("<(H\\d)><EM>(.*?)</EM></\1>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<" + m.group(1) + ">" + m.group(2) + "</"
	 * + m.group(1) + ">"); } else { m = Pattern.compile("<H1([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<div" + m.group(1) +
	 * "><b><font size=\"6\">");
	 * 
	 * m = Pattern.compile("<H2([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<div" + m.group(1) +
	 * "><b><font size=\"5\">");
	 * 
	 * m = Pattern.compile("<H3([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<div" + m.group(1) +
	 * "><b><font size=\"4\">");
	 * 
	 * m = Pattern.compile("<H4([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<div" + m.group(1) +
	 * "><b><font size=\"3\">");
	 * 
	 * m = Pattern.compile("<H5([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<div" + m.group(1) +
	 * "><b><font size=\"2\">");
	 * 
	 * m = Pattern.compile("<H6([^>]*)>",
	 * MATCHER_ALL_FLAGS).matcher(content); while (m.find()) content =
	 * content.replace(m.group(), "<div" + m.group(1) +
	 * "><b><font size=\"1\">");
	 * 
	 * content = Pattern.compile("</H\\d>",
	 * MATCHER_ALL_FLAGS).matcher(content).replaceAll("</font></b></div>");
	 * 
	 * // Transform <P> to <DIV> content =
	 * Pattern.compile("(<P)([^>]*>.*?)(</P>)",
	 * MATCHER_ALL_FLAGS).matcher(content).replaceAll("</font></b></div>");
	 * 
	 * // Remove empty tags (three times, just to be sure). // This also
	 * removes any empty anchor content =
	 * Pattern.compile("<([^\\s>]+)(\\s[^>]*)?>\\s*</\1>",
	 * MATCHER_ALL_FLAGS).matcher(content).replaceAll("</font></b></div>");
	 * content = Pattern.compile("<([^\\s>]+)(\\s[^>]*)?>\\s*</\1>",
	 * MATCHER_ALL_FLAGS).matcher(content).replaceAll("</font></b></div>");
	 * content = Pattern.compile("<([^\\s>]+)(\\s[^>]*)?>\\s*</\1>",
	 * MATCHER_ALL_FLAGS).matcher(content).replaceAll("</font></b></div>");
	 * }
	 */
	//return content;
//}
}
