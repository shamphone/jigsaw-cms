/**
 * 
 */
package com.fulong.longcon.workflow.xml;

import java.util.Comparator;

import com.fulong.longcon.workflow.ProcessDefinition;

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
public class NameComparator implements Comparator<ProcessDefinition> {

	public int compare(ProcessDefinition o1, ProcessDefinition o2) {		
		return o1.getName().compareTo(o2.getName());
	}

}
