package com.fulong.lyvc;

import java.io.IOException;


/**
 * 
 * MediaServer
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-12
 */
public interface MediaServer {
	/**
	 * 服务器IP地址
	 * 
	 * @return
	 */
	public String getIP();

	/**
	 * 设置IP地址
	 * 
	 * @param ip
	 */
	public void setIP(String ip);

	/**
	 * UDP端口
	 * 
	 * @return
	 */
	public int getUDPPort();

	/**
	 * 设置UDP端口
	 * 
	 * @param port
	 */
	public void setUDPPort(int port);

	/**
	 * TCP端口
	 * 
	 * @return
	 */
	public int getTCPPort();

	/**
	 * 设置TCP端口
	 * 
	 * @param port
	 */
	public void setTCPPort(int port);

	/**
	 * 获取通道
	 */
	public TCPChannel getChannel();

	/**
	 * 设置通道
	 * 
	 * @param channel
	 */
	public void setChannel(TCPChannel channel);

	/**
	 * 启动
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException;
}
