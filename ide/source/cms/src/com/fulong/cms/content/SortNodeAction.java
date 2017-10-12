package com.fulong.cms.content;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.workflow.Activity;
import com.fulong.longcon.workflow.ProcessDefinition;
import com.fulong.portlet.ServletNodeWorkItem;

/**
 * 支持上移下移和输入序号的node排序
 * 
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
public class SortNodeAction extends BatchAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.content.ContentBaseAction#doExecute(com.fulong.longcon.repository.Node[], com.fulong.longcon.repository.NodeDefinition, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doExecute(Node[] nodes, NodeDefinition definion,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean ok = true;
		Writer writer = response.getWriter();
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        
        String nodeID = request.getParameter("contentId");
        String shift = request.getParameter("shift");
        String down = request.getParameter("down");
        String orderNum = request.getParameter("orderNum");
        if(nodeID!=null&&!nodeID.equals("")){
        	Node node = this.getRepository(request).getNode(nodeID);
        	int thisOrderNum = node.getOrderNo();
        	int termOrderNum = 0;
        	int begin = 0;
            int end = 0;
        	if(node!=null){
        		NodeDefinition def = node.getDefinition();node.getParent().getID();node.getName();
        		Query query = this.getRepository(request).getQueryManager().createQuery(def, Query.SQL);
        		query.sortByOrdinal(true);
        		query.filterByParentAndName(node.getParent().getID(), node.getName(), false);
        		NodeIterator<Node> contents = query.nodes();
        		List<Node> list = new ArrayList<Node>();
        		while(contents.hasNext()){
                    list.add(contents.nextNode());
                }
        		if(list.size()>1){
        			int cont = 0;
        			for(int i=0;i<list.size();i++){
        				if(list.get(i).getOrderNo()==thisOrderNum){
        					cont = i;
        					break;
        				}
        			}
        			if(shift!=null&&shift.equals("true")){  //上移操作
            			if(cont>0){
            				termOrderNum = list.get(cont-1).getOrderNo();
            			}
            		}else if(down!=null&&down.equals("true")){   //下移操作
            			if(cont<list.size()-1){
            				termOrderNum = list.get(cont+1).getOrderNo();
            			}
            		}else if(orderNum!=null&&!orderNum.equals("")){  //自定义序号排序操作
            			try{
            				termOrderNum = Integer.parseInt(orderNum);
            			}catch(Exception ex){
            				termOrderNum = 0;
            			}            			
            		}
                    if(termOrderNum>list.size())
                    	termOrderNum = list.size();
                    if(termOrderNum<1)
                    	termOrderNum = 1;       	
        			if(termOrderNum!=0){
        				if (thisOrderNum > termOrderNum) {   //目标序号小于原序号，即往前排
                            for (int i = 0; i < list.size(); i++) {
                                Node oNode = (list.get(i));
                                if (oNode.getOrderNo() == termOrderNum){
                                    begin = i;
                                }
                                if (oNode.getOrderNo() == thisOrderNum){
                                    end = i;
                                }
                            }
                            for(int j=begin;j<end;j++){
                                list.get(j).setOrderNo(list.get(j+1).getOrderNo());
                            }
                            list.get(end).setOrderNo(termOrderNum);
                        }
                        else if(thisOrderNum < termOrderNum){    //目标序号大于原序号，即往后排                        	
                            for (int u = 0; u < list.size(); u++) {
                                Node ooNode = list.get(u);
                                if (ooNode.getOrderNo() == thisOrderNum){
                                    begin = u;
                                }
                                if (ooNode.getOrderNo() == termOrderNum){
                                    end = u;
                                }
                            }
                            for(int z=end;z>begin;z--){
                                list.get(z).setOrderNo(list.get(z-1).getOrderNo());
                            }
	                            list.get(begin).setOrderNo(termOrderNum);
                        }
        			}
        		}
        		processActivity(request, response, node);
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

	private void processActivity(HttpServletRequest request,HttpServletResponse response,Node node) throws Exception{
		String processID = request.getParameter("processID");
		if(processID==null||processID.length()==0){
			processID="blank";
		}
		String activityID = request.getParameter("activityID");
		if(activityID==null||activityID.length()==0){
			activityID = "begin";
		}
		ProcessDefinition definition = this.getWorkflowService(request).getDefinition(processID);
		if(definition!=null){
			Activity activity = definition.getActivity(activityID);
			if(activity!=null){
				activity.execute(new ServletNodeWorkItem(node, request, response));
			}
		}
	}
	
}
