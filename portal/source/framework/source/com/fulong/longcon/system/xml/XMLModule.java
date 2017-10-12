package com.fulong.longcon.system.xml;

import com.fulong.longcon.system.Module;
import java.util.List;
import java.util.Vector;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class XMLModule
    implements Module {
    private boolean active;
    private String name;
    private String URL;
    private String position;
    private String ID;
    private List<String> roles;
    public void setActive(boolean active) {
        this.active = active;
        this.roles=new Vector<String>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setRoles(String roles){
        String[] splits=roles.split("\\W");
        for(int i=0;i<splits.length;i++){
            String role=splits[i].trim();
            if(role.length()>0)
                this.roles.add(role);
        }
    }

    public boolean isAvaibleTo(String role){
        return this.roles.contains(role);
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getID() {
        return ID;
    }

    public String getURL() {
        return URL;
    }

    public boolean equals(Object obj){
        Module another=(Module)obj;
        return another.getID().equals(this.getID());
    }

    public String[] getUserRoles() {
        return (String[])this.roles.toArray(new String[this.roles.size()]);
    }
}
