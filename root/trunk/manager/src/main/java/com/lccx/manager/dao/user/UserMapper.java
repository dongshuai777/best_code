package com.lccx.manager.dao.user;

import com.lccx.manager.entity.SysUser;
import com.lccx.manager.entity.SysUserRole;
import com.lccx.manager.service.user.UserServiceData;

import java.util.List;

public interface UserMapper {
    List<UserServiceData> queryUser(SysUser data);

    void save(SysUser data);

    void update(SysUser data);

    void delete(String []ids);

    void deleteUserRoleByUserId(String id);

    void saveUserRole(List<SysUserRole> list);

}
