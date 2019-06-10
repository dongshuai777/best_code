package com.lccx.manager.dao.menu;

import com.lccx.manager.entity.SysMenu;

import java.util.List;

public interface MenuMapper {
    List<SysMenu> queryRoleMenu(String roleIds);
    List<SysMenu> queryMenu(SysMenu data);
    void saveMenu(SysMenu data);
    SysMenu getMenuById(SysMenu data);
    void deleteMenu(String id);
    void updateMenu(SysMenu data);
}
