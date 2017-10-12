package com.fulong.webdav.server;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import java.io.StringWriter;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fulong.webdav.utils.DOMWriter;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Lock请求是类似如下格式的请求：
 * LOCK /hr/ergonomics/posture.doc HTTP/1.1
 Host: www.example.com
 Timeout: Infinite
 If-Match: *
 Content-Type: text/xml; charset="utf-8"
 Content-Length: xxxx

 <?xml version="1.0" encoding="utf-8" ?>
 <D:lockinfo xmlns:D='DAV:'>
   <D:lockscope><D:exclusive/></D:lockscope>
   <D:locktype><D:write/></D:locktype>
   <D:owner xmlns:x="http://www.customapp.com/ns/">
      <x:lock-user>alice@example.com</x:lock-user>
      <x:created-by>Text Editor v1.2.5</x:created-by>
   </D:owner>
 </D:lockinfo>

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
public class LockInfoParser extends RequestParser {
    /**
     * Create a new lock.
     */
    private static final int LOCK_CREATION = 0;

    /**
     * Refresh lock.
     */
    private static final int LOCK_REFRESH = 1;

    Node lockInfoNode = null;
    int lockRequestType = LOCK_CREATION;
    String locktype = null;
    String lockscope = null;
    String lockowner = null;
    public LockInfoParser(InputStream request) throws SAXException,
            ParserConfigurationException {
        DocumentBuilder documentBuilder = getDocumentBuilder();

        try {
            Document document = documentBuilder.parse(new InputSource(request));

            // Get the root element of the document
            Element rootElement = document.getDocumentElement();
            lockInfoNode = rootElement;
        } catch (Exception e) {
            lockRequestType = LOCK_REFRESH;
        }

        if (lockInfoNode != null) {

            // Reading lock information

            NodeList childList = lockInfoNode.getChildNodes();
            StringWriter strWriter = null;
            DOMWriter domWriter = null;

            Node lockScopeNode = null;
            Node lockTypeNode = null;
            Node lockOwnerNode = null;

            for (int i = 0; i < childList.getLength(); i++) {
                Node currentNode = childList.item(i);
                switch (currentNode.getNodeType()) {
                case Node.TEXT_NODE:
                    break;
                case Node.ELEMENT_NODE:
                    String nodeName = currentNode.getNodeName();
                    if (nodeName.endsWith("lockscope")) {
                        lockScopeNode = currentNode;
                    }
                    if (nodeName.endsWith("locktype")) {
                        lockTypeNode = currentNode;
                    }
                    if (nodeName.endsWith("owner")) {
                        lockOwnerNode = currentNode;
                    }
                    break;
                }
            }

            if (lockScopeNode != null) {

                childList = lockScopeNode.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        break;
                    case Node.ELEMENT_NODE:
                        String tempScope = currentNode.getNodeName();
                        if (tempScope.indexOf(':') != -1) {
                            lockscope = tempScope.substring
                                        (tempScope.indexOf(':') + 1);
                        } else {
                            lockscope = tempScope;
                        }
                        break;
                    }
                }

            }
            if (lockscope == null) {
                // Bad request
                throw new SAXException("Unable to parser lock scope.");
            }
            if (lockTypeNode != null) {

                childList = lockTypeNode.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        break;
                    case Node.ELEMENT_NODE:
                        String tempType = currentNode.getNodeName();
                        if (tempType.indexOf(':') != -1) {
                            locktype =
                                    tempType.substring(tempType.indexOf(':') +
                                    1);
                        } else {
                            locktype = tempType;
                        }
                        break;
                    }
                }

            }
            if (locktype == null) {
                // Bad request
                throw new SAXException("Unable to parser lock scope.");
            }
            if (lockOwnerNode != null) {

                childList = lockOwnerNode.getChildNodes();
                for (int i = 0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        lockowner += currentNode.getNodeValue();
                        break;
                    case Node.ELEMENT_NODE:
                        strWriter = new StringWriter();
                        domWriter = new DOMWriter(strWriter, true);
                        domWriter.setQualifiedNames(false);
                        domWriter.print(currentNode);
                        lockowner += strWriter.toString();
                        break;
                    }
                }

                if (lockowner == null) {
                    // Bad request
                    throw new SAXException("Unable to parser lock scope.");
                }

            } else {
                lockowner = new String();
            }

        }

    }

    public int getLockRequestType(){
        return this.lockRequestType;
    }

    public String getLockOwner() {
        return this.lockowner;
    }

    public String getLockType() {
        return this.locktype;
    }

    public String getLockScope() {
        return this.lockscope;
    }
}
