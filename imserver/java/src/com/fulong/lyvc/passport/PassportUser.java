package com.fulong.lyvc.passport;

import com.fulong.lyvc.user.*;

/**
 * 
 * PassportUser
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-11
 */
public class PassportUser implements com.fulong.lyvc.User {

    private com.fulong.longcon.security.User user;
    public PassportUser(com.fulong.longcon.security.User user) {
        this.user = user;
    }

    public long getId() {
        return Long.parseLong(user.getId());
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getAccountName() {
        return user.getUsername();
    }

    public String getFirstName() {
        return user.getCommonname();
    }

    public String getLastName() {
        return "";
    }

    public String getPassword() {
        throw new UserLibraryException("method is not implemented. ");
    }
}

