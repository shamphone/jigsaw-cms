/**
 * 
 */
package com.fulong.longcon.exchange;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class DefaultImporterEventListener implements ImporterEventListener{
	private static final Log log = LogFactory.getLog(DefaultImporterEventListener.class);
	public void error(String message) {
		log.error(message);
		
	}

	public void info(String message) {
		log.info(message);
		
	}

	public void nodeDefinitionImported(NodeDefinition definition) {
		log.info("Node definition "+definition.getName()+" imported.");		
	}

	public void nodeImported(Node node) {
		log.info("Node  "+ node.getID()+" imported.");		
	}

	public void propertyDefinitionImported(PropertyDefinition definition) {
		
	}

}
