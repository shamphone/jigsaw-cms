package com.fulong.longcon.resource.impl;

import java.io.InputStream;
import java.security.Principal;
import java.util.Date;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.resource.Resource;
import com.fulong.longcon.resource.ResourceCollection;
import com.fulong.longcon.resource.ResourceIterator;
import com.fulong.longcon.resource.ext.ResourceManagerExt;
import java.util.Calendar;

/**
 * <p>Title: 资源管理系统</p>
 *
 * <p>Description: 资源管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public class ResourceImpl1 extends NodeWrapper implements Resource {
    private ResourceManagerExt manager;
    private boolean existed;

    public ResourceImpl1(ResourceManagerExt manager,
                         Node node) {
        this(manager, true, node);
    }

    public ResourceImpl1(ResourceManagerExt manager,
                         boolean existed,
                         Node node) {
        super(node);
        this.manager = manager;
        this.existed = existed;
    }

    /**
     * 获取所在的文件夹或者父文件夹
     * @return Resource
     */
    public Resource getParent() {
        String parentPath = this.getProperty("parentPath").getString();
        if (parentPath != null) {
            return this.manager.getResource(parentPath);
        }
        parentPath = this.getParentPath(this.getPath());
        if (parentPath != null) {
            this.setProperty("parentPath", parentPath);
            return this.manager.getResource(parentPath);
        }
        return null;
    }

    public String getParentPath(String path) {
        path = this.format(path);
        int last = path.lastIndexOf("/");
        String parentPath = "";
        if (last > 0) {
            parentPath = path.substring(0, last);
        }
        return parentPath;
    }

    public String getParentPath() {
        return this.getNode().getProperty("parentPath").getString();
    }

    public String getFileName(String path) {
        path = this.format(path);
        int last = path.lastIndexOf("/");
        String fName = "";
        if (last > 0) {
            fName = path.substring(last + 1, path.length());
        }
        return fName;
    }

    /**
     * 是否隐藏文件（夹）
     * @return boolean
     */
    public boolean isHidden() {
        return this.getNode().getProperty("isHidden").getBoolean();

    }

    /**
     * 设置/取消隐藏
     * @param hidden boolean
     */
    public void setHidden(boolean hidden) {
        this.setProperty("isHidden", hidden);
    }

    /**
     * 是否只读
     * @return boolean
     */
    public boolean isReadOnly() {
        return this.getProperty("readOnly").getBoolean();
    }

    /**
     * 设置为只读
     * @param readOnly boolean
     */
    public void setReadOnly(boolean readOnly) {
        this.setProperty("readOnly", readOnly);
    }

    /**
     * 获取文件所有者
     * @return Principal
     */
    public Principal getOwner() {
        return (Principal)this.getParent();
    }

    /**
     *设置文件所有者
     * @param owner Principal
     */
    public void setOwner(Principal owner) {
        this.setParent((Node) owner);
    }

    /**
     * 文件创建者
     * @return Principal
     */
    public Principal getCreator() {
        return (Principal)this.getProperty("creator").getReference();
    }

    /**
     * 获取文件创建者
     * @param creator Principal
     */
    public void setCreator(Principal creator) {
        this.getNode().setProperty("creator", (Node) creator);
    }

    /**
     * 获取创建日期
     * @return Date
     */
    public Date getCreatedDate() {
        return this.getProperty("createTime").getDate().getTime();
    }

    /**
     * 获取最后修改日期。
     * @return Date
     */
    public Date getLastModifiedDate() {
        return this.getNode().getProperty("updateTime").getDate().getTime();
    }

    /**
     * 设置最后修改日期
     * @param date Date
     */
    public void setLastModifiedDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.getNode().getProperty("updateTime").setValue(c);
    }

    /**
     * 获取文件的长度，以字节计算
     * @return long
     */
    public long getLength() {
        return this.getNode().getProperty("length").getLong();
    }


    /**
     * 判断当前资源是否目录。
     * @return boolean
     */
    public boolean isDirectory() {
        return this.getNode().getProperty("isFolder").getBoolean();
    }

    /**
     * 判断当前资源是否文件。
     * @return boolean
     */
    public boolean isFile() {
        return!(this.isDirectory());
    }

    /**
     * 修改文件名。如果是文件夹，则所有该文件夹下的文件路径也发生变化。
     * @param destPath String
     * @return boolean
     */
    public boolean renameTo(String destPath) {
        destPath = this.format(destPath);
        if (destPath.charAt(0) != '/') { //表示当前传入的路径为相对对路径
            destPath = this.getParentPath() + "/" + destPath;
        }
        if (this.isDirectory()) {
            ResourceCollection rc = this.getChildren(true);
            ResourceIterator ri = rc.resources();
            String toReplace = this.getPath();
            while (ri.hasNext()) {
                Resource rs = ri.nextResource();
                ((ResourceImpl1) rs).setParentPath(rs.getParentPath().replace(
                        toReplace, destPath));
                ((ResourceImpl1) rs).setPath(rs.getPath().replace(toReplace,
                        destPath));
            }
        }
        String pPath = this.getParentPath(destPath);
        this.setParentPath(pPath);
        this.setPath(destPath);
        return true;
    }

    private void setPath(String path) {
        this.getNode().setProperty("path", path);
    }

    private void setParentPath(String path) {
        this.getNode().setProperty("parentPath", path);
    }

    /**
     * 测试文件是否存在
     * @return boolean
     */
    public boolean exists() {
        return this.existed;
    }

    /**
     * 获取所有子节点
     * @param recursive 是否包含子文件夹中的内容。
     * @return ResourceCollection
     */
    public ResourceCollection getChildren(boolean recursive) {
        ResourceCollectionImpl rc = new ResourceCollectionImpl(this.manager,
                recursive);
        rc.filterByParent(this.getPath(), recursive);
        return rc;
    }

    /**
     * 获取读取文件内容的句柄,使用后需要调用关闭操作close()。
     * @return InputStream
     */
    public InputStream read() {
        return this.getProperty("resource-content").getStream();

    }

    /**
     *
     * @param is InputStream
     */
    public void write(InputStream is) {
        this.setProperty("resource-content", is);
        this.setProperty("length",
                         this.getProperty("resource-content").getLength());
    }


    /**
     * 设置资源类型
     * @param mime String
     */
    public void setMime(String mime) {
        this.getNode().setProperty("mime", mime);
    }

    /**
     * 获得资源类型
     * @return String
     */
    public String getMime() {
        return this.getProperty("mime").getString();
    }

    public boolean equals(Object obj) {
        Resource another = (Resource) obj;
        return this.getPath().equals(another.getPath());
    }

    /**
     * 获取节点深度，根节点深度为1
     * @return int
     */
    public int getDepth() {
        int depth = 1;
        Resource resouce = this.getParent();
        while (resouce != null) {
            depth++;
            resouce = resouce.getParent();
        }
        return depth;
    }


    private String format(String path) {
        String result;
        result = path.replaceAll("\\\\", "/");
        result = path.replaceAll("//", "/");
        return result;
    }

    public String toString() {
        return this.getPath();
    }

	public int getNodeType(String defid) {
		return 0;
	}

	@Override
	public NodeIterator<Node> getAllNodes(String name) {
		return null;
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
		
	}

}
