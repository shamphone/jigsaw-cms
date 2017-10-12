/**
 * 
 */
package com.fulong.longcon.site.channel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import com.fulong.common.FileUtils;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.ChannelType;
import com.fulong.longcon.site.SiteFolder;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.longcon.site.State;

/**
 * <p>
 * Title: GeneralChannel
 * </p>
 * <p>
 * Description: Coolink协同工作支撑平台
 * </p>
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixf
 * @date 2010-5-12
 */
public abstract class GeneralChannel implements Channel {
	protected SiteTemplate template;
	protected File file;
	protected File working;
	private String checkoutIP;
	private Date checkoutTime;
	private String name;

	// private Collection<NodeDefinition> binding;

	public GeneralChannel() {
	}
	
	public String getID(){
		return this.getContextPath();
	}

	@Override
	public void create() throws IOException {
		// TODO Auto-generated method stub

	}
	@Override
	public SiteFolder getFolder() {
		 File folder = this.file.getParentFile();
		 return this.template.getFolder(folder.getPath());
	}
	/**
	 * 初始化
	 * 
	 * @param template
	 * @param page
	 */
	public void init(SiteTemplate template, File page) {
		this.template = template;
		this.file = page;
		this.checkoutIP = null;
		this.checkoutTime = null;
		this.name = FilenameUtils.getBaseName(page.getName());
	}

	/**
	 * 子类通过这个方法实现初始化
	 */
	protected void doInit() {

	}

	@Override
	public void delete() throws IOException {
		this.template = null;
		this.checkoutIP = null;
		this.checkoutTime = null;
		this.name = null;
		if (this.file.exists())
			this.file.delete();
		if (this.working.exists())
			this.working.delete();

	}

	/**
	 * 栏目创建时间
	 * 
	 * @return Date
	 */
	public Date getCreatedDate() {
		return new Date(this.file.lastModified());
	}

	/**
	 * 栏目名称，用来在网站内唯一定位该栏目的。栏目名称在网站内是唯一的
	 * 
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 栏目所在的网站模板
	 * 
	 * @return Site
	 */
	public SiteTemplate getSiteTemplate() {
		return this.template;
	}

	/**
	 * 获取模板设置
	 * 
	 * @return String
	 */
	public File getPage() {
		return this.file;
	}

	/**
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof GeneralChannel)) {
			return false;
		}
		GeneralChannel another =(GeneralChannel)obj;
		return another.file.equals(this.file);

	}

	/**
	 * 栏目类型，主要用来定义地址的产生方式
	 * 
	 * @return ChannelType
	 */
	public ChannelType getChannelType() {
		return this.template.getSiteFactory().getChannelType(this.getType());
	}

	/**
	 * 发布路径
	 */
	@Override
	public String getContextPath() {
		return this.template.getContextPath(this.file.getPath());
	}

	/**
	 * 工作路径
	 */
	@Override
	public String getWorkingPath() {
		return this.getContextPath() + ".bak";
	}

	/*
	 * 正在修改
	 */
	@Override
	public boolean isWriting() {
		return this.checkoutIP != null;
	}

	@Override
	public String getWriter() {
		return this.checkoutIP;
	}

	@Override
	public Date getLastWritingTime() {
		return this.checkoutTime;
	}

	@Override
	public boolean checkin(String IP) {
		if (this.checkoutIP == null)
			return false;
		this.checkoutIP = null;
		this.checkoutTime = null;
		return true;
	}

	@Override
	public boolean checkin(String IP, InputStream content) throws IOException {
		this.submit(IP, content);
		this.checkoutIP = null;
		this.checkoutTime = null;
		return true;
	}

	@Override
	public boolean submit(String IP, InputStream content) throws IOException {
		if (this.checkoutIP == null)
			return false;
		if (this.working == null)
			return false;
		FileUtils.write(this.working, content);
		return true;
	}
	@Override
	public boolean checkout(String IP) throws IOException {
		this.checkoutIP = IP;
		this.checkoutTime = new Date();

		// 创建工作副本；
		this.working = new File(this.file.getPath() + ".bak");
		if (!working.exists())
			FileUtils.copyFile(file, working);

		// 如果.jsp.bak的最后修改时间早于.jsp文件，则用.jsp的内容覆盖.jsp.bak
//		if (file.lastModified() > working.lastModified())
//			FileUtils.copyFile(file, working);
		return true;
	}

	/**
	 * @throws IOException
	 */
	public boolean publish() throws IOException {
		if (!file.exists())
			file.createNewFile();
		// backup current file;
		File backup = new File(this.file.getPath() + ".old");
		if (!backup.exists())
			backup.createNewFile();
		FileUtils.copyFile(file, backup);
		// copy working file to current file;
		if (working.exists())
			FileUtils.copyFile(working, file);
		return true;
	}
	
	/**
	 * 最后的修改是否已经发布；
	 * @return
	 * @throws IOException
	 */
	public boolean isPublished() {
		if(working == null)
			return true;
		if(!working.exists())
			return true;
		if(file.lastModified()>working.lastModified())
			return true;
		return false;
	}
	
	/**
	 * 兼容老版本
	 * @return
	 * @throws IOException
	 */
	public State getState() {
		if(this.isPublished())
			return State.PUBLISHED; 
		return State.EDITING;
	}

	/**
	 * @throws IOException
	 */
	public boolean restore() throws IOException {
		if (!this.working.exists())
			return false;
		FileUtils.copyFile(file, working);
		working.setLastModified(new Date().getTime());
		return true;
	}
	
	public NodeDefinition getBindingNode(){
		return null;
	}
}
