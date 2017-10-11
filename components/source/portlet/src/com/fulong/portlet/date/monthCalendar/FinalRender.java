package com.fulong.portlet.date.monthCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.portlet.cms.ListContentPortletRender;

/**
 * Title: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同服务管理系统
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
 * @author lixiang
 * @version 3.1
 */
public class FinalRender extends ListContentPortletRender {

	/**
	 * execute
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @throws Exception
	 */
	public ActionForward render(ActionMapping mapping, ActionForm form, RenderRequest request, RenderResponse response)
			throws Exception {
	        request.setAttribute("preferences", request.getPreferences());
	        
	        //片段的路径
			request.setAttribute("path", getClipPath(request, response, ""));
			
	        //取出当月一号的日期
			Calendar calendar = null;
			calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01"); 
			calendar.setTime(sdf.parse(sdf.format(new Date())));
				
	    	//取出传递参数时间当月一号的日期
			String str = request.getParameter("dateParameter");
			if (str != null){
			SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd"); 
			Date par = sdfymd.parse(str);
			calendar.setTime(sdf.parse(sdf.format(par)));
			}
			request.setAttribute("date", calendar.getTime());
				
			//取出该时间是星期几
			int dow = calendar.get(Calendar.DAY_OF_WEEK);
				
			List<Date> list=new ArrayList<Date>();
			calendar.add(Calendar.DATE, -dow+1);
			list.add(calendar.getTime());
			for(int i=1;i<42;i++)
			{
				calendar.add(Calendar.DATE, 1);
				list.add(calendar.getTime());
			}
			request.setAttribute("weekList", list.iterator());
				
			return mapping.findForward("success");	
	}

}
