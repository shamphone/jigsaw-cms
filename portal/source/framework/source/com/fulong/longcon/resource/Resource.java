package com.fulong.longcon.resource;

import java.io.InputStream;
import java.util.Date;
import com.fulong.longcon.repository.Node;
import java.security.Principal;
import java.sql.SQLException;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public interface Resource
    extends Node {
    /**
     * 获取文件名称，即getPath（）方法得到的路径的最后一项
     * @return String
     */
    public String getName();

    /**
     * 获取资源文件的绝对路径，以"/"开始，从根目录开始算。
     * @return String
     */
    public String getPath();

    /**
     *
     * @return String
     */
    public String getParentPath();

    /**
     * 获取所在的文件夹或者父文件夹
     * @return Resource
     */
    public Resource getParent();

    /**
     * 是否隐藏文件（夹）
     * @return boolean
     */
    public boolean isHidden();

    /**
     * 设置/取消隐藏
     * @param hidden boolean
     */
    public void setHidden(boolean hidden);

    /**
     * 是否只读
     * @return boolean
     */
    public boolean isReadOnly();

    /**
     * 设置为只读
     * @param readOnly boolean
     */
    public void setReadOnly(boolean readOnly);

    /**
     * 获取文件所有者
     * @return Principal
     * @throws SQLException 
     */
    public Principal getOwner() throws SQLException;

    /**
     *设置文件所有者
     * @param owner Principal
     */
    public void setOwner(Principal owner);

    /**
     * 文件创建者
     * @return Principal
     * @throws SQLException 
     */
    public Principal getCreator() throws SQLException;

    /**
     * 获取文件创建者
     * @param creator Principal
     */
    public void setCreator(Principal creator);

    /**
     * 获取创建日期
     * @return Date
     */
    public Date getCreatedDate();

    /**
     * 获取最后修改日期。
     * @return Date
     */
    public Date getLastModifiedDate();

    /**
     * 设置最后修改日期
     * @param date Date
     */
    public void setLastModifiedDate(Date date);

    /**
     * 获取文件的长度，以字节计算
     * @return long
     */
    public long getLength();

    /**
     * 判断当前资源是否目录。
     * @return boolean
     */
    public boolean isDirectory();

    /**
     * 判断当前资源是否文件。
     * @return boolean
     */
    public boolean isFile();

    /**
     * 修改文件名。如果是文件夹，则所有该文件夹下的文件路径也发生变化。
     * 以“/”开始的参数表示绝对路径，否则为相对当前资源的相对路径
     * @param dest Resource
     * @return boolean
     */
    public boolean renameTo(String destPath);

    /**
     * 测试文件是否存在
     * @return boolean
     */
    public boolean exists();

    /**
     * 获取所有子节点
     * @param recursive 是否包含子文件夹中的内容。
     * @return ResourceCollection
     */
    public ResourceCollection getChildren(boolean recursive);

    /**
     * 获取读取文件内容的句柄。
     * @return InputStream
     */
    public InputStream read();

    /**
     * 写文件。
     * @return OutputStream
     */
    public void write(InputStream is);

    /**
     * 设置资源类型
     * @param mime String
     */
    public void setMime(String mime);

    /**
     * 获得资源类型
     * @return String
     */
    public String getMime();

    /**
     * 截取文件名
     * @param path String
     * @return String
     */
    public String getFileName(String path);

    /**
     * 截取父文件名
     * @param path String
     * @return String
     */
    public String getParentPath(String path);

    /**
     * 获取节点深度，根节点深度为1
     * @return int
     */
    public int getDepth();

}
