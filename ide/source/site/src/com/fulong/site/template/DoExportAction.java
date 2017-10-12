package com.fulong.site.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fulong.longcon.exchange.DefaultXMLExporter;
import com.fulong.longcon.exchange.XMLExporter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.site.SiteTemplate;
/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 1.1
 */
public class DoExportAction extends TemplateBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.site.template.TemplateBaseAction#templateExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward templateExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/x-zip-compressed");
		//response.setHeader("Content-Transfer-Encoding", "utf-8");
		String templateID=request.getParameter("templateId");
		String[] categoryIDs = request.getParameterValues("categoryIDs");
		URL baseURL = RequestUtils.serverURL(request);
		XMLExporter exporter = new DefaultXMLExporter(this.getRepository(request), baseURL);
		SiteTemplate template = this.getSiteFactory(request).getTemplate(templateID);

		if(template!=null){
			response.addHeader("Content-Disposition", "attachment; filename="+template.getName()+".tpl");			
			ServletOutputStream os = response.getOutputStream();
			ZipOutputStream out = new ZipOutputStream(os);   

			if(categoryIDs!=null){  //将内容分类信息导出成xml数据流，放入zip包中
				for(int i=0;i<categoryIDs.length;i++){
					NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryIDs[i]);
					if(category!=null){
						exporter.export(category);
						Query query = this.getRepository(request).getQueryManager().createQuery(category, Query.SQL);
						for (Iterator<Node> iterator = query.nodes(false); iterator.hasNext();) {
							Node node = iterator.next();
							exporter.export(node);
						}
					}
				}
				InputStream categoryIn = exporter.getStream();
				if(categoryIn!=null){
					zip(out,categoryIn,"category.xml");
					categoryIn.close();
				}
			}
			//  将模板文件放入zip包中
			String sitesPath = this.getSiteFactory(request).getBaseDirectory().getPath() + File.separator + template.getName();
			File siteFiles = new File(sitesPath);
			zip(out,siteFiles,template.getName());
			//  将版本文件放入zip包中
			String versionPath = this.getSiteFactory(request).getBaseDirectory().getPath() + "/ide/web/WEB-INF/version.txt";
			File versionFile = new File(versionPath);
			zip(out,versionFile,"version.txt");
			//  将模板信息生成xml文件放入zip包中
			zipTemplateXML(out,template, baseURL);
			out.flush();
			out.close();
			os.flush();
			os.close();
		}
		return null;
	}

	
	/**
	 * 将一个文件流以文件的形式压缩到一个zip包中
	 * @param out
	 * @param in
	 * @param fileName
	 * @throws Exception
	 */
	private void zip(ZipOutputStream out,InputStream  in,String fileName) throws Exception {
		ZipEntry e = new ZipEntry(fileName);
		out.putNextEntry(e);
		int b;
		while ( (b = in.read()) != -1) {
			out.write(b);   
		}
		out.closeEntry();
	}
	
	/**
	 * 递归压缩文件夹中所有的文件
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if(f.exists()){
			if (f.isDirectory()) {   
			     File[] fl = f.listFiles();   
			     out.putNextEntry(new ZipEntry(base + "/"));   
			     base = base.length() == 0 ? "" : base + "/";   
			     for (int i = 0; i < fl.length; i++) {
			    	 if(fl[i].getName().indexOf(".svn")==-1){     //过滤掉svn文件夹
			    		 zip(out, fl[i], base + fl[i].getName());
			    	 }	       
			     }
			   }
			   else { 
				   String filename = f.getName();
				   if((filename.indexOf(".old")==-1)&&(filename.indexOf(".delete")==-1)
					   &&(filename.indexOf(".bak")==-1)&&(filename.indexOf(".svn")==-1)){  //过滤掉bak文件
					   FileInputStream in = new FileInputStream(f);
					   if(in!=null){
						   zip(out,in,base);
						   in.close();
					   }  
				   }
			   }
		}
	}
	
	/**
	 * 导出一个模板及其所有未被删除的栏目信息到一个InputStream流中
	 * @param template
	 * @param baseURL
	 * @return InputStream
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private void zipTemplateXML(ZipOutputStream out,SiteTemplate template,URL baseURL) throws ParserConfigurationException, IOException {
		Document document = newDocument();
		OutputFormat outputFormat = new OutputFormat();
		ZipEntry e = new ZipEntry("template.xml");
		out.putNextEntry(e);
		XMLSerializer xs = new XMLSerializer(out, outputFormat);
		Element root = document.createElement("site");
		document.appendChild(root);
		root.setAttribute("base", baseURL.toString());
		if(template!=null){
			root.appendChild(renderTemplate(document,template));
		}
		xs.serialize(document);
		out.closeEntry();
	}

	/**
	 * 获取模板节点及其下属栏目信息
	 * @param document
	 * @param template
	 * @return
	 */
	private Element renderTemplate(Document document,SiteTemplate template){
		Element root = document.createElement("template");
		root.setAttribute("name", template.getName());
		root.setAttribute("displayName", template.getDisplayName());
		root.setAttribute("description", template.getDescription());
		root.setAttribute("local", template.getLocale().getLanguage());
		root.setAttribute("resolution", template.getResolution());
		return root;
	}
	
	private Document newDocument() throws ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		return document;
	}
	
}
