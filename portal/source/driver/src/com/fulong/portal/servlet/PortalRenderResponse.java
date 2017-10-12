package com.fulong.portal.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
 * @deprecated 不再使用
 */
public class PortalRenderResponse extends HttpServletResponseWrapper {
    private LockablePrintWriter writer;
    private boolean locked;
    public PortalRenderResponse(HttpServletResponse response) {
        super(response);
        this.writer = null;
        this.locked = false;
    }

    public void prepareWriter() throws IOException {
        //     this.writer=new LockablePrintWriter(((HttpServletResponse)getResponse()).getWriter());
    }

    /**
     * 锁定输出
     */
    public void lock() throws IOException {
//        this.flushBuffer();
//        this.writer.setLock(true);
        this.locked = true;
        if (this.writer != null)
            this.writer.setLock(locked);
    }

    /**
     * 释放输出
     */
    public void release() throws IOException {
//        this.resetBuffer();
//         this.writer.setLock(false);
        this.locked = false;
        if (this.writer != null)
            this.writer.setLock(locked);
    }

    /**
     * The default behavior of this method is to return getWriter()
     * on the wrapped response object.
     */


    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            this.writer = new LockablePrintWriter(((HttpServletResponse)
                    getResponse()).getWriter());
            this.writer.setLock(this.locked);
        }
        return this.writer;
    }

}
