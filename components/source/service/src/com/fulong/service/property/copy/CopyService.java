package com.fulong.service.property.copy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.common.util.RangeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.longcon.repository.value.StringValue;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 同大纲下属性拷贝服务 将一个属性值复制到另一个属性，配置参数： 1．源属性： 用于复制的属性值。 2． 是否使用内容ID。 3．
 * 目标属性：将属性值复制到的目标属性。
 * 
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author sunyuchao
 * @version 3.1
 */
public class CopyService extends NodeService {
	private String editorPath;

	public void setEditorPath(String path) {
		this.editorPath = path;
	}

	public String doEdit(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters)
			throws Exception {
		return this.editorPath;
	}

	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters)
			throws Exception {
		super.doUpdate(request, response, parameters);
	}

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			Property source = null;
			List<Property> destination = new ArrayList<Property>();
			boolean increase = (parameters.getValue("increase")!=null) ? parameters.getValue("increase").equals("true"):false;
			String IDprop = parameters.getValue("IDprop");
			String definitionID = parameters.getValue("definitionID");
			String destinationPropID = parameters.getValue("destinationPropID");
			
			//目的属性
			String[] propIDs = destinationPropID.split("\\.");
			if (propIDs.length > 1) {
				NodeDefinition def = this.repository.getDefinitionManager().getDefinition(definitionID);
				PropertyDefinition prop = def.getPropertyDefinition(propIDs[0]);
				if (prop.getType() == PropertyType.FIX) {
					RangeIterator<Node> contents = node.getNodes(prop
							.getID());
					while(contents.hasNext()){
						destination.add(contents.next().getProperty(propIDs[1]));
					}
				} else if (prop.getType() == PropertyType.REFERENCE) {
					Value[] contents = node.getProperty(propIDs[0])
							.getValues();
					Node[] nodes = ValueUtils.toNodeArray(contents);
					for (int i=0; i<nodes.length; i++) {
						destination.add(nodes[i].getProperty(propIDs[1])) ;
					}
				}
			}else{
				destination.add(node.getProperty(propIDs[0]));
			}
			
			if (IDprop.equals("true")) {
				if(increase)
					for(Iterator<Property> i=destination.iterator();i.hasNext();){
						Property prop = i.next();
						StringValue sv = new StringValue();
						sv.setValue(node.getID());
						Value[] values = {sv};
						prop.setValue(this.addValue(prop.getValues(), values));
					}
				else
					for(Iterator<Property> i=destination.iterator();i.hasNext();){
						i.next().setValue(node.getID());
					}
			} else {
				String sourcePropID = parameters.getValue("sourcePropID");
				source = node.getProperty(sourcePropID);
				if(increase)
					for(Iterator<Property> i=destination.iterator();i.hasNext();){
						Property prop = i.next();
						prop.setValue(this.addValue(prop.getValues(), source.getValues()));
					}
				else
					for(Iterator<Property> i=destination.iterator();i.hasNext();){
						i.next().setValue(source.getValues());
					}
			}

		}
	}
	
	public static Value[] addValue(Value[] values1 , Value[] values2){
		List<Value> list1 = new ArrayList<Value>();
		List<Value> list2 = new ArrayList<Value>(); 
		Value value;
		
		for(int i=0; i<values1.length; i++){
			list1.add(values1[i]);
		}
		for(int j=0; j<values2.length; j++){
			list2.add(values2[j]);
		}
		
		if(list1.size() > list2.size()){
			for(int k=0; k<list1.size(); k++){
				value = list1.get(k);
				if(list2.contains(value))
					list1.remove(value);
			}
		}else{
			for(int l=0; l<list2.size(); l++){
				value = list1.get(l);
				if(list2.contains(value))
					list1.remove(value);
			}		
		}
		
		list1.addAll(list2);
		Value[] a = new Value[1];
		return list1.toArray(a);
	}
}
