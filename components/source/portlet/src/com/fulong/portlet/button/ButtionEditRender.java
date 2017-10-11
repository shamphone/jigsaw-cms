package com.fulong.portlet.button;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.site.SiteTemplate;
import com.fulong.portlet.FormEditRender;

/**
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */
public abstract class ButtionEditRender extends FormEditRender {
	public List<NodeDefinition> lookupDefinitions(RenderRequest request, String parName) {
		List<NodeDefinition> categorys = new ArrayList<NodeDefinition>();
		String[] categories = request.getPreferences().getValues(parName, new String[0]);
		for (int i = 0; i < categories.length && categories[i].length() > 0; i++) {
			//删除已有分类，该ID的分类不存在
			/*if(this.getRepository().getDefinitionManager().getDefinition(categories[i]) != null){
				categorys.add(this.getRepository().getDefinitionManager().getDefinition(categories[i]));
			}*/
			categorys.add(this.getRepository().getDefinitionManager().getDefinition(categories[i]));
		}
		return categorys;
	}

	public void saveChannel(RenderRequest request, RenderResponse response, String parName) {
		SiteTemplate template = this.getCurrentSiteTemplate(request, response);
		String channel = request.getPreferences().getValue(parName, "");
		request.setAttribute("channel", template.getChannel(channel));
		request.setAttribute("siteTemplate", template);

	}
}
