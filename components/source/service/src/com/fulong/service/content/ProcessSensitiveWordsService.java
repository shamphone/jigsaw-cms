package com.fulong.service.content;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @date 2011-01-13	
 * @version 1.0
 */
public class ProcessSensitiveWordsService extends NodeService {
	private static final Log log = LogFactory.getLog(ImageCompressService.class);
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		String sensitiveCategoryID = parameters.getValue("sensitiveCategoryID");
		String checkingPropID = parameters.getValue("checkingPropID");
		String sensitivePropID = parameters.getValue("sensitivePropID");
		String contentOption = parameters.getValue("contentOption");
		String destCategoryID = parameters.getValue("destCategoryID");
		String modifyPropID = parameters.getValue("modifyPropID");
		String destValue = parameters.getValue("destValue");
		String replaceValue = parameters.getValue("replaceValue");
		String regExp = "";
		boolean doOption = false;
		
		NodeDefinition sensitiveCategory = this.repository.getDefinitionManager().getDefinition(sensitiveCategoryID);
		if(sensitiveCategory!=null){
			PropertyDefinition sensitiveField = sensitiveCategory.getPropertyDefinition(sensitivePropID);
			if(sensitiveField!=null){
				Query query = this.repository.getQueryManager().createQuery(sensitiveCategory, Query.SQL);
				NodeIterator<Node> nodes = query.nodes();
				while(nodes.hasNext()){
					Node sensitiveNode = nodes.nextNode();
					Value[] values = sensitiveNode.getProperty(sensitivePropID).getValues();
					if(values!=null){
						for(int i=0;i<values.length;i++){
							if(values[i].getString()!=null&&!values[i].getString().equals("")){
								if(regExp.length()!=0){
									regExp += "|";
								}
								regExp += ("("+values[i].getString()+")");
							}
						}
					}
				}
			}
			Property checkingProp = node.getProperty(checkingPropID);
			if(regExp!=null&&regExp.length()!=0){
				if(checkingProp!=null){
					Value[] values = checkingProp.getValues();
					Pattern search = Pattern.compile(regExp); 
					if(values!=null&&values.length>0){
						for(int i=0;i<values.length;i++){
							Value value = values[i];
							if(value!=null&&value.getString()!=null){
								doOption = search.matcher(value.getString()).find();
								if(doOption){
									break;
								}
							}
						}
					}
				}
			}
			if(doOption&&contentOption!=null&&!contentOption.equals("")){
				NodeDefinition destCategory = this.repository.getDefinitionManager().getDefinition(destCategoryID);
				PropertyDefinition modifyProp = node.getDefinition().getPropertyDefinition(modifyPropID);
				if(contentOption.equals("move")){
					if(destCategory!=null){
						node.setDefinition(destCategory);
					}
				}else if(contentOption.equals("copy")){
					Node newNode = node.clone();
					newNode.setDefinition(destCategory);
				}else if(contentOption.equals("recommend")){
					node.addMixinDefinition(destCategory);
				}else if(contentOption.equals("modifyProperty")){
					if(modifyProp!=null){
						node.setProperty(modifyPropID, destValue);
					}
				}else if(contentOption.equals("replace")){
					Value[] values = checkingProp.getValues();
					if(values!=null&&values.length>0&&replaceValue!=null){
						List<Value> list = new ArrayList<Value>();
						for(int i=0;i<values.length;i++){
							Value value = values[i];
							if(value!=null&&value.getString()!=null){
								String newString = value.getString().replaceAll(regExp, replaceValue);
								list.add(this.repository.getValueFactory().createValue(newString));
							}
						}
						node.setProperty(checkingPropID,list.toArray((Value[])Array.newInstance(values.getClass().getComponentType(), 0)));
					}
				}else if(contentOption.equals("remove")){
					this.repository.delete(node);
				}
			}
		}
	}
}
