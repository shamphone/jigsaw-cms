package com.fulong.lyvc;

import java.util.Collection;


public interface ConferenceRepository {
	
	/**
	 * 通过租户域名来获取ConferenceManager
	 */
	public ConferenceManager getConferenceManager(String domain);
	
	/**
	 * 获取所有的ConferenceManager
	 */
	public Collection<ConferenceManager> getAllConferenceManager();
	
}
