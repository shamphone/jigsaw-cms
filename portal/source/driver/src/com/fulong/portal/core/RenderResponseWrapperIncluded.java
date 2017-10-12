package com.fulong.portal.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspWriter;
/**
 *
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
public class RenderResponseWrapperIncluded  extends HttpServletResponseWrapper implements RenderResponse{

    /**
     * PrintWriter which appends to the JspWriter of the including page.
     */
    private PrintWriter printWriter;

    private JspWriter jspWriter;

    private RenderResponse renderResponse;

    public RenderResponseWrapperIncluded(RenderResponse response,
                                         JspWriter jspWriter) {
        super((HttpServletResponse)response);
        this.printWriter = new PrintWriter(jspWriter);
        this.jspWriter = jspWriter;
        this.renderResponse=response;
    }

    /**
     * Returns a wrapper around the JspWriter of the including page.
     */
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        throw new IllegalStateException();
    }

    /**
     * Clears the output buffer of the JspWriter associated with the including
     * page.
     */
    public void resetBuffer() {
        try {
            jspWriter.clearBuffer();
        } catch (IOException ioe) {
        }
    }

    public PortletURL createRenderURL() {
        return renderResponse.createRenderURL();
    }

    public PortletURL createActionURL() {
        return renderResponse.createActionURL();
    }

    public String getNamespace() {
        return renderResponse.getNamespace();
    }

    public void setTitle(String string) {
        renderResponse.setTitle(string);
    }

    public OutputStream getPortletOutputStream() throws IOException {
        throw new IllegalStateException();
    }

    public void addProperty(String name, String value) {
        renderResponse.addProperty(name,value);
    }

    public void setProperty(String name, String value) {
        renderResponse.setProperty(name,value);
    }
}
