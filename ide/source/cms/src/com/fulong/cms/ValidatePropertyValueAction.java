package com.fulong.cms;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.content.ContentBaseAction;
import com.fulong.common.util.DesEncrypter;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixiang
 *
 * @version 1.0
 *
 */
public class ValidatePropertyValueAction extends ContentBaseAction {
    public ActionForward doExecute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            Exception {
        response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        Writer writer = response.getWriter();
        String password = request.getParameter("password");
        String oValue = request.getParameter("oValue");
        DesEncrypter de = new DesEncrypter("fulong");
        String passwordEncrypt = de.encrypt(password);
        //String oValueEncrypt = de.encrypt(oValue);
        if (oValue.equals(passwordEncrypt)) {
            writer.append("true");
        } else {
            writer.append("false");
        }
        return null;
    }
}
