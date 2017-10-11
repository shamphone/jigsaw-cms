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


package com.fulong.lyvc.message;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class ParserConfig {
    private String javaOutputPath;
    private String javaPackage;
    private String javaMessageFactoryFile;
    private String cplusplusOutputPath;
    private String cplusplusNamespace;
    private String cplusplusMessageFactoryFile;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    public String getJavaOutputPath() {
        return javaOutputPath;
    }
    public void setJavaOutputPath(String javaOutputPath) {
        this.javaOutputPath = javaOutputPath;
    }
    public String getJavaPackage() {
        return javaPackage;
    }
    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }
    public String getJavaMessageFactoryFile() {
        return javaMessageFactoryFile;
    }
    public void setJavaMessageFactoryFile(String javaMessageFactoryFile) {
        this.javaMessageFactoryFile = javaMessageFactoryFile;
    }
    public String getCplusplusOutputPath() {
        return cplusplusOutputPath;
    }
    public void setCplusplusOutputPath(String cplusplusOutputPath) {
        this.cplusplusOutputPath = cplusplusOutputPath;
    }
    public String getCplusplusNamespace() {
        return cplusplusNamespace;
    }
    public void setCplusplusNamespace(String cplusplusNamespace) {
        this.cplusplusNamespace = cplusplusNamespace;
    }
    public String getCplusplusMessageFactoryFile() {
        return cplusplusMessageFactoryFile;
    }
    public void setCplusplusMessageFactoryFile(String cplusplusMessageFactoryFile) {
        this.cplusplusMessageFactoryFile = cplusplusMessageFactoryFile;
    }
    public String getDatabaseUrl() {
        return databaseUrl;
    }
    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
    public String getDatabaseUsername() {
        return databaseUsername;
    }
    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }
    public String getDatabasePassword() {
        return databasePassword;
    }
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
    public void load(String filename) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        Document document = db.parse(filename);

        Element javaRoot = (Element) document.getElementsByTagName("java").item(0);
        javaOutputPath = javaRoot.getElementsByTagName("output-path").item(0).getFirstChild().getNodeValue();
        javaPackage = javaRoot.getElementsByTagName("package").item(0).getFirstChild().getNodeValue();
        javaMessageFactoryFile = javaRoot.getElementsByTagName("message-factory-file").item(0).getFirstChild().getNodeValue();

        Element cplusplusRoot = (Element) document.getElementsByTagName("cplusplus").item(0);
        cplusplusOutputPath = cplusplusRoot.getElementsByTagName("output-path").item(0).getFirstChild().getNodeValue();
        cplusplusNamespace = cplusplusRoot.getElementsByTagName("namespace").item(0).getFirstChild().getNodeValue();
        cplusplusMessageFactoryFile = cplusplusRoot.getElementsByTagName("message-factory-file").item(0).getFirstChild().getNodeValue();

        Element databaseRoot = (Element) document.getElementsByTagName("database").item(0);
        databaseUrl = databaseRoot.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();
        databaseUsername = databaseRoot.getElementsByTagName("username").item(0).getFirstChild().getNodeValue();
        databasePassword = databaseRoot.getElementsByTagName("password").item(0).getFirstChild().getNodeValue();
    }
}

