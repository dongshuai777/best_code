package com.lccx.manager.serviceI.role;

import com.lccx.manager.entity.SysRole;
import com.lccx.manager.service.role.RoleServiceData;

import java.util.List;

public interface RoleServiceI {
    List<SysRole> getRole(String userId);

    RoleServiceData getPageList(RoleServiceData data);

    List<RoleServiceData> getNotPageList(RoleServiceData data);

    void saveRole(SysRole data)throws Exception;

    void deleteRole(String []ids)throws Exception;

    void updateRole(SysRole role)throws Exception;

    void updateRoleMenu(String []menuIds,String id) throws Exception;
}
