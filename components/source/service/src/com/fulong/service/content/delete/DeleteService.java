package com.fulong.service.content.delete;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;

import com.fulong.longcon.security.PassportProvider;
import com.fulong.longcon.site.impl.SiteFactoryImpl;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 内容自动共享--内容发送线程类
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

public class DeleteService extends NodeService implements Runnable {

	private ArrayList<String> contentIDs = new ArrayList<String>();
	private String threadName = "SendContent Thread";
	private PassportProvider passportProvider;
	private Thread thread;
	private SiteFactoryImpl siteFactory;
	private ServiceParameters parameters;
	private String localCategoryID;
	private int remoteNum;

	public void init() {
		super.init();
		thread = new Thread(this, threadName);
		thread.start();
	}

	public void run() {
		while (true) {
			// 从工作流配置文件中读取内容发送的相关参数,若有参数为空,则发送线程休眠
			ArrayList<RemoteInfo> remoteList = parseParameters(this.parameters);
			// 根据内容ID队列发送内容
			if (!this.contentIDs.isEmpty()) {
				Iterator<String> it = this.contentIDs.iterator();

				while (it.hasNext()) {
					String contentID = it.next();
					it.remove();

					Iterator<RemoteInfo> iterator = remoteList.iterator();
					while (iterator.hasNext()) {
						RemoteInfo remoteInfo = iterator.next();
						String remoteURL = remoteInfo.getUrl();
						String remoteCategoryID = remoteInfo.getCategoryID();

						try {
							// 本地请求内容XML后，向远程内容分类发送内容
							String remoteDomain = remoteURL.split("\\/")[2];

							String nodeURL = remoteURL + "/xml/" + remoteCategoryID + "/" + contentID + ".xml";
							
							HttpClient client = new HttpClient();
							client.getHostConfiguration().setHost(remoteDomain,80, "http");
							
							DeleteMethod delete = new DeleteMethod(nodeURL);
							delete.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8 ");
							
							int statusCode1 = client.executeMethod(delete);

							if (statusCode1 == HttpStatus.SC_OK) {
								this.deleteContentQueue(contentID);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				try {
					synchronized (this) {
						this.wait();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public PassportProvider getPassportProvider() {
		return passportProvider;
	}

	public void setPassportProvider(PassportProvider passportProvider) {
		this.passportProvider = passportProvider;
	}

	public void deleteContentQueue(String id) {
		this.contentIDs.remove(id);

	}

	public ArrayList<String> getContentIDs() {
		return contentIDs;
	}

	public void setContentIDs(ArrayList<String> contentIDs) {
		this.contentIDs = contentIDs;
		synchronized (this) {
			this.notifyAll();
		}
	}

	public SiteFactoryImpl getSiteFactory() {
		return siteFactory;
	}

	public void setSiteFactory(SiteFactoryImpl siteFactory) {
		this.siteFactory = siteFactory;
	}

	public String getLocalCategoryID() {
		return localCategoryID;
	}

	public void setParameters(ServiceParameters parameters) {
		this.parameters = parameters;
	}

	public ServiceParameters getParameters() {
		return parameters;
	}

	public ArrayList<RemoteInfo> parseParameters(ServiceParameters parameter) {
		ArrayList<RemoteInfo> remoteList = new ArrayList<RemoteInfo>();
		if (parameter != null) {
			this.localCategoryID = parameters.getValue("localCategoryID");
			
			if (this.localCategoryID == null) {
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 1; i <= remoteNum; i++) {
				String tempURL = parameters.getValue("remote"+i+"Domain");
				String tempCategoryID = parameters.getValue("remote"+i+"CategoryID");
				if(tempURL!=null&&tempCategoryID!=null){
					RemoteInfo remoteInfo=new RemoteInfo(tempURL,tempCategoryID);
					remoteList.add(remoteInfo);
				}
			}
		}
		return remoteList;
	}

	private class RemoteInfo {
		private String url;
		private String categoryID;

		public RemoteInfo(String url, String categoryID) {
			this.url = url;
			this.categoryID = categoryID;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getCategoryID() {
			return categoryID;
		}

		public void setCategoryID(String categoryID) {
			this.categoryID = categoryID;
		}
	}

	public void setRemoteNum(int remoteNum) {
		this.remoteNum=remoteNum;
	}
}
