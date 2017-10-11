package com.fulong.service.oa;

import com.fulong.service.NodeService;

/**
 * 
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
public class ReplyService extends NodeService {
	/*
	public void nodeCreated(NodeEvent event) throws PortletServiceException {
		Node node = event.getNode();
		Node refNode = event.getRefNode();
		if(node!=null){
			if(refNode!=null){
				NodeDefinition def = node.getDefinition();
				if(!def.isNodeType("2467761993594")){
					return;
				}else{					
					String shunxu = node.getProperty("prop296").getString();// 传阅顺序
					Value cyr[] = node.getProperty("prop281").getValues();// 传阅人
					Value dcyr[] = node.getProperty("prop625").getValues();// 当前传阅人
					Node jielunNode = refNode.getProperty("prop968").getReference();// 回复结论
					String jielun = jielunNode.getProperty("prop750").getString(); // 通过标记
					if (shunxu.equals("2474679436283")) { // 串行
						if (cyr.length > 0 && dcyr.length > 0) {
							Value n = null;
							for (int i = 0; i < cyr.length; i++) {
								if (dcyr[0].equals(cyr[i]) && i < cyr.length - 1) {
									n = cyr[i + 1];
								}
							}
							if (n != null && "1".equals(jielun)) {
								dcyr = new Value[1];
								dcyr[0] = n;
							} else {
								dcyr = null;
							}
						}
					}
					node.getProperty("prop625").setValue(dcyr);
					Property property = node.getProperty("prop609");
					Value values[] = property.getValues();
					Map<String, String> valueMap = new HashMap<String, String>();
					for (int i = 0; i < values.length; i++) {
						Node huifu = ((ReferenceValue) values[i]).getReference();
						valueMap.put(huifu.getProperty("creator").getString(), huifu.getID());
					}
					valueMap.put(refNode.getProperty("creator").getString(), refNode.getID());
					node.getProperty("prop609").setValue(valueMap.values().toArray(new String[valueMap.size()]));// 关联传阅
				}
			}			
		}
				
	}	*/
}
