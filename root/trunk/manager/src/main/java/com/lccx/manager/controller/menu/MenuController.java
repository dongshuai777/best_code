package com.lccx.manager.controller.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lccx.manager.entity.SysMenu;
import com.lccx.manager.serviceI.menu.MenuServiceI;
import com.lccx.manager.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.awt.*;
import java.util.List;

@Controller
@RequestMapping(value = "/Manager/Menu",produces ={"application/json;charset=UTF-8"} )
public class MenuController {

    @Resource
    private MenuServiceI service;

    /**
     * 根据角色查询用户菜单
     * @param roles
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Role",method = RequestMethod.GET)
    public String menu(String roles){
        List<SysMenu> list=service.getRoleMenu(roles);
        JSONArray array=JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject json=new JSONObject();
        json.put("success",true);
        json.put("data",array);
        return json.toString();
    }




    /**
     * 获得菜单树
     * @param data
     * @param checked 复选框默认选中的 id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Tree",method = RequestMethod.GET)
    public String getMenuTree(SysMenu data,String checked){
        List<SysMenu> list=service.getMenu(data);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("success",true);
        JSONArray arr=new JSONArray();
        boolean bool=false;
        String []checkedArr=checked.split(",");
        if(Util.isCon(checked)){
            bool=true;
        }
        for(int i=0;i<list.size();i++){
            JSONObject json=new JSONObject();
            json.put("id", list.get(i).getId());
            json.put("text", list.get(i).getName());
            json.put("iconCls","fa fa-folder");
            json.put("state","closed");
            if(bool){
                for(int j=0;j<checkedArr.length;j++){
                    if(list.get(i).getId().equals(checkedArr[j])){
                        json.put("checked",true);
                        break;
                    }
                }
            }
            arr.add(json);
        }
        jsonObject.put("data",arr);
        return jsonObject.toString();
    }

    /**
     * 菜单新增
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String add(SysMenu menu){
        JSONObject json=new JSONObject();
        try {
            service.save(menu);
            json.put("success",true);
            json.put("message","保存成功");
        }catch (Exception e){
            e.printStackTrace();
            json.put("success",false);
            json.put("message","保存失败");
        }
        return json.toString();
    }

    /**
     * 根据id查询单条信息
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Menu",method = RequestMethod.GET)
    public String getMenuById(SysMenu data){
        JSONObject json=new JSONObject();
        data=service.getMenuById(data);
        json.put("data",JSON.toJSONString(data));
        json.put("success",true);
        return json.toString();
    }

    /**
     * 根据菜单id删除，若为一级菜单则删除一级菜单下所有子菜单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.DELETE)
    public String delete(String id){
        JSONObject json=new JSONObject();
        try {
            service.delete(id);
            json.put("success",true);
            json.put("message","删除成功");
        } catch (Exception e) {
            //e.printStackTrace();
            json.put("success",false);
            json.put("message","删除失败");
        }
        return json.toString();
    }

    /**
     * 菜单修改
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.PUT)
    public String update(SysMenu data){
        JSONObject json=new JSONObject();
        try {
            service.update(data);
            json.put("success",true);
            json.put("message","修改成功");
        } catch (Exception e) {
            //e.printStackTrace();
            json.put("success",false);
            json.put("message","修改失败");
        }
        return json.toString();
    }
}
