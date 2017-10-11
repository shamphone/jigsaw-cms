/**
 * 
 */
package com.fulong.service.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 
 * 邮件发送服务
 * 
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author mali
 * 
 * @version 1.0
 * 
 */
public class SendMailService extends NodeService {

	@Override
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {

		// 邮箱服务器地址
		String server = parameters.getValue("server");
		// 寄信人邮箱地址
		String from = parameters.getValue("from");
		// mailPassword
		String mailPassword = parameters.getValue("mailPassword");
		// 收件人分类
		// String toCategory = parameters.getValue("CategoryID");
		// 收件人邮件属性
		String mailAddress = parameters.getValue("emailPropID");
		// to-email
		String[] to_email = ValueUtils.toStringArray(node.getProperty(
				mailAddress).getValues());
		// 邮件标题
		String title = parameters.getValue("title");
		// 邮件内容
		String content = parameters.getValue("content");
		// 链接
		String link = parameters.getValue("address");
		// 密码
		String password = parameters.getValue("password");
		// 替换属性
		String[] properties = parameters.getValues("properties");
		if (properties!=null && properties[0] != null)
			for (int i = 0; i < properties.length; i++) {
				if (link != null)
					link = link.replaceAll("\\$" + String.valueOf((i+1)), node.getProperty(
							properties[i]).getString());
				content = content.replaceAll("\\$" + String.valueOf((i+1)), node.getProperty(
						properties[i]).getString());
			}

		if (link != null) {
			link = "<a href='http://" + link + "'>" + link + "</a>" + "<br>";
			content = content.replaceAll("\\$link", link);
		}
		
		if(password != null && node.getProperty(password) != null){
			password = node.getProperty(password).getString();
			DesEncrypter encrypter = new DesEncrypter("fulong");
			password = encrypter.decrypt(password);
			content = content.replaceAll("\\$password", password);
		}
		//
		content = content.replaceAll("\\$date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));

		// javamail发送邮件
		Properties props = new Properties();
		Session sendMailSession;
		sendMailSession = Session.getInstance(props, null);
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.auth", "true");

		Message newMessage = new MimeMessage(sendMailSession);

		// 设置寄件人
		newMessage.setFrom(new InternetAddress(from));

		List<InternetAddress> list = new ArrayList<InternetAddress>();

		InternetAddress[] ia = { new InternetAddress() };

		for (int j = 0; j < to_email.length; j++) {
			list.add(new InternetAddress(to_email[j]));
		}

		// 设置收件人
		newMessage.addRecipients(Message.RecipientType.TO, list.toArray(ia));

		// 设置标题
		newMessage.setSubject(title);

		// 设置送信时间
		newMessage.setSentDate(new Date());

		// 设置内容
		newMessage.setContent(content, "text/html;charset=UTF-8");

		// 存储邮件信息
		newMessage.saveChanges();

		// 以smtp方式登录邮箱
		Transport transport = sendMailSession.getTransport("smtp");
		transport.connect(server, from, mailPassword);

		// 发送邮件
		transport.sendMessage(newMessage, newMessage.getAllRecipients());// 发送邮件,其中第二个参数是所有已设好的收件人地址
		transport.close();
	}

}
