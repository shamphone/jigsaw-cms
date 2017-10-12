package com.fulong.longcon.resource.dao;

import java.sql.SQLException;

import com.fulong.common.dao.Dao;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.resource.data.ResourceData;

/**
 *
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public interface ResourceDao
    extends Dao {
    /**
     * 向数据库中插入一条记录，不插入BLOB, DESCRIPTION
     * @param data ResourceData
     * @throws SQLException
     */
    public void insert(ResourceData data) throws SQLException;

    /**
     * 向数据库中插入BLOB
     * @param path 主键
     * @param in InputStream
     * @throws SQLException
     * @throws FileNotFoundException
     */
    /**
     public long insertBlob(String path, InputStream in) throws
        SQLException, FileNotFoundException;
     */
    /**
     * 修改.此处不修改BLOB,DESCRIPTION字段
     * @param data ResourceData
     * @throws SQLException
     */
    public void update(ResourceData data) throws SQLException;

    /**
     * 修改content即BLOB字段
     * @param data ResourceData
     * @param in InputStream
     * @throws SQLException
     */
    /** public long updateContent(ResourceData data, InputStream in) throws
         SQLException;
     */
    /**
     * 修改DESCRIPTION字段
     * @param data ResourceData
     * @throws SQLException
     */
    public void updateDescription(ResourceData data) throws SQLException;

    /**
     * 修改主键
     * @param data ResourceData
     * @param oldPath String
     * @throws SQLException
     */
  //  public void update(ResourceData data, String oldPath) throws SQLException;

    /**
     * 删除
     * @param path String
     * @throws SQLException
     */
    public void delete(String path) throws SQLException;

    /**
     * 删除path以及path以下的资源
     * @param path String
     * @throws SQLException
     */
    public void deleteTree(String path) throws SQLException;

    /**
     * 查找
     * @param path String
     * @return ResourceData
     * @throws SQLException
     */
    public ResourceData getByPath(String path) throws SQLException;

    /**
     * 查询
     * @param query String
     * @param parameters SQLParameter[]
     * @param fromIndex int
     * @param number int
     * @return ResourceData[]
     * @throws SQLException
     */
    public ResourceData[] search(String query,
                                 SQLParameter[] parameters,
                                 int fromIndex,
                                 int number) throws
        SQLException;

    /**
     * 统计
     * @param query String
     * @param parameters SQLParameter[]
     * @return long
     * @throws SQLException
     */
    public long countResultNum(String query, SQLParameter[] parameters) throws
        SQLException;

    /**
     * 查询blob字段
     * @param data ResourceData
     * @return InputStream[]
     * @throws SQLException
     */
    // public InputStream getBlob(String path) throws SQLException;

    /**
     * 查找DESCRIPTION字段
     * @param data ResourceData
     * @return String[]
     * @throws SQLException
     */
    public String[] getDescription(ResourceData data) throws SQLException;


}
