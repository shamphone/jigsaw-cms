package com.fulong.site.management;

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
 * @author sunyuchao
 * @version 1.0
 */

public class SearchAction extends ManagementBaseAction {

	protected ActionForward managementExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
	    SiteCollection sites = this.getSiteFactory(request).getSites();
	    String begin=request.getParameter("beginDate");
	    String end=request.getParameter("endDate");
	    String state=request.getParameter("state");
	    String keyWord=request.getParameter("keywords");
	    Date beginDate=null;
	    Date endDate=null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(begin!=null&&begin!=""){
        	beginDate=format.parse(begin);
        }
        if(end!=null&&end!=""){
        	endDate=format.parse(end);
        }
	    if(beginDate!=null||endDate!=null){
	    	sites.filterByCreatedDate(beginDate, endDate);
	    }
	    if(state!=null&&state!=""){
	    	sites.filterByState(state);
	    }
	    sites.filterByKeyWord(keyWord);
	    RangeIterator<Site> iterator = sites.iterator();
        this.setPager(request, iterator.getSize());
        request.setAttribute("sites",iterator);
        
        request.setAttribute("keywords", request.getParameter("keywords"));
        request.setAttribute("beginDate",request.getParameter("beginDate"));
        request.setAttribute("endDate", request.getParameter("endDate"));
        request.setAttribute("state", request.getParameter("state"));
        
        */
        return mapping.findForward("success");
	}

}
