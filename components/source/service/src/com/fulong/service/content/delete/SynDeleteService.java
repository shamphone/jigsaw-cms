package com.fulong.service.content.delete;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.site.impl.SiteFactoryImpl;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 内容自动共享--内容自动发送服务类
 * 可删除选择的内容，同时同步到其他网站。可配置参数：
 * 1．本地内容分类：选择要删除的内容分类。
 * 2．远程网站：要进行删除同步的远程网站地址。
 * 3．远程分类：对应的远程网站的内容分类。
 * 
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2009
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author sunyuchao
 * @version 3.1
 */

public class SynDeleteService extends NodeService {
	private SiteFactoryImpl siteFactory;
	private ArrayList<String> contentIDs = null;
	private PassportProvider passportProvider;
	private DeleteService deleteContent;
	private String editorPath;
	private Thread t; 
	
	public void init(){
		super.init();
		deleteContent =new DeleteService();
		deleteContent.setPassportProvider(passportProvider);
		deleteContent.setSiteFactory(siteFactory);
		deleteContent.setRemoteNum(5);
		t=new Thread(deleteContent);
		t.setDaemon(true);
		
		t.start();
	}
	
	public void setEditorPath(String path) {
		this.editorPath = path;
	}

	@Override
	public String doEdit(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters)
			throws Exception {
		return this.editorPath;
	}

	@Override
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, ServiceParameters parameters)
			throws Exception {
		super.doUpdate(request, response, parameters);
	}

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			this.contentIDs=StringArray2ArrayList(parameters.getValues("nodes"));
			this.contentIDs.add(node.getID());
			parameters.setValues("nodes", this.contentIDs.toArray(new String[this.contentIDs.size()]));
			deleteContent.setParameters(parameters);
			deleteContent.setContentIDs(this.contentIDs);
		}
	}

	private ArrayList<String> StringArray2ArrayList(String[] values) {
		ArrayList<String> arrayList=new ArrayList<String>();
		if(values!=null){
			for(int i=0;i<values.length;i++){
				arrayList.add(values[i]);
			}
		}
		return arrayList;
	}

	public PassportProvider getPassportProvider() {
		return passportProvider;
	}

	public void setPassportProvider(PassportProvider passportProvider) {
		this.passportProvider = passportProvider;
	}

	public DeleteService getDeleteContent() {
		return deleteContent;
	}

	public void setDeleteContent(DeleteService deleteContent) {
		this.deleteContent = deleteContent;
	}

	public SiteFactoryImpl getSiteFactory() {
		return siteFactory;
	}

	public void setSiteFactory(SiteFactoryImpl siteFactory) {
		this.siteFactory = siteFactory;
	}
	
}
