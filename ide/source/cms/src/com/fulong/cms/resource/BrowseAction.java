package com.fulong.cms.resource;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lichengzhao
 * @version 1.0
 */
public class BrowseAction extends ResourceBaseAction {

	private static final DateFormat FORMATTOR = DateFormat.getDateInstance(DateFormat.MEDIUM);
	
    public ActionForward resourcePerform(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws
            Exception {
        request.setAttribute("resourceType", request.getParameter("resourceType"));
        int dayCount = 10;
        String temp = request.getParameter("dayCount");
        if (temp != null) {
        	try {
        		dayCount = Integer.parseInt(temp);
			} catch (Exception e) {
				log.warn("Parseing dayCount Failed: " + e.getMessage());
			}
        }
    	Calendar day = Calendar.getInstance();
        List<String[]> days = new ArrayList<String[]>(dayCount);
        for (int i=0; i<dayCount; i++) {
        	day.add(Calendar.DAY_OF_MONTH, i==0 ? 0 : -1);
        	days.add(new String[]{String.valueOf(day.getTimeInMillis()), FORMATTOR.format(day.getTime())});
        }
        Collections.reverse(days);
        request.setAttribute("days", days);
        return mapping.findForward("success");
    }
}
