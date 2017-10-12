package com.fulong.longcon.repository;

/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */

public class ValueFormatException extends RepositoryException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -150746557421416147L;
	private String[] args;
    public ValueFormatException() {
    }

    public ValueFormatException(String message) {
        this(message,new String[0]);
    }
    public ValueFormatException(String message, String arg) {
        this(message,new String[]{arg});
    }

    public ValueFormatException(String message, String[] args) {
         super(message);
         this.args=args;
    }
    public ValueFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueFormatException(Throwable cause) {
        super(cause);
    }

    public String[] getArguments(){
        return this.args;
    }
}
