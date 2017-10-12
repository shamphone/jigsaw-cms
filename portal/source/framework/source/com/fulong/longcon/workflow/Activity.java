package com.fulong.longcon.workflow;



/**
 * 或称为活动，描述工作流中的一个状况。
 * 每个工作流都缺省的有一个启示状态，一个或者多个终止状态。
 * 内容被创建和保存之后，即处于起始状态，工作流开始。
 * 状态之间的切换是通过转移来控制的。
 * 工作流的授权是以状态而不是转移为基础的。
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */

public interface Activity {   
	public static final int BEGIN = 1;
	public static final int END = 2;
	public static final int IMPLEMENTATION = 3;
	public static final int INTERACTION = 4;
	public static final int ROUTE = 5;

    /**
     * 唯一标识符，由系统指定。
     * @return String
     */
    public String getId();
    
    /**
     * 名称
     * @return String
     */
    public String getName();

    /**
     * 描述，可以是一个非常大的字符串
     * @param locale String
     * @return String
     */
    public String getDescription();
    
    /**
     * 活动类型,参见常量定义
     * @return
     */
    public int getType();

    /*
     * 所在的流程定义
     * @return ProcessDefinition
     */
    public ProcessDefinition getDefinition();

    /**
     * 是否支持对多个内容的处理
     * @return boolean
     */
    public boolean isMultiple();

    /**
     * 获取输入操作
     * @return Transition[]
     */
    public Transition[] getIn();

    /**
     * 获取输出操作
     * @return Transition[]
     */
    public Transition[] getOut();


    /**
     *
     * @return boolean
     */
    public boolean isEnd();

    /**
     *
     * @return boolean
     */
    public boolean isStart();    
    
    /**
     * 活动中有一些属性是用来说明活动运行控制的详细特性的。自动控制模型明确了，当活动被出发或者
     * 停止时的自动化程度。有两个自动控制模型，如下：
     * ● 自动控制模型（Automatic mode ）是一个完全由工作流机自动控制的模型，例如一旦满足转移条
     * 件，工作流机就会自动执行工作流过程中的活动。同样，当一个活动完成时，自动运行其离开动作。
     * ● 人工模型（Manual mode）需要用户交互来引发活动的开始或结束。在这样的系统中，活动的开始
     * 或者完成都是用户交互的结果。
     * 自动控制模型中活动的开始和结束，可以单独定义。
     * @return
     */
    public ExecutionMode getStartMode();
    /**
     * 启动模式，自动控制，或者人工模式，缺省为人工；
     * @param mode
     */
    public void setStartMode(ExecutionMode mode);
    
    /**
     * 结束模式，缺省为自动
     * @return
     */
    public ExecutionMode getFinishMode();
    /**
     * 设置结束模式，缺省为自动
     * @param mode
     */
    public void setFinishMode(ExecutionMode mode);
    
    /**
     * 执行活动
     * @param node
     * @return
     */
    public void enter(WorkItem node) throws Exception;
    /**
     * 执行活动
     * @param node
     * @return
     */
    public void execute(WorkItem node) throws Exception;
/**
 * 设置描述
 * @param description
 */
	public void setDescription(String description);

/**
 * 设置名称
 * @param name
 */

	public void setName(String name);



}
