package com.lccx.module;

import java.io.Serializable;

public class SysUser implements Serializable {


    private static final long serialVersionUID = -3572778655965320477L;

    private String id;
    private String name;
    private String loginName;
    private String active;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
