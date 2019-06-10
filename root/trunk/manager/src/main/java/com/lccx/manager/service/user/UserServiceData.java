package com.lccx.manager.service.user;

import com.lccx.manager.entity.SysUser;

public class UserServiceData extends SysUser {
    private String activeName;//状态名

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }
}
