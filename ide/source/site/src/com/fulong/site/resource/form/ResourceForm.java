package com.fulong.site.resource.form;

import java.io.UnsupportedEncodingException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

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
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile[] files;
    private FormFile zip;
    public static final int size = 8;
    private String[] item;
    private String folder;
    private String upload;
    private String template;
    
    public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

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
    
    public String getUpload() {
        return upload;
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
    
    public void setUpload(String upload) {

        this.upload = upload;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
