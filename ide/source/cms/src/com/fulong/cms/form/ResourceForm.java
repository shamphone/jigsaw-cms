package com.fulong.cms.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

/**
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class ResourceForm extends ActionForm {
    private FormFile[] files;
    private FormFile zip;
    public static final int size = 8;
    private String[] item;
    private String folder;
    public ResourceForm() {
        files = new FormFile[size];
        zip = null;
    }

    public FormFile getFile(int index) {
        return files[index];
    }

    public void setFile(int index, FormFile file) {
        files[index] = file;
    }

    public FormFile getZip() {
        return this.zip;
    }

    public String[] getItem() {
        return item;
    }

    public String getFolder() {
        return folder;
    }

    public String getDecodedFolder() throws UnsupportedEncodingException {
        return this.folder;
    }


    public void setZip(FormFile file) {
        this.zip = file;
    }

    public void setItem(String[] item) {

        this.item = item;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
