package com.lccx.server.service.login;

import com.alibaba.dubbo.config.annotation.Service;
import com.lccx.api.login.LoginServiceI;
import com.lccx.module.SysUser;
import com.lccx.server.dao.LoginMapper;

import javax.annotation.Resource;

@Service(version = "${demo.service.version}")
public class LoginServiceImpl implements LoginServiceI {

    @Resource
    private LoginMapper loginMapper;

    public SysUser getUser(SysUser data) {
        SysUser user=loginMapper.queryUser(data);
        return user;
    }
}
