package com.fulong.longcon.resource;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;

/**
 * 资源管理器
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
public interface ResourceManager {
    /**
     * 根据路径或者资源ID获取资源对象。如果文件已经存在，则获取实体对象，如果不存在，则return null;
     * @param filePath String 资源的绝对路径
     * @return Resource
     */
    public Resource getResource(String filePath);

    /**
     * 获取系统的根目录
     * @return Resource
     */
    public Resource getRootResource();


    /**
     * 当前owner的根目录，如果目录不存在，则直接创建目录
     * @param owner Principal
     * @return Resource
     * @throws IOException
     * @throws SQLException 
     */
    public Resource getRootResource(Principal owner) throws IOException, SQLException ;

    /**
     * 根据父目录和文件名获取资源对象
     * @param child  String 资源的路径，相对或者绝对
     * @param parent Resource 基准路径。
     * @return Resource
     */
    public Resource getResource(Resource parent, String child);

    /**
     * 复制文件
     * @param source 源文件
     * @param dest 目的文件
     * @throws IOException
     */
    public void copyFile(Resource source, Resource dest) throws IOException;

    /**
     * source（不包括本身）下的内容（文件和文件夹）拷贝到dest下
     * 复制目录。目录下的子目录，文件同时复制
     * @param source 源目录
     * @param dest 目的目录
     * @throws IOException
     * @throws SQLException 
     */

    public void copyDirectory(Resource source, Resource dest) throws
            IOException, SQLException;


    /**
    * 创建目录
    * @param path String
    * @return Resource
     * @throws SQLException 
    * @throws IOException如果该路径上对应的目录或者文件已经存在。
    */
   public Resource makeDir(String path) throws  IOException, SQLException;

   /**
    * 创建目录，如果其父目录不存在，则同时也创建父目录
    *  including any necessary but nonexistent parent directories.
    * @return boolean
 * @throws SQLException 
    */

   public Resource mkdirs(String path) throws  IOException, SQLException;


   /**
    * 创建文件，如果文件对应的目录不存在，则同时也创建相关的目录。
    * @param path String
    * @return Resource
 * @throws SQLException 
    * @throws IOException如果该路径上对应的目录或者文件已经存在。
    */
   public Resource createNewFile(String path) throws  IOException, SQLException;

   /**
    * 如果folder为空，则抛出异常
    * @param folder Resource
    * @param name String
    * @return Resource
    * @throws IOException
 * @throws SQLException 
    */
   public Resource createNewFile(Resource folder, String name) throws IOException, SQLException;

   /**
    * 删除文件或者目录。如果是删除目录，则要求这个目录为空。
    * @param resource Resource
    * @return boolean
    */
   public boolean delete(Resource resource);

   /**
    * 删除文件或者目录。
    * @param resource Resource
    * @return boolean
    */
   public boolean deleteTree(Resource resource);


}
