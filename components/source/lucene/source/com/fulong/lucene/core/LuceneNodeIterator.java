/**
 * 
 */
package com.fulong.lucene.core;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Row;
import com.fulong.longcon.repository.RowIterator;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class LuceneNodeIterator implements NodeIterator<Node> {
	private RowIterator rowIterator;
	private LuceneIndexManager IndexManager;

	private static Log log = LogFactory.getLog(LuceneNodeIterator.class);

	public LuceneNodeIterator(RowIterator rowIterator,
			LuceneIndexManager indexManager) {
		this.rowIterator = rowIterator;
		this.IndexManager = indexManager;
	}

	public Node nextNode() {
		return ((Row) rowIterator.next()).getNode();
	}

	public void setFetchSize(long size) {
		this.rowIterator.setFetchSize((int) size);

	}

	public long getPosition() {
		return this.rowIterator.getPosition();
	}

	public void skip(long skipNum) {
		this.rowIterator.skip(skipNum);

	}

	public long getSize() {
		return this.rowIterator.getSize();
	}

	public boolean hasNext() {
		return this.rowIterator.hasNext();
	}

	public Node next() {
		Row row = this.rowIterator.next();
		if (row.getNode() != null)
			return row.getNode();
		else
			try {
				this.IndexManager.deleteIndexByNodeID(row
						.getValue(LuceneIndexManager.FIELD_ID));
				log.info("错误索引ID："
						+ row.getValue(LuceneIndexManager.FIELD_ID + "已删除"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}

	public void remove() {
		this.rowIterator.remove();

	}

}
