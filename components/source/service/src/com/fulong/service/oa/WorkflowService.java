package com.fulong.service.oa;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.value.ReferenceValue;
import com.fulong.service.NodeObject;
import com.fulong.service.NodeService;

/**
 * 文件传阅串行、并行处理服务
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
public class WorkflowService extends NodeService {
	private Repository repository;
	public void setRepository(Repository repository){
		this.repository= repository;
	}
	
	public void nodeCreated(NodeObject event) throws ServletException {
		Node node = event.getNode();
		if(node!=null){
			NodeDefinition def = node.getDefinition();
			if(!def.isNodeType("2467761993594")){
				return;
			}else{
				String shunxu = node.getProperty("prop296").getString();// 传阅顺序
				Value cyr[] = node.getProperty("prop281").getValues();// 传阅人
				Value dcyr[] = node.getProperty("prop625").getValues();// 当前传阅人
				if (shunxu.equals("2474679436283")) { // 串行
					if (cyr.length > 0) {
						if (dcyr.length == 0) {
							dcyr = new Value[1];
							dcyr[0] = cyr[0];
						} else {
							Value n = null;
							for (int i = 0; i < cyr.length; i++) {
								if (dcyr[0].equals(cyr[i]) && i < cyr.length - 1) {
									n = cyr[i + 1];
								}
							}
							if (n != null) {
								dcyr = new Value[1];
								dcyr[0] = n;
							} else {
								dcyr = null;
							}
						}
					}
				} else if (shunxu.equals("2474679436284")) { // 并行
					if (dcyr.length == 0) {
						dcyr = cyr;
					}
				}
				node.getProperty("prop625").setValue(dcyr);
				// 初始化传阅回复
				List<String> valueList = new ArrayList<String>();
				for (int i = 0; i < cyr.length; i++) {
					Node huifuren = ((ReferenceValue) cyr[i]).getReference();
					Node huifu = ((Node) huifuren).addNode(repository.getDefinitionManager().getDefinition("2471074613468"),"contents"); // 设置大纲为传阅回复
					huifu.setProperty("creator", huifuren);
					valueList.add(huifu.getID());
				}
				node.getProperty("prop609").setValue(valueList.toArray(new String[valueList.size()]));
			}
		}		
	}
}
