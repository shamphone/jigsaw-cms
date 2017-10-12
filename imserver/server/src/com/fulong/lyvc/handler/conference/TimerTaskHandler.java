package com.fulong.lyvc.handler.conference;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TimerHandler;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.ConferenceFinish;
import com.fulong.lyvc.message.StartConference;

/**
 * TimerTaskHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-15
 */

public class TimerTaskHandler extends TimerHandler {
	
	private Collection<String> olds1;		//该变量用来保存那些正在进行的会议
	private Collection<String> olds2;		//该变量用来保存那些正在进行的会议，以及那些已经结束但仍有用户在会议中的会议
	
	/**
	 * 根据时钟消息处理会议开始与结束方面的事务
	 */
	public void execute() throws Exception {
		
		checkConferenceStartTime();

		checkConferenceEndTime();

		return;
	}
	
	/**
	 * 初始化正在进行中的会议
	 * olds1用来判断新开始的会议，olds2用来判断已经结束的会议
	 */
	public void init(Collection<String> conferences1, Collection<String> conferences2) {
		olds1 = conferences1;
		olds2 = conferences2;
	}
	
	/**
	 * 如果一个会议的状态由"计划中"变为"正在进行"，则通知所有可以参加该会议的在线的用户
	 * @throws SQLException
	 */
	protected void checkConferenceStartTime() throws SQLException {
		Collection<String> news = new ArrayList<String>();

		Collection<ConferenceManager> managers = this.conferenceRepository.getAllConferenceManager();
		for(ConferenceManager conManager : managers) {
			Collection<Conference> list = conManager.getHoldingConferences();
			
			for(Conference con : list)
				news.add(con.getId());
		}
		
		for(String conId : news) {
			//新开始的会议
			if(!olds1.contains(conId)) {
				Conference con = null;
				
				for(ConferenceManager conManager : managers) {
					con = conManager.getConference(conId);
					if(con != null)
						break;
				}
				
				//开始会议
				con.start();
				
				//发送开始会议的信息给该会议中的所有在线用户
				StartConference message = new StartConference();
				message.conferenceId = conId;
				
				Collection<User> members = con.getMembers();
				for (User user : members) {
					TCPChannel channel = server.getChannel(user);
					if (channel != null) {
						sendMessage(channel, message);
					}
				}
			}
		}
		
		olds1.clear();
		olds1.addAll(news);
	}

	/**
	 * 检查会议的结束时间，如果已经过了结束时间，而且会议中没有用户，则关闭该会议
	 * @throws Exception
	 */
	protected void checkConferenceEndTime() throws Exception {
		Collection<String> news = new ArrayList<String>();
		
		Collection<ConferenceManager> managers = this.conferenceRepository.getAllConferenceManager();
		for(ConferenceManager conManager : managers) {
			Collection<Conference> list = conManager.getHoldingConferences();
			
			for(Conference con : list)
				news.add(con.getId());
		}
		
		Collection<String> temp = new ArrayList<String>();
		
		for(String conId : olds2) {
			//会议已经结束
			if(!news.contains(conId)) {
				Conference con = null;
				
				for(ConferenceManager conManager : managers) {
					con = conManager.getConference(conId);
					if(con != null)
						break;
				}
				
				if(con != null) {
					//没有人在会议中了（参加会议的人都退出了会议）
					Collection<User> participants = con.getParticipants();
					if(participants == null || participants.size() == 0) {
						
						//结束该会议
						con.terminate();

						//发送消息通知所有在线的用户会议已经结束
						ConferenceFinish message = new ConferenceFinish();
						message.conId = conId;
						
						Collection<User> members = con.getMembers();
						for (User user : members) {
							TCPChannel channel = this.server.getChannel(user);
							if (channel != null) {
								sendMessage(channel, message);
							}
						}
					}
					//保存那些已经结束但仍有用户在会议中的会议
					else {
						temp.add(conId);
					}
				}
			}
			//保存那些仍然正在进行的会议
			else {
				temp.add(conId);
			}
		}
		
		//保存那些新开始的会议
		for(String conId : news) {
			if(!olds2.contains(conId)) {
				temp.add(conId);
			}
		}
		
		olds2.clear();
		olds2.addAll(temp);
	}
}
