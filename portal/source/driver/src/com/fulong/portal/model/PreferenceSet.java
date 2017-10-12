package com.fulong.portal.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.Serializable;
/**
 *
 * <p>Title: Longcon Portal Driver</p>
 *
 * <p>Description: Longcon WebMaster</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Technology LTD.</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class PreferenceSet implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6902925067804599967L;
	Map<String,Preference> preferences = new HashMap<String,Preference>();
    private String portletName;

    public void put(String name, String value){
       this.put(name,new String[]{value});

    }
    public void put(String name, String[] values) {
        Preference pref = new Preference();
        pref.setName(name);
        for(int i=0;i<values.length;i++){
            if((values[i]!=null)&&(values[i].length()>0))
            pref.addValue(values[i]);
        }
        put(pref);
    }

    public void put(Preference preference) {
        preferences.put(preference.getName(), preference);
    }

    public void putAll(PreferenceSet set) {
        if (set != null) {
            this.preferences.putAll(set.preferences);
        }
    }

    public String[] getValues(String name) {
        Preference preference = (Preference)this.preferences.get(name);
        if (preference != null) {
            return preference.getValues();
        }
        return null;
    }

    public Iterator<String> names() {
        return this.preferences.keySet().iterator();
    }

    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    public String getPortletName() {
        return portletName;
    }

    public Map<String,String[]> getMap() {
        Map<String,String[]> preferences = new HashMap<String,String[]>();
        for(Iterator<String> iterator=this.preferences.keySet().iterator();
                              iterator.hasNext();){
            String key=(String)iterator.next();
            preferences.put(key, this.getValues(key));
        }
        return preferences;
    }

    public Iterator<Preference> getPreferences(){
        return this.preferences.values().iterator();
    }

    public String toString(){
        return this.toHTML();
    }
    public String toHTML(){
        StringBuffer buffer=new StringBuffer();
        for(Iterator<Preference> iterator=this.preferences.values().iterator();iterator.hasNext();){
            Preference preference=(Preference)iterator.next();
            buffer.append(preference.toHTML());
        }
      return buffer.toString();
    }
    public String toXML(){
        StringBuffer buffer=new StringBuffer();
        for(Iterator<Preference> iterator=this.preferences.values().iterator();iterator.hasNext();){
            Preference preference=(Preference)iterator.next();
            buffer.append(preference.toXML());
        }
      return buffer.toString();
    }

}
