package com.fulong.longcon.repository.core;


import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public interface InternalNodeDefinition extends NodeDefinition {

        /**
         * 获取definition在属性定义列表中的序号
         * @param definition PropertyDefinition
         * @return int
         */
        public int getOrderNo(PropertyDefinition definition);
        /**
         * 如果父分类增加了个属性定义，则子分类中相应的也要增加。
         * @param definition
         */
        public void addInheritedPropertyDefinition(PropertyDefinition definition);


}
