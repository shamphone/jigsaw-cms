package com.fulong.lyvc.manage.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.fulong.longcon.repository.Node;
import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.ConferenceRepository;
import com.fulong.lyvc.Document;
import com.fulong.lyvc.MediaServer;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPServer;
import com.fulong.lyvc.handler.server.LoginHandler;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.EditConferenceForm;
import com.fulong.lyvc.message.AddConference;
import com.fulong.lyvc.message.DeleteConference;
import com.fulong.lyvc.message.ModifyConference;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * ModifyAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-2
 */

public class ModifyAction extends BaseAction {

	/**
	 * 
	 * 修改普通会议信息
	 */
	@SuppressWarnings("unchecked")
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EditConferenceForm form = (EditConferenceForm) actionForm;
		
		//获取会议id
		this.setDomain(request);
		ConferenceManager manager = this.getConferenceManager();
		String conferenceId = (String) request.getSession().getAttribute("conferenceId");
		Conference conference = manager.getConference(conferenceId);
		
		//获取原来会议的基本信息
		String title = form.getTitle();
		String desc = form.getDesc();
		String startTime = form.getStartTime();
		String endTime = form.getEndTime();
		String userId = form.getUserId();	//主持人
		String[] participants = form.getParticipants(); //参会人员 
		String[] delFiles = form.getDelFiles();			//删除的文档
		HashMap<String, FormFile[]> map = form.getFiles();	//会议中的文档
		
		//主持人不能为空，应该有1个
		if(userId == null || userId == "") {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("conference.hostError"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//参会人员至少应该有1个
		if(participants == null || participants.length == 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("conference.participantsError"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long now = Calendar.getInstance().getTime().getTime();
		long sTime = 0;
		long eTime = 0; 
		try {
			sTime = format.parse(startTime).getTime();
			eTime = format.parse(endTime).getTime(); 
		} catch(Exception e) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("conference.timeFormatError"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//开始时间不能早于当前时间
		if(sTime < now) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("conference.startTimeError"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		//结束时间不能早于开始时间
		else if(sTime >= eTime) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("conference.endTimeError"));
			this.addErrors(request, messages);
			
			return mapping.getInputForward();
		}
		
		//得到创建者id
		String leaserId = manager.getLeaserId();
		
		//获取bean，并进行初始化
		LoginHandler handler = new LoginHandler();
		TCPServer server = (TCPServer) this.getWebApplicationContext().getBean("lyvcServer");
		MediaServer mediaServer = (MediaServer) this.getWebApplicationContext().getBean("mediaServer");
		ConferenceRepository conferenceRepository = (ConferenceRepository) this.getWebApplicationContext().getBean("conferenceRepository");
		handler.setConferenceServer(server);
		handler.setMediaServer(mediaServer);
		handler.setConferenceRepository(conferenceRepository);
		handler.setConferenceManager(this.domain);

		//得到原来会议中的主持人和参会人员
		String oldHost = (String) request.getSession().getAttribute("host");
		String[] oldMembers = (String[]) request.getSession().getAttribute("participants");
		
		//删除会议中原来会议中的主持人和参会人员
		conference.removeMember(oldHost);
		for(String temp : oldMembers) {
			conference.removeMember(temp);
		}
		
		//修改会议信息
		conference.setTitle(title);				//修改会议标题
		conference.setDesc(desc);				//修改会议描述
		conference.setStartTime(format.parse(startTime));	//修改会议开始时间
		conference.setEndTime(format.parse(endTime));		//修改会议结束时间
		conference.addMember("5", userId);		//添加新的主持人
		//添加新的参会人员
		for(String participant : participants) 
			conference.addMember(participant);
		//设置管理员为参会人员（这样管理员以后可以对会议进行管理如修改、删除、发送会议通知等）
		conference.addMember(leaserId);
		
		//获取保存文档的节点
		Node owner = (Node) manager.getGroup(leaserId);
		Node root = owner.getNode("resources");
		if(root == null)
			root = owner.addNode(owner.getRepository().getDefinitionManager().getDefinition(SchemeConstant.documentScheme), "resources");
		
		//获取资源访问的基本路径
		String url = "http://" + this.domain;
		
		//保存文档信息
		StringBuffer docTitles = new StringBuffer();
		StringBuffer docURLs = new StringBuffer();
		
		//得到上传的所有文档
		ArrayList<String> paths = new ArrayList<String>();
		if(map != null && map.size() > 0) {
			FormFile[] files = new FormFile[map.size()];
			Iterator<Entry<String, FormFile[]>> iterator = map.entrySet().iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				files[i++] = iterator.next().getValue()[0];
			}
			
			i = 0;
			for(FormFile file : files) {
				int size = file.getInputStream().available();
				//判断文档是否存在（是否上传时已经被删除）
				if(size > 0) {
					//如果存在重名的文件，则重新命名
					String baseName = file.getFileName();
					String fileName = file.getFileName();
					if(root.getNodes(baseName).getSize() > 0) {
						int start = 0;
						do{
							int pos = baseName.lastIndexOf(".");
							start++;
							if (pos > 0)
								fileName = baseName.substring(0, pos) +  "_" + start + baseName.substring(pos);
							else
								fileName = baseName + start;
						}while (root.getNodes(fileName).getSize() > 0) ;
					}
					
					//保存文档到内容库
					Node newResource = root.addNode(owner.getRepository().getDefinitionManager().getDefinition("resource-scheme"), fileName);
					newResource.setProperty("resource-content", file.getInputStream());
					newResource.setProperty("mime", this.getServlet().getServletContext().getMimeType(fileName));
					newResource.setProperty("createdTime", Calendar.getInstance());
					newResource.setProperty("title", fileName);
					newResource.setProperty("length", newResource.getProperty("resource-content").getLength());
					
					//会议中添加文档
					String path = newResource.getPath();
					conference.addDocument(path);
					
					paths.add(path);
				}
			}
			
			//添加新上传的文档信息
			for(i=0; i<paths.size(); i++) {
				docTitles.append(files[i].getFileName()+ ",");
				docURLs.append(url + paths.get(i) + ",");
			}
		}
		
		//得到原来上传而且没有删除的文档
		Collection<Document> documents = (Collection<Document>) request.getSession().getAttribute("documents");
		if(documents != null && delFiles != null) {
			for(String path : delFiles) {
				Document doc = conference.getDocument(path);
				conference.removeDocument(path);		//删除会议中的文档
				documents.remove(doc);
			}
		}
		
		//添加原来上传而且没有删除的文档信息
		if(documents != null) {
			for(Document doc : documents) {
				docTitles.append(doc.getFileName()+ ",");
				docURLs.append(url + doc.getDocURL() + ",");
			}
		}
		
		//设置修改会议信息
		ModifyConference message = new ModifyConference();
		message.conFileURL = docURLs.toString();
		message.conFileDesc = docTitles.toString();
		message.conDesc = desc;
		message.conId = conference.getId();
		message.conModelId = "1";
		message.conName = title;
		message.endTime = format.parse(endTime);
		message.startTime = format.parse(startTime);
		
		//设置增加会议信息
		AddConference msgToSend = new AddConference();	
		msgToSend.conFilesURL = docURLs.toString();
		msgToSend.conFilesDesc = docTitles.toString();
		msgToSend.isEnded = conference.isEnded();
		msgToSend.isStarted = conference.isStarted();
		msgToSend.creatorId = leaserId;
		msgToSend.conId = conference.getId();
		msgToSend.conDesc = desc;
		msgToSend.conName = title;
		msgToSend.conModelId = "1";
		msgToSend.startTime = format.parse(startTime);
		msgToSend.endTime = format.parse(endTime);	
		
		//设置删除会议的信息
		DeleteConference deleteMessage = new DeleteConference();
		deleteMessage.conId = conference.getId();
		
		//会议信息添加、更新、删除操作
		//得到会议中原来的所有人员
		Collection<String> olds = new ArrayList<String>();
		olds.add(oldHost);
		for(String temp : oldMembers)
			olds.add(temp);
		
		//得到会议目前的所有人员
		Collection<String> news = new ArrayList<String>();
		news.add(userId);
		for(String temp : participants)
			news.add(temp);
		
		for(String temp : news) {
			//如果旧成员中包含新成员，更新会议信息
			if(olds.contains(temp)) {
				TCPChannel channel = handler.getChannel(temp);
				handler.sendMessage(channel, message);
			}
			//如果旧成员中不包含新成员，添加会议信息
			else {
				TCPChannel channel = handler.getChannel(temp);
				handler.sendMessage(channel, msgToSend);
			}
		}
		
		for(String temp : olds) {
			//如果新成员中不包含旧成员，删除会议信息
			if(!news.contains(temp)) {
				TCPChannel channel = handler.getChannel(temp);
				handler.sendMessage(channel, deleteMessage);
			}
		}
		
		//将创建会议的信息发送给管理员
		TCPChannel channel = handler.getChannel(leaserId);
		if(channel != null)
			handler.sendMessage(channel, msgToSend);
		
		//从session中删除保存的变量
		request.getSession().removeAttribute("conferenceId");
		
		return mapping.findForward("success");
	}
}
