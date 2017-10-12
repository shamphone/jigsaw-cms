package com.fulong.cms;

import javax.servlet.http.*;

import org.w3c.dom.*;
import javax.servlet.http.Cookie;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import org.apache.commons.collections.IteratorUtils;
import java.util.List;
import java.io.*;
import java.util.*;
import com.fulong.longcon.repository.definition.*;
import com.fulong.longcon.repository.ChildNodeDefinition;

/**
 * <p>Title: 龙驭网站管理系统</p>
 *
 * <p>Description: 龙驭网站管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author maweishan
 * @version 3.1
 */
public class GetAttributeAction extends AjaxAction {
    /**
     * renderXML
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Document
     * @throws Exception
     */
    public Document renderXML(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String categoryId = request.getParameter("id");
        DOMImplementation domImpl =
                new org.apache.xerces.dom.DOMImplementationImpl();
        DocumentType docType =
                domImpl.createDocumentType("parameters", "",
                                           "");
        Document doc = domImpl.createDocument("", "parameters", docType);
        Element root = doc.getDocumentElement();
        NodeDefinition nd = this.getRepository(request).getDefinitionManager().getDefinition(
                categoryId);
        if (nd != null) { //返回分类属性列表
            Iterator properties = nd.
                                  propertyDefinitions(false);
            while (properties.hasNext()) {
                PropertyDefinition pd = (PropertyDefinition) properties.next();
                if (pd.getType() != 0) {
                    fillDoc(doc, root, pd, "", "");
                } else if (request.getParameter("open") != null) {
                    ChildNodeDefinition cnd = (ChildNodeDefinition) pd;
                    Iterator comProperties = cnd.getNodeDefinition().
                                             propertyDefinitions(false);
                    while (comProperties.hasNext()) {
                        fillDoc(doc, root,
                                (PropertyDefinition) comProperties.next(),
                                pd.getName() + ".", pd.getID() + "/");
                    }
                }
            }
        }
        return doc;
        
    }

    private void fillDoc(Document doc, Element root, PropertyDefinition pd,
                         String superName, String superId) throws
            DOMException {
        Element e = doc.createElement("parameter");
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(superName + pd.getName()));
        e.appendChild(name);
        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode(pd.getType() + ""));
        e.appendChild(type);
        Element id = doc.createElement("id");
        id.appendChild(doc.createTextNode(superId + pd.getID()));
        e.appendChild(id);
        Element defID = doc.createElement("defID");
        if (pd instanceof ChildNodeDefinition) {
            defID.appendChild(doc.createTextNode(((
                    ChildNodeDefinition) pd).getNodeDefinition().
                                                 getID()));
        } else {
            defID.appendChild(doc.createTextNode(""));
        }
        e.appendChild(defID);
        root.appendChild(e);
    }
}
