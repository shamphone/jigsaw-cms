package com.fulong.longcon.site.dao;

import com.fulong.common.dao.Dao;
import java.sql.SQLException;
import com.fulong.longcon.site.data.ChannelData;
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
public interface ChannelDao
    extends Dao {

    /**
     *
     * @param ID String
     * @return ChannelData
     * @throws SQLException
     */
    public ChannelData findByID(String ID) throws SQLException;

    /**
     *
     * @param templateID String
     * @param ID String
     * @return ChannelData
     * @throws SQLException
     */
    public ChannelData findByTemplateAndID(String templateID, String ID) throws
        SQLException;

    /**
     *
     * @param templateID String
     * @return ChannelData[]
     * @throws SQLException
     */
    public ChannelData[] findByTemplate(String templateID) throws
        SQLException;

    /**
     *
     * @param templateID String
     * @param name String
     * @return ChannelData
     * @throws SQLException
     */
    public ChannelData findByTemplateAndName(String templateID, String name) throws
        SQLException;

    /**
     * 查询templateID的根栏目，即在channel中父栏目为空的栏目
     * @param templateID String
     * @return ChannelData
     * @throws SQLException
     */
    public ChannelData findRootChannelByTemplate(String templateID) throws
        SQLException;

    /**
     *
     * @param data ChannelData
     * @throws SQLException
     */
    public void insert(ChannelData data) throws SQLException;

    /**
     *
     * @param data ChannelData
     * @throws SQLException
     */
    public void update(ChannelData data) throws SQLException;

    /**
     *
     * @param ID String
     * @throws SQLException
     */
    public void delete(String ID) throws SQLException;

    /**
     *
     * @param templateID String
     * @param ID String
     * @throws SQLException
     */
    public void delete(String templateID, String ID) throws SQLException;

    /**
     *
     * @param query String
     * @param parameters SQLParameter[]
     * @param fromIndex int
     * @param number int
     * @return ChannelData[]
     * @throws SQLException
     */
    public ChannelData[] search(String query,
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


    public ChannelData findBindingChannel(String templateID, String nodeID, String type) throws
        SQLException;

}
