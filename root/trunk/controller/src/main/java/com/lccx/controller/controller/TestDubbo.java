package com.lccx.controller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lccx.api.login.LoginServiceI;
import com.lccx.api.service.DemoServiceI;
import com.lccx.module.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestDubbo {

    @Reference(version = "${demo.service.version}",url = "${lccx.address}")
    private DemoServiceI service;

    @Reference(version = "${demo.service.version}",url = "${lccx.address}")
    private LoginServiceI loginService;

    @ResponseBody
    @RequestMapping("/demo")
    public String demo(String str){
        String sss=service.dubboDemo(str);
        return sss;
    }


    @ResponseBody
    @RequestMapping("/testLogin")
    public String testLogin(SysUser user){
        user=loginService.getUser(user);
        System.out.println(user.getName());
        return user.getName();
    }
}
