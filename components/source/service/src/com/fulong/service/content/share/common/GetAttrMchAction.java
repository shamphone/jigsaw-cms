package com.fulong.service.content.share.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.fulong.service.ServiceAjaxAction;

/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author sunyuchao
 *
 * @version 1.0
 *
 */
public class GetAttrMchAction extends ServiceAjaxAction {
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
       /* String rcId = request.getParameter("rcId");
        DOMImplementation domImpl =
                new org.apache.xerces.dom.DOMImplementationImpl();
        DocumentType docType =
                domImpl.createDocumentType("parameters", "",
                                           "");
        Document doc = domImpl.createDocument("", "parameters", docType);
        Element root = doc.getDocumentElement();

        RemoteCategory rct = this.getRemoteManager().getCategory(rcId);
        if (rct == null) {
            return doc;
        }
        if (rct.isAutoMatched()) {
            return doc;
        }else{
            Iterator<?> it = rct.getProperties();
            while (it.hasNext()) {
                RemoteProperty p = (RemoteProperty) it.next();
                Element matchItem = doc.createElement("matchItem");
                Element source = doc.createElement("source");
                source.appendChild(doc.createTextNode(p.getBindingProperty()));
                Element dest = doc.createElement("dest");
                dest.appendChild(doc.createTextNode(p.getPropertyID()));
                Element specifyValue = doc.createElement("specifyValue");
                Element type = doc.createElement("type");
                type.appendChild(doc.createTextNode(p.getPropertyEditorType()));
                matchItem.appendChild(source);
                matchItem.appendChild(dest);
                matchItem.appendChild(specifyValue);
                matchItem.appendChild(type);
                root.appendChild(matchItem);
            }
            if (rct.getDefaultValues().size() > 0) {
                Iterator<?> its = rct.getDefaultValues().keySet().iterator();
                while (its.hasNext()) {
                    String key = (String) its.next();
                    Element matchItem = doc.createElement("matchItem");
                    Element source = doc.createElement("source");
                    Element dest = doc.createElement("dest");
                    dest.appendChild(doc.createTextNode(key));
                    Element specifyValue = doc.createElement("specifyValue");
                    specifyValue.appendChild(doc.createTextNode(rct.
                            getDefaultMappingValue(key)));
                    matchItem.appendChild(source);
                    matchItem.appendChild(dest);
                    matchItem.appendChild(specifyValue);
                    root.appendChild(matchItem);
                }
            }
        }
        return doc;*/
    	return null;
    }
}
