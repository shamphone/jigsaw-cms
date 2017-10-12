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
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodeTypeData extends BasicData {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5672612231854810102L;
	private String ID;
    private String definitionID;
    private boolean main;
    public NodeTypeData() {
        super();
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setDefinitionID(String definitionID) {
        this.definitionID = definitionID;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public String getDefinitionID() {
        return definitionID;
    }

    public boolean isMain() {
        return main;
    }

}
