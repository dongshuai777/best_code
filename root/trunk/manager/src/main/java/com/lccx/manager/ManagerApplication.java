package com.lccx.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.lccx.manager.dao.*")
public class ManagerApplication{

    protected static final Logger logger= LoggerFactory.getLogger("ManagerApplication");

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }


}

