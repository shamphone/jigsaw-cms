package com.fulong.longcon.site;

import com.fulong.longcon.repository.Node;

/**
 * 是显示模型的总入口，通过它来获取和创建网站模型、网站以及网站类别。
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
public interface Site{   
	
	/**
	 * 网站的id
	 */
	public String getID();
    /**
     * 网站的域名，
     * @return String
     */
    public String getDomain();
    /**
     * 网站显示名称
     * @return
     */
    public String getDisplayName();

    /**
     * 域名所有者，即租户，可以为个人（如个人博客），可以为机构。
     * @return Principal
     */
    
    public Node getOwner(); 

    /**
     * 获取所使用的网站模板
     * @return Site
     */
    public SiteTemplate getTemplate();

    /**
     * 获取所使用的网站模板
     * @return Site
     */
    public String[] getTemplates();

    /**
     * 更新所使用的网站模板
     * @param template SiteTemplate
     */
    public void setTemplates(String[] templates);

}
