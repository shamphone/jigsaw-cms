package com.fulong.longcon.site.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;

import com.fulong.common.DesEncrypter;
import com.fulong.common.util.Enumerator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.ChannelType;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteExistsException;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;

/**
 * 网站工厂,网站的总入口,提供对网站和域名的管理.
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭建站系统核心引擎
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
 * @author lixf,lishaobo
 * @version 1.0
 */
public class SiteFactoryImpl implements SiteFactory {
	private static final Log log = LogFactory.getLog(SiteFactoryImpl.class);
	private String reservedDomains;
	private String encMaxSiteNum;
	private int siteQuota;
	private static final String DES_KEY = "fulong";
	private String defaultDomain;
	private Map<String, ChannelType> channelTypes;
	private Map<String, SiteTemplate> loadedTemplates;
	private Repository repository;
	private String[] excludes;// 排除的目录；
	private File directory; // 文件目录
	// private Query query;
	NodeDefinition siteScheme;
	private List<String> resolutions;

	public void init() {
		/**
		 * 初始的时候计算出该系统的最大建站数量 在统计还有多少建站指标的时候使用
		 */
		DesEncrypter encrypter = new DesEncrypter(DES_KEY);
		String EncMaxSiteNum = this.getEncMaxSiteNum();
		String maxSiteNum = encrypter.decrypt(EncMaxSiteNum);
		siteQuota = Integer.parseInt(maxSiteNum);
		// this.siteCache = this.cacheFactory.getCache(Site.class);
		this.loadedTemplates = new Hashtable<String, SiteTemplate>();
		siteScheme = this.repository.getDefinitionManager().getDefinition(
				"site-scheme");

	}

	/**
	 * 加载栏目类型
	 * 
	 * @param types
	 *            List
	 */
	@SuppressWarnings("unchecked")
	public void setChannelTypeFile(Resource resource) {
		XmlBeanFactory beans = new XmlBeanFactory(resource);
		this.channelTypes = beans.getBeansOfType(ChannelType.class);
	}

	/**
	 * 内容库的总入口
	 * 
	 * @return Repository
	 */
	public Repository getRepository() {
		return this.repository;
	}

	/**
	 * 
	 * @param repository
	 */
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public List<String> getResolutions() {
		return resolutions;
	}

	public void setResolutions(List<String> resolutions) {
		this.resolutions = resolutions;
	}

	/**
	 * @param reservedDomains
	 */
	public void setReservedDomains(String reservedDomains) {
		this.reservedDomains = reservedDomains;
	}

	/**
	 * @param encMaxSiteNum
	 */
	public void setEncMaxSiteNum(String encMaxSiteNum) {
		this.encMaxSiteNum = encMaxSiteNum;
	}

	/**
	 * @param defaultDomain
	 */
	public void setDefaultDomain(String defaultDomain) {
		this.defaultDomain = defaultDomain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFactory#getBaseDirectory()
	 */
	public File getBaseDirectory() {
		return this.directory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFactory#getBaseDirectory()
	 */
	public void setDirectory(Resource directory) throws IOException {
		this.directory = directory.getFile();
	}

	public void setDirectory(File directory) throws IOException {
		this.directory = directory;
	}

	/**
	 * @return
	 */
	public String getEncMaxSiteNum() {
		return encMaxSiteNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFactory#getBaseDirectory()
	 */
	public void setExcludes(String excludes) {
		this.excludes = excludes.split(",");
		Arrays.sort(this.excludes);
	}

	/**
	 * @return
	 */
	public String getDefaultDomain() {
		return defaultDomain;
	}

	/**
	 * 给定的域名是否是预留域名。在配置文件中定义预留域名
	 * 
	 * @param domain
	 *            String
	 * @return boolean
	 * 
	 */
	public boolean isReservedDomain(String domain) {
		String[] domainsCollection = reservedDomains.split(",");
		for (int i = 0; i < domainsCollection.length; i++)
			if (domainsCollection[i].equals(domain))
				return true;
		return false;
	}

	/**
	 * 注册网站域名。
	 * 
	 * @param domain
	 *            String 域名
	 * @param owner
	 *            Principal 域名所有者
	 * @param template
	 *            String 网站模板名称
	 * @return Site
	 * @throws SiteExistsException
	 *             如果owner已经建立网站，或者domain已经被占用
	 */
	public Site createSite(String domain, Principal owner)
			throws SiteExistsException {
		if (this.getSite(domain) != null)
			throw new SiteExistsException();
		// 获得租户节点
		Node creater = this.getRepository().getNode(owner.getName());
		// 创建租户节点的site节点,site暂定为租户节点site符合属性id
		Node s = creater.addNode(this.siteScheme, "site");
		s.setProperty("domain", domain);
		s.setProperty("owner", creater);
		Site site = new SiteImpl(this, s);
		return site;
	}

	/**
	 * 删除域名，同时删除网站的所有数据。这里是物理删除。在应用中，可以将网站标记为删除状态来避免误删除操作。参见Site.setState();
	 * 
	 * @param site
	 *            要删除的网站域名
	 */
	public void delete(Site site) {
		this.getRepository().delete(this.getRepository().getNode(site.getID()));
	}

	/**
	 * 方法输入的参数可以是ID、domain、name 根据ID或者二级域名来查找域名
	 * 
	 * @param site
	 *            String 域名的ID、二级域名或者一级域名
	 * @return SiteDomain
	 */
	public Site getSite(String name) {
		Node site = this.getRepository().getNode(name);
		if (site == null) {
			Query query = this.repository.getQueryManager().createQuery(
					siteScheme, Query.SQL);
			query.filterByProperty("domain", name);
			NodeIterator<Node> sites = query.nodes();
			if (sites.getSize() == 1)
				site = sites.nextNode();
		}
		if (site != null)
			return new SiteImpl(this, site);
		return null;
	}

	/**
	 * 
	 * @return Enumeration
	 */
	public Enumeration<ChannelType> channelTypes() {
		return new Enumerator<ChannelType>(this.channelTypes.values());
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return ChannelType
	 */
	public ChannelType getChannelType(String name) {
		return (ChannelType) this.channelTypes.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFactory#getDefaultTemplate()
	 */
	public SiteTemplate getDefaultTemplate() {
		return getTemplate(SiteFactory.DEFAULTEMPLATEID);
	}

	public int checkQuota() {
		Query query = this.repository.getQueryManager().createQuery(siteScheme,
				Query.SQL);
		NodeIterator<Node> sites = query.nodes();
		return siteQuota - (int) sites.getSize();
	}

	public NodeIterator<Node> getSites() {
		Query query = this.repository.getQueryManager().createQuery(siteScheme,
				Query.SQL);
		return query.nodes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fulong.longcon.site.SiteFactory#getTemplateNames()
	 */
	public Iterator<String> getTemplateNames() {
		final Set<String> names = new HashSet<String>();
		this.directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if ((!ArrayUtils.contains(excludes, name))
						&& !name.startsWith(".")) {
					if (new File(dir, name + "/WEB-INF").exists())
						names.add(name);
				}
				return false;
			}
		});
		names.addAll(this.loadedTemplates.keySet());
		return names.iterator();

	}

	/**
	 * 获取指定ID的网站模板
	 * 
	 * @param name
	 *            String
	 * @return SiteTemplate
	 */
	public SiteTemplate getTemplate(String name) {
		SiteTemplate template = this.loadedTemplates.get(name);
		if (template != null)
			return template;
		File file = new File(this.directory, name);
		if (!file.exists()) {
			this.loadedTemplates.remove(name);
			return null;
		}
		if (!file.isDirectory())
			return null;
		template = new SiteTemplateImpl(this, name);
		log.info("Template[" + (this.loadedTemplates.size() + 1) + "]:" + name
				+ " loaded from " + file.getPath() + ".");
		// 将新创建的网站模版添加到缓存中；注意，如果模板很多（>1000，而且栏目多），则这种做法会引起系统内存耗尽；
		this.loadedTemplates.put(name, template);
		return template;

	}

	/**
	 * 创建网站，同时建立网站的根栏目
	 * 
	 * @param displayName
	 *            显示名称
	 * @param category
	 *            所属类别
	 * @return SiteTemplate
	 * @throws IOException
	 **/
	public SiteTemplate createTemplate(String name) throws IOException {
		File newFolder = new File(this.directory, name);
		if (newFolder.exists())
			throw new IllegalArgumentException("Site template with name "
					+ name + " exists.");
		FileUtils.copyDirectory(this.getDefaultTemplate().getRootFolder()
				.getFile(), newFolder, new FileFilter() {
			public boolean accept(File file) {
				if (!file.getName().startsWith(".")) {
					return true;
				}
				return false;
			}
		});
		return this.getTemplate(name);
	}

	/**
	 * 创建网站
	 * 解压zip模板文件
	 * InputStream inputStream
	 * @return SiteTemplate
	 * @throws Exception
	 * @throws IOException
	 **/
	public SiteTemplate createTemplate(InputStream inputStream)
			throws Exception {
		return this.getTemplate(com.fulong.common.FileUtils.unzip(this.directory, inputStream , true));
	}

	public SiteTemplate registerTemplate(String name, File folder)
			throws IOException {
		SiteTemplate template = new SiteTemplateImpl(this, name, folder);
		log.info("Template[" + (this.loadedTemplates.size() + 1) + "]-" + name
				+ " loaded from " + folder.getPath() + ".");
		// 将新创建的网站模版添加到缓存中；注意，如果模板很多（>1000，而且栏目多），则这种做法会引起系统内存耗尽；
		this.loadedTemplates.put(name, template);
		// 在正式运行时未配置directory参数，则从缺省网站模板中自动加载这个参数；
		if (this.directory == null) {
			this.directory = folder.getParentFile();
			/*if(this.directory.getName()!=null&&this.directory.getName().equals("web")){
				this.directory = this.directory.getParentFile();  
			}*/
		}
		return template;
	}

	public void deleteTemplate(SiteTemplate template) throws IOException {
		java.text.DateFormat format = new java.text.SimpleDateFormat(
				"yyyyMMddhhmmss");
		SiteFolder folder = template.getRootFolder();
		File deletedFolder = new File(folder.getFile().getParentFile(), "."
				+ folder.getName() + format.format(new Date()));
		if (deletedFolder.exists()) {
			FileUtils.deleteDirectory(folder.getFile());
		}
		// 在此使用renameTo方法通常不成功，无法进行更名操作，故用以下方法代替
		FileUtils.copyDirectory(folder.getFile(), deletedFolder);
		FileUtils.deleteDirectory(folder.getFile());
		this.loadedTemplates.remove(template.getName());
	}

	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("D:/coolinknew/a.zip");
		SiteFactoryImpl si = new SiteFactoryImpl();
		si.setDirectory(new File("D:/"));
		try {
			System.out.println(com.fulong.common.FileUtils.unzip(si.directory, fis , true));
			//si.unzip(new File("D:/coolinknew/a.zip"), "D:/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
