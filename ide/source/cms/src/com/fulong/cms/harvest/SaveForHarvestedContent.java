package com.fulong.cms.harvest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * 在当前category下创建内容节点
 *
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author HaoJingwei
 * @version 1.0
 */

public class SaveForHarvestedContent {
    private NodeDefinition nodeDefinition;
    private Principal owner;
    public Node node;

    /*构造函数，新建一个内容Node，并定义好nodeDefinition和propertyDefinition*/
    SaveForHarvestedContent(NodeDefinition nodeDefinition, Principal own) {
        this.nodeDefinition = nodeDefinition;
        owner = own;
        Iterator propertyDefinitions = nodeDefinition.propertyDefinitions(false);
        List nomalProperties = new ArrayList();
        List fixProperties = new ArrayList();
        while (propertyDefinitions.hasNext()) {
            PropertyDefinition propertyDefinition = (PropertyDefinition)
                    propertyDefinitions.next();
            if (propertyDefinition.getType() == 0) {
                fixProperties.add(propertyDefinition);
            } else {
                nomalProperties.add(propertyDefinition);
            }
        }
        node = ((Node) owner).addNode(nodeDefinition,
                                      nodeDefinition.getID());

    }

    /*返回一个Node,用Node创建content 然后再setProperty*/
    public Node doSaveNode() {
        //return category.getContent(node.getID());
    	return null;
    }
    /*返回在解析时需要复制(填入)的属性id，比如title,content
     *webharvest返回的内容中需要用属性id作标签
     */
    public List properties() {
        Iterator proDefs = nodeDefinition.propertyDefinitions(false);
        List pros = new ArrayList();
        while (proDefs.hasNext()) {
            PropertyDefinition proDef = (PropertyDefinition)
                    proDefs.next();
            if(proDef.getType()!=0){
                pros.add(proDef.getID());
            }
        }
        return pros;
    }
}
