package com.fulong.portal.page;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */

class RemoveNodesFilter implements NodeFilter {
	
	private static final long serialVersionUID = -2582375280995664667L;
	
	@SuppressWarnings("unchecked")
	private Class[] clazz;
    
    @SuppressWarnings("unchecked")
	public RemoveNodesFilter(Class[] clazz) {
        this.clazz = clazz;
    }

    public boolean accept(Node node) {
        boolean removable = false;
        for (int i = 0; i < clazz.length; i++) {
            if (clazz[i].isInstance(node)) {
                removable = true;
            }
        }
        if (removable) {
            Node parent = node.getParent();
            parent.getChildren().remove(node);
            return false;
        }
        return true;
    }

}
