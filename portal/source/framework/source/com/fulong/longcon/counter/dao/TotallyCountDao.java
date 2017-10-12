package com.fulong.longcon.counter.dao;

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
 * @author lishaobo@fulong.com.cn
 * @version 1.0
 */

public interface TotallyCountDao
    extends Dao {
    /**
     * 获取计数值
     * @return long
     */
    public long loadCount(String name)throws SQLException;
    /**
     * 将计数值保存到数据库中
     * @param newValue long
     */
    public void saveCount(String name,long newValue)throws SQLException;

    /**
     * 插入一条记录
     * @param name String
     */
    public void insertCount(String name)throws SQLException;
}
