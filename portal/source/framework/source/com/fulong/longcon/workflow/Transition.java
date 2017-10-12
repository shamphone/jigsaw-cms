package com.fulong.longcon.workflow;

import com.fulong.longcon.repository.Node;


/**
 * 转移定义了对内容的具体操作。
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

public interface Transition {
	
	public class Condition{
	public static final int NONE = 0;
	public static final int CONDITION = 1;
	public static final int OTHERWISE = 2 ;
	public static final int EXCEPTION = 3;
	public static final int DEFAULTEXCEPTION = 4;
	
	private static final String[] names ={"NONE","CONDITION","OTHERWISE","EXCEPTION","DEFAULTEXCEPTION"};
	
	  public static String nameFromValue(int type) {
		  if((type>=0) && (type<5))
		  return names[type];
		  return names[0];
	  }
	  
	  public static int valueFromName(String name){
		  if(name == null)
			  return 0;
		  for(int i=0;i<names.length;i++)
			  if(name.equalsIgnoreCase(names[i]))
				  return i;
		  throw new IllegalArgumentException("Unknow value for "+name+".");
	  }
	
	}
	
    /**
     * 唯一标识符，由系统指定，只读。
     * @return String
     */
    public String getId();
    
    /**
     * 类型，参看Condition定义的常量
     * @return
     */
    public int getType();

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
     * @param description
     */
    public void setDescription(String description);

    /**
     * 所在的内容定义
     * @return ProcessDefinition
     */
    public ProcessDefinition getDefinition();

    /**
     * 起始活动
     * @return Activity
     */
    public Activity getFrom();
    
    /**
     * 设置起始活动
     * @param activity
     */
    public void setFrom(Activity activity);

    /**
     * To活动
     * @return Activity
     */
    public Activity getTo();
    
    /**
     * 设置终止活动
     * @param activity
     */
    public void setTo(Activity activity);
    
    /**
     * 当前节点是否满足要求
     * @param node
     * @return
     */
    public boolean applyTo(Node node);
    
    /**
     * 获取转移条件，如果为空，则是无条件转移
     * @return
     */
    public String getCondition();
    
    /**
     * 设置转移条件
     * @param conditions
     */
    public void setCondition(String conditions);
    
}
