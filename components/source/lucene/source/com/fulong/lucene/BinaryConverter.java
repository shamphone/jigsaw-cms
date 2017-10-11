/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene;

import java.io.IOException;
import java.io.InputStream;

/**
 * BinaryConverter
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-23
 */
public interface BinaryConverter {
	/**
	 * 将流转换成文本文件
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String convert(InputStream is) throws IOException;
}
