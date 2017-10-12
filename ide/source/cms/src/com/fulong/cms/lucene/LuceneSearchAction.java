package com.fulong.cms.lucene;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.content.ContentBaseAction;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;

public class LuceneSearchAction extends ContentBaseAction {

	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * 暂不知此类有何用途，故为了顺利打包，注释之
		 * liuzijun
		 */
		
		/*Query query=this.getLuceneManager().createQuery(this.getRepository(request));
		String strValue = request.getParameter("keyValue");
		query.filterByKeywords(strValue);		
		NodeIterator<Node> nodes = query.nodes();
		request.setAttribute("nodes", nodes);*/
		return mapping.findForward("success");
	
	}

}
