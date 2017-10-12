package com.fulong.longcon.repository.core;

import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.data.NodeData;

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
public interface InternalRepository extends Repository{
    /**
     * 获取数据库联接
     * @return JdbcDaoFactory
     */
    public JdbcDaoFactory newJdbcDaoFactory();


    /**
     * 获取验证解析器
     * @return ValidatorParser
     */
    public ValidatorParser getValidatorParser();

    /**
     * 在节点从数据库中加载时调用，在服务中对这个节点进行再封装，返回新节点。如果不需要处理，则直接返回。
     * @param node Node
     * @return Node
     */
    public Node makeNode(NodeData node);
    
  

}
