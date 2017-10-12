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
public class ProcessDefinitionData extends BasicData {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4789386955669892551L;
	private String pkid;
    private String name;
    private String beginID;
    private String type;
    public ProcessDefinitionData() {

    }


    public String getPkid() {
        return pkid;
    }

    public String getName() {
        return name;
    }

    public String getBeginID() {
        return beginID;
    }

    public String getType() {
        return type;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginID(String beginID) {
        this.beginID = beginID;
    }

    public void setType(String type) {
        this.type = type;
    }



}
