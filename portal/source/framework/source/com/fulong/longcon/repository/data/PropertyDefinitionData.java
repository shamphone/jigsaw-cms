package com.fulong.longcon.repository.data;

/**
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
public class PropertyDefinitionData extends BasicData {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3401792926834245120L;
	private int type;  //1表示字符串，10表示clob之类
    private String name; //
    private String ID; //
    private String nodeDefinitionID; //对应的nodeDefinition的ID
    private boolean multiple; //表示是否为多值属性
    private int minLength; //当为多值属性的时候，表示最少可以有的值
    private int maxLength; //当为多值属性的时候，表示最多可以有的值
    private int orderNo; //序号
    private String description; //
    private String referenceType; //
    private String enumEntry; //
    private boolean deletable;  //是否可以删除
    private String nodeType; //复合属性时,表示该属性用的nodedefinition的ID
    private boolean readOnly;//属性值是否是继承自父结点的
    public PropertyDefinitionData() {
        this.minLength = -1;
        this.maxLength = -1;
        this.name = null;
        this.description = null;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodeDefinitionID(String nodeDefinitionID) {
        this.nodeDefinitionID = nodeDefinitionID;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }


    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public void setEnumEntry(String enumEntry) {
        this.enumEntry = enumEntry;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNodeDefinitionID() {
        return nodeDefinitionID;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }


    public int getOrderNo() {
        return orderNo;
    }

    public String getDescription() {
        return description;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public String getEnumEntry() {
        return enumEntry;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public String getNodeType() {
        return nodeType;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public String getID() {
        return ID;
    }
}
