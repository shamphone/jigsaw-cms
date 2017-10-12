package com.fulong.lyvc;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.lyvc.jcr.JCRConferenceManager;
import com.fulong.lyvc.jcr.ModeRolePropertyDefinition;

public class Init {

	private static JCRConferenceManager cr;
	
	@Test
	public void init() {
		ClassPathResource resource = new ClassPathResource("test.config.xml");
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource);	
		cr = (JCRConferenceManager) beanFactory.getBean("conferenceManager");
		
		//初始化各种会议模式中各个角色的权限
		Collection<Mode> modes = cr.getModes();
		for(Mode mode : modes) {
			Collection<ModeRole> roles = mode.getRoles();
			for(ModeRole role : roles) {
				PropertyDefinition propertyDefinition = ((ModeRolePropertyDefinition)role).getProperty();
				String[] rights = null;
				String name = propertyDefinition.getID();
				
				//常规会议模式中的角色
				if(name.equals("5"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("6"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10"};
				
				//网上培训模式中的角色
				else if(name.equals("7"))
					rights = new String[]{"1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("8"))
					rights = new String[]{"1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("9"))
					rights = new String[]{"2", "4", "5", "8", "10"};
				
				//网上咨询模式中的角色
				else if(name.equals("10"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11"};
				else if(name.equals("11"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11"};
				
				//即时会议模式中的角色
				else if(name.equals("12"))
					rights = new String[]{"1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12"};
				else if(name.equals("13"))
					rights = new String[]{"1", "2", "3", "4", "7", "8", "9", "10", "11"};
				
				//主控会议模式中的角色
				else if(name.equals("14"))
					rights = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
				else if(name.equals("15"))
					rights = new String[]{"1", "2", "3", "4", "14"};
				
				propertyDefinition.setValueConstraints(rights);
			}
		}
	}
}
