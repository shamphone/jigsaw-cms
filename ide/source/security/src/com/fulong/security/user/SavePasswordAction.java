package com.fulong.security.user;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.security.SecurityBaseAction;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixiang
 * @version 3.1
 */
public class SavePasswordAction extends SecurityBaseAction {
	protected static final String DES_KEY = "fulong";

	/* 
	 * @param mapping 参见配置文件security-config.xml
	 * @param form 参见UserForm
	 * @param request 包含如下参数：
	 *     1. IDs：为所选择的要修改密码的用户ID
	 *     2. password：为修改后的密码
	 * @param response 操作成功返回true，操作失败返回false
	 * @return 无
	 * @throws Exception
	 * @see com.fulong.security.SecurityBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *  Administrator 2009-10-29
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        Writer writer = response.getWriter();
		String[] IDs = request.getParameterValues("IDs");
        String pw = request.getParameter("password");
		
		String enPW = "";
		if(pw!=null&&!pw.equals("")){
			//密码加密
			DesEncrypter encrypter = new DesEncrypter(DES_KEY);
			enPW = encrypter.encrypt(pw);
			for(int i=0;i<IDs.length;i++){
				Node user = this.getRepository(request).getNode(IDs[i]);
				if(user!=null){
					if(enPW!=null&&!enPW.equals("")){
						//修改用户密码
						user.setProperty("user-password", enPW);
				}else {
		            writer.append("false");
				}				
			}else {
	            writer.append("false");
			}
			}
            writer.append("true");
		} else {
            writer.append("false");
		}
        writer.close();
        return null;
	}
}
