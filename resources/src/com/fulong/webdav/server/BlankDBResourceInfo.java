/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.webdav.server;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;

/**
 * BlankDBResourceInfo
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-10
 */
public class BlankDBResourceInfo extends DBResourceInfo{
	private Repository repository;
	private String name;
	private Node owner;
	private static String SCHEME="resource-scheme";
	
	public BlankDBResourceInfo(Repository repository, Node owner, String path) {
		super(repository,null);
		this.repository = repository;
		this.owner = owner;
		this.name = path;
	}

	public boolean delete() {
		return true;
	}

	public boolean isReserved() {
		return name.equalsIgnoreCase("/resources");
	}

	public String getPath() {
		return this.owner.getPath()+"/"+this.name;
	}

	public boolean isHidden() {
		return false;
	}

	public boolean isReadonly() {
		return false;
	}

	public Date getLastAccessed() {
		return new Date();
	}

	public InputStream getInputStream() throws IOException {
		return null;
	}

	/**
	 * 更新文件内容
	 * 
	 * @param is
	 *            InputStream
	 * @param start
	 *            long
	 * @param length
	 *            long
	 */
	public void write(InputStream is, long start, long length) throws IOException {
		this.write(is);

	}

	public long getLength() {
		return 0l;
	}

	/**
	 * 更新文件内容
	 * 
	 * @param is
	 *            InputStream
	 */
	public void write(InputStream is) throws IOException {
		Node resource = this.owner.addNode(this.repository.getDefinitionManager().getDefinition(SCHEME), this.name);
		resource.setProperty("resource-content", is);
		resource.setProperty("length", resource.getProperty("resource-content").getLength());
	}

	public String getContentType() {
		return null;
	}

	public boolean exists() {
		return false;
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return this.name;
	}

	public Date getLastModified() {
		return new Date();
		// return this.resource.getProperty("updateTime").getDate().getTime();
	}

	/**
	 * 获取readme文件的内容。readme文件内容定义在目录下的readme.txt文件中
	 * 
	 * @return String
	 */
	public String getReadme() {
		return "";
	}

	public void setContentType(String mimeType) {
		
	}

	public boolean isRoot() {
		return false;
	}

	public Principal getCreator() {
		return null;
	}

	public void setCreator(Principal creator) {
		
	}

	public InputStream read() throws IOException {
		return null;
	}

	public Date getCreationDate() {
		return null;
	}

	public void copy(ResourceInfo resource) throws IOException {
		DBResourceInfo dbres=(DBResourceInfo)resource;
		Node newNode = dbres.getNode().clone();
		newNode.setParent(owner);
		newNode.setName(name);
	}
	public boolean move(ResourceInfo resource) throws IOException {
		return false;
	}
	/**
	 * 新建目录
	 */
	public boolean mkdirs() throws IOException {
		Node node = this.owner.addNode(this.repository.getDefinitionManager().getDefinition(SCHEME), this.name);
		node.setProperty("createdTime", Calendar.getInstance());
		node.setProperty("isFolder", true);
		return true;
	}

	public boolean isDirectory() {
		return false;
	}

	public boolean isFile() {
		return false;

	}

	public ResourceInfo getParent() {
		return new DBResourceInfo(this.repository, this.owner);
	}

	public Iterator<ResourceInfo> getChildren() {
		return null;
	}


}
