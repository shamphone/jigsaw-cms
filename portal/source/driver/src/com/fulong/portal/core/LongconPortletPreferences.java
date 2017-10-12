package com.fulong.portal.core;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import com.fulong.portal.model.PortletWindow;
import com.fulong.portal.model.Preference;
import com.fulong.portal.model.PreferenceSet;
import com.fulong.portal.utils.Enumerator;

/**
 *
 * <p>Title: Longcon Portal</p>
 *
 * <p>Description: Longcon Portal Driver</p>
 *
 * <p>Copyright: Beijing Zhongke Fulong Computer Tech Co.LTD</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech Co.LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */

public class LongconPortletPreferences implements PortletPreferences {
    private PreferenceSet working;
    private PreferenceSet original;
    private PortletWindow window;
    public LongconPortletPreferences(PortletWindow window) {
        this.window = window;
        this.working = new PreferenceSet();
        this.original = new PreferenceSet();
        /**
         * 在页面上定义的Preferences;
         */
        this.original.putAll(this.window.getDefinition().getPreferenceSet());
        /**
         * 在占位符定义文件中定义的占位符Preferences
         */
        this.original.putAll(this.window.getTemplatePreferenceSet());
    }

    // javax.portlet.PortletPreferences implementation --------------------------------------------
    public boolean isReadOnly(String key) {
        return false;
    }

    public String getValue(String key){
        return this.getValue(key,null);
    }

    public String[] getValues(String key){
        return this.getValues(key,new String[0]);
    }

    public String getValue(String key, String def) {
        if (key == null) {
            throw new IllegalArgumentException("key == null");
        }

        String[] defStr = new String[1];
        defStr[0] = def;

        String[] values = this.getValues(key, defStr);

        // null values are allowed
        if ((values == null) || (values.length == 0) || (values[0]==null)||(values[0].length()==0)) {
            return def;
        }

        return values[0];
    }

    public String[] getValues(String key, String[] def) {
        if (key == null) {
            throw new IllegalArgumentException("key == null");
        }

        // get modified preferences
        String[] values = this.working.getValues(key);
        if (values == null) {
            values = this.original.getValues(key);
        }
        if ((values == null) || (values.length == 0)) {
            return def;
        }
        if ((values.length == 1) && (values[0]==null)) {
            return def;
        }
        return values;

    }

    public void setValue(String key, String value) throws ReadOnlyException {
        if (key == null) {
            throw new IllegalArgumentException("key == null");
        }

        String[] values = new String[1];
        values[0] = value;
        setValues(key, values);
    }

    public void setValues(String key, String[] values) throws ReadOnlyException {
        if (key == null) {
            throw new IllegalArgumentException("key == null");
        }

        if (isReadOnly(key)) {
            throw new ReadOnlyException("Preference attribute called " + key +
                                        " may not be modified");
        }
        Preference preference = new Preference();
        preference.setName(key);
        preference.setValues(values);

        working.put(preference);

    }

    @SuppressWarnings("unchecked")
	public Enumeration getNames() {
        return new Enumerator(this.original.names());
    }

    @SuppressWarnings("unchecked")
	public Map getMap() {
        HashMap result = new HashMap(this.original.getMap());
        result.putAll(this.working.getMap());
        return result;
    }

    public void reset(String key) throws ReadOnlyException {
        this.working = new PreferenceSet();
    }

    public PreferenceSet getPreferenceSet() {
         return this.original;
    }



    public void store() throws IOException, ValidatorException {
        this.window.getTemplatePreferenceSet().putAll(this.working);
        this.original.putAll(this.working);
        this.working = new PreferenceSet();
        /*
        String file=request.getSession().getServletContext().getRealPath(request.getServletPath());
        try {
            PortletPageParser parser=new PortletPageParser(file);
        parser.updatePreference(this.window.getId(),this.working);
        parser.save();
        } catch (ParserException ex) {
            throw new IOException(ex.getMessage());
        }
        */
    }

    public String toString(){
        PreferenceSet result=new PreferenceSet();
        result.putAll(this.original);
        result.putAll(this.working);
        return result.toHTML();
    }

}
