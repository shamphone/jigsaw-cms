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


package com.fulong.lyvc.message.generator;

import com.fulong.lyvc.message.parser.MessageDefinition;
import java.io.*;

public class CplusplusGenerator {
    private String outputPath;
    private String namespace;

    private static final String template_h =
            "///////////////////////////////\r\n" +
            "// Message definition file\r\n" +
            "// Generated by lyvc Message\r\n" +
            "///////////////////////////////\r\n" +
            "\r\n" +
            "#ifndef %name%_H\r\n" +
            "#define %name%_H\r\n" +
            "\r\n" +
            "#include \"BaseMessage.h\"\r\n" +
            "\r\n" +
            "namespace %namespace% {\r\n" +
            "\r\n" +
            "class %name% : public BaseMessage{\r\n" +
            "\r\n" +
            "\tpublic:\r\n" +
            "\t\tstatic const id = %id%;\r\n" +
            "%declaration%\r\n" +
            "\tpublic:\r\n" +
            "\t\tvirtual string toXML();\r\n" +
            "\t\tvirtual bool fromXML(string& xmlString);\r\n" +
            "\r\n" +
            "}; // class\r\n" +
            "\r\n" +
            "}; // namespace\r\n" +
            "\r\n" +
            "#endif\r\n";

    private static final String template_cpp =
            "///////////////////////////////\r\n" +
            "// Message definition file\r\n" +
            "// Generated by lyvc Message\r\n" +
            "///////////////////////////////\r\n" +
            "\r\n" +
            "#include \"stdafx.h\"\r\n" +
            "#include \"%name%.h\"\r\n" +
            "\r\n" +
            "string %namespace%::%name%::toXML()\r\n" +
            "{\r\n" +
            "%toXML%" +
            "}\r\n" +
            "\r\n" +
            "bool %namespace%::%name%::fromXML(string& xmlString)\r\n" +
            "{\r\n" +
            "%fromXML%" +
            "}\r\n" +
            "\r\n";

    public CplusplusGenerator(String outputPath, String namespace) throws Exception {
        this.outputPath = outputPath;
        this.namespace = namespace;
    }

    public void generateFile(MessageDefinition definition) throws Exception {

        String filename_h = outputPath + "/" + definition.name + ".h";
        PrintWriter pw = new PrintWriter( new BufferedWriter(new FileWriter(filename_h)));

        // Make declaration
        StringBuffer sb = new StringBuffer();
        sb.append( this.generatePropertyDeclaration(definition.booleanProperties, "bool"));
        sb.append( this.generatePropertyDeclaration(definition.dateProperties, "__time64_t"));
        sb.append( this.generatePropertyDeclaration(definition.doubleProperties, "double"));
        sb.append( this.generatePropertyDeclaration(definition.floatProperties, "float"));
        sb.append( this.generatePropertyDeclaration(definition.i4Properties, "__int32"));
        sb.append( this.generatePropertyDeclaration(definition.i8Properties, "__int64"));
        sb.append( this.generatePropertyDeclaration(definition.stringProperties, "string"));
        String declaration = sb.toString();

        // Replace template placeholder
        String output = template_h.replaceAll("%name%", definition.name);
        output = output.replaceAll("%namespace%", this.namespace);
        output = output.replaceAll("%id%", Integer.toString(definition.id));
        output = output.replaceAll("%declaration%", declaration);
        pw.println(output);
        pw.close();

        // write cpp file
        String filename_cpp = outputPath + "/" + definition.name + ".cpp";
        pw = new PrintWriter( new BufferedWriter(new FileWriter(filename_cpp)));

        // Make toXML
        String toXML = this.generateToXmlMethod(definition);

        // Make fromXML
        String fromXML = this.generateFromXmlMethod(definition);

        // Replace template placeholder
        output = template_cpp.replaceAll("%name%", definition.name);
        output = output.replaceAll("%namespace%", this.namespace);
        output = output.replaceAll("%toXML%", toXML);
        output = output.replaceAll("%fromXML%", fromXML);
        pw.println(output);
        pw.close();

        return;
    }

    public String generatePropertyDeclaration(String[] propertyNames, String type) {
        StringBuffer sb = new StringBuffer();
        for( int i=0; i<propertyNames.length; i++) {
            sb.append("\t\t" + type + " " + propertyNames[i] + ";\r\n");
        }
        return sb.toString();
    }

    public String generateToXmlMethod(MessageDefinition definition) {
        StringBuffer sb = new StringBuffer();
        sb.append("\tconst int BUFFERSIZE=64;\r\n");
        sb.append("\tchar buffer[BUFFERSIZE];\r\n");
        sb.append("\tstring xml;\r\n");
        sb.append("\txml = xml + \"<lyvcmessage>\";\r\n");

        // id
        sb.append("\txml = xml + \"<id>\";\r\n");
        sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%I32d\", id);\r\n");
        sb.append("\txml = xml + buffer;\r\n");
        sb.append("\txml = xml + \"</id>\";\r\n");

        // base sender id
        sb.append("\txml = xml + \"<_senderid>\";\r\n");
        sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%I64d\", this->_senderId);\r\n");
        sb.append("\txml = xml + buffer;\r\n");
        sb.append("\txml = xml + \"</_senderid>\";\r\n");

        // conference id
        sb.append("\txml = xml + \"<_conferenceid>\";\r\n");
        sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%I64d\", this->_conferenceId);\r\n");
        sb.append("\txml = xml + buffer;\r\n");
        sb.append("\txml = xml + \"</_conferenceid>\";\r\n");

        for(int i=0; i<definition.booleanProperties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.booleanProperties[i] + ">\";\r\n");
            sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%d\", this->" + definition.booleanProperties[i] + ");\r\n");
            sb.append("\txml = xml + buffer;\r\n");
            sb.append("\txml = xml + \"</" + definition.booleanProperties[i] + ">\";\r\n");
        }
        for(int i=0; i<definition.dateProperties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.dateProperties[i] + ">\";\r\n");
            sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%I64d\", this->" + definition.dateProperties[i] + "*1000);\r\n");
            sb.append("\txml = xml + buffer;\r\n");
            sb.append("\txml = xml + \"</" + definition.dateProperties[i] + ">\";\r\n");
        }
        for(int i=0; i<definition.doubleProperties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.doubleProperties[i] + ">\";\r\n");
            sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%f\", this->" + definition.doubleProperties[i] + ");\r\n");
            sb.append("\txml = xml + buffer;\r\n");
            sb.append("\txml = xml + \"</" + definition.doubleProperties[i] + ">\";\r\n");
        }
        for(int i=0; i<definition.floatProperties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.floatProperties[i] + ">\";\r\n");
            sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%f\", this->" + definition.floatProperties[i] + ");\r\n");
            sb.append("\txml = xml + buffer;\r\n");
            sb.append("\txml = xml + \"</" + definition.floatProperties[i] + ">\";\r\n");
        }
        for(int i=0; i<definition.i4Properties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.i4Properties[i] + ">\";\r\n");
            sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%I32d\", this->" + definition.i4Properties[i] + ");\r\n");
            sb.append("\txml = xml + buffer;\r\n");
            sb.append("\txml = xml + \"</" + definition.i4Properties[i] + ">\";\r\n");
        }
        for(int i=0; i<definition.i8Properties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.i8Properties[i] + ">\";\r\n");
            sb.append("\t_snprintf(buffer, BUFFERSIZE, \"%I64d\", this->" + definition.i8Properties[i] + ");\r\n");
            sb.append("\txml = xml + buffer;\r\n");
            sb.append("\txml = xml + \"</" + definition.i8Properties[i] + ">\";\r\n");
        }
        for(int i=0; i<definition.stringProperties.length; i++) {
            sb.append("\txml = xml + \"<" + definition.stringProperties[i] + ">\";\r\n");
            sb.append("\txml = xml + BaseMessage::encodeHtmlEscape(this->" + definition.stringProperties[i] + ");\r\n");
            sb.append("\txml = xml + \"</" + definition.stringProperties[i] + ">\";\r\n");
        }
        sb.append("\txml = xml + \"</lyvcmessage>\";\r\n");
        sb.append("\treturn xml;\r\n");
        return sb.toString();
    }

    public String generateFromXmlMethod(MessageDefinition definition) {
        StringBuffer sb = new StringBuffer();
        sb.append("\tstring tagContent;\r\n");
        sb.append("\t\r\n");

        // find senderId
        sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"_senderid\");\r\n");
        sb.append("\tif(tagContent == \"\"){\r\n");
        sb.append("\t\treturn false;\r\n");
        sb.append("\t}\r\n");
        sb.append("\tthis->_senderId = _atoi64(tagContent.c_str());\r\n");

        // find conferenceid
        sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"_conferenceid\");\r\n");
        sb.append("\tif(tagContent == \"\"){\r\n");
        sb.append("\t\treturn false;\r\n");
        sb.append("\t}\r\n");
        sb.append("\tthis->_conferenceId = _atoi64(tagContent.c_str());\r\n");

        for(int i=0; i<definition.booleanProperties.length; i++) {
            sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"" + definition.booleanProperties[i] + "\");\r\n");
            sb.append("\tif(tagContent == \"\"){\r\n");
            sb.append("\t\treturn false;\r\n");
            sb.append("\t}\r\n");
            sb.append("\tthis->" + definition.booleanProperties[i] + " = atoi(tagContent.c_str());\r\n");
        }

        for(int i=0; i<definition.dateProperties.length; i++) {
            sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"" + definition.dateProperties[i] + "\");\r\n");
            sb.append("\tif(tagContent == \"\"){\r\n");
            sb.append("\t\treturn false;\r\n");
            sb.append("\t}\r\n");
            sb.append("\tthis->" + definition.dateProperties[i] + " = _atoi64(tagContent.c_str())/1000;\r\n");
        }

        for(int i=0; i<definition.doubleProperties.length; i++) {
            sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"" + definition.doubleProperties[i] + "\");\r\n");
            sb.append("\tif(tagContent == \"\"){\r\n");
            sb.append("\t\treturn false;\r\n");
            sb.append("\t}\r\n");
            sb.append("\tthis->" + definition.doubleProperties[i] + " = atof(tagContent.c_str());\r\n");
        }

        for(int i=0; i<definition.floatProperties.length; i++) {
            sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"" + definition.floatProperties[i] + "\");\r\n");
            sb.append("\tif(tagContent == \"\"){\r\n");
            sb.append("\t\treturn false;\r\n");
            sb.append("\t}\r\n");
            sb.append("\tthis->" + definition.floatProperties[i] + " = atof(tagContent.c_str());\r\n");
        }

        for(int i=0; i<definition.i4Properties.length; i++) {
            sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"" + definition.i4Properties[i] + "\");\r\n");
            sb.append("\tif(tagContent == \"\"){\r\n");
            sb.append("\t\treturn false;\r\n");
            sb.append("\t}\r\n");
            sb.append("\tthis->" + definition.i4Properties[i] + " = atoi(tagContent.c_str());\r\n");
        }

        for(int i=0; i<definition.i8Properties.length; i++) {
            sb.append("\ttagContent = BaseMessage::getStringBetweenTag(xmlString, \"" + definition.i8Properties[i] + "\");\r\n");
            sb.append("\tif(tagContent == \"\"){\r\n");
            sb.append("\t\treturn false;\r\n");
            sb.append("\t}\r\n");
            sb.append("\tthis->" + definition.i8Properties[i] + " = _atoi64(tagContent.c_str());\r\n");
        }

        for(int i=0; i<definition.stringProperties.length; i++) {
            sb.append("\tthis->" + definition.stringProperties[i] + " = BaseMessage::decodeHtmlEscape(BaseMessage::getStringBetweenTag(xmlString, \"" + definition.stringProperties[i] + "\"));\r\n");
        }

        sb.append("\treturn true;\r\n");
        return sb.toString();
    }
}
