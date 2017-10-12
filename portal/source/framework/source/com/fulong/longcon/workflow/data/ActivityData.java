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
public class ActivityData extends BasicData{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2164367530794638265L;
	private String pkid;
    private String name;
    private String definitionID;
    private String type;
    private boolean end=false;
    private int duration;
    public String getPkid() {
        return pkid;
    }

    public String getName() {
        return name;
    }

    public String getDefinitionID() {
        return definitionID;
    }

    public String getType() {
        return type;
    }

    public boolean isEnd() {
        return end;
    }

    public int getDuration() {
        return duration;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefinitionID(String definitionID) {
        this.definitionID = definitionID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
