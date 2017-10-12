package com.fulong.cms.resource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fulong.common.util.Tree;
import com.fulong.longcon.resource.Resource;
import com.fulong.longcon.resource.ResourceCollection;
import com.fulong.longcon.resource.ResourceIterator;
import com.fulong.longcon.resource.ResourceManager;
import org.apache.commons.io.FilenameUtils;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.NodeIterator;

/**
 *
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 1.0
 */
public class ResourceWrapper {
    private Node resource;
    private Repository manager;
    public ResourceWrapper(Repository manager, Node resource) {
        this.resource = resource;
        this.manager = manager;
    }

    public ResourceWrapper(Repository manager, String contextPath) {
        this.resource = manager.getNodeByPath(contextPath);
        this.manager = manager;
    }

    public ResourceWrapper getParent() {
        if (this.resource.getParent() == null) {
            return null;
        } else {
            return new ResourceWrapper(this.manager, this.resource.getParent());
        }
    }

    public String getPath() {
        return this.resource.getPath();
    }

    public String getName() {
        return this.resource.getName();
    }

    public int getDepth() {
        return this.resource.getDepth();
    }

    public boolean isPic() {
        if (this.resource.getProperty("mime") == null) {
            return false;
        }
        return this.resource.getProperty("mime").getString().startsWith("image");
    }

    public String getSuffix() {
        return FilenameUtils.getExtension(this.resource.getName());
    }

    public List getChildFolders() {
        return new ArrayList();
    }

    public List getChildFiles() {
        Query query = this.manager.getQueryManager().createQuery(this.manager.getDefinitionManager().getDefinition("resource-scheme"), Query.SQL);
        try {
			query.filterByParent(this.resource, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        NodeIterator nodes = query.nodes();
        List resources = new ArrayList();
        while (nodes.hasNext()) {
            resources.add(new ResourceWrapper(this.manager, nodes.nextNode()));
        }
        return resources;
    }

    public String getDisplayPath(String split) {
        split = split == null ? "" : split;
        String subString = "";
        for (int i = 1; i < this.getDepth(); i++) {
            subString += split;
        }
        return subString + this.resource.getName();
    }

    public Tree childTree(boolean includeFiles) {
        final Tree tree = new Tree();
        /*
        if (this.resource == null) {
            return tree;
        }
        String parentId = "0";
        if (this.getParent() != null) {
            parentId = this.getParent().getPath();
        }
        tree.addNode(this.getPath(), parentId, this);
        preorder(tree, this, includeFiles);
*/
        return tree;

    }

    private void preorder(Tree tree, ResourceWrapper root, boolean includeFiles) {
        Iterator children = root.getChildFolders().iterator();
        while (children.hasNext()) {
            ResourceWrapper child = (ResourceWrapper) children.next();
            tree.addNode(child.getPath(), root.getPath(), child);
            preorder(tree, child, includeFiles);
        }
        if (includeFiles) {
            Iterator files = root.getChildFiles().iterator();
            while (files.hasNext()) {
                ResourceWrapper child = (ResourceWrapper) files.next();
                tree.addNode(child.getPath(), root.getPath(), child);
            }
        }
    }

    /**
     * 遍历删除其子节点
     */
    public void deleteTree() {
        this.manager.delete(this.resource);
    }

    /**
     * copy
     *
     * @param folder ResourceWrapper
     */
    public void copy(Resource res) throws IOException {
        res.clone();

    }

}
