package com.fulong.taglib.site;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.site.Site;
import com.fulong.taglib.SpringTagSupport;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 1.0
 */
public class SiteURLTag extends SpringTagSupport {
     
	private static final long serialVersionUID = -4736231728039816813L;
	
	private String name;
    private String property;
    private String scope;
    public SiteURLTag() {
    }

    public int doEndTag() throws JspException {
        Site site = (Site) TagUtils.getInstance().lookup(this.
                pageContext, name, property, scope);
        TagUtils.getInstance().write(pageContext,"http://"+site.getDomain());
        return EVAL_PAGE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public String getScope() {
        return scope;
    }

}
