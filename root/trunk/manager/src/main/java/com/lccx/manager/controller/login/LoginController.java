package com.lccx.manager.controller.login;


import com.alibaba.fastjson.JSONObject;
import com.lccx.manager.entity.SysUser;
import com.lccx.manager.serviceI.login.LoginServiceI;
import com.lccx.manager.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping(value = "/Login",produces ={"application/json;charset=UTF-8"} )
public class LoginController {

    private Logger logger= LoggerFactory.getLogger("LoginController");

    @Resource
    private LoginServiceI service;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JWTUtil jwtUtil;

    @Value("${system.name}")
    private String systemName;


    /**
     * 登录获取公钥
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        JSONObject json=new JSONObject();
        try {
            String pk = EncryptUtils.getKey();
            String key = PrimaryId.getId();
            redisUtil.setex(key, 1800, pk);
//        request.setAttribute("pk",pk);
            json.put("pk", pk);
            json.put("key", key);
            json.put("systemName",systemName);
            json.put("success", true);
        }catch (Exception e){
            e.printStackTrace();
            json.put("success",false);
            json.put("message","链接redis失败");
        }
        return json.toString();
    }


    /**
     * 登录
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/Commit")
    public String login(SysUser data,String key){
        JSONObject json=new JSONObject();
        try{
            String pk=redisUtil.get(key,0);
            String pwd= null;
            try {
                pwd = EncryptUtils.aesDecrypt(data.getPwd(),pk);
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.setPwd(Encrypt.EncryptString(pwd));//二次加密
            data=service.getUser(data);
            if(Util.isCon(data)){
                if(data.getActive().equals("1")){
                    String token=jwtUtil.creatJwtToken(data.getLoginName());
                    JSONObject redisJson=new JSONObject();
                    redisJson.put("state","1");
                    redisJson.put("loginTime",new Date());
                    redisJson.put("loginName",data.getLoginName());
                    redisJson.put("id",data.getId());
                    redisJson.put("name",data.getName());
                    redisUtil.setex(token,1800,redisJson.toJSONString());//设置用户已登录
                    json.put("token",token);
                    json.put("success",true);
                    json.put("message","登录成功");
                }else{
                    json.put("success",false);
                    json.put("code","40000");
                    json.put("message","用户状态不可用");
                }

            }else{
                json.put("success",false);
                json.put("code","10000");
                json.put("message","用户名或密码错误");
            }
        }catch (Exception e){
            json.put("success",false);
            json.put("code","50000");//系统报错
            logger.info("系统登录时报错：50000："+e);
        }

        return json.toString();
    }




}
