package com.fulong.longcon.site.impl;

import org.springframework.beans.factory.BeanNameAware;

import com.fulong.longcon.site.ChannelType;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class ChannelTypeSupport implements ChannelType,BeanNameAware {
    private String name;
    private String displayName;
    private String className;
    private String postfix;
    private String prefix;
  
    public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setClassName(String className) {
		this.className = className;
	}

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
    
 
	@Override
	public String getClassName() {
		return this.className;
	}

	@Override
	public String getPostfix() {
		 return this.postfix;
	}
	
	public void setPostfix(String postfix){
		this.postfix=postfix;
	}

	@Override
	public void setBeanName(String name) {
		 this.name = name;
	}
}
