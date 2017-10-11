package com.fulong.lucene.core;

import org.apache.lucene.search.highlight.Encoder;

/**
 * 对lucene的SimpleHtmlEncoder的改进，解决中文编码问题
 *
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
public class HTMLEncoder
    implements Encoder {
    /**
     *
     * @param originalText The section of text being output
     * @return String
     */
    public String encodeText(String plainText) {
        if (plainText == null || plainText.length() == 0) {
            return "";
        }
        if (plainText == null || plainText.length() == 0)
        {
                return "";
        }

        StringBuffer result = new StringBuffer(plainText.length());

        for (int index=0; index<plainText.length(); index++)
        {
                char ch = plainText.charAt(index);

                switch (ch)
                {
                case '"':
                        result.append("&quot;");
                        break;

                case '&':
                        result.append("&amp;");
                        break;

                case '<':
                        result.append("&lt;");
                        break;

                case '>':
                        result.append("&gt;");
                        break;

                default:

                           result.append(ch);

                }
        }

        return result.toString();


    }
}
