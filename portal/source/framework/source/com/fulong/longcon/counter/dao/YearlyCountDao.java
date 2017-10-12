package com.fulong.longcon.counter.dao;

import java.sql.SQLException;

import com.fulong.common.dao.Dao;


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

public interface YearlyCountDao
    extends Dao {
    /**
     * 获取计数值
     * @return long
     */
    public long loadCount(String name, int accessYear)throws SQLException;
}
