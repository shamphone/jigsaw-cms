package com.fulong.longcon.site;

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
public class SiteExistsException extends SiteException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 643859989605305561L;

	public SiteExistsException() {
        super();
    }

    public SiteExistsException(String message) {
        super(message);
    }

    public SiteExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SiteExistsException(Throwable cause) {
        super(cause);
    }
}
