package com.fulong.portal.core;

import javax.portlet.PortletException;
import javax.servlet.ServletException;

/**
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
public class LongconPortletException extends PortletException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2828366742134886761L;
	public LongconPortletException() {
        super();
    }

    public LongconPortletException(String string) {
        super(string);
    }

    public LongconPortletException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public LongconPortletException(Throwable throwable) {
        super(throwable);
    }

    public LongconPortletException(ServletException ex){
        super(ex);
    }
    public LongconPortletException(String message,ServletException ex){
        super(message,ex);
    }
}
