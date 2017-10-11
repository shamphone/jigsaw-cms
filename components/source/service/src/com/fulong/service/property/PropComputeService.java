package com.fulong.service.property;



import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2010</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author luobin
 * @date 2010-7-1	
 * @version 1.0.1
 */
public class PropComputeService extends NodeService {
	@Override
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		String computType = parameters.getValue("computType");
		String[] properties = parameters.getValues("properties");
		String destProperty = parameters.getValue("destProp");
 		String dateUnit = parameters.getValue("dateUnit");
		
		if(properties==null||properties.length==0){
			throw new IllegalArgumentException("the length of properties must greater than 0.");
		}
		Property firstProp = node.getProperty(properties[0]);
		if(properties.length==1){
			node.setProperty(destProperty, firstProp.getValues());
			return;
		}
		long coefficient = convertToMillis(dateUnit);
		if(computType.equals("plus")){
			switch (firstProp.getType()) {
			case PropertyType.DATE:
				Calendar first = firstProp.getDate();
				Calendar result = Calendar.getInstance();
				result.setTime(first.getTime());
				Property secondProp = node.getProperty(properties[1]);
				switch (secondProp.getType()) {
					case PropertyType.DATE:
						throw new IllegalArgumentException("can't plus two property of type:date.");
					default:
						for(int i=1;i<properties.length;i++){
							Property Prop = node.getProperty(properties[i]);
							int b = (int) Prop.getLong();
							calCalendar(result,b,dateUnit,true);
						}
						node.setProperty(destProperty, result);
					break;
				}
				break;
			case PropertyType.STRING:
				String s = firstProp.getString();
				for(int i=1;i<properties.length;i++){
					Property Prop = node.getProperty(properties[i]);
					String b = Prop.getString();
					s += b;
				}
				node.setProperty(destProperty, s);
				break;
			case PropertyType.DOUBLE:
			case PropertyType.LONG:
				double l2 = firstProp.getDouble();
				for(int i=1;i<properties.length;i++){
					Property Prop = node.getProperty(properties[i]);
					double b = Prop.getDouble();
					l2 += b;
				}
				setProperty(node, destProperty, l2);
				break;
			}
		}else if(computType.equals("minus")){
			switch (firstProp.getType()) {
			case PropertyType.DATE:
				long l = firstProp.getLong();
				Calendar result = Calendar.getInstance();
				result.setTime(firstProp.getDate().getTime());
				Property secondProp = node.getProperty(properties[1]);
				switch (secondProp.getType()) {
					case PropertyType.DATE:
						l -= secondProp.getLong();
						setProperty(node, destProperty, l*1.0/coefficient);
					break;
					default:
						for(int i=1;i<properties.length;i++){
							Property Prop = node.getProperty(properties[i]);
							int b = (int) Prop.getLong();
							calCalendar(result,b,dateUnit,false);
						}
					node.setProperty(destProperty, result);
					break;
				}
				break;
			default:
				double l2 = firstProp.getDouble();
				for(int i=1;i<properties.length;i++){
					Property Prop = node.getProperty(properties[i]);
					double b = Prop.getDouble();
					l2 -= b;
				}
				setProperty(node, destProperty, l2);
				break;
			}
		}else{
			double l = firstProp.getDouble();
			for(int i=1;i<properties.length;i++){
				Property Prop = node.getProperty(properties[i]);
				double b = Prop.getDouble();
				l += b;
			}
			setProperty(node, destProperty, l/properties.length);
		}
	}
	
	private void setProperty(Node node,String destProperty,double value){
		Property destProp = node.getProperty(destProperty);
		switch (destProp.getType()) {
		case PropertyType.DATE:
			node.setProperty(destProperty, (long)value);
			break;
		case PropertyType.LONG:
			node.setProperty(destProperty, (long)value);
			break;
		default:
			node.setProperty(destProperty, value);
			break;
		}
	}
	
	private void calCalendar(Calendar first,int step,String dateUnit,boolean bPlus){
		if(dateUnit.equals("second")){
			first.set(Calendar.SECOND, bPlus?(first.get(Calendar.SECOND)+step):(first.get(Calendar.SECOND)-step));
		}else if(dateUnit.equals("minute")){
			first.set(Calendar.MINUTE, bPlus?(first.get(Calendar.MINUTE)+step):(first.get(Calendar.MINUTE)-step));
		}else if(dateUnit.equals("hour")){
			first.set(Calendar.HOUR_OF_DAY, bPlus?(first.get(Calendar.HOUR_OF_DAY)+step):(first.get(Calendar.HOUR_OF_DAY)-step));
		}else if(dateUnit.equals("date")){
			first.set(Calendar.DATE, bPlus?(first.get(Calendar.DATE)+step):(first.get(Calendar.DATE)-step));
		}else if(dateUnit.equals("month")){
			first.set(Calendar.MONTH, bPlus?(first.get(Calendar.MONTH)+step):(first.get(Calendar.MONTH)-step));
		}else if(dateUnit.equals("year")){
			first.set(Calendar.YEAR, bPlus?(first.get(Calendar.YEAR)+step):(first.get(Calendar.YEAR)-step));
		}
	}
	
	private long convertToMillis(String dateUnit){
		if(dateUnit.equals("second")){
			return 1000;
		}else if(dateUnit.equals("minute")){
			return 1000*60;
		}else if(dateUnit.equals("hour")){
			return 1000*60*60;
		}else if(dateUnit.equals("date")){
			return 1000*60*60*24;
		}else if(dateUnit.equals("year")){
			return 1000L*60*60*24*365;
		}
		return 1;
	}
}
