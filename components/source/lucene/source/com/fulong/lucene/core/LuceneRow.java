package com.fulong.lucene.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Row;
import com.fulong.longcon.repository.Value;
import com.fulong.lucene.IndexManager;

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
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class LuceneRow implements Row {
	private LuceneRowIterator rowIterator;
	private Map<String, String> values;
	private Repository repository;
	// private LuceneIndexManager manager;
	private LuceneQuery query;

	public LuceneRow(Repository repository, LuceneQuery query, LuceneRowIterator rowIterator, Document doc) {
		this.values = new HashMap<String, String>();
		String[] columns = rowIterator.getColumnNames();
		for (int i = 0; i < columns.length; i++) {
			values.put(columns[i], doc.get(columns[i]));
		}
		this.repository = repository;
		// this.manager=manager;
		this.query = query;
	}

	/**
	 * 
	 * @return Node
	 */
	public Node getNode() {
		String id = (String) this.values.get(IndexManager.FIELD_ID);
		return this.repository.getNode(id);
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return Value
	 */
	public Value getValue(String name) {
		return this.repository.getValueFactory().createValue(
				"" + this.values.get(name));
	}

	/**
	 * 
	 * @param field
	 *            String
	 * @return String
	 */
	public String getHilightValue(String field) {
		String text = "";
		Value value = this.getValue(field);
		if (value.getString() != null)
			text = value.getString();
		return this.query.highlight(field, text);
	}

	/**
	 * 
	 * @return Value[]
	 */
	public Value[] getValues() {
		String[] columns = rowIterator.getColumnNames();
		Value[] values = new Value[columns.length];
		for (int i = 0; i < values.length; i++)
			values[i] = this.getValue(columns[i]);
		return values;
	}
}
