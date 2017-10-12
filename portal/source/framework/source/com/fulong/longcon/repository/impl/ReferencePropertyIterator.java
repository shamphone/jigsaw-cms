package com.fulong.longcon.repository.impl;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyIterator;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.common.dao.DatabaseException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import com.fulong.longcon.repository.dao.ReferenceValueDao;

import com.fulong.longcon.repository.data.NodePropertyData;
import com.fulong.common.dao.DaoFactory;
import com.fulong.longcon.repository.core.InternalRepository;
import java.util.Collection;


/**
 * <p>Title: 龙驭核心引擎</p>
 *
 * <p>Description: 龙驭核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class ReferencePropertyIterator implements PropertyIterator<Object> {
    private Node node;
    private InternalRepository repository;
    private int pageSize;
    private long size;
    private Iterator<Node> nodes;
    private int position;
    public ReferencePropertyIterator(Node node,
                                     InternalRepository repository) {
        this.node = node;
        this.repository = repository;
        this.pageSize = 20;
        this.size = -1;
        this.nodes = null;
        this.position = 0;
    }

    /**
     * getSize
     *
     * @return long
     * @todo Implement this com.fulong.common.util.PageIterator method
     */
    public long getSize() {
        if (this.size >= 0)
            return this.size;
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(
                ReferenceValueDao.class);
            this.size = dao.countReferenceByNode(this.node.getID());
            return this.size;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } finally {
            factory.close();
        }

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
     * Removes from the underlying collection the last element returned by the iterator (optional operation).
     *
     * @todo Implement this java.util.Iterator method
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }


    public Node nextProperty() {
           if((this.nodes==null)||(!this.nodes.hasNext()))
               this.populateNodes();
           this.position++;
           return (Node)this.nodes.next();

    }


    private Iterator<Node> populateNodes() {
        Collection<Node> nodes = new Vector<Node>((int)this.pageSize);
        JdbcDaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(ReferenceValueDao.class);
            NodePropertyData[] data;
            data = dao.findReferenceByNode(this.node.getID(), position,
                                           pageSize);
            for (int i = 0; i < data.length; i++){
                Node node=this.repository.getNode(data[i].getID());
                nodes.add(node);
            }
            this.nodes = nodes.iterator();
            return this.nodes;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } finally {
            factory.close();
        }

    }


    public Object next() {
        return this.nextProperty();
    }

    protected NodePropertyData[] loadMore(int fromIndex, int pageSize) {
        DaoFactory factory = this.repository.newJdbcDaoFactory();
        try {
            factory.open();
            ReferenceValueDao dao = (ReferenceValueDao) factory.getDao(
                ReferenceValueDao.class);
            return dao.findReferenceByNode(this.node.getID(), fromIndex,
                                           pageSize);
        } catch (SQLException se) {
            throw new DatabaseException(se);
        } finally {
            factory.close();
        }
    }

    public void skip(long skipNum) {
        this.position += skipNum;
    }

    public long getPosition() {
        return this.position;
    }

}
