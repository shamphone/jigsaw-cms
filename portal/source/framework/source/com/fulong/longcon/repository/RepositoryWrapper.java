package com.fulong.longcon.repository;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public abstract class RepositoryWrapper implements Repository {

    public abstract Repository getRepository() ;

     /**
     *
     * @param node Node
     */
    public boolean delete(Node node) {
        return getRepository().delete(node);
    }


    /**
     *
     * @param nodeId String
     * @return Node
     */
    public Node getNode(String nodeId) {
        return getRepository().getNode(nodeId);
    }


    /**
     *
     * @return QueryManager
     */
    public QueryManager getQueryManager() {
        return getRepository().getQueryManager();
    }

    /**
     *
     * @return Node
     */
    public Node getRootNode() {
        return this.getRepository().getRootNode();
    }

    /**
     *
     * @return ValueFactory
     */
    public ValueFactory getValueFactory() {
        return this.getRepository().getValueFactory();
    }


    public NodeDefinitionManager getDefinitionManager() {
        return this.getRepository().getDefinitionManager();
    }

}
