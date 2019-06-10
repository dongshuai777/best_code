package com.lccx.manager.service.role;

import com.lccx.manager.dao.role.RoleMapper;
import com.lccx.manager.entity.SysRole;
import com.lccx.manager.entity.SysRoleMenu;
import com.lccx.manager.frame.HelperPage;
import com.lccx.manager.serviceI.role.RoleServiceI;
import com.lccx.manager.util.PrimaryId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleServiceI {

    @Resource
    private RoleMapper mapper;


    @Override
    public List<SysRole> getRole(String userId){
        List<SysRole> list=mapper.queryUserRole(userId);
        return list;
    }


    /**
     * 分页查询
     * @param data
     * @return
     */
    @Override
    public RoleServiceData getPageList(RoleServiceData data) {
        HelperPage.getPage(data);
        List<RoleServiceData> list=mapper.queryList(data);
        HelperPage.setPageMessage(data,list);
        data.setPageList(list);
        return data;
    }


    /**
     * 不分页查询角色列表
     * @param data
     * @return
     */
    @Override
    public List<RoleServiceData> getNotPageList(RoleServiceData data) {
        return mapper.queryList(data);
    }

    /**
     * 新增角色
     * @param data
     * @throws Exception
     */
    @Override
    public void saveRole(SysRole data) throws Exception {
        data.setId(PrimaryId.getId());
        mapper.save(data);
    }

    /**
     * 删除角色
     * @param ids
     * @throws Exception
     */
    @Override
    public void deleteRole(String []ids) throws Exception {
        mapper.delete(ids);
    }

    @Override
    public void updateRole(SysRole data) throws Exception {
        mapper.update(data);
    }


    /**
     * 修改角色下菜单
     * @param menuIds
     * @param id
     * @throws Exception
     */
    @Override
    public void updateRoleMenu(String[] menuIds, String id) throws Exception {
        /**
         * 先删除关联表中所有数据
         */
        mapper.deleteRoleMenuByRoleId(id);
        /**
         * 再进行插入操作
         */
        if(null!=menuIds){
            List<SysRoleMenu> list=new ArrayList<SysRoleMenu>();
            for(int i=0;i<menuIds.length;i++){
                SysRoleMenu data=new SysRoleMenu();
                data.setId(PrimaryId.getId());
                data.setRoleId(id);
                data.setMenuId(menuIds[i]);
                list.add(data);
            }
            mapper.saveRoleMenu(list);
        }


    }
}
