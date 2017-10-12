package com.fulong.cms.harvest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.repository.NodeDefinition;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author HaoJingwei
 * @version 1.0
 *
 */

public class ContentInAction extends ContentHarvestBaseAction {

    protected ActionForward contentHarvestPerform(ActionMapping mapping,
                                                ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws
            Exception {
        String configPath = this.getServletContext().getRealPath(request.getParameter("config"));
        String catID = request.getParameter("categoryID");
        String path = this.getServletContext().getRealPath(request.getParameter("path"));
        String workingDir = this.getServletContext().getRealPath(request.getParameter("workingDir"));
        Principal pri=request.getUserPrincipal();  //获得当前用户的Principal
        Harvest h = new Harvest(configPath,workingDir); //建立一个对象，让webharvest工作
        while(h.status()!=3){
            Thread.sleep(3000);
        }
        NodeDefinition cat = this.getRepository(request).getDefinitionManager().getDefinition(catID);
        ParseAndSave pAS = new ParseAndSave(cat,path,pri) ;//建立一个ParseAndSave对象，用于解析和存储内容
        pAS.doPaseAndSave();//存储
        return null;
    }
}
