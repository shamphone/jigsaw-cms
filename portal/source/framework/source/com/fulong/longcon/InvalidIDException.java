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

public class InvalidIDException
    extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5077892304146018732L;

	public InvalidIDException() {
        super();
    }

    public InvalidIDException(String ID, String column, int result) {
        super("Invalid ID " + ID + " for column, " + result +
              " results returned.");
    }

    public InvalidIDException(String message) {
        super(message);
    }

    public InvalidIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIDException(Throwable cause) {
        super(cause);
    }
}
