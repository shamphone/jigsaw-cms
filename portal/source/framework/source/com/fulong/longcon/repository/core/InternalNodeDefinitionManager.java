package com.fulong.longcon.repository.core;

import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.NodeDefinitionManager;
import com.fulong.longcon.repository.ValidatorParser;
import com.fulong.longcon.repository.ValueFactory;

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
public interface InternalNodeDefinitionManager extends NodeDefinitionManager {
    /**
      * 获取数据库联接
      * @return JdbcDaoFactory
      */
    public JdbcDaoFactory newJdbcDaoFactory();
    /**
     * 获取值定义库
     * @return ValueFactory
     */
    public ValueFactory getValueFactory();
    /**
     * 解析验证器
     * @return ValidatorParser
     */
    public ValidatorParser getValidatorParser();
    
  
}
