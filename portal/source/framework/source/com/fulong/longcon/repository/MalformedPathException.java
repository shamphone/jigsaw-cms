package com.fulong.longcon.repository;

/**
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
public class MalformedPathException extends RepositoryException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6723381491515126443L;

	public MalformedPathException() {
        super();
    }

    public MalformedPathException(String message) {
        super(message);
    }

    public MalformedPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedPathException(Throwable cause) {
        super(cause);
    }
}
