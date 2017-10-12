package com.fulong.taglib.portal;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.struts.taglib.TagUtils;
import org.springframework.util.StringUtils;

import com.fulong.common.DomUtils;
import com.fulong.common.FileUtils;
import com.fulong.longcon.site.Channel;
import com.fulong.portal.core.Constants;
import com.fulong.taglib.SpringTagSupport;


/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-8-24	
 * @version 1.0.1
 */
public class DefinePageTag extends SpringTagSupport {
	
	private static final long serialVersionUID = -3942894451476750414L;
	private static final String TMP_DIR = "/repeater";
	private static final String TMP_SUFFIX = ".tmp";
	private String contentName;					//指定内容
	private String contentProperty;				//指定内容
	private String contextPath;					//定义页面所在应用，如不指定，默认为当前模板应用
	private String id;							//类似struts的define标记 定义一个bean
	private String part;						//指定占位符所属部分    如表格重复器分head、body、foot三部分 ，默认为空
	private String scope;						//contentName所在scope
	private String toScope;						//定义id的scope  默认为page
	
	public DefinePageTag() {
		super();
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public void setContentProperty(String contentProperty) {
		this.contentProperty = contentProperty;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public void setToScope(String toScope) {
		this.toScope = toScope;
	}
	
	

	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		assertNotNull(id, "'id' must not be null.");
		
		//获取临时文件路径
		String tmpPath = getTmpPath();
		
		//获取片段所在模板应用
		ServletContext context = getIncludedContext();
		
		File tmpFile = new File(context.getRealPath(tmpPath));
		//如果临时文件不存在，创建临时文件
		if(!tmpFile.exists()){
			String content = (String) TagUtils.getInstance().lookup(pageContext, contentName, contentProperty, scope);
			if(content==null){
				content = "";
			}
			content = DomUtils.antiFilter(content);
			
			if(!tmpFile.getParentFile().exists()){
				tmpFile.getParentFile().mkdirs();
			}
			
			try {
				FileUtils.write(tmpFile, content);
			} catch (IOException e) {
				if(tmpFile.exists()){
					tmpFile.delete();
				}
				String msg = "Can't write clip content into file:"+tmpFile.getPath()+".";
				log.error(msg, e);
				throw new JspException(msg, e);
			}
		}
		
		int mScope = PageContext.PAGE_SCOPE;
		if(toScope!=null){
			try {
				mScope = TagUtils.getInstance().getScope(toScope);
			} catch (Exception e) {
	            log.warn("toScope was invalid name so we default to PAGE_SCOPE",e);
			}
		}
		pageContext.setAttribute(id, tmpPath, mScope);
		
		return EVAL_PAGE;
	}
	
	private void assertNotNull(Object o,String msg) throws JspException{
		if(o==null){
			log.error(msg);
			throw new JspException(msg);
		}
	}
	
	private ServletContext getRootContext() {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		while(request instanceof HttpServletRequestWrapper){
			request =(HttpServletRequest)((HttpServletRequestWrapper)request).getRequest();
		}
		return request.getSession().getServletContext();
	}
	
	/**
	 * 获取重复器片段的临时路径
	 * @return
	 * @throws JspException
	 */
	private String getTmpPath() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String portletId = (String) request.getAttribute(Constants.ATTRIBUTE_PORTLET_ID);
		assertNotNull(portletId, "portletId must not be null.");

		Channel channel = this.getCurrentChannel(request);
		assertNotNull(channel, "channel must not be null.");

		String tmpName = channel.getContextPath();
		if (request.getServletPath().endsWith(".bak.bak")) {
			tmpName += ".bak.bak";
		}
		tmpName += "_" + portletId;
		if(part!=null){
			tmpName += "."+part;
		}
		tmpName += TMP_SUFFIX;
		tmpName = StringUtils.cleanPath(tmpName);
		tmpName = tmpName.replaceAll("/", "_");
		String tmpPath = TMP_DIR + "/" + tmpName;
		return tmpPath;
	}
	
	private ServletContext getIncludedContext(){
		ServletContext context = null;
		if(contextPath==null){
			context = getRootContext();
		}else{
			context = pageContext.getServletContext().getContext(contextPath);
		}
		return context;
	}
	
	public static class TEI extends TagExtraInfo {

		public VariableInfo[] getVariableInfo(TagData tagData) {
			VariableInfo[] info = new VariableInfo[] {
					new VariableInfo(tagData.getAttributeString("id"), "java.lang.String", true, VariableInfo.AT_BEGIN) };

			return info;
		}
	}

}



