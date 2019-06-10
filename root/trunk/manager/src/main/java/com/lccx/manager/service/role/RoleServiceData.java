package com.lccx.manager.service.role;

import com.lccx.manager.entity.SysRole;

public class RoleServiceData extends SysRole {

    private String stateName;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
