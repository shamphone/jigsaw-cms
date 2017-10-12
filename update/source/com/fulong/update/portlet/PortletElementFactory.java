package com.fulong.update.portlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.ResourceUtils;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-11-11	
 * @version 1.0.1
 */

public abstract class PortletElementFactory {
	private static final String path = "com/fulong/update/portlet/portletElement.properties";
	private static final Constructor<PortletElement> constructor;
	private static final Log log = LogFactory.getLog(PortletElementFactory.class);
	static {
		constructor = loadConstructor(path);
		constructor.setAccessible(true);
	}
	
	@SuppressWarnings("unchecked")
	private static Constructor<PortletElement> loadConstructor (String path){
		Properties properties = new Properties();
		String portletElementClass = null;
		Class clazz = null;
		try {
			InputStream in = ResourceUtils.getResourceAsStream(path);
			properties.load(in);
			portletElementClass = properties.getProperty("portletElement");
			if(portletElementClass==null){
				clazz = DefaultPortletElement.class;
			}else{
				try {
					clazz = (Class<PortletElement>) Class.forName(portletElementClass);
				} catch (ClassNotFoundException e) {
					String msg = "Class '"+portletElementClass+"' not exist.\n"+e.getMessage();
					log.error(msg, e);
					throw new IllegalArgumentException(msg,e);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		Constructor<PortletElement> constructor = null;
		if(clazz!=null){
			try {
				constructor = clazz.getConstructor(String.class,File.class);
			} catch (SecurityException e) {
				log.error(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				log.error("can't find such constructor.", e);
			}
		}
		
		if(constructor==null){
			throw new IllegalStateException("can't load portletElement class from config file.");
		}
		return constructor;
	}
	
	public static PortletElement getPortletElement (String xml,File file){
		if(constructor==null){
			throw new IllegalStateException("can't create PortletElement because constructor equals null.");
		}
		try {
			return (PortletElement) constructor.newInstance(xml,file);
		}  catch (Exception e) {
			String msg = "instantiation constructor error.";
			log.error(msg,e);
			throw new IllegalStateException(msg);
		}
	}
	
}
