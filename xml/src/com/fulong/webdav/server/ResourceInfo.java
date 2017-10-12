package com.fulong.webdav.server;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;

/**
 * 对File对象的封装，以便在页面上展示
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public interface ResourceInfo {

    /**
     * 将资源输出到ostream
     * @param ostream OutputStream
     * @throws IOException
     */
    public InputStream read() throws IOException;

    /**
     * 从source中复制资源。
     * @param source ResourceInfo
     */
    public void copy(ResourceInfo resource) throws IOException;

    /**
     * 更新文件内容
     * @param is InputStream
     */
    public void write(InputStream is) throws IOException;

    /**
     * 更新文件内容
     * @param is InputStream
     * @param start long
     * @param length long
     */
    public void write(InputStream is, long start, long length) throws
        IOException;

    /**
     * 创建目录
     * @return boolean
     */
    public boolean mkdirs() throws
        IOException;

    /**
     * 删除文件，返回错误集合
     * @return Map <错误页面的path, 错误代码>
     */
    public boolean delete();
    
    /**
     * 转移到
     * @param dest
     * @return
     */
    public boolean move(ResourceInfo dest) throws IOException;

    /**
     * 是否目录
     * @return boolean
     */
    public boolean isDirectory();

    /**
     * 是否文件
     * @return boolean
     */
    public boolean isFile();

    /**
     * 获取readme文件的内容。readme文件内容定义在目录下的readme.txt文件中
     * @return String
     */
    public String getReadme();

    /**
     * 获取父资源
     * @return ResourceInfo
     */
    public ResourceInfo getParent();

    /**
     * 遍历获取所有子资源
     * @return Iterator
     */
    public Iterator<ResourceInfo> getChildren();

    /**
     * 是否保留文件，不让外部访问
     * @return boolean
     */
    public boolean isReserved();

    /**
     * 文件长度
     * @return long
     */
    public long getLength();

    /**
     * 文件类型
     * @return String
     */
    public String getContentType();

    /**
     * 设置文件类型
     * @param contentType String
     */
    public void setContentType(String contentType);

    /**
     * 是否物理存在
     * @return boolean
     */
    public boolean exists();

    /**
     * 路径
     * @return String
     */
    public abstract String getPath();

    /**
     * 创建者
     * @return Principal
     */
    public Principal getCreator();

    /**
     * 显示名称
     * @return String
     */
    public String getDisplayName();

    /**
     * 是否隐藏
     * @return boolean
     */
    public boolean isHidden();

    /**
     * 是否只读
     * @return boolean
     */
    public boolean isReadonly();

    /**
     * 最后访问时间
     * @return Date
     */
    public Date getLastAccessed();

    /**
     * 是否跟目录
     * @return boolean
     */
    public boolean isRoot();

    /**
     * 创建者
     * @param creator Principal
     */
    public void setCreator(Principal creator);

    /**
     * 名称
     * @return String
     */
    public String getName();

    /**
     * 最后访问时间
     * @return Date
     */
    public Date getLastModified();

    /**
     * 创建日期
     * @return Date
     */
    public Date getCreationDate();

}
