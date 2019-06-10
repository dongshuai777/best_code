package com.lccx.manager.service.login;

import com.lccx.manager.dao.login.LoginMapper;
import com.lccx.manager.entity.SysUser;
import com.lccx.manager.serviceI.login.LoginServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginServiceI {


    @Resource
    private LoginMapper mapper;


    /**
     * 查询单个用户
     * @param data
     * @return
     */
    @Override
    public SysUser getUser(SysUser data) {
        List<SysUser> list=mapper.queryUser(data);
        if(list.size()==1)
            return list.get(0);
        else
            return null;
    }
}
