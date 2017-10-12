package com.fulong.longcon.crawler;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import java.util.Collection;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Attribute;
import org.apache.commons.logging.Log;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import org.htmlparser.Tag;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class ParserNodeFilter  implements NodeFilter {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7506247255200252862L;
	private ConvertRule rule;
    private List<String> pendingURLs;
    private static Log log = LogFactory.getLog(ParserNodeFilter.class);
    public ParserNodeFilter(ConvertRule rule) {
        this.rule = rule;
        this.pendingURLs = new Vector<String>();
    }

    /**
     * accept
     *
     * @param node Node
     * @return boolean
     */
    @SuppressWarnings("unchecked")
	public boolean accept(Node node) {
        String host = node.getPage().getUrl();
        if (node instanceof Tag) {
            Tag tag = (Tag) node;
            if (this.isIgnoredTag(tag)) {
                return false;
            }
            List<Attribute> attributes = tag.getAttributesEx();
            for (int i = 0; i < attributes.size(); i++) {
                Attribute attr = attributes.get(i);
                String value = URLUtils.decode(attr.getValue());
                URL url = URLUtils.calcURL(host, value);
                if ( (url != null) && rule.needToChange(url)) {
                    try {
                        String newValue = rule.convert(url);
                        log.trace("convert:" + value + " to " + newValue);
                        attr.setValue(newValue);
                    }
                    catch (CrawlException crex) {
                        log.error("Error in convert:" + value, crex);
                    }
                    if (!this.pendingURLs.contains(url.toString()))
                        this.pendingURLs.add(url.toString());
                }
            }
        }
        return node.getParent() == null;
    }

    /**
     * 不做处理的标签
     * @param tag Tag
     * @return boolean
     */
    private boolean isIgnoredTag(Tag tag) {
        return "FORM".equalsIgnoreCase(tag.getTagName());
    }

    public Collection<String> getFindURLs() {
        return this.pendingURLs;
    }

}
