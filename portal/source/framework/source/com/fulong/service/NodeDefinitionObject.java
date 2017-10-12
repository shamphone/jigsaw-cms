/**
 * 
 */
package com.fulong.service;

import com.fulong.longcon.repository.NodeDefinition;

/**
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class NodeDefinitionObject  extends ServiceObject{
	private static final long serialVersionUID = 6692113202251346442L;
	public static final int Type = 2;
	
	public NodeDefinitionObject(NodeDefinition definition){
		super(definition,Type);
	}
	
	public NodeDefinition getNodeDefinition(){
		return (NodeDefinition)this.getSource();
	}

}
