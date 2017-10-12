package com.fulong.site.template;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.RequestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fulong.common.FileUtils;
import com.fulong.longcon.exchange.DefaultXMLImporter;
import com.fulong.longcon.exchange.XMLImporter;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.site.form.TemplateForm;


/**
 * 网站模板导入
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.0
 */
public class DoImportAction extends TemplateBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.site.template.TemplateBaseAction#templateExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm aform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TemplateForm form = (TemplateForm) aform;
		FormFile file =  form.getZipFile();
		String zipFileName = FilenameUtils.getBaseName(file.getFileName());
		String outputDir = this.getServletContext().getRealPath("/") + "/temp/";
		String outputPath = outputDir+zipFileName;
		FileUtils.unzip(new File(outputPath), file.getInputStream());
		String sourcePath = outputPath + "/" + zipFileName;
		String destPath = this.getSiteFactory(request).getBaseDirectory()+ "/" + zipFileName;
		File source = new File(sourcePath);
		File dest = new File(destPath); 
		if(source.exists()){
			//复制模板目录文件
			FileUtils.copyDirectory(source,dest);
			//导入分类及节点数据
			URL baseURL = RequestUtils.serverURL(request);
			XMLImporter importer = new DefaultXMLImporter(this.getRepository(request), baseURL);			
			File categoryFile = new File(outputPath + "/category.xml");
			if(categoryFile.exists()){
				Document categoryDoc = FileUtils.readXML(categoryFile);
				importer.doImport(categoryDoc);
			}
			//导入模板数据
			File templateFile = new File(outputPath + "/template.xml");
			if(templateFile.exists()){
				this.doImport(FileUtils.readXML(templateFile),request);
			}
		}
		FileUtils.deleteDirectory(new File (outputDir));
		return mapping.findForward("success");
	}
	
	/**
	 * 导入一个模板信息xml文件，将模板及其栏目导入数据库中
	 * @param document
	 * @throws IOException 
	 */
	private void doImport(Document document,HttpServletRequest request) throws IOException{
		Element root = document.getDocumentElement();
		if (root == null) {
			return;
		}
		NodeList nodes = root.getElementsByTagName("template");
		for (int i = 0; i < nodes.getLength(); i++)
			importTemplate((Element) nodes.item(i),request);
	}
	
	/**
	 * 导入模板template数据
	 * @param root
	 * @throws IOException 
	 */
	private void importTemplate(Element root,HttpServletRequest request) throws IOException {
		String name = root.getAttribute("name");
		SiteTemplate template = this.getSiteFactory(request).getTemplate(name);
		if(template==null){
			template = this.getSiteFactory(request).createTemplate(name);
		}
		template.setDescription(root.getAttribute("description"));
		template.setDisplayName(root.getAttribute("displayName"));
		template.setResolution(root.getAttribute("resolution"));
		if(root.getAttribute("local")!=null&&!root.getAttribute("local").equals("")){
			template.setLocale(new Locale(root.getAttribute("local")));
		}
		
	}
}
