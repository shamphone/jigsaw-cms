package com.fulong.portal.servlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

/**
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class ParameterMap implements Map<String, Object> {
    private HashMap<String,Object> parameters;
    public ParameterMap() {
        this.parameters=new HashMap<String,Object>();
    }

    public void addParameter(String name,String value){
    	if(parameters.get(name)==null){
    		String[] textValues = new String[1];
    		textValues[0] = value;
    		parameters.put(name,textValues);
    	}else{
    		String[] textValues = (String[]) parameters.get(name);
            if (textValues != null) {
                String[] textValues2 = new String[textValues.length + 1];
                System.arraycopy(textValues, 0, textValues2, 0, textValues.length);
                textValues2[textValues.length] = value;
                textValues = textValues2;
            } else {
                textValues = new String[1];
                textValues[0] = value;
            }
            parameters.put(name,textValues);
    	}
    }
    public void addParameter(String name,FileItem value){
    	FileItem[] textValues = (FileItem[]) parameters.get(name);
           if (textValues != null) {
               FileItem[] textValues2 = new FileItem[textValues.length + 1];
               System.arraycopy(textValues, 0, textValues2, 0, textValues.length);
               textValues2[textValues.length] = value;
               textValues = textValues2;
           } else {
               textValues = new FileItem[1];
               textValues[0] = value;
         }
         parameters.put(name,textValues);
    }

    /**
     * Removes all mappings from this map (optional operation).
     *
    */
    public void clear() {
        this.parameters.clear();
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.
     *
     * @param key key whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map contains a mapping for the
     *   specified key.
     */
    public boolean containsKey(Object key) {
        return this.parameters.containsKey(key);
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map maps one or more keys to the
     *   specified value.
     */
    public boolean containsValue(Object value) {
        return this.parameters.containsValue(value);
    }

    /**
     * Returns a set view of the mappings contained in this map.
     *
     * @return a set view of the mappings contained in this map.
     */
    @SuppressWarnings("unchecked")
	public Set entrySet() {
        return this.parameters.entrySet();
    }

    /**
     * Returns the value to which this map maps the specified key.
     *
     * @param key key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or
     *   <tt>null</tt> if the map contains no mapping for this key.
     */
    public Object get(Object key) {
        return this.parameters.get(key);
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    /**
     * Returns a set view of the keys contained in this map.
     *
     * @return a set view of the keys contained in this map.
     */
    @SuppressWarnings("unchecked")
	public Set keySet() {
        return this.parameters.keySet();
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return previous value associated with specified key, or
     *   <tt>null</tt> if there was no mapping for key. A <tt>null</tt>
     *   return can also indicate that the map previously associated
     *   <tt>null</tt> with the specified key, if the implementation
     *   supports <tt>null</tt> values.
     */
    public Object put(String key, Object value) {
        return this.parameters.put(key,value);
    }

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).
     *
     * @param t Mappings to be stored in this map.
     */
    @SuppressWarnings("unchecked")
	public void putAll(Map t) {
        this.parameters.putAll(t);
    }

    /**
     * Removes the mapping for this key from this map if it is present
     * (optional operation).
     *
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or
     *   <tt>null</tt> if there was no mapping for key.
     */
    public Object remove(Object key) {
        return this.parameters.remove(key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return this.parameters.size();
    }

    /**
     * Returns a collection view of the values contained in this map.
     *
     * @return a collection view of the values contained in this map.
     */
    @SuppressWarnings("unchecked")
	public Collection values() {
        return this.parameters.values();
    }
}
