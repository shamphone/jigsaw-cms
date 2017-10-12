package com.fulong.webdav.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;

/**
 * <p>Title: 龙驭电子商务系统</p>
 *
 * <p>Description: 龙驭电子商务系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
public class NodeDefinitionInfo
    implements ResourceInfo {
    private Repository repository;
   private NodeDefinition category;
   private URL baseURL;
    public NodeDefinitionInfo(URL baseURL,Repository repository, NodeDefinition category) {
    	
        this.repository=repository;
        this.category=category;
        this.baseURL=baseURL;
    }

    /**
     *
     * @param resource ResourceInfo
     * @throws IOException
     */
    public void copy(ResourceInfo resource) throws IOException {
        if(resource.isFile())
            throw new IOException("Unable to copy content to content category.");
        /**
         * @todo do copy category.
         */
        }

        /**
         *
         * @return boolean
         */
        public boolean delete() {
         this.repository.getDefinitionManager().delete(category);
         return true;
    }

    /**
     *
     * @return boolean
     */
    public boolean exists() {
        return true;
    }


    /**
     *
     * @return Iterator
     */
    public Iterator<ResourceInfo> getChildren() {
        Vector<ResourceInfo> result=new Vector<ResourceInfo>();
        for(Iterator<NodeDefinition> children=this.category.getInheritDefinitions();children.hasNext();){
        	NodeDefinition child=children.next();
            result.add(new NodeDefinitionInfo(this.baseURL, repository,child));
        }
        Query query = this.repository.getQueryManager().createQuery(this.category, Query.SQL);
        query.sortByOrdinal(false);
        int count = 0;
        for(Iterator<Node> iterator=query.nodes(false);iterator.hasNext()&&(count<256);count++){            
            NodeInfo node = new NodeInfo(baseURL, repository);
            node.setNode(iterator.next(), this.category);            
            result.add(node);
        }
        return result.iterator();
    }

    /**
     *
     * @return String
     */
    public String getContentType() {
        return "";
    }

    /**
     *
     * @return Principal
     */
    public Principal getCreator() {
        return null;
    }

    /**
     *
     * @return String
     */
    public String getDisplayName() {
        return this.category.getName();
    }

    /**
     * getInputStream
     *
     * @return InputStream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        return null;
    }

    /**
     *
     * @return Date
     * @todo Implement this com.fulong.webdav.ResourceInfo method
     */
    public Date getLastAccessed() {
        return null;
    }

    /**
     *
     * @return Date
     */
    public Date getLastModified() {
    	   Date time = this.category.getCreateDate();
           if(time == null)
           	time = new Date();
           return time;
    }

    /**
     *
     * @return long
     */
    public long getLength() {
        return 0L;
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return this.category.getName();
    }

    /**
     *
     * @return ResourceInfo
     */
    public ResourceInfo getParent() {
        if(this.category.getSuperDefinition()==null)
            return null;
        return new NodeDefinitionInfo(this.baseURL, repository,category.getSuperDefinition());
    }

    /**
     *
     * @return String
     */
    public String getPath() {
        return "/xml/"+this.category.getID();
    }

    /**
     *
     * @return String
     */
    public String getReadme() {
        return null;
    }

    /**
     *
     * @return boolean
     */
    public boolean isDirectory() {
        return true;
    }

    /**
     *
     * @return boolean
     */
    public boolean isFile() {
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean isHidden() {
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean isReadonly() {
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean isReserved() {
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean isRoot() {
        return this.category.getSuperDefinition()==null;
    }

    /**
     *
     * @return boolean
     * @throws IOException
     */
    public boolean mkdirs() throws IOException {
        return false;
    }

    /**
     *
     * @param contentType String
     */
    public void setContentType(String contentType) {
    }

    /**
     *
     * @param creator Principal
     */
    public void setCreator(Principal creator) {
    }

    /**
     *
     * @param is InputStream
     * @param start long
     * @param length long
     * @throws IOException
     */
    public void write(InputStream is, long start, long length) throws
        IOException {
    }

    /**
     *
     * @param is InputStream
     * @throws IOException
     */
    public void write(InputStream is) throws IOException {
    }

    public InputStream read() throws IOException {
        return null;
    }

    public Date getCreationDate() {
        Date time = this.category.getCreateDate();
        if(time == null)
        	time = new Date();
        return time;
    }

	@Override
	public boolean move(ResourceInfo dest) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
}
