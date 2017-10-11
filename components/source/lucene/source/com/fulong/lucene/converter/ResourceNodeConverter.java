/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.lucene.converter;

import java.io.IOException;
import java.io.InputStream;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.lucene.BinaryConverter;
import com.fulong.lucene.ConverterFactory;
import com.fulong.lucene.NodeConverter;

/**
 * ResourceNodeConverter
 * 如果有一个节点使用Resource-scheme类型节点作为附件，一般是将这个附件也作为全文检索内容附加到原节点中
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-6-24
 */
public class ResourceNodeConverter implements NodeConverter {
	private String mime;// mime属性名称；
	private String content; // 二进制属性名称

	public ResourceNodeConverter() {
		this.mime = "mime";
		this.content = "resource_content";
	}

	public void setMime(String property) {
		this.mime = property;
	}

	public void setContent(String property) {
		this.content = property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fulong.lucene.NodeConverter#convert(com.fulong.longcon.repository
	 * .Node)
	 */
	@Override
	public String convert(Node node) throws IOException {
		Property type = node.getProperty(mime);
		if ((type != null) && (type.getString() != null)) {
			InputStream is = node.getProperty(content).getStream();
			if (is != null)
				try {
					BinaryConverter converter = ConverterFactory.getInstance().getBinaryConverter(type.getString());
					if (converter == null)
						return null;
					String text = converter.convert(is);
					if ((text != null) && (text.length() > 0))
						return text;
				} finally {
					is.close();
				}
		}
		return null;
	}

}
