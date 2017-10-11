/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene.converter;

import java.io.IOException;
import java.io.InputStream;

import com.fulong.lucene.BinaryConverter;
import com.fulong.lucene.FilesToTextUtils;

/**
 * PDFConverter
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-6-24
 */
public class WordConverter implements BinaryConverter {

	/* (non-Javadoc)
	 * @see com.fulong.longcon.repository.search.BinaryConverter#convert(java.io.InputStream)
	 */
	@Override
	public String convert(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		return FilesToTextUtils.getInstance()
		.Word2Text(is);
	}

}
