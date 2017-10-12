package com.fulong.lyvc.handler.server;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.message.Login;
/**
 * 
 * DuplicateLoginMessage
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-13
 */
public class DuplicateLoginMessage {
    public TCPChannel channel;
    public Login loginMessage;
}
