package com.lccx.manager.serviceI.user;

import com.lccx.manager.entity.SysUser;
import com.lccx.manager.service.user.UserServiceData;

public interface UserServiceI {

    SysUser getUser(UserServiceData data);
    void save(SysUser data) throws Exception;
    void update(SysUser data) throws Exception;
    void delete(String []ids) throws Exception;
    void updateUserRole(String []roleIds,String id) throws Exception;
}
