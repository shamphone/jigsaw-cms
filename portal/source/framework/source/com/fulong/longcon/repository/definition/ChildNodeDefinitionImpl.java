package com.fulong.longcon.repository.definition;

import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.core.InternalNodeDefinition;
import com.fulong.longcon.repository.core.InternalNodeDefinitionManager;
import com.fulong.longcon.repository.data.PropertyDefinitionData;

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
public class ChildNodeDefinitionImpl
    extends PropertyDefinitionImpl implements ChildNodeDefinition { 

    public ChildNodeDefinitionImpl(InternalNodeDefinitionManager repository,
                                   InternalNodeDefinition node,
                                   PropertyDefinitionData data) {
        super(repository, node, data);
    }

    public ChildNodeDefinitionImpl(InternalNodeDefinitionManager repository, InternalNodeDefinition node, PropertyDefinition parent) {
		super(repository, node, parent);
	}

	public ChildNodeDefinitionImpl(InternalNodeDefinitionManager repository, InternalNodeDefinition node, PropertyDefinitionData data, PropertyDefinition parent) {
		super(repository, node, data, parent);
	}

	/**
     * 返回当前复合属性的type所对应的NodeDefinition
     * @return NodeDefinition
     */
    public NodeDefinition getNodeDefinition() {
    	if(this.data.getNodeType()== null)
    		return ((ChildNodeDefinition)this.overload).getNodeDefinition();
        return this.repository.getDefinition(this.data.getNodeType());
    }


}
