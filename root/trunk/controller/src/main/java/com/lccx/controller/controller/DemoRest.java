package com.lccx.controller.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Rest",produces ={"application/json;charset=UTF-8"} )
public class DemoRest {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String rest(){

        return "前端进行的post提交";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String rest1(){

        return "前端进行的get提交";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String rest2(){

        return "前端进行的delete提交";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public String rest3(){

        return "前端进行的put提交";
    }

}
