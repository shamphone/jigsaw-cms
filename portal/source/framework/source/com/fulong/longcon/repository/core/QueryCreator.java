package com.fulong.longcon.repository.core;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public interface QueryCreator {

    /**
     * 创建查询
     *
     * @param repository Repository
     * @return Query
     */
    public  Query createQuery(NodeDefinition node);


}