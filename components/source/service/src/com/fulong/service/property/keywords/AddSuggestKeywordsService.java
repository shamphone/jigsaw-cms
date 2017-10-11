package com.fulong.service.property.keywords;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 自动添加关键字服务
 * 自动提示框中支持自动添加库中所没有的关键字，配置参数：
 * 1．	内容储存分类：用户当前有权限访问的分类。
 * 2．	关键字属性：用于储存的关键字属性。
 * 3．	关键字储存分类：用于关键字储存的分类。
 * 4．	关键字显示属性：用于显示的关键字属性。
 * 
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author liuzijun
 * @version 3.1
 */
public class AddSuggestKeywordsService extends NodeService {
	private Repository repository;
	private PassportProvider passportProvider;
	private String editorPath;

	public void setEditorPath(String path) {
		this.editorPath = path;
	}

	@Override
	public String doEdit(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}

	@Override
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters) throws Exception {
		// TODO Auto-generated method stub
		super.doUpdate(request, response, parameters);
	}

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			String keywordsProp = parameters.getValue("keywordsProp");
			String dictDef = parameters.getValue("dictDef");
			String dictDisplayName = parameters.getValue("dictDisplayName");
			if (keywordsProp != null && dictDef != null && dictDisplayName != null) {
				Node owner = this.getPassportProvider().getDefaultOrganization();
				NodeDefinition def = node.getDefinition();
				PropertyDefinition prop = def.getPropertyDefinition(keywordsProp);
				if (prop != null) {
					Value[] values = node.getProperty(keywordsProp).getValues();
					if (values != null) {
						List<String> list = new ArrayList<String>();
						for (int i = 0; i < values.length; i++) {
							if (values[i].getString() != null) {
								String[] strs = values[i].getString().split(" ");
								for (int j = 0; j < strs.length; j++) {
									if (!strs[j].equals("")) {
										list.add(strs[j]);
									}
								}
							}
						}
						String[] valuesTemp = new String[list.size()];
						for (int z = 0; z < list.size(); z++) {
							valuesTemp[z] = list.get(z);
						}
						node.setProperty(keywordsProp, valuesTemp);
						NodeDefinition dictDefinition = this.getRepository().getDefinitionManager().getDefinition(dictDef);
						if (dictDefinition != null) {
							for (int u = 0; u < list.size(); u++) {
								Query query = this.getRepository().getQueryManager().createQuery(dictDefinition, Query.SQL);
								query.filterByKeywords(dictDisplayName, list.get(u));
								NodeIterator<?> contents = query.nodes();
								if (!contents.hasNext()) {
									Node newDict = owner.addNode(dictDefinition, "contents");
									newDict.setProperty(dictDisplayName, list.get(u));
								}
							}
						}
					}
				}
			}
		}
	}

	public Repository getRepository() {
		return this.repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public PassportProvider getPassportProvider() {
		return this.passportProvider;
	}

	public void setPassportProvider(PassportProvider passportProvider) {
		this.passportProvider = passportProvider;
	}
}
