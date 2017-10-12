package com.fulong.cms;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;

/**
 * 复合属性节点排序中的上移操作
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class UpCompositeNodeAction extends CMSBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.CMSBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean ok = false;
		Writer writer = response.getWriter();
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
		
		String parentID = request.getParameter("parentID");
		String fixPropID = request.getParameter("fixPropID");
		String childID = request.getParameter("childID");
		if(parentID!=null&&!parentID.equals("")){
			Node parent = this.getRepository(request).getNode(parentID);
			if(parent!=null){
				if(fixPropID!=null&&!fixPropID.equals("")){
					NodeIterator childs = parent.getNodes(fixPropID);
					if(childID!=null&&!childID.equals("")){
						Node child = this.getRepository(request).getNode(childID);
						if(child!=null){
							List<Node> list = new ArrayList();
							while(childs.hasNext()){
								list.add(childs.nextNode());
							}
							int count = 0;
							for(int i=0;i<list.size();i++){
								if(list.get(i).getOrderNo()==child.getOrderNo()){
									count = i;
									break;
								}
							}
							if(count>0){
								int temp = child.getOrderNo();
								child.setOrderNo(list.get(count-1).getOrderNo());
								list.get(count-1).setOrderNo(temp);
								ok = true;
							}							
						}
					}
				}				
			}
		}
		if(ok){
			writer.append("true");
		}else{
			writer.append("false");
		}
		writer.close();
		return null;
	}

}
