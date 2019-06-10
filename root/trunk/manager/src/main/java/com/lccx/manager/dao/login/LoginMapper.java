package com.lccx.manager.dao.login;

import com.lccx.manager.entity.SysUser;

import java.util.List;

public interface LoginMapper {

    List<SysUser> queryUser(SysUser data);
}
