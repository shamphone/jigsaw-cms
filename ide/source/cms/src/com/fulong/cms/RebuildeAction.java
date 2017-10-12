package com.fulong.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.search.IndexRebuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author lixf
 * @version 1.0
 */
public class RebuildeAction extends CMSBaseAction {

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
        IndexRebuilder rebuilder=(IndexRebuilder)this.getBeanFactory().getBean("indexRebulder");
        if(rebuilder.isIdle())
            rebuilder.rebuild(this.getRepository(request).getRootNode());
        return mapping.findForward("success");
    }
}
