package com.fulong.longcon.repository.definition;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;
import com.fulong.common.util.ListRangeIterator;
import java.util.Collection;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class NodeDefinitionIteratorImpl extends ListRangeIterator<NodeDefinition> implements NodeDefinitionIterator {
    public NodeDefinitionIteratorImpl(Collection<NodeDefinition> list){
        super(list);
    }
    /**
     *
     * @return NodeDefinition
     */
    public NodeDefinition nextDefinition() {
        return (NodeDefinition)this.next();
    }

}
