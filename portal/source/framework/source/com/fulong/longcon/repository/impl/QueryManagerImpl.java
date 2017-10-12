/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.repository.impl;

import java.util.Hashtable;
import java.util.Map;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.QueryManager;
import com.fulong.longcon.repository.core.QueryCreator;

/**
 * QueryManagerImpl
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-9-11
 */
public class QueryManagerImpl implements QueryManager {
	private Map<String, QueryCreator> creators;

	public void init() {
		this.creators = new Hashtable<String, QueryCreator>();
	}

	/**
	 * 
	 * @return ContentQuery
	 * @param language
	 *            String
	 */
	@Override
	public Query createQuery(NodeDefinition node, String language) {
		QueryCreator creator = this.creators.get(language);
		if (creator == null)
			throw new IllegalArgumentException("There is no query creator register with name " + language);
		return creator.createQuery(node);
	}

	/**
	 * 
	 * @param language
	 *            String
	 * @param creator
	 *            QueryCreator
	 */
	@Override
	public void register(String language, Object creator) {
		this.creators.put(language, (QueryCreator) creator);
	}

	@Override
	public String[] getSupportedQueryLanguages() {
		return this.creators.values().toArray(new String[this.creators.size()]);
	}

}
