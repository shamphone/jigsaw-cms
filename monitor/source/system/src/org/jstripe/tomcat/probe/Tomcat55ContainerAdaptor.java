package org.jstripe.tomcat.probe;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.Wrapper;
import org.apache.commons.modeler.Registry;

/**
 * Created by IntelliJ IDEA. User: ilyschenkov Date: 04-Jan-2006 Time: 15:55:49
 * To change this template use File | Settings | File Templates.
 */
public class Tomcat55ContainerAdaptor extends AbstractTomcatContainer {

	private Host host;
	private ObjectName deployerOName;
	private MBeanServer mBeanServer;

	public void setWrapper(Wrapper wrapper) {
		host = (Host) wrapper.getParent().getParent();
		try {
			deployerOName = new ObjectName(host.getParent().getName()
					+ ":type=Deployer,host=" + host.getName());
		} catch (MalformedObjectNameException e) {
			// do nothing here
		}
		host.getPipeline().addValve(new Tomcat55AgentValve());
		mBeanServer = Registry.getRegistry(null, null).getMBeanServer();
	}

	public boolean canBoundTo(String binding) {
		return binding != null
				&& (binding.startsWith("Apache Tomcat/5.5") || binding
						.startsWith("Apache Tomcat/6.0"));
	}

	public Context findContext(String name) {
		return (Context) host.findChild(name);
	}

	public List<Container> findContexts() {
		Container containers[] = host.findChildren();
		List<Container> contextList = new ArrayList<Container>(containers.length);
		for (int i = 0; i < containers.length; i++) {
			contextList.add(containers[i]);
		}
		return contextList;
	}

	public void stop(String name) throws Exception {
		Context ctx = findContext(name);
		if (ctx != null)
			((Lifecycle) ctx).stop();
	}

	public void start(String name) throws Exception {
		Context ctx = findContext(name);
		if (ctx != null)
			((Lifecycle) ctx).start();
	}

	private void checkChanges(String name) throws Exception {
		Boolean result = (Boolean) mBeanServer.invoke(deployerOName,
				"isServiced", new String[] { name },
				new String[] { "java.lang.String" });
		if (!result.booleanValue()) {
			mBeanServer.invoke(deployerOName, "addServiced",
					new String[] { name }, new String[] { "java.lang.String" });
			try {
				mBeanServer.invoke(deployerOName, "check",
						new String[] { name },
						new String[] { "java.lang.String" });
			} finally {
				mBeanServer.invoke(deployerOName, "removeServiced",
						new String[] { name },
						new String[] { "java.lang.String" });
			}
		}
	}

	public void removeInternal(String name) throws Exception {
		checkChanges(name);
	}

	public void installWar(String name, URL url) throws Exception {
		checkChanges(name);
	}

	public void installContextInternal(String name, File config)
			throws Exception {
		checkChanges(name);
	}

	public File getAppBase() {
		File base = new File(host.getAppBase());
		if (!base.isAbsolute()) {
			base = new File(System.getProperty("catalina.base"), host
					.getAppBase());
		}
		return base;
	}

	public String getConfigBase() {
		File configBase = new File(System.getProperty("catalina.base"), "conf");
		Container container = host;
		Container host = null;
		Container engine = null;
		while (container != null) {
			if (container instanceof Host)
				host = container;
			if (container instanceof Engine)
				engine = container;
			container = container.getParent();
		}
		if (engine != null) {
			configBase = new File(configBase, engine.getName());
		}
		if (host != null) {
			configBase = new File(configBase, host.getName());
		}
		return configBase.getAbsolutePath();
	}

	public Object getLogger(Context context) {
		//return context.getLogger();
		return null;
	}

	public String getHostName() {
		return host.getName();
	}
}
