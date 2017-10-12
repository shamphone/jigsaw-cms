package com.fulong.longcon.site.dao;

import com.fulong.common.dao.Dao;
import java.sql.SQLException;
import com.fulong.longcon.site.data.SiteCategoryData;
import com.fulong.common.dao.SQLParameter;

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
public interface SiteCategoryDao
    extends Dao {
    /**
     * 根据ID查找
     * @param ID String
     * @return SiteCategoryData
     * @throws SQLException
     */
    public SiteCategoryData findByID(String ID) throws SQLException;

    /**
     * 所有分类
     * @return SiteCategoryData[]
     * @throws SQLException
     */
    public SiteCategoryData[] getAllCategories() throws SQLException;

    /**
     * 插入一条记录
     * @param data SiteCategoryData
     * @throws SQLException
     */
    public void insert(SiteCategoryData data) throws SQLException;

    /**
     * 修改
     * @param data SiteCategoryData
     * @throws SQLException
     */
    public void update(SiteCategoryData data) throws SQLException;

    /**
     * 删除,如果该分类
     * @param ID String
     * @throws SQLException
     */
    public void delete(String ID) throws SQLException;

    /**
     *
     * @param query String
     * @param parameters SQLParameter[]
     * @param fromIndex int
     * @param number int
     * @return SiteCategoryData[]
     * @throws SQLException
     */
    public SiteCategoryData[] search(String query,
                                     SQLParameter[] parameters,
                                     int fromIndex,
                                     int number) throws
        SQLException;

    /**
     *
     * @param query String
     * @param parameters SQLParameter[]
     * @return long
     * @throws SQLException
     */
    public long countResultNum(String query, SQLParameter[] parameters) throws
        SQLException;

}
