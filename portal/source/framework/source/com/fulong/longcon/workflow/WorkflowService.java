package com.fulong.longcon.workflow;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletContext;

import com.fulong.longcon.site.SiteTemplate;

/**
 * 工作流系统是以规格化的流程描述作为输入的软件组件,它维护流程的运行状态,并在人和应用之间分派活动。
 * 这个接口提供工作流接口访问的总入口。
 *
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public interface WorkflowService {   

    /**
     * 根据 ID来查找指定的工作流
     * @param id String
     * @return ProcessDefinition
     */
    public ProcessDefinition getDefinition(String id);

    /**
     * 获取指定机构所拥有的所有工作流定义
     * @return Iterator
     */
    
    public Iterator<ProcessDefinition> getModuleDefinitions(String module);
    /**
     * 获取指定机构所拥有的所有工作流定义
     * @return Iterator
     */
    public Iterator<ProcessDefinition> definitions();
    
    /**
     * 创建一个空的工作流
     * @param id
     * @return
     */
    public ProcessDefinition create(String id,SiteTemplate template);
    
    /**
     * 加载NodeDefinition
     * @param file
     * @return
     */
    public void load(ServletContext servletContext)  throws IOException;
    /**
     * 删除指定工作流
     * @param definition
     */
    public void delete(ProcessDefinition definition);
    
    /**
     * 获取指定类型的应用管理器
     * @param type
     * @return
     */
    public ApplicationManager getApplicationManager();


}
