package com.fulong.lyvc.manage.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.fulong.lyvc.MediaServer;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPServer;
import com.fulong.lyvc.handler.server.LoginHandler;
import com.fulong.lyvc.manage.base.BaseAction;
import com.fulong.lyvc.manage.form.CreateConferenceForm;
import com.fulong.lyvc.message.AddConference;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * SaveAction
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-9-1
 */

public class SaveAction extends BaseAction {

	/**
	 * 
	 * 创建一个普通会议
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setDomain(request);
		
		CreateConferenceForm form = (CreateConferenceForm) actionForm;

		//去掉前后的空格
		String title = form.getTitle();						//会议标题
		String desc = form.getDesc();						//会议描述
		String startTime = form.getStartTime();				//开始时间
		String endTime = form.getEndTime();					//结束时间
		String userId = form.getUserId();					//主持人
		String[] participants = form.getParticipants(); 	//参会人员 
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
		
		ConferenceManager manager = this.getConferenceManager();
		
		//得到管理员节点id
		String leaserId = manager.getLeaserId();
		
		Conference conference = manager.createNormalConference(title, leaserId, format.parse(startTime), format.parse(endTime), desc);
		//设置主持人
		conference.addMember("5", userId);
		//设置参会人员
		for(String participant : participants) 
			conference.addMember(participant);
		//设置管理员为参会人员（这样管理员以后可以对会议进行管理如修改、删除、发送会议通知等）
		conference.addMember(leaserId);
		
		//获取bean，并进行初始化
		LoginHandler handler = new LoginHandler();
		TCPServer server = (TCPServer) this.getWebApplicationContext().getBean("lyvcServer");
		MediaServer mediaServer = (MediaServer) this.getWebApplicationContext().getBean("mediaServer");
		ConferenceRepository conferenceRepository = (ConferenceRepository) this.getWebApplicationContext().getBean("conferenceRepository");
		handler.setConferenceServer(server);
		handler.setMediaServer(mediaServer);
		handler.setConferenceRepository(conferenceRepository);
		handler.setConferenceManager(this.domain);

		//获取创建者节点
		Node owner = (Node) manager.getGroup(leaserId);
		//获取保存文档的节点
		Node root = owner.getNode("resources");
		if(root == null)
			root = owner.addNode(owner.getRepository().getDefinitionManager().getDefinition("resource-scheme"), "resources");
		
		//得到上传的所有文档
		FormFile[] files = new FormFile[map.size()];
		Iterator<Entry<String, FormFile[]>> iterator = map.entrySet().iterator();
		
		int i = 0;
		while(iterator.hasNext()) {
			files[i++] = iterator.next().getValue()[0];
		}
		
		i = 0;
		ArrayList<String> paths = new ArrayList<String>();
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
				Node newResource = root.addNode(owner.getRepository().getDefinitionManager().getDefinition(SchemeConstant.documentScheme), fileName);
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
		
		//获取资源访问的基本路径
		String url = "http://" + this.domain;
		
		//获取文档信息
		StringBuffer docTitles = new StringBuffer();
		StringBuffer docURLs = new StringBuffer();
		for(i=0; i<paths.size(); i++) {
			docTitles.append(files[i].getFileName()+ ",");
			docURLs.append(url + paths.get(i) + ",");
		}
		
		//设置创建（添加）会议信息
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

		//将创建会议的信息发送给主持人
		TCPChannel channel = handler.getChannel(userId);
		if (channel != null) 
			handler.sendMessage(channel, msgToSend);
		
		//将创建会议的信息发送给参会人员
		for(String participant : participants) {
			channel = handler.getChannel(participant);
			if (channel != null) 
				handler.sendMessage(channel, msgToSend);
		}
		
		//将创建会议的信息发送给管理员
		channel = handler.getChannel(leaserId);
		if(channel != null)
			handler.sendMessage(channel, msgToSend);
		
		return mapping.findForward("success");
	}
}
