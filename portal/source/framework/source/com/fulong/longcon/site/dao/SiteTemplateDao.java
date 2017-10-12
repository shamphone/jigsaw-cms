package com.fulong.longcon.site.dao;

import com.fulong.common.dao.Dao;
import java.sql.SQLException;
import com.fulong.longcon.site.data.SiteTemplateData;
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
public interface SiteTemplateDao
    extends Dao {

    /**
     *
     * @param ID String
     * @return SiteTemplateData
     * @throws SQLException
     */
    public SiteTemplateData findByID(String ID) throws SQLException;

    /**
     * 支持通过PKID或者NAME查询
     * @param name String
     * @return SiteTemplateData
     * @throws SQLException
     */
    public SiteTemplateData findByName(String name) throws SQLException;

    /**
     *
     * @param data SiteTemplateData
     * @throws SQLException
     */
    public void insert(SiteTemplateData data) throws SQLException;

    /**
     *
     * @param data SiteTemplateData
     * @throws SQLException
     */
    public void update(SiteTemplateData data) throws SQLException;

    /**
     * 按照分类计数
     * @param categoryID String
     * @return int
     * @throws SQLException
     */
    public int getCountbyCategory(String categoryID) throws SQLException;

    /**
     * 做删除标记，实际数据不从数据库移除
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
     * @return SiteTemplateData[]
     * @throws SQLException
     */
    public SiteTemplateData[] search(String query,
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
