package com.fulong.longcon;

/**
 *
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport</p>
 *
 * <p>Copyright: Copyright (c) Beijing Zhongke Fulong Computer Technology LTD.
 * 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author Lixf
 * @version 2.0.0
 */

public class ItemExistsException
    extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7827407093009377242L;

	public ItemExistsException() {
    }

    public ItemExistsException(String p0) {
        super(p0);
    }

    public ItemExistsException(String p0, Throwable p1) {
        super(p0, p1);
    }

    public ItemExistsException(Throwable p0) {
        super(p0);
    }
}
