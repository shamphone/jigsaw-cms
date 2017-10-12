package com.fulong.longcon.repository;

import java.util.Calendar;

/**
 * 属性定义。这里描述每一个内容库描述中支持的属性定义。
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public interface PropertyDefinition {
    /**
       * 属性的标识符,和属性定义中的ID是相同的。
       * @return String 标识符
       */
      public String getID();

      /**
       * 名称，可以使用中文
       * @return java.lang.String
       */

      public String getName();

      /**
       * 属性的类型 参考PropertyType接口定义
       * @return int
       */
      public int getType();

      /**
       *
       * @return String
       */
      public String getTypeName();


      /**
       * 重命名
       * @param name String
       */
      public void setName(String name);

      /**
       * 描述
       * @return String
       */
      public String getDescription();

      /**
       * 描述
       * @param description String
       */
      public void setDescription(String description);

      /**
       * 该节点定义/属性定义是不能删除的
       * @return boolean
       */
      public boolean isProtected();

      /**
       * 当前节点/属性定义所在的节点定义.对于属性定义，必须有一个返回值，确定这个属性定义所在的节点。
       * 而对于节点定义，如果这个值为空，则表明这个节点定义没有父节点
       * @return NodeDefinition
       */
      public NodeDefinition getDeclaringNodeDefinition();

      /**
       * 是否是多值
       * @return boolean
       */
      public boolean isMultiple();

      /**
       * 如果是枚举项，则返回可以选择的最小个数。如果是非枚举项，则返回1表示这个值是必填的，0为可选的。
       * @return int
       */
      public int getMinLength();

      /**
       * 如果是枚举项，则返回可以选择的最大个数。如果是非枚举项，该方法无效。
       * @return int
       */
      public int getMaxLength();

      /**
       * 设置枚举值的最大和最小个数。如果是非枚举项，需要限定必选属性值，则调用<code>setLength(1,1)</code>即可
       * @param min int
       * @param max int
       * @return int
       */
      public void setLength(int min, int max);

      /**
       * 获得属性定义的序号
       * @return int
       */
      public int getOrderNo();

      /**
       * 设置属性定义的序号
       * @param orderNo int
       */
      public void setOrderNo(int orderNo);

      /**
       * 设置是否多值
       * @param multiple boolean
       */
      public void setMultiple(boolean multiple);
      /**
       * 设置属性（系统基本属性或固有属性默认为只读属性）
       * @param readonly boolean
       */
      public void setReadonly(boolean readonly);
      /**
       * 属性是否为只读属性
       * @return boolean
       */
    public boolean isReadonly();
    /**
     * 缺省值
     * @return java.lang.String
     */
    public Value[] getDefaultValues();

    /**
     * 缺省值
     * @return java.lang.String
     */
    public Value getDefaultValue();

    /**
     * 缺省值
     * @return java.lang.String
     */
    public void setDefaultValues(Value[] values);

    /**
     * 缺省值
     * @return java.lang.String
     */
    public void setDefaultValue(String value);

    /**
     * 缺省值
     * @return java.lang.String
     */
    public void setDefaultValue(long value);

    /**
     * 缺省值
     * @return java.lang.String
     */
    public void setDefaultValue(Calendar value);

    /**
     * 缺省值
     * @return java.lang.String
     */
    public void setDefaultValue(double value);


    /**
     * 获取类型的数据验证。返回数组中的每个值限定了属性的值。限制条件是AND关系。即<code>["constraint1", "constraint2",
     * "constraint3"]</code>意味着这个属性值必须满足constraint1, constraint2和constraint3三个条件。
     * <p>
     * 这个数组返回null表明这个功能未实现，返回空数组表明有数据校验功能，但是没有具体的校验。
     * </p>
     * <p>
     * 对于多值属性，每个值都必须满足条件要求。具体数据校验的表达式和属性类型有关：
     * <ul>
     * <li>
     * <code>STRING</code>: 属性校验是一个正则表达式。
     * </li>
     * <li>
     * <code>PATH</code>: </li>
     * <li>
     * <code>NAME</code>:
     * </li>
     * <li>
     * <code>REFERENCE</code>:
     * </li>
     * <li>
     * <code>BOOLEAN</code>: "<code>true</code>" 或"<code>false</code>".
     * </li>
     * </ul>
     * 以下类型都使用最大、最小值来做限制，限制语法中需包含开、闭区间。
     * 例如 "<code>[min, max]</code>", "<code>(min, max)</code>",
     * "<code>(min, max]</code>" 或"<code>[min, max)</code>"。
     * <ul>
     * <li>
     * <code>BINARY</code>: <code>min</code>和<code>max</code>指字节数限制。
     * </li>
     * <li>
     * <code>DATE</code>: <code>min</code> and <code>max</code> 日期限制，使用
     * <code>YYYY-MM-DDThh:mm:ss.sssTZD</code>格式。
     * </li>
     * <li>
     * <code>LONG</code>, <code>DOUBLE</code>: 最大数和最小数。
     * </li>
     * </ul>
     * @return a <code>String</code> array.
     */

    public String[] getValueConstraints();

    /**
     * 设置数据验证
     * @param constraints String[]
     */
    public void setValueConstraints(String[] constraints);

    /**
     * 设置数据验证
     * @param constraints String[]
     */
    public void setValueConstraint(String constraint);

    /**
     * 枚举值的根节点
     * @return String
     * @deprecated,使用public String getReferenceType();
     */
    public String getEnumEntry();

    /**
     * 设置枚举型值的跟节点
     * @param entry String
     * @deprecated，使用public void setReferenceType(String referenceType);
     */
    public void setEnumEntry(String entry);

    /**
     * 获取引用类型的属性定义具体的引用类型
     * @return String
     */
    public String getReferenceType();

    /**
     *
     * @return NodeDefinition
     */
    public NodeDefinition getReferenceDefinition();

    /**
     * 设置引用类型的属性定义具体的引用类型
     * @param entry String
     */
    public void setReferenceType(String referenceType);

    /**
     *是否是必填
     * @return boolean
     */
    public boolean isRequired();


}
