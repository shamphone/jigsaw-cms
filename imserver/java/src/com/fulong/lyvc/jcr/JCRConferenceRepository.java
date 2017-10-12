package com.fulong.lyvc.jcr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionIterator;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.ConferenceRepository;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.util.SchemeConstant;

public class JCRConferenceRepository implements ConferenceRepository {

	private static final String DOMAINNAME = "domain";				//域名

	private Repository repository;							
	public static Map<String, Mode> modes;							//用来保存所有的会议模式
	private Map<String, ConferenceManager> conferenceManagerMap;	//保存创建的ConferenceManager
	
	public void init() {
		modes = new HashMap<String, Mode>();
		conferenceManagerMap = new HashMap<String, ConferenceManager>();
		
		//初始化所有的会议模式
		NodeDefinition definition = this.repository.getDefinitionManager().getDefinition(SchemeConstant.conferenceScheme);
		NodeDefinitionIterator ndIterator = definition.getInheritDefinitions(true);
		while(ndIterator.hasNext()) {
			NodeDefinition temp = ndIterator.next();
			Mode mode = new ModeNodeDefinition(temp);
			String modeId = mode.getId();
			modes.put(modeId, mode);
		}
	}

	/**
	 * 通过租户域名来获取ConferenceManager
	 */
	public ConferenceManager getConferenceManager(String domain) {
		if(domain == null || domain.equals(""))
			domain = "default";
		
		ConferenceManager manager = conferenceManagerMap.get(domain);
		
		if(manager == null) {
			String leaserId = getLeaserId(domain);

			if(!leaserId.equals("-1")) {
				manager = new JCRConferenceManager(repository, leaserId);
				conferenceManagerMap.put(domain, manager);
			}
			else {
				manager = conferenceManagerMap.get("default");
				if(manager == null) {
					manager = new JCRConferenceManager(repository, leaserId);
					conferenceManagerMap.put("default", manager);
				}
			}
 		}
		
		return manager;
	}
	
	public Collection<ConferenceManager> getAllConferenceManager() {
		return conferenceManagerMap.values();
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
	/**
	 * 通过租户域名获取租户节点
	 */
	private String getLeaserId(String domainName) {
		Query query = this.repository.getQueryManager().createQuery(repository.getDefinitionManager().getDefinition(SchemeConstant.domainName), Query.SQL);
		query.filterByProperty(DOMAINNAME, domainName);
		NodeIterator<Node> iterator = query.nodes();
		if(iterator.hasNext()) {
			Node node = iterator.next();
			return node.getParent().getID();
		}
		
		return "-1";
	}

}
