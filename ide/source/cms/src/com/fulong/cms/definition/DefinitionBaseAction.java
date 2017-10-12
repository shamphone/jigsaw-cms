package com.fulong.cms.definition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.CMSBaseAction;
import com.fulong.longcon.repository.ChildNodeDefinition;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeDefinitionManager;
import com.fulong.longcon.repository.PropertyDefinition;

/**
 * <p>Title: 龙驭网站内容管理系统</p>
 *
 * <p>Description: 龙驭网站内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 2.0
 */
public abstract class DefinitionBaseAction extends CMSBaseAction {

    /**
     * doPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws
            Exception {
        return this.definitionPerform(mapping, form, request, response);
    }

    public NodeDefinitionManager getDefinitionManager(HttpServletRequest request){
        return this.getRepository(request).getDefinitionManager();
    }

    public abstract ActionForward definitionPerform(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws
            Exception;

    protected void saveOrdinal(PropertyDefinition[] list) {
        int length = list.length;
        for (int i = length - 1; i >= 0; i--) {
            list[i].setOrderNo(--length);
        }
    }
    /*
     * 通过指定的大纲ID（definitionId），以及“伪属性ID（pseudoId）”，获得一个 PropertyDefinition
     */
    protected PropertyDefinition getPropertyDefinition(String definitionId, String pseudoId, String separator,HttpServletRequest request) {
        NodeDefinition definition = this.getRepository(request).getDefinitionManager().getDefinition(definitionId);
        String[] ids = pseudoId.indexOf(separator)>-1 ? pseudoId.split("\\" + separator) : new String[]{pseudoId};
        NodeDefinition tmp = definition;
        int i = 0;
        for (; i < ids.length - 1; i++)
            tmp = ((ChildNodeDefinition) tmp.getPropertyDefinition(ids[i])).getNodeDefinition();
        PropertyDefinition property = tmp.getPropertyDefinition(ids[i]);
        return property;
    }
   /*
    protected ActionForward setForwardPath(ActionMapping mapping, String name, Map paramMap) {
        ActionForward srcForward = mapping.findForward(name);
        ActionForward forward = new ActionForward(srcForward);
        StringBuilder path = new StringBuilder(srcForward.getPath());
        Object[] keys = paramMap.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Object[] values =  paramMap.get((String) keys[i]);
            for (int j = 0; j < values.length; j++) {
                path.append("&" + (String) keys[i] + "=" + this.Encode((String) values[j], "UTF-8"));
            }
        }
        if (path.indexOf("?") == -1)
            path.setCharAt(path.indexOf("&"), '?');

        forward.setPath(path.toString());
        return forward;
    }
 
   
    private String Encode(String str, String charset) {
        String encoded;
        try {
            encoded = URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException ex) {
            encoded = str;
        }
        return encoded;
    }  */
    /*
     * 获得一个“伪属性ID（pseudoId）”对应的ID
     */
    protected String getId(String pseudoId, String separator) {
        String id = null;
        if (pseudoId != null && pseudoId.trim().length() > 0 && separator != null && separator.length() > 0) {
            String[] ids = pseudoId.indexOf(separator) > -1 ? pseudoId.split("\\" + separator) : new String[] {pseudoId};
            id = ids[ids.length];
        }
        return id;
    }
    /*
     * 获得一个“伪'属性ID'（pseudoId）”对应的父“伪'属性ID'”
     */
    protected String getParentPseudoId(String pseudoId, String separator) {
        String id = null;
        if (pseudoId != null && pseudoId.trim().length() > 0 && separator != null && separator.length() > 0) {
            int position = pseudoId.lastIndexOf(separator);
            if (position > -1)
                id = pseudoId.substring(0, position);
        }
        return id;
    }
    /*
     * 获得一个“伪属性ID（pseudoId）”对应的父ID
     */
    protected String getParentId(String pseudoId, String separator) {
        String id = null;
        id = this.getId(this.getParentPseudoId(pseudoId, separator), separator);
        return id;
    }
   /*
    protected class Map {
        private java.util.Map map = new HashMap();

        public Map(){}
     
        public void put(Object key, Object value) {
            if (value == null)
                return;
            if (map.containsKey(key)) {
                Set set = (Set) map.get(key);
                set.add(value);
            } else {
                Set set = new HashSet();
                set.add(value);
                map.put(key, set);
            }
        }
  
        public void put(Object key, Collection values) {
            if (values == null)
                return;
            if (map.containsKey(key)) {
                Set set = (Set) map.get(key);
                set.addAll(values);
            } else {
                Set set = new HashSet();
                set.addAll(values);
                map.put(key, set);
            }
        }
    
        public void put(Object key, Object[] values) {
            if (values != null)
                put(key, Arrays.asList(values));
        }
 
        public Object[] get(Object key) {
            Object[] values = null;
            if (map.containsKey(key))
                values = ((Set) map.get(key)).toArray();
            return values;
        }
    
        public Set keySet() {
            return map.keySet();
        }
    }*/
}
