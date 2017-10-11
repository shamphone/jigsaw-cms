package com.fulong.service.property;



import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.counter.AccessCounterRepository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.ValueFormatException;
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
 * @author luobin
 * @date 2010-7-23	
 * @version 1.0.1
 */
public class NodeCounterService extends NodeService {
	private static final Log log = LogFactory.getLog(NodeCounterService.class);
	private AccessCounterRepository counterRepository;
	
	public void setCounterRepository(AccessCounterRepository counterRepository) {
		this.counterRepository = counterRepository;
	}

	@Override
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		String allPropId = parameters.getValue("allPropId");
		String yearPropId = parameters.getValue("yearPropId");
		String monthPropId = parameters.getValue("monthPropId");
 		String weekPropId = parameters.getValue("weekPropId");
 		String dayPropId = parameters.getValue("dayPropId");
		
 		setCount(node, allPropId, Calendar.ERA);
 		setCount(node, yearPropId, Calendar.YEAR);
 		setCount(node, monthPropId, Calendar.MONTH);
 		setCount(node, weekPropId, Calendar.WEEK_OF_MONTH);
 		setCount(node, dayPropId, Calendar.DATE);
	}
	
	private void setCount(Node node,String prop,int type){
		if(prop!=null){
			long count = counterRepository.getCount(node.getID(), type);
			try {
				node.setProperty(prop, count);
			} catch (ValueFormatException e) {
				log.error(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
