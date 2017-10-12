package com.fulong.lyvc.manage.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.fulong.lyvc.manage.base.BaseForm;

/**
 * EditConferenceForm
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class EditConferenceForm extends BaseForm {

	private static final long serialVersionUID = 5358674134815031486L;

	private String title;			//会议标题
	private String desc;			//会议描述
	private String startTime;		//会议开始时间
	private String endTime;			//会议结束时间
	
	private String groupId;			//公共联系人组id
	private String userId;			//主持人id
	private String[] participants;	//参会人员
	
	private String[] delFiles;					//删除的文档
	private HashMap<String, FormFile[]> files;	//上传的文档
	
	public EditConferenceForm(){
		this.files = new HashMap<String, FormFile[]>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setParticipants(String[] participants) {
		this.participants = participants;
	}

	public String[] getParticipants() {
		return participants;
	}
	
	public void setDelFiles(String[] delFiles) {
		this.delFiles = delFiles;
	}

	public String[] getDelFiles() {
		return delFiles;
	}
	
	public void setFile(String property, FormFile value) {
		if (value == null)
			this.files.remove(property);
		
		FormFile[] files = this.files.get(property);
		if(files == null){
			this.setFiles(property, new FormFile[]{value});
		}
		else{
			List<FormFile> fileList = new ArrayList<FormFile>();
			
			for(int i=0; i<files.length; i++){
				fileList.add(files[i]);
			}
			
			fileList.add(value);
			FormFile[] filesTemp = new FormFile[fileList.size()];
			for(int i=0; i<fileList.size(); i++){
				filesTemp[i] = fileList.get(i);
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
	
	public FormFile[] getFiles(String property) {
		return this.files.get(property);
	}

	public void setFiles(String property, FormFile[] values) {
		this.files.put(property, values);
	}
	
	public HashMap<String, FormFile[]> getFiles() {
		return this.files;
	}
	
	public void setFiles(HashMap<String, FormFile[]> files) {
		this.files = files;
	}

}
