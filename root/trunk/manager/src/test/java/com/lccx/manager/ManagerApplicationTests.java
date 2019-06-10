package com.lccx.manager;


import com.lccx.manager.serviceI.role.RoleServiceI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerApplicationTests {


    @Resource
    private RoleServiceI serviceI;

    @Test
    public void contextLoads() {

    }

}

