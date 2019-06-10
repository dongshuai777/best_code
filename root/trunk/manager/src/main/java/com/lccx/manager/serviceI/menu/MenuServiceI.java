package com.lccx.manager.serviceI.menu;

import com.lccx.manager.entity.SysMenu;

import java.util.List;

public interface MenuServiceI {

    List<SysMenu> getRoleMenu(String roleIds);
    List<SysMenu> getMenu(SysMenu data);
    void save(SysMenu data)throws Exception;
    SysMenu getMenuById(SysMenu data);
    void delete(String id)throws Exception;
    void update(SysMenu data)throws Exception;
}
