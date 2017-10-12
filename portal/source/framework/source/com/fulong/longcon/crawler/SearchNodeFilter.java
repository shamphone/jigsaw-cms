package com.fulong.longcon.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class SearchNodeFilter implements NodeFilter {

	private static final long serialVersionUID = 6617463937282221342L;
	
	private List<String> foundURLs;
	private ConvertRule rule;

	/**
	 * 
	 * @param pattern
	 *            String 待查找的文件名格式
	 * @param rule
	 *            ConvertRule 搜索范围
	 */
	public SearchNodeFilter(ConvertRule rule) {
		this.rule = rule;
		this.foundURLs = new Vector<String>();
		;
	}

	/**
	 * accept
	 * 
	 * @param node
	 *            Node
	 * @return boolean
	 */
	public boolean accept(Node node) {
		String host = node.getPage().getUrl();
		URL hostURL = null;
		try {
			hostURL = new URL(host);
		} catch (MalformedURLException ex) {
			return false;
		}
		if (node instanceof Tag) {
			Tag tag = (Tag) node;
			if (this.isIgnoredTag(tag)) {
				return false;
			}
			List<?> attributes = tag.getAttributesEx();
			for (int i = 0; i < attributes.size(); i++) {
				Attribute attr = (Attribute) attributes.get(i);
				String value = URLUtils.decode(attr.getValue());
				URL url = URLUtils.calcURL(host, value);
				if ((url != null) && this.rule.needToChange(url))
					if (!this.foundURLs.contains(url.toString()))
						this.foundURLs.add(url.toString());
			}
			if (tag instanceof LinkTag) {
				LinkTag link = (LinkTag) tag;
				if (link.isHTTPLink()) {
					URL url = URLUtils.calcURL(host, link.getLink());
					if (url.getHost().equalsIgnoreCase(hostURL.getHost()))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * 不做处理的标签
	 * 
	 * @param tag
	 *            Tag
	 * @return boolean
	 */
	private boolean isIgnoredTag(Tag tag) {
		return "FORM".equalsIgnoreCase(tag.getTagName());
	}

	public Collection<String> getMatchedURLs() {
		return this.foundURLs;
	}

}
