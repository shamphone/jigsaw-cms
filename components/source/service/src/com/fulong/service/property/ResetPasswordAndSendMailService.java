package com.fulong.service.property;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 文具网修改更新密码邮件发送服务
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
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
 * @author liuzijun
 * @version 1.0
 */
public class ResetPasswordAndSendMailService extends NodeService {

	protected static final String DES_KEY = "fulong";
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			//用户大纲分类
			String CategoryID = parameters.getValue("CategoryID");
			// 用户名
			String username = parameters.getValue("userNamePropID");
			// 用户输入username
			String username2 = node.getProperty(parameters.getValue("userNamePropID2")).getString();
			// 密码
			String password = parameters.getValue("passwordPropID");
			// email
			String email = parameters.getValue("emailPropID");
			// 是否重置密码
			String isReset = parameters.getValue("isReset");
			// 邮件标题
			String emailTitle = parameters.getValue("emailTitle");
			// 邮件内容
			String content = parameters.getValue("content");
			//邮箱服务器地址
			String server = parameters.getValue("server");
			//寄信人邮箱地址
			String from = parameters.getValue("from");
			//mailPassword
			String mailPassword = parameters.getValue("mailPassword");
			
			//用户库
			NodeDefinition def = this.repository.getDefinitionManager().getDefinition(CategoryID);
			
			Query query = this.repository.getQueryManager().createQuery(def, Query.SQL);
			
			query.filterByProperty(username, username2);
			
			NodeIterator<Node> nodes = query.nodes(true);
			long nodeSize = nodes.getSize();
			if(nodeSize>0){
				if(nodeSize==1){
					Node user = nodes.nextNode();
					DesEncrypter encrypter = new DesEncrypter(DES_KEY);
					if(isReset!=null&&isReset.equals("false")){
						String oldPassword = user.getProperty(password).getString();
						oldPassword = encrypter.decrypt(oldPassword);
						content = content.replaceAll("\\$username", username2).replaceAll("\\$password", oldPassword);
					}else{
						String newPassword = getRandom();//随机生成一个8位的密码并对其加密置入密码属性中
						String EN_NewPassword = encrypter.encrypt(newPassword);
						user.setProperty(password, EN_NewPassword);
						String password2 = user.getProperty(password).getString();
						email = user.getProperty(email).getString();
						content = content.replaceAll("\\$username", username2).replaceAll("\\$password", newPassword);
					}
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
					newMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					
					//设置标题
					newMessage.setSubject(emailTitle);
					
					//设置送信时间
					newMessage.setSentDate(new Date());
					
					//设置内容
				    newMessage.setContent(content,"text/html;charset=UTF-8");
					
					//存储邮件信息
					newMessage.saveChanges();
					
					//以smtp方式登录邮箱
					Transport transport=sendMailSession.getTransport("smtp");
					transport.connect(server,from,mailPassword);
					
					//发送邮件
					transport.sendMessage(newMessage,newMessage.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址 
					transport.close();
				}else{
					throw new IllegalArgumentException("the user named "+ username2 +" is not unique in definition "+CategoryID+".");
				}
			}
		}
	}
	protected String getRandom(){
		String[] a2 = { "0 ",   "1 ",   "2 ",   "3 ",   "4 ",   "5 ",   "6 ",   "7 ",   "8 ",   "9 ",   "a ",   "b ",   "c ",   "d ",   "e ",   "f ",   "g ",   "h ",   "i ",   "j ",   "k ",   "l ",   "m ",   "n ",   "o ",   "p ",   "q ",   "r ",   "s ",   "t ",   "u ",   "v ",   "w ",   "x ",   "y ",   "z "}; 
		Random r = new Random(); 
		String result = " "; 
		while(result.length() <8){
			int temp = r.nextInt(36); 
			if(result.indexOf(temp+ " ")==-1){
				result = result + a2[temp]; 
			} 
		}
		return result;
	}
}
