/**
 * 
 */
package com.fulong.lyvc.server;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fulong.lyvc.ChannelBrokenHandler;
import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.ConferenceRepository;
import com.fulong.lyvc.EventDispatcher;
import com.fulong.lyvc.EventHandler;
import com.fulong.lyvc.MediaServer;
import com.fulong.lyvc.MessageHandler;
import com.fulong.lyvc.TCPEvent;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.TCPServer;
import com.fulong.lyvc.TimerHandler;
import com.fulong.lyvc.message.BaseMessage;

/**
 * LyvcEventDispatcher
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-14
 */

public class LyvcEventDispatcher extends EventDispatcher implements Runnable {
	
	private static Log logger = LogFactory.getLog(LyvcEventDispatcher.class);
	
	private boolean isStopped;
	private List<TCPEvent> pendingEvents;
	private int[] eventMutex = { 0 };
	private long timerDelay;
	private long timerPeriod;
	private TCPServer server;
	private MediaServer mediaServer;
	
	// Save all event handler
	public Map<Integer, EventHandler> eventHandlerMap;
	// message Type map;
	public Map<Integer, String> messageTypeMap;
	// Save all channel broken message handler
	private List<ChannelBrokenHandler> channelBrokenHandlerList;
	// Save all timer message handler
	private List<TimerHandler> timerTaskHandlerList;
	// Save all channel broken message handler
	private List<MessageHandler> conferenceLibraryMessageHandlerList;
	
	private ConferenceRepository conferenceRepository;
	
	public LyvcEventDispatcher(TCPServer server, MediaServer mediaServer, ConferenceRepository conferenceRepository) {
		this.pendingEvents = new LinkedList<TCPEvent>();
		this.timerDelay = 10000l;
		this.timerPeriod = 60000l;
		this.server = server;
		this.mediaServer = mediaServer;
		this.conferenceRepository = conferenceRepository;
	}
	
	public void init() {
		Thread myThread = new Thread(this);
		myThread.setDaemon(true);
		myThread.setName("EventScannerThread");
		myThread.start();

		Timer timer = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				addEvent(TCPEvent.timerEvent());
			}
		};
		
		timer.schedule(task, this.timerDelay, this.timerPeriod);
	}

	/**
	 * Stop the event scanner. Decrement the reference count and exit the
	 * scanner thread if the ref count goes to 0.
	 */

	public void stop() {
		synchronized (eventMutex) {
			isStopped = true;
			eventMutex.notify();
		}
	}

	/**
	 * For the non-re-entrant listener this delivers the events to the listener
	 * from a single queue. If the listener is re-entrant, then the stack just
	 * calls the deliverEvent method above.
	 */

	public void run() {
		while (true) {
			List<TCPEvent> eventsToDeliver;
			synchronized (this.eventMutex) {
				// First, wait for some events to become available.
				while (pendingEvents.isEmpty()) {
					if (this.isStopped) {
						return;
					}

					// We haven't been stopped, and the event list is indeed
					// rather empty. Wait for some events to come along.
					try {
						eventMutex.wait();
					} catch (InterruptedException ex) {
						return;
					}
				}

				eventsToDeliver = pendingEvents;
				pendingEvents = new LinkedList<TCPEvent>();
			}

			Iterator<TCPEvent> iterator = eventsToDeliver.listIterator();
			while (iterator.hasNext()) {
				TCPEvent event = iterator.next();
				try {
					switch (event.getType()) {
					case TCPEvent.SOCKET_EVENT:
						TCPMessage pBaseMessage = this.processMessage(event);
						// Check for obsolete message from old client
						EventHandler handler = eventHandlerMap.get(Integer.parseInt(pBaseMessage.getId()));
						handler.execute(event.getChannel(), pBaseMessage);
						break;
					case TCPEvent.BROKEN_CHANNEL_EVENT:
						for (int i = 0; i < channelBrokenHandlerList.size(); i++) {
							ChannelBrokenHandler cbHandler = channelBrokenHandlerList.get(i);
							cbHandler.execute(event.getChannel());
						}
						break;
					case TCPEvent.CONFERENCE_EVENT:
						for (int i = 0; i < conferenceLibraryMessageHandlerList.size(); i++) {
							MessageHandler msgHandler = conferenceLibraryMessageHandlerList.get(i);
							msgHandler.execute(event.getMessage());
						}

						break;
					case TCPEvent.TIMER_EVENT:
						for (int i = 0; i < timerTaskHandlerList.size(); i++) {
							TimerHandler timerHandler = timerTaskHandlerList.get(i);
							timerHandler.execute();
						}

						break;
					default:
						logger.error("Bad event type in pendingEvents list");

					}
				} catch (Exception ex) {
					logger.error("Error in process event " + event, ex);
				}
			}
		} // end While
	}

	public void setTimerDelay(long timerDelay) {
		this.timerDelay = timerDelay;
	}

	public void setTimerPeriod(long timerPeriod) {
		this.timerPeriod = timerPeriod;
	}

	public void setEventHandlerMap(Map<Integer, EventHandler> eventHandlerMap) {
		this.eventHandlerMap = eventHandlerMap;
		for (EventHandler handler : this.eventHandlerMap.values()) {
			handler.setConferenceServer(this.server);
			handler.setMediaServer(this.mediaServer);
			handler.setConferenceRepository(conferenceRepository);
		}
	}

	public void setMessageTypeMap(Map<Integer, String> types) {
		for (Iterator<Integer> iterator = types.keySet().iterator(); iterator.hasNext();) {
			Integer key = iterator.next();
			String className = types.get(key);
			try {
				if (!(Class.forName(className).newInstance() instanceof TCPMessage)) {
					types.remove(key);
					logger.error("Error message type for key: " + key + ", class name " + className + ".");
				}
			} catch (Exception e) {
				logger.error("Error message type for key: " + key + ", class name " + className + ".", e);
			}
		}
		this.messageTypeMap = types;
	}

	public void setChannelBrokenHandlerList(List<ChannelBrokenHandler> channelBrokenHandlerList) {
		this.channelBrokenHandlerList = channelBrokenHandlerList;
		for (ChannelBrokenHandler handler : this.channelBrokenHandlerList) {
			handler.setConferenceServer(this.server);
			handler.setMediaServer(this.mediaServer);
			handler.setConferenceRepository(conferenceRepository);
		}
	}

	public void setTimerTaskHandlerList(List<TimerHandler> timerTaskHandlerList) {
		this.timerTaskHandlerList = timerTaskHandlerList;
		for (TimerHandler handler : this.timerTaskHandlerList) {
			handler.setConferenceServer(this.server);
			handler.setMediaServer(this.mediaServer);
			handler.setConferenceRepository(conferenceRepository);
			
			ConferenceManager manager = handler.getConferenceManager();
			
			Collection<Conference> list1 = manager.getAllHoldingConferences();
			Collection<String> conferences1 = new ArrayList<String>();
			for(Conference con : list1)
				conferences1.add(con.getId());
			
			Collection<Conference> list2 = manager.getAllHoldingConferences();
			Collection<String> conferences2 = new ArrayList<String>();
			for(Conference con : list2)
				conferences2.add(con.getId());
			
			handler.init(conferences1, conferences2);
		}
	}

	public void setConferenceLibraryMessageHandlerList(List<MessageHandler> conferenceLibraryMessageHandlerList) {
		this.conferenceLibraryMessageHandlerList = conferenceLibraryMessageHandlerList;
		for (MessageHandler handler : this.conferenceLibraryMessageHandlerList) {
			handler.setConferenceServer(this.server);
			handler.setMediaServer(this.mediaServer);
			handler.setConferenceRepository(conferenceRepository);
		}
	}

	public TCPMessage parseMessage(String xmlString) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xmlString));
		Document document = builder.parse(is);
		Element root = document.getDocumentElement();
		String id = getChildText(root, "id");
		String className = this.messageTypeMap.get(new Integer(id));
		if (className == null)
			throw new IllegalArgumentException("Unknown message id :" + id + " .");
		
		TCPMessage message = (TCPMessage) Class.forName(className).newInstance();
		message.setId(id);
		message.setConferenceId(getChildText(root, "_conferenceid"));
		message.setSenderId(getChildText(root, "_senderid"));
		// ��BaseMessage�����⴦�?������룻
		if (message instanceof BaseMessage) {
			((BaseMessage) message).fromXML(xmlString);
		} 
		else {
			NodeList children = root.getChildNodes();
			Map<String, String> properties = new HashMap<String, String>();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					if (!elem.getTagName().startsWith("_"))
						properties.put(elem.getTagName(), getNodeText(elem));
				}
			}
			BeanUtils.populate(message, properties);
		}
		
		return message;
	}

	protected TCPMessage processMessage(TCPEvent event) throws Exception {
		String xmlString = event.getMessage();
		
		return this.parseMessage(xmlString);
	}

	public static String getChildText(Element root, String tagName) {
		Element child = getChildElement(root, tagName);
		if (child == null)
			return null;
		
		return getNodeText(child);
	}

	private static Element getChildElement(Element elem, String tagName) {
		NodeList children = elem.getElementsByTagName(tagName);
		if (children.getLength() == 0)
			return null;
		
		return (Element) children.item(0);
	}

	public static String getNodeText(Element elem) {
		if (elem.getChildNodes().getLength() == 1)
			return elem.getChildNodes().item(0).getNodeValue();
		
		return null;
	}

	public void addEvent(TCPEvent event) {
		synchronized (this.eventMutex) {
			pendingEvents.add(event);
			eventMutex.notify();
		}
	}
}
