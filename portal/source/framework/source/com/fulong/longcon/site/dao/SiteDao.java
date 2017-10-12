package com.fulong.longcon.site.dao;

import java.sql.SQLException;

import com.fulong.common.dao.Dao;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.site.data.SiteData;

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
public interface SiteDao
    extends Dao {
    /**
     * 根据PKID=? or NAME=? or DOMAIN=?作最大匹配查询
     * @param ID String
     * @return SiteData
     * @throws SQLException
     */
    public SiteData findByID(String ID) throws SQLException;

    public SiteData findByOwner(String ownerID) throws SQLException;

    public int getSitesCount() throws SQLException;

    public int getSitesCountByTemplate(String templateID) throws SQLException;

    public int getSitesCountByCategory(String CategoryID) throws SQLException;

    public void insert(SiteData data) throws SQLException;

    public void update(SiteData data) throws SQLException;

    public void delete(String ID) throws SQLException;

    public SiteData[] search(String query,
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
