package com.fulong.portal.model;

import javax.portlet.PortalContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * 占位符容器，负责启动、管理、关闭占位符及其所在的环境
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public interface PortletContainer {
    /**
     * 启动占位符容器
     * @param config ServletConfig
     */
    public void registerPortlets(ServletContext context, String file) throws ServletException;

    /**
     * 关闭容器
     */
    public void shutdown();

    /**
     * 创建一个PortletWindow
     * @param ID String
     * @param portletType String
     * @return PortletWindow
     */
    public PortletWindow createWindow(String ID, String portletType);
    /**
     * 门户上下文
     * @return PortalContext
     */
    public PortalContext getPortalContext();
    /**
     * 获取容器名称
     * @return
     */
    public String getName();
}
