package com.fulong.longcon.resource.impl;

import java.security.Principal;
import java.sql.Types;
import java.util.List;
import java.util.Vector;

import com.fulong.common.dao.SQLParameter;
import com.fulong.longcon.resource.ResourceCollection;
import com.fulong.longcon.resource.ResourceIterator;
import com.fulong.longcon.resource.ext.ResourceManagerExt;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 */
public class ResourceCollectionImpl
    implements ResourceCollection {
    private ResourceManagerExt repository;

    private List<SQLParameter> parameters;
    private StringBuffer where;
    private StringBuffer recursiveWhere = new StringBuffer("");
    private StringBuffer orderBy = new StringBuffer("");
    private String sortPostfix = "";
    private String findInit = "SELECT c.* FROM resources c ";
    private String countInit = "SELECT COUNT(*) FROM resources c  ";
    public ResourceCollectionImpl(ResourceManagerExt repository) {
        this(repository, false);
    }

    public ResourceCollectionImpl(ResourceManagerExt repository,
                                  boolean recursive) {
        this.repository = repository;

        this.where = new StringBuffer(" WHERE 1=1 ");
        this.orderBy = new StringBuffer("");
        this.parameters = new Vector<SQLParameter>();

    }

    public void filterByParent(String path, boolean recursive) {
        if (path != null) {
            if (recursive) {
                this.recursiveWhere.append(
                    " start with PARENT_PATH=? connect by prior PATH=PARENT_PATH");
                this.parameters.add(new SQLParameter(Types.VARCHAR, path));
            }
            else {
                this.where.append(" AND PARENT_PATH=? ");
                this.parameters.add(new SQLParameter(Types.VARCHAR, path));
            }
        }
    }

    /**
     * 按照所有者来过滤
     * @param owner Principal
     */
    public void filterByOwner(Principal owner) {
        if (owner != null) {
            this.where.append(" AND OWNER_ID=? ");
            this.parameters.add(new SQLParameter(Types.VARCHAR, owner.getName()));
        }

    }

    /**
     * 按照resource类型过滤，是文件或者文件夹,false为文件，true为文件夹
     * @param owner Principal
     */
    public void filterByType(boolean isfolder) {
        this.where.append(" AND IS_FOLDER=? ");
        this.parameters.add(new SQLParameter(Types.BOOLEAN, isfolder));
    }

    /**
     * 按照创建者过滤
     * @param creator Principal
     */
    public void filterByCreator(Principal creator) {
        if (creator != null) {
            this.where.append(" AND CREATOR_ID=? ");
            this.parameters.add(new SQLParameter(Types.VARCHAR, creator.getName()));
        }
    }

    /**
     * 按照创建日期排序
     * @param asc 排序方式
     */
    public void sortByCreatedDate(boolean asc) {
        this.addSort("CREATE_TIME", asc);
    }

    /**
     * 按照最后修改日期排序
     * @param  asc 排序方式
     */
    public void sortByLastModifiedDate(boolean asc) {
        this.addSort("UPDATE_TIME", asc);
    }

    /**
     * 获取查询结果遍历器
     * @return SiteDomainIterator
     */
    public ResourceIterator resources() {
        StringBuffer query = new StringBuffer(findInit);

        query.append(this.where);
        query.append(this.recursiveWhere);
        query.append(this.sortPostfix);
        query.append(this.orderBy);

        StringBuffer count = new StringBuffer(countInit);
        count.append(this.where);
        count.append(this.recursiveWhere);
        count.append(this.sortPostfix);

        SQLParameter[] parameters = (SQLParameter[])this.parameters
            .toArray(new SQLParameter[this.parameters.
                     size()]);

        ResourceIterator result = new BasicResourceIterator(this.repository,
            parameters, query.toString(),
            count.toString());
        return result;

    }

    /**
     *
     * @param attribute String 可以是系统属性，也可以是自定义属性
     * @param asc boolean
     */
    public void addSort(String attribute, boolean asc) {
        if (attribute != null && !attribute.equals("")) {
            if (this.orderBy.length() == 0) {
                this.orderBy.append(" ORDER BY ");
            }
            else {
                this.orderBy.append(", ");
            }
            this.orderBy.append(attribute);
            this.orderBy.append(" ");
            if (asc) {
                this.orderBy.append("asc");
            }
            else {
                this.orderBy.append("desc");
            }
        }
    }

}

