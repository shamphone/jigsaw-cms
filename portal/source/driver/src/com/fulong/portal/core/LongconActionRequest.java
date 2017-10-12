package com.fulong.portal.core;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.fulong.portal.model.PortletContainer;
import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.utils.Enumerator;
/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
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

public class LongconActionRequest extends LongconPortletRequest implements
        ActionRequest {
    private Map<String, Object> parameters;
    
    private boolean parsedParams;
    public LongconActionRequest(PageContext context,
                                PortletWindow window,
                                PortletContainer container) {
        super(context, window,container);
        this.parsedParams=false;
    }
    /**
     * 获取和这个占位符相关的所有参数，具体包括：
     * 1. 全局性的参数，不带任何前缀。
     * 2. 本地参数，本地参数使用[portletid].[参数名称]的形式。
     * @todo ：如果该占位符不是当前活动占位符，就优先使用本地参数，否则 优先使用全局参数。
     */

    @SuppressWarnings("unchecked")
	protected void parseParameters(){
        if (parsedParams) {
          return;
      }
        this.parameters=new HashMap<String, Object>();
        HttpServletRequest request = this.getHttpRequest();
        Map<String, Object> orginMap=request.getParameterMap();
              Enumeration<String> params = request.getParameterNames();
              while (params.hasMoreElements()) {
                  String param = (String) params.nextElement();
                  if (NamespaceMapper.isGlobal(param)) {
                     this.parameters.put(param,orginMap.get(param));
                  } else {
                      String real = NamespaceMapper.decode(this.portletWindow.
                              getId(),
                              param);
                      if (real != null) {
                          parameters.put(real,orginMap.get(param));
                      }
                  }
              }
      this.parsedParams=true;
    }
    /**
     * 获取InputStream，未校验
     * @return InputStream
     * @throws IOException
     */
    public InputStream getPortletInputStream() throws java.io.IOException {

        HttpServletRequest servletRequest = (HttpServletRequest)super.
                                            getRequest();

        if (servletRequest.getMethod().equals("POST")) {
            String contentType = servletRequest.getContentType();
            if (contentType == null ||
                contentType.equals("application/x-www-form-urlencoded")) {
                throw new java.lang.IllegalStateException(
                        "User request HTTP POST data is of type application/x-www-form-urlencoded. This data has been already processed by the portal/portlet-container and is available as request parameters."
                        );
            }
        }
        return servletRequest.getInputStream();
    }
    /**
     * 获取缺省参数值
     * @param name String
     * @return String
     */
    public String getParameter(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Parameter name == null");
        }
        String[] values = this.getParameterValues(name);
        if (values == null) {
            return null;
        }
        if (values.length == 0) {
            return null;
        }
        return values[0];
    }
    /**
     * 获取和这个占位符相关的所有参数，具体包括：
     * 1. 全局性的参数，不带任何前缀。
     * 2. 本地参数，本地参数使用[portletid].[参数名称]的形式。
     * @return Enumeration
     * @todo ：如果该占位符不是当前活动占位符，就优先使用本地参数，否则 优先使用全局参数。
     */
    @SuppressWarnings("unchecked")
	public Enumeration<String> getParameterNames() {
      this.parseParameters();
      return new Enumerator(this.parameters.keySet());
    }
    /**
     * 获取参数值。首先从全局参数中获取，如果没有，则从本地参数中获取。
     * @param name String
     * @return String[]
     * @todo ：如果该占位符不是当前活动占位符，就优先使用本地参数，否则 优先使用全局参数。
     */
    public String[] getParameterValues(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Parameter name == null");
        }
        parseParameters();
        Object value = parameters.get(name);
        if (value == null)
            return ((String[])null);
        else if (value instanceof String[])
            return ((String[]) value);
        else if (value instanceof String) {
            String values[] = new String[1];
            values[0] = (String) value;
            return (values);
        } else {
            String values[] = new String[1];
            values[0] = value.toString();
            return (values);
        }

    }

    /**
     * 获取参数集和
     * @return Map
     */
    public Map<String, Object> getParameterMap() {
        this.parseParameters();
        return this.parameters;
    }
/*
    public int getContentLength() {
        return this.getRequest().getContentLength();
    }

    public String getContentType() {
        return this.getRequest().getContentType();
    }

    public String getCharacterEncoding() {
        return this.getRequest().getCharacterEncoding();
    }

    public BufferedReader getReader() throws UnsupportedEncodingException,
            IOException {
        return this.getRequest().getReader();
    }

    public void setCharacterEncoding(String enc) throws
            UnsupportedEncodingException {
        this.getRequest().setCharacterEncoding(enc);
    }
*/
}
