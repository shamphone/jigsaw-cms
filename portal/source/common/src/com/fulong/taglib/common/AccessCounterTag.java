package com.fulong.taglib.common;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.counter.AccessCounterRepository;
import com.fulong.taglib.SpringTagSupport;
/**
 *应用系统中可以把计数器标签放置到Jsp页面上来处理计数。
 * <fulong:counter name=”对象ID”  display=”yes/no”  increase=”yes/no”/>
 * 该标签的作用是：将对象的计数值增加1，同时显示出计数器的值。在上述例子中，需要在页面上放4个计数器标签。Display属性确定是否在页面上显示计数值，为no则仅计数不显示，缺省为Yes。Increase属性确定是否要增加计数，no表示仅显示不增加，缺省为yes。

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
public class AccessCounterTag
    extends SpringTagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5499840514793239736L;
	private static Log log = LogFactory.getLog(AccessCounterTag.class);
    public static final String APPLICATION_COUNTER = "Application_Counter";
    private String name;
    private boolean display;
    private boolean increase;
    private String property;
    private TagUtils utils = TagUtils.getInstance();
    private String scope;
    public AccessCounterTag() {
        this.display = true;
        this.increase = true;
        this.name = null;
    }



    public int doStartTag() throws JspException {
        String id=this.getId();
        if(id==null)
            id = (String) utils.lookup(pageContext, name, property, scope);
        if (id != null) {
            this.name = id;
        }
        ServletContext context = pageContext.getServletContext();
        try {
            AccessCounterRepository repository = (AccessCounterRepository)this.getBeanFactory().getBean("acccessCounterRepository");
            if (this.increase) {
            	repository.increase(this.name);
            }
            if (this.display) {
                long value = repository.getCount(this.name);
                context.setAttribute(APPLICATION_COUNTER,new Long(value));
                TagUtils.getInstance().write(this.pageContext, "" + value);
            }
        }catch (Exception ex) {
            log.error("Unable to record count ", ex);
            throw new JspException(ex);
        }
        return SKIP_BODY;
    }

    /**
     * 计数器名字,必须
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 是否显示计数值,缺省为true
     * @param display boolean
     */
    public void setDisplay(boolean display) {
        this.display = display;
    }

    /**
     * 是否增加计数，缺省为true;
     * @param increase boolean
     */
    public void setIncrease(boolean increase) {
        this.increase = increase;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isDisplay() {
        return display;
    }

    public boolean isIncrease() {
        return increase;
    }

    public String getProperty() {
        return property;
    }

    public String getScope() {
        return scope;
    }



    public void release() {
        super.release();
        this.id=null;
        this.display = true;
        this.increase = true;
        this.name = null;
    }
}
