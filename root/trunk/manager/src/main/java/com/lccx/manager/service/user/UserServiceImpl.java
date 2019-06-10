package com.lccx.manager.service.user;

import com.lccx.manager.dao.user.UserMapper;
import com.lccx.manager.entity.SysRoleMenu;
import com.lccx.manager.entity.SysUser;
import com.lccx.manager.entity.SysUserRole;
import com.lccx.manager.frame.HelperPage;
import com.lccx.manager.serviceI.user.UserServiceI;
import com.lccx.manager.util.Encrypt;
import com.lccx.manager.util.PrimaryId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceI {

    @Resource
    private UserMapper mapper;

    public UserServiceData getUser(UserServiceData data){
        HelperPage.getPage(data);
        List<UserServiceData> list=mapper.queryUser(data);
        HelperPage.setPageMessage(data,list);
        data.setPageList(list);
        return data;
    }


    @Override
    public void save(SysUser data) throws Exception {
        data.setId(PrimaryId.getId());
        data.setPwd(Encrypt.EncryptString(data.getPwd()));
        mapper.save(data);
    }


    @Override
    public void update(SysUser data) throws Exception {
        mapper.update(data);
    }


    @Override
    public void delete(String[] ids) throws Exception {
        mapper.delete(ids);
    }


    @Override
    public void updateUserRole(String[] roleIds, String id) throws Exception {
        mapper.deleteUserRoleByUserId(id);
        if(null!=roleIds){
            List<SysUserRole> list=new ArrayList<SysUserRole>();
            for(int i=0;i<roleIds.length;i++){
                SysUserRole data=new SysUserRole();
                data.setId(PrimaryId.getId());
                data.setUserId(id);
                data.setRoleId(roleIds[i]);
                list.add(data);
            }
            mapper.saveUserRole(list);
        }
    }
}
