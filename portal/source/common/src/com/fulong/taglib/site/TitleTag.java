package com.fulong.taglib.site;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.site.Channel;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteTemplate;
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
public class TitleTag extends SpringTagSupport {
	private static final long serialVersionUID = 2644345673661266968L;
	private String title;
	private String definitionID;
	private String definitionName;
	private String propertyID;
	private String propertyName;
	
	public String getDefinitionID() {
		return definitionID;
	}

	public void setDefinitionID(String definitionID) {
		this.definitionID = definitionID;
	}


	public String getDefinitionName() {
		return definitionName;
	}


	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}


	public String getPropertyID() {
		return propertyID;
	}


	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public TitleTag() {
    }


    public int doEndTag() throws JspException {
            if (title != null) {
                TagUtils.getInstance().write(pageContext, title);
                return EVAL_PAGE;
            } 
            HttpServletRequest request = (HttpServletRequest)this.pageContext.
                                         getRequest();
            Site site = (Site) request.getSession().getAttribute(Site.class.getName());
    		if (site == null) {
    			String siteId = request.getParameter("siteId");
    			if (siteId == null) {
    				siteId = request.getServerName();
    			}
    			site = this.getSiteFactory().getSite(siteId);
    			if (site != null) {
    				 request.getSession().setAttribute(Site.class.getName(), site);
    			}
    		}   
    		if(site!=null)
    			title = "-"+site.getDisplayName();
    		
            Node content = null;
            String id = request.getParameter("contentId");
            if (id != null) {
                content = this.getRepository().getNode(id);
            }
            if (content != null) {
            	if(propertyID!=null){
            		Property property = content.getProperty(propertyID);
            		if(property!=null){
            			String value = property.getString();
            			if(value!=null&&!value.trim().equals("")){
            				title = property.getString()+ title;
            				TagUtils.getInstance().write(pageContext, title);
        		            this.title =null;
            		        return EVAL_PAGE;
            			}
            		}
            	}
            }
            String workPath = request.getServletPath();
            SiteTemplate template = this.getSiteFactory().getTemplate(request.getContextPath().substring(1));
            if (template != null) {
            	 Channel channel = template.getChannel(workPath);
            	 if(channel!=null){
            		 title = channel.getDisplayName() + title;
            	 }
            }
            TagUtils.getInstance().write(pageContext, title);
            this.title =null;
            return EVAL_PAGE;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
