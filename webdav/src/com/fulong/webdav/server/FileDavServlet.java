package com.fulong.webdav.server;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 基于文件系统的WebDAV，实现对各个网站模版的文件进行管理；处理如下模式的地址：
 * http://www.mysite.com/webdav/[模版名称]/[文件相对于模版的地址]；
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class FileDavServlet
    extends WebDavServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2388832864927398425L;
	 public void init() throws ServletException {
		 super.init();
	 }

	/**
     * 获取用户请求的文件
     * @param req HttpServletRequest
     * @param path String
     * @return ResourceInfo
     */
    protected ResourceInfo getRequestResourceInfo(HttpServletRequest req,
                                                  String path) {
    	String[] splits = path.split("/");
    	if(splits.length<2)
    		return null;
        String name = splits[1];
        ServletContext context = this.getServletContext().getContext("/"+name);
        String filePath = path.substring(req.getServletPath().length()+name.length()+1);
        File file = new File(context.getRealPath(filePath));
        return new FileResourceInfo(file, path);
    }

	
}
