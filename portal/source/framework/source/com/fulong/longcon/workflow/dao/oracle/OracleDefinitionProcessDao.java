package com.fulong.longcon.workflow.dao.oracle;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fulong.common.dao.JdbcDao;
import com.fulong.longcon.workflow.dao.DefinitionProcessDao;

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
public class OracleDefinitionProcessDao extends JdbcDao implements DefinitionProcessDao{

    /**
     * 插入属性定义值
     * @param data NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_INSERT =
        "insert into nodedef_processdef (node_definition, process_definition) VALUES (?,?)";
    public void insert(String definitionID, String processID) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_INSERT);
            command.setString(1, definitionID);
            command.setString(2, processID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     *更新数据
     * @param data NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_UPDATE =
        "update nodedef_processdef set process_definition =? where node_definition=?";
    public void update(String definitionID, String processID) throws
        SQLException {
        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_UPDATE);
            command.setString(1, processID);
            command.setString(2, definitionID);
            command.executeUpdate();
        } finally {
            this.close(command);
        }
    }

    /**
     * 根据ID来查询属性定义,注意：为了保持数据完整，做了删除标记的定义。
     * @param id String
     * @return NodeDefinitionData
     * @throws SQLException
     */
    private static final String SQL_FIND =
        "select t.process_definition from nodedef_processdef t where t.node_definition=?";
    public String findByDefinitionID(String id) throws SQLException {

        PreparedStatement command = null;
        try {
            command = connection.prepareStatement(SQL_FIND);
            command.setString(1, id);
            return this.queryString(command);
        } finally {
            this.close(command);
        }
    }

}
