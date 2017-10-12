/**
 * 
 */
package com.fulong.cms.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author lixf
 * 
 * @version 2.0
 * 
 */
public class EditForm extends ActionForm{
	private static final long serialVersionUID = 2898732630972300253L;
	private String definitionID;
	private Map<String, String[]> values;
	private Map<String, FormFile[]> files;
	private String contentID;
	private String parentID;
	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParentID() {
		return parentID;
	}


	public void setParentID(String parentID) {
		this.parentID = parentID;
	}


	public EditForm(){
		this.values= new HashMap<String, String[]>();
		this.files = new HashMap<String, FormFile[]>();
	}
	

	public FormFile[] getFiles(String property) {
		return this.files.get(property);
	}

	public void setFiles(String property, FormFile[] values) {
		this.files.put(property, values);
	}

	public void setFile(String property, FormFile value) {
		property = property.substring(3);
		if (value == null)
			this.files.remove(property);
		FormFile[] files = this.files.get(property);
		if(files==null){
			this.setFiles(property, new FormFile[]{value});
			
		}else{
			List<FormFile> fileList = new ArrayList<FormFile>();
			for(int i=0;i<files.length;i++){
				fileList.add(files[i]);
			}
			fileList.add(value);
			FormFile[] filesTemp = new FormFile[fileList.size()];
			for(int i=0;i<fileList.size();i++){
				filesTemp[i]=fileList.get(i);
			}
			this.setFiles(property, filesTemp);
		}
	}

	public FormFile getFile(String property) {
		FormFile[] files = this.getFiles(property);
		if (files == null)
			return null;
		if (files.length == 0)
			return null;
		return files[0];
	}

	public String[] getValues(String property) {
		return this.values.get(property);
	}

	public void setValues(String property, String[] values) {
		this.values.put(property, values);
	}

	public void setValue(String property, String value) {
		if (value == null)
			this.values.remove(property);
		this.setValues(property, new String[] { value });
	}
	
	public String[] getValueNames(){
		return this.values.keySet().toArray(new String[this.values.size()]);
	}
	
	public String[] getFileNames(){
		return this.files.keySet().toArray(new String[this.values.size()]);
	}

	public String getValue(String property) {
		String[] values = this.getValues(property);
		if (values == null)
			return null;
		if (values.length == 0)
			return null;
		return values[0];
	}
	public String getContentID() {
		return contentID;
	}

	public void setContentID(String contentID) {
		this.contentID = contentID;
	}

	public String getDefinitionID() {
		return definitionID;
	}

	public void setDefinitionID(String definitionID) {
		this.definitionID = definitionID;
	}

}
