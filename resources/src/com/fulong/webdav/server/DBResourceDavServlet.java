package com.fulong.webdav.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
/**
 * 基于文件系统的WebDAV
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @author lixf
 * @version 1.0
 */
public class DBResourceDavServlet extends WebDavServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8722143331723112928L;
	private Repository repository;
	private SiteFactory siteFactory;
    public void init() throws ServletException {
        super.init();
        //初始化该变量；
        this.repository = (Repository)this.getBeanFactory().getBean("repository", Repository.class);
        this.siteFactory= (SiteFactory)this.getBeanFactory().getBean("siteFactory", SiteFactory.class);
    }


    /**
     * 获取用户请求的文件
     * @param req HttpServletRequest
     * @param path String
     * @return ResourceInfo
     */
    protected ResourceInfo getRequestResourceInfo(HttpServletRequest req,
                                                  String path) {
    	Site site =this.getRequestSite(req);
    	if(site == null)
    		return null;
         Node parent = site.getOwner().getNode("resources");
         if(parent == null)
        	 return null;
         String[] pathes = path.split("\\/");
         Node node = parent;
         for(int i=0;i<pathes.length;i++){
        	 if(pathes[i].length()>0){
        		 parent=node;
        		 node = parent.getNode(pathes[i]);
        	 }
         }
         if (node != null)
        	 return new DBResourceInfo(this.repository, node);
         else
        	 return new BlankDBResourceInfo(this.repository,  parent, pathes[pathes.length-1]); 
    }
    
    private Site getRequestSite(HttpServletRequest req){
    	String domain = req.getServerName();
    	int port = req.getServerPort();
    	if(port!=80)
    		domain = domain +":"+port;
    	return this.siteFactory.getSite(domain);
    }
}
