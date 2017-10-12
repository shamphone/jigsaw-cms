package com.fulong.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author lixf
 * @version 1.0
 */
public class DomUtils {
    public static Element getElement(Element root, String path) {
        String[] split = path.split("/");
        Element current = root;
        if (!current.getTagName().equals(split[0])) {
            return null;
        }
        for (int i = 1; i < split.length; i++) {
            NodeList list = current.getElementsByTagName(split[i]);
            if (list.getLength() == 0) {
                return null;
            }
            current = (Element) list.item(0);
        }
        return current;
    }

    public static NodeList getElements(Element root, String path) {
        String[] split = path.split("/");
        Element current = root;
        if (!current.getTagName().equals(split[0])) {
            return null;
        }
        int i = 1;
        for (i = 1; i < split.length - 1; i++) {
            NodeList list = current.getElementsByTagName(split[i]);
            if (list.getLength() == 0) {
                return null;
            }
            current = (Element) list.item(0);
        }
        return current.getElementsByTagName(split[i]);
    }

    public static String getValue(Element elem) {
        Node node = elem.getFirstChild();
        if (node == null) {
            return null;
        }
        if (node.getNodeType() == Node.TEXT_NODE) {
            return node.getNodeValue();
        }
        if(node.getNodeType() == Node.CDATA_SECTION_NODE){
        	return ((CDATASection)node).getData();
        }
        return null;
    }

    public static String getValue(Element root, String path) {
        Element node = getElement(root, path);
        if (node == null) {
            return null;
        }
        return getValue(node);
    }

    public static void removeChildren(Element root, String tagName) {
        NodeList children = root.getElementsByTagName(tagName);
        int number = children.getLength();
        for (int i = number - 1; i >= 0; i--) {
            root.removeChild(children.item(i));
        }
    }

    public static Document open(String file) throws IOException, ParserConfigurationException, SAXException {
        return open(new File(file));
    }

    public static Document open(File file) throws  ParserConfigurationException, SAXException, IOException {
      
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(file);     

    }

    public static void save(Document document, String file) throws IOException {
        save(document, new File(file));

    }

    public static void save(Document document, File file) throws IOException {
        if(!file.exists())
            file.createNewFile();
        OutputFormat format = new OutputFormat(document, "UTF-8", true);

        FileOutputStream fos = new FileOutputStream(file);

        XMLSerializer xs = new XMLSerializer(fos, format);
        xs.serialize(document);
        fos.close();

    }
    
    public static String antiFilter(String s){
    	if ( s==null||s.length()==0 )
    		return s ;

    	s = s.replace( "&gt;", ">" ) ;
    	s = s.replace( "&lt;", "<" ) ;
    	s = s.replace( "&amp;", "&" ) ;
    	s = s.replace( "&quot;", "\"" ) ;
    	s = s.replace( "&#39;", "'" ) ;
    	return s;
    }
    
}
