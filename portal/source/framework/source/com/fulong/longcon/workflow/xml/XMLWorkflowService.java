/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.fulong.common.FileUtils;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.workflow.ApplicationManager;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.longcon.workflow.WorkflowException;
import com.fulong.longcon.workflow.WorkflowService;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class XMLWorkflowService implements WorkflowService {
	/**
	 * 存放配置文件的文件夹
	 */
	private File folder;
	private Resource templateFile;
	private final String postfix = ".bpml.xml";
	private Map<String, ProcessDefinition> definitions = new HashMap<String, ProcessDefinition>();
	private ApplicationManager applications;
	private static Log log = LogFactory.getLog(XMLWorkflowService.class);

	public XMLWorkflowService() {
	}

	public void setFolder(Resource folder) throws IOException {
		this.folder = folder.getFile();
	}

	public void setTemplateFile(Resource file) throws IOException {
		this.templateFile = file;
	}

	/**
	 * 启动的时候加载系统中所有的流程到缓存中
	 */
	public void init() {
		log.info("WorkflowService ready.");
	}

	/**
	 * 返回所有的过程定义；
	 */
	public Iterator<ProcessDefinition> definitions() {
		Vector<ProcessDefinition> result = new Vector<ProcessDefinition>(
				this.definitions.values());
		Collections.sort(result, new NameComparator());
		return result.iterator();

	}

	/**
	 * 获取指定标识的过程定义
	 */
	public ProcessDefinition getDefinition(String id) {
		if (id == null) {
			return null;
		}
		return this.definitions.get(id);
	}

	/**
	 * 创建一个空的工作流
	 * 
	 * @throws IOException
	 */
	public ProcessDefinition create(String id,SiteTemplate template) {
		if (this.definitions.get(id) != null)
			throw new IllegalArgumentException("process.definition.id.exists");
		try {
			File folder = new File(template.getRealPath("/WEB-INF/workflow"));
			System.out.println("processdefinition:"+folder.getPath());
			//判断该应用是否存在workflow文件夹
			if(!folder.exists())
				folder.mkdirs();
			File file = new File(folder, id + postfix);
			file.createNewFile();
			FileUtils.copy(this.templateFile.getInputStream(), file);
			XMLProcessDefinition definition = new XMLProcessDefinition(file,this);
			definition.setId(id);
			definition.setModule(template.getName());
			definition.save();
			this.definitions.put(id, definition);
			return definition;
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}

	}

	public void delete(ProcessDefinition definition) {
		this.definitions.remove(definition.getId());
		File file = new File(this.folder, definition.getId() + postfix);
		File dest = new File(this.folder, definition.getId() + "."
				+ (System.currentTimeMillis() % 1000) + ".delete.xml");
		file.renameTo(dest);

	}

	public void setApplicationManager(ApplicationManager manager) {
		this.applications = manager;
	}

	public ApplicationManager getApplicationManager() {
		return this.applications;
	}

	public void load(ServletContext servletContext) {
		File folder = new File(servletContext.getRealPath("/WEB-INF/workflow"));
		System.out.println("load.....:"+folder.getPath());
		final String postfix = ".bpml.xml";
		if (folder.exists()) {
			File[] files = folder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(postfix);
				}
			});
			if (files != null){
				for (int i = 0; i < files.length; i++) {
					try {
						ProcessDefinition definition = new XMLProcessDefinition(
								files[i], this);
						this.definitions.put(definition.getId(), definition);
						log.info("load process definition '"
								+ definition.getName() + "' from "
								+ files[i].getPath());
					} catch (Exception ex) {
						log.error("Error in load definition from file "
								+ files[i].getPath(), ex);
					}
				}
			}
		}
	}

	@Override
	public Iterator<ProcessDefinition> getModuleDefinitions(String module) {
		List<ProcessDefinition> result = new ArrayList<ProcessDefinition>();
		for(ProcessDefinition definition:this.definitions.values()){
			if(definition.getModule().equals(module)){
				result.add(definition);
			}
		}
		return result.iterator();
	}
}