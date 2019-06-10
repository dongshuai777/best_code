package com.lccx.manager.controller.user;

import com.alibaba.druid.sql.ast.statement.SQLUnique;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lccx.manager.entity.SysUser;
import com.lccx.manager.service.user.UserServiceData;
import com.lccx.manager.service.user.UserServiceImpl;
import com.lccx.manager.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/Manager/User", produces = {"application/json;charset=UTF-8"})
public class UserController {


    private Logger logger= LoggerFactory.getLogger("UserController");

    @Resource
    private UserServiceImpl serviceI;


    /**
     * 用户列表
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String list(UserServiceData data){
        JSONObject json=new JSONObject();
        data= serviceI.getUser(data);
        json.put("data",JSON.toJSONString(data));
        json.put("success",true);
        return json.toString();
    }


    /**
     * 新增用户
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public  String save(SysUser data){
        JSONObject json=new JSONObject();
        try {
            serviceI.save(data);
            json.put("success",true);
            json.put("message","保存成功");
        } catch (Exception e) {
//            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                json.put("message","用户名重复");
            }else{
                json.put("message","保存失败");
            }
            json.put("success",false);
            json.put("code","10000");
            logger.info("用户新增保存失败："+e);
        }
        return json.toString();
    }


    /**
     * 修改用户
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public String update(SysUser data){
        JSONObject json=new JSONObject();
        try {
            serviceI.update(data);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","修改失败");
            json.put("code","10001");
            logger.info("修改用户失败："+e);
        }
        return json.toString();
    }


    /**
     * 删除用户
     * 可批量，多个id逗号分隔
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(String ids){
        JSONObject json=new JSONObject();
        String []arr=ids.split(",");
        try {
            serviceI.delete(arr);
            json.put("success",true);
            json.put("message","删除成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","删除失败");
            json.put("code","10002");
            logger.info("删除用户失败："+e);
        }
        return json.toString();
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public String updateUserRole(String roleIds,String id){
        String []arr=null;
        JSONObject json=new JSONObject();
        if(Util.isCon(roleIds)){
            arr=roleIds.split(",");
        }
        try {
            serviceI.updateUserRole(arr,id);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
//            e.printStackTrace();
            json.put("success",false);
            json.put("message","修改失败");
            json.put("code","10001");
            logger.info("修改用户角色时失败："+e);
        }
        return json.toString();
    }

}
