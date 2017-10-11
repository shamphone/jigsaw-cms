/**
 * 
 */
package com.fulong.portlet.chart;

import com.fulong.longcon.chart.ChartManager;
import com.fulong.portlet.PortletRender;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public abstract class ChartRender extends PortletRender {	

	protected ChartManager getChartManager() {
		return (ChartManager)this.getBeanFactory().getBean("chartManager");
	}

	
}
