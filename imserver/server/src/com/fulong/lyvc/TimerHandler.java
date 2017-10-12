/**
 * 
 */
package com.fulong.lyvc;

import java.util.Collection;

/**
 * TimerHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-14
 */

public abstract class TimerHandler extends Handler {
	
	public abstract void execute() throws Exception;

	public abstract void init(Collection<String> conferences1, Collection<String> conferences2);
}
