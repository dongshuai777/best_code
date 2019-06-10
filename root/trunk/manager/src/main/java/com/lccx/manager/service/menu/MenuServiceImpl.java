package com.lccx.manager.service.menu;

import com.lccx.manager.dao.menu.MenuMapper;
import com.lccx.manager.entity.SysMenu;
import com.lccx.manager.serviceI.menu.MenuServiceI;
import com.lccx.manager.util.PrimaryId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuServiceI {


    @Resource
    private MenuMapper mapper;


    @Override
    public List<SysMenu> getRoleMenu(String roleIds) {
        return mapper.queryRoleMenu(roleIds);
    }

    @Override
    public List<SysMenu> getMenu(SysMenu data) {
        return mapper.queryMenu(data);
    }

    @Override
    public void save(SysMenu data) throws Exception{
        data.setId(PrimaryId.getId());
        mapper.saveMenu(data);
    }

    @Override
    public SysMenu getMenuById(SysMenu data) {
        return mapper.getMenuById(data);
    }

    @Override
    public void delete(String id) throws Exception {
        mapper.deleteMenu(id);
    }

    @Override
    public void update(SysMenu data) throws Exception {
        mapper.updateMenu(data);
    }
}
