package com.fulong.portal.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

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
public class BlankServletOutputStream extends ServletOutputStream {
    protected BlankServletOutputStream() {
    }
    public void print(String s) throws IOException {

    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param b the <code>byte</code>.
     * @throws IOException if an I/O error occurs. In particular, an <code>IOException</code> may be thrown if the output stream has been closed.
     * @todo Implement this java.io.OutputStream method
     */
    public void write(int b) throws IOException {
    }
}
