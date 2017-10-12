package com.fulong.longcon.system.xml;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.fulong.longcon.system.Configuration;
import com.fulong.longcon.system.Module;

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
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class XMLConfiguration implements Configuration {
	private Properties properties;
	private Map<String, Module> modules;

	public XMLConfiguration() {
		this.modules = new LinkedHashMap<String, Module>();

	}

	public void setProperty(String name, String value) {
		this.properties.put(name, value);
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@SuppressWarnings("unchecked")
	public void setModules(List modules) {
		for (Iterator iterator = modules.iterator(); iterator.hasNext();) {
			XMLModule module = (XMLModule) iterator.next();
			this.modules.put(module.getID(), module);
		}
	}

	public String getProperty(String name) {
		return this.properties.getProperty(name);
	}

	public void addModule(Module module) {
		this.modules.put(module.getID(), module);
	}

	public void init() {

	}

	public Module[] getActiveModules(String position) {
		Vector<XMLModule> result = new Vector<XMLModule>();
		for (Iterator<Module> iterator = this.modules.values().iterator(); iterator.hasNext();) {
			XMLModule module = (XMLModule) iterator.next();
			if (module.getPosition().equalsIgnoreCase(position) && module.isActive()) {
				result.add(module);
			}
		}
		
		return (Module[]) result.toArray(new Module[result.size()]);
	}

	public Module getModule(String id) {
		return (Module) this.modules.get(id);
	}
}
