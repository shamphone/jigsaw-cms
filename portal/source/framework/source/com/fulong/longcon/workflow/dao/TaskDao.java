package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.Dao;
import java.sql.SQLException;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.workflow.data.TaskData;

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
public interface TaskDao extends Dao {
    /**
     *
     * @param data ContentData
     * @throws SQLException
     */
    public void insert(TaskData data) throws SQLException;

    /**
     *
     * @param data ContentData
     * @throws SQLException
     */
    public void update(TaskData data) throws SQLException;

    /**
     *
     * @param contentID String
     * @param categoryID String
     * @throws SQLException
     */
    public void delete(String contentID, String categoryID) throws SQLException;

    /**
     *
     * @param id String
     * @return ContentTaskData
     * @throws SQLException
     */
    public TaskData findByID(String id) throws SQLException;

    /**
     *
     * @param contentId String
     * @param categoryId String
     * @return ContentTaskData
     * @throws SQLException
     */
    public TaskData getCurrentTask(String contentId, String categoryId) throws
        SQLException;

    /**
     *
     * @param query String
     * @param parameters SQLParameter[]
     * @return ContentTaskData[]
     * @throws SQLException
     */
    public TaskData[] search(String query, SQLParameter[] parameters) throws
        SQLException;

    /**
     *
     * @param query String
     * @param parameters SQLParameter[]
     * @return int
     * @throws SQLException
     */
    public int countResultNum(String query, SQLParameter[] parameters) throws
        SQLException;

}
