/*
 * $Workfile: $
 *
 * $Revision: $
 *
 * $Author: $
 *
 * $Date: $
 *
 * Copyright (c) 2004 Zhongkefulong Corporation
 */


package com.fulong.lyvc.message.parser;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class MessageDefinitionParser {

    public static final String propertyNameExceptionString = "property name must be letter and digit, and digit can't be first character";
    public static String[] getPropertyNames(Document document, String propertyTagName) {

        NodeList nodes = document.getElementsByTagName(propertyTagName);
        String[] propertyNames = new String[nodes.getLength()];

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            String propertyName = element.getFirstChild().getNodeValue();

            // Only letter, digit and _ is allowed, and digit can't appear in the first position
            for( int j=0; j<propertyName.length(); j++ ) {
                if( j == 0 ) {
                    if( propertyName.charAt(j) == '_') {
                        throw new RuntimeException(propertyNameExceptionString );
                    }
                    if(!Character.isLetter( propertyName.charAt(j))) {
                        throw new RuntimeException(propertyNameExceptionString );
                    }
                } else {
                    if (!Character.isLetter(propertyName.charAt(j))
                     && !Character.isDigit(propertyName.charAt(j)) ) {
                        throw new RuntimeException(propertyNameExceptionString);
                    }
                }
            }

            propertyNames[i] = propertyName;
        }
        return propertyNames;
    }

    public static MessageDefinition parse(String file) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        Document document = db.parse(file);

        MessageDefinition result = new MessageDefinition();

        // Find name
        result.name = document.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
        File messageFile = new File(file);
        String messageFilename = messageFile.getName();
        if( !messageFilename.equals(result.name + ".xml")) {
            throw new RuntimeException("文件名必须和其中定义的类名一致.");
        }


        // Find properties
        result.booleanProperties = getPropertyNames(document, "boolean");
        result.dateProperties = getPropertyNames(document, "date");
        result.doubleProperties = getPropertyNames(document, "double");
        result.floatProperties = getPropertyNames(document, "float");
        result.i4Properties = getPropertyNames(document, "i4");
        result.i8Properties = getPropertyNames(document, "i8");
        result.stringProperties = getPropertyNames(document, "string");

        return result;
    }
}
