package com.fulong.longcon.crawler;

/**
 * <p>Title: 龙驭网页爬虫系统</p>
 *
 * <p>Description: 龙驭网页爬虫系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
public class CrawlException
    extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8734912007113874146L;

	public CrawlException() {
        super();
    }

    public CrawlException(String message) {
        super(message);
    }

    public CrawlException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlException(Throwable cause) {
        super(cause);
    }
}
