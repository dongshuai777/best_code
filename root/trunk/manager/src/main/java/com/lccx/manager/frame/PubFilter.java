package com.lccx.manager.frame;

import com.lccx.manager.frame.jwt.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class PubFilter {

    /**
     * jwt filter
     * @return
     */

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean1() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        JwtFilter jwtFilter = new JwtFilter();
        registrationBean.setFilter(jwtFilter());
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/Manager/*");
        registrationBean.setUrlPatterns(urlPatterns);
//        registrationBean.setOrder(1);
        return registrationBean;
    }
}
