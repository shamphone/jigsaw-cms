/**
 * 
 */
package com.fulong.longcon.exchange;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

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
public interface ImporterEventListener {
	/**
	 * 提示信息处理
	 */
	public void info(String message);
	/**
	 * 错误信息处理
	 */
	public void error(String message);
	/**
	 * 节点定义被导入
	 */
	public void nodeDefinitionImported(NodeDefinition definition);
	/**
	 * 属性定义被导入
	 */
	public void propertyDefinitionImported(PropertyDefinition definition);
	
	/**
	 * 节点被导入
	 */
	public void nodeImported(Node node);

}
