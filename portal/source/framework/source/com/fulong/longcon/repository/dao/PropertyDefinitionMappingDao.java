package com.fulong.longcon.repository.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.repository.data.MappingData;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class PropertyDefinitionMappingDao extends JdbcDao {
  
    /**
     * 插入新结点
     * @param data NodeData
     * @throws SQLException
     */
    private static final String SQL_INSERT =
            "insert into propertymapping(sproid,snodeid,dproid,dnodeid,remotesite ) VALUES (?,?,?,?,?)";
    public void insert(MappingData data) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, data.getSPropertyID());
            command.setString(2, data.getSDefinitionID());
            command.setString(3, data.getDPropertyID());
            command.setString(4, data.getDDefinitionID());
            command.setString(5, data.getRemoteSite());
            command.executeUpdate();
        } finally {
            this.close(command);
        }

    }


    private static final String SQL_DELETE =
            "delete from propertymapping where sproid=? and snodeid=? and dproid=? and dnodeid=? and remotesite=?";
    public void remove(MappingData data) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE);
            command.setString(1, data.getSPropertyID());
            command.setString(2, data.getSDefinitionID());
            command.setString(3, data.getDPropertyID());
            command.setString(4, data.getDDefinitionID());
            command.setString(5, data.getRemoteSite());
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }


    private static final String SQL_DELETE_BY_REMOTEPRO =
            "delete from propertymapping where remotesite=? and dnodeid=? and  dproid=?";
    public void remove(String website, String nodeDefinition,
                       String propertyDefinition) throws SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_DELETE_BY_REMOTEPRO);
            command.setString(1, website);
            command.setString(2, nodeDefinition);
            command.setString(3, propertyDefinition);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }


    private static final String SQL_SELECT =
            "select * from propertymapping where dnodeid=? and remotesite=? and snodeid=? and type=?";
    public MappingData[] findBySiteAndDef(String remoteDef, String site,
                                          String localDef, int type) throws
            SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_SELECT);
            command.setString(1, remoteDef);
            command.setString(2, site);
            command.setString(3, localDef);
            command.setInt(4, type);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
        return null;

    }


 
}

