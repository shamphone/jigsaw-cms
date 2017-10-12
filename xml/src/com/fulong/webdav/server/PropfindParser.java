package com.fulong.webdav.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.Reader;

/**
 * 解析Propfind请求
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class PropfindParser extends RequestParser {

    private Node propNode = null;
    /**
     * PROPFIND - Specify a property mask.
     */
    public static final int FIND_BY_PROPERTY = 0;


    /**
     * PROPFIND - Display all properties.
     */
    public static final int FIND_ALL_PROP = 1;


    /**
     * PROPFIND - Return property names.
     */
    public static final int FIND_PROPERTY_NAMES = 2;
    private int type = FIND_ALL_PROP;
    public PropfindParser(Reader request) throws IOException,
            ParserConfigurationException, SAXException {
        try {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(request));
            this.parse(document);

        } catch (SAXException sax) {
            String content = IOUtils.toString(request);
            if (content.length() > 0)
                throw sax;

        }
    }

    public PropfindParser(InputStream request) throws IOException,
            ParserConfigurationException, SAXException {
            if(request.available()==0)
                return ;
        try {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(request));
            this.parse(document);
        } catch (SAXException sax) {
            String content = IOUtils.toString(request, "UTF-8");
            if (content.length() > 0)
                throw sax;
        }
    }

    private void parse(Document document) {
        // Get the root element of the document
        Element rootElement = document.getDocumentElement();
        NodeList childList = rootElement.getChildNodes();

        for (int i = 0; i < childList.getLength(); i++) {
            Node currentNode = childList.item(i);
            switch (currentNode.getNodeType()) {
            case Node.TEXT_NODE:
                break;
            case Node.ELEMENT_NODE:
                if (currentNode.getNodeName().endsWith("prop")) {
                    type = FIND_BY_PROPERTY;
                    propNode = currentNode;
                }
                if (currentNode.getNodeName().endsWith("propname")) {
                    type = FIND_PROPERTY_NAMES;
                }
                if (currentNode.getNodeName().endsWith("allprop")) {
                    type = FIND_ALL_PROP;
                }
                break;
            }
        }

    }

    public Collection<String> getRequestProperties() {
        Vector<String> properties = null;
        if (type == PropfindParser.FIND_BY_PROPERTY) {
            properties = new Vector<String>();
            NodeList childList = propNode.getChildNodes();

            for (int i = 0; i < childList.getLength(); i++) {
                Node currentNode = childList.item(i);
                switch (currentNode.getNodeType()) {
                case Node.TEXT_NODE:
                    break;
                case Node.ELEMENT_NODE:
                    String nodeName = currentNode.getNodeName();
                    String propertyName = null;
                    if (nodeName.indexOf(':') != -1) {
                        propertyName = nodeName.substring
                                       (nodeName.indexOf(':') + 1);
                    } else {
                        propertyName = nodeName;
                    }

                    // href is a live property which is handled differently
                    properties.addElement(propertyName);
                    break;
                }
            }

        }
        return properties;
    }

    public int getType() {
        return this.type;
    }


}
