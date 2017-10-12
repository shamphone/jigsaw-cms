package com.fulong.longcon.resource.impl;

import java.io.InputStream;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Date;

import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.resource.Resource;
import com.fulong.longcon.resource.ResourceCollection;
import com.fulong.longcon.resource.ResourceIterator;
import com.fulong.longcon.resource.dao.ResourceDao;
import com.fulong.longcon.resource.data.ResourceData;
import com.fulong.longcon.resource.ext.ResourceManagerExt;

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
public class ResourceImpl
    extends NodeWrapper implements Resource {
    private ResourceManagerExt manager;
    private ResourceData data;
    private boolean existed;

    public ResourceImpl(ResourceManagerExt manager, ResourceData data, Node node) {
        this(manager, data, true, node);
    }

    public ResourceImpl(ResourceManagerExt manager,
                        ResourceData data,
                        boolean existed,
        Node node) {
        super(node);
        this.manager = manager;
        this.data = data;
        this.existed = existed;
    }

    /**
     * 获取文件名称，即getPath（）方法得到的路径的最后一项
     * @return String
     */
    public String getName() {
        if (this.data.getName() != null) {
            return this.data.getName();
        }
        String path = this.getPath();
        int last = path.lastIndexOf("/");
        String name = "";
        if (last > 0) {
            name = path.substring(last + 1, path.length());
        }
        return name;

    }

    /**
     * 获取资源文件的绝对路径，以"/"开始，从根目录开始算。
     * @return String
     */
    public String getPath() {
        return this.data.getPath();
    }

    /**
     * 获取所在的文件夹或者父文件夹
     * @return Resource
     */
    public Resource getParent() {
        String parentPath = this.data.getParentPath();
        if (parentPath != null) {
            return this.manager.getResource(parentPath);
        }
        parentPath = this.getParentPath(this.data.getPath());
        if (parentPath != null) {
            this.data.setParentPath(parentPath);
            this.save();
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
        return this.data.getParentPath();
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
        return this.data.isHidden();
    }

    /**
     * 设置/取消隐藏
     * @param hidden boolean
     */
    public void setHidden(boolean hidden) {
        this.data.setHidden(hidden);
        this.save();
    }

    /**
     * 是否只读
     * @return boolean
     */
    public boolean isReadOnly() {
        return this.data.isReadOnly();
    }

    /**
     * 设置为只读
     * @param readOnly boolean
     */
    public void setReadOnly(boolean readOnly) {
        this.data.setReadOnly(readOnly);
        this.save();
    }


    /**
     *设置文件所有者
     * @param owner Principal
     */
    public void setOwner(Principal owner) {
        this.data.setOwnerID(owner.getName());
        this.save();
    }

    /**
     * 文件创建者
     * @return Principal
     * @throws SQLException 
     */
    public Principal getCreator() throws SQLException {
        return this.manager.getPassport().getUser(this.data.getCreatorID());
    }

    /**
     * 获取文件创建者
     * @param creator Principal
     */
    public void setCreator(Principal creator) {
        this.data.setCreatorID(creator.getName());
        this.save();
    }

    /**
     * 获取创建日期
     * @return Date
     */
    public Date getCreatedDate() {
        return this.data.getCreationTime();
    }

    /**
     * 获取最后修改日期。
     * @return Date
     */
    public Date getLastModifiedDate() {
        if(this.data.getUpdateTime()==null)
            return new Date();
        return this.data.getUpdateTime();
    }

    /**
     * 设置最后修改日期
     * @param date Date
     */
    public void setLastModifiedDate(Date date) {
        this.data.setUpdateTime(date);
        this.save();
    }

    /**
     * 获取文件的长度，以字节计算
     * @return long
     */
    public long getLength() {
        return data.getLength();
    }

    /**
     * 创建目录
     * @return boolean
     * @throws ItemExistsException 如果该路径上对应的目录或者文件已经存在。

    private boolean makeDir() throws ItemExistsException {
        Node node = this.getNode("resource-root").addNode("resource-scheme");
        Resource rs = this.manager.getResource(this.data.getPath());
        if (rs.exists()) {
            throw new ItemExistsException();
        }

        DaoFactory factory = this.manager.newDaoFactory();
        try {
            factory.open();
            ResourceDao dao = (ResourceDao) factory.getDao(ResourceDao.class);
            data.setPkid(node.getID());
            data.setCreationTime(new Date());
            data.setCreatorID(this.manager.getPassport().getDefaultOrganization().
                              getId());
            data.setIsFolder(true);
            data.setParentPath(this.getParentPath(this.data.getPath()));
            dao.insert(this.data);
            this.existed = true;
            return true;
        }
        catch (SQLException se) {
            factory.rollback();
            throw new DatabaseException(se);
        }
        finally {
            factory.close();
        }
    }
*/
    /**
     * 删除文件或者目录。如果是删除目录，则要求这个目录为空。
     * @return boolean 删除成功

    private boolean delete() {
        Resource rs = this.manager.getResource(this.data.getPath());
        if (rs.isDirectory()) {
            ResourceCollection rc = this.getChildren(true);
            if (rc.resources().getSize() > 0) {
                return false;
            }
        }
        DaoFactory factory = this.manager.newDaoFactory();
        try {
            factory.open();
            ResourceDao dao = (ResourceDao) factory.getDao(ResourceDao.class);
            dao.delete(this.data.getPath());
            this.manager.getRepository().delete(this.getNode(data.getPkid()));
            return true;
        }
        catch (SQLException se) {
            factory.rollback();
            throw new DatabaseException(se);
        }
        finally {
            factory.close();
        }
    }
 */
    /**
     * 判断当前资源是否目录。
     * @return boolean
     */
    public boolean isDirectory() {
        if (this.exists()) {
            return this.data.isIsFolder();
        }
        else {
            return false;
        }
    }

    /**
     * 判断当前资源是否文件。
     * @return boolean
     */
    public boolean isFile() {
        if (this.exists()) {
            return! (this.isDirectory());
        }
        else {
            return false;
        }
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
                ( (ResourceImpl) rs).setParentPath(rs.getParentPath().replace(
                    toReplace, destPath));
                ( (ResourceImpl) rs).setPath(rs.getPath().replace(toReplace,
                    destPath));
            }
        }
        String pPath = this.getParentPath(destPath);
        this.setParentPath(pPath);
        this.setPath(destPath);
        return true;
    }

    private void setPath(String path) {
        this.data.setPath(path);
        this.save();
    }

    private void setParentPath(String path) {
        this.data.setParentPath(path);
        this.save();
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
        //return this.manager.getRepository().getNode(this.data.getPkid()).
          //  getProperty("resource-content").getStream();
          return this.getProperty("resource-content").getStream();

    }

    /**
     *
     * @param is InputStream
     */
    public void write(InputStream is) {
        this.setProperty("resource-content", is);
        this.data.setLength(this.getProperty("resource-content").getLength());
        DaoFactory factory = this.manager.newDaoFactory();
        try {
            factory.open();
            ResourceDao dao = (ResourceDao) factory.getDao(ResourceDao.class);
            dao.update(this.data);
        }
        catch (SQLException se) {
            factory.rollback();
            throw new DatabaseException(se);
        }
        finally {
            factory.close();
        }
    }

    private void save() {
        DaoFactory factory = this.manager.newDaoFactory();
        try {
            factory.open();
            ResourceDao dao = (ResourceDao) factory.getDao(ResourceDao.class);
            dao.update(this.data);
        }
        catch (SQLException se) {
            factory.rollback();
            throw new DatabaseException(se);
        }
        finally {
            factory.close();
        }
    }

    /**
     * 设置资源类型
     * @param mime String
     */
    public void setMime(String mime) {
        this.data.setMime(mime);
        this.save();
    }

    /**
     * 获得资源类型
     * @return String
     */
    public String getMime() {
        return this.data.getMime();
    }

    public boolean equals(Object obj) {
        Resource another = (Resource) obj;
        return this.data.getPath().equalsIgnoreCase(another.getPath());
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


    private String format(String path){
       String result;
       result = path.replaceAll("\\\\", "/");
       result = path.replaceAll("//", "/");
       return result;
   }

   public String toString(){
       return this.data.getPath();
   }

public int getNodeType(String defid) {
	return 0;
}

@Override
public NodeIterator<Node> getAllNodes(String name) {
	return null;
}

@Override
public Principal getOwner() throws SQLException {
	return null;
}

@Override
public void setMaxOrderNo(int orderNo) {
	
}

}
