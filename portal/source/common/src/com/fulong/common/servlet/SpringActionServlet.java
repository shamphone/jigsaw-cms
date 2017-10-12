package com.fulong.common.servlet;

import java.net.ProxySelector;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.WebApplicationContext;


/**
 * 对struts的ActionServlet的扩展，支持从环境变量或者配置文件中加载系统所需要的Bean。
 * 增加bean-factory的配置选项。如果在JNDI中未配置全局变量，则使用本地的配置
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
public class SpringActionServlet extends ActionServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3697640710640742223L;	 
    private static Log log = LogFactory.getLog(SpringActionServlet.class);
    public SpringActionServlet() {
    }

    /**
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        processProxy();
        super.init();  
        BeanFactory factory =(BeanFactory)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//        this.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,    		factory);
        this.getServletContext().setAttribute("com.fulong.system", factory.getBean("configuration"));
    }



    /**
     * 处理proxy，在某些机器上，需要把proxy关闭，否则启动会非常慢
     */
    protected void processProxy() {
        if (this.getServletConfig().getInitParameter("set-proxy") != null) {
            ProxySelector.setDefault(null);
            log.info("Proxy set to null.");
        }
    }

 

}
