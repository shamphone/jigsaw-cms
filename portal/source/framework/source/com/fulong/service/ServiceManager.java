package com.fulong.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;




/**
 * 服务模型管理的总入口，提供对服务类别和服务的管理
 * <p>Title: Coolink平台服务模型管理系统</p>
 *
 * <p>Description: Coolink平台服务管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author liuzijun
 * @author lixf
 * @version 3.1
 */
public interface ServiceManager {	
	
    /**
     * 获取某个服务
     * @param ID 服务ID
     * @return Service
     */
    public Service getService(String ID);
    
    /**
     * 获取服务配置
     * @param ID
     * @return
     */
    public ServiceConfig getServiceConfig(String ID);
   
	/**
	 * 获取所有服务类别的名称
	 * @param id
	 * @return
	 */
	public ServiceContext getServiceContext(String id);
	
	/**
	 * 获取指定contextPath下的服务类别
	 * @param contextPath
	 * @return
	 */
	public Collection<ServiceContext> getModuleServiceContexts(String Module);
	
	/**
	 * 获取已注册的所有服务类别
	 * @return
	 */
	public Collection<ServiceContext> getServiceContexts();
  /**
   * 从指定配置文件中加载服务
   * @param file
   * @throws IOException
   */
	public void load(ServletContext module) throws IOException;
	
}
