package com.lccx.manager.controller.home;

import com.alibaba.fastjson.JSONObject;
import com.lccx.manager.frame.ConstantClass;
import com.lccx.manager.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/Manager/Home",produces ={"application/json;charset=UTF-8"} )
public class HomeController {


    @Resource
    private RedisUtil redisUtil;


    /**
     * 获取当前用户基本信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request){
        String str=redisUtil.get(request.getHeader(ConstantClass.TOKEN_HEADER),0);
        JSONObject json=JSONObject.parseObject(str);
        json.put("success",true);
        return json.toString();
    }


    /**
     * 用户登出
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/LoginOut",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        JSONObject json=new JSONObject();
        try {
            String token=request.getHeader(ConstantClass.TOKEN_HEADER);
            JSONObject tokenJson=JSONObject.parseObject(redisUtil.get(token,0));
//        jedisPool.getJedisPool().getResource().del(token);//清空redis信息
            tokenJson.put("state","0");
            redisUtil.setex(token, 1800, tokenJson.toJSONString());
        }catch (Exception e){
            json.put("success",false);
            json.put("code","50000");
        }
        json.put("success",true);
        return json.toString();
    }

}
