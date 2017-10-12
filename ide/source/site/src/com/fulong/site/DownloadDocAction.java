package com.fulong.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.TagUtils;

import com.fulong.common.WordUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lichengzhao
 *
 * @version 2.0
 */
public class DownloadDocAction extends SiteBaseAction  {
	
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Node material = this.getRepository(request).getNode(id);
		String path = material.getProperty("petition").getString();
		if (path == null)
			return null;
		Node resource = this.getRepository(request).getNodeByPath(path);
		if (resource == null)
			return null;
		Property mime = resource.getProperty("mime");
		if (mime == null || !"application/msword".equalsIgnoreCase(mime.getString()))
			return null;
		File doc = File.createTempFile("word", ".doc");
		OutputStream output = null;
		InputStream input = null;
		try {
			output = FileUtils.openOutputStream(doc);
			input = resource.getProperty("resource-content").getStream();
			IOUtils.copy(input, output);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		// 获取有可能被填充的属性
		File processdDoc = null;
		Node user = this.getCurrentUser(request, response);
		if (user != null) {
			NodeDefinition def = user.getDefinition();
			Iterator<PropertyDefinition> pds = def.propertyDefinitions(false);
			PropertyDefinition pd = null;
			String value = null;
			Property prop = null;
			Map<String, String> map = new HashMap<String, String>();
			while (pds.hasNext()) {
				pd = pds.next();
				prop = user.getProperty(pd.getID());
				try {
					value = prop.getString();
				} catch (Exception e) {}
				if (value != null)
					map.put(pd.getName(), value);
			}
			WordUtils wordUtils = (WordUtils) this.getBeanFactory().getBean("wordUtils");
			try {
				processdDoc = wordUtils.replaceField(map, doc);
			} finally {
				wordUtils.recycle(doc);
			}
		}
		if (processdDoc == null)
			processdDoc = doc;
		int length = (int) processdDoc.length();
        if (length != 0) {
        	String oldContentType = response.getContentType();
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment;filename=" + TagUtils.getInstance().encodeURL(resource.getProperty("title").getString()));
	        response.setContentLength(length);
	        try {
		        output = response.getOutputStream();
		        input = FileUtils.openInputStream(processdDoc);
	            IOUtils.copy(input, output);
	            output.flush();
	        } catch (IOException ex) {
	        	response.setContentType(oldContentType);
	        	throw ex;
	        } finally {
	    		IOUtils.closeQuietly(input);
	    		IOUtils.closeQuietly(output);
	        }
        }
        return null;
	}
}
