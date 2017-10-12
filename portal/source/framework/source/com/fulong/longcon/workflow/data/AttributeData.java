package com.fulong.longcon.workflow.data;

/**
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
public class AttributeData extends BasicData{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1870842326224411272L;
	private String name;
    private int type;
    private String value;
    private String objectId;
    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }



}
