package com.lccx.manager.controller.role;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lccx.manager.entity.SysRole;
import com.lccx.manager.frame.ConstantClass;
import com.lccx.manager.service.role.RoleServiceData;
import com.lccx.manager.serviceI.role.RoleServiceI;
import com.lccx.manager.util.RedisUtil;
import com.lccx.manager.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/Manager/Role",produces ={"application/json;charset=UTF-8"})
public class RoleController {

    private Logger logger= LoggerFactory.getLogger("RoleController");

    @Resource
    private RoleServiceI service;

    @Resource
    private RedisUtil redisUtil;


    /**
     * 获取当前登录用户角色信息或根据用户id查询用户角色
     * @param userId
     * 不传userId默认查询当前token下用户角色
     * 传入userId根据传入id查询用户角色
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/User",method = RequestMethod.GET)
    public String role(HttpServletRequest request,String userId){
        if(!Util.isCon(userId)){
            String str=redisUtil.get(request.getHeader(ConstantClass.TOKEN_HEADER),0);
            JSONObject jsonObject=JSONObject.parseObject(str);
            userId=jsonObject.getString("id");
        }
        List<SysRole> list=service.getRole(userId);
        JSONObject json=new JSONObject();
        json.put("success",true);
        if(list.size()>0){
            StringBuffer roles=new StringBuffer();
            for (int i=0;i<list.size();i++){
                roles.append(list.get(i).getId()).append(",");
            }

            json.put("roles",roles.substring(0,roles.length()-1));
        }
        return json.toString();
    }


    /**
     * 分页查询角色列表
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String list(RoleServiceData data){
        JSONObject json=new JSONObject();
        data=service.getPageList(data);
        json.put("data", JSON.toJSONString(data));
        json.put("success",true);
        return json.toString();
    }

    /**
     * 不分页获得角色树形结构
     * @param data
     * @param checked 默认选中的复选框id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Tree",method = RequestMethod.GET)
    public String roleTree(RoleServiceData data,String checked){
        JSONObject json=new JSONObject();
        json.put("success",true);
        List<RoleServiceData> list=service.getNotPageList(data);
        JSONArray arr=new JSONArray();
        boolean bool=false;
        String []checkedArr=checked.split(",");
        if(Util.isCon(checked)){
            bool=true;
        }
        for(int i=0;i<list.size();i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id", list.get(i).getId());
            jsonObject.put("text", list.get(i).getName());
            jsonObject.put("iconCls","fa fa-paper-plane");
            if(bool){
                for(int j=0;j<checkedArr.length;j++){
                    if(list.get(i).getId().equals(checkedArr[j])){
                        jsonObject.put("checked",true);
                        break;
                    }
                }
            }
            arr.add(jsonObject);
        }
        json.put("data",arr);
        return json.toString();
    }


    /**
     * 新增角色
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String save(SysRole data){
        JSONObject json=new JSONObject();
        try {
            service.saveRole(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("code","10000");
            json.put("message","保存数据出错");
            logger.info("保存角色时出错："+e.getMessage());
        }
        return json.toString();
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(String ids){
        JSONObject json=new JSONObject();
        String []arr=ids.split(",");
        try {
            service.deleteRole(arr);
            json.put("success",true);
            json.put("message","删除成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","删除失败");
            json.put("code","10002");
            logger.info("删除角色失败："+e);
        }
        return json.toString();
    }

    /**
     * 修改角色
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public String update(SysRole data){
        JSONObject json=new JSONObject();
        try {
            service.updateRole(data);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","修改失败");
            json.put("code","10001");
            logger.info("修改角色失败："+e);
        }
        return json.toString();
    }


    /**
     * 修改角色下菜单
     * @param menuIds 菜单ids
     * @param id 角色id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public String saveMenu(String menuIds,String id) {
        String [] arr=null;
        JSONObject json=new JSONObject();
        if(Util.isCon(menuIds)){
            arr=menuIds.split(",");
        }
        try {
            service.updateRoleMenu(arr,id);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
            json.put("success",false);
            json.put("message","修改失败");
            json.put("code","10001");
            logger.info("修改角色下菜单时失败："+e);
        }
        return json.toString();
    }

}
