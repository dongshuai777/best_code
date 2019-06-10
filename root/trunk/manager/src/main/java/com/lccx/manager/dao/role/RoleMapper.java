package com.lccx.manager.dao.role;

import com.lccx.manager.entity.SysRole;
import com.lccx.manager.entity.SysRoleMenu;
import com.lccx.manager.service.role.RoleServiceData;

import java.util.List;

public interface RoleMapper {
    List<SysRole> queryUserRole(String userId);
    List<RoleServiceData> queryList(SysRole data);
    void save(SysRole data);
    void delete(String []ids);
    void update(SysRole data);
    void deleteRoleMenuByRoleId(String id);
    void saveRoleMenu(List<SysRoleMenu> list);
}
