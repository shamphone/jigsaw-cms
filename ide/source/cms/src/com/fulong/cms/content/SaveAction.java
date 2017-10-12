package com.fulong.cms.content;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.fulong.cms.form.EditForm;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;

/**
 * 
 * <p>
 * Title: 龙驭内容管理系统-插件
 * </p>
 * 
 * <p>
 * Description: 主要包括工作流、编辑器、校验、格式化
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author 姜崎
 * @author lixf
 * @version 2.0
 */
public class SaveAction extends ContentBaseAction {
	/**
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 */

	public ActionForward doExecute(ActionMapping mapping, ActionForm aform, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EditForm form = (EditForm) aform;
		NodeDefinition nodeDefinition = this.getRepository(request).getDefinitionManager().getDefinition(form.getDefinitionID());
		Node parent = this.getRepository(request).getNode(form.getParentID());
		Node content;
		if (form.getContentID() != null)
			content = this.getRepository(request).getNode(form.getContentID());
		else
			content = parent.addNode(nodeDefinition, form.getName());
		String[] names = form.getValueNames();
		for (int i = 0; i < names.length; i++)
			if (form.getValues(names[i]) != null)
				content.setProperty(names[i], form.getValues(names[i])); 
		names = form.getFileNames();
		for (int f = 0; f < names.length; f++) {
			if (form.getFiles(names[f]) != null) {
				String[] paths = new String[form.getFiles(names[f]).length];
				for (int i = 0; i < form.getFiles(names[f]).length; i++) {
					FormFile file = form.getFiles(names[f])[i];
					if ((file.getFileName() != null) && file.getFileName().length() > 0) {
						Property prop = content.getProperty(names[f]);
						if(prop.getType()==PropertyType.BINARY){
							Value value = this.getRepository(request).getValueFactory().createValue(file.getInputStream());
							content.setProperty(names[f], value);
						}else{
							String path = this.uploadFile(file, request, response);
							paths[i] = path;
						}
					}
				}
				if(paths!=null){
					List<String> path = new ArrayList<String>();
					for(int i=0; i<paths.length; i++){
						if(paths[i] != null)
							path.add(paths[i]);
							//content.setProperty(names[f], paths[i]);
					}
					String[] b = new String[]{};
					content.setProperty(names[f], path.toArray(b));
				}
			}
		}

		return this.forward(mapping, "success", content.getID());
	}

}
