package com.fulong.longcon.site;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Repository;

/**
 * 网站工厂,网站的总入口,提供对网站和域名的管理.
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 李雄锋
 * @version 1.0
 */
public interface SiteFactory {
	public static final String DEFAULTEMPLATEID = "default";

	/**
	 * 存放模板的基本目录
	 * 
	 * @return
	 */
	public File getBaseDirectory();

	/**
	 * 创建网站模板，同时建立网站模板的根栏目
	 * 
	 * @param name
	 *            名称
	 * @param category
	 *            所属类别
	 * @return SiteTemplate
	 * @throws IOException
	 **/
	public SiteTemplate createTemplate(String name) throws IOException;
	/**
	 * 
	 * 将已有的模板添加到管理目录下
	 * 
	 * @param name
	 *            名称
	 * @param category
	 *            所属类别
	 * @return SiteTemplate
	 * @throws IOException
	 **/
	public SiteTemplate registerTemplate(String name, File path) throws IOException;


	/**
	 * 获取指定ID的网站模板
	 * 
	 * @param name   String 支持通过ID或者NAME查询
	 * 
	 * @return SiteTemplate
	 */
	public SiteTemplate getTemplate(String name);


	/**
	 * 获取所有的网站模板
	 * 
	 * @return SiteTemplateCollection
	 */
	public Iterator<String> getTemplateNames();

	/**
	 * 取默认模板 name = default
	 * 
	 * @return
	 */
	public SiteTemplate getDefaultTemplate();

	/**
	 * 获取所有的栏目类型
	 * 
	 * @return Iterator
	 */
	public Enumeration<ChannelType> channelTypes();

	/**
	 * 获取指定名称的栏目类型
	 * 
	 * @param name
	 *            String
	 * @return ChannelType
	 */
	public ChannelType getChannelType(String name);

	/**
	 * 检查域名的注册数量是否已经达到限额,返回还可以使用的限额;
	 * 
	 * @return 已经达到限额
	 */
	public int checkQuota();

	/**
	 * 给定的域名是否是预留域名。在配置文件中定义预留域名
	 * 
	 * @param domain
	 *            String
	 * @return boolean
	 * 
	 */
	public boolean isReservedDomain(String domain);

	/**
	 * 注册网站域名。
	 * 
	 * @param domain
	 *            String 域名
	 * @param owner
	 *            Principal 域名所有者
	 * @return Site
	 * @throws SiteExistsException
	 *             如果owner已经建立网站，或者domain已经被占用
	 */
	public Site createSite(String domain, Principal owner) throws SiteExistsException;

	/**
	 * 删除域名，同时删除网站的所有数据。注意: 这里是物理删除。
	 * 在应用中，可以将网站标记为删除状态来避免误删除操作。参见Site.setState();
	 * 
	 * @param site
	 *            要删除的网站域名
	 */
	public void delete(Site site);

	/**
	 * 根据ID或者二级域名来查找域名
	 * 
	 * @param site
	 *            String 域名的ID、二级域名或者一级域名
	 * @return SiteDomain
	 */
	public Site getSite(String site);

	/**
	 * 获取所有的已注册域名
	 * 
	 * @return SiteCollection
	 * */
	 
	public NodeIterator<Node> getSites();
	
	/**
     * 获取所有的模板分辨率
     */
    public List<String> getResolutions();
    
    /**
     * 删除模板
     * @throws IOException 
     */
    public void deleteTemplate(SiteTemplate template) throws IOException;
    
    /**
	 * 内容库的总入口
	 * 
	 * @return Repository
	 */
	public Repository getRepository();
}
