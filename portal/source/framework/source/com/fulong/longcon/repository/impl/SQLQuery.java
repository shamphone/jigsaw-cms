/**
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2010-2015
 */
package com.fulong.longcon.repository.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.DaoFactory;
import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.NodeDao;
import com.fulong.longcon.repository.dao.QName;
import com.fulong.longcon.repository.data.NodeData;

/**
 * SQLQuery
 * 
 * @author <a href="lixf@fulong.com.cn">李雄峰</a>
 * @date 2010-9-11
 */
public class SQLQuery extends GeneralQuery {
	protected InternalRepository repository;

	protected Set<String> tables; // 连表排序
	protected StringBuffer selections;
	protected StringBuffer  where;
	protected StringBuffer orderBy;
	protected NodeDefinition nodeDefinition;
	protected List<SQLParameter> parameters;
	protected boolean ifDistinct = false; // 是否distinct

	protected static final Log log = LogFactory.getLog(SQLQuery.class);

	public SQLQuery() {
		this.selections = new StringBuffer(" n.* ");
		this.tables = new HashSet<String>();
		this.parameters = new Vector<SQLParameter>();
		this.orderBy = new StringBuffer("");
		this.where = new StringBuffer("");

	}

	public void setRepository(Repository repository) {
		if (repository == null)
			throw new NullPointerException("repository should not be null.");
		this.repository = (InternalRepository) repository;
		this.valueFactory = this.repository.getValueFactory();
	}

	public void setNodeDefinition(NodeDefinition definition) {
		if (definition == null)
			throw new NullPointerException("node definition should not be null.");
		this.nodeDefinition = definition;
	}

	/**
	 * 等值过滤
	 */
	public void filterByProperty(String field, Value value) {
		// by mali 2010-7-7
		if (value == null || value.getString() == null || value.getString().equals(""))
			this.filterByPropertyNotExist(field);
		else
			this.filterByCompareValue(field, value, "=");
	}

	public void filterByNotEqualProperty(String field, Calendar from, Calendar to) {
		PropertyDefinition property = this.nodeDefinition.getPropertyDefinition(field);
		if (property == null)
			throw new IllegalArgumentException("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
		String table = QName.encode(property.getType(), property.getID());
		this.tables.add(table);
		this.where.append(" AND " + table + ".node_id=n.pkid AND (" + table + ".value<? OR " + table + ".value>? )");
		this.parameters.add(new SQLParameter(Types.TIMESTAMP, new Timestamp(from.getTimeInMillis())));
		this.parameters.add(new SQLParameter(Types.TIMESTAMP, new Timestamp(to.getTimeInMillis())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fulong.longcon.repository.Query#filterByFromValue(java.lang.String,
	 * com.fulong.longcon.repository.Value)
	 */
	public void filterByFromValue(String field, Value value) {
		this.filterByCompareValue(field, value, ">=");
	}

	public void filterGreaterThan(String field, Value value) {
		this.filterByCompareValue(field, value, ">");
	}

	public void filterLessThan(String field, Value value) {
		this.filterByCompareValue(field, value, "<");
	}

	public void filterByToValue(String field, Value max) {
		this.filterByCompareValue(field, max, "<=");
	}

	public void filterByNotEqualValue(String field, Value value) {
		this.filterByCompareValue(field, value, "<>");
	}

	protected void filterByCompareValue(String field, Value value, String op) {
		if (field.indexOf(".") == -1) {
			PropertyDefinition property = this.nodeDefinition.getPropertyDefinition(field);
			if (property == null)
				throw new IllegalArgumentException("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
			this.filterByCompareValue(property, value, op);
		} else {
			String[] ids = field.split("[\\.\\/]");
			PropertyDefinition prop1 = this.nodeDefinition.getPropertyDefinition(ids[0]);
			PropertyDefinition prop2 = null;
			int start = field.indexOf('.');
			if (start < 0)
				start = field.indexOf('/');
			String subID = field.substring(start + 1);
			switch (prop1.getType()) {
			case PropertyType.FIX:
				prop2 = ((ChildNodeDefinition) prop1).getNodeDefinition().getPropertyDefinition(subID);
				break;
			case PropertyType.REFERENCE:
				prop2 = this.repository.getDefinitionManager().getDefinition(prop1.getReferenceType()).getPropertyDefinition(subID);
				break;
			}
			if (prop2 == null)
				throw new IllegalArgumentException("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
			this.filterByCompareValueForReOrFix(prop1, prop2, value, op);
		}
	}

	private void filterByCompareValue(PropertyDefinition property, Value value, String op) {
		String table = QName.encode(property.getType(), property.getID());
		this.tables.add(table);

		if (op.equals("<>")) {
			if (value == null || value.getString() == null || value.getString().equals("")) {
				this.where.append(" AND " + table + ".node_id=n.pkid AND " + table + ".value is not null");
				return;
			}
			this.where.append(" AND " + table + ".node_id=n.pkid AND " + table + ".value" + op + "?");
		} else {
			this.where.append(" AND " + table + ".node_id=n.pkid AND " + table + ".value" + op + "?");
		}

		if (value != null) {
			switch (property.getType()) {
			case PropertyType.STRING:
			case PropertyType.REFERENCE:
			case PropertyType.PATH:
				this.parameters.add(new SQLParameter(Types.VARCHAR, value.getString()));
				break;
			case PropertyType.DATE:
				this.parameters.add(new SQLParameter(Types.TIMESTAMP, new Timestamp(value.getDate().getTimeInMillis())));
				break;
			case PropertyType.BOOLEAN:

				this.parameters.add(new SQLParameter(Types.BOOLEAN, new Boolean(value.getBoolean())));
				break;
			case PropertyType.LONG:

				this.parameters.add(new SQLParameter(Types.NUMERIC, new Long(value.getLong())));
				break;

			case PropertyType.DOUBLE:

				this.parameters.add(new SQLParameter(Types.NUMERIC, new Double(value.getDouble())));
				break;
			default:
				throw new IllegalArgumentException("Unable to use type: " + property.getType() + " for compare condition.");
			}
		}
	}

	
	protected void filterByCompareID(String def, String field, Value value, String op) {
		PropertyDefinition property = this.repository.getDefinitionManager().getDefinition(def).getPropertyDefinition(field);
			if (property == null)
				throw new IllegalArgumentException("No property with name " + field + " in definition " + def + ".");
			this.filterByCompareID(property, value, op);
	}
	
	private void filterByCompareID(PropertyDefinition property, Value value, String op) {
		String table = QName.encode(property.getType(), property.getID());
		this.tables.add(table);

		if (op.equals("<>")) {
			if (value == null || value.getString() == null || value.getString().equals("")) {
				this.where.append(" AND " + table + ".value=n.pkid AND " + table + ".node_id is not null");
				return;
			}
			this.where.append(" AND " + table + ".value=n.pkid AND " + table + ".node_id" + op + "?");
		} else {
			this.where.append(" AND " + table + ".value=n.pkid AND " + table + ".node_id" + op + "?");
		}

		if (value != null) {
			switch (property.getType()) {
			case PropertyType.STRING:
			case PropertyType.REFERENCE:
			case PropertyType.PATH:
				this.parameters.add(new SQLParameter(Types.VARCHAR, value.getString()));
				break;
			case PropertyType.DATE:
				this.parameters.add(new SQLParameter(Types.TIMESTAMP, new Timestamp(value.getDate().getTimeInMillis())));
				break;
			case PropertyType.BOOLEAN:

				this.parameters.add(new SQLParameter(Types.BOOLEAN, new Boolean(value.getBoolean())));
				break;
			case PropertyType.LONG:

				this.parameters.add(new SQLParameter(Types.NUMERIC, new Long(value.getLong())));
				break;

			case PropertyType.DOUBLE:

				this.parameters.add(new SQLParameter(Types.NUMERIC, new Double(value.getDouble())));
				break;
			default:
				throw new IllegalArgumentException("Unable to use type: " + property.getType() + " for compare condition.");
			}
		}
	}
	
	/**
	 * prop1 引用或复合属性 property 过滤属性 对引用或复合属性的属性进行过滤 modified by mali 2010-7-21
	 */
	private void filterByCompareValueForReOrFix(PropertyDefinition prop1, PropertyDefinition prop2, Value value, String op) {
		String table1;
		String table2;
		String table;
		ifDistinct = true;
		if (prop1.getType() == PropertyType.REFERENCE) {
			table1 = QName.encode(prop1.getType(), prop1.getID());
			table2 = QName.encode(prop2.getType(), prop2.getID());
			this.where.append(" AND " + table1 + ".node_id=n.pkid AND " + table1 + ".value in (select node_id from " + table2 + " where value " + op + "?)");
			this.tables.add(table1);
		} else {
			table = QName.encode(prop2.getType(), prop2.getID());
			this.where.append(" AND n.pkid in (select node.parent_id from " + table + " s,node where s.value " + op + "? AND s.node_id=node.pkid AND node.name='" + prop1.getID() + "')");
		}

		if (value != null) {
			switch (prop2.getType()) {
			case PropertyType.STRING:
			case PropertyType.REFERENCE:
			case PropertyType.PATH:
				this.parameters.add(new SQLParameter(Types.VARCHAR, value.getString()));
				break;
			case PropertyType.DATE:
				this.parameters.add(new SQLParameter(Types.TIMESTAMP, new Timestamp(value.getDate().getTimeInMillis())));
				break;
			case PropertyType.BOOLEAN:

				this.parameters.add(new SQLParameter(Types.BOOLEAN, new Boolean(value.getBoolean())));
				break;
			case PropertyType.LONG:

				this.parameters.add(new SQLParameter(Types.NUMERIC, new Long(value.getLong())));
				break;

			case PropertyType.DOUBLE:

				this.parameters.add(new SQLParameter(Types.NUMERIC, new Double(value.getDouble())));
				break;
			default:
				throw new IllegalArgumentException("Unable to use type: " + prop2.getType() + " for compare condition.");
			}
		}
	}

	public void filterByPropertyNotExist(String field) {
		PropertyDefinition property = this.nodeDefinition.getPropertyDefinition(field);
		if (property == null)
			throw new IllegalArgumentException("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
		String table = QName.encode(property.getType(), property.getID());

		this.where.append(" AND n.PKID not in (select node_id from " + table + ")");

	}

	/*
	 * 按关键字查找
	 * 
	 * @see
	 * com.fulong.longcon.repository.Query#filterByKeywords(java.lang.String,
	 * java.lang.String)
	 */
	public void filterByKeywords(String field, String[] values) {
		PropertyDefinition property = this.nodeDefinition.getPropertyDefinition(field);
		if (property == null)
			throw new IllegalArgumentException("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
		if (values == null || values.length == 0)
			return;
		String table = QName.encode(property.getType(), property.getID());
		this.tables.add(table);
		this.where.append(" AND " + table + ".node_id=n.pkid AND (" + table + ".value like ? ");
		this.parameters.add(new SQLParameter(Types.VARCHAR, "%" + values[0] + "%"));
		for (int i = 1; i < values.length; i++) {
			this.where.append(" OR " + table + ".value like ? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, "%" + values[i] + "%"));
		}
		this.where.append(")");
	}

	public void filterByParent(Node parent, boolean recursive) {
		if (!recursive) {
			this.where.append(" AND n.PARENT_ID =? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		} else {
			this.where.append(" AND n.PARENT_ID in (select t.pkid from NODE t start with t.pkid = ? connect by prior t.PKID = t.PARENT_ID) ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		}
	}
	
	public void filterByParent(String id, boolean recursive) {
		Node node = this.repository.getNode(id);
		if(node == null)		
			log.error("this node id=: "+id+"is null");
		else
			filterByParent(node, recursive);
	}
	
	public void filterByParentAndName(String id,String name, boolean recursive) throws SQLException{
		if (!recursive) {
			this.where.append(" AND n.PARENT_ID =? and n.name =? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, id));
			this.parameters.add(new SQLParameter(Types.VARCHAR, name));
		} else {
			this.where.append(" AND n.PARENT_ID in (select t.pkid from NODE t start with t.pkid = ? connect by prior t.PKID = t.PARENT_ID) and n.name =? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, id));
			this.parameters.add(new SQLParameter(Types.VARCHAR, name));
		}
	}
	
	public void filterByNotEqualParent(Node parent, boolean recursive) {
		if (!recursive) {
			this.where.append(" AND n.PARENT_ID <>? ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		} else {
			this.where.append(" AND n.PARENT_ID not in (select t.pkid from NODE t start with t.pkid = ? connect by prior t.PKID = t.PARENT_ID) ");
			this.parameters.add(new SQLParameter(Types.VARCHAR, parent.getID()));
		}
	}

	public void sortByProperty(String field, boolean asc) {
		PropertyDefinition property = this.nodeDefinition.getPropertyDefinition(field);
		if (property == null) {
			//log.warn("No property with name " + field + " in definition " + this.nodeDefinition.getID() + ".");
			this.sortByOrdinal(asc);
			return;
		}
		String table = QName.encode(property.getType(), property.getID());
		if (!this.tables.contains(table)) {
			this.tables.add(table);
			this.where.append(" AND " + table + ".node_id(+)=n.pkid ");
		}
		// 产生如 select s_inc.value s_incvalue
		this.selections.append("," + table + ".value " + table + "value");
		if (this.orderBy.length() > 0)
			this.orderBy.append(",");
		if (property.getType() == 1) {
			this.orderBy.append("nlssort(nvl(" + table
					+ "value,null),'NLS_SORT=SCHINESE_PINYIN_M') ");
		} else if (property.getType() == 3 || property.getType() == 4
				|| property.getType() == 5) {
			this.orderBy.append("nvl(" + table + "value,null) ");
		}
		if (asc)
			this.orderBy.append(" asc nulls last");
		else
			this.orderBy.append(" desc nulls last");
	}

	public void sortByOrdinal(boolean asc) {
		if (this.orderBy.length() > 0)
			this.orderBy.append(",");
		if (asc)
			this.orderBy.append(" n.ORDERNO asc ");
		else
			this.orderBy.append(" n.ORDERNO desc ");
	}

	protected String tables() {
		StringBuffer tables = new StringBuffer(" node n ");
		for (String table : this.tables) {
			tables.append("," + table);
		}
		return tables.toString();
	}

	protected String getQuerySQL() {
		StringBuffer query = new StringBuffer("SELECT ");
		// 是否distinct modified by mali 2010-7-21
		ifDistinct = true;
		query.append("distinct ");
		query.append(this.selections.toString());
		query.append(" FROM ");
		query.append(this.tables());
		query.append(" WHERE ");
		query.append(this.where);
		if (this.orderBy.length() > 0) {
			query.append(" ORDER BY ");
			query.append(this.orderBy);
		} else {
			// 默认按ORDERNO排序
			query.append(" ORDER BY n.ORDERNO asc ");
		}
		return query.toString();
	}

	protected String getCountSQL() {
		StringBuffer query = new StringBuffer("SELECT ");
		// 是否distinct modified by mali 2010-7-21
		if (ifDistinct)
			query.append("count(distinct n.pkid) FROM ");
		else
			query.append("count(n.pkid) FROM ");
		query.append(this.tables());
		query.append(" WHERE ");
		query.append(this.where);
		return query.toString();
	}

	public synchronized NodeIterator<Node> nodes(boolean recursive) {
		StringBuffer where = new StringBuffer(" (n.definition in (? ");
		this.parameters.add(0, new SQLParameter(Types.VARCHAR, this.nodeDefinition.getID()));
		// 由于in里的参数不能大于1000,故将此语句用or分开拼
		int i = 1;
		if (recursive)
			for (Iterator<NodeDefinition> iterator = this.nodeDefinition.getInheritDefinitions(true); iterator.hasNext()&&i<2000;) {
				String id = iterator.next().getID();
				if (i % 1000 != 0)
					where.append(",?");
				else
					where.append(") or n.definition in (?");
				this.parameters.add(0, new SQLParameter(Types.VARCHAR, id));
				i++;
			}
		where.append("))");
		while(this.where.indexOf("n.definition in")!=-1){
			String src = this.where.substring(0, this.where.indexOf("))")+2);
			int index=-1;
			while((index=src.indexOf("?",index))>-1){
				this.parameters.remove(0);
				++index;
			  }
			this.where.delete(0, this.where.indexOf("))")+2);
		}
		this.where.insert(0, where);
		this.hashCode();
		SQLParameter[] parameters = (SQLParameter[]) this.parameters.toArray(new SQLParameter[this.parameters.size()]);
		String query = this.getQuerySQL();

		String count = this.getCountSQL();

		return new NodeIteratorImpl<Node>(parameters, query, count);
	}

	class NodeIteratorImpl<E extends Node> implements NodeIterator<E> {
		private Iterator<E> iterator;
		private int position;
		private long size;
		private String query;
		private String count;
		private SQLParameter[] parameters;

		public NodeIteratorImpl(SQLParameter[] parameters, String query, String count) {
			this.size = -1;
			this.position = 0;
			this.iterator = new Vector<E>().iterator(); // init an empty
			// iterator;
			this.parameters = parameters;
			this.query = query;
			this.count = count;
		}

		protected NodeData[] loadMore(int fromIndex, int pageSize) {
			DaoFactory factory = repository.newJdbcDaoFactory();
			try {
				factory.open();
				NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
				return dao.search(query, parameters, fromIndex, pageSize);
			} catch (SQLException se) {
				throw new DatabaseException(se);
			} finally {
				factory.close();
			}
		}

		protected long loadSize() {
			if (this.size < 0) {
				DaoFactory factory = repository.newJdbcDaoFactory();
				try {
					factory.open();
					NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
					this.size = dao.countResultNum(count, parameters);
				} catch (SQLException se) {
					throw new DatabaseException(se);
				} finally {
					factory.close();
				}
			}
			return this.size;
		}

		/**
		 * Returns the current position within the iterator.
		 * 
		 * @return a long
		 */
		public long getPosition() {
			return this.position;
		}

		/**
		 * Returns the number of elements in the iterator.
		 * 
		 * @return a long
		 */
		public long getSize() {
			if (size < 0) {
				this.size = this.loadSize();
			}
			return this.size;
		}

		/**
		 * Returns <tt>true</tt> if the iteration has more elements.
		 * 
		 * @return <tt>true</tt> if the iterator has more elements.
		 */
		public boolean hasNext() {
			return this.position < this.getSize();
		}

		/**
		 * Returns the next element in the iteration.
		 * 
		 * @return the next element in the iteration.
		 */
		@SuppressWarnings("unchecked")
		public E next() {
			if (!this.hasNext())
				throw new NoSuchElementException();
			if (!this.iterator.hasNext()) {
				NodeData[] data = this.loadMore(this.position, this.position + 20);
				Vector<E> nodes = new Vector<E>();
				for (int i = 0; i < data.length; i++) {
					nodes.add((E) repository.makeNode(data[i]));
				}
				this.iterator = nodes.iterator();
			}
			if (this.iterator.hasNext()) {
				this.position++;
				return this.iterator.next();
			} else
				throw new NoSuchElementException();
		}

		/**
		 * Skip a number of elements in the iterator.
		 * 
		 * @param skipNum
		 *            the non-negative number of elements to skip
		 */
		public void skip(long skipNum) {
			this.position += skipNum;
		}

		/**
		 * nextNode
		 * 
		 * @return Node
		 * @todo Implement this com.fulong.longcon.repository.NodeIterator
		 *       method
		 */
		public E nextNode() {
			return (E) this.next();
		}

		/**
		 * Removes from the underlying collection the last element returned by
		 * the iterator (optional operation).
		 * 
		 * @todo Implement this java.util.Iterator method
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		public void setFetchSize(long size) {
			throw new UnsupportedOperationException();
		}

	}

	public void filterByPropertyCompareID(String def, String field, Value value) {
		this.filterByCompareID(def ,field, value, "=");		
	}
}
