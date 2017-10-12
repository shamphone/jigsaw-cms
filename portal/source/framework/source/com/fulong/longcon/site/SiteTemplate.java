package com.fulong.longcon.site;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
/**
 * 网站模板，定义网站结构。网站模板是由栏目组成的。网站模板中的栏目形成树状结构，提供对栏目结构的创建、修改、删除等管理功能以及访问和遍历功能。
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
public interface SiteTemplate {
	
	public static final String DEFAULT_RESOLUTION = "default";
    
  	
	/**
	 * 网站模板ID，即网站模板名称，兼容以前版本
	 * @return String
	 */
	public String getID();
	
    /**
     * 网站模板名称
     * @return String
     */
    public String getName();

    /**
     * 网站的显示名称,缺省的displayName等于name属性
     * @return String
     */
    public String getDisplayName();
   
    /**
     * 网站的显示分辨率
     * @return String
     */
    public String getResolution();
    
    /**
     * 修改网站显示名称
     * @param name String
     */
    public void setDisplayName(String name);

    /**
     * 修改网站显示分辨率
     * @param name String
     */
    public void setResolution(String resolution);       

    
     /**
      * 设置网站模板的语言类型
      * @param locale Locale
      */
     public void setLocale(Locale locale);

     /**
      * 获取网站模板的语言类型,缺省为中文
      * @return Locale
      */
     public Locale getLocale();
     /**
 	 * 将相对于网站的路径转成绝对路径
 	 * @param relative
 	 * @return
 	 */
 	public String getRealPath(String contextPath);
 	/**
	 * 将路径转成相对路径
	 * @param relative
	 * @return
	 */
	public String getContextPath(String relative);
    /**
     * 获取对本类型的描述
     * @return String
     */
    public String getDescription();

    /**
     * 类别描述
     * @param description String
     */
    public void setDescription(String description);

      
    /**
     * 获取当前模板对应的目录
     * @return File
     */
    public SiteFolder getRootFolder();      
    /**
     * 获取当前模板对应的目录
     * @return File
     */
    public SiteFolder getFolder(String contextPath);      
    /**
     * 获得指定ID的栏目
     * @param contextPath 栏目的路径
     * @return Channel, 没有找到对应的channel，则返回null
     */
    public Channel getChannel(String contextPath);   

	/**
	 * 将当前网站模板克隆到目标模板
	 * @param destTemplate 目标模板
	 * @throws IOException
	 */
	public void clone(SiteTemplate destTemplate) throws IOException;

	/**
	 * 获取模板的所有栏目
	 */
	
	public Iterator<Channel> getChannels();
	/**
	 * @return
	 */
	public SiteFactory getSiteFactory();
}
