/**
 * 
 */
package com.fulong.longcon.site.impl;

import java.util.Comparator;

import com.fulong.longcon.site.Channel;

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
public class ChanneDisplaylNameComparator implements Comparator<Channel> {

	public int compare(Channel o1, Channel o2) {
		 return o2.getDisplayName().compareTo(o1.getDisplayName());
	}

}
