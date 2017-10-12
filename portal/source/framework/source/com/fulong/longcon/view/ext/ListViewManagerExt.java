package com.fulong.longcon.view.ext;

import com.fulong.common.dao.DaoFactory;
import com.fulong.longcon.view.ListViewManager;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public interface ListViewManagerExt extends ListViewManager {

    /**
     * 数据访问设置
     * @return DaoFactory
     */
    public DaoFactory getDaoFactory();

}
