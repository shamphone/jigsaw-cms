package com.fulong.longcon.site.impl;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.longcon.site.SiteTemplate;



/**
* SiteImpl
* @author    <a href="lixf@fulong.com.cn">李雄峰</a>
* @date      2010-5-26
*/
public class SiteImpl extends NodeWrapper implements Site {
    private SiteFactory factory;
    private Node node;

    public SiteImpl(SiteFactory factory, Node node) {
    	super(node);
        this.factory = factory;
        this.node = node;
    }
    
	public String getID() {
		return node.getID();
	}

      /**
     * 网站的域名，如果用户未设定域名，则使用[name].[mysite.com]作为为域名
     * @return String
     */
    public String getDomain() {
    	//return node.getName();
    	return node.getProperty("domain").getString();
      }


    /**
     * 域名所有者，可以为个人，可以为机构
     * @return Principal
     
    public Node getOwner() {
    	return node.getProperty("owner").getReference();

    }*/

  
    /**
     * 获取所使用的网站模板
     * @return Site
     */
    public SiteTemplate getTemplate() {    	
        String[] templates = this.node.getProperty("templates").getArray();
        if(templates.length==0)
        	return this.factory.getTemplate("default");
        return this.factory.getTemplate(templates[0]);
    }

    /**
     * 更新所使用的网站模板
     * @param template SiteTemplate
     */
    public void setTemplate(String template) {
    	this.node.getProperty("templates").setValue(template);
    }


    /**
     *
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
    	SiteImpl another = (SiteImpl)obj;
    	return another.node.equals(this.node);
        
    }

	public String[] getTemplates() {		
		return this.node.getProperty("templates").getArray();
	}

	public void setTemplates(String[] templates) {
		this.node.getProperty("templates").setValue(templates);
		
	}

	public String getDisplayName() {		
		return this.node.getProperty("displayName").getString();
	}

	
	public Node getOwner() {
		return this.getNode().getParent();
	}

	@Override
	public NodeIterator<Node> getAllNodes(String name) {
		return null;
	}

	@Override
	public void setMaxOrderNo(int orderNo) {
	}
}
