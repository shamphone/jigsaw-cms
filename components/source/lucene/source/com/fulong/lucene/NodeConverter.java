/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene;

import java.io.IOException;

import com.fulong.longcon.repository.Node;

/**
 * NodeConverter
 * 在建立索引时,对一些特殊的引用节点,如文件\枚举等,需直接转换成字符串以便检索;
 * 
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-23
 */
public interface NodeConverter {
	public String convert(Node node) throws IOException;
}
