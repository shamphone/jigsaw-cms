package com.fulong.common;

import java.io.Closeable;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.struts.ActionSupport;

import com.fulong.common.util.Pager;
import com.fulong.common.util.ParameterString;
import com.fulong.longcon.site.SiteException;
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

public class BaseAction extends ActionSupport  {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
    public static final String XML_HEADER =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public String CURRENTORG = "currentOrg";

    public String DICTLAEL = "label";

    public String DICTTITLE = "title";

    public final String DEFAULT_ENCODING = "UTF-8";



    /**
     * 设置分页
     * @param request HttpServletRequest
     * @param count int 总页�\uFFFD
     */
    protected void setPager(HttpServletRequest request, long count) {
        Pager pager = this.getPager(request);
        pager.setCount(count);
    }

    /**
     * 设置分页
     * @param request HttpServletRequest
     * @param count int
     * @param pageSize int
     */
    protected void setPager(HttpServletRequest request, long count,
                            int pageSize) {
        Pager pager = this.getPager(request);
        pager.setCount(count);
        pager.setPageSize(pageSize);

    }

    /**
     * 获取页码
     * @param request HttpServletRequest
     * @return int
     */
    protected int getPageNo(HttpServletRequest request) {
        String pageNo = request.getParameter("pageNo");
        try {
            return Integer.parseInt(pageNo);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * 获取页面大小
     * @param request HttpServletRequest
     * @return int
     */
    protected int getPageSize(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        try {
            return Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            return 20;
        }
    }

    /**
     * 获取分页�\uFFFD
     * @param request HttpServletRequest
     * @return Pager
     */
    protected Pager getPager(HttpServletRequest request) {
        Pager pager = (Pager) request.getAttribute(Pager.BEAN_PAGER);
        if (pager == null) {
            pager = new Pager();
            pager.setPageNo(this.getPageNo(request));
            pager.setPageSize(this.getPageSize(request));
            request.setAttribute(Pager.BEAN_PAGER, pager);
        }
        return pager;
    }

 


    /**
     * @deprecated??????util.StringUtils??????
     * @param source String
     * @param arg1 Object
     * @return String
     */
    protected String format(String source, Object arg1) {
        return format(source, new Object[] {arg1});
    }

    /**
     * @deprecated??????util.StringUtils??????
     * @param source String
     * @param arg1 Object
     * @param arg2 Object
     * @return String
     */
    protected String format(String source, Object arg1, Object arg2) {
        return format(source, new Object[] {arg1, arg2});
    }

    /**
     * @deprecated 使用util.StringUtils来替代
     * @param source String
     * @param args Object[]
     * @return String
     */
    protected String format(String source, Object[] args) {
        String result = source;
        for (int i = 0; i < args.length; i++) {
            String regex = "\\x7B" + i + "\\x7D";
            if (args[i] == null) {
                result = result.replaceFirst(regex, "");
            } else {
                String encoded;
                try {
                    encoded = URLEncoder.encode(args[i].toString(), "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    encoded = args[i].toString();
                }

                result = result.replaceFirst(regex, encoded);
            }
        }

        return result;

    }

    /**
     * 重定向到地质newURL。
     * @param newURL String
     * @param relative boolean
     * @return ActionForward
     */

    @SuppressWarnings("deprecation")
	protected ActionForward redirect(String newURL, boolean relative) {
        ActionForward dest = new ActionForward();
        dest.setPath(newURL);
        dest.setContextRelative(relative);
        dest.setRedirect(true);
        return dest;
    }

    /**
     * 是否是域名,即用户不是使用localhost或者IP地址来访问
     * @param request HttpServletRequest
     * @return boolean
     */
    protected boolean isDomain(HttpServletRequest request) {
        String serverName = request.getServerName();
        if ("localhost".equalsIgnoreCase(serverName)) {
            return false;
        }
        boolean isIP = true;
        String[] splits = serverName.split("\\.");
        for (int i = 0; isIP && (i < splits.length); i++) {
            try {
                Integer.parseInt(splits[i]);
            } catch (NumberFormatException ex) {
                isIP = false;
            }
        }

        return!isIP;
    }

    /**
     * 重定向到指定页面，同时用参数来填充重定向URL地址中的参数。
     * @param mapping ActionMapping
     * @param name String
     * @param arg0 Object
     * @return ActionForward
     */
    protected ActionForward forward(ActionMapping mapping, String name
                                    , Object arg0) {
        return forward(mapping, name, new Object[] {arg0});
    }

    /**
     *重定向到指定页面，同时用参数来填充重定向URL地址中的参数。
     * @param mapping ActionMapping
     * @param name String
     * @param arg0 Object
     * @param arg1 Object
     * @return ActionForward
     */

    protected ActionForward forward(ActionMapping mapping, String name
                                    , Object arg0, Object arg1) {
        return forward(mapping, name, new Object[] {arg0, arg1});
    }

    /**
     * 重定向到指定页面，同时用参数来填充重定向URL地址中的参数。
     * @param mapping ActionMapping
     * @param name String
     * @param arg0 Object
     * @param arg1 Object
     * @param arg2 Object
     * @return ActionForward
     */
    protected ActionForward forward(ActionMapping mapping, String name
                                    , Object arg0, Object arg1, Object arg2) {
        return forward(mapping, name, new Object[] {arg0, arg1, arg2});
    }

    /**
     * 重定向到指定页面，同时用参数来填充重定向URL地址中的参数。
     * @param mapping ActionMapping
     * @param name String
     * @param args Object[]
     * @return ActionForward
     */
    protected ActionForward forward(ActionMapping mapping,
                                    String name,
                                    Object[] args) {
        ActionForward source = mapping.findForward(name);
        String path = source.getPath();
        ParameterString ps = new ParameterString(path);
        ps.replace(args);
        ActionForward dest = new ActionForward();
        dest.setPath(ps.toString());
        dest.setRedirect(source.getRedirect());
        dest.setName(source.getName());
        return dest;

    }

    /**
     *
     * @param date String
     * @return Date
     */
    protected Date string2Date(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }


    /**
     * 获取名称为name的Cookie。如果没有则返回空
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param name String
     * @return Cookie
     */

    protected Cookie getCookie(HttpServletRequest request,
                               HttpServletResponse response,
                               String name) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                return cookies[i];
            }
        }
        return null;
    }

    /**
     * 删除名称为name的Cookie项
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param name String
     */
    protected void deleteCookie(HttpServletRequest request,
                                HttpServletResponse response,
                                String name) {
        Cookie cookie = this.getCookie(request, response, name);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            throw new IllegalArgumentException(
                    "Unable to find cookie with name:" + name + ".");
        }
    }
    /**
     * 获取临时文件目录
     * @return String
     */
    protected final String getTempDirectoryPath() {
        return this.getTempDirectory().getAbsolutePath() + File.separator;
    }

    private File tempDirectory;
    /**
     * 获取临时文件目录
     * @return File
     */
    protected final File getTempDirectory() {
        if (tempDirectory == null) {
            tempDirectory = new File(this.getServletContext().getRealPath(
                    "/tmp"));
            if(!tempDirectory.exists())
            tempDirectory.mkdirs();
        }
        return tempDirectory;
    }
    /**
     * 销毁上传文件时产生的FormFile
     * @param FormFile
     * @return boolean 删除成功或formFile不存在返回true，否则返回false
     */
    protected final boolean destroyFormFile(FormFile formFile) {
    	boolean ret = true;
    	if (formFile != null) {
    		try {
    			formFile.destroy();
			} catch (Exception e) {
				log.warn("Destroy FormFile " + formFile.getFileName() + " Failed:", e);
				ret = false;
			}
    	}
        return ret;
    }
    /**
     * 删除文件或目录
     * @param File
     * @return boolean 删除成功或文件不存在返回true，否则返回false
     */
    protected final boolean forceDelete(File file) {
		boolean ret = true;
		if (file != null) {
			try {
				FileUtils.forceDelete(file);
			} catch(Exception ex) {
				log.warn("Delete File "+ file.getPath()+" Failed:", ex);
				ret = false;
			}
		}
		return ret;
	}
    /**
     * 关闭流（注：Closeable接口的实现类都可以）
     * @param Closeable
     * @return boolean 关闭成功或流不存在返回true，否则返回false
     */
    protected final boolean closeStream(Closeable stream) {
		boolean ret = true;
		if (stream != null) {
			try {
				stream.close();
			} catch (Exception ex) {
				log.warn("Close Stream Failed:", ex);
				ret = false;
			}
		}
		return ret;
	}
    
    
    /**
	 * 解析关键字
	 * 
	 * @param keywords
	 *            String
	 * @return String[]
	 */
	protected String[] parseKeywords(String keywords) {
		if ((keywords == null) || (keywords.trim().length() == 0))
			return new String[0];
		return keywords.split("\\s+");
	}

	protected boolean exists(FormFile file) {
		return (file != null) && (file.getFileName() != null) && (file.getFileName().length() > 0);
	}

	protected String random() {
		return "" + Calendar.getInstance().get(Calendar.MILLISECOND);
	}

	protected String toRelativeURL(HttpServletRequest request, String contextURL) {
		return request.getContextPath() + contextURL;

	}

	/*
	 * 将 array 中的所有元素用指定的 separator 连成一个字符串; by: Li Chengzhao
	 */
	protected String arrayToString(Object[] array, String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null) {
			for (int i = 0; i < array.length; i++)
				result.append(separator + array[i].toString());
			if (result.length() > 0)
				result.deleteCharAt(0);
		}
		return result.toString();
	}    
    
    protected BeanFactory getBeanFactory(){
    	return this.getWebApplicationContext();
    }
  	
    /**
     * redirect to other urls
     * @param mapping ActionMapping
     * @param name String
     * @param value String[]
     * @return ActionForward
     */
    public ActionForward setForwardPath(ActionMapping mapping, String name,
                                        String[] value) {
        ActionForward source = mapping.findForward(name);
        String path = source.getPath();
        ActionForward forward = new ActionForward(source);
        forward.setPath(format(path, value));
        return forward;

    }
    public ActionForward setForwardPath(ActionMapping mapping, String name,
            String value) {
    	String[] ret = new String[1];
    	ret[0] = value;
    	return this.setForwardPath(mapping, name, ret);
    }
    
  	protected String getIpAddr(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    		ip = request.getHeader("Proxy-Client-IP");      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    		ip = request.getHeader("WL-Proxy-Client-IP");      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    		ip = request.getRemoteAddr();      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		throw new SiteException("获取IP失败");
    	}
    	return ip;    
    }
}
