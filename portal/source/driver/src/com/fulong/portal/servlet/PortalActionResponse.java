package com.fulong.portal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
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
public class PortalActionResponse extends HttpServletResponseWrapper {
    private ServletOutputStream os;
    private PrintWriter writer;
    public PortalActionResponse(HttpServletResponse response) {
        super(response);
        os=new BlankServletOutputStream();
        writer=new BlankPrintWriter(new StringWriter());
    }


    /**
     * The default behavior of this method is to return getOutputStream()
     * on the wrapped response object.
     */

    public ServletOutputStream getOutputStream() throws IOException {
        return this.os;
    }

    /**
     * The default behavior of this method is to return getWriter()
     * on the wrapped response object.
     */


    public PrintWriter getWriter() throws IOException {
        return this.writer;
    }

    /**
     * The default behavior of this method is to call setContentLength(int len)
     * on the wrapped response object.
     */

    public void setContentLength(int len) {

    }

    /**
     * The default behavior of this method is to call setContentType(String type)
     * on the wrapped response object.
     */

    public void setContentType(String type) {

    }


    /**
     * The default behavior of this method is to call setBufferSize(int size)
     * on the wrapped response object.
     */
    public void setBufferSize(int size) {

    }


    /**
     * The default behavior of this method is to call flushBuffer()
     * on the wrapped response object.
     */

    public void flushBuffer() throws IOException {

    }


    /**
     * The default behavior of this method is to call reset()
     * on the wrapped response object.
     */

    public void reset() {

    }

    /**
     * The default behavior of this method is to call resetBuffer()
     * on the wrapped response object.
     */

    public void resetBuffer() {

    }

    /**
     * The default behavior of this method is to call setLocale(Locale loc)
     * on the wrapped response object.
     */

    public void setLocale(Locale loc) {

    }


}

