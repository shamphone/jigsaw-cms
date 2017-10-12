package com.fulong.cms.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;


/**
 *
 * <p>Title: 龙驭内容管理系统-插件</p>
 *
 * <p>Description: 主要包括工作流、编辑器、校验、格式化</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 姜崎
 * @version 2.0
 */
public class SaveSendContentTransition extends ContentBaseAction {

    public ActionForward doExecute(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
        Exception {
    	
          NodeDefinition cc = null;
          String[] ids = request.getParameterValues("IDs");
          for (int i = 0; i < ids.length; i++) {
              Node content = this.getRepository(request).getNode(ids[i]);
              for (int j = 0; j < request.getParameterValues("categorys").length;j++) {
                  cc =this.getRepository(request).getDefinitionManager().getDefinition(request.getParameterValues("categorys")[j]);
                  content.addMixinDefinition(cc);
              }
          }
          return mapping.findForward("success");
    }
}
