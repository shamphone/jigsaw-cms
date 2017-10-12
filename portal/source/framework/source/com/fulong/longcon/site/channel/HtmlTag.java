/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.site.channel;

import org.htmlparser.tags.CompositeTag;

/**
 * HtmlTag
 * @author    <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date      2010-5-29
 */
public class HtmlTag extends CompositeTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5832848989022920874L;


	public HtmlTag() {
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
        StringBuffer sb = new StringBuffer("<site:html");
        if(this.getDefinition()!=null)
        sb.append(" definition="+this.getDefinition());
        if(this.isSecure())
        	sb.append(" allow="+this.getAllow()); 
        if(this.getOntagstart()!=null)
            sb.append(" ontagstart="+this.getOntagstart());
        if(this.getOntagend()!=null)
        	sb.append(" ontagend="+this.getOntagend());
        if(this.getCheckLeaser()!=null)
        	sb.append(" checkLeaser="+this.getCheckLeaser());
        if(this.getDir()!=null)
        	sb.append(" dir="+this.getDir());
        if(this.getLang()!=null)
        	sb.append(" lang="+this.getLang());
        sb.append("></site:html>");
        return sb.toString();
    }
    /**
     * 榜定的内容分类；
     * @return
     */
    public String getDefinition(){
    	return this.getAttribute("definition");
    }

    /**
     * 允许访问的用户组
     * @return
     */
    public String getAllow(){
    	return this.getAttribute("allow");
    }
    /**
     * 允许访问的用户组
     * @return
     */
    public boolean isSecure(){
    	return this.getAttribute("allow")!=null;
    }
    /**
     * 关联流程
     * @return
     */
    public String getOntagstart() {
		return this.getAttribute("ontagstart");
	}
    /**
     * 关联流程
     * @return
     */
	public String getOntagend() {
		return this.getAttribute("ontagend");
	}
	/**
     * 是否检查租户
     * @return
     */
	public String getCheckLeaser() {
		return this.getAttribute("checkLeaser");
	}
	/**
     * dir
     * @return
     */
	public String getDir() {
		return this.getAttribute("dir");
	}

	/**
	 * lang
	 * @return
	 */
	public String getLang() {
		return this.getAttribute("lang");
	}
 
    private static final String mIds[] = {"site:html"};
    private static final String mEndTagEnders[] = {"BODY", "HTML","HEAD","SITE:HTML"};
    private static final String[] mEnders = new String[]{"HEAD","SITE:HTML"};

}
