package com.fulong.site.management;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.RangeIterator;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteCollection;
import com.fulong.site.form.SearchForm;

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
 * @author liuzijun
 * @version 1.0
 */
public class DoSearchAction extends ManagementBaseAction {
    protected final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	protected ActionForward managementExecute(ActionMapping mapping,
			ActionForm tform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SearchForm form = (SearchForm) tform;
	    /*SiteCollection sites = this.getSiteFactory(request).getSites();
	    String begin = form.getBeginDate();
	    String end = form.getEndDate();
	    String state = form.getState();
	    String keyword = form.getKeywords();
	    
	    Date beginDate = null;
	    Date endDate = null;
        if (begin != null && !begin.equals(""))
        	beginDate = FORMATTER.parse(begin);
        if (end != null && !end.equals(""))
        	endDate = FORMATTER.parse(end);
    	sites.filterByCreatedDate(beginDate, endDate);
	    
    	sites.filterByState(state);
	    sites.filterByKeyWord(keyword);
	    sites.sortByCreatedDate(false);
	    
	    RangeIterator<Site> iterator = sites.iterator();
	    iterator.skip(this.getPager(request).getFromIndex());
        this.setPager(request, iterator.getSize());
        request.setAttribute("sites",iterator);*/
		NodeIterator<Node> iterator = this.getSiteFactory(request).getSites();
		iterator.skip(this.getPager(request).getFromIndex());
        this.setPager(request, iterator.getSize());
		request.setAttribute("sites", iterator);
		request.setAttribute("factory", this.getSiteFactory(request));
        return mapping.findForward("success");
	}
}
