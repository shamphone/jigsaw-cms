package com.fulong.service.property.compare;

import java.util.Calendar;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Repository;
import com.fulong.service.ServiceParameters;

public class CompareValue extends Thread {
	private ServiceParameters parameters;
	private Repository repository;
	private int t = 24;

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public CompareValue(Repository repository) {
		this.repository = repository;
	}

	public void run() {
		if (parameters == null) {
			try {
				synchronized (this) {
					this.wait();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		t = Integer.parseInt(parameters.getValue("time"));
		if (t == 0) {
			t = 24;
		}
		String prop1ID = parameters.getValue("prop1ID");
		String prop2ID = parameters.getValue("prop2ID");
		String resultID = parameters.getValue("resultPropID");
		String categoryID = parameters.getValue("compareCategoryID");
		String reducePropID=parameters.getValue("reducePropID");
		String resultMore = parameters.getValue("resultMore");
		String resultLess = parameters.getValue("resultLess");
		NodeDefinition category = repository.getDefinitionManager()
				.getDefinition(categoryID);
		Query query = repository.getQueryManager().createQuery(category,
				Query.SQL);
		query.sortByOrdinal(true);
		NodeIterator<Node> it = query.nodes();
		while (it.hasNext()) {
			Node node = it.next();
			Calendar prop1 = node.getProperty(prop1ID).getValue().getDate();
			if(prop1==null){
				prop1 = Calendar.getInstance();
			}
			Calendar prop2 = node.getProperty(prop2ID).getValue().getDate();
			Property resultProp = node.getProperty(resultID);
			if(prop1!=null&&prop2!=null){
			int i = prop1.compareTo(prop2);
			if (i == 1) {// prop1>prop2
				resultProp.setValue(resultMore);
			} else {
				resultProp.setValue(resultLess);
			}
			long day=(prop2.getTimeInMillis()-prop1.getTimeInMillis())/86400000;
			if(day<0){
				day=0;
			}
			node.getProperty(reducePropID).setValue(day);
			}
		}
		try {
			synchronized (this) {
				CompareValue.sleep(t * 60 * 60 * 1000);
			}
		} catch (Exception e) {

		}
	}

	public ServiceParameters getParameters() {
		return parameters;
	}

	public void setParameters(ServiceParameters parameters) {
		this.parameters = parameters;
		synchronized (this) {
			this.notifyAll();
		}
	}
}
