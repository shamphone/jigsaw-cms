package com.fulong.service.cff;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.repository.Node;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 邮件自动发送
 * <p>
 * Title: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同服务管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2010
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author lixiang
 * @version 3.1
 */

public class SendMailService  extends NodeService {
	
	private String editorPath;
	
	public void setEditorPath(String path){
		this.editorPath = path;
	}

	public String doEdit(HttpServletRequest request, HttpServletResponse response, ServiceParameters parameters) throws Exception {
		return this.editorPath;
	}

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception {
		
		String from = parameters.getValue("from");
		String password = parameters.getValue("password");
		String server = parameters.getValue("server");
		String subject = parameters.getValue("subject");
		String path = parameters.getValue("path");
		String text=parameters.getValue("text");
		
		String mail = node.getProperty("Email").getString();
		String[] nodeId = node.getProperty("content").getArray();
		
		String temp="";
		
		//设置需要发送的超链接内容页地址
		for(int i=0;i<nodeId.length;i++)
		{
			temp = temp+"<a href='"+path+"?contentId="+nodeId[i]+"'>"+path+"?contentId="+nodeId[i]+"</a>"+"<br>";
		}
		
		//要发送的内容
		String context = text+"<br>"+temp;
		
		//javamail发送邮件
		Properties props = new Properties();
		Session sendMailSession;
		sendMailSession = Session.getInstance(props, null);
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.auth", "true"); 
		
		Message newMessage = new MimeMessage(sendMailSession);
		
		//设置寄件人
		newMessage.setFrom(new InternetAddress(from));
		
		//设置收件人
		newMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
		
		//设置标题
		newMessage.setSubject(subject);
		
		//设置送信时间
		newMessage.setSentDate(new Date());
		
		//设置内容
	    newMessage.setContent(context,"text/html;charset=UTF-8");
		
		//存储邮件信息
		newMessage.saveChanges();
		
		//以smtp方式登录邮箱
		Transport transport=sendMailSession.getTransport("smtp");
		transport.connect(server,from,password);
		
		//发送邮件
		transport.sendMessage(newMessage,newMessage.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址 
		transport.close();   
	}
}