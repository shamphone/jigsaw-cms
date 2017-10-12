package com.fulong.webdav.server;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Repository;

/**
 * DBResourceInfo
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-6
 */
public class DBResourceInfo implements ResourceInfo {
	private Repository repository;
	private Node resource;
	
	public DBResourceInfo(Repository repository, Node resource) {
		this.repository = repository;
		this.resource = resource;
	}

	public boolean delete() {
		return this.repository.delete(this.resource);
	}

	public boolean isReserved() {
		return this.resource.getName().equalsIgnoreCase("resources");
	}

	public String getPath() {
		return this.resource.getPath();
	}
	
	public Node getNode(){
		return this.resource;
	}

	public boolean isHidden() {
		if (this.resource != null) {
			Property property = this.resource.getProperty("isHidden");
			if (property != null)
				return property.getBoolean();
		}
		return false;
	}

	public boolean isReadonly() {
		if (this.resource != null) {
			Property property = this.resource.getProperty("isReadonly");
			if (property != null)
				return property.getBoolean();
		}
		return false;
	}

	public Date getLastAccessed() {
		if (this.resource != null) {
			Property property = this.resource.getProperty("lastAccessed");
			if (property != null)
				return property.getDate().getTime();
		}
		return new Date();
	}
	public boolean move(ResourceInfo resource) throws IOException {
		DBResourceInfo dbres=(DBResourceInfo)resource;
		Node parent = ((DBResourceInfo)dbres.getParent()).getNode();
		String name = 	((DBResourceInfo)dbres).getName();
		this.resource.setParent(parent);
		this.resource.setName(name);
		return true;
	}
	public InputStream getInputStream() throws IOException {
		return this.resource.getProperty("resource-content").getStream();
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
		return this.resource.getProperty("resource-content").getLength();
	}

	/**
	 * 更新文件内容
	 * 
	 * @param is
	 *            InputStream
	 */
	public void write(InputStream is) throws IOException {
		this.resource.setProperty("resource-content", is);
		this.resource.setProperty("length", this.resource.getProperty("resource-content").getLength());
	}

	public String getContentType() {
		return this.resource.getProperty("mime").getString();
	}

	public boolean exists() {
		 return true;
	}

	public String getName() {
		if (this.resource.getName() == null)
			return "new folder";
		return this.resource.getName();
	}

	public String getDisplayName() {
		return this.resource.getName();
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
		this.resource.setProperty("mime", mimeType);
	}

	public boolean isRoot() {
		return (this.resource.getParent() == null) || (this.resource.getParent().getParent() == null);
	}

	public Principal getCreator() {
		return (Principal) this.resource.getProperty("creatorID").getReference();
	}

	public void setCreator(Principal creator) {
		this.resource.setProperty("creatorID", (Node) creator);
	}

	public InputStream read() throws IOException {
		return this.resource.getProperty("resource-content").getStream();
	}

	public Date getCreationDate() {
		Property property=this.resource.getProperty("createdTime");
		if(property!=null)
			if(property.getDate()!=null)
			return property.getDate().getTime();
		return new Date();
	}

	public void copy(ResourceInfo resource) throws IOException {
	}

	public boolean mkdirs() throws IOException {	
		return true;
	}

	public boolean isDirectory() {
		if (this.resource != null)
			return this.resource.getProperty("isFolder").getBoolean();
		return false;
	}

	public boolean isFile() {
		if (this.resource != null)
			return !this.resource.getProperty("isFolder").getBoolean();
		return false;

	}

	public ResourceInfo getParent() {
		return new DBResourceInfo(this.repository, this.resource.getParent());
	}

	public Iterator<ResourceInfo> getChildren() {
		return new IteratorWrapper(this.resource);
	}
}

/**
 * 将NodeIterator转换成ResourceInfo的Iterator;
* IteratorWrapper
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-6-11
*/
class IteratorWrapper implements Iterator<ResourceInfo> {
	private Node node;
	private NodeIterator<Node> iterator;

	public IteratorWrapper(Node node) {
		this.node = node;
		this.iterator = node.getNodes();
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public ResourceInfo next() {
		return new DBResourceInfo(node.getRepository(), this.iterator.nextNode());
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
