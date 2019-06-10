package com.lccx.api.login;

import com.lccx.module.SysUser;

public interface LoginServiceI {

    SysUser getUser(SysUser data);//登录查询用户
}
