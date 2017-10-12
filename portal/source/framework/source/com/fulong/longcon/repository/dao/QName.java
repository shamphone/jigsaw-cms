/**
 * 
 */
package com.fulong.longcon.repository.dao;

import com.fulong.longcon.repository.PropertyDefinition;

/**
 * 每个属性都对应一张单独的表。
 * 表名规则为：类型缩写+属性Name.
 * 类型缩写：S(tring) L(ong) F(loat) B(oolean) B(lob) T(ext) R(eference) D(ate) P(ath) N(ame) 
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
public class QName {
	
	private static final String[] PREFIX = { "", "S", "I", "L", "F", "D", "B", "N", "P", "R", "T"};

	public static String encode(PropertyDefinition property) {
		return encode(property.getType(),property.getID());
	}

	public static String encode(int type, String name) {		
		return PREFIX[type] + "_" + name.replace('-', '_');
	}

}
