package com.lccx.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.lccx.api.service.DemoServiceI;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoServiceI {


//    @Resource
//    private JedisPool jedisPool;

    public String dubboDemo(String str) {

//        jedisPool.getResource().setex(str,20,"{'555':'222'}");//20秒后过期
        return "生产者返回消费者发送内容："+str;
    }
}
