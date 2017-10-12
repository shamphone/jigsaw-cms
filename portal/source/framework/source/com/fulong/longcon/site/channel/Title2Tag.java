/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.site.channel;

import org.htmlparser.tags.CompositeTag;

/**
 * Title2Tag
 * 用来支持parser来对jsp页面进行解析
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-5-26
 */
public class Title2Tag extends CompositeTag {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7621700556213865489L;
	public Title2Tag() {
    }

    public String[] getIds() {
        return mIds;
    }
    public String[] getEnders (){
         return mEnders;
    }
    public String[] getEndTagEnders() {
        return mEndTagEnders;
    }
    public String toHtml() {
        StringBuffer sb = new StringBuffer("<site:title2>");
        putChildrenInto(sb);
        sb.append("</site:title2>");

        return sb.toString();
    }

 
    private static final String mIds[] = {"site:title2"};
    private static final String mEndTagEnders[] = {"BODY", "HTML","HEAD","SITE:TITLE2"};
    private static final String[] mEnders = new String[]{"HEAD","SITE:TITLE2"};

}

