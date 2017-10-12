package com.fulong.longcon.workflow.dao;

import com.fulong.common.dao.Dao;
import java.sql.SQLException;

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
public interface DefinitionProcessDao extends Dao {

    public void insert(String definitionID, String processID) throws
        SQLException;

    public void update(String definitionID, String processID) throws
        SQLException;

    public String findByDefinitionID(String id) throws SQLException;

}
