package com.fulong.portlet.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.value.ReferenceValue;
import com.fulong.portlet.PortletAction;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */
public abstract class ButtionPortletAction extends PortletAction {
	public List<NodeDefinition> lookupDefinitions(ActionRequest request, String parName) {
		List<NodeDefinition> categorys = new ArrayList<NodeDefinition>();
		String[] categories = request.getPreferences().getValues(parName, new String[0]);
		for (int i = 0; i < categories.length; i++) {
			categorys.add(this.getRepository().getDefinitionManager().getDefinition(categories[i]));
		}
		return categorys;
	}

	protected void nodeAddReferenceValue(Node pNode, String prop, Node node, boolean distinct) {
		Value oldValues[] = pNode.getProperty(prop).getValues();
		if (distinct) {
			Map<String, String> valueMap = new HashMap<String, String>();
			for (int i = 0; i < oldValues.length; i++) {
				Node oldNode = ((ReferenceValue) oldValues[i]).getReference();
				valueMap.put(oldNode.getProperty("creator").getString(), oldNode.getID());
			}
			valueMap.put(node.getProperty("creator").getString(), node.getID());
			pNode.getProperty(prop).setValue(valueMap.values().toArray(new String[valueMap.size()]));
		} else {
			List<String> valueList = new ArrayList<String>();
			for (int i = 0; i < oldValues.length; i++) {
				valueList.add(((ReferenceValue) oldValues[i]).getReference().getID());
			}
			valueList.add(node.getID());
			node.getProperty("prop609").setValue(valueList.toArray(new String[valueList.size()]));
		}
	}


	
}
