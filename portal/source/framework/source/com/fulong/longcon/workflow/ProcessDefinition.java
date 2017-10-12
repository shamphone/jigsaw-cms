package com.fulong.longcon.workflow;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * 是一个业务流程或过程的规格化描述。
 * 系统支持多支单线程的工作流，任何时刻内容只能处于工作流的某个状态上，而不能同时处于多个状态上。
 * 例如，上图所示的工作流，“正在初审”状态的内容不能同时被移交到“质量审核”和“紧急审核”。
 * 每个流程只有一个开始活动，可以有多个结束活动。
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
public interface ProcessDefinition {
    /**
      * 唯一标识符，由系统指定，只读。
      * @return String
      */
     public String getId();

     /**
      * 名称
      * @return String
      */
     public String getName();
     /**
      * 设置名称
      * @param name
      */
     public void setName(String name);

     /**
      * 描述，可以是一个非常大的字符串
      * @param locale String
      * @return String
      */
    public String getDescription();
    /**
     * 设置描述
     * @param desc
     */
    public void setDescription(String desc);
    
    
    public Parameters getFormalParameters();
    /**
     * 获取起始活动，每个流程只能有一个初始活动，如果为空，则表明该流程未设置初始活动
     * @return Activity  
     */
    public Activity getBegin();

    /**
     * @return Activity
     */
    public Activity getEnd();

    /**
     * 获取指定标识的转移
     * @param ID String
     * @return Transition
     */

    public Transition getTransition(String ID);

    /**
     * 获取该工作流的所有转移
     * @return Transition[]
     */
    public Transition[] getTransitions();
    
    /**
     * 创建转移
     * @param begin
     * @param end
     * @return
     */
    public Transition createTransition(String ID,Activity begin, Activity end);
    
    /**
     * 删除转移
     * @param transition
     */
    public void delete(Transition transition);
    
    /**
     * 创建普通的交互活动
     * @param name 活动名称，中文
     * @return
     */
    public Activity createActivity(String ID,String name);
    
    /**
     * 创建路由活动
     * @param name
     * @return
     */
    public Activity createRouteActivity(String ID,String name);

    /**
     * 创建自动活动
     * @param service 服务标识
     * @param name
     * @return
     */
    public Activity createTaskActivity(String ID,String service, String name);

    /**
     * 获取该工作流的所有活动
     * @return Activity[]
     */
    public Activity[] getActivities();

    /**
     * 获取指定标识的活动
     * @param ID String
     * @return Activity
     */

    public Activity getActivity(String ID);
    
    
    /**
     * 删除活动，同时删除和活动相关联的转移
     * @param activity
     */
    public void delete(Activity activity);
    
    /**
     * 存储用于图形显示工作流的xml文档
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * 
     */
    public void setXML(String graphic) throws ParserConfigurationException, SAXException, IOException;
    
    /**
     * 获取用于图形显示工作流的xml文档
     * @return
     * @throws TransformerConfigurationException 
     * @throws TransformerException 
     */
    public String getXML() throws TransformerConfigurationException, TransformerException;
  
    /**
     * 所有的更改必须调用这个保存才能够生效
     * @throws IOException
     * @throws SAXException 
     * @throws ParserConfigurationException 
     */
    public void save() throws IOException, ParserConfigurationException, SAXException;

	public String getModule();

}
