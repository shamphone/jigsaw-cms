package com.fulong.longcon.repository.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fulong.common.dao.DatabaseException;
import com.fulong.common.dao.JdbcDaoFactory;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.core.InternalRepository;
import com.fulong.longcon.repository.dao.NodeDao;
import com.fulong.longcon.repository.data.NodeData;

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
public class ChildNodeIterator implements NodeIterator<Node> {
    private Node parent;
    private String name;
    private InternalRepository service;
    private long pageSize;
    private long size;
    private Iterator<Node> nodes;
    private long position;
    private boolean recursion = false;
    private static final Log log=LogFactory.getLog(ChildNodeIterator.class);
    public ChildNodeIterator(Node parent, InternalRepository service){
        this.parent=parent;
        this.service=service;
        this.pageSize=20;
        this.size=-1;
        this.nodes=null;
        this.position=0;
    }

    public ChildNodeIterator(Node parent, InternalRepository service, String name){
        this.parent=parent;
        this.name = name;
        this.service=service;
        this.pageSize=20;
        this.size=-1;
        this.nodes=null;
        this.position=0;
    }
    
    public ChildNodeIterator(Node parent, InternalRepository service, String name, boolean recursion){
        this.parent=parent;
        this.name = name;
        this.service=service;
        this.pageSize=20;
        this.size=-1;
        this.nodes=null;
        this.position=0;
        this.recursion = recursion;
    }

    /**
     * getSize
     *
     * @return long
     */
    public long getSize() {
        if (this.size >= 0)
            return this.size;
        JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
        try {
            factory.open();
            NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
            if(this.name==null){
                this.size = dao.countByParent(this.parent.getID());
            }else if(this.recursion){
                this.size = dao.countByRecParent(this.parent.getID(), this.name);
            }else{
            	this.size = dao.countByParent(this.parent.getID(), this.name);
            }
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
        return this.position<this.getSize();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     */
    public Node next() {
        return this.nextNode();
    }

    /**
     * nextNode
     *
     * @return Node
     * @todo Implement this com.fulong.longcon.repository.NodeIterator method
     */
    public Node nextNode() {
        if((this.nodes==null)||(!this.nodes.hasNext()))
            this.populateNodes();
        this.position++;
        return (Node)this.nodes.next();

    }

    /**
     * Removes from the underlying collection the last element returned by the iterator (optional operation).
     *
     * @todo Implement this java.util.Iterator method
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }



    private Iterator<Node> populateNodes() {
        long start=0;
        if(log.isTraceEnabled())
            start=System.currentTimeMillis();

        Collection<Node> nodes = new Vector<Node>((int)this.pageSize);
        JdbcDaoFactory factory = this.service.newJdbcDaoFactory();
        try {
            factory.open();
            NodeDao dao = (NodeDao) factory.getDao(NodeDao.class);
            NodeData[] data = null;
            if(this.name==null){
                data = dao.findByParent(this.parent.getID(), position,
                                        pageSize);
            }else if(this.recursion){
                data = dao.findByParentRec(this.parent.getID(), this.name, position,
                                        pageSize);
	            
            }else{
            	data = dao.findByParent(this.parent.getID(), this.name, position,
                        pageSize);
            }
            for (int i = 0; i < data.length; i++){
                Node node=this.service.makeNode(data[i]);
                nodes.add(node);
            }
            this.nodes = nodes.iterator();
            return this.nodes;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        } finally {
            factory.close();
            if(log.isTraceEnabled())
                log.trace("["+(System.currentTimeMillis()-start)+"] populateNodes.");
        }

    }

    public void skip(long skipNum) {
        this.position+=skipNum;
    }

    public long getPosition() {
        return this.position;
    }

    public void setFetchSize(long size) {
        this.pageSize=size;
    }


}
